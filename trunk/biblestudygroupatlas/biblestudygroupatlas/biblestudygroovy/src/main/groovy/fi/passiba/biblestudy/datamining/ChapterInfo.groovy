package fi.passiba.biblestudy.datamining

class ChapterInfo {

    
    String subtitle
    String number
    String text

    List verses
    ChapterInfo () 
    {
      verses = new ArrayList()
    }
    public List getVerses()
    {
       return verses
    }
    public String getText()
    {
       return text
    }
    public String getSubTitle()
    {

       return subtitle
    }
    public String getNumber()
    {
       return number
    }
     

}
