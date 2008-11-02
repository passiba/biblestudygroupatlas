package fi.passiba.hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;



@MappedSuperclass
@Searchable(root=false)
public class BaseEntity implements DomainObject,Identifiable{
	private Long id;
    @SearchableId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
        
    
}
