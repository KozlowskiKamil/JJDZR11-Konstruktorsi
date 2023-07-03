/*
package pl.isa.biblioteka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.user.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        List<User> userList = PersonService.readUsers();

        List<UserDetails> admin = userList.stream().map(person -> org.springframework.security.core.userdetails.User.withUsername(person.getUsername()).password(passwordEncoder.encode(person.getPassword())).roles(person.getUsername().equalsIgnoreCase("admin") || person.getUsername().equalsIgnoreCase("bibliotekarz") ? "ADMIN" : "USER").build()).collect(Collectors.toList());

        return new InMemoryUserDetailsManager(admin.stream().toArray(UserDetails[]::new));
    }


    @Bean      //KONFIGURACJA BEZ ZABEZPIECZENIA authorizeHttpRequests
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(authorize -> authorize.requestMatchers("/", "/images/**", "/css/**", "/static/font/**", "/font/**", "/searchText", "/searchByText", "/list", "/bookList/**", "/register").permitAll().anyRequest().authenticated()).formLogin(login -> login.loginPage("/").defaultSuccessUrl("/", true).usernameParameter("user").passwordParameter("password")).logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}

*/
/*    @Bean      KONFIGURACJA Z ZABEZPIECZENIEM
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/images/**", "/css/**", "/static/font/**", "/font/**",
                                "/searchText", "/searchByText", "/list", "/bookList/**", "/register")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(login -> login.loginPage("/")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("user").passwordParameter("password"))
                .logout(logout -> logout.logoutSuccessUrl("/logout").permitAll());
        return http.build();
    }*//*



//todo skasować przed wgraniem na main

    */
/*    @Bean  metoda dopuszcza każdego bez logowania
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .anyRequest().permitAll();
                .and().formLogin().defaultSuccessUrl("index.html");
        return httpSecurity.build();
    }*//*





*/
/*@Bean    Dodać metodę AntMatchers   do wejścia dowolnego usera na daną stronę i logowanie dopiero z formularza
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and().formLogin().loginPage("/");
    return http.build();
}*//*



*/
/*
    protected void logoutUser(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .logout()
                .logoutSuccessUrl("index")
                .permitAll()
                .and()
                .authorizeHttpRequests().
    }*/

