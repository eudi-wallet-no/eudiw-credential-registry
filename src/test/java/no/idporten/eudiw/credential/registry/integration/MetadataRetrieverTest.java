package no.idporten.eudiw.credential.registry.integration;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

@DisplayName("metadata retriever testing")
@SpringBootTest
@ActiveProfiles("junit")
public class MetadataRetrieverTest {

    private DummyMetadataRetriever credentialIssuerMetadataRetriever;

    @Autowired
    public MetadataRetrieverTest(DummyMetadataRetriever credentialIssuerMetadataRetriever) {
        this.credentialIssuerMetadataRetriever = credentialIssuerMetadataRetriever;
    }

    @Test
    @DisplayName("that uri is built correctly")
    public void wellKnownUriFormattingTest() {
        URI issuer1 = URI.create("https://utsteder.test.eidas2sandkasse.net");
        URI issuer2 =  URI.create("https://utsteder.test.eidas2sandkasse.net/tenant2");
        URI formattedIssuer1 = credentialIssuerMetadataRetriever.formatWellKnwonOpenidCredentialIssuerUri(issuer1);
        URI formattedIssuer2 = credentialIssuerMetadataRetriever.formatWellKnwonOpenidCredentialIssuerUri(issuer2);

        assertEquals("https://utsteder.test.eidas2sandkasse.net/.well-known/openid-credential-issuer", formattedIssuer1.toString());
        assertEquals("https://utsteder.test.eidas2sandkasse.net/.well-known/openid-credential-issuer/tenant2", formattedIssuer2.toString());
    }
}
