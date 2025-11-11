package no.idporten.eudiw.credential.registry.response;


import no.idporten.eudiw.credential.registry.integration.CredentialIssuerMetadataRetriever;
import no.idporten.eudiw.credential.registry.response.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CredentialRegisterService {

    private CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever;
    private Credentials credentials;

    @Autowired
    public CredentialRegisterService(CredentialIssuerMetadataRetriever credentialIssuerMetadataRetriever) {
        this.credentialIssuerMetadataRetriever = credentialIssuerMetadataRetriever;
        this.credentials = setResponse();
    }

    public Credentials setResponse() {
        return new Credentials(credentialIssuerMetadataRetriever.getListOfIssuer());
    }

    public Credentials getCredentials() {
        return credentials;
    }


}
