package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Client {

    @XmlAttribute(name = "ip")
    private String ipAddress;
    @XmlAttribute(name = "lat")
    private Double lat;
    @XmlAttribute(name = "lon")
    private Double lon;
    @XmlAttribute(name = "isp")
    private String isp;
    @XmlAttribute(name = "isprating")
    private Double ispRating;
    @XmlAttribute(name = "country")
    private String isoAlpha2CountryCode;

    public Client() {
    }

    public Client(String ipAddress, Double lat, Double lon, String isp, Double ispRating, String isoAlpha2CountryCode) {
        this.ipAddress = ipAddress;
        this.lat = lat;
        this.lon = lon;
        this.isp = isp;
        this.ispRating = ispRating;
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public Double getIspRating() {
        return ispRating;
    }

    public void setIspRating(Double ispRating) {
        this.ispRating = ispRating;
    }

    public String getIsoAlpha2CountryCode() {
        return isoAlpha2CountryCode;
    }

    public void setIsoAlpha2CountryCode(String isoAlpha2CountryCode) {
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
    }

}
