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

import com.swardana.ayara.manga.Existence;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Hentai manga existence in nhentai.net.
 *
 * @author Sukma Wardana
 */
public class NhentaiExistence implements Existence {

    private final String number;

    /**
     * Creates new NhentaiExistence.
     *
     * @param number the hentai manga digit code.
     */
    public NhentaiExistence(final String number) {
        this.number = number;
    }

    @Override
    public final boolean isExist() {
        boolean result = false;
        var url = Nhentai.WEBSITE_URL.concat(this.number);
        try {
            var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
            var request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
            var response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );
            if (response.statusCode() == 200) {
                result = true;
            }
            return result;
        } catch (final URISyntaxException ex) {
            throw new RuntimeException(ex);
        } catch (final SSLHandshakeException ex) {
            throw new RuntimeException(
                String.format(
                    "Failed send request to %s, check are you need a VPN or not!",
                    url
                )
            );
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
