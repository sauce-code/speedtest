package org.example.framework.out.server;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.domain.IsoAlpha2CountryCode;
import org.example.domain.Location;

import java.net.URI;
import java.util.Optional;

@XmlRootElement
public class Server {

    @XmlAttribute
    String url;

    @XmlAttribute
    Double lat;

    @XmlAttribute
    Double lon;

    @XmlAttribute
    String name;

    @XmlAttribute
    String country;

    @XmlAttribute
    String cc;

    @XmlAttribute
    String sponsor;

    @XmlAttribute
    Integer id;

    @XmlAttribute
    String host;

    public Optional<org.example.domain.Server> toDomain() {
        try {
            org.example.domain.Server domain = new org.example.domain.Server(
                    URI.create(url),
                    new Location(lat, lon),
                    name,
                    country,
                    new IsoAlpha2CountryCode(cc),
                    sponsor,
                    id,
                    host);
            return Optional.of(domain);
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
    }

}
