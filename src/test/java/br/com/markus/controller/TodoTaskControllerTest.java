package br.com.markus.controller;

import br.com.markus.ApplicationTests;
import br.com.markus.converter.TodoTaskConverter;
import br.com.markus.dao.TodoTaskDAO;
import br.com.markus.dto.TodoTaskDTO;
import br.com.markus.model.TodoTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Markus on 07/08/2015.
 */
public class TodoTaskControllerTest extends ApplicationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    TodoTaskDAO taskDAO;

    @Autowired
    TodoTaskConverter converter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateTask() throws Exception {
        mockMvc.perform(post("/task/save")
                .content(asJsonString(getDefaultTaskDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateTaskFail() throws Exception {
        mockMvc.perform(get("/task/save")
                .content(asJsonString(getDefaultTaskDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testGetTask() throws Exception {
        TodoTaskDTO todoTaskDTO = persistNewTodoTask();
        mockMvc.perform(get("/task/get")
                .content(asJsonString(todoTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskFail() throws Exception {
        TodoTaskDTO todoTaskDTO = persistNewTodoTask();
        mockMvc.perform(post("/task/get")
                .content(asJsonString(todoTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testListTask() throws Exception {
        mockMvc.perform(get("/task/list")
                .content(asJsonString(getDefaultTaskDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testListTaskFail() throws Exception {
        mockMvc.perform(post("/task/list")
                .content(asJsonString(getDefaultTaskDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testUpdateTask() throws Exception {
        TodoTaskDTO todoTaskDTO = persistNewTodoTask();
        todoTaskDTO.setDescription("Updated Description");
        mockMvc.perform(post("/task/save")
                .content(asJsonString(todoTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testRemoveTask() throws Exception {
        TodoTaskDTO todoTaskDTO = persistNewTodoTask();
        mockMvc.perform(delete("/task/remove")
                .content(asJsonString(todoTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveTaskFail() throws Exception {
        TodoTaskDTO todoTaskDTO = persistNewTodoTask();
        mockMvc.perform(post("/task/remove")
                .content(asJsonString(todoTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TodoTaskDTO getDefaultTaskDTO() {
        TodoTaskDTO todoTaskDTO = new TodoTaskDTO();
        todoTaskDTO.setName("Default TodoTask");
        todoTaskDTO.setDescription("Default Description");
        todoTaskDTO.setLocation("Default Location");
        todoTaskDTO.setStartDate("07/08/2015");
        todoTaskDTO.setEndDate("08/08/2015");
        return todoTaskDTO;
    }

    private TodoTaskDTO persistNewTodoTask() throws Exception {
        TodoTask savedTask = taskDAO.save(converter.toTask(getDefaultTaskDTO()));
        return converter.fromTask(savedTask);
    }
}