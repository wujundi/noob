package wujundi.noob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wujundi.noob.service.TaskService;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(path = {"/manage"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String index() {
        return "index func works";
    }

}
