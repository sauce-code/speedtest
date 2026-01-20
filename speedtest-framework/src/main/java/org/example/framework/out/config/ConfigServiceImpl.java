package org.example.framework.out.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.application.out.ConfigService;
import org.example.application.out.model.Config;
import org.example.framework.out.http.HttpGetClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigServiceImpl implements ConfigService {

    private static final String CONFIG_URL = "https://www.speedtest.net/speedtest-config.php";

    private final HttpGetClient httpGetClient;

    public ConfigServiceImpl(HttpGetClient httpGetClient) {
        this.httpGetClient = httpGetClient;
    }

    @Override
    public Config config() {
        final byte[] bytes = httpGetClient.get(CONFIG_URL);
        if (bytes != null) {
            return getSettingFromXml(bytes);
        } else {
            throw new MissingResultException("Missing result for config settings request");
        }
    }

    private Config getSettingFromXml(final byte[] xml) throws ParsingException {
        if (xml != null) {
            try (InputStream is = new ByteArrayInputStream(xml)) {
                final JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
                final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return (Config) jaxbUnmarshaller.unmarshal(is);
            } catch (IOException | JAXBException e) {
                throw new ParsingException(e);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

}
