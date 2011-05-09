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
 * ID: $Id: Msg.java 1466 2007-07-02 02:48:09Z dmsmith $
 */
package fi.passiba.services.bibledata.sword;

import org.crosswire.common.util.MsgBase;

/**
 * Compile safe Msg resource settings.
 *
 * @see gnu.lgpl.License for license details.
 *      The copyright to this program is held by it's authors.
 * @author Joe Walker [joe at eireneh dot com]
 */
final class UserMsg extends MsgBase
{
    static final UserMsg INSTALLING = new UserMsg("SwordInstaller.Installing"); //$NON-NLS-1$
    static final UserMsg JOB_INIT = new UserMsg("SwordInstaller.JobInit"); //$NON-NLS-1$
    static final UserMsg JOB_CONFIG = new UserMsg("SwordInstaller.JobConfig"); //$NON-NLS-1$
    static final UserMsg JOB_DOWNLOADING = new UserMsg("SwordInstaller.JobDownloading"); //$NON-NLS-1$
    static final UserMsg MISSING_FILE = new UserMsg("HttpSwordInstaller.MissingFile"); //$NON-NLS-1$

    /**
     * Passthrough ctor
     */
    private UserMsg(String name)
    {
        super(name);
    }
}
