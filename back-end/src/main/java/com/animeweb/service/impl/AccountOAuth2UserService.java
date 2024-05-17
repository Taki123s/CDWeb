package com.animeweb.service.impl;

import com.animeweb.dto.SocialUser;
import com.animeweb.entities.User;
import com.animeweb.mapper.SocialUserMapper;
import com.animeweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AccountOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public User findByEmailGoogle(String email) {
        return userRepository.findByEmailGoogle(email);
    }
    public User findByEmailFacebook(String email){
       return userRepository.findByEmailFacebook(email);
    }

    public SocialUser createAccount(SocialUser socialUser) {
        User account = SocialUserMapper.mapToAccount(socialUser);
        User addAccount = userRepository.save(account);
        return SocialUserMapper.mapToDTO(addAccount);
    }
}