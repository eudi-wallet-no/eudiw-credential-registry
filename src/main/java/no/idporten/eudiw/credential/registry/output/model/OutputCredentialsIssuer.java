package no.idporten.eudiw.credential.registry.output.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputCredentialsIssuer {

    private String credentialIssuer;
    private String credentialConfigurationId;
    private String credentialType;
    private String format;
    private OutputCredentialMetadata credentialMetadata;

    public OutputCredentialsIssuer(String credentialIssuer, String credentialConfigurationId, String credentialType, String format, OutputCredentialMetadata credentialMetadata) {
        this.credentialIssuer = credentialIssuer;
        this.credentialConfigurationId = credentialConfigurationId;
        this.credentialType = credentialType;
        this.format = format;
        this.credentialMetadata = credentialMetadata;

    }

    public String getCredentialIssuerString() {
        return credentialIssuer;
    }
    public String getCredentialConfigurationId() {
        return credentialConfigurationId;
    }
    public String getCredentialType() {
        return credentialType;
    }
    public String getFormat() {
        return format;
    }

    public OutputCredentialMetadata getCredentialMetadata() {
        return credentialMetadata;
    }



}
