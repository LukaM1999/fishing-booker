package com.fishingbooker.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fishingbooker.model.Boat;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.model.Role;
import com.fishingbooker.repository.BoatRepository;
import com.fishingbooker.security.auth.SecurityConstants;
import com.fishingbooker.security.auth.TokenBasedAuth;
import com.fishingbooker.service.impl.BoatServiceImpl;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoatControllerTest {

    private static final String URL_PREFIX = "/boat";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private BoatRepository boatRepository;

    @Mock
    private Boat boat;

    @InjectMocks
    private BoatServiceImpl boatService;

    private String token;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(sharedHttpSession()).build();
        try {
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            this.token = "Bearer " + JWT.create()
                    .withSubject("Popaye")
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
            RegisteredUser user = new RegisteredUser();
            user.setUsername("Popaye");
            user.setPassword("$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i");
            user.setRole(new Role("BOAT_OWNER"));
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
    public void findBoat() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/find/4").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name").value("Monohull Bavaria Cruiser"))
                .andExpect(jsonPath("$.country").value("Serbia"))
                .andExpect(jsonPath("$.city").value("Subotica"));
    }

    @Test
    public void getAllBoat() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].name").value("Monohull Bavaria Cruiser"))
                .andExpect(jsonPath("$[0].country").value("Serbia"))
                .andExpect(jsonPath("$[0].city").value("Subotica"));
    }

    @Test
    public void getOwnerBoats() throws Exception {

        mockMvc.perform(get(URL_PREFIX + "/owner?username=Popaye").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].name").value("Monohull Bavaria Cruiser"))
                .andExpect(jsonPath("$[0].country").value("Serbia"))
                .andExpect(jsonPath("$[0].city").value("Subotica"));
    }

    @Test
    public void deleteBoat() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/delete/4").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
}
