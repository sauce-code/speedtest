package org.example.framework.out.config.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client {

    @XmlAttribute
    public String ip;

    @XmlAttribute
    public String lat;

    @XmlAttribute
    public String lon;

    @XmlAttribute
    public String isp;

    @XmlAttribute
    public String isprating;

    @XmlAttribute
    public String country;

}
