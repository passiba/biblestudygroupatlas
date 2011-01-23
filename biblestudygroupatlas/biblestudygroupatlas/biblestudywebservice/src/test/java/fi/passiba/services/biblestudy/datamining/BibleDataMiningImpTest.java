/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.biblestudy.datamining;

import fi.passiba.biblestudy.services.datamining.IBibleDataMining;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import java.util.List;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 *
 * @author haverinen
 {
    
    
 */
public class BibleDataMiningImpTest extends   AbstractDependencyInjectionSpringContextTests 
{
   private IBibleDataMining dataminingService = null;
   
    public enum StatusType {
        ACTIVE("Aktiivinen"), NOTACTIVE("Ei Aktiivinen"),PARSED("Parsittu");
        private String status;
        private StatusType(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
       
    }
   
    
   @Override
   protected String[] getConfigLocations() {
      return new String[] { "classpath:META-INF/biblestudy-webservice.xml" };
   }

    
    public void testRetrieveBibleData() throws Exception{
        dataminingService = (IBibleDataMining) applicationContext.getBean("IBibleDataMining");
        dataminingService.retrieveBookdata();
      
    }

    

}
