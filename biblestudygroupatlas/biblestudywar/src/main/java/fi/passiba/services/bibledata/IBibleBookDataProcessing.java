/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.bibledata;

import org.crosswire.jsword.book.Book;

/**
 *
 * @author haverinen
 *
 * Handles the processing of sending the new book throuhg JMS queue for processing
 */
public interface IBibleBookDataProcessing {

     public void sendBibleBookDataForProcessing(Book book);


}
