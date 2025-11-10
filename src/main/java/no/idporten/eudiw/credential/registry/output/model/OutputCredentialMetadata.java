package no.idporten.eudiw.credential.registry.output.model;

import no.idporten.eudiw.credential.registry.integration.model.Claims;
import no.idporten.eudiw.credential.registry.integration.model.Display;

import java.util.ArrayList;
import java.util.List;

public class OutputCredentialMetadata {

    private List<OutputDisplay> outputDisplayList;
    private List<OutputClaims>  outputClaimsList;

    public OutputCredentialMetadata(List<Display> inputDisplay, List<Claims>  inputClaims) {
        outputDisplayList = new ArrayList<>();
        outputClaimsList = new ArrayList<>();
        setOutputDisplay(inputDisplay);
        setOutputClaims(inputClaims);
    }

    protected void setOutputDisplay(List<Display> inputDisplayList) {
        for(Display display : inputDisplayList) {
            OutputDisplay outputDisplay = new OutputDisplay(display.name(), display.locale());
            outputDisplayList.add(outputDisplay);
        }
    }

    public List<OutputDisplay> getOutputDisplayList() {
        return outputDisplayList;
    }

    protected void setOutputClaims(List<Claims> inputClaimsList) {
        for(Claims claim : inputClaimsList) {
            List<String> pathList = new ArrayList<>();
            for(String path : claim.path()) {
                pathList.add(path);
            }
            List<OutputDisplay> displayListInternal = new ArrayList<>();
            for(Display display : claim.display())
            {
                OutputDisplay outputDisplay = new OutputDisplay(display.name(), display.locale());
                outputDisplayList.add(outputDisplay);
            }
            OutputClaims outputClaims = new OutputClaims(pathList, displayListInternal);
            outputClaimsList.add(outputClaims);
        }
    }

    public List<OutputClaims> getOutputClaimsList() {
        return outputClaimsList;
    }

}
