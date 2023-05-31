package pl.isa.biblioteka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails librarian = User.withUsername("Bibliotekarz").password(passwordEncoder.encode("bibliotekarz")).roles("ADMIN").build();

        UserDetails admin = User.withUsername("Admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();

        UserDetails kamil = User.withUsername("Kamil").password(passwordEncoder.encode("kamil")).roles("USER").build();

        UserDetails mikolaj = User.withUsername("Mikołaj").password(passwordEncoder.encode("mikolaj")).roles("USER").build();

        UserDetails przemek = User.withUsername("Przemek").password(passwordEncoder.encode("przemek")).roles("USER").build();

        UserDetails kinga = User.withUsername("Kinga").password(passwordEncoder.encode("kinga")).roles("USER").build();

        UserDetails user = User.withUsername("User").password(passwordEncoder.encode("user")).roles("USER").build();

        return new InMemoryUserDetailsManager(user, admin, kinga, mikolaj, przemek, kamil, librarian);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/", "/images/**", "/css/**", "/static/font/**", "/font/**").permitAll().anyRequest().authenticated()).formLogin(login -> login.loginPage("/").defaultSuccessUrl("/", true).usernameParameter("user").passwordParameter("password")).logout(logout -> logout.logoutSuccessUrl("/logout").permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }

}


//todo skasować przed wgraniem na main

    /*    @Bean  metoda dopuszcza każdego bez logowania
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .anyRequest().permitAll();
                .and().formLogin().defaultSuccessUrl("index.html");
        return httpSecurity.build();
    }*/




/*@Bean    Dodać metodę AntMatchers   do wejścia dowolnego usera na daną stronę i logowanie dopiero z formularza
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and().formLogin().loginPage("/");
    return http.build();
}*/


/*
    protected void logoutUser(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .logout()
                .logoutSuccessUrl("index")
                .permitAll()
                .and()
                .authorizeHttpRequests().
    }*/
