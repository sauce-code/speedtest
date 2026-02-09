package org.example.framework.out.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import org.example.application.out.ConfigService;
import org.example.domain.config.Config;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.xml.Context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;

@ApplicationScoped
public class ConfigServiceImpl implements ConfigService {

    private static final URI CONFIG_URL = URI.create("https://www.speedtest.net/speedtest-config.php");

    private final HttpGetClient httpGetClient;
    private final Context context;

    @Inject
    public ConfigServiceImpl(
            HttpGetClient httpGetClient,
            Context context) {
        this.httpGetClient = httpGetClient;
        this.context = context;
    }

    @Override
    public Config config() {
        byte[] bytes = httpGetClient.get(CONFIG_URL);
        ConfigSetting settingFromXml = getSettingFromXml(bytes);
        return settingFromXml.toDomain();
    }

    private ConfigSetting getSettingFromXml(byte[] xml) throws ParsingException {
        Objects.requireNonNull(xml);
        try (InputStream is = new ByteArrayInputStream(xml)) {
           return context.unmarshal(is, ConfigSetting.class);
        } catch (IOException | JAXBException e) {
            throw new ParsingException(e);
        }
    }

}
