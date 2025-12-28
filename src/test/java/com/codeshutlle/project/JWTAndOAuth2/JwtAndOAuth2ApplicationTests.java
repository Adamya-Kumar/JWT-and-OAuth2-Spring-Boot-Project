package com.codeshutlle.project.JWTAndOAuth2;

import com.codeshutlle.project.JWTAndOAuth2.entity.Session;
import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import com.codeshutlle.project.JWTAndOAuth2.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class JwtAndOAuth2ApplicationTests {
    private final SessionService sessionService;
	@Test
	void contextLoads() {
        Session newSession =Session.builder()
                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MiIsImVtYWlsIjoiYWRhbUBnbWFpbC5jb20iLCJuYW1lIjoiYWRhbSIsImlhdCI6MTc2Njk0Mzc2MiwiZXhwIjoxNzY2OTQ0MzYzfQ.EqERs_yjZWwc_07VGyhhNGXHUgg2EnP7uT2LdN3luoGt-8Mmk0KctZoibq2wYXeOFyNLd8tgF-CwvQLSr5HmTg")
                .user(new User())
                .build();
	    sessionService.generateNewSession(new User(),"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MiIsImVtYWlsIjoiYWRhbUBnbWFpbC5jb20iLCJuYW1lIjoiYWRhbSIsImlhdCI6MTc2Njk0Mzc2MiwiZXhwIjoxNzY2OTQ0MzYzfQ.EqERs_yjZWwc_07VGyhhNGXHUgg2EnP7uT2LdN3luoGt-8Mmk0KctZoibq2wYXeOFyNLd8tgF-CwvQLSr5HmTg");
    }

}
