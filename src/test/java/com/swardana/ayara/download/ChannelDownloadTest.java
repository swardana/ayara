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

package com.swardana.ayara.download;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.swardana.ayara.manga.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Unit test for {@link ChannelDownload}.
 *
 * @author Sukma Wardana
 */
class ChannelDownloadTest {

    @Test
    @DisplayName("Test download a page")
    public void testDownloadPage(final @TempDir Path temp) throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPage = mock(Page.class);
        when(mockPage.name()).thenReturn("1.jpg");
        when(mockPage.read()).thenReturn(mockContent);

        var command = new ChannelDownload(temp);
        command.download(mockPage);

        var actual = temp.resolve(mockPage.name());
        assertThat(actual).isNotNull().isRegularFile();
    }

    @Test
    @DisplayName("Test when fail to download a page")
    public void testFailToDownloadPage(final @TempDir Path temp) throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPage = mock(Page.class);
        when(mockPage.name()).thenReturn("1.jpg");
        when(mockPage.read()).thenReturn(mockContent);

        var outdir = temp.resolve("not-exist");
        var file = outdir.resolve(mockPage.name());

        var command = new ChannelDownload(outdir);

        assertThatThrownBy(() -> command.download(mockPage))
            .isInstanceOf(IOException.class)
            .hasMessage(
                String.format("The file %1s is not exist!", file)
            );
    }

}