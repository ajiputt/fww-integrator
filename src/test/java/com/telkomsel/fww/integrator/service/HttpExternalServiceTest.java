package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.exception.DisdukcapilException;
import com.telkomsel.fww.integrator.exception.PaymentException;
import com.telkomsel.fww.integrator.exception.PeduliLindungiException;
import com.telkomsel.fww.integrator.feign.service.ScheduleClientService;
import com.telkomsel.fww.integrator.payload.response.ResponseMidtrans;
import com.telkomsel.fww.integrator.payload.response.ResponsePedulilindungi;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpExternalServiceTest {

    HttpExternalService httpExternalService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ScheduleClientService scheduleClientService;

    @BeforeEach
    void init() {
        httpExternalService = new HttpExternalService(restTemplate,
                scheduleClientService);
        ReflectionTestUtils.setField(httpExternalService, "urlMidtrans",
                "https://dummy.abc");
        ReflectionTestUtils.setField(httpExternalService, "urlDisdukcapil",
                "https://dummy.abc");
        ReflectionTestUtils.setField(httpExternalService, "urlPeduliLindungi", "https://dummy.abc");
    }

    @Test
    void sendPaymentInfo() {
        Schedule schedule = Schedule.builder().price(new BigDecimal(1)).build();

        when(scheduleClientService.getSchedulesByCode(anyString())).thenReturn(schedule);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(new ResponseEntity<>(ResponseMidtrans.builder().statusCode("00").build(), HttpStatus.OK));

        ResponseMidtrans resp =
                httpExternalService.sendPaymentInfo("test", "test2");

        Assertions.assertThat(resp.getStatusCode()).isEqualTo("00");
    }

    @Test
    void sendPaymentInfoFailed() {
        Schedule schedule = Schedule.builder().price(new BigDecimal(1)).build();

        when(scheduleClientService.getSchedulesByCode(anyString())).thenReturn(schedule);
        doThrow(PaymentException.class).when(restTemplate)
                .postForEntity(anyString(), any(), any());

        assertThrows(PaymentException.class,
                () -> httpExternalService.sendPaymentInfo("test", "test"));
    }

    @Test
    void getDisdukcapil() {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>("", HttpStatus.OK));


        httpExternalService.getDisdukcapil("test");

        verify(restTemplate).getForEntity("https://dummy.abc?nik=test", Object.class);
    }

    @Test
    void getDisdukcapilFailed() {
        doThrow(DisdukcapilException.class).when(restTemplate)
                .getForEntity(anyString(), any());

        assertThrows(DisdukcapilException.class,
                () -> httpExternalService.getDisdukcapil("test"));
    }

    @Test
    void getPeduliLindungi() {

        ResponsePedulilindungi resp = ResponsePedulilindungi.builder()
                .vaccine(Arrays.asList(ResponsePedulilindungi.Vaccine.builder().build(),
                        ResponsePedulilindungi.Vaccine.builder().build(),
                        ResponsePedulilindungi.Vaccine.builder().build())).build();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(resp, HttpStatus.OK));


        httpExternalService.getPeduliLindungi("test");

        verify(restTemplate).getForEntity("https://dummy.abc?nik=test",
                ResponsePedulilindungi.class);
    }

    @Test
    void getPeduliLindungiException() {

        ResponsePedulilindungi resp = ResponsePedulilindungi.builder()
                .vaccine(Arrays.asList(ResponsePedulilindungi.Vaccine.builder().build(),
                        ResponsePedulilindungi.Vaccine.builder().build())).build();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(resp, HttpStatus.OK));


        assertThrows(PeduliLindungiException.class,
                () -> httpExternalService.getPeduliLindungi("test"));

    }
}