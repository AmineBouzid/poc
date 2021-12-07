package tse.poc.timemgr.tse.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.User;

@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        /*userRepository.flush();
        User user_test = new User("Ruben", "Feliciations","Manager");
        userRepository.save(user_test);*/
    }
}
