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

/**
 *
 * @author haverinen
 * 
 * Converts JMS MapMessages into Java Objects to be stored in DB
 * 
 */
public class BibleBookDataMessageConverter implements MessageConverter{

    public Message toMessage(Object arg0, Session arg1) throws JMSException, MessageConversionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * 
     * @param dataMessage
     * @return Object ,object retrieved from Message object,ie fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource
     * class instance 
     * @throws javax.jms.JMSException,org.springframework.jms.support.converter.MessageConversionException
     * @throws org.springframework.jms.support.converter.MessageConversionException
     */
    public Object fromMessage(Message dataMessage) throws JMSException, MessageConversionException {
       
        if(dataMessage!=null && !( dataMessage instanceof MapMessage))
        {
            throw new MessageConversionException("Given message was not MapMessage");
        }
        MapMessage mapMessage=(MapMessage)dataMessage;
        Bookdatasource bookSource= new Bookdatasource();
        
        bookSource.setWeburlName(mapMessage.getString("weburlName"));
        bookSource.setCreatedBy(mapMessage.getString("createdBy"));
        bookSource.setStatus(mapMessage.getString("status"));
        return bookSource;
        
    }

}
