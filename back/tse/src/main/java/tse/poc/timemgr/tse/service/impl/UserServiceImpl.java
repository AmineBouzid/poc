package tse.poc.timemgr.tse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.ERole;
import tse.poc.timemgr.tse.domain.Role;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.service.UserService;

import java.util.Collection;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
    @Transactional
    public void addUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }



}
