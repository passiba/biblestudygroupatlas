/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.serices.bibledata;

import fi.passiba.AbstractDependencyInjectionSpringContextTest;
import fi.passiba.services.bibledata.IBibleBookDataProcessing;
import fi.passiba.services.bibledata.SiteEditor;
import java.util.Iterator;
import java.util.Map;
import org.crosswire.jsword.book.install.Installer;
import fi.passiba.services.bibledata.sword.HttpSwordInstaller;
import fi.passiba.services.bibledata.sword.IndexResolver;
import java.util.List;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookData;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.BookFilters;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.OSISUtil;
import org.crosswire.jsword.book.install.InstallException;
import org.crosswire.jsword.bridge.BookIndexer;
import org.crosswire.jsword.bridge.BookInstaller;
import org.crosswire.jsword.passage.Key;

/**
 *
 * @author haverinen
 */
public class BibleDataServiceImpTest extends AbstractDependencyInjectionSpringContextTest {

    public void testBibeDataInstaller() throws Exception {
        SiteEditor siteEditorService = (SiteEditor) applicationContext.getBean("BibleDataService");

        IBibleBookDataProcessing bibleDataProcessing=(IBibleBookDataProcessing) applicationContext.getBean("bibleDataProcessingGageway");
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

                bibleDataProcessing.sendBibleBookDataForProcessing(book);
              /*  Key results = book.getGlobalKeyList();

                int entries = 0;
                System.out.println("Book " + book.getInitials() + " is available");
               
                Iterator it2 = results.iterator();

                while(it2.hasNext()) {

                    Key key = (Key) it2.next();
                    BookData data = new BookData(book, key);
                    System.out.println("And the text against that key is " + OSISUtil.getPlainText(data.getOsisFragment()));
                    entries++;


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
                    }
                }*/
              
            }


        }

  


    }
}
