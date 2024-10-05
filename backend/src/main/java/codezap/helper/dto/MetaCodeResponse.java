package codezap.helper.dto;


import java.util.List;

public record MetaCodeResponse(
        String title,
        String description,
        String categories,
        List<String> tags) {
}
