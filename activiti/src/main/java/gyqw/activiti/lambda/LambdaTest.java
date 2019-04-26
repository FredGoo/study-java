package gyqw.activiti.lambda;

import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fred
 * 2019-04-25 2:15 PM
 */
public class LambdaTest {
    private Logger logger = LoggerFactory.getLogger(LambdaTest.class);

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();

        IntegrationContextImpl integrationContext = new IntegrationContextImpl();
        Content content = new Content("body", false, null);
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        integrationContext.setInBoundVariables(map);

        lambdaTest.connector().apply(integrationContext);
    }

    private Connector connector() {
        return integrationContext -> {
            Content contentToDiscard = (Content) integrationContext.getInBoundVariables().get("content");
            contentToDiscard.getTags().add(" :( ");
            integrationContext.addOutBoundVariable("content",
                    contentToDiscard);
            logger.info("Final Content: " + contentToDiscard);
            return integrationContext;
        };
    }
}
