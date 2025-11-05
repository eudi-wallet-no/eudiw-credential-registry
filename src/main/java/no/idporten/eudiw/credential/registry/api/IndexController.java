package no.idporten.eudiw.credential.registry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.idporten.eudiw.credential.registry.integration.*;
import no.idporten.eudiw.credential.registry.integration.model.CredentialConfigurationsSupported;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private MetadataDataGathering metadataDataGathering;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                
                <head>
                  <meta charset="utf-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>Bevisregister frå Digdir</title>
                </head>
                <body>
                  <h1>Bevisregister frå Digdir</h1>
                  <p>Bevisregister er ein del av <a href="https://docs.digdir.no/docs/lommebok/lommebok_om.html">Nasjonal sandkasse for digital lommebok</a>.</p>
                </body>
                </html>
                """;

    }

    @GetMapping(value = "/metadata", produces = "application/json")
    @ResponseBody
    public List<CredentialConfigurationsSupported> get() throws JsonProcessingException {
        metadataDataGathering.loopThroughAllIssuersAndStartFlow();
        return metadataDataGathering.getHashMap().get("https://utsteder.test.eidas2sandkasse.net");
    }
}
