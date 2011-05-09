package fi.passiba.biblestudy.restlet.resource.xml

import groovy.xml.MarkupBuilder

class BibleReporter {
    final def BASE_URL = "http://localhost:8084/biblestudy-1.0.0/biblestudy/"

    String bibletranslationToXml(ibibletranslation) {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.setDoubleQuotes true
        builder."passiba-bibleversions"() {
            def iden = ibibletranslation.id
            def desc = ibibletranslation.bibleName
            bibleversion(name: ibibletranslation.bibleName, publisheddate: ibibletranslation.publishedDate, publisher: ibibletranslation.publisherName, id: ibibletranslation.id) {
                uri("${BASE_URL}/${iden}")
                description(desc)
            }
        }
        return writer.toString()
    }

    String booksectionToXml(ibooksection) {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.setDoubleQuotes true
        builder."passiba-bibleversions"() {
            sections() {
                ibooksection.each {
                    def iden = ibooksection.id
                    def desc = ibooksection.section
                    section(name: ibooksection.section, id:ibooksection.id) {
                        uri("${BASE_URL}/${iden}")
                        description(desc)
                    }
                }
            }
        }
        return writer.toString()
    }
     String bookToXml(ibook) {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.setDoubleQuotes true
        builder."passiba-sections"() {
              books() {
                    ibook.each {
                    def iden = ibook.id
                    def desc = ibook.bookText
                    section(name: ibook.bookText, id:ibook.id, booknumber:ibook.bookNum) {
                        uri("${BASE_URL}/${iden}")
                        description(desc)
                    }
                }
            }
        }
        return writer.toString()
    }
      String chapterToXml(ichapter) {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.setDoubleQuotes true
        builder."passiba-books"() {
              chapters() {
                    ichapter.each {
                    def iden = ichapter.id
                    def desc = ichapter.chapterTitle
                    section(title: ichapter.chapterTitle, id: ichapter.id, number:ichapter.chapterNum) {
                        uri("${BASE_URL}/${iden}")
                        description(desc)
                    }
                }
            }
        }
        return writer.toString()
    }
     String verseToXml(iverse) {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.setDoubleQuotes true
        builder."passiba-chapters"() {
              verses() {
                    iverse.each {
                    def iden = iverse.id
                    def desc = iverse.bookText
                    section(text: iverse.verseText, verseNumber:iverse.verseNum, verseNumid:iverse.id) {
                        uri("${BASE_URL}/${iden}")
                        description(desc)
                    }
                }
            }
        }
        return writer.toString()
    }
    String bibletranslationsToXml(ibibletranslations) {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.setDoubleQuotes true
        builder."passiba-bibleversions"() {
            bibleversion() {
                ibibletranslations.each {
                   
								
                 def iden = ibibletranslations.id
                 def desc = ibibletranslations.bibleName
                 bibleversion(name: ibibletranslations.bibleName, publisheddate: ibibletranslations.publishedDate, publisher: ibibletranslations.publisherName, id: ibibletranslations.id) {
                    uri("${BASE_URL}/${iden}")
                    description(desc)
                  }






                    
                }
            }
        }
        return writer.toString()
    }
    


}
