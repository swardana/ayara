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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Unit test for {@link NhentaiPages}.
 *
 * @author Sukma Wardana
 */
class NhentaiPagesTest {

    private static final String HTML = "src/test/resources/html/index.html";
    private static final String HTML_JPG_PNG = "src/test/resources/html/index_jpg_png.html";
    private static final String HTML_PNG = "src/test/resources/html/index_png.html";

    @Test
    @DisplayName("Test get Hentai manga pages from HTML resource")
    public void testGetHentaiMangaPagesFromHtmlResource() throws IOException {
        var expected = 27;

        var mockBookId = mock(GalleryNumber.class);
        when(mockBookId.asString()).thenReturn("1429412");

        var document = Jsoup.parse(new File(HTML), StandardCharsets.UTF_8.name());

        var actual = new NhentaiPages(document, mockBookId);

        assertThat(actual).isNotNull().isNotEmpty().size().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get Hentai manga pages from HTML_PNG resource")
    public void testGetHentaiMangaPagesFromHtmlPngResource() throws IOException {
        var expected = 40;

        var mockBookId = mock(GalleryNumber.class);
        when(mockBookId.asString()).thenReturn("1792633");

        var document = Jsoup.parse(new File(HTML_PNG), StandardCharsets.UTF_8.name());

        var actual = new NhentaiPages(document, mockBookId);

        assertThat(actual).isNotNull().isNotEmpty().size().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get Hentai pages from HTML-JPG_PNG resource")
    public void testGetHentaiPagesFromHtmlJpgPngResource() throws IOException {
        var expected = 32;

        var mockBookId = mock(GalleryNumber.class);
        when(mockBookId.asString()).thenReturn("1795953");

        var document = Jsoup.parse(new File(HTML_JPG_PNG), StandardCharsets.UTF_8.name());

        var actual = new NhentaiPages(document, mockBookId);

        assertThat(actual).isNotNull().isNotEmpty().size().isEqualTo(expected);
    }

}