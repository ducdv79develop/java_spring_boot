package com.local.ducdv.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.local.ducdv.entity.User;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiControllerTest extends AbstractTest {
	private final Integer ID = 22;
	
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void test_getAllUsers() throws Exception {
        String uri = "/api/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();

        JSONObject json = (JSONObject) (new JSONParser()).parse(content);
        String data = json.get("data").toString();

        User[] users = super.mapFromJson(data, User[].class);

        assertTrue(users.length > 0);
    }

    @Test
    public void test_findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/users/{id}", 10)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email").value("duccsv3@gmail.com"));
    }


    @Test
    public void test_create() throws Exception {
        String uri = "/api/user/create";
        User user = new User();
        user.setName("Unit Test");
        user.setEmail("unittest@gmail.com");

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email").value("unittest@gmail.com"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void test_update() throws Exception {
        String uri = "/api/user/update";
        User user = new User();
        user.setId(ID + 1);
        user.setName("Unit Test update");
        user.setEmail("unittestupdate@gmail.com");

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("Unit Test update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email").value("unittestupdate@gmail.com"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void test_delete() throws Exception {
        String uri = "/api/user/delete";
        User user = new User();
        user.setId(ID);

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
