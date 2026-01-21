package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "download")
@XmlAccessorType(XmlAccessType.FIELD)
public final class DownloadSetting {

    @XmlAttribute(name = "testlength")
    private Integer testLength;

    @XmlAttribute(name = "threadsperurl")
    private Integer threadsPerUrl;

    public DownloadSetting() {
    }

    public DownloadSetting(Integer testLength, Integer threadsPerUrl) {
        this.testLength = testLength;
        this.threadsPerUrl = threadsPerUrl;
    }

    public Integer getTestLength() {
        return testLength;
    }

    public void setTestLength(Integer testLength) {
        this.testLength = testLength;
    }

    public Integer getThreadsPerUrl() {
        return threadsPerUrl;
    }

    public void setThreadsPerUrl(Integer threadsPerUrl) {
        this.threadsPerUrl = threadsPerUrl;
    }

}
