package com.animeweb.service.impl;

import com.animeweb.dto.UserPackedDTO;
import com.animeweb.dto.UserServicePackedDTO;
import com.animeweb.entities.User;
import com.animeweb.entities.UserPacked;
import com.animeweb.mapper.UserPackedMapper;
import com.animeweb.repository.UserPackedRepository;
import com.animeweb.service.UserPackedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserPackedServiceImpl implements UserPackedService {
    @Autowired
    UserPackedRepository userPackedRepository;

    public void save(UserPacked userPacked) {
        userPackedRepository.save(userPacked);
    }

    @Override
    public boolean checkUserBuyedService(User idUser) {
        UserPacked isBuyed = userPackedRepository.checkUserBuyedService(idUser);
        if (isBuyed == null) {
            return false;
        } else {
            Date now = new Date();
            if (isBuyed.getExpiredTime().compareTo(now)>0){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public List<UserServicePackedDTO> getServicePackActiveByUserId(Long userId) {
        List<UserPacked> list= userPackedRepository.GetAllServicePackBoughtActive(userId);
        List<UserServicePackedDTO> userPackedDTOList=new ArrayList<>();
        for (UserPacked u:list
             ) {
            userPackedDTOList.add(UserPackedMapper.mapToEntity(u));
        }
        return userPackedDTOList;
    }

    @Override
    public List<UserServicePackedDTO> getServicePackExpiredByUserId(Long userId) {
        List<UserPacked> list= userPackedRepository.GetAllServicePackBoughtExpired(userId);
        List<UserServicePackedDTO> userPackedDTOList=new ArrayList<>();
        for (UserPacked u:list
        ) {
            userPackedDTOList.add(UserPackedMapper.mapToEntity(u));
        }
        return userPackedDTOList;
    }
}
