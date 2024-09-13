package gm.tasks.Controller;

import gm.tasks.Model.Task;
import gm.tasks.Service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @GetMapping("/")
    public String initiate(ModelMap model) {
        List<Task> tasks = taskService.listTasks();
        tasks.forEach((task -> logger.info(task.toString())));
        model.put("tasks", tasks);
        return "index";
    }

    @GetMapping("/add")
    public String showAdd(){
        return "add";
    }

    @PostMapping("/add")
    public String addTask (@ModelAttribute("taskForm") Task task) {
        logger.info("Added task: " + task);
        taskService.saveTask(task);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable(value = "id") int taskId, ModelMap model) {
        Task task = taskService.searchTaskById(taskId);
        logger.info("Task to edit: " + task);
        model.put("task", task);
        return "edit";
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute("task") Task task) {
        logger.info("Task edited: " + task);
        taskService.saveTask(task);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable(value = "id") int taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        logger.info("Task deleted: " + task);
        taskService.deleteTask(task);
        return "redirect:/";
    }

}
