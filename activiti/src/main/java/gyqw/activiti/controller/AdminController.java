package gyqw.activiti.controller;

import gyqw.activiti.util.SecurityUtil;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessAdminRuntime;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fred
 * 2019/04/29 23:42
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    private SecurityUtil securityUtil;
    private ProcessRuntime processRuntime;
    private ProcessAdminRuntime processAdminRuntime;
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

    @Autowired
    public void setProcessAdminRuntime(ProcessAdminRuntime processAdminRuntime) {
        this.processAdminRuntime = processAdminRuntime;
    }

    @GetMapping("index")
    public String index() {
        this.securityUtil.logInAs("admin");
        Page<ProcessInstance> processInstancePage = this.processAdminRuntime.processInstances(Pageable.of(0, 10));
//        HistoricProcessInstanceQuery
        return processInstancePage.toString();
    }
}
