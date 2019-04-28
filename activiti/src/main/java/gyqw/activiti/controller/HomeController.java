package gyqw.activiti.controller;

import gyqw.activiti.util.SecurityUtil;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final SecurityUtil securityUtil;
    private final ProcessRuntime processRuntime;
    private final TaskRuntime taskRuntime;

    public HomeController(SecurityUtil securityUtil, ProcessRuntime processRuntime, TaskRuntime taskRuntime) {
        this.securityUtil = securityUtil;
        this.processRuntime = processRuntime;
        this.taskRuntime = taskRuntime;
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
        this.securityUtil.logInAs("applicant");

        // 处理请假申请
        if (HttpMethod.POST.matches(request.getMethod()) &&
                start != null && end != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("assign", assign);
            map.put("start", start);
            map.put("end", end);

            // 开始新的流程
            this.processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionId("11e7aab6-6961-11e9-ab78-0242bc0cc748")
                    .withVariable("content", map)
                    .build());
        }

        // 获取处理中的流程列表
        Page<ProcessInstance> processInstancePage = this.processRuntime.processInstances(Pageable.of(0, 10));
        if (processInstancePage.getTotalItems() > 0) {
            for (ProcessInstance processInstance : processInstancePage.getContent()) {
                logger.info(processInstance.toString());
            }
        }

        // 获取任务列表
        Page<Task> taskPage = this.taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage.getTotalItems() > 0) {
            for (Task task : taskPage.getContent()) {
                logger.info(task.toString());
            }
        }

        return new ModelAndView("home/applicant", "user", this.securityUtil.getUserDetails().getPrincipal());
    }
}
