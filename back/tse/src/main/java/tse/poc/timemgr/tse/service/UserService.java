package tse.poc.timemgr.tse.service;

import tse.poc.timemgr.tse.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

     Collection<User> findAllUser();

     User findUserById(Long id);

     void addUser(User user);

     void deleteUser(User user);




}
