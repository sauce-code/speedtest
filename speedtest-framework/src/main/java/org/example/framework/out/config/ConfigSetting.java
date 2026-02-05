package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.domain.IsoAlpha2CountryCode;
import org.example.domain.Location;
import org.example.domain.config.Config;
import org.example.domain.config.DownloadSettings;
import org.example.domain.config.UploadSettings;

@XmlRootElement(name = "settings")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ConfigSetting {

    @XmlElement(name = "client")
    private Client client;
    @XmlElement(name = "download")
    private DownloadSetting download;
    @XmlElement(name = "upload")
    private UploadSetting upload;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public DownloadSetting getDownload() {
        return download;
    }

    public void setDownload(DownloadSetting download) {
        this.download = download;
    }

    public UploadSetting getUpload() {
        return upload;
    }

    public void setUpload(UploadSetting upload) {
        this.upload = upload;
    }
    
    public Config toDomain() {
        return new Config(
                new org.example.domain.Client(
                        client.getIpAddress(),
                        new Location(
                                client.getLat(),
                                client.getLon()),
                        client.getIsp(),
                        client.getIspRating(),
                        new IsoAlpha2CountryCode(
                                client.getIsoAlpha2CountryCode())),
                new DownloadSettings(
                        download.getTestLength(),
                        download.getThreadsPerUrl()),
                new UploadSettings(
                        upload.getRatio(),
                        upload.getMaxChunkCount(),
                        upload.getThreads(),
                        upload.getTestLength()));
    }
    
}
