package org.example.framework.out.server.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.domain.IsoAlpha2CountryCode;
import org.example.domain.Location;

import java.net.URI;

@XmlRootElement
public class Server {

    @XmlAttribute
    public String url;

    @XmlAttribute
    public Double lat;

    @XmlAttribute
    public Double lon;

    @XmlAttribute
    public String name;

    @XmlAttribute
    public String country;

    @XmlAttribute
    public String cc;

    @XmlAttribute
    public String sponsor;

    @XmlAttribute
    public Integer id;

    @XmlAttribute
    public String host;

    public org.example.domain.Server toDomain() {
        return new org.example.domain.Server(
                URI.create(url),
                new Location(lat, lon),
                name,
                country,
                new IsoAlpha2CountryCode(cc),
                sponsor,
                id,
                host);
    }

}
