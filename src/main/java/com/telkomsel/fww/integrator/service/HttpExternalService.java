package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.exception.DisdukcapilException;
import com.telkomsel.fww.integrator.exception.PeduliLindungiException;
import com.telkomsel.fww.integrator.feign.service.ScheduleClientService;
import com.telkomsel.fww.integrator.payload.response.ResponseMidtrans;
import com.telkomsel.fww.integrator.payload.response.ResponsePedulilindungi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class HttpExternalService {

    @Value("${url.midtrans}")
    private String urlMidtrans;

    @Value("${url.disdukcapil}")
    private String urlDisdukcapil;

    @Value("${url.pedulilindungi}")
    private String urlPeduliLindungi;

    private final RestTemplate restTemplate;

    private final ScheduleClientService scheduleClientService;

    public HttpExternalService(RestTemplate restTemplate, ScheduleClientService scheduleClientService) {
        this.restTemplate = restTemplate;
        this.scheduleClientService = scheduleClientService;
    }

    public ResponseMidtrans sendPaymentInfo(String bookingCode,
                                            String scheduleCode) {

        Schedule schedule =
                scheduleClientService.getSchedulesByCode(scheduleCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> map = new HashMap<>();

        map.put("booking_code", bookingCode);
        map.put("price", schedule.getPrice());
        map.put("timeout", 3600);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<ResponseMidtrans> resp = restTemplate.postForEntity(urlMidtrans,
                entity, ResponseMidtrans.class);

        return resp.getBody();
    }

    public void getDisdukcapil(String nik) {

        try {
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(urlDisdukcapil)
                    .queryParam("nik", nik).build();

            restTemplate.getForEntity(builder.toUriString(),
                    Object.class);
        } catch (Exception e) {
            throw new DisdukcapilException();
        }
    }

    public void getPeduliLindungi(String nik) {

        try {
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(urlPeduliLindungi)
                    .queryParam("nik", nik).build();

            ResponseEntity<ResponsePedulilindungi> resp =
                    restTemplate.getForEntity(builder.toUriString(),
                            ResponsePedulilindungi.class);

            if (Objects.requireNonNull(resp.getBody()).getVaccine().size() < 3) {
                throw new PeduliLindungiException();
            }
        } catch (Exception e) {
            throw new PeduliLindungiException();
        }
    }
}
