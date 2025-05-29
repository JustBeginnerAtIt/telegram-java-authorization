package telegram.auth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Component
public class TelegramAuthValidator {

    private final String botToken;

    @Autowired
    public TelegramAuthValidator(@Value("${telegram.bot-token}") String botToken) {
        this.botToken = botToken;
    }

    public boolean validate(String initData) {
        Map<String, String> data = parseInitData(initData);

        String hash = data.remove("hash");

        if (hash == null) { return false; }

        List<String> sortedList = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            sortedList.add(entry.getKey() + "=" + entry.getValue());
        }

        Collections.sort(sortedList);

        String dataCheckString = String.join("\n", sortedList);
        byte[] secretKey = sha256(botToken);
        String hmac = hmacSha256(dataCheckString, secretKey);

        return hmac.equals(hash);
    }

    public Map<String, String> parseInitData(String initData) {
        Map<String, String> result = new HashMap<>();

        String[] pairs = initData.split("&");

        for(String pair : pairs) {
            String[] keyValue = pair.split("=", 2);

            if (keyValue.length == 2) {
                result.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
            }
        }

        return result;
    }

    private byte[] sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String hmacSha256(String data, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            byte[] hmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmac);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
