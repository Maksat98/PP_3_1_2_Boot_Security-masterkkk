package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DB {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DB(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void addDefault(){
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        Set<Role> userRoles = new HashSet<>();
        Set<Role> adminRoles = new HashSet<>();


        userRoles.add(roleUser);
        adminRoles.add(roleAdmin);

        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        User admin = new User("Макс", "Асанов", 22, "maks@mail.ru",
                "$2a$12$HhQfoKTeV847qThavBDXeeNvIbCDqzD6BM44vmAcQ90vGPNucL2lO");
        User user = new User("Рустам", "Акаев", 22, "rus@mail.ru",
                "$2a$12$3anc9flP6qhGYDMslt5HF.Wu9Hu7s5n/5fWIeT4x7PCqKr9MBJ4CS");

        user.setRoles(userRoles);
        admin.setRoles(adminRoles);

        userRepository.save(user);
        userRepository.save(admin);
    }
}
