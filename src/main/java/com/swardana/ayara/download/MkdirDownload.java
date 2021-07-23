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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Decorator for {@link PageDownload}.
 * <p>
 *     This decorator will automatically create directory if its not exist.
 * </p>
 *
 * @author Sukma Wardana
 */
public class MkdirDownload implements PageDownload {

    private final PageDownload origin;
    private final Path outdir;

    /**
     * Creates new MkdirDownload.
     *
     * @param origin the origin of {@link PageDownload}.
     * @param output the output location for page to be downloaded and might
     *               not exist.
     */
    public MkdirDownload(final PageDownload origin, final Path output) {
        this.origin = origin;
        this.outdir = output;
    }

    @Override
    public final void download(final Page page) throws IOException {
        Files.createDirectories(this.outdir);
        this.origin.download(page);
    }

}
