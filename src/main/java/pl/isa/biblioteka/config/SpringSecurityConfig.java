package pl.isa.biblioteka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.isa.biblioteka.user.PersonService;
import pl.isa.biblioteka.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class SpringSecurityConfig {

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(request -> request
                        .requestMatchers("/", "/images/**", "/css/**", "/static/font/**", "/font/**", "/searchText", "/searchByText", "/list", "/bookList/**", "/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/").usernameParameter("user").passwordParameter("password").permitAll()
                        .failureUrl("/")
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout=true"))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

/*
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        List<User> userList = PersonService.readUsers();

        List<UserDetails> admin = userList.stream().map(person -> org.springframework.security.core.userdetails.User.withUsername(person.getUsername()).password(passwordEncoder.encode(person.getPassword())).roles(person.getUsername().equalsIgnoreCase("admin") || person.getUsername().equalsIgnoreCase("bibliotekarz") ? "ADMIN" : "USER").build()).collect(Collectors.toList());

        return new InMemoryUserDetailsManager(admin.stream().toArray(UserDetails[]::new));
    }
*/

}



/*
 @Bean      //KONFIGURACJA BEZ ZABEZPIECZENIA authorizeHttpRequests
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.csrf().disable().authorizeHttpRequests(authorize ->
                  authorize.requestMatchers("/", "/images/**", "/css/**", "/static/font/**", "/font/**", "/searchText", "/searchByText", "/list", "/bookList/**", "/register")
                          .permitAll().anyRequest().authenticated())
          .formLogin(login -> login.loginPage("/")
                  .defaultSuccessUrl("/", true)
                  .usernameParameter("user").passwordParameter("password"))
          .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
  return http.build();
 }
 */