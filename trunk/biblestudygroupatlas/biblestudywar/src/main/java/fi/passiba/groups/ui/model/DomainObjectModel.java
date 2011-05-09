/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.model;

import fi.passiba.hibernate.DomainObject;
import java.io.Serializable;
import org.apache.wicket.injection.web.InjectorHolder;

/**
 *
 * @author haverinen
 */
public class DomainObjectModel<T extends DomainObject> implements Serializable{

    
    private final Class<T> type;

 
  public DomainObjectModel(Class<T> type) {
    InjectorHolder.getInjector().inject(this);
    this.type = type;
  }

  @SuppressWarnings("unchecked")
  public DomainObjectModel(T domainObject) {
    super();
    InjectorHolder.getInjector().inject(this);
    this.type = (Class<T>) domainObject.getClass();
   
  }

}
