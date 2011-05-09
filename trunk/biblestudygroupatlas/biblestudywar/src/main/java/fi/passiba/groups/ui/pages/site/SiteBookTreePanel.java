package fi.passiba.groups.ui.pages.site;

import fi.passiba.services.bibledata.SiteEditor;

import fi.passiba.services.bibledata.sword.HttpSwordInstaller;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
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
import org.crosswire.jsword.book.install.Installer;

/**
 * Panel displaying the different site book information
 * @author haverinen
 *
 */
public class SiteBookTreePanel extends Panel {

    @SpringBean
    private SiteEditor siteEditorService;
   
   
    private Tree tree;

   private List<SiteInstaller>siteIntstallers=new ArrayList();

   private Map installers = null;
    /**
     * There isn't any model because model is auto binded using {@link FolderProvider}
     * @param id
     */
    public SiteBookTreePanel(String id) {
        super(id);

        if (siteEditorService != null && siteEditorService.getInstallers() != null) {
            installers = siteEditorService.getInstallers();

            if (installers != null) {
                Iterator iter = installers.keySet().iterator();
                while (iter.hasNext()) {
                    String name = (String) iter.next();
                    Installer installer = (Installer) installers.get(name);
                    SiteInstaller siteValues = new SiteInstaller(name, installer);
                    siteIntstallers.add(siteValues);


                }
            }
        }



       /* try {
            siteEditorService.getInstaller(site).reloadBookList();
        } catch (InstallException e) {
            e.printStackTrace();
        }*/
        //availableBibles = siteEditorService.getInstaller(site).getBooks(BookFilters.getOnlyBibles());
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
        return convertToTreeModelInstallers(siteIntstallers);
    }

    public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {

        PageParameters params = new PageParameters();

        Object obj = (Object) ((DefaultMutableTreeNode) node).getUserObject();
        if (obj != null && obj instanceof Book) {
            Book book = (Book) obj;
            params.put("bookInitials", book.getInitials());

           // params.put("sitename", parent.getParent().getModelObjectAsString());
            Object par= (Object)((DefaultMutableTreeNode)node.getParent()).getUserObject();
            if(par != null && par instanceof SiteInstaller)
            {

                SiteInstaller inst=(SiteInstaller)par;
                params.put("sitename", inst.getSiteUpdateName());
            }
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

     protected TreeModel convertToTreeModelInstallers(List<SiteInstaller>siteIntstallers) {

        TreeModel model = null;
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Installers");


        if (installers != null && !installers.isEmpty()) {
             addInstallers(rootNode, siteIntstallers);
        }
        model = new DefaultTreeModel(rootNode);
        return model;
    }
    private void addInstallers(DefaultMutableTreeNode parent,List<SiteInstaller>siteIntstallers)
    {

            for (SiteInstaller installer:siteIntstallers) {

            DefaultMutableTreeNode child = new DefaultMutableTreeNode(installer);
            Installer inst=siteEditorService.getInstaller(installer.getSiteUpdateName());
            try
                {
                   inst.reloadBookList();
                }catch(InstallException ie)
                {
                    ie.printStackTrace();
                }
                List<Book> availableBibles=inst.getBooks(BookFilters.getOnlyBibles());
                parent.add(child);
                addSiteBibles(child,availableBibles);

        }

    }
    private void addSiteBibles(DefaultMutableTreeNode parent, List<Book> sub) {
        for (Iterator<Book> bibleBookIt = sub.iterator(); bibleBookIt.hasNext();) {
            final Book book = bibleBookIt.next();
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(book);
            parent.add(child);

        }
    }

  
}
