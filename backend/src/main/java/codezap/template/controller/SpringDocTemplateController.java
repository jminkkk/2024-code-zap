package codezap.template.controller;

import org.springframework.http.ResponseEntity;

import codezap.template.dto.request.CreateTemplateResponse;
import codezap.template.dto.response.CreateTemplateRequest;
import io.swagger.v3.oas.annotations.Operation;

public interface SpringDocTemplateController {

    @Operation(summary = "템플릿 생성", description = """
            새로운 템플릿을 생성합니다. \n 
            템플릿의 제목, 썸네일 스니펫의 순서, 스니펫 목록이 필요합니다. \n
            스니펫 목록은 파일 이름, 소스 코드, 해당 스니펫의 순서가 필요합니다. \n
            * 썸네일 스니펫은 1로 고정입니다. (2024.07.15 기준) \n
            * 모든 스니펫 순서는 1부터 시작합니다. \n
            """)
    ResponseEntity<CreateTemplateResponse> create(CreateTemplateRequest createTemplateRequest);

}

