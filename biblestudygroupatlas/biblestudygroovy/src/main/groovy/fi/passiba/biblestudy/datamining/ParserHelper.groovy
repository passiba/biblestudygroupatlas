package fi.passiba.biblestudy.datamining

class ParserHelper {

   private List chapters
   private List verses 
    ParserHelper () 
   {
      chapters = new ArrayList()
   }

    List  readParesdBookDataSources(outPutFile) 
    {
       def currentFile = new File(outPutFile)
       //def files = basedir.listFiles().grep(~/.*xml$/)
      // for (currentFile in files) 
      // {
            def chapteXmlr=new XmlSlurper().parse(currentFile)
            chapteXmlr.chapter.each 
            {
                verses=new ArrayList()
                def chapterinfo = new ChapterInfo()
                chapterinfo.subtitle = it.subtitle.text()
                chapterinfo.number = it.number.text()
                chapterinfo.text = it.text.text()
                
                def verseinfo = new VerseInfo()
                char[] chars =  chapterinfo.text.toCharArray();
                StringBuilder versetext= new StringBuilder();

                for (int j = 0; j < chars.length; j++) {      
                	if(Character.isDigit(chars[j]))
       
                	{
          
                           if(j!=0 &&  verseinfo.text !=null && verseinfo.text.size()>0)
                           {
                        	  
                              verseinfo.text=versetext.toString()
                              verses.add(verseinfo)
                              versetext== new StringBuilder();
                           }
                           verseinfo = new VerseInfo() 
                           verseinfo.number=j+1
                           verseinfo.text=''
                        }
                		versetext.append(chars[j]);
             
                }                                       
                chapterinfo.verses=verses
                chapters.add(chapterinfo)
            }
            
        
       return  chapters
    }
}
