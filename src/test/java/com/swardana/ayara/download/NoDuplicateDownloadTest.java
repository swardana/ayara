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

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swardana.ayara.manga.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Unit test for {@link NoDuplicateDownload}.
 *
 * @author Sukma Wardana
 */
class NoDuplicateDownloadTest {

    @Test
    @DisplayName("Test not download existing page in target directory")
    public void testAvoidDownloadExistingPageOnTargetDirectory(
        final @TempDir Path temp
    ) throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPage = mock(Page.class);
        when(mockPage.name()).thenReturn("1.jpg");
        when(mockPage.read()).thenReturn(mockContent);

        var file = temp.resolve(mockPage.name());

        var mockDownload = mock(PageDownload.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock iom) throws Throwable {
                Files.createFile(file);
                return null;
            }
        }).when(mockDownload).download(mockPage);

        var command = new NoDuplicateDownload(mockDownload, file);

        command.download(mockPage);
        command.download(mockPage);
        command.download(mockPage);

        verify(mockDownload).download(mockPage);
    }

    @Test
    @DisplayName("Test download a page if not exist in target directory")
    public void testDownloadPageIfNotExistInTargetDirectory(
        final @TempDir Path temp
    ) throws IOException {
        var mockContent = new ByteArrayInputStream(
            "content".getBytes(StandardCharsets.UTF_8)
        );

        var mockPage = mock(Page.class);
        when(mockPage.name()).thenReturn("1.jpg");
        when(mockPage.read()).thenReturn(mockContent);

        var file = temp.resolve(mockPage.name());

        var mockDownload = mock(PageDownload.class);

        var command = new NoDuplicateDownload(mockDownload, file);
        command.download(mockPage);

        verify(mockDownload).download(mockPage);
    }

}