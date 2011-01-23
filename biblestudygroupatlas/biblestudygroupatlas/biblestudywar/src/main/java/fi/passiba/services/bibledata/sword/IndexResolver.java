/**
 * Distribution License:
 * BibleDesktop is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License, version 2 as published by
 * the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/gpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * Copyright: 2005
 *     The copyright to this program is held by it's authors.
 *
 * ID: $Id: IndexResolver.java 1850 2008-05-10 18:34:02Z dmsmith $
 */
package fi.passiba.services.bibledata.sword;

import fi.passiba.services.bibledata.sword.index.IndexManagerFactory;



import org.crosswire.common.util.Logger;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.install.Installer;


/**
 * A class to prompt the user to download or create a search index and to do
 * carry out the users wishes.
 *
 * @see gnu.gpl.License for license details.
 *      The copyright to this program is held by it's authors.
 * @author Joe Walker [joe at eireneh dot com]
 */
public final class IndexResolver
{
    /**
     * Prevent instantiation
     */
    private IndexResolver()
    {
    }

    public static void scheduleIndex(Book book, Installer installer)
    {

            IndexManagerFactory.getIndexManager().scheduleIndexCreation(book);    
    }

   

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(IndexResolver.class);
}
