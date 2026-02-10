package org.example.framework.out.image;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.ImageStore;
import org.example.application.out.Logger;
import org.example.domain.ShareURL;

import java.io.*;
import java.net.URI;
import java.net.URL;

@ApplicationScoped
public class ImageStoreImpl implements ImageStore {

    private final Logger logger;

    public ImageStoreImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public URI store(ShareURL shareURL) {
        try {
            URL url = shareURL.uri().toURL();
            InputStream is = url.openStream();
            URI output = URI.create("target" + url.getFile());
            File file = new File(output.getPath());
            if (file.getParentFile().mkdirs()) {
                logger.infov("created dir {}}", file.getParentFile());
            }
            FileOutputStream os = new FileOutputStream(file, false);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();

            return output;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
