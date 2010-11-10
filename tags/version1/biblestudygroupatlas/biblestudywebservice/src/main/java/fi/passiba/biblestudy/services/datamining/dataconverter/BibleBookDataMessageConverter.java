/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining.dataconverter;

import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 *
 *@author haverinen
 * Converts JMS MapMessages into Java Objects to be stored in DB
 */
public class BibleBookDataMessageConverter implements MessageConverter{

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {

     System.out.println("object converted to message");
         if (!(object instanceof Bookdatasource)) {
         throw new MessageConversionException("Object isn't a Datasource");
      }
      Bookdatasource datasource = (Bookdatasource) object;
      ObjectMessage message = session.createObjectMessage(datasource);
      return message;
    }

    public Object fromMessage(Message dataMessage) throws JMSException, MessageConversionException {

        System.out.println("converting object");
        if(dataMessage!=null && !( dataMessage instanceof ObjectMessage))
        {
            throw new MessageConversionException("Given message was not ObjectMessage");
 	    }
        ObjectMessage objectMessage=(ObjectMessage)dataMessage;
       // Bookdatasource bookSource= new Bookdatasource();
       // objectMessage.getObject();
       /* String bookInitials=mapMessage.getString("bookInitials");
        Book book=Books.installed().getBook(bookInitials);

        if(!BookCategory.BIBLE.equals(book.getBookCategory()))
        {
            throw new MessageConversionException("Given book is not a bible");
        }*/
        Bookdatasource bookSource=(Bookdatasource)objectMessage.getObject();
        return bookSource;

    }





}
