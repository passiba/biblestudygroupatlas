/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.biblestudy.crosswaybible.webservice.client;

import fi.passiba.biblestudy.crosswaybible.webservice.client.domain.RequestDetails;
import fi.passiba.biblestudy.crosswaybible.webservice.client.request.doPassageQueryRequest;
import fi.passiba.biblestudy.crosswaybible.webservice.client.response.CrosswayBible;
import fi.passiba.biblestudy.crosswaybible.webservice.client.response.DoPassageQueryResponse;
import java.io.IOException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 *
 * @author haverinen
 */
public class BibleVerseClient  extends WebServiceGatewaySupport{

    

   

    public CrosswayBible doPassageQuery(RequestDetails requestDetails)
            throws IOException {


        doPassageQueryRequest request = new doPassageQueryRequest();
        request.setKey(requestDetails.getKey());
        request.setPassage(requestDetails.getPassage());
        request.setOptions(requestDetails.getOptions());
        DoPassageQueryResponse response = (DoPassageQueryResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        if (response != null && response.getReturn() != null && response.getReturn().getCrosswayBible() != null) {
            return response.getReturn().getCrosswayBible();
        }

        return null;


    }
}
