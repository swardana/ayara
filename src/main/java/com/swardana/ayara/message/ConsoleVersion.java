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
 * Display the ayara build version into command-line interface (shell).
 *
 * @author Sukma Wardana
 */
public class ConsoleVersion implements Version {

    /**
     * Creates new ConsoleVersion.
     */
    public ConsoleVersion() {}

    @Override
    public final void print() {
        var content = """
            Ayara %s (%s), %s
            
            Copyright (C) %s %s.
            License GPLv3+: GNU GPL version 3 or later
            <http://www.gnu.org/licenses/gpl.html>.
            This is free software: you are free to change and redistribute it.
            There is NO WARRANTY, to the extent permitted by law.
            
            Written by Sukma Wardana <swardana@tutanota.com>.
            Please send bug reports and questions to <swardana@tutanota.com>.
            """.formatted(
                BuildVersion.getInstance().buildVersion(),
                BuildVersion.getInstance().buildNumber(),
                BuildVersion.getInstance().desc(),
                BuildVersion.getInstance().copyright(),
                BuildVersion.getInstance().author()
            );
        System.out.println(content);
        System.exit(0);
    }

}
