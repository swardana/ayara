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

package com.swardana.ayara;

import com.swardana.ayara.download.ChannelDownload;
import com.swardana.ayara.download.MkdirDownload;
import com.swardana.ayara.download.NoDuplicateDownload;
import com.swardana.ayara.download.PageDownload;
import com.swardana.ayara.manga.nhentai.NhentaiExistence;
import com.swardana.ayara.manga.nhentai.NhentaiPages;
import com.swardana.ayara.message.ConsoleHelp;
import com.swardana.ayara.message.ConsoleMessage;
import com.swardana.ayara.message.ConsoleProgress;
import com.swardana.ayara.message.ConsoleVersion;
import com.swardana.ayara.message.Help;
import com.swardana.ayara.message.Message;
import com.swardana.ayara.message.Version;

import java.nio.file.Path;

/**
 * The bootstrap ayara command-line interface.
 *
 * @author Sukma Wardana
 */
public class AyaraConsole {

    private static final Version VERSION = new ConsoleVersion();
    private static final Message MESSAGE = new ConsoleMessage();
    private static final Help HELP = new ConsoleHelp();

    private static String outdir;
    private static String code;
    private static boolean isContinue;
    private static boolean isForce;

    /**
     * The ayara-cli main method.
     *
     * @param args the ayara-cli arguments.
     */
    public static void main(final String[] args) {
        if (args.length == 0) {
            MESSAGE.print("Missing hentai digit code!", 1);
        }
        int idx = 0;
        while (idx < args.length) {
            var arg = args[idx];
            switch (arg) {
                case "-h":
                case "--help":
                    HELP.print();
                    break;
                case "-V":
                case "--version":
                    VERSION.print();
                    break;
                case "-c":
                case "--continue":
                    isContinue = true;
                    break;
                case "-O":
                case "--output-directory":
                    outdir = args[++idx];
                    break;
                case "-x":
                case "--force-directories":
                    isForce = true;
                    break;
                default:
                    if (arg.startsWith("-")) {
                        MESSAGE.print(
                            "Unknown option " + arg,
                            1
                        );
                    } else {
                        code = arg;
                    }
                    break;
            }
            idx++;
        }
        process();
    }

    private static void process() {
        if (code == null) {
            MESSAGE.print("Missing hentai manga digit code!", 1);
        }
        var existence = new NhentaiExistence(code);
        try {
            if (!existence.isExist()) {
                MESSAGE.print(
                    "The hentai manga is not found, please try different digit code!",
                    1
                );
            }
            var target = pathDirectory();
            var pages = new NhentaiPages(code);
            var command = pageDownload(target);
            var content = """
                Processing to download hentai manga:
                Code          : %s
                Save to       : %s
                """.formatted(code, target.toString());
            System.out.println(content);

            long total = pages.size();
            long start = System.currentTimeMillis();
            var progress = new ConsoleProgress(start, total);
            int current = 1;
            for (final var page : pages) {
                command.download(page);
                progress.print(current++);
            }

        } catch (final Exception ex) {
            MESSAGE.print(
                "Something went wrong!" + ex.getMessage(),
                1
            );
        }
    }

    private static Path pathDirectory() {
        final Path target;
        if (outdir == null) {
            target = Path.of("").toAbsolutePath();
        } else {
            target = Path.of(outdir);
        }
        return target;
    }

    private static PageDownload pageDownload(final Path target) {
        PageDownload command = new ChannelDownload(target);
        if (isForce) {
            command = new MkdirDownload(command, target);
        }
        if (isContinue) {
            command = new NoDuplicateDownload(command, target);
        }
        return command;
    }

}
