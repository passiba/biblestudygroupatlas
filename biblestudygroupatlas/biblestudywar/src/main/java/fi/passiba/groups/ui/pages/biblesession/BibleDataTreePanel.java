package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.services.biblestudy.persistance.Chapter;
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
import org.apache.wicket.markup.html.tree.AbstractTree;

/**
 * Panel displaying the different bibletranslation,booksection,chapter and verse information
 * @author haverinen
 *
 */
public class BibleDataTreePanel extends AbstractDataPanel  {

  
    /**
     * There isn't any model because model is auto binded using {@link FolderProvider}
     * @param id
     */
    public BibleDataTreePanel(String id) {
        super(id);
         bibleDataForm form = new bibleDataForm("bibleDataForm");
         add(form);

    }

    private class bibleDataForm extends Form {

        private List<Bibletranslation> fetchedBibleTranslations;
        private Tree tree;
        private List<Booksection> sections;
        private List<Book> books;
        private List<Chapter> chapters;

        private bibleDataForm(String id) {
            super(id);

            fetchedBibleTranslations = bibleTranslationDataRetrievalService.findAllBibleTranslations();
            tree = new ItemTree("itemTree", createTreeModel()) {

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

        /**
         * Creates the model that feeds the tree.
         * @return
         * 		New instance of tree model.
         */
        protected TreeModel createTreeModel() {


            return convertToTreeModel(fetchedBibleTranslations);
        }

        public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
           

            Object obj = (Object) ((DefaultMutableTreeNode) node).getUserObject();
            BookmarkablePageLink nodeLink = new BookmarkablePageLink(id, BibleSessionPage.class, getItemTypeAndId(obj));
           
            return nodeLink;
        }
        
        private PageParameters getItemTypeAndId(Object obj) {
            PageParameters params = new PageParameters();
            if (obj != null && obj instanceof Bibletranslation) {
                Bibletranslation bibletranslation = (Bibletranslation) obj;
                params.put("type", DataType.BIBLETRANSLATION);
                params.put("id", bibletranslation.getId());

            }

            if (obj != null && obj instanceof Book) {
                Book book = (Book) obj;
                params.put("type", DataType.BOOK);
                params.put("id", book.getId());
            }
            if (obj != null && obj instanceof Chapter) {
                 Chapter chapter = (Chapter) obj;
                 params.put("type", DataType.CHAPTER);
                 params.put("id", chapter.getId());
                
            }
            return params;
        }
                
                
           
        protected TreeModel convertToTreeModel(List<Bibletranslation> fetchedBibleTranslations) {


            TreeModel model = null;
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Versions");


            if (fetchedBibleTranslations != null && !fetchedBibleTranslations.isEmpty()) {
                addTranslations(rootNode, fetchedBibleTranslations);
            }
            model = new DefaultTreeModel(rootNode);
            return model;
        }

        private void addTranslations(DefaultMutableTreeNode parent, List<Bibletranslation> sub) {
            for (Iterator<Bibletranslation> transLationIt = sub.iterator(); transLationIt.hasNext();) {
                final Bibletranslation trans = transLationIt.next();
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(trans);
                parent.add(child);

                sections = bibleTranslationDataRetrievalService.findBookSectionByBibleTranslationId(trans.getId());


                if (sections != null && !sections.isEmpty()) {
                    addBooksectionSections(child, sections);
                }
            }
        }

        private void addBooksectionSections(DefaultMutableTreeNode parent, List<Booksection> sub) {
            for (Iterator<Booksection> bookSectionIt = sub.iterator(); bookSectionIt.hasNext();) {
                final Booksection section = bookSectionIt.next();
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(section);
                parent.add(child);
                books = bibleTranslationDataRetrievalService.findBooksByBooksectionId(section.getId());
                if (books != null && !books.isEmpty()) {
                    addBooks(child, books);
                }
            }
        }

        private void addBooks(DefaultMutableTreeNode parent, List<Book> sub) {
            for (Iterator<Book> bookIt = sub.iterator(); bookIt.hasNext();) {
                final Book book = bookIt.next();
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(book);
                parent.add(child);
                chapters = bibleTranslationDataRetrievalService.findChaptersByBookId(book.getId());

                if (chapters != null && !chapters.isEmpty()) {
                    addChapters(child, bibleTranslationDataRetrievalService.findChaptersByBookId(book.getId()));
                }
            }
        }

        private void addChapters(DefaultMutableTreeNode parent, List<Chapter> sub) {
            for (Iterator<Chapter> chapterIt = sub.iterator(); chapterIt.hasNext();) {
                Chapter chapter = chapterIt.next();
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(chapter);
                parent.add(child);
            /*if (book.getChapters()!= null) {
            
            }*/
            }
        }
    }
}
