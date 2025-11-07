package no.idporten.eudiw.credential.registry.output.model;

public record OutputCredentialsIssuer(
        String credentialIssuer,
        String credentialConfigurationId,
        String credentialType,
        String format,
        OutputCredentialMetadata credentialMetadata
) {
}
