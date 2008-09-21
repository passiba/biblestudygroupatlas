/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.address;

import fi.passiba.services.dao.IAddressDAO;
import fi.passiba.services.persistance.Adress;

/**
 *
 * @author haverinen
 */
public class AddressServiceImp implements IAddressService{

    
    
    
    private IAddressDAO addressDAO= null;

    public IAddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(IAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
    
    
    
    public Adress  findAddressByAddressId(Long id) {
         return this.addressDAO.getById(id);
    }
    
    

   

    
  
}
