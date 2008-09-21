package fi.passiba.hibernate;



import java.util.List;

public interface BaseDao<B extends Identifiable> {
    B getNewInstance();

    List<B> getAll();

    B getById(Long id);

    void save(B obj);

    void update(B obj);

    void saveOrUpdate(B obj);

    void delete(B obj);

    int getCountAll();

    int getCountByExample(B example);

    List<B> getPageOfDataAll(PaginationInfo pageInfo);

    List<B> getPageOfDataByExample(B example, PaginationInfo pageInfo);

    List<B> getByExample(B example);
}
