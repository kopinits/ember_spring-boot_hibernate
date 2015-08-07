package br.com.markus.converter;

import br.com.markus.dto.TodoTaskDTO;
import br.com.markus.model.TodoTask;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Markus on 06/08/2015.
 */
@Component
public class TodoTaskConverter {

    public TodoTask toTask(TodoTaskDTO todoTaskDTO) {
        TodoTask todoTask = new TodoTask();
        todoTask.setId(Long.valueOf(todoTaskDTO.getId()));
        todoTask.setDescription(todoTaskDTO.getDescription());
        todoTask.setName(todoTaskDTO.getName());
        todoTask.setStartDate(todoTaskDTO.getStartDate());
        todoTask.setEndDate(todoTaskDTO.getEndDate());
        todoTask.setWhere(todoTaskDTO.getWhere());
        return todoTask;
    }

    public TodoTaskDTO fromTask(TodoTask todoTask) {
        TodoTaskDTO todoTaskDTO = new TodoTaskDTO();
        todoTaskDTO.setId(todoTask.getId().toString());
        todoTaskDTO.setDescription(todoTask.getDescription());
        todoTaskDTO.setName(todoTask.getName());
        todoTaskDTO.setStartDate(todoTask.getStartDate());
        todoTaskDTO.setEndDate(todoTask.getEndDate());
        todoTaskDTO.setWhere(todoTask.getWhere());
        return todoTaskDTO;
    }

    public Collection<TodoTaskDTO> fromTask(Iterator<TodoTask> todoTaskList) {
        ArrayList<TodoTaskDTO> todoTaskDTOs = new ArrayList<>();
        while (todoTaskList.hasNext()) {
            todoTaskDTOs.add(fromTask(todoTaskList.next()));
        }
        return todoTaskDTOs;
    }

}
