package com.future.cinemaxx.configuration;

import com.future.cinemaxx.security.entities.ERole;
import com.future.cinemaxx.security.entities.Role;
import com.future.cinemaxx.security.entities.User;
import com.future.cinemaxx.security.repositories.RoleRepository;
import com.future.cinemaxx.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!testSec")
public class SetupTestUsers implements CommandLineRunner {

    @Autowired
    PasswordEncoder encoder;

    UserRepository userRepository;
    RoleRepository roleRepository;

    public SetupTestUsers(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role userRole = new Role(ERole.ROLE_CUSTOMER);
        Role managerRole = new Role(ERole.ROLE_MANAGER);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(managerRole);

        User user = new User("user", "user@a.dk", encoder.encode("test"));
        user.addRole(userRole);
        User admin = new User("admin", "admin@a.dk", encoder.encode("test"));
        admin.addRole(adminRole);
        User emp = new User("employee", "emp@a.dk", encoder.encode("test"));
        emp.addRole(managerRole);

        User both = new User("user_admin", "both@a.dk", encoder.encode("test"));
        both.addRole(userRole);
        both.addRole(adminRole);

        userRepository.save(user);
        userRepository.save(admin);
        userRepository.save(emp);
        userRepository.save(both);

        System.out.println("######################################################################################");
        System.out.println("################################## WARNING ! #########################################");
        System.out.println("# This part breaks a fundamental security rule -> NEVER ship code with default users #");
        System.out.println("######################################################################################");
        System.out.println("################## REMOVE BEFORE DEPLOYMENT ##########################################");
        System.out.println("######################################################################################");


        System.out.println("Created TEST Users");
    }
}