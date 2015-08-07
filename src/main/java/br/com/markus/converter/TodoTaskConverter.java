package br.com.markus.converter;

import br.com.markus.dto.TodoTaskDTO;
import br.com.markus.model.TodoTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Date;
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
        if (StringUtils.isNotBlank(todoTaskDTO.getId())) {
            todoTask.setId(Long.valueOf(todoTaskDTO.getId()));
        }
        todoTask.setDescription(todoTaskDTO.getDescription());
        todoTask.setName(todoTaskDTO.getName());
        todoTask.setStartDate(new Date(Long.valueOf(todoTaskDTO.getStartDate())));
        todoTask.setEndDate(new Date(Long.valueOf(todoTaskDTO.getEndDate())));
        todoTask.setLocation(todoTaskDTO.getLocation());
        return todoTask;
    }

    public TodoTaskDTO fromTask(TodoTask todoTask) {
        TodoTaskDTO todoTaskDTO = new TodoTaskDTO();
        todoTaskDTO.setId(todoTask.getId().toString());
        todoTaskDTO.setDescription(todoTask.getDescription());
        todoTaskDTO.setName(todoTask.getName());
        todoTaskDTO.setStartDate(String.valueOf(todoTask.getStartDate().getTime()));
        todoTaskDTO.setEndDate(String.valueOf(todoTask.getEndDate().getTime()));
        todoTaskDTO.setLocation(todoTask.getLocation());
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
