package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Upload {

    @XmlAttribute
    Integer ratio;

    @XmlAttribute
    Integer maxchunkcount;

    @XmlAttribute
    Integer threads;

    @XmlAttribute
    Integer testlength;

}
