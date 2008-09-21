/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.biblestudy.datamining;

import fi.passiba.services.biblestudy.datamining.dao.IBookDatasouceDAO;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import fi.passiba.services.biblestudy.persistance.Book;
import java.io.FileNotFoundException;
import java.util.List;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;

/**
 *
 * @author haverinen
 */
@ManagedResource(objectName="biblestudy:name=BibleDataMining")
public class BibleDataMiningImp implements IBibleDataMining {
    
      private IBookDatasouceDAO datasourceDAO= null;

    public IBookDatasouceDAO getDatasourceDAO() {
        return datasourceDAO;
    }

    public void setDatasourceDAO(IBookDatasouceDAO datasourceDAO) {
        this.datasourceDAO = datasourceDAO;
    }
    
    @ManagedOperation(description="Retrieve daily new section of books of bible")
    public void retrieveBookdata() {
       
         try {
            ScraperConfiguration config =
                    new ScraperConfiguration("C:/java/projects/biblestudy/src/main/resources/bible.xml");
            Scraper scraper = new Scraper(config, "c:/temp/");
            
         
            scraper.setDebug(true);
            scraper.execute();

           // takes variable created during execution
         //   Variable article =  (Variable) scraper.getContext().getVar("article");

            // do something with articles...
          //  System.out.println(article.toString())
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void registerBookDatasource(String bookdatasourceURL) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addBookDatasource(Bookdatasource datasource) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Bookdatasource updateBookDatasource(Bookdatasource datasource) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteBookDatasource(Bookdatasource datasource) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Book> findGAllRetrievedBooksbyTranslation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
