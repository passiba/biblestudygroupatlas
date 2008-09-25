/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages.wizards.biblesession.dataprocessing;

import fi.passiba.groups.ui.pages.wizards.biblesession.BibleSession;

/**
 *
 * @author haverinen
 * 
 * Handles the processing of sending the new databookinfo throuhg JMS queue for processing
 * 
 */
public interface IBibleBookdDataProcessing {
    
    
    public void sendBibleBookDataForProcessing(BibleSession biblesession);

}
