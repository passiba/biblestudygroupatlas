

package fi.passiba.services.address;

import fi.passiba.services.persistance.Adress;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author haverinen
 */
public interface IAddressService {
     
     
      @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public Adress findAddressByAddressId(Long id);

}
