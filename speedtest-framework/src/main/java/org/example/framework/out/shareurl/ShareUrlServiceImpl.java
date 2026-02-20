package org.example.framework.out.shareurl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;
import org.example.application.out.ShareUrlService;
import org.example.domain.Latency;
import org.example.domain.ShareURL;
import org.example.framework.out.http.HttpPostClient;
import org.example.util.Objectz;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class ShareUrlServiceImpl implements ShareUrlService {

    public static final String RESULT_ID = "resultid";

    private final Properties properties;
    private final HttpPostClient httpPostClient;

    public ShareUrlServiceImpl(
            Properties properties,
            HttpPostClient httpPostClient) {
        this.properties = properties;
        this.httpPostClient = httpPostClient;
    }

    @Override
    public ShareURL createShareUrl(int serverId, Latency latency, double uploadMbps, double downloadMbps) {
        Objectz.require(serverId > 0);
        Objectz.require(uploadMbps > 0);
        Objectz.require(downloadMbps > 0);
        try {
            int ping = (int) Math.round(latency.ms().doubleValue());
            int uploadKbps = (int) Math.round(uploadMbps * 1000.0);
            int downloadKbps = (int) Math.round(downloadMbps * 1000.0);
            String md5Hash = generateMd5Hash(String.format("%s-%s-%s-%s", ping, uploadKbps, downloadKbps, "297aae72"));
            String encodedBody = String.format("serverid=%s&hash=%s&ping=%s&download=%s&upload=%s&accuracy=1",
                    serverId, md5Hash, ping, downloadKbps, uploadKbps);
            String result = httpPostClient.postBodyWithSharedData(properties.url(), encodedBody);
            Map<String, String> queryParams = getQueryParams(result);
            if (queryParams.containsKey(RESULT_ID) && queryParams.get(RESULT_ID) != null) {
                var s = String.format("https://www.speedtest.net/result/%s.png", queryParams.get(RESULT_ID));
                return new ShareURL(URI.create(s));
            } else {
                throw new MissingResultException("Missing result for shareUrl request");
            }
        } catch (Exception e) {
            throw new ShareUrlException(e);
        }
    }

    private String generateMd5Hash(String data) {
        Objects.requireNonNull(data);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getQueryParams(String paramString) {
        Objects.requireNonNull(paramString);
        Map<String, String> params = new HashMap<>();
        for (String param : paramString.split("&")) {
            String[] pair = param.split("=");
            String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
            if (!params.containsKey(key)) {
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                }
                params.put(key, value);
            }
        }
        return params;
    }

}
