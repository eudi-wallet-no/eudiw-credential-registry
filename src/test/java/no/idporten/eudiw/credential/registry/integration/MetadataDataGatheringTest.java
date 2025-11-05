package no.idporten.eudiw.credential.registry.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.idporten.eudiw.credential.registry.integration.model.CredentialConfigurationsSupported;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;


import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("When collecting metadata")
@SpringBootTest
@TestPropertySource(properties = {"credential-register-properties.uri=https://utsteder.test.eidas2sandkasse.net https://utsteder.eidas2sandkasse.dev"})
public class MetadataDataGatheringTest {

    private static final Logger log = LoggerFactory.getLogger(MetadataDataGatheringTest.class);
    @MockitoSpyBean
    private MetadataDataGathering metadataDataGathering;

    @BeforeEach
    public void setup() {
        metadataDataGathering.clearHashMap();
    }


    @DisplayName("When setting metadata")
    @Test
    public void testSetMetadata() throws JsonProcessingException {

          String metadata = """
          {
          "credential_issuer": "https://utsteder.test.eidas2sandkasse.net",
          "authorization_servers": [
            "https://auth.test.eidas2sandkasse.net"
          ],
          "credential_endpoint": "https://utsteder.test.eidas2sandkasse.net/openid4vci/credential",
          "nonce_endpoint": "https://utsteder.test.eidas2sandkasse.net/openid4vci/nonce",
          "notification_endpoint": "https://utsteder.test.eidas2sandkasse.net/openid4vci/notification",
          "credential_configurations_supported": {
            "no.minid.mpid_sd_jwt_vc": {
              "vct": "no:minid:mpid:1",
              "scope": "eudiw:no:minid:mpid",
              "format": "dc+sd-jwt",
              "cryptographic_binding_methods_supported": [
                "jwk"
              ],
              "credential_signing_alg_values_supported": [
                "ES256"
              ],
              "display": [
                {
                  "name": "MinID PID",
                  "locale": "no"
                }
              ],
              "claims": [
                {
                  "path": [
                    "personal_administrative_number"
                  ],
                  "mandatory": true,
                  "display": [
                    {
                      "name": "Norsk identitetsnummer",
                      "locale": "no"
                    }
                  ]
                }
               ]
              }
            }
          }
          """;
          metadataDataGathering.setMetadata(metadata);

          assertAll(
                  () -> assertEquals(1, metadataDataGathering.getHashMap().size()),
                  () -> assertTrue(metadataDataGathering.getHashMap().containsKey("https://utsteder.test.eidas2sandkasse.net")),
                  () -> assertFalse(metadataDataGathering.getHashMap().containsKey("https://utsteder.eidas2sandkasse.dev"))
          );



    }

    @DisplayName("When having more then one issuer")
    @Test
    public void testHavingMoreThenOneIssuer() throws JsonProcessingException {
        metadataDataGathering.loopThroughAllIssuersAndStartFlow();
        HashMap<String, ArrayList<CredentialConfigurationsSupported>> list = metadataDataGathering.getHashMap();
        assertTrue(metadataDataGathering.getHashMap().containsKey("https://utsteder.eidas2sandkasse.dev"));
        assertTrue(metadataDataGathering.getHashMap().containsKey("https://utsteder.eidas2sandkasse.dev"));
        assertEquals(2, list.size());




    }

    @DisplayName("That credential configuration supported is saved correctly in the list")
    @Test
    void testIfCredentialConfigurationsSupportedIsSavedProperly() throws JsonProcessingException {

        String metadata = """
          {
          "credential_issuer": "https://utsteder.test.eidas2sandkasse.net",
          "authorization_servers": [
            "https://auth.test.eidas2sandkasse.net"
          ],
          "credential_endpoint": "https://utsteder.test.eidas2sandkasse.net/openid4vci/credential",
          "nonce_endpoint": "https://utsteder.test.eidas2sandkasse.net/openid4vci/nonce",
          "notification_endpoint": "https://utsteder.test.eidas2sandkasse.net/openid4vci/notification",
          "credential_configurations_supported": {
            "no.minid.mpid_sd_jwt_vc": {
              "vct": "no:minid:mpid:1",
              "scope": "eudiw:no:minid:mpid",
              "format": "dc+sd-jwt",
              "cryptographic_binding_methods_supported": [
                "jwk"
              ],
              "credential_signing_alg_values_supported": [
                "ES256"
              ],
              "display": [
                {
                  "name": "MinID PID",
                  "locale": "no"
                }
              ],
              "claims": [
                {
                  "path": [
                    "personal_administrative_number"
                  ],
                  "mandatory": true,
                  "display": [
                    {
                      "name": "Norsk identitetsnummer",
                      "locale": "no"
                    }
                  ]
                }
               ]
              }
            }
          }
          """;
        metadataDataGathering.setMetadata(metadata);

        ArrayList<CredentialConfigurationsSupported> list = metadataDataGathering.getHashMap()
                .get("https://utsteder.test.eidas2sandkasse.net");


        assertTrue(!list.isEmpty());
        assertEquals(1, list.size());
        assertEquals("personal_administrative_number", list.get(0).claims().get(0).path().get(0));
    }

}
