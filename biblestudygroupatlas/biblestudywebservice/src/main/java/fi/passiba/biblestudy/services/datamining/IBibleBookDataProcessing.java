/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining;

import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;



/**
 *
 * @author haverinen
 *
 * Handles the processing of sending the new book throuhg JMS queue for processing
 */
public interface IBibleBookDataProcessing {

     public void sendBibleBookDataForProcessing(Bookdatasource bookSource);


}
