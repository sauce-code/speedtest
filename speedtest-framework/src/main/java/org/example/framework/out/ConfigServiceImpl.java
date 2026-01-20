package org.example.framework.out;

import org.example.application.out.ConfigService;
import org.example.application.out.model.Config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigServiceImpl implements ConfigService {

    @Override
    public Config config() {
        final byte[] bytes = HttpGetClient.get(CONFIG_URL);
        if (bytes != null) {
            return getSettingFromXml(bytes);
        } else {
            throw new MissingResultException("Missing result for config settings request");
        }
    }

    private Config getSettingFromXml(final byte[] xml) throws ParsingException {
        if (xml != null) {
            try (InputStream is = new ByteArrayInputStream(xml)) {
                final JAXBContext jaxbContext = JAXBContext.newInstance(ConfigSetting.class);
                final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return (ConfigSetting) jaxbUnmarshaller.unmarshal(is);
            } catch (IOException | JAXBException e) {
                throw new ParsingException(e);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

}
