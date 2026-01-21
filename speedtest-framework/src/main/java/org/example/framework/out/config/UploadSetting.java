package org.example.framework.out.config;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "upload")
@XmlAccessorType(XmlAccessType.FIELD)
public final class UploadSetting {

    @XmlAttribute(name = "ratio")
    private Integer ratio;
    @XmlAttribute(name = "maxchunkcount")
    private Integer maxChunkCount;
    @XmlAttribute(name = "threads")
    private Integer threads;
    @XmlAttribute(name = "testlength")
    private Integer testLength;

    public UploadSetting() {
    }

    public UploadSetting(Integer ratio, Integer maxChunkCount, Integer threads, Integer testLength) {
        this.ratio = ratio;
        this.maxChunkCount = maxChunkCount;
        this.threads = threads;
        this.testLength = testLength;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Integer getMaxChunkCount() {
        return maxChunkCount;
    }

    public void setMaxChunkCount(Integer maxChunkCount) {
        this.maxChunkCount = maxChunkCount;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getTestLength() {
        return testLength;
    }

    public void setTestLength(Integer testLength) {
        this.testLength = testLength;
    }

}
