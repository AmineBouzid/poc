package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/no")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users", produces ="application/json")
    public Collection<User> findAllUser() {
        return userService.findAllUser();
    }


    @GetMapping(path ="/user/{id}")
    public User findUserById(@PathVariable Long id){
        return this.userService.findUserById(id);
    }

    @PostMapping(path ="/user")
    public void addUser(@Validated @RequestBody User user){
        this.userService.addUser(user);
    }

    @DeleteMapping(path ="/user/{id}")
    public void deleteUser(@PathVariable Long id){

        User userToDelete = this.userService.findUserById(id);
        this.userService.deleteUser(userToDelete);
    }

}
