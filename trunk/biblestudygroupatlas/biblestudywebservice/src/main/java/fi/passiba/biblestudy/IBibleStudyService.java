/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy;

import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.Verse;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author haverinen
 */
public interface IBibleStudyService {
     
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Chapter> findChapterByChapterIdBookIdSectionIdBibleVersionID(long translationid, long sectionid, long bookid, long chapterid);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Verse> findVerseByVerseidBookIdChapterIdSectionIdTranslationId(long translationid,long sectionid,long bookid,long chaperid,long verseid);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public Bibletranslation findBibleVersionByTranslationId(long translationid);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Bibletranslation> findBibleVersionByTranslations();
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Booksection> findBooksectionByBooksectionIdAndBibleTranslationId(long bibletranslationid,long booksectionid);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Book>  findBooksByBookIDSectionIDandTranslationId( long translationid, long sectionid, long bookid);
}
