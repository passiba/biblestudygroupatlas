/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 *
 * @author haverinen
 */

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public abstract class AbstractTransactionalJUnit4SpringContext extends AbstractTransactionalJUnit4SpringContextTests  {

    
}
