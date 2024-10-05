package codezap.helper.client.gemini.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeminiClientErrorResponse(
        ErrorDetail error
) {

    record ErrorDetail(
            @JsonProperty("status") String code,
            @JsonProperty("message") String message) {
    }
}
