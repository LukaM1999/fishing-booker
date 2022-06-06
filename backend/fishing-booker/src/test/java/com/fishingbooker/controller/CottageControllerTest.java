package com.fishingbooker.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fishingbooker.model.Cottage;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.model.Role;
import com.fishingbooker.repository.CottageRepository;
import com.fishingbooker.security.auth.SecurityConstants;
import com.fishingbooker.security.auth.TokenBasedAuth;
import com.fishingbooker.service.impl.CottageServiceImpl;
import com.fishingbooker.util.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CottageControllerTest {
    private static final String URL_PREFIX = "/cottage";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private CottageRepository cottageRepository;

    @Mock
    private Cottage cottage;

    @InjectMocks
    private CottageServiceImpl cottageService;

    private String token;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(sharedHttpSession()).build();
        try {
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            this.token = "Bearer " + JWT.create()
                    .withSubject("Piwneuh")
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
            RegisteredUser user = new RegisteredUser();
            user.setUsername("Piwneuh");
            user.setPassword("$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i");
            user.setRole(new Role("COTTAGE_OWNER"));
            TokenBasedAuth authentication = new TokenBasedAuth(user);
            authentication.setToken(token);
            ctx.setAuthentication(authentication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getUserTestAccessDenied() throws Exception {
        Exception exception = Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/user/all").header("Authorization", token));
        });
        assertTrue(exception.getCause() instanceof AccessDeniedException);
    }

    @Test
    public void findCottageTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/find/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name").value("Prvenstvo Srbije"))
                .andExpect(jsonPath("$.country").value("Serbia"))
                .andExpect(jsonPath("$.city").value("Petrovaradin"));
    }

    @Test
    public void getAllCottagesTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].name").value("Prvenstvo Srbije"))
                .andExpect(jsonPath("$[0].country").value("Serbia"))
                .andExpect(jsonPath("$[0].city").value("Petrovaradin"));
    }

    @Test
    public void getOwnersCottagesTest() throws Exception {

        mockMvc.perform(get(URL_PREFIX + "/owner?username=Piwneuh").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].name").value("Prvenstvo Srbije"))
                .andExpect(jsonPath("$[0].country").value("Serbia"))
                .andExpect(jsonPath("$[0].city").value("Petrovaradin"));
    }

    @Test
    public void deleteCottageTest() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/delete/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }


}
