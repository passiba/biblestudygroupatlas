/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.bibledata;

import fi.passiba.groups.ui.pages.wizards.biblesession.BibleSession;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

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

             System.out.println("SendingSending: " +bookSource.getBookName());
             jmsTemplate.convertAndSend(destination, bookSource);
            /*jmsTemplate.send(destination,new MessageCreator() {

                public Message createMessage(Session session) throws JMSException {
                  ObjectMessage objectMessage = session.createObjectMessage();
                  objectMessage.setObject(bookSource);
                 // mapMessage.setString("bookInitials", bookSource.getBookInitials());
                  return objectMessage;
                }
            });*/

        }

    }
}

