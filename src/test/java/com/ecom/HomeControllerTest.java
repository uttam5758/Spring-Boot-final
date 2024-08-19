package com.ecom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSignin() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testProducts() throws Exception {
        mockMvc.perform(get("/products")
                        .param("category", "electronics")
                        .param("pageNo", "0")
                        .param("pageSize", "12")
                        .param("ch", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("paramValue"))
                .andExpect(model().attributeExists("productsSize"))
                .andExpect(model().attributeExists("pageNo"))
                .andExpect(model().attributeExists("pageSize"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("isFirst"))
                .andExpect(model().attributeExists("isLast"));
    }

    @Test
    public void testProduct() throws Exception {
        int productId = 1;  // Replace with a valid product ID for testing
        mockMvc.perform(get("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("view_product"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testSaveUser() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "img",
                "profile.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        mockMvc.perform(multipart("/saveUser")
                        .file(mockFile)
                        .param("email", "test@example.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    public void testShowForgotPassword() throws Exception {
        mockMvc.perform(get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot_password.html"));
    }

    @Test
    public void testProcessForgotPassword() throws Exception {
        mockMvc.perform(post("/forgot-password")
                        .param("email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/forgot-password"));
    }

    @Test
    public void testShowResetPassword() throws Exception {
        String token = "validToken";  // Replace with a valid token for testing
        mockMvc.perform(get("/reset-password").param("token", token))
                .andExpect(status().isOk())
                .andExpect(view().name("reset_password"))
                .andExpect(model().attributeExists("token"));
    }

    @Test
    public void testResetPassword() throws Exception {
        String token = "validToken";  // Replace with a valid token for testing
        mockMvc.perform(post("/reset-password")
                        .param("token", token)
                        .param("password", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(model().attributeExists("msg"));
    }
    @Test
    public void testSearchProduct() throws Exception {
        mockMvc.perform(get("/search")
                        .param("ch", "laptop"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("categories"));
    }

}
