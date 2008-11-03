/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.serices.search;

import fi.passiba.AbstractDependencyInjectionSpringContextTest;
import fi.passiba.services.search.ISearchService;

/**
 *
 * @author haverinen
 */
public class SearchServiceImpTest  extends AbstractDependencyInjectionSpringContextTest{

    public void testsearchingChapters() throws Exception
  {

        ISearchService searchService = (ISearchService) applicationContext.getBean("IAuthenticator");

        
        /*List<Person> persons=    authenticator.findPerson(username);
        Person fetchUser=null;
        for(Person per:persons)
        {
             fetchUser=person;
        }
        assert(person.getFk_userid().getUsername().equals(fetchUser.getFk_userid().getUsername()));*/
  }

}
