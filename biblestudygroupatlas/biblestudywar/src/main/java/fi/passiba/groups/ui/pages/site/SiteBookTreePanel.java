package fi.passiba.groups.ui.pages.site;

import fi.passiba.services.bibledata.SiteEditor;

import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookFilters;
import org.crosswire.jsword.book.install.InstallException;

/**
 * Panel displaying the different site book information
 * @author haverinen
 *
 */
public class SiteBookTreePanel extends Panel {

    @SpringBean
    private SiteEditor siteEditorService;
    private String sitename;
    private List<Book> availableBibles;
    private Tree tree;

    /**
     * There isn't any model because model is auto binded using {@link FolderProvider}
     * @param id
     */
    public SiteBookTreePanel(String id, String site) {
        super(id);
        // bibleDataForm form = new bibleDataForm("siteBooksForm",site);
        // add(form);
        this.sitename = site;
        try {
            siteEditorService.getInstaller(site).reloadBookList();
        } catch (InstallException e) {
            e.printStackTrace();
        }
        availableBibles = siteEditorService.getInstaller(site).getBooks(BookFilters.getOnlyBibles());
        tree = new BookTree("bookTree", createTreeModel()) {

            @Override
            protected MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
                return SiteBookTreePanel.this.newNodeLink(parent, id, node);

            }
        };
        add(tree);

    }

    protected AbstractTree getTree() {
        return tree;
    }

    protected TreeModel createTreeModel() {


        return convertToTreeModel(availableBibles);
    }

    public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {

        PageParameters params = new PageParameters();
        Object obj = (Object) ((DefaultMutableTreeNode) node).getUserObject();
        if (obj != null && obj instanceof Book) {
            Book book = (Book) obj;
            params.put("bookInitials", book.getInitials());
            params.put("sitename", sitename);
        }
        BookmarkablePageLink nodeLink = new BookmarkablePageLink(id, SiteBookView.class, params);

        return nodeLink;
    }

    protected TreeModel convertToTreeModel(List<Book> availableBibles) {

        TreeModel model = null;
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Site Bibles");


        if (availableBibles != null && !availableBibles.isEmpty()) {
            addSiteBibles(rootNode, availableBibles);
        }
        model = new DefaultTreeModel(rootNode);
        return model;
    }

    private void addSiteBibles(DefaultMutableTreeNode parent, List<Book> sub) {
        for (Iterator<Book> bibleBookIt = sub.iterator(); bibleBookIt.hasNext();) {
            final Book book = bibleBookIt.next();
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(book);
            parent.add(child);

        }
    }

    /*private class bibleDataForm extends Form {

    private String sitename;
    List<Book> availableBibles;
    private Tree tree;


    private bibleDataForm(String id,String site) {
    super(id);
    this.sitename=site;
    try {
    siteEditorService.getInstaller(site).reloadBookList();
    } catch (InstallException e) {
    e.printStackTrace();
    }
    availableBibles =siteEditorService.getInstaller(site).getBooks(BookFilters.getOnlyBibles());
    tree = new BookTree("bookTree", createTreeModel()) {

    @Override
    protected MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
    return bibleDataForm.this.newNodeLink(parent, id, node);

    }
    };
    add(tree);
    }

    protected AbstractTree getTree() {
    return tree;
    }


    protected TreeModel createTreeModel() {


    return convertToTreeModel(availableBibles);
    }

    public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {

    PageParameters params = new PageParameters();
    Object obj = (Object) ((DefaultMutableTreeNode) node).getUserObject();
    if (obj != null && obj instanceof Book) {
    Book book = (Book ) obj;
    params.put("bookname", book.getName());
    params.put("sitename",sitename);
    }
    BookmarkablePageLink nodeLink = new BookmarkablePageLink(id, SiteBookView.class,params);

    return nodeLink;
    }
    protected TreeModel convertToTreeModel(List<Book> availableBibles) {

    TreeModel model = null;
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Site Bibles");


    if (availableBibles != null && !availableBibles.isEmpty()) {
    addSiteBibles(rootNode, availableBibles);
    }
    model = new DefaultTreeModel(rootNode);
    return model;
    }

    private void  addSiteBibles(DefaultMutableTreeNode parent, List<Book> sub) {
    for (Iterator<Book> bibleBookIt = sub.iterator();  bibleBookIt.hasNext();) {
    final Book book = bibleBookIt.next();
    DefaultMutableTreeNode child = new DefaultMutableTreeNode(book);
    parent.add(child);

    }
    }
    }*/
}
