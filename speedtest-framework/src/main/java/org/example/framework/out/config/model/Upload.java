package org.example.framework.out.config.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Upload {

    @XmlAttribute
    public Integer ratio;

    @XmlAttribute
    public Integer maxchunkcount;

    @XmlAttribute
    public Integer threads;

    @XmlAttribute
    public Integer testlength;

}
