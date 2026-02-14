package org.example.framework.out.xml;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.Objects;

@ApplicationScoped
public class Context {

    @SuppressWarnings("unchecked")
    public <T> T unmarshal(InputStream is, Class<T> clazz) throws JAXBException {
        Objects.requireNonNull(is);
        Objects.requireNonNull(clazz);
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(is);
    }

}
