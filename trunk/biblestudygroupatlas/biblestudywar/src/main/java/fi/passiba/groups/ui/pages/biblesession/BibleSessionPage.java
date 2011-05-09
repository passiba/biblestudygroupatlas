package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.groups.ui.pages.*;
import org.apache.wicket.PageParameters;


public class BibleSessionPage extends BasePage {

   
   
    
    public BibleSessionPage(PageParameters parameters) {
        
                String type= (String)parameters.get("type");
                Long itemid=Long.parseLong((String)parameters.get("id"));
		add(new BibleSessionPanel("bibleSessionViewpanel",type,itemid));
	}
}
