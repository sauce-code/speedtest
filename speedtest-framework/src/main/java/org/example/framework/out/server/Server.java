package org.example.framework.out.server;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.domain.IsoAlpha2CountryCode;
import org.example.domain.Location;

import java.net.URI;

@XmlRootElement(name = "server")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Server {

    @XmlAttribute(name = "uri")
    private String url;
    @XmlAttribute(name = "lat")
    private Double lat;
    @XmlAttribute(name = "lon")
    private Double lon;
    @XmlAttribute(name = "name")
    private String city;
    @XmlAttribute(name = "country")
    private String country;
    @XmlAttribute(name = "cc")
    private String isoAlpha2CountryCode;
    @XmlAttribute(name = "sponsor")
    private String sponsor;
    @XmlAttribute(name = "id")
    private Integer id;
    @XmlAttribute(name = "host")
    private String host;

    public Server() {
    }

    public Server(String url, Double lat, Double lon, String city, String country, String isoAlpha2CountryCode, String sponsor, Integer id, String host) {
        this.url = url;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.country = country;
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
        this.sponsor = sponsor;
        this.id = id;
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoAlpha2CountryCode() {
        return isoAlpha2CountryCode;
    }

    public void setIsoAlpha2CountryCode(String isoAlpha2CountryCode) {
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public org.example.domain.Server toDomain() {
        return new org.example.domain.Server(
                URI.create(url),
                new Location(lat, lon),
                city,
                country,
                new IsoAlpha2CountryCode(isoAlpha2CountryCode),
                sponsor,
                id,
                host);
    }

}
