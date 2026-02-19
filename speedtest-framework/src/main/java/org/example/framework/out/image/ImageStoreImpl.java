package org.example.framework.out.image;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.ImageStore;
import org.example.application.out.Logger;
import org.example.domain.ShareURL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@ApplicationScoped
public class ImageStoreImpl implements ImageStore {

    private final Logger logger;
    private final Properties properties;

    public ImageStoreImpl(
            Logger logger,
            Properties properties) {
        this.logger = logger;
        this.properties = properties;
    }

    @Override
    public File store(ShareURL shareURL) {
        try {
            URL url = shareURL.uri().toURL();
            InputStream is = url.openStream();
            File file = new File(properties.path() + url.getPath());
            File parentFile = file.getParentFile();
            if (parentFile.mkdirs()) {
                logger.infov("Created Directory: {0}", parentFile);
            }
            FileOutputStream os = new FileOutputStream(file, false);

            byte[] b = new byte[properties.bufferSize()];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();

            return file;
        } catch (IOException e) {
            throw new ImageStoreException(e);
        }
    }

}
