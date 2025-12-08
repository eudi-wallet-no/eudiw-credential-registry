package no.idporten.eudiw.credential.registry.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchCredentialIssuance {

    @JsonProperty("batch_size")
    private Integer batchSize = -1;

    public BatchCredentialIssuance() {
    }

    public BatchCredentialIssuance(Integer batchSize) {
        setBatchSize(batchSize);
    }

    public Integer getBatchSize() {
        return batchSize;
    }
    public void setBatchSize(Integer batchSize) {
        if (batchSize >=2 && batchSize < 100) {
            this.batchSize = batchSize;
        } else {
            this.batchSize = -1;
        }
    }
}
