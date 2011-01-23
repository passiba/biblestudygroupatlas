/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 as published by
 * the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * Copyright: 2005
 *     The copyright to this program is held by it's authors.
 *
 * ID: $Id: AbstractSwordInstaller.java 1877 2008-06-18 20:22:45Z dmsmith $
 */
package fi.passiba.services.bibledata.sword;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.crosswire.common.progress.Progress;
import org.crosswire.common.util.CWProject;
import org.crosswire.common.util.CollectionUtil;
import org.crosswire.common.util.IOUtil;
import org.crosswire.common.util.Logger;
import org.crosswire.common.util.NetUtil;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookDriver;
import org.crosswire.jsword.book.BookFilter;
import org.crosswire.jsword.book.BookFilterIterator;
import org.crosswire.jsword.book.BookMetaData;
import org.crosswire.jsword.book.BookSet;
import org.crosswire.jsword.book.basic.AbstractBookList;
import org.crosswire.jsword.book.install.InstallException;
import org.crosswire.jsword.book.install.Installer;
import org.crosswire.jsword.book.sword.ConfigEntry;
import org.crosswire.jsword.book.sword.SwordBook;
import org.crosswire.jsword.book.sword.SwordBookDriver;
import org.crosswire.jsword.book.sword.SwordBookMetaData;
import org.crosswire.jsword.book.sword.SwordBookPath;
import org.crosswire.jsword.book.sword.SwordConstants;

import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;
import fi.passiba.hibernate.DomainObject;
import org.crosswire.jsword.book.BookException;

/**
 * .
 *
 * @see gnu.lgpl.License for license details.
 *      The copyright to this program is held by it's authors.
 * @author Joe Walker [joe at eireneh dot com]
 * @author DM Smith [dmsmith555 at yahoo dot com]
 */
public abstract class AbstractSwordInstaller extends AbstractBookList implements DomainObject, Installer, Comparable
{



    public enum ProcessStatus {

        INIT("INITIALIZING"),DOWNLOADING("BIBLEDATADOWNLOADING"), INSTALLING("BIBLEBOOKDATAINSTALLING");
        private String type;

        private ProcessStatus(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }










    /**
     * Utility to download a file from a remote site
     * @param job The way of noting progress
     * @param dir The directory from which to download the file
     * @param file The file to download
     * @throws InstallException
     */
    protected abstract void download(String processStatus, String dir, String file, URI dest) throws InstallException;

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.install.Installer#getInstallerDefinition()
     */
    public String getInstallerDefinition()
    {
        StringBuffer buf = new StringBuffer(host);
        buf.append(',');
        buf.append(packageDirectory);
        buf.append(',');
        buf.append(catalogDirectory);
        buf.append(',');
        buf.append(indexDirectory);
        buf.append(',');
        if (proxyHost != null)
        {
            buf.append(proxyHost);
        }
        buf.append(',');
        if (proxyPort != null)
        {
            buf.append(proxyPort);
        }
        return buf.toString();
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.install.Installer#isNewer(org.crosswire.jsword.book.BookMetaData)
     */
    public boolean isNewer(Book book)
    {
        File dldir = SwordBookPath.getSwordDownloadDir();

        SwordBookMetaData sbmd = (SwordBookMetaData) book.getBookMetaData();
        File conf = new File(dldir, sbmd.getConfPath());

        // The conf may not exist in our download dir.
        // In this case we say that it should not be downloaded again.
        if (!conf.exists())
        {
            return false;
        }

        URI configURI = NetUtil.getURI(conf);

        URI remote = toRemoteURI(book);
        return NetUtil.isNewer(remote, configURI, proxyHost, proxyPort);
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookList#getBooks()
     */
    public List getBooks()
    {
        try
        {
            if (!loaded)
            {
                loadCachedIndex();
            }

            // We need to create a List from the Set returned by
            // entries.values() so the underlying list is not modified.
            return new ArrayList(entries.values());
        }
        catch (InstallException ex)
        {
            log.error("Failed to reload cached index file", ex); //$NON-NLS-1$
            return new ArrayList();
        }
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookList#getBook(java.lang.String)
     */
    public Book getBook(String name)
    {
        // Check name first
        // First check for exact matches
        List books = getBooks();
        Iterator iter = books.iterator();
        while (iter.hasNext())
        {
            Book book = (Book) iter.next();
            if (name.equals(book.getName()))
            {
                return book;
            }
        }

        // Next check for case-insensitive matches
        iter = books.iterator();
        while (iter.hasNext())
        {
            Book book = (Book) iter.next();
            if (name.equalsIgnoreCase(book.getName()))
            {
                return book;
            }
        }

        // Then check initials
        // First check for exact matches
        iter = books.iterator();
        while (iter.hasNext())
        {
            Book book = (Book) iter.next();
            BookMetaData bmd = book.getBookMetaData();
            if (name.equals(bmd.getInitials()))
            {
                return book;
            }
        }

        // Next check for case-insensitive matches
        iter = books.iterator();
        while (iter.hasNext())
        {
            Book book = (Book) iter.next();
            if (name.equalsIgnoreCase(book.getInitials()))
            {
                return book;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookList#getBooks(org.crosswire.jsword.book.BookFilter)
     */
    public  List getBooks(BookFilter filter)
    {
        List temp = CollectionUtil.createList(new BookFilterIterator(getBooks(), filter));
        return new BookSet(temp);
    }
    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.install.Installer#install(org.crosswire.jsword.book.Book)
     */
    public void install(Book book)
    {

        final SwordBookMetaData sbmd = (SwordBookMetaData) book.getBookMetaData();

               URI predictURI = CWProject.instance().getWritablePropertiesURI("sword-install"); //$NON-NLS-1$
                try
                {
                   // job.setSectionName(UserMsg.JOB_INIT.toString());

                    URI temp = NetUtil.getTemporaryURI("swd", ZIP_SUFFIX); //$NON-NLS-1$

                    download(ProcessStatus.INIT.getType(), packageDirectory, sbmd.getInitials() + ZIP_SUFFIX, temp);

                    // Once the unzipping is started, we need to continue
                    File dldir = SwordBookPath.getSwordDownloadDir();
                    IOUtil.unpackZip(NetUtil.getAsFile(temp), dldir);
                    sbmd.setLibrary(NetUtil.getURI(dldir));
                    SwordBookDriver.registerNewBook(sbmd);

                }
                catch (IOException e)
                {
                   e.printStackTrace();
                   // job.cancel();
                }
                catch (InstallException e)
                {
                     e.printStackTrace();
                   // job.cancel();
                }
                catch (BookException e)
                {
                    e.printStackTrace();
                   // job.cancel();
                }
                finally
                {
                   // job.done();
                }
           // }
        //};

        // this actually starts the thread off
       // worker.setPriority(Thread.MIN_PRIORITY);
       // worker.start();
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.install.Installer#reloadIndex()
     */
    public void reloadBookList() throws InstallException
    {
        
        try
        {
            URI scratchfile = getCachedIndexFile();
            download(ProcessStatus.DOWNLOADING.getType(), catalogDirectory, FILE_LIST_GZ, scratchfile);
            loaded = false;
        }
        catch (InstallException ex)
        {
          
            throw ex;
        }
        finally
        {
            //job.done();
        }
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.install.Installer#downloadSearchIndex(org.crosswire.jsword.book.BookMetaData, java.net.URI)
     */
    public void downloadSearchIndex(Book book, URI localDest) throws InstallException
    {
       // Progress job = JobManager.createJob(UserMsg.JOB_DOWNLOADING.toString(), Thread.currentThread(), false);

        try
        {
          download(ProcessStatus.DOWNLOADING.getType(), packageDirectory + '/' + SEARCH_DIR, book.getInitials() + ZIP_SUFFIX, localDest);
        }
       
        finally
        {
           // job.done();
        }
    }

    /**
     * Load the cached index file into memory
     */
    private void loadCachedIndex() throws InstallException
    {
        // We need a sword book driver so the installer can use the driver
        // name to use in deciding where to put the index.
        BookDriver fake = SwordBookDriver.instance();

        entries.clear();

        URI cache = getCachedIndexFile();
        if (!NetUtil.isFile(cache))
        {
            reloadBookList();
        }

        InputStream in = null;
        GZIPInputStream gin = null;
        TarInputStream tin = null;
        try
        {
            ConfigEntry.resetStatistics();

            in = NetUtil.getInputStream(cache);
            gin = new GZIPInputStream(in);
            tin = new TarInputStream(gin);
            while (true)
            {
                TarEntry entry = tin.getNextEntry();
                if (entry == null)
                {
                    break;
                }

                String internal = entry.getName();
                if (!entry.isDirectory())
                {
                    try
                    {
                        int size = (int) entry.getSize();

                        // Every now and then an empty entry sneaks in
                        if (size == 0)
                        {
                            log.error("Empty entry: " + internal); //$NON-NLS-1$
                            continue;
                        }

                        byte[] buffer = new byte[size];
                        if (tin.read(buffer) != size)
                        {
                            // This should not happen, but if it does then skip it.
                            log.error("Did not read all that was expected " + internal); //$NON-NLS-1$
                            continue;
                        }

                        if (internal.endsWith(SwordConstants.EXTENSION_CONF))
                        {
                            internal = internal.substring(0, internal.length() - 5);
                        }
                        else
                        {
                            log.error("Not a SWORD config file: " + internal); //$NON-NLS-1$
                            continue;
                        }

                        if (internal.startsWith(SwordConstants.DIR_CONF + '/'))
                        {
                            internal = internal.substring(7);
                        }

                        SwordBookMetaData sbmd = new SwordBookMetaData(buffer, internal);
                        sbmd.setDriver(fake);
                        Book book = new SwordBook(sbmd, null);
                        entries.put(book.getName(), book);
                    }
                    catch (IOException ex)
                    {
                        log.error("Failed to load config for entry: " + internal, ex); //$NON-NLS-1$
                    }
                }
            }

            loaded = true;

            ConfigEntry.dumpStatistics();
        }
        catch (IOException ex)
        {
            throw new InstallException(Msg.CACHE_ERROR, ex);
        }
        finally
        {
            IOUtil.close(tin);
            IOUtil.close(gin);
            IOUtil.close(in);
        }
    }

    /**
     * @return the catologDirectory
     */
    public String getCatalogDirectory()
    {
        return catalogDirectory;
    }

    /**
     * @param catologDirectory the catologDirectory to set
     */
    public void setCatalogDirectory(String catologDirectory)
    {
        this.catalogDirectory = catologDirectory;
    }

    /**
     * @return Returns the directory.
     */
    public String getPackageDirectory()
    {
        return packageDirectory;
    }

    /**
     * @param newDirectory The directory to set.
     */
    public void setPackageDirectory(String newDirectory)
    {
        if (packageDirectory == null || !packageDirectory.equals(newDirectory))
        {
            packageDirectory = newDirectory;
            loaded = false;
        }
    }

    /**
     * @return the indexDirectory
     */
    public String getIndexDirectory()
    {
        return indexDirectory;
    }

    /**
     * @param indexDirectory the indexDirectory to set
     */
    public void setIndexDirectory(String indexDirectory)
    {
        this.indexDirectory = indexDirectory;
    }

    /**
     * @return Returns the host.
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @param newHost The host to set.
     */
    public void setHost(String newHost)
    {
        if (host == null || !host.equals(newHost))
        {
            host = newHost;
            loaded = false;
        }
    }

    /**
     * @return Returns the proxyHost.
     */
    public String getProxyHost()
    {
        return proxyHost;
    }

    /**
     * @param newProxyHost The proxyHost to set.
     */
    public void setProxyHost(String newProxyHost)
    {
        String pHost = null;
        if (newProxyHost != null && newProxyHost.length() > 0)
        {
            pHost = newProxyHost;
        }
        if (proxyHost == null || !proxyHost.equals(pHost))
        {
            proxyHost = pHost;
            loaded = false;
        }
    }

    /**
     * @return Returns the proxyPort.
     */
    public Integer getProxyPort()
    {
        return proxyPort;
    }

    /**
     * @param newProxyPort The proxyPort to set.
     */
    public void setProxyPort(Integer newProxyPort)
    {
        if (proxyPort == null || !proxyPort.equals(newProxyPort))
        {
            proxyPort = newProxyPort;
            loaded = false;
        }
    }

    /**
     * The URL for the cached index file for this installer
     */
    protected URI getCachedIndexFile() throws InstallException
    {
        try
        {
            URI scratchdir = CWProject.instance().getWriteableProjectSubdir(getTempFileExtension(host, catalogDirectory), true);
            return NetUtil.lengthenURI(scratchdir, FILE_LIST_GZ);
        }
        catch (IOException ex)
        {
            throw new InstallException(Msg.URL_FAILED, ex);
        }
    }

    /**
     * What are we using as a temp filename?
     */
    private static String getTempFileExtension(String host, String catalogDir)
    {
        return DOWNLOAD_PREFIX + host + catalogDir.replace('/', '_');
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /* @Override */
    public boolean equals(Object object)
    {
        if (!(object instanceof AbstractSwordInstaller))
        {
            return false;
        }
        AbstractSwordInstaller that = (AbstractSwordInstaller) object;

        if (!equals(this.host, that.host))
        {
            return false;
        }

        if (!equals(this.packageDirectory, that.packageDirectory))
        {
            return false;
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object arg0)
    {
        HttpSwordInstaller myClass = (HttpSwordInstaller) arg0;

        int ret = host.compareTo(myClass.host);
        if (ret != 0)
        {
            ret = packageDirectory.compareTo(myClass.packageDirectory);
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /* @Override */
    public int hashCode()
    {
        return host.hashCode() + packageDirectory.hashCode();
    }

    /**
     * Quick utility to check to see if 2 (potentially null) strings are equal
     */
    protected boolean equals(String string1, String string2)
    {
        if (string1 == null)
        {
            return string2 == null;
        }
        return string1.equals(string2);
    }

    /**
     * A map of the entries in this download area
     */
    protected Map entries = new HashMap();

    /**
     * The remote hostname.
     */
    protected String host;

    /**
     * The remote proxy hostname.
     */
    protected String proxyHost;

    /**
     * The remote proxy port.
     */
    protected Integer proxyPort;

    /**
     * The directory containing zipped books on the <code>host</code>.
     */
    protected String packageDirectory = ""; //$NON-NLS-1$

    /**
     * The directory containing the catalog of all books on the <code>host</code>.
     */
    protected String catalogDirectory = ""; //$NON-NLS-1$

    /**
     * The directory containing the catalog of all books on the <code>host</code>.
     */
    protected String indexDirectory = ""; //$NON-NLS-1$

    /**
     * Do we need to reload the index file
     */
    protected boolean loaded;

    /**
     * The sword index file
     */
    protected static final String FILE_LIST_GZ = "mods.d.tar.gz"; //$NON-NLS-1$

    /**
     * The suffix of zip books on this server
     */
    protected static final String ZIP_SUFFIX = ".zip"; //$NON-NLS-1$

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(AbstractSwordInstaller.class);

    /**
     * The relative path of the dir holding the search index files
     */
    protected static final String SEARCH_DIR = "search/jsword/L1"; //$NON-NLS-1$

    /**
     * When we cache a download index
     */
    protected static final String DOWNLOAD_PREFIX = "download-"; //$NON-NLS-1$

}
