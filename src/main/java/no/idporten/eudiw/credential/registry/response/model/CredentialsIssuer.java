package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialsIssuer {

    private String credential_issuer;
    private String credential_configuration_id;
    private String credential_type;
    private String format;
    private CredentialMetadata credential_metadata;

    public CredentialsIssuer(String credentialIssuer, String credentialConfigurationId, String credentialType, String format, CredentialMetadata credentialMetadata) {
        this.credential_issuer = credentialIssuer;
        this.credential_configuration_id = credentialConfigurationId;
        this.credential_type = credentialType;
        this.format = format;
        this.credential_metadata = credentialMetadata;

    }

    public String getCredential_issuer() {
        return credential_issuer;
    }
    public String getCredential_configuration_id() {
        return credential_configuration_id;
    }
    public String getCredential_type() {
        return credential_type;
    }
    public String getFormat() {
        return format;
    }

    public CredentialMetadata getCredential_metadata() {
        return credential_metadata;
    }
}
