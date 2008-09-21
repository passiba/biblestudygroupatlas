/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining;

import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import fi.passiba.services.biblestudy.persistance.Book;
import java.util.List;

/**
 *
 * @author haverinen
 */
public interface IBibleDataMining {
    
    public void retrieveBookdata();
    public void registerBookDatasource(String bookdatasourceURL);
    
    public void addBookDatasource(Bookdatasource datasource);

    public Bookdatasource updateBookDatasource(Bookdatasource datasource); 
    public void deleteBookDatasource(Bookdatasource datasource); 
    public List<Book> findGAllRetrievedBooksbyTranslation();

}
