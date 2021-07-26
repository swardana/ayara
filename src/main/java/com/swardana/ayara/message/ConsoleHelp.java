/*
 * Ayara, non-interactive nhentai.net manga downloader
 * Copyright (C) 2021  Sukma Wardana
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.swardana.ayara.message;

import com.swardana.ayara.BuildVersion;

/**
 * Display the ayara usage help into command-line interface (shell).
 *
 * @author Sukma Wardana
 */
public class ConsoleHelp implements Help {

    /**
     * Creates new ConsoleHelp.
     */
    public ConsoleHelp() {}

    @Override
    public final void print() {
        var content = """
            Ayara %s, %s
            Usage: ayara [OPTION]... [NUMBER]
            
            Arguments
            
              number               The hentai manga digit code number
            
            Mandatory arguments to long options are mandatory for short options too.
            
            Startup:
              -h, --help           Show this help and exit
              -V, --version        Show version number and exit
            
            Download:
              -c, --continue       Resume getting a partially-downloaded file
            
            Directories:
              -O, --output-directory DIRECTORY
                                   Download hentai manga to DIRECTORY
              -x, --force-directories
                                   Force creation of directories
            
            Email bug reports, questions, discussions to <swardana@tutanota.com>.
            """.formatted(
                BuildVersion.getInstance().buildVersion(),
                BuildVersion.getInstance().desc()
            );
        System.out.println(content);
        System.exit(0);
    }

}
