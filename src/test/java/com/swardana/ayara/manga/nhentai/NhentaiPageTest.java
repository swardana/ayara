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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link NhentaiPage}.
 *
 * @author Sukma Wardana
 */
class NhentaiPageTest {

    @Test
    @DisplayName("Test get Hentai page name")
    public void testGetHentaiPageName() {
        var expected = "1.jpg";
        var mockNumber = mock(GalleryNumber.class);
        when(mockNumber.asString()).thenReturn("171301");
        var page = new NhentaiPage(
            "https://t.nhentai.net/galleries/143764/1t.jpg",
            mockNumber
        );
        var actual = page.name();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get Hentai page name not from thumb url")
    public void testGetHentaiPageNameNotFromThumbSrcUrl() {
        var mockNumber = mock(GalleryNumber.class);
        when(mockNumber.asString()).thenReturn("171301");
        var page = new NhentaiPage("1.jpg", mockNumber);
        assertThatThrownBy(() -> page.name())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The source not contains slash (/)!");
    }

    @Test
    @DisplayName("Test parse nhentai HTML JPG PNG to get Hentai manga title")
    public void testParseHtmlJpgPngAndGetHentaiAuthor() {
        var mockNumber = mock(GalleryNumber.class);
        when(mockNumber.asString()).thenReturn("171301");
        var page = new NhentaiPage(
            "https://t.nhentai.net/galleries/143764/1t",
            mockNumber
        );
        assertThatThrownBy(() -> page.name())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The file not have extensions!");
    }

}