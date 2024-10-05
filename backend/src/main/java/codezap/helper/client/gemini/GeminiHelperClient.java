package codezap.helper.client.gemini;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codezap.helper.dto.MetaCodeResponse;
import codezap.helper.client.HelperClient;
import codezap.helper.client.dto.TestCodeClientRequest;
import codezap.helper.dto.CodeHelperRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GeminiHelperClient implements HelperClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiHelperClient(final RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public MetaCodeResponse confirm(CodeHelperRequest codeHelperRequest) {
        GeminiCodeResponse geminiCodeResponse = restClient.post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .body(createRequestBody(codeHelperRequest))
                .retrieve()
                .toEntity(GeminiCodeResponse.class)
                .getBody();
        String text = geminiCodeResponse.candidates().get(0).content().parts().get(0).text();
        String cleanedResponse = cleanResponse(text);
        return parseCodeMeta(cleanedResponse);
    }


    private String createRequestBody(CodeHelperRequest codeHelperRequest) {
        String sourceCode = codeHelperRequest.script() + "\n" + String.join("\n", codeHelperRequest.code());
        Map<String, Object> requestMap = Map.of("contents",
                List.of(Map.of("parts", List.of(Map.of("text", sourceCode)))));

        try {
            return objectMapper.writeValueAsString(requestMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String cleanResponse(String responseBody) {
        responseBody = responseBody.replaceAll("```json", "").replaceAll("```", "");

        Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            responseBody = matcher.group();
        }

        return responseBody.trim();
    }

    private MetaCodeResponse parseCodeMeta(String responseBody) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
            return new MetaCodeResponse(
                    (String) responseMap.get("title"),
                    (String) responseMap.get("description"),
                    (String) responseMap.get("category"),
                    (List<String>) responseMap.get("tags")
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    private TestCodeClientRequest parseCodeTest(String responseBody) {
//        try {
//            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
//            return new TestCodeClientRequest(
//                    (List<String>) responseMap.get("tests")
//            );
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
