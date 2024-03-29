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
import fi.passiba.services.biblestudy.persistance.ChapterVoting;
import fi.passiba.services.biblestudy.persistance.Verse;

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
    @Transactional(propagation = Propagation.REQUIRED)
    public void addBookDatasource(Bookdatasource datasource);
    @Transactional(propagation = Propagation.REQUIRED)
    public Bookdatasource updateBookDatasource(Bookdatasource datasource); 
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBookDatasource(Bookdatasource datasource); 

    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Bookdatasource> findBookDataSourcesByStatus(String status);

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Bibletranslation> findAllBibleTranslations();
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Bibletranslation findBibleTranslationById(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Booksection> findBookSectionByBibleTranslationId(long id);
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Booksection findBookSectionById(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Book> findBooksByBooksectionId(long id);
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Book findBookById(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Chapter> findChaptersByBookId(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Chapter findChapterById(long id);
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Verse> findVersesByChapterId(long id);

    @Transactional(propagation = Propagation.REQUIRED)
    public void addVerse(Verse verse);
    @Transactional(propagation = Propagation.REQUIRED)
    public Verse updateVerse(Verse verse);
    
    @Transactional(propagation = Propagation.REQUIRED)
    public Chapter updateChapter(Chapter chapter);

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateChapterVoting(ChapterVoting chaptervoting);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public ChapterVoting findRatingByChapterid(long id);

    @Transactional(propagation = Propagation.REQUIRED)
    public void addBibleData(Bookdatasource datasource);

}
