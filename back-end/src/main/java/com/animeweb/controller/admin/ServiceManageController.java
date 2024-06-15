package com.animeweb.controller.admin;

import com.animeweb.dto.payment.ServicePackAdmin;
import com.animeweb.dto.payment.ServicePackDTO;
import com.animeweb.dto.payment.UserPackedDTO;
import com.animeweb.dto.user.UserServicePackedDTO;
import com.animeweb.entities.ServicePack;
import com.animeweb.entities.UserPacked;
import com.animeweb.mapper.ServicePackMapper;
import com.animeweb.mapper.UserPackedMapper;
import com.animeweb.repository.ServicePackRepository;
import com.animeweb.service.UserPackedService;
import com.animeweb.service.impl.CloudinaryService;
import com.animeweb.service.impl.ServicePackServiceImpl;
import com.animeweb.service.impl.UserPackedServiceImpl;
import com.animeweb.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/servicePack")
public class ServiceManageController {
    @Autowired
    private ServicePackRepository servicePackRepository;
    @Autowired
    private ServicePackServiceImpl servicePackService;
    @Autowired
    private UserPackedServiceImpl userPackedService;
    @Autowired
    private UserPackedService userPackedServices;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CloudinaryService uploadService;
    private static final Logger logger = LoggerFactory.getLogger(ServiceManageController.class);
    @GetMapping
    public ResponseEntity<List<ServicePackDTO>> getServiceList() {
        return ResponseEntity.ok(servicePackService.getListServicePack());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> editServicePack(@PathVariable Long id, @ModelAttribute ServicePackAdmin updatedService) throws Exception {
        ServicePack servicePack = ServicePackMapper.mapToEntity(updatedService);
        String avatar = uploadService.uploadServiceAvt(updatedService.getFile(),updatedService.getId());

        servicePack.setService_img(avatar);
        servicePack.setUpdateAt(new Date());
        servicePackService.save(servicePack);
        logger.info("Service with id "+id+" has been edited by admin..",id);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> updateServicePack(@PathVariable Long id) throws Exception {

        servicePackService.updateServicePack(id);
        logger.warn("Service with id "+id+" has been deleted by admin..",id);
        return ResponseEntity.ok("Service Pack updated successfully");
    }

    @PostMapping("/create")
    public ResponseEntity<ServicePackAdmin> createServicePack(@ModelAttribute ServicePackAdmin service) throws IOException {
        ServicePack servicePack = ServicePackMapper.mapToEntity(service);
        long id = servicePackRepository.findFirstByStatusNotNullOrderByIdDesc().getId()+1;
        if (servicePackService.existType(service.getService_type())) {
            return ResponseEntity.ok(null);
        }
        String avatar = uploadService.uploadServiceAvt(service.getFile(), id);
        servicePack.setService_img(avatar);
        System.out.println(avatar);
        Date now = new Date();
        servicePack.setCreateAt(now);
//        System.out.println(service.getService_type());


        servicePackService.createServicePack(servicePack);
        logger.info("Service with id "+id+" has been created by admin..",id);
        return ResponseEntity.ok(ServicePackMapper.Maptoadmin(servicePack));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserPackedDTO>> getAll() {
        List<UserPacked> userPackeds = userPackedService.findAllUserPacked();
        List<UserPackedDTO> userPackedDTOS = new ArrayList<>();
        for (UserPacked u : userPackeds
        ) {
            UserPackedDTO userPackedDTO = UserPackedMapper.mapToDTO(u);

            userPackedDTO.setStatus(u.getStatus());
            userPackedDTO.setId(u.getId());
            userPackedDTO.setCreatedAt(u.getCreatedAt());
            userPackedDTOS.add(userPackedDTO);
        }
        return ResponseEntity.ok(userPackedDTOS);
    }

    @GetMapping("/getAll/{idUser}")
    public ResponseEntity<List<UserPackedDTO>> getAllByUser(@PathVariable("idUser") Long idUser) {
        List<UserPacked> userPackeds = userPackedService.findAllUserPackedById(userService.findUserById(idUser));
        List<UserPackedDTO> userPackedDTOS = new ArrayList<>();
        for (UserPacked u : userPackeds) {
            UserPackedDTO userPackedDTO = UserPackedMapper.mapToDTO(u);
            userPackedDTO.setStatus(u.getStatus());
            userPackedDTO.setId(u.getId());
            userPackedDTO.setCreatedAt(u.getCreatedAt());
            userPackedDTOS.add(userPackedDTO);
        }
        return ResponseEntity.ok(userPackedDTOS);
    }

    @PutMapping("/delete/user-packed/{id}")
    public ResponseEntity<String> deleteServicePack(@PathVariable Long id) {

        userPackedService.deleteUserPacked(id);
        logger.warn("Service with id "+id+" has been deleted by admin..",id);
        return ResponseEntity.ok("Service Pack updated successfully");
    }


}
