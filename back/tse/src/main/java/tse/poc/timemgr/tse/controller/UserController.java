package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.dao.RoleRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.ERole;
import tse.poc.timemgr.tse.domain.Role;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.service.UserService;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(path = "/all", produces ="application/json")
    public Collection<User> findAllUser() {
        return userService.findAllUser();
    }

    @GetMapping(path = "/managers", produces ="application/json")
    public Collection<User> findallManagers() {
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_MANAGER);
        return userRepository.findByRoles(role);
    }

    @GetMapping(path ="/user/{id}", produces ="application/json")
    public Optional<User> findUserById(@PathVariable Long id){
        return this.userRepository.findById(id);
    }


    @DeleteMapping(path ="/user/{id}")
    public void deleteUser(@PathVariable Long id){

        User userToDelete = this.userService.findUserById(id);
        this.userService.deleteUser(userToDelete);
    }

}
