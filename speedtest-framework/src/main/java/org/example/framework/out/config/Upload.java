package org.example.framework.out.config;

import jakarta.xml.bind.annotation.*;

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
