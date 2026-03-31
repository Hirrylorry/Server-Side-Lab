package com.stu.helloserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("操作成功"))
                .andExpect(jsonPath("$.data.id").value(1001))
                .andExpect(jsonPath("$.data.name").value("用户1001"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "张三",
                                  "age": 21
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("操作成功"))
                .andExpect(jsonPath("$.data.id").value(1001))
                .andExpect(jsonPath("$.data.name").value("张三"))
                .andExpect(jsonPath("$.data.age").value(21));
    }

    @Test
    void shouldRejectUpdateUserWithoutToken() throws Exception {
        mockMvc.perform(put("/api/users/1002")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "李四",
                                  "age": 22
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value("非法操作，敏感动作 [PUT] 必须携带有效 Token"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void shouldUpdateUserWithToken() throws Exception {
        mockMvc.perform(put("/api/users/1002")
                        .header("Authorization", "mock-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "李四",
                                  "age": 22
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("操作成功"))
                .andExpect(jsonPath("$.data.id").value(1002))
                .andExpect(jsonPath("$.data.name").value("李四"));
    }

    @Test
    void shouldRejectDeleteUserWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/users/1003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value("非法操作，敏感动作 [DELETE] 必须携带有效 Token"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void shouldDeleteUserWithToken() throws Exception {
        mockMvc.perform(delete("/api/users/1003")
                        .header("Authorization", "mock-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("操作成功"))
                .andExpect(jsonPath("$.data").value("已移除 ID 为 1003 的用户"));
    }

    @Test
    void shouldLoginWithoutToken() throws Exception {
        mockMvc.perform(post("/api/users/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("操作成功"))
                .andExpect(jsonPath("$.data").value("mock-token"));
    }

    @Test
    void shouldHandleExceptionAsJson() throws Exception {
        mockMvc.perform(get("/api/users/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("系统繁忙，请稍后再试"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
