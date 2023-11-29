package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.domain.MemberToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "member-token", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface MemberTokenClientService {

    @PostMapping("/member-token")
    MemberToken postMemberToken(@RequestBody MemberToken memberToken);

    @GetMapping("/member-token/search/existsByUsernameAndToken")
    Boolean existsByUsernameAndToken(@RequestParam("username") String username,
                                     @RequestParam("token") String token);
}
