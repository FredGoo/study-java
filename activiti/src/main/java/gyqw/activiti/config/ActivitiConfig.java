package gyqw.activiti.config;

import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author fred
 * 2019-04-30 11:56 AM
 */
@SpringBootConfiguration
public class ActivitiConfig {
    private Logger logger = LoggerFactory.getLogger(ActivitiConfig.class);

    @Bean
    public ProcessRuntimeEventListener<ProcessCompletedEvent> processCompletedListener() {
        return processCompleted -> logger.info("-->> Process Completed: {} We can send a notification to the initiator: {}",
                processCompleted.toString(), processCompleted.getEntity().getInitiator());
    }

    @Bean
    public VariableEventListener<VariableUpdatedEvent> variableUpdatedEventVariableEventListener() {
        return variableUpdatedEvent -> logger.info("-->> Variable Updated: {}", variableUpdatedEvent.getEntity().getValue().toString());
    }
}
