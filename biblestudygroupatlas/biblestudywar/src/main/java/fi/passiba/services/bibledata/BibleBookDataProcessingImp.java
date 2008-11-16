/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.bibledata;

import fi.passiba.groups.ui.pages.wizards.biblesession.BibleSession;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.crosswire.jsword.book.Book;
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

    /**
     *
     * @return JmsTemplate varialbe
     */
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
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
     * @param book- org.crosswire.jsword.book.Book bible book instance to be installed
     */
    public void sendBibleBookDataForProcessing(final Book book) {

        if (book != null  && book.getInitials()!=null)

        {
            jmsTemplate.send(new MessageCreator() {

                public Message createMessage(Session session) throws JMSException {
                  MapMessage mapMessage = session.createMapMessage();
                  mapMessage.setString("bookInitials", book.getInitials());
                  return mapMessage;
                }
            });

        }

    }
}

