/**
 * 
 */
package fi.passiba.biblestudy.datamining

import java.util.List
//import junit.framework.TestCase
import  fi.passiba.biblestudy.datamining.ParserHelper
import  fi.passiba.biblestudy.datamining.ParserHelper

/**
 * @author pahaveri
 *
 */
public class ParserHelperTest{
	
	private ParserHelper parseHelper
        /* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	void setUp() throws Exception{
             parseHelper=new ParserHelper();
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	void tearDown() throws Exception{
	}
	
	/* (non-Javadoc)
	 * @see fi.passiba.biblestudy.datamining.ParserHelper#readParesdBookDataSources(java.lang.Object)
	 */
	void testReadParesdBookDataSources(){
                 def file="C:/temp/bible/chapters/Mark.xml"
		 List chapters=parseHelper.readParsedBookDataSources(file)

                  chapters.each() {
                    println "subtitle :" +it.getSubTitle() + " text :" + it.getText()


                }
	}
	
}
