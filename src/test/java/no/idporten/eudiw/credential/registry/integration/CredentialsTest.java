package no.idporten.eudiw.credential.registry.integration;


import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;
import no.idporten.eudiw.credential.registry.response.CredentialRegisterService;
import no.idporten.eudiw.credential.registry.response.model.Credentials;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.net.URI;

@DisplayName("credentials testing")
@SpringBootTest
@ActiveProfiles("junit")
public class CredentialsTest {

    @MockitoBean
    private CredentialRegisterService credentialRegisterService;

    private DummyMetadataRetriever mockRetriever;


    @Autowired
    public CredentialsTest(DummyMetadataRetriever credentialIssuerMetadataRetriever) {
        this.mockRetriever = credentialIssuerMetadataRetriever;
    }


    @DisplayName("When registering credentials")
    @Test
    void whenReformattingTheData() {
        this.credentialRegisterService = new CredentialRegisterService(mockRetriever);
        credentialRegisterService.setResponse();
        Credentials credentials = credentialRegisterService.getCredentials();

        assertAll(
                () -> assertEquals("mock_utsteder", credentials.credentials().get(0).credentialIssuer()),
                () -> assertEquals("mdoc", credentials.credentials().get(0).format()),
                () -> assertEquals("sd+jwt-vc", credentials.credentials().get(1).format())
        );
    }

    @DisplayName("When issuer URL is not https")
    @Test
    void whenIssuerUrlIsNotHttps() {
        this.credentialRegisterService = new CredentialRegisterService(mockRetriever);
        URI uri = URI.create("http://localhost:8080/");
        CredentialIssuer issuer = mockRetriever.fetchCredentialIssuerFromMetadataRequest(uri);

        assertNull(issuer);
    }

    @DisplayName("When issuer uri is empty")
    @Test
    void whenIssuerUriIsEmpty() {
        this.credentialRegisterService = new CredentialRegisterService(mockRetriever);
        URI uri = URI.create("");
        CredentialIssuer issuer = mockRetriever.fetchCredentialIssuerFromMetadataRequest(uri);
        assertNull(issuer);
    }
}
