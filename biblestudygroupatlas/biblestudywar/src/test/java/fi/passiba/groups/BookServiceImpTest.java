package fi.passiba.groups;

import fi.passiba.AbstractTransactionalJUnit4SpringContext;
import fi.passiba.services.biblestudy.dao.IBookDAO;
import fi.passiba.services.biblestudy.persistance.Book;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

 @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
public final class BookServiceImpTest extends AbstractTransactionalJUnit4SpringContext {

     @Autowired
     private IBookDAO bookDAO;
    
     @Test
     public void testFindingBookByBookSectionId() throws Exception {
       
        List<Book> books = bookDAO.findBooksByBooksectionId(1);
        //assertEquals(66, books.size());
    }
     /*public void testFindingBookByDataSourceId() throws Exception {
        IBookDAO bookDAO = (IBookDAO) applicationContext.getBean("BookDAO");
        Book book = bookDAO.findBooksByBookDataSourcId(1);
        //assertEquals("Evankeliumi Matteuksen mukaan", book.getBookText());
    }*/
}
