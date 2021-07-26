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

package com.swardana.ayara.message;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * A progress bar for command-line interface (shell) environment.
 * <p>
 *     Usage example:
 *     <pre>{@code
 *         long total = 235;
 *         long start = System.currentTimeMillis();
 *
 *         Progress progress = new ConsoleProgress(start, total);
 *
 *         for (int i=1; i <= total; i++) {
 *             try {
 *                 Thread.sleep(50);
 *                 progress.print(i);
 *             } catch (InterruptedException ex) {
 *                 // Do something!
 *             }
 *         }
 *     }</pre>
 * </p>
 *
 * @author Sukma Wardana
 */
public class ConsoleProgress implements Progress{

    private final long start;
    private final long total;

    /**
     * Creates new ConsoleProgress.
     *
     * @param start the start time.
     * @param total the total process.
     */
    public ConsoleProgress(final long start, final long total) {
        this.start = start;
        this.total = total;
    }

    @Override
    public final void print(final long current) {
        var msg = new StringBuilder(140);
        int percent = (int) (current * 100 / this.total);
        msg.append('\r');
        msg.append(String.join("", Collections.nCopies(this.percentageCopies(percent), " ")));
        msg.append(String.format(" %d%% [", percent));
        msg.append(String.join("", Collections.nCopies(percent, "=")));
        msg.append('>');
        msg.append(String.join("", Collections.nCopies(100 - percent, " ")));
        msg.append(']');
        msg.append(String.join("", Collections.nCopies(this.currentCopies(current), " ")));
        msg.append(String.format(" %d/%d, ETA: %s", current, this.total, this.time(current)));
        System.out.print(msg);
    }

    private String time(final long current) {
        final String hms;
        if (current == 0) {
            hms = "N/A";
        } else {
            long eta = (this.total - current) * (System.currentTimeMillis() - this.start) / current;
            hms = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(eta),
                TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1)
            );
        }
        return hms;
    }

    private int percentageCopies(final int percentage) {
        final int percentCopies;
        if (percentage == 0) {
            percentCopies = 2;
        } else {
            percentCopies = 2 - (int) (Math.log10(percentage));
        }
        return percentCopies;
    }

    private int currentCopies(final long current) {
        final int currentCopies;
        if (current == 0) {
            currentCopies = (int) (Math.log10(this.total));
        } else {
            currentCopies = (int) (Math.log10(this.total)) - (int) (Math.log10(current));
        }
        return currentCopies;
    }
}
