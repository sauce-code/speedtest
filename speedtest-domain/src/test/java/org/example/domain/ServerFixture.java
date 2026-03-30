package org.example.domain;

import org.example.domain.location.Latitude;
import org.example.domain.location.Location;
import org.example.domain.location.Longitude;

import java.net.URI;
import java.util.List;

public class ServerFixture {

    public static List<Server> some() {
        return List.of(
                new Server(
                        URI.create("http://speedtest6.t-mobile.cz:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.1953"),
                                Longitude.valueOf("16.6083")),
                        "Brno",
                        "Czechia",
                        new IsoAlpha2CountryCode("CZ"),
                        "T-Mobile Czechia a.s.",
                        69440,
                        "speedtest6.t-mobile.cz:8080"),
                new Server(
                        URI.create("http://speedtest.net.vutbr.cz:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.1953"),
                                Longitude.valueOf("16.6083")),
                        "Brno",
                        "Czechia",
                        new IsoAlpha2CountryCode("CZ"),
                        "Brno University of Technology",
                        64446,
                        "speedtest.net.vutbr.cz:8080"),
                new Server(
                        URI.create("http://speedtest.vivo.cz:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.1533"),
                                Longitude.valueOf("16.8765")),
                        "Slavkov u Brna",
                        "Czechia",
                        new IsoAlpha2CountryCode("CZ"),
                        "VIVO CONNECTION spol. s r.o.",
                        15684,
                        "speedtest.vivo.cz:8080"),
                new Server(
                        URI.create("http://speed.inext.cz:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.2331"),
                                Longitude.valueOf("17.6669")),
                        "Zlin",
                        "Czechia",
                        new IsoAlpha2CountryCode("CZ"),
                        "INTERNEXT 2000",
                        10855,
                        "speed.inext.cz:8080"),
                new Server(
                        URI.create("http://speedtest.blizznet.at:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.2088"),
                                Longitude.valueOf("16.3726")),
                        "Vienna",
                        "Austria",
                        new IsoAlpha2CountryCode("AT"),
                        "Wien Energie Business",
                        62623,
                        "speedtest.blizznet.at:8080"),
                new Server(
                        URI.create("http://speedtest.netcup.at:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.2088"),
                                Longitude.valueOf("16.3726")),
                        "Vienna",
                        "Austria",
                        new IsoAlpha2CountryCode("AT"),
                        "netcup GmbH",
                        60470,
                        "speedtest.netcup.at:8080"),
                new Server( //6
                        URI.create("http://at.blackhost.network:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.2088"),
                                Longitude.valueOf("16.3726")),
                        "Vienna",
                        "Austria",
                        new IsoAlpha2CountryCode("AT"),
                        "BlackHOST Ltd.",
                        46058,
                        "at.blackhost.network:8080"),
                new Server(
                        URI.create("http://speedtest-vix.cosys.cc:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.2088"),
                                Longitude.valueOf("16.3726")),
                        "Vienna",
                        "Austria",
                        new IsoAlpha2CountryCode("AT"),
                        "COSYS DATA GmbH",
                        16161,
                        "speedtest-vix.cosys.cc:8080"),
                new Server(
                        URI.create("http://speedtest.telematica.at:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.2088"),
                                Longitude.valueOf("16.3726")),
                        "Vienna",
                        "Austria",
                        new IsoAlpha2CountryCode("AT"),
                        "Telematica",
                        53528,
                        "speedtest.telematica.at:8080"),
                new Server( // 9
                        URI.create("http://speedtest.kapper.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.2088"),
                                Longitude.valueOf("16.3726")),
                        "Vienna",
                        "Austria",
                        new IsoAlpha2CountryCode("AT"),
                        "kapper.net",
                        7412,
                        "speedtest.kapper.net:8080"),
                new Server(
                        URI.create("http://ookla2.bisping.de:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.4543"),
                                Longitude.valueOf("11.0746")),
                        "Nuremberg",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Bisping & Bisping GmbH & Co. KG",
                        73743,
                        "ookla2.bisping.de:8080"),
                new Server(
                        URI.create("http://nbg01.speedtest.flavius.cloud:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.4521"),
                                Longitude.valueOf("11.0767")),
                        "Nuremberg",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Flavius Hager",
                        73033,
                        "nbg01.speedtest.flavius.cloud:8080"),
                new Server(
                        URI.create("http://speed.degrotewolk.nl:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.0976"),
                                Longitude.valueOf("12.4869")),
                        "Falkenstein",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "LaurensK",
                        74033,
                        "speed.degrotewolk.nl:8080"),
                new Server(
                        URI.create("http://speedtest.fra1.hivelocity.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1103"),
                                Longitude.valueOf("8.7147")),
                        "Frankfurt",
                        "United Germany", // sic!
                        new IsoAlpha2CountryCode("DE"),
                        "Hivelocity",
                        68177,
                        "speedtest.fra1.hivelocity.net:8080"),
                new Server( //14
                        URI.create("http://speedtest.58243.as:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "TELE AG",
                        44477,
                        "speedtest.58243.as:8080"),
                new Server(
                        URI.create("http://fra.speedtest.clouvider.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt am Main",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Clouvider Ltd",
                        35692,
                        "fra.speedtest.clouvider.net:8080"),
                new Server(
                        URI.create("https://lg-fra.fdcservers.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "fdcservers.net",
                        10010,
                        "lg-fra.fdcservers.net:8080"),
                new Server( // 17
                        URI.create("http://speedtest.creolineserver.de:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "creoline GmbH",
                        17312,
                        "speedtest.creolineserver.de:8080"),
                new Server(
                        URI.create("http://speedtest.twerion.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Twerion.net | Minecraft Server",
                        55462,
                        "speedtest.twerion.net:8080"),
                new Server(
                        URI.create("https://speedtest-net.mk.de:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "MK Netzdienste",
                        61917,
                        "speedtest-net.mk.de:8080"),
                new Server( // 20
                        URI.create("http://speedtest1.s-it.de:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.7127"),
                                Longitude.valueOf("8.7425")),
                        "Calw",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "S-IT Informationstechnologie Betreiber GmbH & Co. KG im Nordschwarzwald",
                        70596,
                        "speedtest1.s-it.de:8080"),
                new Server(
                        URI.create("http://speedtest.indasys.cloud:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.7758"),
                                Longitude.valueOf("9.1829")),
                        "Stuttgart",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "indasys IT Systemhaus AG",
                        50623,
                        "speedtest.indasys.cloud:8080"),
                new Server(
                        URI.create("http://strasbourg3.d2m.c2d.liveservices.fr:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.5734"),
                                Longitude.valueOf("7.7521")),
                        "Strasbourg",
                        "France",
                        new IsoAlpha2CountryCode("FR"),
                        "ORANGE FRANCE",
                        61300,
                        "strasbourg3.d2m.c2d.liveservices.fr:8080"),
                new Server( // 23
                        URI.create("http://testdebit.vialis.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("48.5734"),
                                Longitude.valueOf("7.7521")),
                        "Strasbourg",
                        "France",
                        new IsoAlpha2CountryCode("FR"),
                        "Vialis",
                        11769,
                        "testdebit.vialis.net:8080"),
                new Server(
                        URI.create("http://speedtest.eu-fr.kamatera.com:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "KamaTera, Inc.",
                        69755,
                        "speedtest.eu-fr.kamatera.com:8080"),
                new Server(
                        URI.create("http://speedtest.webdiscount.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "WEBDISCOUNT GmbH & Co. KG",
                        63028,
                        "speedtest.webdiscount.net:8080"),
                new Server( // 26
                        URI.create("http://speedtest.frankfurt-am-main.valtrix.org:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt am Main",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Valtrix Worldwide LLC",
                        73035,
                        "speedtest.frankfurt-am-main.valtrix.org:8080"),
                new Server(
                        URI.create("http://ookla.fnm.as6453.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt am Main",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Tata Communications",
                        73624,
                        "ookla.fnm.as6453.net:8080"),
                new Server(
                        URI.create("http://de-lg.h2.nexus:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "H2NEXUS LTD",
                        71892,
                        "de-lg.h2.nexus:8080"),
                new Server(
                        URI.create("http://speedtest-de.webdade.com:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt am Main",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Webdade",
                        72038,
                        "speedtest-de.webdade.com:8080"));
    }

}
