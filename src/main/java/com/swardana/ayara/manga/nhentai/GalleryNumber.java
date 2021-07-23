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

package com.swardana.ayara.manga.nhentai;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * The nhentai.net media gallery number identity.
 *
 * @author Sukma Wardana
 */
class GalleryNumber {

    private static final String CONTENT = "content";
    private static final String ITEM_PROP = "itemprop";
    private static final String IMAGE = "image";

    private final Document document;

    /**
     * Creates new GalleryNumber.
     *
     * @param number the Hentai manga magic number.
     * @throws IOException if fail connecting to nhentai.net website.
     */
    GalleryNumber(final String number) throws IOException {
        this(
            Jsoup.connect(
                Nhentai.WEBSITE_URL
                    .concat(number)
                    .concat("/")
            ).get()
        );
    }

    /**
     * Creates new GalleryNumber.
     *
     * @param document the HTML document page.
     */
    GalleryNumber(final Document document) {
        this.document = document;
    }

    /**
     * String representation of the gallery number.
     *
     * @return the gallery number as String.
     */
    String asString() {
        return this.number(this.cover());
    }

    /**
     * Crawl the meta cover information from HTML document.
     * <p>
     *
     * @return the url for cover meta-data.
     */
    private String cover() {
        var result = new StringBuilder();
        var elements = this.document.getElementsByTag("meta");
        for (final var element : elements) {
            String content = element.attr(CONTENT);
            String prop = element.attr(ITEM_PROP);

            if (IMAGE.equals(prop)) {
                result.append(content);
                break;
            }

        }
        return result.toString();
    }

    /**
     * Fetch the gallery identity from the given url.
     * <p>
     * Require special URL that have the gallery identity information.
     *
     * @param src the source of gallery identity information.
     * @return the gallery number.
     */
    private String number(final String src) {
        String[] temp = src.split("/");
        return temp[temp.length - 2];
    }

}
