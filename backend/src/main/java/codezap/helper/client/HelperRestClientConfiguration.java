package codezap.helper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import codezap.helper.client.gemini.exception.GeminiClientErrorHandler;

@Configuration
public class HelperRestClientConfiguration {

    private final HelperProperties helperProperties;

    public HelperRestClientConfiguration(final HelperProperties helperProperties) {
        this.helperProperties = helperProperties;
    }

    @Bean
    public RestClient geminiRestClient() {
        return restClient()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + helperProperties.getSecretKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(new GeminiClientErrorHandler())
                .build();
    }

    @Bean
    public RestClient.Builder restClient() {
        return RestClient.builder()
                .requestFactory(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(1000);
        simpleClientHttpRequestFactory.setReadTimeout(2000);
        return simpleClientHttpRequestFactory;
    }
}
