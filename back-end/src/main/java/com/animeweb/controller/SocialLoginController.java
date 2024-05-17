package com.animeweb.controller;

import com.animeweb.dto.SocialUser;
import com.animeweb.entities.User;
import com.animeweb.service.impl.AccountOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@RestController
public class SocialLoginController {
    @Autowired
    AccountOAuth2UserService accountOAuth2UserService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login/google")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = (String) oAuth2User.getAttribute("email");
        String name = (String) oAuth2User.getAttribute("name");
        String givenName = (String) oAuth2User.getAttribute("given_name");
        String id = (String) oAuth2User.getAttribute("sub");
        String pictureUrl = (String) oAuth2User.getAttribute("picture");
        User socialUser = accountOAuth2UserService.findByEmailGoogle(email);
        Date now = java.sql.Date.valueOf(LocalDate.now());
        if (socialUser == null) {

            SocialUser socialUser1 = new SocialUser(name, name, id, email, pictureUrl, id, now,2);
            accountOAuth2UserService.createAccount(socialUser1);
        }
        return oAuth2User.getAttributes();

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login/facebook")
    public Map<String, Object> getUserFacebook(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = (String) oAuth2User.getAttribute("email");
        String name = (String) oAuth2User.getAttribute("name");
        String id = (String) oAuth2User.getAttribute("id");
        Map<String, Map<String, Object>> pictureData = oAuth2User.getAttribute("picture");
        String pictureUrl = null;
        if (pictureData != null) {
            Map<String, Object> data = pictureData.get("data");
            if (data != null) {
                pictureUrl = (String) data.get("url");
            }
        }
        User socialUser = accountOAuth2UserService.findByEmailFacebook(email);
        Date now = java.sql.Date.valueOf(LocalDate.now());
        if (socialUser == null) {
            System.out.println(name);
            SocialUser socialUser1 = new SocialUser(name, name, id, email, pictureUrl, id, now,3);
            accountOAuth2UserService.createAccount(socialUser1);
        }
        return oAuth2User.getAttributes();
    }


}