/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining.dataconverter;

import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookCategory;
import org.crosswire.jsword.book.Books;
/**
 *
 *@author haverinen
 * Converts JMS MapMessages into Java Objects to be stored in DB
 */
public class BibleBookDataMessageConverter implements MessageConverter{

    public Message toMessage(Object arg0, Session arg1) throws JMSException, MessageConversionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object fromMessage(Message dataMessage) throws JMSException, MessageConversionException {

        if(dataMessage!=null && !( dataMessage instanceof MapMessage))
        {
            throw new MessageConversionException("Given message was not MapMessage");
 	    }
        MapMessage mapMessage=(MapMessage)dataMessage;
       // Bookdatasource bookSource= new Bookdatasource();
        String bookInitials=mapMessage.getString("bookInitials");
        Book book=Books.installed().getBook(bookInitials);

        if(!BookCategory.BIBLE.equals(book.getBookCategory()))
        {
            throw new MessageConversionException("Given book is not a bible");
        }
        return  book;
    }





}
