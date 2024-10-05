package codezap.helper.client;

import java.util.Random;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "helper")
public class HelperProperties {

    private final String[] secretKey;

    public HelperProperties(final String[] secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        Random random = new Random();
        int index = random.nextInt(0, secretKey.length - 1);
        return secretKey[index];
    }
}
