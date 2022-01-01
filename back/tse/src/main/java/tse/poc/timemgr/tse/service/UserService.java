package tse.poc.timemgr.tse.service;

import tse.poc.timemgr.tse.domain.User;

import java.util.Collection;

public interface UserService {

     Collection<User> findAllUser();

     User findUserById(Long id);

     Collection<User> findAllManagers();

     void deleteUser(User user);


}
