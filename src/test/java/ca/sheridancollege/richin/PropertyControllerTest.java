package ca.sheridancollege.richin;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "USER") 
    void shouldSaveValidProperty() throws Exception {
        mockMvc.perform(post("/save")
                .param("address", "456 Oak Ave")
                .param("price", "450000")
                .param("status", "For Sale"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "USER")  
    void shouldRejectInvalidProperty() throws Exception {
    	mockMvc.perform(post("/save")
                .param("address", "")
                .param("price", "-100")
                .param("status", ""))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(view().name(either(is("add")).or(is("edit"))));
    }
}
