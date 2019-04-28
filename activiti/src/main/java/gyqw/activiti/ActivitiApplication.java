package gyqw.activiti;

import gyqw.activiti.util.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fred
 * 2019-04-25 2:16 PM
 */
@SpringBootApplication
public class ActivitiApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(ActivitiApplication.class);

    private final ProcessRuntime processRuntime;
    private final TaskRuntime taskRuntime;
    private final SecurityUtil securityUtil;

    public ActivitiApplication(ProcessRuntime processRuntime, TaskRuntime taskRuntime, SecurityUtil securityUtil) {
        this.processRuntime = processRuntime;
        this.taskRuntime = taskRuntime;
        this.securityUtil = securityUtil;
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.securityUtil.logInAs("system");

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        logger.info("> Available Process definitions: " + processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            logger.info("\t > Process definition: " + pd);
        }
    }
}