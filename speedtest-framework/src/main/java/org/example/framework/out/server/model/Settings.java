package org.example.framework.out.server.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Settings {

    @XmlElement
    public Servers servers;

}
