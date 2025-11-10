package no.idporten.eudiw.credential.registry.output.model;

import no.idporten.eudiw.credential.registry.integration.model.Claims;
import no.idporten.eudiw.credential.registry.integration.model.Display;

import java.util.ArrayList;
import java.util.List;

public class OutputCredentialMetadata {

    private List<OutputDisplay> outputDisplayList;
    private List<OutputClaims>  outputClaimsList;

    public OutputCredentialMetadata(List<Display> inputDisplay, List<Claims>  inputClaims) {
        outputDisplayList = setOutputDisplay(inputDisplay);
        outputClaimsList = setOutputClaims(inputClaims);

    }

    protected List<OutputDisplay> setOutputDisplay(List<Display> inputDisplayList) {
        List<OutputDisplay> outputDisplayList = new ArrayList<>();
        for(Display display : inputDisplayList) {
            OutputDisplay outputDisplay = new OutputDisplay(display.name(), display.locale());
            outputDisplayList.add(outputDisplay);
        }
        return outputDisplayList;
    }

    public List<OutputDisplay> getOutputDisplayList() {
        return outputDisplayList;
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

    public List<OutputClaims> getOutputClaimsList() {
        return outputClaimsList;
    }

}
