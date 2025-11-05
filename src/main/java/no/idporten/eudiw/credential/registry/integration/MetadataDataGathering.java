package no.idporten.eudiw.credential.registry.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.idporten.eudiw.credential.registry.configuration.ConfigProperties;
import no.idporten.eudiw.credential.registry.integration.model.CredentialConfigurationsSupported;
import no.idporten.eudiw.credential.registry.integration.model.CredentialIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ansvarlig for å sende get-forespørsel til well known open id credential issuer til alle issuere som er registrert
 * i application.yaml. Lagrer data i en HashMap, der nøkkel er issuer og verdien er en ArrayList med de forskjellige
 * credential sonfigurations supported-feltene, som har hvert sitt objekt i arraylisten.
 *
 */

@Service
public class MetadataDataGathering {
    @Autowired
    private ConfigProperties configProperties;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private HashMap<String, ArrayList<CredentialConfigurationsSupported>> mapOfEntries;

    public MetadataDataGathering(){
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.mapOfEntries = new HashMap<>();
    }

    /**
     * Setter en Streng med all json-dataen som hentes basert på get-forespørsel sendt til url-en i konstruktøren.
     * Vi legger på .well-known/openid-credential-issuer.
     *
     * @param url DINUTSTEDER.DOMENE
     */
    public String getMethod(URI url) {
        url = URI.create(url + "/.well-known/openid-credential-issuer");
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * Setter metadata basert på strengen i konstruktøren. Dataen parses til json her.
     *
     * @param json Hele metadataen som en streng.
     * @throws JsonProcessingException
     */
    public void setMetadata(String json) throws JsonProcessingException {
        JsonNode metadataStructure = objectMapper.readTree(json);
        CredentialIssuer credentialIssuer = objectMapper.readValue(json, CredentialIssuer.class);
        setCredentialConfigurations(metadataStructure, credentialIssuer);
    }

    /**
     * Tar den fulle json strukturen inn. Henter ut biten med credential_configurations_supported. For hver credential
     * configuration supported, så oppretter den et objekt basert på dataene i json strukturen. Dette objektet legges
     * på lista over objekter tilhørende sin credential configuration supported. Til slutt settes lista med credential
     * configurations supported sammen med credential issuer i et HasmHap, der issuer er nøkkelen.
     *
     * @param data En Json node med hele json strukturen i et well-known/openid endepunkt.
     * @param credentialIssuer utstederen som vi leser av metadata fra.
     */
    public void setCredentialConfigurations(JsonNode data, CredentialIssuer credentialIssuer)  {
        JsonNode node = data.get("credential_configurations_supported");
        ArrayList<CredentialConfigurationsSupported> listOfCredentialConfigurationsSupported = new ArrayList<>();
        if (node != null && node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                CredentialConfigurationsSupported credentialConfigurationsObject = objectMapper.convertValue(entry.getValue(), CredentialConfigurationsSupported.class);
                listOfCredentialConfigurationsSupported.add(credentialConfigurationsObject);
            }
            );
            updateHashMap(credentialIssuer, listOfCredentialConfigurationsSupported);
        }
    }

    public void updateHashMap(CredentialIssuer credentialIssuer, ArrayList<CredentialConfigurationsSupported> listOfCredentialConfigurationsSupported) {
        mapOfEntries.put(credentialIssuer.credentialIssuer(), listOfCredentialConfigurationsSupported);
    }

    public void clearHashMap() {
        mapOfEntries.clear();
    }

    public HashMap<String, ArrayList<CredentialConfigurationsSupported>> getHashMap() {
        return  mapOfEntries;
    }
    /**
     * Dersom det eksisterer flere utstedere i application.yaml fila, vil det leses metadata fra respektive endepunkt.
     *
     * @throws JsonProcessingException
     */

    public void loopThroughAllIssuersAndStartFlow() throws JsonProcessingException {
        String uri = configProperties.uri().toString();
        String[] listOfIssuers = uri.split("%20");
        for (String issuer : listOfIssuers) {
            String content = getMethod(URI.create(issuer));
            setMetadata(content);
        }
    }
}
