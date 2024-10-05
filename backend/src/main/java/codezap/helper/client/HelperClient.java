package codezap.helper.client;

import codezap.helper.dto.CodeHelperRequest;
import codezap.helper.dto.MetaCodeResponse;

public interface HelperClient {

    MetaCodeResponse confirm(CodeHelperRequest codeHelperRequest);
}
