package codezap.helper.service;

import org.springframework.stereotype.Service;

import codezap.helper.client.HelperClient;
import codezap.helper.dto.MetaCodeResponse;
import codezap.helper.client.dto.MetaCodeClientRequest;
import codezap.helper.client.HelperType;
import codezap.helper.dto.CodeHelperRequest;

@Service
public class CodeHelperService {

    private final HelperClient helperClient;

    public CodeHelperService(final HelperClient helperClient) {
        this.helperClient = helperClient;
    }

    public MetaCodeResponse createMetaCode(final MetaCodeClientRequest codes) {
        CodeHelperRequest codeHelperRequest = new CodeHelperRequest(HelperType.META, getMetaDescription(), codes.codes());
        return helperClient.confirm(codeHelperRequest);
    }

    public MetaCodeResponse createTestCode(final MetaCodeClientRequest codes) {
        CodeHelperRequest codeHelperRequest = new CodeHelperRequest(HelperType.TEST, getTestDescription(), codes.codes());
        return helperClient.confirm(codeHelperRequest);
    }

    private String getMetaDescription() {
        return """
                안녕하세요 저는 개발자입니다.
                다음의 소스코드들은 한 번에 실행할 수 있는 코드들입니다. 
                제가 이 코드들을 언제 사용할지에 대한 코드 제목, 코드에 대한 설명, 카테고리 한 개와, 태그들을 추천해주세요.
                
                형식은 다음과 같습니다.
                
                {
                    "title": "코드 제목",
                    "description": "코드에 대한 설명",
                    "category": "카테고리",
                    "tags": ["태그1", "태그2", "태그3"]
                }
                
                다음은 이제 소스 코드 목록입니다.
                
                ---------------------------
                """;
    }

    private String getTestDescription() {
        return """
                안녕하세요 저는 개발자입니다.
                다음의 소스코드들은 한 번에 실행할 수 있는 코드들입니다. 
                이 코드들에 대해 테스트 코드를 작성해주세요.
                
                형식은 다음과 같습니다.
                
                {
                    "test": [
                        {
                            "filename": "exampl.java",
                            "sourcecode": "@Test\npublic void test() {\n    // test code\n}",
                        },
                        // ...
                    ]
                }
                
                다음은 이제 소스 코드 목록입니다.
                
                ---------------------------
                """;
    }
}
