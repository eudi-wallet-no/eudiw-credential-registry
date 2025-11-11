package no.idporten.eudiw.credential.registry.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import no.idporten.eudiw.credential.registry.integration.model.Claims;
import no.idporten.eudiw.credential.registry.integration.model.Display;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputCredentialMetadata {

    private List<OutputDisplay> display;
    private List<OutputClaims> claims;

    public OutputCredentialMetadata(List<Display> inputDisplay, List<Claims>  inputClaims) {
        display = setOutputDisplay(inputDisplay);
        claims = setOutputClaims(inputClaims);

    }

    protected List<OutputDisplay> setOutputDisplay(List<Display> inputDisplayList) {
        List<OutputDisplay> outputDisplayList = new ArrayList<>();
        for(Display display : inputDisplayList) {
            OutputDisplay outputDisplay = new OutputDisplay(display.name(), display.locale());
            outputDisplayList.add(outputDisplay);
        }
        return outputDisplayList;
    }

    public List<OutputDisplay> getDisplay() {
        return display;
    }

    protected List<OutputClaims> setOutputClaims(List<Claims> inputClaimsList) {
        List<OutputClaims> outputClaimsList = new ArrayList<>();
        for(Claims claim : inputClaimsList) {

            List<String> pathListInternal = new ArrayList<>();
            List<OutputDisplay> displayListInternal = new ArrayList<>();

            for(String path : claim.path()) {
                pathListInternal.add(path);
            }

            for(Display display : claim.display())
            {
                OutputDisplay outputDisplay = new OutputDisplay(display.name(), display.locale());
                displayListInternal.add(outputDisplay);
            }
            OutputClaims outputClaims = new OutputClaims(pathListInternal, displayListInternal);
            outputClaimsList.add(outputClaims);
        }
        return outputClaimsList;
    }

    public List<OutputClaims> getClaims() {
        return claims;
    }

}
