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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Hentai manga page from nhentai.net.
 *
 * @author Sukma Wardana
 */
public class NhentaiPage implements Page {

    private static final String DIGIT_AND_DECIMAL_REGEX = "[^\\d]";

    private String url;
    private GalleryNumber galleryId;

    /**
     * Creates new NhentaiPage.
     *
     * @param url the thumbnail url from nhentai website.
     * @param number the hentai manga digit code.
     * @throws IOException if fail to construct the page from nhentai.net
     * website.
     */
    public NhentaiPage(final String url, final String number) throws IOException {
        this(url, new GalleryNumber(number));
    }

    /**
     * Creates new NhentaiPage.
     *
     * @param url the thumbnail url from nhentai website.
     * @param galleryId the hentai manga gallery number.
     */
    public NhentaiPage(final String url, final GalleryNumber galleryId) {
        this.url = url;
        this.galleryId = galleryId;
    }

    @Override
    public final String name() {
        var base = this.name(this.url);
        var normalize = this.normalize(base);
        var ext = this.extension(base);
        return normalize.concat(ext);
    }

    @Override
    public final InputStream read() throws IOException {
        var target = Nhentai.GALLERY_URL
            .concat(this.galleryId.asString())
            .concat("/")
            .concat(this.name());
        return new URL(target).openStream();
    }

    private String name(final String src) {
        int lastIdx = src.lastIndexOf("/");
        if (lastIdx == -1) {
            throw new IllegalArgumentException("The source not contains slash (/)!");
        }
        return src.substring(lastIdx + 1);
    }

    private String normalize(final String src) {
        return src.replaceAll(DIGIT_AND_DECIMAL_REGEX, "");
    }

    private String extension(final String src) {
        int lastIdx = src.lastIndexOf(".");
        if (lastIdx == -1) {
            throw new IllegalArgumentException("The file not have extensions!");
        }
        return src.substring(lastIdx);
    }

}
