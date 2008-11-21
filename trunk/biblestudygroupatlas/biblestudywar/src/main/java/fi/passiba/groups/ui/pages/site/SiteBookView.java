/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages.site;

import fi.passiba.groups.ui.pages.BasePage;

import fi.passiba.services.bibledata.IBibleBookDataProcessing;
import fi.passiba.services.bibledata.SiteEditor;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.crosswire.common.util.Language;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookList;
//import org.crosswire.bibledesktop.book.install.BookTreeCellRenderer;
//import org.crosswire.bibledesktop.book.install.BookNode;

import org.crosswire.jsword.book.BookSet;
import org.crosswire.jsword.book.install.InstallException;
/**
 *
 * @author haverinen
 */
public class SiteBookView extends BasePage{

     /**
     * The model that we are providing a view/controller for
     */


    @SpringBean
    private SiteEditor siteEditorService ;
    @SpringBean
    private IBibleBookDataProcessing bibleDataProcessing;

    private String siteName;
    private Page backPage;
    private Book book;

    public SiteBookView(PageParameters parameters) {

          String bookInitials= (String)parameters.get("bookInitials");
          String siteName=(String)parameters.get("sitename");
          init (bookInitials,siteName);
		  
	}
   /*  public SiteBookView(Page backPage,final Book bookdata,final String site ) {

        this.backPage = backPage;
        this.siteName=site;
       
        init();
    }*/
    private void init(final String bookInitials,String siteName)
    {

         /*setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
            protected Object load() {

              
                return book;
            }
        }));*/



       SiteBookForm sitebookForm= new SiteBookForm("sitebookform",bookInitials,siteName);
       add(sitebookForm);
    }
    private final class SiteBookForm extends Form {

        public SiteBookForm(String id,String bookInit,String siteName) {
            super(id);

            Book book=siteEditorService.getInstaller(siteName).getBook(bookInit);
           

            final Label bookInitials = new Label("bookinitials",book.getInitials());
            bookInitials.setOutputMarkupId(true);
            add(bookInitials);
            final Label bookname = new Label("bookname", book.getName());
            bookname.setOutputMarkupId(true);
            add(bookname);

            final Label language =  new Label("language", book.getLanguage().getName());
            language.setOutputMarkupId(true);
            add(language);

            add(new SaveButton("save",siteName,book));
            add(new CancelButton("cancel"));
        }
    }

    private final class SaveButton extends Button {

        private Book installedBook;
        private String siteName;
        private SaveButton(String id,String sitename,Book book) {
            super(id);
            this.siteName=sitename;
            this.installedBook=book;

        }

        @Override
        public void onSubmit() {
            try {
                siteEditorService.getInstaller(siteName).install(installedBook);
            } catch (InstallException ex) {
               // Logger.getLogger(SiteBookView.class.getName()).log(Level.SEVERE, null, ex);
            }
            bibleDataProcessing.sendBibleBookDataForProcessing(installedBook);
            setResponsePage(SiteUpdateView.class);
        //setResponsePage(SiteEdit.this.backPage);
        }
    }

    private final class CancelButton extends Button {

        private static final long serialVersionUID = 1L;

        private CancelButton(String id) {
            super(id);

            setDefaultFormProcessing(false);
        }

        @Override
        public void onSubmit() {
            if (SiteBookView.this.backPage != null) {
                  setResponsePage(SiteBookView.this.backPage);
            } else {
                setResponsePage(SiteUpdateView.class);
            }
        }
    }

  
   
  

 
     private TreeModel createTreeModel(BookList books)
    {
        // return new BooksTreeModel(books);
        BookSet bmds = new BookSet(books.getBooks());
        TreeNode bookRoot=null ;//= new BookNode("root", bmds, 0, new Object[] {BookMetaData.KEY_CATEGORY, BookMetaData.KEY_XML_LANG}); //$NON-NLS-1$
        return new DefaultTreeModel(bookRoot);

    }
     protected void selected()
    {
        TreePath path = null;//treAvailable.getSelectionPath();

        Book book = null;
        Language lang = null;
        if (path != null)
        {
            Object last = path.getLastPathComponent();
            book = getBook(last);
            lang = getLanguage(last);
        }



       /* actions.getAction(DELETE).setEnabled(book != null && book.getDriver().isDeletable(book));
        actions.getAction(UNLOCK).setEnabled(book != null && book.isEnciphered());
        actions.getAction(UNINDEX).setEnabled(book != null && IndexManagerFactory.getIndexManager().isIndexed(book));
        actions.getAction(INSTALL).setEnabled(book != null && book.isSupported());
        actions.getAction(INSTALL_SEARCH).setEnabled(book != null && book.isSupported() && book.getBookCategory() == BookCategory.BIBLE);
        actions.getAction(CHOOSE_FONT).setEnabled(book != null || lang != null);*/
    }
     private Book getBook(Object anObj)
    {
        Object obj = anObj;
        if (obj instanceof DefaultMutableTreeNode)
        {
            obj = ((DefaultMutableTreeNode) obj).getUserObject();
        }
        if (obj instanceof Book)
        {
            return (Book) obj;
        }
        return null;
    }

    private Language getLanguage(Object anObj)
    {
        Object obj = anObj;
        if (obj instanceof DefaultMutableTreeNode)
        {
            obj = ((DefaultMutableTreeNode) obj).getUserObject();
        }
        if (obj instanceof Language)
        {
            return (Language) obj;
        }
        return null;
    }

    /*
     *
     *
     * Delete the current book
     *
    public void doDelete()
    {
        TreePath path = treAvailable.getSelectionPath();
        if (path == null)
        {
            return;
        }

        Object last = path.getLastPathComponent();
        Book book = getBook(last);

        try
        {
            String msg = shaper.shape(Msg.CONFIRM_DELETE_BOOK.toString(new Object[] {book.getName()}));
            if (CWOptionPane.showConfirmDialog(this, msg,
                            Msg.CONFIRM_DELETE_TITLE.toString(),
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                book.getDriver().delete(book);

                IndexManager imanager = IndexManagerFactory.getIndexManager();
                if (imanager.isIndexed(book))
                {
                    imanager.deleteIndex(book);
                }
            }
        }
        catch (BookException e)
        {
            Reporter.informUser(this, e);
        }
    }*/

    /**
     * Unlock the current book

    public void doUnlock()
    {
        TreePath path = treAvailable.getSelectionPath();
        if (path == null)
        {
            return;
        }

        Object last = path.getLastPathComponent();
        Book book = getBook(last);

        String unlockKey =
            (String) CWOptionPane.showInputDialog(this,
                                        Msg.UNLOCK_BOOK.toString(new Object[] {book.getName()}),
                                        Msg.UNLOCK_TITLE.toString(),
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        null,
                                        book.getUnlockKey());

        if (unlockKey != null && unlockKey.length() > 0)
        {
            book.unlock(unlockKey);
            Books.installed().addBook(book);
        }
    }
    */
    /**
     * Delete the current book

    public void doUnindex()
    {
        TreePath path = treAvailable.getSelectionPath();
        if (path == null)
        {
            return;
        }

        Object last = path.getLastPathComponent();
        Book book = getBook(last);

        try
        {
            IndexManager imanager = IndexManagerFactory.getIndexManager();
            if (imanager.isIndexed(book)
                && CWOptionPane.showConfirmDialog(this, Msg.CONFIRM_UNINSTALL_BOOK.toString(new Object[] {book.getName()}),
                                              Msg.CONFIRM_UNINSTALL_TITLE.toString(),
                                              JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                imanager.deleteIndex(book);
            }
            actions.getAction(UNINDEX).setEnabled(imanager.isIndexed(book));
        }
        catch (BookException e)
        {
            Reporter.informUser(this, e);
        }
    }
    */
    /**
     * Reload and redisplay the list of books

    public void doRefresh()
    {
        if (installer != null)
        {
            try
            {
                int webAccess = InternetWarning.GRANTED;
                if (WebWarning.instance().isShown())
                {
                    webAccess = InternetWarning.showDialog(this, "?"); //$NON-NLS-1$
                }

                if (webAccess == InternetWarning.GRANTED)
                {
                    installer.reloadBookList();
                    setTreeModel(installer);
                }
            }
            catch (InstallException ex)
            {
                Reporter.informUser(this, ex);
            }
        }
    }*/

    /**
     * Kick off the installer

    public void doInstall()
    {
        if (installer == null)
        {
            return;
        }

        TreePath path = treAvailable.getSelectionPath();
        if (path == null)
        {
            return;
        }

        int webAccess = InternetWarning.GRANTED;
        if (WebWarning.instance().isShown())
        {
            webAccess = InternetWarning.showDialog(this, "?"); //$NON-NLS-1$
        }

        if (webAccess != InternetWarning.GRANTED)
        {
            return;
        }

        Object last = path.getLastPathComponent();
        Book name = getBook(last);

        try
        {
            // Is the book already installed? Then nothing to do.
            Book book = Books.installed().getBook(name.getName());
            if (book != null && !installer.isNewer(name))
            {
                Reporter.informUser(this, Msg.INSTALLED, name.getName());
                return;
            }

            float size = installer.getSize(name) / 1024.0F;
            Msg msg = Msg.KB_SIZE;
            if (size > 1024.0F)
            {
                size /= 1024.0F;
                msg = Msg.MB_SIZE;
            }

            if (CWOptionPane.showConfirmDialog(this, msg.toString(new Object[] {name.getName(), new Float(size)}),
                            Msg.CONFIRMATION_TITLE.toString(),
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                installer.install(name);
            }
        }
        catch (InstallException ex)
        {
            Reporter.informUser(this, ex);
        }
    }*/


}
