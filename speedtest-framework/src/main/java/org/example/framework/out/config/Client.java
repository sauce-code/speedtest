package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client {

    @XmlAttribute
    String ip;

    @XmlAttribute
    Double lat;

    @XmlAttribute
    Double lon;

    @XmlAttribute
    String isp;

    @XmlAttribute
    Double isprating;

    @XmlAttribute
    String country;

}
