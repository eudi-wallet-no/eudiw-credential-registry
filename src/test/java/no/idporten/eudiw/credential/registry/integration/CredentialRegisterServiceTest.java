package no.idporten.eudiw.credential.registry.integration;


import no.idporten.eudiw.credential.registry.response.model.Credentials;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("credentials testing")
@SpringBootTest
@ActiveProfiles("junit")
public class CredentialRegisterServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CredentialRegisterServiceTest.class);

    private no.idporten.eudiw.credential.registry.response.CredentialRegisterService credentialRegisterService;

    private DummyMetadataRetriever mockRetriever;


    @Autowired
    public CredentialRegisterServiceTest(DummyMetadataRetriever credentialIssuerMetadataRetriever) {
        this.mockRetriever = credentialIssuerMetadataRetriever;
    }


    @DisplayName("When registering credentials")
    @Test
    void whenReformattingTheData() {
        this.credentialRegisterService = new no.idporten.eudiw.credential.registry.response.CredentialRegisterService(mockRetriever);
        credentialRegisterService.setResponse();
        Credentials credentials = credentialRegisterService.getCredentials();

        assertAll(
                () -> assertEquals("mock_utsteder", credentials.credentials().get(0).credentialIssuer()),
                () -> assertEquals("sd+jwt-vc", credentials.credentials().get(0).format()),
                () -> assertEquals("mdoc", credentials.credentials().get(1).format()),
                () -> assertEquals("jwt_vc_json", credentials.credentials().get(2).format())
        );


    }
}
