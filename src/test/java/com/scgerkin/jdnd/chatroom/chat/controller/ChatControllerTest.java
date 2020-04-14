package com.scgerkin.jdnd.chatroom.chat.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(mockMvc);
    }

    @Test
    public void loginStatusIsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void loginViewIsLogin() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void indexStatusIsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/index"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void indexViewIsLoginWithNoParameters() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/index"))
            .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void indexUsernameAttributeIsSetFromPath() throws Exception {
        String username = "artichoke";
        this.mockMvc.perform(
            MockMvcRequestBuilders.get("/index?username="+username))
            .andExpect(MockMvcResultMatchers.model().attribute("username", username));
    }

    @Test
    public void indexNullUsernameReturnsLoginView() throws Exception {
        this.mockMvc.perform(
            MockMvcRequestBuilders.get("/index?username="))
            .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void indexViewIsChatWithValidUsername() throws Exception {
        this.mockMvc.perform(
            MockMvcRequestBuilders.get("/index?username=artichoke"))
            .andExpect(MockMvcResultMatchers.view().name("chat"));
    }


}
