/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.persistance;

import fi.passiba.hibernate.AuditableEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 *
 * @author haverinen
 */
@Entity
@Table(name = "users")
@AttributeOverride( name="id", column = @Column(name="userid") )
public class Users  extends  AuditableEntity {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "username", nullable = false,unique=true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
   
  
    @Column(name="status")
    private String status;
      
     
    @Column(name = "rolename")
    private String rolename;

    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
   
    
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Users other = (Users) obj;

        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }


}

    
