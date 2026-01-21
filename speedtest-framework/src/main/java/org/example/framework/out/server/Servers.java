package org.example.framework.out.server;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "servers")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Servers {

    @XmlElement(name = "server")
    private List<Server> serverList;

    public Servers() {
    }

    public Servers(List<Server> serverList) {
        this.serverList = serverList;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

}
