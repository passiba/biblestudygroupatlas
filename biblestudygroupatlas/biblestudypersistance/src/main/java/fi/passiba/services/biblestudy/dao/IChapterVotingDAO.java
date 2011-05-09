package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDao;


import fi.passiba.services.biblestudy.persistance.ChapterVoting;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for ChapterVotingDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IChapterVotingDAO extends  BaseDao<ChapterVoting>{

  @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
  public List<ChapterVoting> findTopRatedChapters();

  @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
  public ChapterVoting findRatingByChapterid(long id);

}