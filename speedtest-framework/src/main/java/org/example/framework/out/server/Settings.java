package org.example.framework.out.server;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Settings {

    @XmlElement
    Servers servers;

}
