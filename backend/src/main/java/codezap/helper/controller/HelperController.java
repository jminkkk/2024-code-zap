package codezap.helper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codezap.helper.service.CodeHelperService;
import codezap.helper.dto.MetaCodeResponse;
import codezap.helper.client.dto.MetaCodeClientRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/helper")
public class HelperController {

    private final CodeHelperService codeHelperService;

    @GetMapping("/meta")
    public ResponseEntity<MetaCodeResponse> getHelperCode(@RequestBody final MetaCodeClientRequest codes) {
        return ResponseEntity.ok(codeHelperService.createMetaCode(codes));
    }

    @GetMapping("/test")
    public ResponseEntity<MetaCodeResponse> getHelperTestCode(@RequestBody final MetaCodeClientRequest codes) {
        return ResponseEntity.ok(codeHelperService.createTestCode(codes));
    }
}
