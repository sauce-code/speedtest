package org.example.framework.out.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.domain.IsoAlpha2CountryCode;
import org.example.domain.Location;
import org.example.domain.config.Config;
import org.example.application.out.ConfigService;
import org.example.domain.config.DownloadSettings;
import org.example.domain.config.UploadSettings;
import org.example.domain.Client;
import org.example.framework.out.http.HttpGetClient;
import org.example.util.Objectz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class ConfigServiceImpl implements ConfigService {

    private static final URL CONFIG_URL = Objectz.notThrows(() -> URI.create("https://www.speedtest.net/speedtest-config.php").toURL());

    private final HttpGetClient httpGetClient;

    public ConfigServiceImpl(HttpGetClient httpGetClient) {
        this.httpGetClient = httpGetClient;
    }

    @Override
    public Config config() {
        byte[] bytes = httpGetClient.get(CONFIG_URL);
        ConfigSetting settingFromXml = getSettingFromXml(bytes);
        return new Config(
                new Client(
                        settingFromXml.getClient().getIpAddress(),
                        new Location(
                                settingFromXml.getClient().getLat(),
                                settingFromXml.getClient().getLon()),
                        settingFromXml.getClient().getIsp(),
                        settingFromXml.getClient().getIspRating(),
                        new IsoAlpha2CountryCode(
                                settingFromXml.getClient().getIsoAlpha2CountryCode())),
                new DownloadSettings(
                        settingFromXml.getDownload().getTestLength(),
                        settingFromXml.getDownload().getThreadsPerUrl()),
                new UploadSettings(
                        settingFromXml.getUpload().getRatio(),
                        settingFromXml.getUpload().getMaxChunkCount(),
                        settingFromXml.getUpload().getThreads(),
                        settingFromXml.getUpload().getTestLength()));
    }

    private ConfigSetting getSettingFromXml(byte[] xml) throws ParsingException {
        Objects.requireNonNull(xml);
        try (InputStream is = new ByteArrayInputStream(xml)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConfigSetting.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (ConfigSetting) jaxbUnmarshaller.unmarshal(is);
        } catch (IOException | JAXBException e) {
            throw new ParsingException(e);
        }
    }

}
