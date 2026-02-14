package org.example.framework.out.config.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Download {

    @XmlAttribute
    public Integer testlength;

    @XmlAttribute
    public Integer threadsperurl;

}
