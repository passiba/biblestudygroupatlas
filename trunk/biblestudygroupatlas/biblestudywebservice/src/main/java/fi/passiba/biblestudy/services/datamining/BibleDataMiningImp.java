/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.biblestudy.services.datamining;

import fi.passiba.services.biblestudy.dao.IBibletranslationDAO;
import fi.passiba.services.biblestudy.dao.IBooksectionDAO;
import fi.passiba.services.biblestudy.dao.IBookDAO;
import fi.passiba.services.biblestudy.dao.IChapterDAO;
import fi.passiba.services.biblestudy.dao.IVerseDAO;
import fi.passiba.services.biblestudy.dao.VerseDAO;
import fi.passiba.services.biblestudy.datamining.dao.IBookDatasouceDAO;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.ChapterVoting;
import fi.passiba.services.biblestudy.persistance.Verse;
import fi.passiba.biblestudy.datamining.ParserHelper;
import fi.passiba.biblestudy.datamining.ChapterInfo;
import fi.passiba.services.biblestudy.dao.BookDAO;
import fi.passiba.services.biblestudy.dao.BooksectionDAO;
import fi.passiba.services.biblestudy.dao.ChapterDAO;
import fi.passiba.services.biblestudy.dao.IChapterVotingDAO;
import fi.passiba.services.biblestudy.datamining.dao.BookDatasouceDAO;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.crosswire.jsword.book.BookCategory;
import org.crosswire.jsword.book.BookData;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.OSISUtil;
import org.crosswire.jsword.passage.Key;
import org.crosswire.jsword.passage.KeyFactory;
import org.crosswire.jsword.passage.NoSuchKeyException;
import org.crosswire.jsword.passage.NoSuchVerseException;
import org.crosswire.jsword.passage.Passage;
import org.crosswire.jsword.passage.PassageKeyFactory;
import org.crosswire.jsword.passage.VerseFactory;
import org.crosswire.jsword.versification.BibleInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jmx.export.annotation.ManagedResource;

//import org.webharvest.definition.ScraperConfiguration;
//import org.webharvest.runtime.Scraper;

/**
 *
 * @author haverinen
 */
@ManagedResource(objectName = "biblestudy:name=BibleDataMining")
public class BibleDataMiningImp implements IBibleDataMining {

    private IBookDatasouceDAO datasourceDAO = null;
    private IBibletranslationDAO translationDAO = null;
    private IBooksectionDAO booksectionDAO = null;
    private IBookDAO bookDAO = null;
    private IChapterDAO chapterDAO = null;
    private IVerseDAO verseDAO=null;
    private IChapterVotingDAO chapterVotingDAO=null;
    private SessionFactory sessionFactory;

        /**
     * How we create Passages
     */
    private static KeyFactory keyf = PassageKeyFactory.instance();
  
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

   

    public enum StatusType {

        ACTIVE("Aktiivinen"), NOTACTIVE("Ei Aktiivinen"), PARSED("Parsittu"), FLAWED("Virheellinen");
        private String status;

        private StatusType(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

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
    
   

    public IVerseDAO getVerseDAO() {
		return verseDAO;
	}

	public void setVerseDAO(IVerseDAO verseDAO) {
		this.verseDAO = verseDAO;
	}

    public IChapterVotingDAO getChapterVotingDAO() {
        return chapterVotingDAO;
    }

    public void setChapterVotingDAO(IChapterVotingDAO chapterVotingDAO) {
        this.chapterVotingDAO = chapterVotingDAO;
    }

    public void addVerse(Verse verse) {
         verseDAO.save(verse);
    }

    public Verse updateVerse(Verse verse) {
        verseDAO.saveOrUpdate(verse);
        return verse;
    }
    public Chapter updateChapter(Chapter chapter) {
      chapterDAO.update(chapter);
      return chapter;
    }
	//@ManagedOperation(description = "Retrieve daily new section of books of bible")
    public void retrieveBookdata() {
         /*
        try {
           ScraperConfiguration config = null;
            Scraper scraper = null;

            List<Bookdatasource> datasources = datasourceDAO.findBookDataSourcesByStatus(StatusType.ACTIVE.getStatus());
            String outputDir = "";
            for (Bookdatasource datasource : datasources) {

                if (config == null && scraper == null) {
                    config = new ScraperConfiguration(datasource.getConfigFileDir() + datasource.getScraperConfigFile());
                    outputDir = datasource.getOutputDir();
                    scraper = new Scraper(config, datasource.getOutputDir());
                    outputDir += datasource.getOutputSubDir();

                }
                // int begingIndex = datasource.getWeburlName().indexOf("1992/"), endIndex = datasource.getWeburlName().length() - 1;
                // String filename = datasource.getWeburlName().substring(begingIndex, endIndex);
                scraper.addVariableToContext("startUrl", datasource.getWeburlName());
                scraper.addVariableToContext("outputSubDir", datasource.getOutputSubDir());

                scraper.addVariableToContext("filename", datasource.getOutputFileName());
                scraper.setDebug(true);
                scraper.execute();
                parseBookXMLData(datasource, outputDir);

            }


        // takes variable created during execution
        //   Variable article =  (Variable) scraper.getContext().getVar("article");

        // do something with articles...
        //  System.out.println(article.toString())
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }*/

    }

   private List<Verse> addVersesData(Chapter chap, String Text,String numbers) {

     
       System.out.println("number "+ numbers);
       System.out.println("text "+ Text);
       StringTokenizer numbersplit=new StringTokenizer(numbers);
       System.out.println("numbersplit.countTokens() "+ numbersplit.countTokens());
       List<Verse>verses=new ArrayList();
       int count=numbersplit.countTokens();
       List<String>num=new ArrayList();
       for(;numbersplit.hasMoreElements();)
       {
          num.add(numbersplit.nextToken());
       }
        for(int i=0;i<num.size();i++)
        {
            String verseText="";
            
            if(i+1<num.size())
            {
                 verseText=Text.substring(Text.indexOf(num.get(i)+1), Text.indexOf(num.get(i+1)));
            }else
            {
                verseText=Text.substring(Text.indexOf(num.get(i)+1), Text.length()-1);
            }
            Verse verse = new Verse();
            verse.setVerseNum(Integer.valueOf(num.get(i)));
            verse.setChapter(chap);
            verse.setVerseText(verseText);
            System.out.println("verse nro "+num.get(i));
            System.out.println("verse text "+verseText);
            verses.add(verse);
        }
        return verses;
    }


    public void parseBookXMLData(Bookdatasource datasource, String ouputDir) {

    /*    Transaction tx = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            BookDAO bookDao = new BookDAO();
            bookDao.setSessionFactory(sessionFactory);
            BookDatasouceDAO datasourceDao = new BookDatasouceDAO();
            datasourceDao.setSessionFactory(sessionFactory);
            BooksectionDAO booksectionDao = new BooksectionDAO();
            booksectionDao.setSessionFactory(sessionFactory);
            ChapterDAO chapterDao = new ChapterDAO();
            chapterDao.setSessionFactory(sessionFactory);
            
            VerseDAO verseDao= new VerseDAO();
            verseDao.setSessionFactory(sessionFactory);

            ParserHelper parseHelper = new ParserHelper();
            List<Book> books = new ArrayList();
            Booksection booksection = booksectionDao.getById(new Long(3));
            int index = 0;

            //datasources = datasourceDao.findBookDataSourcesByStatus(StatusType.ACTIVE.getStatus());
            //for (Bookdatasource datasource : datasources) {
            List<ChapterInfo> chapters = parseHelper.readParsedBookDataSources(ouputDir + datasource.getOutputFileName());
            Book book = new Book();


            book.setBooksection(booksection);
            book.setBookText(datasource.getOutputFileName().substring(0, datasource.getOutputFileName().indexOf(".")));
            bookDao.save(book);
            String status = "";
            int i = 1;
            List<Chapter> chaps = new ArrayList();
            for (ChapterInfo chapterInfo : chapters) {
                
                List <Verse>verses=new ArrayList();
                Chapter chapter = new Chapter();
                chapter.setBook(book);
                String title = chapterInfo.getSubTitle();
                chapter.setChapterTitle(title.trim());
               // System.out.println("verse nros "+chapterInfo.getNumber());
               // System.out.println("chapter text "+chapterInfo.getText());
               verses=addVersesData(chapter,chapterInfo.getText(),chapterInfo.getNumber());
               chapterDao.save( chapter);
                for (Verse verse : verses) {
                       verseDao.save(verse);
                  
                }
               chapterDao.update(chapter);
                chaps.add(chapter);

                i += 1;
            }
          
            bookDao.update(book);
            //datasource.setBook(book);
            //datasource.setStatus(status);
            //datasourceDao.update(datasource);

            if (((index++) % 15) == 0) {

                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
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

    public Bibletranslation findBibleTranslationById(long id) {
        return translationDAO.getById(id);
    }

    public Book findBookById(long id) {
        return bookDAO.getById(id);
    }

    public Chapter findChapterById(long id) {
        return chapterDAO.getById(id);
    }

    public List<Bookdatasource> findBookDataSourcesByStatus(String status) {
        return datasourceDAO.findBookDataSourcesByStatus(status);
    }

	public List<Verse> findVersesByChapterId(long id) {
		return verseDAO.findVersesByChapterId(id);
	}
    public void updateChapterVoting(ChapterVoting rating) {
        chapterVotingDAO.saveOrUpdate(rating);
    }

    public ChapterVoting findRatingByChapterid(long id) {
        return chapterVotingDAO.findRatingByChapterid(id);
    }

    public void addBibleData(org.crosswire.jsword.book.Book book) {

        Key results = book.getGlobalKeyList();
        


        System.out.println("Book " + book.getInitials() + " is available");
        if (BookCategory.BIBLE.equals(book.getBookCategory())) {
            
            Bibletranslation translation= new Bibletranslation();
            translation.setBibleName(book.getName());
            translation.setBibleAbbrv(book.getInitials());
            translation.setPublishedDate(new Date());
            translation.setPublisherName("test");
            translationDAO.save( translation);
            Iterator it2 = results.iterator();

            for(int entries = 1;it2.hasNext() && entries < BibleInfo.booksInBible();entries++) {
                Key key = (Key) it2.next();
                BookData data = new BookData(book, key);

                try {
                     org.crosswire.jsword.passage.Verse verse =getVerse(key);
                    if(verse!=null)
                    {
                        int bookNum=verse.getBook();
                        int chapterNum=verse.getChapter();
                        int verseNum=verse.getVerse();
                    } 
                    if (data.getOsisFragment() == null)
                    {
                        System.out.println("And the text against that key is " + OSISUtil.getPlainText(data.getOsisFragment()));
                    }
                    /* StringBuffer buf = new StringBuffer();
                    String osisID = key.getOsisID();
                    buf.append(book.getInitials());
                    buf.append(':');
                    buf.append(osisID);
                    buf.append(" - "); //$NON-NLS-1$
                    String rawText = book.getRawText(key);
                    if (rawText != null && rawText.trim().length() > 0) {
                    buf.append(rawText);
                    } else {
                    buf.append("Not found"); //$NON-NLS-1$
                    }*/
                } catch (BookException ex) {
                    Logger.getLogger(BibleDataMiningImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            // entries++;
              /* StringBuffer buf = new StringBuffer();
            String osisID = key.getOsisID();
            buf.append(book.getInitials());
            buf.append(':');
            buf.append(osisID);
            buf.append(" - "); //$NON-NLS-1$
            String rawText = book.getRawText(key);
            if (rawText != null && rawText.trim().length() > 0) {
            buf.append(rawText);
            } else {
            buf.append("Not found"); //$NON-NLS-1$
            }*/
            }
        }
    }
     private static org.crosswire.jsword.passage.Verse getVerse(Key key)
    {
        if (key instanceof org.crosswire.jsword.passage.Verse)
        {
            return (org.crosswire.jsword.passage.Verse) key;
        }

       /* if (key instanceof Passage)
        {
            Passage ref = getPassage(key);
            return ref.getVerseAt(0);
        }*/

        try
        {
            return VerseFactory.fromString(key.getName());
        }
        catch (NoSuchVerseException ex)
        {
           // log.warn("Key can't be a verse: " + key.getName()); //$NON-NLS-1$
            return null;
        }
    }
     /**
     * Not all keys represent passages, but we ought to be able to get something
     * close to a passage from anything that does passage like work.
     * If you pass a null key into this method, you get a null Passage out.
     */
    private static Passage getPassage(Key key)
    {
        if (key == null)
        {
            return null;
        }

        if (key instanceof Passage)
        {
            return (Passage) key;
        }

        Key ref = null;
        try
        {
            ref = keyf.getKey(key.getName());
        }
        catch (NoSuchKeyException ex)
        {
           // log.warn("Key can't be a passage: " + key.getName()); //$NON-NLS-1$
            ref = keyf.createEmptyKeyList();
        }
        return (Passage) ref;
    }


}
