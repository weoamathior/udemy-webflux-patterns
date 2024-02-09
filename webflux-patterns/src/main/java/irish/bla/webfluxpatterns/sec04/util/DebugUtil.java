package irish.bla.webfluxpatterns.sec04.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import irish.bla.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebugUtil {
    public static void print(OrchestrationRequestContext ctx) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ctx);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            log.warn("Failed to pretty print the context", e);
        }
    }
}
