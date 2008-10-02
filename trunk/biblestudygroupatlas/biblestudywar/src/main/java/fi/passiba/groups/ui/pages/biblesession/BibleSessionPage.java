package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.groups.ui.pages.*;
import org.apache.wicket.PageParameters;


public class BibleSessionPage extends BasePage {

   
    private Object obj;
    
    public BibleSessionPage(PageParameters parameters) {
		initPage(parameters);
		add(new BibleSessionPanel("bibleSessionViewpanel",obj));
	}
	protected void initPage(PageParameters parameters) {
		obj=parameters.get("viewObject");
	}
    
       

    

   
}
