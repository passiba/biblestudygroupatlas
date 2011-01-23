package fi.passiba.hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.search.annotations.DocumentId;


@MappedSuperclass
public class BaseEntity implements DomainObject,Identifiable{
	private Long id;
    @DocumentId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
        
    
}
