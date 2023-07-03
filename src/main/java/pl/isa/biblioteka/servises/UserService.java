package pl.isa.biblioteka.servises;

import pl.isa.biblioteka.dto.CustomUserDetails;
import pl.isa.biblioteka.dto.UserDto;
import pl.isa.biblioteka.mappers.UserMapper;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value="userServiceDetails")
public class UserService implements UserDetailsService {

 @Autowired
 private UserRepository userRepository;

 @Autowired
 private UserMapper userMapper;

 public UserDto createUser(UserDto userDto) {
  User user = userMapper.toEntity(userDto);
  User saved = userRepository.save(user);
  return userMapper.toDto(saved);
 }

 public UserService(UserRepository userRepository) {
  this.userRepository = userRepository;
 }

 public Optional<User> findByUserName(String username) {
  return userRepository.findByUsername(username);
 }

 public boolean existsByUserName(String username) {
  return userRepository.existsByUsername(username);
 }

 public boolean existsByEmail(String userEmail) {
  return userRepository.existsByEmail(userEmail);
 }

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  Optional<User> user = userRepository.findByUsername(username);
  return new CustomUserDetails(user
      .map(userMapper::toDto)
      .orElse(null));
 }
}
