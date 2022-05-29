package com.fishingbooker.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fishingbooker.dto.ReservationHistoryDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.RegisteredUserRepository;
import com.fishingbooker.security.auth.SecurityConstants;
import com.fishingbooker.security.auth.TokenBasedAuth;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ReservationControllerTest {
    private static final String URL_PREFIX = "/reservation";

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
            this.token = "Bearer " +  JWT.create()
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
    public void reserveRentableTest() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Piwneuh");
        reservation.setName("Prvenstvo Srbije");
        reservation.setType(ReservationType.COTTAGE);
        reservation.setStartTime(LocalDateTime.parse("2022-06-23T00:00:00"));
        reservation.setEndTime(LocalDateTime.parse("2022-06-26T00:00:00"));
        reservation.setPrice(90);
        reservation.setGuests(1);
        reservation.setAdditionalServices("");
        String json = TestUtil.json(reservation);

        mockMvc.perform(post(URL_PREFIX + "/reserveRentable/{rentableId}", 1)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is("Prvenstvo Srbije")))
                .andExpect(jsonPath("$.customerUsername", is("imbiamba")))
                .andExpect(jsonPath("$.ownerUsername", is("Piwneuh")))
                .andExpect(jsonPath("$.price", is(90.0)));
    }

    @Test
    public void cancelReservationTest() throws Exception {
        mockMvc.perform(patch(URL_PREFIX + "/cancelReservation/{reservationId}", 2)
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelReservationTestFail() throws Exception {
        Exception exception = Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(patch(URL_PREFIX + "/cancelReservation/{reservationId}", 1)
                            .header("Authorization", token));
        });
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
    }

    @Test
    public void getFinishedReservationsTest() throws Exception {
        ReservationHistoryDTO reservationHistoryDTO = new ReservationHistoryDTO();
        reservationHistoryDTO.setUsername("imbiamba");
        reservationHistoryDTO.setType(ReservationType.COTTAGE);
        reservationHistoryDTO.setIsCustomer(true);
        String json = TestUtil.json(reservationHistoryDTO);

        mockMvc.perform(post(URL_PREFIX + "/getFinishedReservations")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Silver Mirror")))
                .andExpect(jsonPath("$[0].customerUsername", is("imbiamba")))
                .andExpect(jsonPath("$[0].ownerUsername", is("Piwneuh")))
                .andExpect(jsonPath("$[0].price", is(300.0)));
    }

    @Test
    public void addReviewTest() throws Exception {
        Review review = new Review();
        review.setComment("This is a review");
        review.setOwnerUsername("Piwneuh");
        review.setRentableName("Silver Mirror");
        review.setReservationId(1L);
        review.setOwnerRating(4);
        review.setRentableRating(5);
        review.setReservationType(ReservationType.COTTAGE);
        String json = TestUtil.json(review);

        mockMvc.perform(post(URL_PREFIX + "/review")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
