package br.com.markus.controller;

import br.com.markus.converter.TodoTaskConverter;
import br.com.markus.dao.TodoTaskDAO;
import br.com.markus.dto.TodoTaskDTO;
import br.com.markus.model.TodoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Markus on 07/08/2015.
 */
@RestController
@RequestMapping("/task")
public class TodoTaskController {

    @Autowired
    private TodoTaskDAO taskDAO;
    @Autowired
    private TodoTaskConverter converter;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public TodoTaskDTO createTask(@RequestBody TodoTaskDTO taskDTO) throws Exception {
        TodoTask task = converter.toTask(taskDTO);
        taskDAO.save(task);
        return converter.fromTask(task);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<TodoTaskDTO> listTask() {
        Iterable<TodoTask> tasks = taskDAO.findAll();
        return converter.fromTask(tasks.iterator());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public TodoTaskDTO getTask(@RequestBody TodoTaskDTO taskDTO) {
        TodoTask task = taskDAO.findOne(Long.valueOf(taskDTO.getId()));
        return converter.fromTask(task);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Collection<TodoTaskDTO> removeTask(@RequestBody TodoTaskDTO taskDTO) {
        taskDAO.delete(Long.valueOf(taskDTO.getId()));
        return listTask();
    }
}
