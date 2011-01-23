/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages.biblesession;

/**
 *
 * @author haverinen
 */
import fi.passiba.biblestudy.services.datamining.IBibleDataMining;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
public  class AbstractDataPanel extends Panel{
    
     @SpringBean
     protected IBibleDataMining bibleTranslationDataRetrievalService;

     public AbstractDataPanel(String id) {
        super(id);
    }
     
    public enum DataType {

        BIBLETRANSLATION("BIBLETRANSLATION"), BOOK("BOOK"), CHAPTER("CHAPTER"),
        VERSE("VERSE");
        private String type;

        private DataType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
   
}
