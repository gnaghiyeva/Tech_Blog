package org.example.techblog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class Security {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //csrf ?  x->x.disable()?
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/admin/category/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/admin")
                        .loginPage("/login")
                        .failureUrl("/login")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        // ensure the passwords are encoded properly
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("nigar").password("password").roles("USER").build());
        manager.createUser(users.username("gulnar").password("password").roles("USER","ADMIN").build());
        return manager;
    }

}

//        @Bean
//        public UserDetailsService userDetailsService() throws Exception {
//            // ensure the passwords are encoded properly
//            User.UserBuilder users = User.withDefaultPasswordEncoder();
//            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//            manager.createUser(users.username("gulnar.nagiyeva17@gmail.com").password("$2a$10$ZQlR2yOBh2uNdT/84ALVNOow8X0HJsUWnXgAqmkTtC6JvwzkaSuP6").roles("USER","ADMIN").build());
//            manager.createUser(users.username("kismis@gmail.com").password("$2a$10$8pVwsA7q7IV5eE3K/u92LuD0eFWptORiP1hKQvgdRhetrx5nwoEaG").roles("USER","ADMIN").build());
//            return manager;
//        }

