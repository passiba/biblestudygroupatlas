/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.restlet;

import fi.passiba.services.biblestudy.dao.IBibletranslationDAO;
import fi.passiba.services.biblestudy.dao.IBookDAO;
import fi.passiba.services.biblestudy.dao.IBooksectionDAO;
import fi.passiba.services.biblestudy.dao.IChapterDAO;
import fi.passiba.services.biblestudy.dao.IVerseDAO;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.Verse;
import java.util.List;

/**
 *
 * @author haverinen
 */
public class BibleStudyServiceImp implements IBibleStudyService{
    

    private  IBookDAO bookDAO;
    private IChapterDAO chapterDao;
    private  IVerseDAO verseDAO;
    private IBibletranslationDAO translationDao;
    private IBooksectionDAO sectionDao;

    public IChapterDAO getChapterDao() {
        return chapterDao;
    }

    public void setChapterDao(IChapterDAO chapterDao) {
        this.chapterDao = chapterDao;
    }

    public IBooksectionDAO getSectionDao() {
        return sectionDao;
    }

    public void setSectionDao(IBooksectionDAO sectionDao) {
        this.sectionDao = sectionDao;
    }

    public IBibletranslationDAO getTranslationDao() {
        return translationDao;
    }

    public void setTranslationDao(IBibletranslationDAO translationDao) {
        this.translationDao = translationDao;
    }

    public IVerseDAO getVerseDAO() {
        return verseDAO;
    }

    public void setVerseDAO(IVerseDAO verseDAO) {
        this.verseDAO = verseDAO;
    }

    public IBookDAO getBookDAO() {
        return bookDAO;
    }

    public void setBookDAO(IBookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    
    
    public List<Chapter> findChapterByChapterIdBookIdSectionIdBibleVersionID(long translationid, long sectionid, long bookid, long chapterid) {
       return chapterDao.findChapterByChapterIdBookIdSectionIdBibleVersionID(translationid, sectionid, bookid, chapterid);
    }

    public List<Verse> findVerseByVerseidBookIdChapterIdSectionIdTranslationId(long translationid, long sectionid, long bookid, long chaperid, long verseid) {
        return verseDAO.findVerseByVerseidBookIdChapterIdSectionIdTranslationId(translationid, sectionid, bookid, chaperid, verseid);
    }

    public Bibletranslation findBibleVersionByTranslationId(long translationid) {
        return translationDao.getById(translationid);
    }

    public List<Booksection> findBooksectionByBooksectionIdAndBibleTranslationId(long bibletranslationid, long booksectionid) {
        return sectionDao.findBooksectionByBooksectionIdAndBibleTranslationId(bibletranslationid, booksectionid);
    }

    public List<Book> findBooksByBookIDSectionIDandTranslationId(long translationid, long sectionid, long bookid) {
       return bookDAO.findBooksByBookIDSectionIDandTranslationId(translationid, sectionid, bookid);
    }

    public List<Bibletranslation> findBibleVersionByTranslations() {
        return translationDao.getAll();
    }

}
