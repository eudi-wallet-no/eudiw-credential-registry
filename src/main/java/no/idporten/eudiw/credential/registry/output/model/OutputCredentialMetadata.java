package no.idporten.eudiw.credential.registry.output.model;

public record OutputCredentialMetadata(
        OutputDisplay[] display,
        OutputClaims[]  claims
) {
}
