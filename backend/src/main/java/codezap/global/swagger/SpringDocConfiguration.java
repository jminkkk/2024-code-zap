package codezap.global.swagger;

import java.util.List;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("코드잽 API")
                .version("v1.0")
                .description("""
                        코드잽 API 명세서입니다. \n
                        모든 예외 응답의 메시지는 "detail" : "내부 서버 오류입니다." 와 같은 형태로 응답합니다. \n
                        """);

        return new OpenAPI()
                .servers(
                        List.of(
                                new Server()
                                        .url("https://api.code-zap.com")
                                        .description("클라우드에 배포되어 있는 테스트 서버입니다."),
                                new Server()
                                        .url("http://localhost:8080")
                                        .description("BE 팀에서 사용할 로컬 환경 테스트를 위한 서버입니다.")
                        )
                )
                .info(info);
    }

    @Bean
    public OperationCustomizer addCommonErrorResponses() {
        return new AuthOperationCustomizer();
    }
}
