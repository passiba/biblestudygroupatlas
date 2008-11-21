/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.serices.bibledata;

import fi.passiba.AbstractDependencyInjectionSpringContextTest;
import fi.passiba.biblestudy.services.datamining.IBibleDataMining;
import fi.passiba.services.bibledata.IBibleBookDataProcessing;
import fi.passiba.services.bibledata.SiteEditor;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.crosswire.jsword.book.install.Installer;
import fi.passiba.services.bibledata.sword.HttpSwordInstaller;
import fi.passiba.services.bibledata.sword.IndexResolver;
import java.util.List;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookCategory;
import org.crosswire.jsword.book.BookData;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.BookFilters;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.OSISUtil;
import org.crosswire.jsword.book.install.InstallException;
import org.crosswire.jsword.bridge.BookIndexer;
import org.crosswire.jsword.bridge.BookInstaller;
import org.crosswire.jsword.passage.Key;
import org.crosswire.jsword.passage.KeyFactory;
import org.crosswire.jsword.passage.NoSuchKeyException;
import org.crosswire.jsword.passage.NoSuchVerseException;
import org.crosswire.jsword.passage.Passage;
import org.crosswire.jsword.passage.PassageKeyFactory;
import org.crosswire.jsword.passage.VerseFactory;
import org.crosswire.jsword.versification.BibleInfo;

/**
 *
 * @author haverinen
 */
public class BibleDataServiceImpTest extends AbstractDependencyInjectionSpringContextTest {

    /**
     * How we create Passages
     */
    private static KeyFactory keyf = PassageKeyFactory.instance();

    public void testBibeDataInstaller() throws Exception {
        /* SiteEditor siteEditorService = (SiteEditor) applicationContext.getBean("BibleDataService");
        IBibleDataMining bibeDataMining= (IBibleDataMining ) applicationContext.getBean("IBibleDataMining");
        // IBibleBookDataProcessing bibleDataProcessing=(IBibleBookDataProcessing) applicationContext.getBean("bibleDataProcessingGageway");
        Map installers = siteEditorService.getInstallers();
        String name = "";
        Iterator iter = installers.keySet().iterator();
        this.assertEquals(3, installers.size());
        while (iter.hasNext()) {
        name = (String) iter.next();
        HttpSwordInstaller installer = (HttpSwordInstaller) installers.get(name);
        System.out.println("Name" + name);
        System.out.println("Host" + installer.getHost());
        System.out.println("PackageDir" + installer.getPackageDirectory());
        System.out.println("IndexDir" + installer.getIndexDirectory());
        System.out.println("ProxyHost" + installer.getProxyHost());
        System.out.println("Proxyport" + installer.getProxyPort());

        try {
        installer.reloadBookList();
        } catch (InstallException e) {
        e.printStackTrace();
        }

        // Get a list of all the available bibles
        List<Book> availableBibles = installer.getBooks(BookFilters.getOnlyBibles());


        for (Book book : availableBibles) {
        System.out.println("Book " + book.getInitials() + " is available before jms"); //$NON-NLS-1$ //$NON-NLS-2$

        // Delete the book, if present
        // At the moment, JSword will not re-install. Later it will, if the remote version is greater.
        //try {
        if (Books.installed().getBook(book.getInitials()) != null) //$NON-NLS-1$
        {
        // Make the book unavailable.
        // This is normally done via listeners.

        // Actually do the delete
        // This should be a call on installer.
        try {
        Books.installed().removeBook(book);
        book.getDriver().delete(book);
        } catch (BookException e) {
        e.printStackTrace();
        }
        }
        // } catch (BookException e1) {
        //     e1.printStackTrace();
        // }
        // Now install it. Note this is a background task.
        installer.install(book);
        //IndexResolver.scheduleIndex(book, installer);
        }

        List bibles = Books.installed().getBooks(BookFilters.getOnlyBibles());
        Iterator it=bibles.iterator();
        while (it.hasNext())
        {
        Book book =(Book)it.next();
        //processBookData(book);
        //bibleDataProcessing.sendBibleBookDataForProcessing(book);
        bibeDataMining.addBibleData(book);



        }



        }*/
    }
    public void testSingeBibleDataInstaller() throws Exception {
        SiteEditor siteEditorService = (SiteEditor) applicationContext.getBean("BibleDataService");
        IBibleDataMining bibeDataMining = (IBibleDataMining) applicationContext.getBean("IBibleDataMining");
        // IBibleBookDataProcessing bibleDataProcessing=(IBibleBookDataProcessing) applicationContext.getBean("bibleDataProcessingGageway");
        Map installers = siteEditorService.getInstallers();
        String name = "", bookInitials = "FinPR92";

        Iterator iter = installers.keySet().iterator();
        this.assertEquals(3, installers.size());
        while (iter.hasNext()) {
            name = (String) iter.next();
            HttpSwordInstaller installer = (HttpSwordInstaller) installers.get(name);
            System.out.println("Name" + name);
            System.out.println("Host" + installer.getHost());
            System.out.println("PackageDir" + installer.getPackageDirectory());
            System.out.println("IndexDir" + installer.getIndexDirectory());
            System.out.println("ProxyHost" + installer.getProxyHost());
            System.out.println("Proxyport" + installer.getProxyPort());

            try {
                installer.reloadBookList();
            } catch (InstallException e) {
                e.printStackTrace();
            }

            // Get a list of all the available bibles


            Book book = installer.getBook(bookInitials);
            if (book != null) {
                System.out.println("Book " + book.getInitials() + " is available before jms"); //$NON-NLS-1$ //$NON-NLS-2$

                // Delete the book, if present
                // At the moment, JSword will not re-install. Later it will, if the remote version is greater.
                //try {
                if (Books.installed().getBook(book.getInitials()) != null) //$NON-NLS-1$
                {
                    // Make the book unavailable.
                    // This is normally done via listeners.

                    // Actually do the delete
                    // This should be a call on installer.
                    try {
                        Books.installed().removeBook(book);
                        book.getDriver().delete(book);
                    } catch (BookException e) {
                        e.printStackTrace();
                    }
                }
                // } catch (BookException e1) {
                //     e1.printStackTrace();
                // }
                // Now install it. Note this is a background task.
                //installer.install(book);
                try {
                siteEditorService.getInstaller(name).install(book);
            } catch (InstallException ex) {
               // Logger.getLogger(SiteBookView.class.getName()).log(Level.SEVERE, null, ex);
            }


                book = Books.installed().getBook(bookInitials);

                //processBookData(book);
                //bibleDataProcessing.sendBibleBookDataForProcessing(book);
                bibeDataMining.addBibleData(book);
            }
        }





    }

    private void processBookData(Book book) {
        Key results = book.getGlobalKeyList();




        if (BookCategory.BIBLE.equals(book.getBookCategory())) {

            System.out.println("Book " + book.getInitials() + " is available");

            Iterator it2 = results.iterator();

            for (int entries = 1; it2.hasNext() && entries < BibleInfo.booksInBible(); entries++) {
                Key key = (Key) it2.next();
                BookData data = new BookData(book, key);
                try {
                    /*   org.crosswire.jsword.passage.Verse verse =getVerse(key);
                    if(verse!=null)
                    {
                    int bookNum=verse.getBook();
                    int chapterNum=verse.getChapter();
                    int verseNum=verse.getVerse();
                    System.out.println("Verse data: bookNum "+ bookNum + " ChapterNum "+chapterNum+ " verseNum"+verseNum);
                    }*/
                    if (data.getOsisFragment() != null) {
                        System.out.println("And the text against that key is " + OSISUtil.getPlainText(data.getOsisFragment()));
                    }
                } catch (BookException ex) {
                    //  Logger.getLogger(BibleDataServiceImpTest.class.getName()).log(Level.SEVERE, null, ex);
                }



            }
        }
    }

    private static org.crosswire.jsword.passage.Verse getVerse(Key key) {
        if (key instanceof org.crosswire.jsword.passage.Verse) {
            return (org.crosswire.jsword.passage.Verse) key;
        }

        /* if (key instanceof Passage)
        {
        Passage ref = getPassage(key);
        return ref.getVerseAt(0);
        }*/

        try {
            return VerseFactory.fromString(key.getName());
        } catch (NoSuchVerseException ex) {
            // log.warn("Key can't be a verse: " + key.getName()); //$NON-NLS-1$
            return null;
        }
    }

    /**
     * Not all keys represent passages, but we ought to be able to get something
     * close to a passage from anything that does passage like work.
     * If you pass a null key into this method, you get a null Passage out.
     */
    private static Passage getPassage(Key key) {
        if (key == null) {
            return null;
        }

        if (key instanceof Passage) {
            return (Passage) key;
        }

        Key ref = null;
        try {
            ref = keyf.getKey(key.getName());
        } catch (NoSuchKeyException ex) {
            // log.warn("Key can't be a passage: " + key.getName()); //$NON-NLS-1$
            ref = keyf.createEmptyKeyList();
        }
        return (Passage) ref;
    }
}
