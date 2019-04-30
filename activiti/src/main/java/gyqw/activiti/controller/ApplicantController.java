package gyqw.activiti.controller;

import gyqw.activiti.util.SecurityUtil;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.DeleteProcessPayloadBuilder;
import org.activiti.api.process.model.builders.GetVariablesPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessAdminRuntime;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.AssignTaskPayloadBuilder;
import org.activiti.api.task.model.builders.CompleteTaskPayloadBuilder;
import org.activiti.api.task.model.builders.GetTasksPayloadBuilder;
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
import java.util.List;
import java.util.Map;

/**
 * @author fred
 * 2019/04/29 22:32
 */
@Controller
@RequestMapping("applicant")
public class ApplicantController {
    private Logger logger = LoggerFactory.getLogger(ApplicantController.class);

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

    @RequestMapping("index")
    public ModelAndView applicant(HttpServletRequest request,
                                  @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") Date start,
                                  @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") Date end,
                                  @RequestParam(value = "assign", required = false) String assign) {
        // 处理请假申请
        if (HttpMethod.POST.matches(request.getMethod()) &&
                !StringUtils.isEmpty(start) &&
                !StringUtils.isEmpty(end) &&
                !StringUtils.isEmpty(assign)) {
            long time = end.getTime() - start.getTime();
            int days = Math.toIntExact(time / 86400000);
            Map<String, Object> map = new HashMap<>();
            map.put("assign", assign);
            map.put("start", start);
            map.put("end", end);
            map.put("days", days);
            map.put("approved", false);

            // 开始新的流程
            this.securityUtil.logInAs("applicant");
            ProcessInstance processInstance = this.processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionKey("process-7523a1c8-3161-4875-9436-8dd5586eaa01")
                    .withVariables(map)
                    .build());
            logger.info("-->> process instance: {}", processInstance.toString());

            // 用管理员角色获取所有任务
            this.securityUtil.logInAs("admin");
            Page<Task> taskPage1 = this.taskAdminRuntime.tasks(Pageable.of(0, 10), new GetTasksPayloadBuilder()
                    .withProcessInstanceId(processInstance.getId())
                    .build());
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
        return new ModelAndView("applicant", "map", map);
    }

    @GetMapping("process/{processInstanceId}/delete")
    public ModelAndView applicantDeleteProcess(@PathVariable String processInstanceId) {
        this.securityUtil.logInAs("applicant");
        this.processRuntime.delete(
                new DeleteProcessPayloadBuilder()
                        .withProcessInstanceId(processInstanceId)
                        .build());

        return homepage();
    }

    @GetMapping("task/{taskId}/complete")
    public ModelAndView completeTask(@PathVariable String taskId) {
        this.securityUtil.logInAs("applicant");

        // 获取指定领导1
        Task task = taskRuntime.task(taskId);
        String processInstanceId = task.getProcessInstanceId();
        List<VariableInstance> variableInstanceList = this.processRuntime.variables(new GetVariablesPayloadBuilder()
                .withProcessInstanceId(processInstanceId)
                .build());
        String assign = "";
        for (VariableInstance variableInstance : variableInstanceList) {
            if ("assign".equals(variableInstance.getName())) {
                assign = variableInstance.getValue().toString();
            }
        }

        // 完成任务
        this.taskRuntime.complete(new CompleteTaskPayloadBuilder()
                .withTaskId(taskId)
                .build());

        // 指定审批领导1
        this.securityUtil.logInAs("admin");
        Page<Task> taskPage1 = this.taskAdminRuntime.tasks(Pageable.of(0, 10), new GetTasksPayloadBuilder()
                .withProcessInstanceId(processInstanceId)
                .build());
        if (taskPage1.getTotalItems() > 0) {
            for (Task task1 : taskPage1.getContent()) {
                this.taskAdminRuntime.assign(new AssignTaskPayloadBuilder()
                        .withTaskId(task1.getId())
                        .withAssignee(assign)
                        .build());
            }
        }

        return homepage();
    }

    private ModelAndView homepage() {
        return new ModelAndView("redirect:/applicant/index");
    }
}
