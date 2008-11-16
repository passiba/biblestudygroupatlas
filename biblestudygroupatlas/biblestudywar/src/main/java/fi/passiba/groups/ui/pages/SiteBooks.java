/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages;

import fi.passiba.groups.ui.pages.BasePage;

import fi.passiba.services.bibledata.IBibleBookDataProcessing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.BookList;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.install.InstallException;
import org.crosswire.jsword.book.install.InstallManager;
import org.crosswire.jsword.book.install.Installer;
import org.crosswire.jsword.book.install.sword.AbstractSwordInstaller;
//import org.crosswire.bibledesktop.book.install.BookTreeCellRenderer;
//import org.crosswire.bibledesktop.book.install.BookNode;

import org.crosswire.common.util.Language;
import org.crosswire.jsword.book.BookMetaData;
import org.crosswire.jsword.book.BookSet;
import org.crosswire.jsword.index.IndexManagerFactory;
import org.crosswire.jsword.util.IndexDownloader;
/**
 *
 * @author haverinen
 */
public class SiteBooks extends BasePage{

     /**
     * The model that we are providing a view/controller for
     */


    @SpringBean
    private IBibleBookDataProcessing bibleDataBooksourceService;
    private transient InstallManager imanager;
    private transient AbstractSwordInstaller installer;
    private String port,host,catalogDir,packageDir,proxyHost,proxyPort;

    private static final String INSTALLED_BOOKS_LABEL = "InstalledBooksLabel"; //$NON-NLS-1$
    private static final String AVAILABLE_BOOKS_LABEL = "AvailableBooksLabel"; //$NON-NLS-1$
    private static final String SELECTED_BOOK_LABEL = "SelectedBookLabel"; //$NON-NLS-1$
    private static final String REFRESH = "Refresh"; //$NON-NLS-1$
    private static final String INSTALL = "Install"; //$NON-NLS-1$
    private static final String INSTALL_SEARCH = "InstallSearch"; //$NON-NLS-1$
    private static final String DELETE = "Delete"; //$NON-NLS-1$
    private static final String UNLOCK = "Unlock"; //$NON-NLS-1$
    private static final String CHOOSE_FONT = "ChooseFont"; //$NON-NLS-1$
    private static final String UNINDEX = "Unindex"; //$NON-NLS-1$
    

    private JTree treAvailable;
    private Page backPage;
     public SiteBooks(Page backPage,final Installer installer) {

        this.backPage = backPage;
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

               // installers=imanager.getInstallers();
                return installer;
            }
        }));
        //init();
    }


   
    private void initializeIntallerBooks(Installer installer)
    {
         BookList bl = installer;
         if (bl == null)
         {
                bl = Books.installed();
         }
         createScrolledTree(bl);
    }
     /**
     *
     */
    private void createScrolledTree(BookList books)
    {
        treAvailable = new JTree();
        // Turn on tooltips so that they will show
        ToolTipManager.sharedInstance().registerComponent(treAvailable);
     //   treAvailable.setCellRenderer(new BookTreeCellRenderer());

        setTreeModel(books);
        // Add lines if viewed in Java Look & Feel
        treAvailable.putClientProperty("JTree.lineStyle", "Angled"); //$NON-NLS-1$ //$NON-NLS-2$
        treAvailable.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treAvailable.setCellEditor(null);
        treAvailable.setRootVisible(false);
        treAvailable.setShowsRootHandles(true);
        treAvailable.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent ev)
            {
               // selected();
            }
        });

    }
    public static void scheduleIndex(Book book, Installer installer,int operation)
    {
        // LATER(DMS): Enable this when we have indexes to download
//        String title = Msg.HOW_MESSAGE_TITLE.toString();
//        Msg msg = Msg.HOW_MESSAGE;
//        int choice = CWOptionPane.showOptionDialog(parent, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        //int choice = 1;

        switch (operation)
        {
        case 0: // download
            //Installer installer = selectInstaller(parent);
            if (installer != null)
            {
                Exception ex = null;
                try
                {
                    IndexDownloader.downloadIndex(book, installer);
                }
                catch (InstallException e)
                {
                    ex = e;

                }
                catch (BookException e)
                {
                    ex = e;
                }
                catch (IOException e)
                {
                    ex = e;
                }

                if (ex != null)
                {

                    //Reporter.informUser(parent, ex);

                   // String gtitle = Msg.HOW_GENERATE_TITLE.toString();
                   // Msg gmsg = Msg.HOW_GENERATE;
                   // int yn = CWOptionPane.showConfirmDialog(parent, gmsg, gtitle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                   // if (yn == JOptionPane.YES_OPTION)
                   // {
                        IndexManagerFactory.getIndexManager().scheduleIndexCreation(book);
                   // }
                }
            }
            break;

        case 1: // generate
            IndexManagerFactory.getIndexManager().scheduleIndexCreation(book);
            break;

        default: // cancel
            break;
        }
    }

    /**
     * Pick an installer
     * @param parent A component to tie dialogs to
     * @return The chosen installer or null if the user cancelled.
     
    private static Installer selectInstaller(Component parent)
    {
        // Pick an installer
        InstallManager insman = new InstallManager();
        Map installers = insman.getInstallers();
        Installer installer = null;
        if (installers.size() == 1)
        {
            Iterator it = installers.values().iterator();
            boolean hasNext = it.hasNext();
            assert hasNext;
            installer = (Installer) it.next();
        }
        else
        {
            JComboBox choice = new JComboBox(new InstallManagerComboBoxModel(insman));
            JLabel label = new JLabel(Msg.HOW_SITE.toString());
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(choice, BorderLayout.CENTER);

            String title = Msg.HOW_SITE_TITLE.toString();

            int yn = CWOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.YES_OPTION);
            if (yn == JOptionPane.YES_OPTION)
            {
                installer = (Installer) choice.getSelectedItem();
            }
        }

        return installer;
    }

 */
    public void setTreeModel(BookList books)
    {
        treAvailable.setModel(createTreeModel(books));
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
        TreePath path = treAvailable.getSelectionPath();

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
