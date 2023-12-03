package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.domain.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "members", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface MemberClientService {

    @GetMapping("/members/search/findByUsername?projection=member-view")
    Member getMemberByUsername(@RequestParam("username") String username);

    @GetMapping("/members/search/existsByUsernameOrEmail")
    Boolean existMemberByUsernameOrEmail(@RequestParam("username") String username,
                                         @RequestParam("email") String email);

    @PostMapping("/members")
    Member postReservation(@RequestBody Member member);
}
