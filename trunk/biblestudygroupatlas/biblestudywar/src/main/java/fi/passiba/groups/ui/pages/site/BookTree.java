package fi.passiba.groups.ui.pages.site;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.crosswire.jsword.book.Book;

public class BookTree extends Tree {
	
	public BookTree(String id, TreeModel model) {
		super(id, model);
	}
	
        @Override
	protected String renderNode(TreeNode node) {
		String nodeTitle="";
                Object nodeItem = (Object) ((DefaultMutableTreeNode) node).getUserObject();
		
                if(nodeItem!=null && nodeItem instanceof  Book)
                {
                    Book book=(Book )nodeItem;
                    nodeTitle=book.getInitials();
                }
                return nodeTitle;
	}
	
	@Override
	protected ResourceReference getFolderClosed() {
		return new ResourceReference(BookTree.class, "folder-closed.png");
	}

	@Override
	protected ResourceReference getFolderOpen() {
		return new ResourceReference(BookTree.class, "folder-open.png");
	}

	@Override
	protected ResourceReference getNodeIcon(TreeNode node) {
		return getFolderClosed();
	}

	
	
	
}