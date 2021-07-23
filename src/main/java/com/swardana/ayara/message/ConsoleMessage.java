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

/**
 * Display the ayara-cli message into command-line interface (shell).
 *
 * @author Sukma Wardana
 */
public class ConsoleMessage implements Message {

    /**
     * Creates new ConsoleMessage.
     */
    public ConsoleMessage() {}

    @Override
    public final void print(final String message, final int status) {
        var content = """
            ayara: %s
            
            Usage: ayara [OPTION]... [NUMBER]
            
            Try `ayara --help` for more options
            """.formatted(message);
        System.out.println(content);
        System.exit(status);
    }
}
