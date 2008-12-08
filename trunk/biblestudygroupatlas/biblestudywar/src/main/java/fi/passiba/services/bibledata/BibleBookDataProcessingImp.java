/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.bibledata;

import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import javax.jms.Destination;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author haverinen
 *  Handlest the sending of JMS message into the specific queue so that the newly created
 * Biblebook data is later processed and stored into corresponding tables.
 *
 */
public class BibleBookDataProcessingImp implements IBibleBookDataProcessing {

    private JmsTemplate jmsTemplate;

     private Destination destination;

    public void setDestination(Destination destination) {
      this.destination = destination;
    }

    /**
     * set jsmTemplate variable
     * @param jmsTemplate
     */
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Handles the sending of new Databook Weburl and send for futher processing
     *
     * @param bookSource- fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource  instance to be installed
     */
    public void sendBibleBookDataForProcessing(final Bookdatasource bookSource) {

        if (bookSource != null  && bookSource.getBookName()!=null)

        {

             jmsTemplate.convertAndSend(destination, bookSource);

        }

    }
}

