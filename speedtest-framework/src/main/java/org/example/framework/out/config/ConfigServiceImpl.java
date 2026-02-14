package org.example.framework.out.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBException;
import org.example.application.out.ConfigService;
import org.example.domain.config.Config;
import org.example.framework.out.config.model.Settings;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.xml.Context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@ApplicationScoped
public class ConfigServiceImpl implements ConfigService {

    private final Properties properties;
    private final HttpGetClient httpGetClient;
    private final Context context;

    public ConfigServiceImpl(
            Properties properties,
            HttpGetClient httpGetClient,
            Context context) {
        this.properties = properties;
        this.httpGetClient = httpGetClient;
        this.context = context;
    }

    @Override
    public Config config() {
        byte[] bytes = httpGetClient.get(properties.url());
        Settings settingFromXml = getSettingFromXml(bytes);
        return settingFromXml.toDomain();
    }

    private Settings getSettingFromXml(byte[] xml) throws ParsingException {
        Objects.requireNonNull(xml);
        try (InputStream is = new ByteArrayInputStream(xml)) {
            return context.unmarshal(is, Settings.class);
        } catch (IOException | JAXBException e) {
            throw new ParsingException(e);
        }
    }

}
