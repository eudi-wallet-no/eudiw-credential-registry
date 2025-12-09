package no.idporten.eudiw.credential.registry.integration;


import no.idporten.eudiw.credential.registry.response.CredentialRegisterService;
import no.idporten.eudiw.credential.registry.response.model.Credentials;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;

@DisplayName("credentials testing")
@SpringBootTest
@ActiveProfiles("test")
public class CredentialsTest {
    private static final Logger log = LoggerFactory.getLogger(CredentialsTest.class);

    private CredentialRegisterService credentialRegisterService;
    @MockitoBean
    private CredentialIssuerMetadataRetriever mockRetriever;


    @Autowired
    public CredentialsTest(CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever) {
        this.mockRetriever = credentialIssuerMetadataRetriever;
        MockData mockData = new MockData();
        Mockito.when(mockRetriever.getListOfIssuer()).thenReturn(mockData.getCredentialIssuers());
        this.credentialRegisterService = new CredentialRegisterService(mockRetriever);
        credentialRegisterService.setResponse();

    }


    @DisplayName("When registering credentials")
    @Test
    void whenReformattingTheData() throws IOException {
        Credentials credentials = credentialRegisterService.getCredentials();
        log.info(credentials.toString());

        assertAll(
                () -> assertEquals("mock_utsteder", credentials.credentials().get(0).credentialIssuer()),
                () -> assertEquals("mdoc", credentials.credentials().get(0).format()),
                () -> assertEquals("sd+jwt-vc", credentials.credentials().get(1).format())
        );
    }
}
