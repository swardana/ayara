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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit test for {@link BuildVersion}.
 *
 * @author Sukma Wardana
 */
public class BuildVersionTest {

    @Test
    @DisplayName("Test get the current build version")
    public void testCurrentBuildVersion() {
        var expected = "1.0.0";
        var actual = BuildVersion.getInstance().buildVersion();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    public void testCurrentBuildNumber() {
        var sdf = new SimpleDateFormat("yyM.w");
        var curr = sdf.format(new Date());
        System.out.println(curr);
        var actual = BuildVersion.getInstance().buildNumber();
        assertThat(actual).isNotNull().isEqualTo(curr);
    }

    @Test
    @DisplayName("Test get the copyright year")
    public void testCopyrightYear() {
        var expected = "2021";
        var actual = BuildVersion.getInstance().copyright();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get the author name")
    public void testAuthorName() {
        var expected = "Sukma Wardana";
        var actual = BuildVersion.getInstance().author();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get the app description")
    public void testApplicationDescription() {
        var expected = "a non-interactive nhentai.net manga downloader";
        var actual = BuildVersion.getInstance().desc();
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

}