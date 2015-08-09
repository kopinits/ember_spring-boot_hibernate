package br.com.markus.converter;

import br.com.markus.ApplicationTests;
import br.com.markus.dto.TodoTaskDTO;
import br.com.markus.model.TodoTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private void validateTaskData(TodoTaskDTO todoTaskDTO, TodoTask todoTask) throws ParseException {
        assert todoTask.getId().equals(Long.valueOf(todoTaskDTO.getId()));
        assert todoTask.getName().equals(todoTaskDTO.getName());
        assert todoTask.getDescription().equals(todoTaskDTO.getDescription());
        assert todoTask.getLocation().equals(todoTaskDTO.getLocation());
        SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
        assert todoTask.getStartDate().getTime() == sdf.parse(todoTaskDTO.getStartDate()).getTime();
        assert todoTask.getEndDate().getTime() == sdf.parse(todoTaskDTO.getEndDate()).getTime();
    }

    private TodoTask getDefaultTask() throws ParseException {
        SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
        TodoTask todoTask = new TodoTask();
        todoTask.setId(Long.valueOf(1));
        todoTask.setName("Default TodoTask");
        todoTask.setDescription("Default Description");
        todoTask.setLocation("Default Location");
        todoTask.setStartDate(sdf.parse("07/08/2015"));
        todoTask.setEndDate(sdf.parse("08/08/2015"));
        return todoTask;
    }
}