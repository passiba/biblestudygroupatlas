package fi.passiba.groups.ui.pages.wizards.biblesession;


import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.extensions.markup.html.tree.Tree;

public class ItemTree extends Tree {
	
	public ItemTree(String id, TreeModel model) {
		super(id, model);
	}
	
        @Override
	protected String renderNode(TreeNode node) {
		String nodeTitle="";
                Object nodeItem = (Object) ((DefaultMutableTreeNode) node).getUserObject();
		
                if(nodeItem!=null && nodeItem instanceof Bibletranslation)
                {
                    Bibletranslation translation=(Bibletranslation )nodeItem;
                    nodeTitle=translation.getBibleName();
                }
                if(nodeItem!=null && nodeItem instanceof Booksection)
                {
                   Booksection section=(Booksection )nodeItem;
                    nodeTitle=section.getSection();
                }
                if(nodeItem!=null && nodeItem instanceof Book)
                {
                    Book book=(Book )nodeItem;
                    nodeTitle=book.getBookText();
                }
                if(nodeItem!=null && nodeItem instanceof Chapter)
                {
                   Chapter chapter=( Chapter )nodeItem;
                    nodeTitle=chapter.getChapterTitle();
                }
                return nodeTitle;
	}
	
	@Override
	protected ResourceReference getFolderClosed() {
		return new ResourceReference(ItemTree.class, "folder-closed.png");
	}

	@Override
	protected ResourceReference getFolderOpen() {
		return new ResourceReference(ItemTree.class, "folder-open.png");
	}

	@Override
	protected ResourceReference getNodeIcon(TreeNode node) {
		return getFolderClosed();
	}

	
	
	
}