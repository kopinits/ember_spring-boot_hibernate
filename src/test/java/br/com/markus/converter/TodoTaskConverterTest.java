package br.com.markus.converter;

import br.com.markus.ApplicationTests;
import br.com.markus.dto.TodoTaskDTO;
import br.com.markus.model.TodoTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Markus on 06/08/2015.
 */
public class TodoTaskConverterTest extends ApplicationTests {

    @Autowired
    TodoTaskConverter todoTaskConverter;

    @Test
    public void testToTask() throws Exception {
        TodoTask defaultTodoTask = getDefaultTask();
        TodoTaskDTO todoTaskDTO = todoTaskConverter.fromTask(defaultTodoTask);
        TodoTask todoTask = todoTaskConverter.toTask(todoTaskDTO);
        validateTaskData(todoTaskDTO, todoTask);
    }

    @Test
    public void testFromTask() throws Exception {
        TodoTask defaultTodoTask = getDefaultTask();
        TodoTaskDTO todoTaskDTO = todoTaskConverter.fromTask(defaultTodoTask);
        validateTaskData(todoTaskDTO, defaultTodoTask);
    }

    private void validateTaskData(TodoTaskDTO todoTaskDTO, TodoTask todoTask) {
        assert todoTask.getId().equals(Long.valueOf(todoTaskDTO.getId()));
        assert todoTask.getName().equals(todoTaskDTO.getName());
        assert todoTask.getDescription().equals(todoTaskDTO.getDescription());
        assert todoTask.getLocation().equals(todoTaskDTO.getLocation());
        assert String.valueOf(todoTask.getStartDate().getTime()).equals(todoTaskDTO.getStartDate());
        assert String.valueOf(todoTask.getEndDate().getTime()).equals(todoTaskDTO.getEndDate());
    }

    private TodoTask getDefaultTask(){
        TodoTask todoTask = new TodoTask();
        todoTask.setId(Long.valueOf(1));
        todoTask.setName("Default TodoTask");
        todoTask.setDescription("Default Description");
        todoTask.setLocation("Default Location");
        todoTask.setStartDate(new Date());
        todoTask.setEndDate(new Date());
        return todoTask;
    }
}