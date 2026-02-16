package org.example.framework.out.server.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Servers {

    @XmlElement
    public List<Server> server;

}
