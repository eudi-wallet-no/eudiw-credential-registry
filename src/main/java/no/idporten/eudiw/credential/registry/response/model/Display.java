package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Display {
    private String name;
    private String locale;

    public Display(String name, String locale) {
        setName(name);
        setLocale(locale);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    private void setLocale(String locale) {
        this.locale = locale;
    }
}
