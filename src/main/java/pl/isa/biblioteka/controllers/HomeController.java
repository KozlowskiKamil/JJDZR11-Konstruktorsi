package pl.isa.biblioteka.controllers;

import pl.isa.biblioteka.dto.CustomUserDetails;
import pl.isa.biblioteka.mappers.UserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
class HomeController {

 @Autowired
 private UserMapper userMapper;

 public HomeController(UserMapper userMapper) {
  this.userMapper = userMapper;
 }

 @GetMapping
 public String home(Model model) {
  final var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  if (principal instanceof String) {
   model.addAttribute("username", Strings.EMPTY);
  } else {
   model.addAttribute("username", userMapper.toDtoFromPrincipal((CustomUserDetails) principal).getUsername());
  }
  return "index";
 }

/* @GetMapping("info")
 @PreAuthorize("hasAuthority('USER')")
 public String info() {
  return "o-nas";
 }*/

 @GetMapping("pre-auth")
 @PreAuthorize("hasAuthority('ADMIN')")
 public String preAuth() {
  return "/";
 }
}
