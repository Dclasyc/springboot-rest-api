package com.dclasyc.springboot.firstrestapi.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {
    public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
        super();
        this.repository = repository;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserDetailsRepository repository;

    @Override
    public void run(String... args) throws Exception {
        logger.info(Arrays.toString(args));
        repository.save(new UserDetails("Oladipo", "Admin"));
        repository.save(new UserDetails("Ayoola", "Admin2"));
        repository.save(new UserDetails("Olugbemi", "Manager"));

//        List<UserDetails> users = repository.findAll();
//        users.forEach(user -> logger.info(users.toString()));

        //Find users by designation
        List<UserDetails> users = repository.findByRole("Manager");
        users.forEach(user -> logger.info(users.toString()));



    }
}
