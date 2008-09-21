package fi.passiba.services.biblestudy.dao;



import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Book;
import java.util.List;

/**
 * Interface for BookDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBookDAO  extends  BaseDao<Book>{
    
    public List<Book>  findBooksByBookIDSectionIDandTranslationId( long translationid, long sectionid, long bookid);
	
}