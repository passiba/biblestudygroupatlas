#Development environment setup intructions.

# Introduction #

Setup environment instructions in order to run biblestudygroup atlas application on your pc


# Details #

Step-by-step instructions:
  * Download maven http://maven.apache.org/download.html
  * Download svn client - tortoisesvn will do...http://tortoisesvn.tigris.org/



  * Checkout thirdparty modules - wicket stuff modules from wicketstuff-core-1.4.9.2 branch - needed  module - gmap2

https://wicket-stuff.svn.sourceforge.net/svnroot/wicket-stuff/tags/wicketstuff-core-1.4.9.2/

**Checkout thirdparty modules from svn repository https://crosswire.org/svn/jsword/trunk
> -needed modules are common and jsword**

**Install each maven module depenency using maven install command
> mvn install:install-file -DgroupId=org.webharvest -DartifactId=webharvest    -Dversion=1.0 -Dpackaging=jar -Dfile=/path/to/file**

> mvn install:install-file -DgroupId=org.crosswire -DartifactId=jsword -Dversion=1.6 -Dpackaging=jar -Dfile=/path/to/file

> mvn install:install-file -DgroupId=org.crosswire -DartifactId=jsword-common -Dversion=1.6 -Dpackaging=jar -Dfile=/path/to/file