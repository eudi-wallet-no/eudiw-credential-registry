package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialMetadata {

    private static final Logger log = LoggerFactory.getLogger(CredentialMetadata.class);
    @JsonProperty("display")
    private List<Display> display = new ArrayList<>();
    @JsonProperty("claims")
    private List<Claims> claims = new ArrayList<>();

    public CredentialMetadata(){}


    public void setDisplay(List<Display> display) {
        if(display.stream().map(Display::getName).noneMatch(StringUtils::hasText)){
            log.error("All display names are null or empty");
        } else {
            this.display = display.stream()
                    .filter(disp -> StringUtils.hasText(disp.getName()))
                    .toList();
        }
    }

    public List<Display> getDisplay() {
        return this.display;
    }

    public void setClaims(List<Claims> claims){
        this.claims = claims;
    }

    public List<Claims> getClaims() {
        return this.claims;
    }


}


