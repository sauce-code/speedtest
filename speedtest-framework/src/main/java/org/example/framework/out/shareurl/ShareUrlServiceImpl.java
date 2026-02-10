package org.example.framework.out.shareurl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;
import org.example.application.out.ShareUrlService;
import org.example.domain.ShareURL;
import org.example.framework.out.Util;
import org.example.framework.out.http.HttpPostClient;
import org.example.util.Objectz;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class ShareUrlServiceImpl implements ShareUrlService {

    public static final String RESULT_ID = "resultid";

    public static final String URL_API = "https://www.speedtest.net/api/api.php";

    private final HttpPostClient httpPostClient;

    public ShareUrlServiceImpl(HttpPostClient httpPostClient) {
        this.httpPostClient = httpPostClient;
    }

    @Override
    public ShareURL createShareUrl(int serverId, double latency, double uploadMbps, double downloadMbps) {
        Objectz.require(serverId > 0);
        Objectz.require(uploadMbps > 0);
        Objectz.require(downloadMbps > 0);
        int ping = (int) Math.round(latency);
        int uploadKbps = (int) Math.round(uploadMbps * 1000.0);
        int downloadKbps = (int) Math.round(downloadMbps * 1000.0);
        String md5Hash = generateMd5Hash(String.format("%s-%s-%s-%s", ping, uploadKbps, downloadKbps, "297aae72"));
        String encodedBody = String.format("serverid=%s&hash=%s&ping=%s&download=%s&upload=%s&accuracy=1",
                serverId, md5Hash, ping, downloadKbps, uploadKbps);
        String result = httpPostClient.postBodyWithSharedData(URI.create(URL_API), encodedBody);
        Map<String, String> queryParams = Util.getQueryParams(result);
        if (queryParams.containsKey(RESULT_ID) && queryParams.get(RESULT_ID) != null) {
            var s = String.format("https://www.speedtest.net/result/%s.png", queryParams.get(RESULT_ID));
            return new ShareURL(URI.create(s));
        } else {
            throw new MissingResultException("Missing result for shareUrl request");
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

}
