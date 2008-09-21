/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.biblestudy.crosswaybible.webservice.client;

import fi.passiba.biblestudy.crosswaybible.webservice.client.domain.RequestDetails;
import fi.passiba.biblestudy.crosswaybible.webservice.client.jaxb.request.EvalutateBibleVerseReguest;
import fi.passiba.biblestudy.crosswaybible.webservice.client.jaxb.response.CrosswayBible;
import fi.passiba.biblestudy.crosswaybible.webservice.client.jaxb.response.DoPassageQueryResponse;
import java.io.IOException;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 *
 * @author haverinen
 */
public class BibleVerseClient {

    private WebServiceTemplate webServiceTemplate;
  
    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

   

    public CrosswayBible doPassageQuery(RequestDetails requestDetails)
            throws IOException {


        EvalutateBibleVerseReguest request = new EvalutateBibleVerseReguest();
        request.setKey(requestDetails.getKey());
        request.setPassage(requestDetails.getPassage());
        request.setOptions(requestDetails.getOptions());
        DoPassageQueryResponse response = (DoPassageQueryResponse) webServiceTemplate.marshalSendAndReceive(request);
        if (response != null && response.getReturn() != null && response.getReturn().getCrosswayBible() != null) {
            return response.getReturn().getCrosswayBible();
        }

        return null;


    }
}
