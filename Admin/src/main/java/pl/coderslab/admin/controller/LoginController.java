package pl.coderslab.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.admin.model.Admin;
import pl.coderslab.admin.model.dto.AdminDto;
import pl.coderslab.admin.service.impl.AdminServiceImpl;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/index")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        return "index";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }


    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model){
        try {
            if(result.hasErrors()){
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null){
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Jesteś już zarejestrowany!");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getConfirmPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                //System.out.println("Sukces");
                model.addAttribute("success", "Zarejestrowano pomyślnie!");
                model.addAttribute("adminDto", adminDto);
            }else{
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Błędne hasło. Spróbuj ponownie");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorServer", "Błąd serwera. Spróbuj ponownie!");
        }
        return "register";
    }
}
