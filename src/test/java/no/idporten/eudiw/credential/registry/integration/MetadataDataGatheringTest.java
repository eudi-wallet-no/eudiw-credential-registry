package no.idporten.eudiw.credential.registry.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@DisplayName("When collecting metadata")
@SpringBootTest
@TestPropertySource(properties = {"credential-register-properties.uri=https://utsteder.test.eidas2sandkasse.net/.well-known/openid-credential-issuer,https://utsteder.eidas2sandkasse.dev/.well-known/openid-credential-issuer"})
@ActiveProfiles
public class MetadataDataGatheringTest {


    @MockitoSpyBean
    private CredentialIssuerMetadataRetriever metadataDataGathering;


    private final ObjectMapper objectMapper;

    @Autowired
    public MetadataDataGatheringTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setup() {
        metadataDataGathering.getMapOfIssuers().clear();
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

          CredentialIssuer credentialIssuer = objectMapper.readValue(metadata, CredentialIssuer.class);
          HashMap<String, CredentialIssuer> map = new HashMap<>();
          map.put(credentialIssuer.credentialIssuer(), credentialIssuer);
          when(metadataDataGathering.getMapOfIssuers()).thenReturn(map);

          assertAll(
                  () -> assertEquals(1, metadataDataGathering.getMapOfIssuers().size()),
                  () -> assertTrue(metadataDataGathering.getMapOfIssuers().containsKey("https://utsteder.test.eidas2sandkasse.net")),
                  () -> assertFalse(metadataDataGathering.getMapOfIssuers().containsKey("https://utsteder.eidas2sandkasse.dev"))
          );



    }

    @DisplayName("When having more then one issuer")
    @Test
    public void testHavingMoreThenOneIssuer() throws JsonProcessingException {
        metadataDataGathering.loopThroughAllIssuersAndStartFlow();
        Map<String, CredentialIssuer> list = metadataDataGathering.getMapOfIssuers();
        assertTrue(metadataDataGathering.getMapOfIssuers().containsKey("https://utsteder.eidas2sandkasse.dev"));
        assertTrue(metadataDataGathering.getMapOfIssuers().containsKey("https://utsteder.eidas2sandkasse.dev"));
        assertEquals(2, list.size());
    }

    @DisplayName("That credential configuration supported is saved correctly in the list")
    @Test
    void testIfCredentialConfigurationsSupportedIsSavedProperly() throws JsonProcessingException {

        String metadata = """
          {"credential_issuer":"https://utsteder.test.eidas2sandkasse.net","authorization_servers":["https://auth.test.eidas2sandkasse.net"],"credential_endpoint":"https://utsteder.test.eidas2sandkasse.net/openid4vci/credential","nonce_endpoint":"https://utsteder.test.eidas2sandkasse.net/openid4vci/nonce","notification_endpoint":"https://utsteder.test.eidas2sandkasse.net/openid4vci/notification","credential_configurations_supported":{"no.minid.mpid_sd_jwt_vc":{"vct":"no:minid:mpid:1","scope":"eudiw:no:minid:mpid","format":"dc+sd-jwt","cryptographic_binding_methods_supported":["jwk"],"credential_signing_alg_values_supported":["ES256"],"display":[{"name":"MinID PID","locale":"no"}],"claims":[{"path":["personal_administrative_number"],"mandatory":true,"display":[{"name":"Norsk identitetsnummer","locale":"no"}]},{"path":["family_name"],"mandatory":true,"display":[{"name":"Etternavn","locale":"no"}]},{"path":["given_name"],"mandatory":true,"display":[{"name":"Fornavn","locale":"no"}]},{"path":["middle_name"],"mandatory":false,"display":[{"name":"Mellomnavn","locale":"no"}]},{"path":["birth_date"],"mandatory":true,"display":[{"name":"Fødselsdato","locale":"no"}]},{"path":["expiry_date"],"mandatory":true,"display":[{"name":"Gyldig til","locale":"no"}]},{"path":["issuance_date"],"mandatory":true,"display":[{"name":"Ustedt","locale":"no"}]},{"path":["iat"],"mandatory":true,"display":[{"name":"Utstedt","locale":"no"},{"name":"Issued","locale":"en"}]},{"path":["exp"],"mandatory":true,"display":[{"name":"Gyldig til","locale":"no"},{"name":"Valid until","locale":"en"}]}],"credential_metadata":{"display":[{"name":"MinID PID","locale":"no"}],"claims":[{"path":["personal_administrative_number"],"mandatory":true,"display":[{"name":"Norsk identitetsnummer","locale":"no"}]},{"path":["family_name"],"mandatory":true,"display":[{"name":"Etternavn","locale":"no"}]},{"path":["given_name"],"mandatory":true,"display":[{"name":"Fornavn","locale":"no"}]},{"path":["middle_name"],"mandatory":false,"display":[{"name":"Mellomnavn","locale":"no"}]},{"path":["birth_date"],"mandatory":true,"display":[{"name":"Fødselsdato","locale":"no"}]},{"path":["expiry_date"],"mandatory":true,"display":[{"name":"Gyldig til","locale":"no"}]},{"path":["issuance_date"],"mandatory":true,"display":[{"name":"Ustedt","locale":"no"}]},{"path":["iat"],"mandatory":true,"display":[{"name":"Utstedt","locale":"no"},{"name":"Issued","locale":"en"}]},{"path":["exp"],"mandatory":true,"display":[{"name":"Gyldig til","locale":"no"},{"name":"Valid until","locale":"en"}]}]},"proof_types_supported":{"jwt":{"proof_signing_alg_values_supported":["ES256"]}}},"no.kontaktregisteret.kontaktinformasjon_mso_mdoc":{"doctype":"no:kontaktregisteret:kontaktinformasjon:1","scope":"eudiw:no:kontaktregisteret:kontaktinformasjon","format":"mso_mdoc","cryptographic_binding_methods_supported":["jwk"],"credential_signing_alg_values_supported":["ES256"],"display":[{"name":"Digital kontaktinformasjon","locale":"no"}],"claims":[{"path":["no:kontaktregisteret:kontaktinformasjon:1","personidentifikator"],"mandatory":true,"display":[{"name":"Personidentifikator","locale":"no"}]},{"path":["no:kontaktregisteret:kontaktinformasjon:1","epostadresse"],"mandatory":true,"display":[{"name":"Epost","locale":"no"}]},{"path":["no:kontaktregisteret:kontaktinformasjon:1","mobiltelefonnummer"],"mandatory":true,"display":[{"name":"Telefonnummer","locale":"no"}]}],"credential_metadata":{"display":[{"name":"Digital kontaktinformasjon","locale":"no"}],"claims":[{"path":["no:kontaktregisteret:kontaktinformasjon:1","personidentifikator"],"mandatory":true,"display":[{"name":"Personidentifikator","locale":"no"}]},{"path":["no:kontaktregisteret:kontaktinformasjon:1","epostadresse"],"mandatory":true,"display":[{"name":"Epost","locale":"no"}]},{"path":["no:kontaktregisteret:kontaktinformasjon:1","mobiltelefonnummer"],"mandatory":true,"display":[{"name":"Telefonnummer","locale":"no"}]}]},"proof_types_supported":{"jwt":{"proof_signing_alg_values_supported":["ES256"]}}},"no.skatteetaten.nnid_mso_mdoc":{"doctype":"no.skatteetaten.nnid.1","scope":"eudiw:no:skatteetaten:nnid","format":"mso_mdoc","cryptographic_binding_methods_supported":["jwk"],"credential_signing_alg_values_supported":["ES256"],"display":[{"name":"Norsk identitetsnummer","locale":"no"},{"name":"Norwegian identification number","locale":"en"}],"claims":[{"path":["no.skatteetaten.nnid.1","norwegian_national_id_number"],"mandatory":true,"display":[{"name":"Norsk identitetsnummer","locale":"no"},{"name":"Norwegian identification number","locale":"en"}]},{"path":["no.skatteetaten.nnid.1","norwegian_national_id_number_type"],"mandatory":true,"display":[{"name":"Type norsk identitetsnummer","locale":"no"},{"name":"Type of Norwegian identification number","locale":"en"}]}],"credential_metadata":{"display":[{"name":"Norsk identitetsnummer","locale":"no"},{"name":"Norwegian identification number","locale":"en"}],"claims":[{"path":["no.skatteetaten.nnid.1","norwegian_national_id_number"],"mandatory":true,"display":[{"name":"Norsk identitetsnummer","locale":"no"},{"name":"Norwegian identification number","locale":"en"}]},{"path":["no.skatteetaten.nnid.1","norwegian_national_id_number_type"],"mandatory":true,"display":[{"name":"Type norsk identitetsnummer","locale":"no"},{"name":"Type of Norwegian identification number","locale":"en"}]}]},"proof_types_supported":{"jwt":{"proof_signing_alg_values_supported":["ES256"]}}},"no.advokattilsynet.advokatregisteret_mso_mdoc":{"doctype":"no.advokattilsynet.advokatregisteret.1","scope":"eudiw:no:advokattilsynet:advokatregisteret","format":"mso_mdoc","cryptographic_binding_methods_supported":["jwk"],"credential_signing_alg_values_supported":["ES256"],"display":[{"name":"Advokatbevilling","locale":"no"}],"claims":[{"path":["no.advokattilsynet.advokatregisteret.1","personidentifikator"],"mandatory":true,"display":[{"name":"Personidentifikator","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","tittel"],"mandatory":true,"display":[{"name":"Tittel","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","mellomnavn"],"mandatory":false,"display":[{"name":"Mellomnavn","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","etternavn"],"mandatory":true,"display":[{"name":"Etternavn","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","fornavn"],"mandatory":true,"display":[{"name":"Fornavn","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","regnr"],"mandatory":true,"display":[{"name":"Regnr","locale":"no"}]}],"credential_metadata":{"display":[{"name":"Advokatbevilling","locale":"no"}],"claims":[{"path":["no.advokattilsynet.advokatregisteret.1","personidentifikator"],"mandatory":true,"display":[{"name":"Personidentifikator","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","tittel"],"mandatory":true,"display":[{"name":"Tittel","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","mellomnavn"],"mandatory":false,"display":[{"name":"Mellomnavn","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","etternavn"],"mandatory":true,"display":[{"name":"Etternavn","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","fornavn"],"mandatory":true,"display":[{"name":"Fornavn","locale":"no"}]},{"path":["no.advokattilsynet.advokatregisteret.1","regnr"],"mandatory":true,"display":[{"name":"Regnr","locale":"no"}]}]},"proof_types_supported":{"jwt":{"proof_signing_alg_values_supported":["ES256"]}}},"no.digdir.eudiw.pid_mso_mdoc":{"doctype":"eu.europa.ec.eudi.pid.1","scope":"eudiw:no:pid","format":"mso_mdoc","cryptographic_binding_methods_supported":["jwk"],"credential_signing_alg_values_supported":["ES256"],"display":[{"name":"Norsk PID","locale":"no"}],"claims":[{"path":["eu.europa.ec.eudi.pid.1","personal_administrative_number"],"mandatory":true,"display":[{"name":"Fødselsnummer","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","given_name"],"mandatory":true,"display":[{"name":"Førenamn","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","family_name"],"mandatory":true,"display":[{"name":"Etternamn","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","birth_date"],"mandatory":true,"display":[{"name":"Fødselsdato","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","birth_place","country"],"mandatory":true,"display":[{"name":"Fødeland","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","nationality"],"mandatory":true,"display":[{"name":"Nasjonalitet","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","expiry_date"],"mandatory":true,"display":[{"name":"Gyldig til dato","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","issuing_authority"],"mandatory":true,"display":[{"name":"Utsteda av","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","issuing_country"],"mandatory":true,"display":[{"name":"Utsteda i land","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","age_over_18"],"mandatory":false,"display":[{"name":"Over 18","locale":"no"}]}],"credential_metadata":{"display":[{"name":"Norsk PID","locale":"no"}],"claims":[{"path":["eu.europa.ec.eudi.pid.1","personal_administrative_number"],"mandatory":true,"display":[{"name":"Fødselsnummer","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","given_name"],"mandatory":true,"display":[{"name":"Førenamn","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","family_name"],"mandatory":true,"display":[{"name":"Etternamn","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","birth_date"],"mandatory":true,"display":[{"name":"Fødselsdato","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","birth_place","country"],"mandatory":true,"display":[{"name":"Fødeland","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","nationality"],"mandatory":true,"display":[{"name":"Nasjonalitet","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","expiry_date"],"mandatory":true,"display":[{"name":"Gyldig til dato","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","issuing_authority"],"mandatory":true,"display":[{"name":"Utsteda av","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","issuing_country"],"mandatory":true,"display":[{"name":"Utsteda i land","locale":"no"}]},{"path":["eu.europa.ec.eudi.pid.1","age_over_18"],"mandatory":false,"display":[{"name":"Over 18","locale":"no"}]}]},"proof_types_supported":{"jwt":{"proof_signing_alg_values_supported":["ES256"]}}},"no.kontaktregisteret.kontaktinformasjon_sd_jwt_vc":{"vct":"no:kontaktregisteret:kontaktinformasjon:1","scope":"eudiw:no:kontaktregisteret:kontaktinformasjon","format":"dc+sd-jwt","cryptographic_binding_methods_supported":["jwk"],"credential_signing_alg_values_supported":["ES256"],"display":[{"name":"Digital kontaktinformasjon","locale":"no"}],"claims":[{"path":["personidentifikator"],"mandatory":true,"display":[{"name":"Personidentifikator","locale":"no"}]},{"path":["epostadresse"],"mandatory":true,"display":[{"name":"Epost","locale":"no"}]},{"path":["mobiltelefonnummer"],"mandatory":true,"display":[{"name":"Telefonnummer","locale":"no"}]},{"path":["iat"],"mandatory":true,"display":[{"name":"Utstedt","locale":"no"},{"name":"Issued","locale":"en"}]},{"path":["exp"],"mandatory":true,"display":[{"name":"Gyldig til","locale":"no"},{"name":"Valid until","locale":"en"}]}],"credential_metadata":{"display":[{"name":"Digital kontaktinformasjon","locale":"no"}],"claims":[{"path":["personidentifikator"],"mandatory":true,"display":[{"name":"Personidentifikator","locale":"no"}]},{"path":["epostadresse"],"mandatory":true,"display":[{"name":"Epost","locale":"no"}]},{"path":["mobiltelefonnummer"],"mandatory":true,"display":[{"name":"Telefonnummer","locale":"no"}]},{"path":["iat"],"mandatory":true,"display":[{"name":"Utstedt","locale":"no"},{"name":"Issued","locale":"en"}]},{"path":["exp"],"mandatory":true,"display":[{"name":"Gyldig til","locale":"no"},{"name":"Valid until","locale":"en"}]}]},"proof_types_supported":{"jwt":{"proof_signing_alg_values_supported":["ES256"]}}}},"display":[{"name":"Digitaliseringsdirektoratet","locale":"no"}]}
          """;
        CredentialIssuer credentialIssuer = objectMapper.readValue(metadata, CredentialIssuer.class);
        HashMap<String, CredentialIssuer> map = new HashMap<>();
        map.put(credentialIssuer.credentialIssuer(), credentialIssuer);
        when(metadataDataGathering.getMapOfIssuers()).thenReturn(map);

        assertEquals("Digital kontaktinformasjon", metadataDataGathering.getMapOfIssuers().get("https://utsteder.test.eidas2sandkasse.net").credentialConfiguration().get("no.kontaktregisteret.kontaktinformasjon_mso_mdoc").credentialMetadata().display().get(0).name());
    }

}
