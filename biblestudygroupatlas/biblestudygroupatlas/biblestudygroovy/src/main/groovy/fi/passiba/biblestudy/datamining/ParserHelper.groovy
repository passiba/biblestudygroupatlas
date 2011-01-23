package fi.passiba.biblestudy.datamining

class ParserHelper {

   
    ParserHelper () 
   {
   }

    List  readParsedBookDataSources(outPutFile) 
    {
       
       List chapters =new ArrayList()
      
       def currentFile = new File(outPutFile)
       //def files = basedir.listFiles().grep(~/.*xml$/)
      // for (currentFile in files) 
      // {
            def chapteXmlr=new XmlSlurper().parse(currentFile)
            chapteXmlr.chapter.each 
            {
               
                def chapterinfo = new ChapterInfo()
                chapterinfo.subtitle = it.subtitle.text()
                chapterinfo.number = it.number.text()
                chapterinfo.text = it.text.text()
                chapters.add(chapterinfo)
            }
            
        
       return  chapters
    }
}
