package fi.passiba.groups;

import fi.passiba.services.biblestudy.dao.IBookDAO;
import fi.passiba.services.biblestudy.persistance.Book;

import java.util.List;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public final class BookServiceImpTest extends AbstractDependencyInjectionSpringContextTests {



    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:applicationContext.xml"};
    }


   
     public void testFindingBookByBookSectionId() throws Exception {
        IBookDAO bookDAO = (IBookDAO) applicationContext.getBean("BookDAO");
        List<Book> books = bookDAO.findBooksByBooksectionId(1);
        //assertEquals(26, books.size());
    }
     /*public void testFindingBookByDataSourceId() throws Exception {
        IBookDAO bookDAO = (IBookDAO) applicationContext.getBean("BookDAO");
        Book book = bookDAO.findBooksByBookDataSourcId(1);
        //assertEquals("Evankeliumi Matteuksen mukaan", book.getBookText());
    }*/
}
