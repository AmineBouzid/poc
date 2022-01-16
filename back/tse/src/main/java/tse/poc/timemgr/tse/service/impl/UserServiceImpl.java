package tse.poc.timemgr.tse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tse.poc.timemgr.tse.dao.RoleRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.ERole;
import tse.poc.timemgr.tse.domain.Role;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.CrRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;
import tse.poc.timemgr.tse.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<User> findAllUser(){
        return this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<User> findAllManagers() {
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_MANAGER);
        return userRepository.findByRoles(role);
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> deleteUser(Long id) {
        Optional<User> userToDelete = this.userRepository.findById(id);
        if (!userToDelete.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User doesnt exist!"));
        }else{
            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateLatestCr(CrRequest crRequest) {
        Optional<User> userToDelete = this.userRepository.findById(crRequest.getId());
        if (!userToDelete.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User doesnt exist!"));
        }else{
            userRepository.findById(crRequest.getId())
                    .map(User -> {

                        Date date_cr = null;
                        try {
                            date_cr = new SimpleDateFormat("dd/MM/yyyy").parse(crRequest.getDate_cr());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        User.setLatest_cr(date_cr);
                        return userRepository.save(User);

                    });
            return ResponseEntity.ok(new MessageResponse("Cr Date updated successfully!"));
        }
    }


}
