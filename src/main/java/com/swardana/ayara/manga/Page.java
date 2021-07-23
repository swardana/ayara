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

package com.swardana.ayara.manga;

import java.io.IOException;
import java.io.InputStream;

/**
 * A hentai manga page.
 *
 * @author Sukma Wardana
 */
public interface Page {

    /**
     * The name of this page.
     * <p>
     *     Represent the single page name of the hentai manga. It could be as
     *     page number as well since page was mostly an image. The name
     *     <b>MUST</b> include the extension of the data type.
     * </p>
     *
     * @return the page name.
     */
    String name();

    /**
     * Read byte data stream of this page.
     *
     * @return the byte data stream.
     * @throws IOException if fail to read this page byte data stream.
     */
    InputStream read() throws IOException;

}
