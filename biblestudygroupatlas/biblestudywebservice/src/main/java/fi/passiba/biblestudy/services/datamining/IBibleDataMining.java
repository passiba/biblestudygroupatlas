/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining;

import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author haverinen
 */
public interface IBibleDataMining {
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public void retrieveBookdata();
    public void registerBookDatasource(String bookdatasourceURL);
    
    public void addBookDatasource(Bookdatasource datasource);

    public Bookdatasource updateBookDatasource(Bookdatasource datasource); 
    public void deleteBookDatasource(Bookdatasource datasource); 

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Bibletranslation> findAllBibleTranslations();
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Booksection> findBookSectionByBibleTranslationId(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Booksection findBookSectionById(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Book> findBooksByBooksectionId(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Chapter> findChaptersByBookId(long id);

}
