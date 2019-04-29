package gyqw.activiti.controller;

import gyqw.activiti.util.SecurityUtil;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.CompleteTaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fred
 * 2019/04/29 22:33
 */
@Controller
@RequestMapping("leader")
public class LeaderController {
    private Logger logger = LoggerFactory.getLogger(LeaderController.class);

    private SecurityUtil securityUtil;
    private ProcessRuntime processRuntime;
    private TaskRuntime taskRuntime;
    private TaskAdminRuntime taskAdminRuntime;

    @Autowired
    public void setSecurityUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Autowired
    public void setProcessRuntime(ProcessRuntime processRuntime) {
        this.processRuntime = processRuntime;
    }

    @Autowired
    public void setTaskRuntime(TaskRuntime taskRuntime) {
        this.taskRuntime = taskRuntime;
    }

    @Autowired
    public void setTaskAdminRuntime(TaskAdminRuntime taskAdminRuntime) {
        this.taskAdminRuntime = taskAdminRuntime;
    }

    @GetMapping("{username}")
    public ModelAndView leader(@PathVariable String username) {
        this.securityUtil.logInAs(username);

        Page<Task> taskPage = this.taskRuntime.tasks(Pageable.of(0, 10));

        Map<String, Object> map = new HashMap<>();
        map.put("user", this.securityUtil.getUserDetails().getPrincipal());
        map.put("taskList", taskPage.getContent());
        return new ModelAndView("leader", "map", map);
    }

    @GetMapping("task/{taskId}/{agreeOrNot}")
    public ModelAndView taskAgreeOrNot(@PathVariable String taskId, @PathVariable String agreeOrNot) {
        this.securityUtil.logInAs("leader1");

        boolean approved = false;
        if ("agree".equals(agreeOrNot)) {
            approved = true;
        }
        this.taskRuntime.complete(new CompleteTaskPayloadBuilder()
                .withTaskId(taskId)
                .withVariable("approved", approved)
                .build());

        return homepage();
    }

    private ModelAndView homepage() {
        return new ModelAndView("redirect:/leader/leader1");
    }
}
