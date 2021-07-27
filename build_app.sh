#
# Ayara, non-interactive nhentai.net manga downloader
# Copyright (C) 2021  Sukma Wardana
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

#!/bin/sh
# ------ ENVIRONMENT --------------------------------------------------------
# The script depends on various environment variables to exist in order to
# run properly. The java version we want to use, the location of the java
# binaries (java home), and the project version as defined inside the pom.xml
# file, e.g. 1.0-SNAPSHOT.
#

MAIN_JAR="ayara-$PROJECT_VERSION.jar"

echo "java home: $JAVA_HOME"
echo "project version: $PROJECT_VERSION"
echo "main JAR file: $MAIN_JAR"

# ------ SETUP DIRECTORIES AND FILES ----------------------------------------
# Remove previously generated java runtime and installers. Copy all required
# jar files into the input/libs folder.

rm -rfd ./target/installer/
rm -rfd ./target/mods

mkdir -p ./target/installer/input/libs/
mkdir -p ./target/mods

cp target/libs/* target/installer/input/libs/
cp target/${MAIN_JAR} target/installer/input/libs/

# ------ AUTOMATIC MODULES -------------------------------------------------
# To create custom Java runtime, all the dependencies should be in modular
# automatic modules is not supported to create custom Java runtime. So, we need
# patch dependencies that still using automatic modules.

# ------ jsoup dependency --------------------------------------------------

JSOUP_JAR=target/installer/input/libs/jsoup-1.13.1.jar

# generate jsoup module

$JAVA_HOME/bin/jdeps --generate-module-info target/mods ${JSOUP_JAR}
jsoup_mods=target/mods/org.jsoup/

# build jsoup module

$JAVA_HOME/bin/javac --patch-module org.jsoup=${JSOUP_JAR} ${jsoup_mods}/module-info.java
$JAVA_HOME/bin/jar uf ${JSOUP_JAR} -C ${jsoup_mods} module-info.class

# ------ REQUIRED MODULES ---------------------------------------------------
# Use jlink to detect all modules that are required to run the application.
# Starting point for the jdep analysis is the set of jars being used by the
# application.

echo "detecting required modules"
detected_modules=`$JAVA_HOME/bin/jdeps \
  -q \
  --ignore-missing-deps \
  --print-module-deps \
  --module-path target/installer/input/libs \
  --add-modules ${MAIN_MODULE} target/installer/input/libs/${MAIN_JAR}`
echo "detected modules: ${detected_modules}"


# ------ MANUAL MODULES -----------------------------------------------------
# jdk.crypto.ec has to be added manually bound via --bind-services or
# otherwise HTTPS does not work.
#
# See: https://bugs.openjdk.java.net/browse/JDK-8221674
#
# In addition we need jdk.localedata if the application is localized.
# This can be reduced to the actually needed locales via a jlink paramter,
# e.g., --include-locales=en,de.

# manual_modules=jdk.crypto.ec,jdk.localedata
manual_modules=jdk.crypto.ec
echo "manual modules: ${manual_modules}"

# ------ RUNTIME IMAGE ------------------------------------------------------
# Use the jlink tool to create a runtime image for our application. We are
# doing this is a separate step instead of letting jlink do the work as part
# of the jpackage tool. This approach allows for finer configuration and also
# works with dependencies that are not fully modularized, yet.

echo "creating ayara binaries"
$JAVA_HOME/bin/jlink \
  --no-header-files \
  --no-man-pages  \
  --compress=2  \
  --strip-debug \
  --module-path ./target/installer/input/libs \
  --add-modules "${detected_modules},${manual_modules},${MAIN_MODULE}" \
  --launcher ayara=${MAIN_MODULE}/${MAIN_CLASS} \
  --output ./target/installer/ayara-${PROJECT_VERSION}

echo "copying document and legal"
cp COPYING CHANGELOG.md ./target/installer/ayara-${PROJECT_VERSION}
cp ./doc/* ./target/installer/ayara-${PROJECT_VERSION}

echo "compress the package"
tar -cvzf ./target/installer/ayara-${PROJECT_VERSION}-bin.tar.gz ./target/installer/ayara-${PROJECT_VERSION}