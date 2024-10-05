package codezap.helper.client.gemini.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import codezap.global.exception.CodeZapException;

public class GeminiClientErrorHandler implements ResponseErrorHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(GeminiClientErrorHandler.class);

    @Override
    public boolean hasError(final ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        log.error("Gemini API 오류가 발생했습니다. status code: {}, text: {}", response.getStatusCode(), response.getStatusText());
        GeminiClientErrorResponse tossClientErrorResponse = objectMapper.readValue(response.getBody(),
                GeminiClientErrorResponse.class);


        if (GeminiErrorCodeNotForUser.hasContains(tossClientErrorResponse.error().code())) {
            throw new CodeZapException(HttpStatus.BAD_REQUEST, "Gemini API 오류입니다. 같은 문제가 반복된다면 문의해주세요.");
        }

        throw new CodeZapException(HttpStatus.INTERNAL_SERVER_ERROR, tossClientErrorResponse.error().message());
    }
}
