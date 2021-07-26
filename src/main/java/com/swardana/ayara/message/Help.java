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
 * The ayara usage help.
 *
 * @author Sukma Wardana
 */
public interface Help {

    /**
     * Print the usage help information and exit.
     * <p>
     *     After successfully display the usage help, it will exit from the
     *     ayara app with exit status 0 (zero).
     * </p>
     */
    void print();

}