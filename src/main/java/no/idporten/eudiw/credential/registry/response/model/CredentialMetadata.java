package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialMetadata {

    private List<Display> display;
    private List<Claims> claims;

    public CredentialMetadata(List<no.idporten.eudiw.credential.registry.integration.model.Display> inputDisplay, List<no.idporten.eudiw.credential.registry.integration.model.Claims>  inputClaims) {
        display = setOutputDisplay(inputDisplay);
        claims = setOutputClaims(inputClaims);

    }

    protected List<Display> setOutputDisplay(List<no.idporten.eudiw.credential.registry.integration.model.Display> inputDisplayList) {
        List<Display> outputDisplayList = new ArrayList<>();
        for(no.idporten.eudiw.credential.registry.integration.model.Display display : inputDisplayList) {
            Display outputDisplay = new Display(display.name(), display.locale());
            outputDisplayList.add(outputDisplay);
        }
        return outputDisplayList;
    }

    public List<Display> getDisplay() {
        return display;
    }

    protected List<Claims> setOutputClaims(List<no.idporten.eudiw.credential.registry.integration.model.Claims> inputClaimsList) {
        List<Claims> outputClaimsList = new ArrayList<>();
        for(no.idporten.eudiw.credential.registry.integration.model.Claims claim : inputClaimsList) {

            List<String> pathListInternal = new ArrayList<>();
            List<Display> displayListInternal = new ArrayList<>();

            for(String path : claim.path()) {
                pathListInternal.add(path);
            }

            for(no.idporten.eudiw.credential.registry.integration.model.Display display : claim.display())
            {
                Display outputDisplay = new Display(display.name(), display.locale());
                displayListInternal.add(outputDisplay);
            }
            Claims outputClaims = new Claims(pathListInternal, displayListInternal);
            outputClaimsList.add(outputClaims);
        }
        return outputClaimsList;
    }

    public List<Claims> getClaims() {
        return claims;
    }

}
