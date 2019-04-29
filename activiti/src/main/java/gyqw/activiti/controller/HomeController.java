package gyqw.activiti.controller;

import gyqw.activiti.util.SecurityUtil;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.DeleteProcessPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.AssignTaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fred
 * 2019-04-28 11:42 AM
 */
@Controller
@RequestMapping("home")
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

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

    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("layout");
    }

    @RequestMapping("applicant")
    public ModelAndView applicant(HttpServletRequest request,
                                  @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") Date start,
                                  @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") Date end,
                                  @RequestParam(value = "assign", required = false) String assign) {
        // 处理请假申请
        if (HttpMethod.POST.matches(request.getMethod()) &&
                !StringUtils.isEmpty(start) &&
                !StringUtils.isEmpty(end) &&
                !StringUtils.isEmpty(assign)) {
            Map<String, Object> map = new HashMap<>();
            map.put("assign", assign);
            map.put("start", start);
            map.put("end", end);

            // 开始新的流程
            this.securityUtil.logInAs("applicant");
            ProcessInstance processInstance = this.processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionId("11e7aab6-6961-11e9-ab78-0242bc0cc748")
                    .withVariable("content", map)
                    .build());
            logger.info("-->> process instance: {}", processInstance.toString());

            // 用管理员角色获取所有任务
            this.securityUtil.logInAs("admin");
            Page<Task> taskPage1 = this.taskAdminRuntime.tasks(Pageable.of(0, 10));
            if (taskPage1.getTotalItems() > 0) {
                for (Task task : taskPage1.getContent()) {
                    logger.info(task.toString());
                    this.taskAdminRuntime.assign(new AssignTaskPayloadBuilder()
                            .withTaskId(task.getId())
                            .withAssignee("applicant")
                            .build());
                }
            }
        }

        // 获取处理中的流程列表
        this.securityUtil.logInAs("applicant");
        Page<ProcessInstance> processInstancePage = this.processRuntime.processInstances(Pageable.of(0, 10));

        // 获取处理中的任务
        Page<Task> taskPage = this.taskRuntime.tasks(Pageable.of(0, 10));

        Map<String, Object> map = new HashMap<>();
        map.put("user", this.securityUtil.getUserDetails().getPrincipal());
        map.put("processList", processInstancePage.getContent());
        map.put("taskList", taskPage.getContent());
        return new ModelAndView("home/applicant", "map", map);
    }

    @GetMapping("applicant/delete/process/{processInstanceId}")
    public ModelAndView applicantDeleteProcess(@PathVariable String processInstanceId) {
        this.securityUtil.logInAs("applicant");
        this.processRuntime.delete(
                new DeleteProcessPayloadBuilder()
                        .withProcessInstanceId(processInstanceId)
                        .build());

        return new ModelAndView("redirect:/home/applicant");
    }

    @GetMapping("leader/{username}")
    public ModelAndView leader(@PathVariable String username) {
        this.securityUtil.logInAs(username);

        Page<Task> taskPage = this.taskRuntime.tasks(Pageable.of(0, 10));

        Map<String, Object> map = new HashMap<>();
        map.put("user", this.securityUtil.getUserDetails().getPrincipal());
        map.put("taskList", taskPage.getContent());
        return new ModelAndView("home/leader", "map", map);
    }
}
