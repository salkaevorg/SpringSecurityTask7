package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    public AdminController(UserService service) {
        this.service = service;
    }


    @GetMapping()
    public String viewHomePage(Model model) {
        List<User> getAllUsers = service.getAllUsers();
        model.addAttribute("getAllUsers", getAllUsers);

        return "user_list";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        service.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{username}")
    public String showEditUserForm(@PathVariable(value = "username") String username, Model model) {
        User user = service.getUserByUsername(username);
        model.addAttribute("user", user);

        return "edit_user";
    }

    @PostMapping("/edit/{username}")
    public String editUserPost(@PathVariable(value = "username")String username,
                               @RequestParam String name, @RequestParam int age, @RequestParam String nationality, @RequestParam String password){
        User user = service.getUserByUsername(username);
        user.setName(name);
        user.setAge(age);
        user.setNationality(nationality);
        user.setPassword(password);
        service.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        service.deleteUser(id);

        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String viewMyInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = service.getUserByUsername(username);

        model.addAttribute("user", user);

        return "user_info";
    }
}

