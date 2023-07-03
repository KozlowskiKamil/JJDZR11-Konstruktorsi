package pl.isa.biblioteka.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

 private UserDto userDto;

 public CustomUserDetails(UserDto userDto) {
  this.userDto = userDto;
 }

 public UserDto getUserDto() {
  return userDto;
 }

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  return List.of(new SimpleGrantedAuthority(userDto.getRole()));
 }

 @Override
 public String getPassword() {
  return userDto == null ? null : userDto.getPassword();
 }

 @Override
 public String getUsername() {
  return userDto == null ? null : userDto.getUsername();
 }

 @Override
 public boolean isAccountNonExpired() {
  return true;
 }

 @Override
 public boolean isAccountNonLocked() {
  return true;
 }

 @Override
 public boolean isCredentialsNonExpired() {
  return true;
 }

 @Override
 public boolean isEnabled() {
  return true;
 }
}
