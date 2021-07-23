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

import com.swardana.ayara.manga.Page;
import com.swardana.ayara.manga.Pages;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Hentai manga pages from nhentai.net.
 *
 * @author Sukma Wardana
 */
public class NhentaiPages implements Pages {

    private static final String QUERY = "a.gallerythumb > img";

    private final Document doc;
    private final GalleryNumber galleryId;

    private final List<Page> pages;

    /**
     * Creates new NhentaiPages.
     *
     * @param number the hentai manga digit code.
     * @throws IOException if fail connecting to nhentai website.
     */
    public NhentaiPages(final String number) throws IOException {
        this(
            Jsoup.connect(
                Nhentai.WEBSITE_URL
                    .concat(number)
                    .concat("/")
            ).get(),
            number
        );
    }

    /**
     * Creates new NhentaiPages.
     *
     * @param doc the HTML document page.
     * @param number the hentai manga digit code.
     */
    public NhentaiPages(final Document doc, final String number) throws IOException {
        this(doc, new GalleryNumber(number));
    }

    /**
     * Creates new JsoupPages.
     *
     * @param doc the HTML document page.
     * @param galleryId the hentai manga gallery number.
     */
    public NhentaiPages(final Document doc, final GalleryNumber galleryId) {
        this.doc = doc;
        this.galleryId = galleryId;
        this.pages = new ArrayList<>();
        this.query();
    }

    @Override
    public Iterator<Page> iterator() {
        return this.pages.iterator();
    }

    @Override
    public final int size() {
        return this.pages.size();
    }

    private void query() {
        var elements = this.doc.select(QUERY);
        for (final var element : elements) {
            this.pages.add(
                new NhentaiPage(
                    element.attr("data-src"),
                    this.galleryId
                )
            );
        }
    }

}
