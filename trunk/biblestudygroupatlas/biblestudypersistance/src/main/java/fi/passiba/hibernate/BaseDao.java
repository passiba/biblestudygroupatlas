package fi.passiba.hibernate;



import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

public interface BaseDao<B extends Identifiable> {
    B getNewInstance();
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    List<B> getAll();
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    B getById(Long id);
    @Transactional(propagation = Propagation.REQUIRED)
    void save(B obj);
    @Transactional(propagation = Propagation.REQUIRED)
    void update(B obj);
    @Transactional(propagation = Propagation.REQUIRED)
    void saveOrUpdate(B obj);
    @Transactional(propagation = Propagation.REQUIRED)
    void delete(B obj);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    int getCountAll();
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    int getCountByExample(B example);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    List<B> getPageOfDataAll(PaginationInfo pageInfo);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    List<B> getPageOfDataByExample(B example, PaginationInfo pageInfo);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    List<B> getByExample(B example);
}
