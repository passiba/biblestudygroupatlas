package fi.passiba.services.biblestudy.feed;


import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import fi.passiba.biblestudy.feed.IGroupFeedResource;
import fi.passiba.services.group.persistance.Groups;


public class GroupFeedTest extends   AbstractDependencyInjectionSpringContextTests  {
    
	
	private String groupType = "Miestenpiiri",  city = "Espoo",  country = "Finland",username="habbo";

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:META-INF/biblestudy-webservice.xml","classpath:client-beans.xml" };
	}
	
	public void testRetrieveBibleStudyGroupFeed() throws Exception{
		IGroupFeedResource bibleStudyGroupFeedService = (IGroupFeedResource) applicationContext.getBean("biblestudyGroupClient");
		List<Groups> groups= bibleStudyGroupFeedService.findBibleStudyGroupsByLocation(country, city, groupType);
      
    }


}
