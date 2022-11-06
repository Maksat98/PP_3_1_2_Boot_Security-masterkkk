package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public adminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminsPage(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "adminPage";
    }

    @GetMapping("/newUser")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.allRoles());
        return "newUser";
    }

    @PostMapping("/newUser")
    public String createUser(@ModelAttribute("user") User user) {
        Long id = userService.addUser(user).getId();
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        List<Role>role =roleService.allRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", role);
        return "editUser";
    }

    @PostMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public  String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("{id}/info")
    public String userInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userInfo";
    }

}