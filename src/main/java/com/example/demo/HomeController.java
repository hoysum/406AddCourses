package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private BullhornRepository bullhornRepository;

//        @RequestMapping("/")
//        public String showRegistrationPage(Model model) {
//            model.addAttribute("user", new User());
//            return "registration";
//        }


    @RequestMapping("/login")
    public String showlogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showregistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "list";
    }

    @RequestMapping("/")
    public String listBullhorns(Model model){
        model.addAttribute("bullhorns",bullhornRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String bullhornForm(Model model){
        model.addAttribute("bullhorn", new Bullhorn());
        return "bullhornform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Bullhorn bullhorn,
                              BindingResult result){
//        if (result.hasErrors()){
//            return "bullhornform";
//        }
        bullhornRepository.save(bullhorn);
        return "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showBullhorn(@PathVariable("id") long id, Model model) {
        model.addAttribute("bullhorn", bullhornRepository.findById(id).get());
       return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateBullhorn(@PathVariable("id") long id, Model model) {
        model.addAttribute("bullhorn", bullhornRepository.findById(id).get());
        return "bullhornform";
    }
    @RequestMapping("/delete/{id}")
    public String delBullhorn(@PathVariable("id") long id){
        bullhornRepository.deleteById(id);
        return "redirect:/";


    }

}

