/*
package pl.isa.biblioteka.controllers;

import pl.isa.biblioteka.dto.UserDto;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
class ExampleController {

  @Autowired
  private UserService userService;

  public ExampleController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/new")
  public String getCreateUserForm(Model model) {
    model.addAttribute("user", new User());
    return "user-create-form";
  }

  @PostMapping("/new")
  public String createUser(@ModelAttribute("user") UserDto user,
      @RequestParam("password") String userPassword,
      @RequestParam("confirmPassword") String confirmPassword,
      Model model) {
    if (userService.existsByUserName(user.getUsername())) {
      model.addAttribute("usernameAlreadyTaken", "Ta nazwa użytkownika jest zajęta");
      return "user-create-form";
    } else if (userService.existsByEmail(user.getEmail())) {
      model.addAttribute("userEmailAlreadyTaken", "Ten address email jest zajęty");
      return "user-create-form";
    } else if (!userPassword.equals(confirmPassword)) {
      model.addAttribute("userPasswordsDoNotMatch", "Hasła muszą być identyczne");
      return "user-create-form";
    } else {
      model.addAttribute("user", userService.createUser(user));
      return "redirect:/user/login";
    }
  }

  @GetMapping("/login")
  public String printHello(Model model) {
    model.addAttribute("user", new UserDto());
    return "user-login-form";
  }

  @PostMapping(value = "/login")
  public String userLogin(@ModelAttribute("user") UserDto user) {
    return "redirect:/";
  }
}
*/
