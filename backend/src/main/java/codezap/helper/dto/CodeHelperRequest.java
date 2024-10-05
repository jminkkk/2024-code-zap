package codezap.helper.dto;

import java.util.List;

import codezap.helper.client.HelperType;

public record CodeHelperRequest(HelperType helperType, String script, List<String> code) {
}
