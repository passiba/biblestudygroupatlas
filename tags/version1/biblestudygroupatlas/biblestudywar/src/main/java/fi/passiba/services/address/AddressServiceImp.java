/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.address;

import fi.passiba.services.dao.IAddressDAO;
import fi.passiba.services.persistance.Adress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author haverinen
 */
@Service("IAddressService")
public class AddressServiceImp implements IAddressService{

    
    
    @Autowired
    private IAddressDAO addressDAO;

    public Adress  findAddressByAddressId(Long id) {
         return this.addressDAO.getById(id);
    }
    
    

   

    
  
}
