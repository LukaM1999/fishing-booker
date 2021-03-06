package com.fishingbooker.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fishingbooker.model.*;
import com.fishingbooker.security.auth.SecurityConstants;
import com.fishingbooker.security.auth.TokenBasedAuth;
import com.fishingbooker.util.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SaleSubscriptionControllerTest {
    private static final String URL_PREFIX = "/saleSubscription";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        try {
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            this.token = "Bearer " + JWT.create()
                    .withSubject("imbiamba")
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
            RegisteredUser user = new RegisteredUser();
            user.setUsername("imbiamba");
            user.setPassword("$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i");
            user.setRole(new Role("CUSTOMER"));
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
    public void getCustomerSubscriptionsTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/customer/{customerUsername}", "imbiamba")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerUsername", is("imbiamba")))
                .andExpect(jsonPath("$[0].ownerUsername", is("Piwneuh")))
                .andExpect(jsonPath("$[0].entityName", is("Prvenstvo Srbije")));
    }

    @Test
    public void createSubscriptionTest() throws Exception {
        SaleSubscription subscription = new SaleSubscription();
        subscription.setCustomerUsername("imbiamba");
        subscription.setOwnerUsername("Piwneuh");
        subscription.setEntityName("Silver Mirror");
        String json = TestUtil.json(subscription);
        mockMvc.perform(post(URL_PREFIX)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.customerUsername", is("imbiamba")))
                .andExpect(jsonPath("$.ownerUsername", is("Piwneuh")))
                .andExpect(jsonPath("$.entityName", is("Silver Mirror")));
    }
}
