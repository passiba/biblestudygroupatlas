/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.services.datamining;

import fi.passiba.services.biblestudy.dao.IBibletranslationDAO;
import fi.passiba.services.biblestudy.dao.IBooksectionDAO;
import fi.passiba.services.biblestudy.dao.IBookDAO;
import fi.passiba.services.biblestudy.dao.IChapterDAO;
import fi.passiba.services.biblestudy.datamining.dao.IBookDatasouceDAO;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
import java.io.FileNotFoundException;
import java.util.List;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
//import org.webharvest.definition.ScraperConfiguration;
//import org.webharvest.runtime.Scraper;

/**
 *
 * @author haverinen
 */
@ManagedResource(objectName="biblestudy:name=BibleDataMining")
public class BibleDataMiningImp implements IBibleDataMining {
    
    private IBookDatasouceDAO datasourceDAO= null;
    private IBibletranslationDAO translationDAO=null;
    
    private IBooksectionDAO booksectionDAO=null;
    private IBookDAO bookDAO=null;
    
    private IChapterDAO chapterDAO=null;
    public IBookDatasouceDAO getDatasourceDAO() {
        return datasourceDAO;
    }

    public void setDatasourceDAO(IBookDatasouceDAO datasourceDAO) {
        this.datasourceDAO = datasourceDAO;
    }

    public IBibletranslationDAO getTranslationDAO() {
        return translationDAO;
    }

    public void setTranslationDAO(IBibletranslationDAO translationDAO) {
        this.translationDAO = translationDAO;
    }

    public IBookDAO getBookDAO() {
        return bookDAO;
    }

    public void setBookDAO(IBookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public IBooksectionDAO getBooksectionDAO() {
        return booksectionDAO;
    }

    public void setBooksectionDAO(IBooksectionDAO booksectionDAO) {
        this.booksectionDAO = booksectionDAO;
    }

    public IChapterDAO getChapterDAO() {
        return chapterDAO;
    }

    public void setChapterDAO(IChapterDAO chapterDAO) {
        this.chapterDAO = chapterDAO;
    }
    
    @ManagedOperation(description="Retrieve daily new section of books of bible")
    public void retrieveBookdata() {
       
        /* try {
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
        }  */

    }

    

    public void addBookDatasource(Bookdatasource datasource) {
        datasourceDAO.save(datasource);
    }

    public Bookdatasource updateBookDatasource(Bookdatasource datasource) {
       datasourceDAO.saveOrUpdate(datasource);
       return datasource;
    }

    public void deleteBookDatasource(Bookdatasource datasource) {
        datasourceDAO.delete(datasource);
    }

   
    public List<Bibletranslation> findAllBibleTranslations() {
         return translationDAO.getAll();
    }

    public List<Booksection> findBookSectionByBibleTranslationId(long id) {
        return booksectionDAO.findBookSectionByBibleTranslationId(id);
    }

    public List<Book> findBooksByBooksectionId(long id) {
       return bookDAO.findBooksByBooksectionId(id);
    }

    public List<Chapter> findChaptersByBookId(long id) {
           return chapterDAO.findChaptersByBookId(id);
    }

   public Booksection findBookSectionById(long id) {
        return booksectionDAO.getById(id);
    }

}