package pl.isa.biblioteka;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class IndexController {

    @GetMapping("/")
    String login(Principal principal, Model model, Authentication authentication) {
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            Optional<? extends GrantedAuthority> roleAdmin = authentication.getAuthorities().stream().filter(role -> role.getAuthority().equals("ROLE_ADMIN")).findFirst();
            if (roleAdmin.isPresent()) {
//                return "administration";
                return "index";
            } else return "index";

        } else return "index";

    }

    @GetMapping("/administration")
    public ModelAndView administration() {
        return new ModelAndView("administration");
    }

    @GetMapping("/template")
    public ModelAndView template() {
        return new ModelAndView("template");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/error")
    public String error() {
        return "index";
    }
}


// todo skasować przed końcem sprint jak nie będą potrzebne lub przed wgraniem na main

    /*    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }*/


//    @GetMapping("/")
//    public ModelAndView index(Authentication authentication){
//        Optional<? extends GrantedAuthority> admin = authentication.getAuthorities().stream().filter(user -> user.getAuthority().equals("ROLE_ADMIN")).findFirst();
//        if (admin.isPresent()){
//            return new ModelAndView("administration");
//        }
//        return new ModelAndView("index");
//    }


//    @GetMapping("/")
//    public String customLogin(Principal principal, Model model, Authentication authentication) {
//
//        String user = principal.getName();
//        model.addAttribute("user", user);
//        return "index";
//    }



