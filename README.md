# Ayara

Ayara Command-Line Interface (CLI), a non-interactive nhentai.net manga downloader.

## Building Ayara

In case you want to fork and build your local version of Ayara.

### Prerequisites

- A recent version of JDK 16 (The version provided by [AdoptOpenJDK](https://adoptopenjdk.net/) is an excellent choice).
- Apache Maven 3.8.1.

### How to run Ayara

Before starting the app, all dependencies must be installed.

```bash
mvn install
```

The ayara can be started with Maven.

```bash
mvn exec:java -Dexec.args="-h"
```

Use -Dexec.args to pass ayara arguments.
For details ayara options check `-h` or `--help`.

### How to build Ayara installer

Build the Ayara installer based on host operating system.

```bash
mvn install -Pinstaller
```

This will build ayara at `target/installer` folder.

## Contributing

Help is welcome.
For major changes, please send an email first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Support

Any donations to support the project are accepted via.

- [Patreon](https://www.patreon.com/swardana)
- [Paypal](https://www.paypal.me/sukmawardana/10)

## License

Ayara is an open-source licensed under the GPL v3 and later license.

[GNU General Public License, version 3 or later](COPYING)
