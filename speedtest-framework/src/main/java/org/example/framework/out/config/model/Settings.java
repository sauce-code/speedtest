package org.example.framework.out.config.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.domain.IsoAlpha2CountryCode;
import org.example.domain.Location;
import org.example.domain.config.Config;
import org.example.domain.config.DownloadSettings;
import org.example.domain.config.UploadSettings;

@XmlRootElement
public class Settings {

    @XmlElement
    public Client client;

    @XmlElement
    public Download download;

    @XmlElement
    public Upload upload;

    public Config toDomain() {
        return new Config(
                new org.example.domain.Client(
                        client.ip,
                        new Location(
                                client.lat,
                                client.lon),
                        client.isp,
                        client.isprating,
                        new IsoAlpha2CountryCode(
                                client.country)),
                new DownloadSettings(
                        download.testlength,
                        download.threadsperurl),
                new UploadSettings(
                        upload.ratio,
                        upload.maxchunkcount,
                        upload.threads,
                        upload.testlength));
    }

}
