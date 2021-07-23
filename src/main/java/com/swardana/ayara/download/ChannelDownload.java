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

import com.swardana.ayara.manga.Page;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.file.Path;

/**
 * Download a page using Java {@link java.nio.channels.Channel}.
 *
 * @author Sukma Wardana
 */
public class ChannelDownload implements PageDownload {

    private final Path outdir;

    /**
     * Creates new ChannelDownload.
     *
     * @param output the output location for page to be downloaded.
     */
    public ChannelDownload(final Path output) {
        this.outdir = output;
    }

    @Override
    public final void download(final Page page) throws IOException {
        var file = this.outdir.resolve(page.name());
        try (
            var channel = Channels.newChannel(page.read());
            var stream = new FileOutputStream(file.toFile());
        ) {
            stream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
        } catch (final FileNotFoundException ex) {
            throw new IOException(
                String.format("The file %1$1s is not exist!", file.toString()),
                ex
            );
        }
    }

}
