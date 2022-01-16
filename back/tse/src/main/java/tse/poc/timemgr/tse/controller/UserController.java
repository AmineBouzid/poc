package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.dao.RoleRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.ERole;
import tse.poc.timemgr.tse.domain.Role;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.CrRequest;
import tse.poc.timemgr.tse.payload.request.UpdateRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;
import tse.poc.timemgr.tse.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping(path = "/all", produces ="application/json")
    public Collection<User> findAllUser() {
        return this.userService.findAllUser();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') ")
    @GetMapping(path = "/managers", produces ="application/json")
    public Collection<User> findAllManagers() {
        return this.userService.findAllManagers();
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping(path ="/user/{id}", produces ="application/json")
    public User findUserById(@PathVariable Long id){
        return this.userService.findUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path ="/delete/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id){
        return this.userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @PutMapping("/cr")
    public ResponseEntity<?>  updateLatestCr(@Valid @RequestBody CrRequest crRequest) {
        return this.userService.updateLatestCr(crRequest);
    }

}
