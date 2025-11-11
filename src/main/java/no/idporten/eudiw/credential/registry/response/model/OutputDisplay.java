package no.idporten.eudiw.credential.registry.response.model;

public class OutputDisplay {
    private String name;
    private String locale;

    public OutputDisplay(String name, String locale) {
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
