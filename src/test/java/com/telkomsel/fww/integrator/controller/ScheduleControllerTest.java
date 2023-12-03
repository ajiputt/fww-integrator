package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(value = ScheduleController.class, excludeAutoConfiguration =
        {SecurityAutoConfiguration.class})
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    void getSchedules() throws Exception {
        List<Schedule> resp =
                Arrays.asList(Schedule.builder().code("Code1").price(new BigDecimal(1)).build(),
                        Schedule.builder().code("Code2").price(new BigDecimal(2)).build());

        when(scheduleService.getSchedule(anyString(), anyString(), anyString())).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/schedules")
                        .param("departure", "A")
                        .param("arrival", "B")
                        .param("date", "2024-01-01")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].code").value(
                        "Code1"));
    }
}