package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.net.URI;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Display {
    @NotBlank
    @JsonProperty("name")
    private String name;
    @JsonProperty("locale")
    private String locale = "";
    @JsonProperty("logo")
    private Logo logo = new Logo(URI.create(""));
    @JsonProperty("description")
    private String description = "";

    public Display(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLogo(Logo logo){
        this.logo = logo;
    }

    public Logo getLogo() {
        return this.logo;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
