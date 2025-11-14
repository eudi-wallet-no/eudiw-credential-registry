package no.idporten.eudiw.credential.registry.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Beviskatalog-API").version("1.0").description("API for å hente alle bevisene som eksisterer i sandkasse-miljøet"));
    }
}