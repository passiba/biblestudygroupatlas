/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 *
 * @author haverinen
 */
public abstract class AbstractDependencyInjectionSpringContextTest extends AbstractDependencyInjectionSpringContextTests {

     @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:applicationContext.xml"};
    }
}
