/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.crosswaybible.webservice.client;

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

}
