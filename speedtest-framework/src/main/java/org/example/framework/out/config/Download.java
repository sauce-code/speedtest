package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Download {

    @XmlAttribute
    Integer testlength;

    @XmlAttribute
    Integer threadsperurl;

}
