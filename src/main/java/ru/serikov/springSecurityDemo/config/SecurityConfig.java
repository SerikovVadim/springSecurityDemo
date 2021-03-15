package ru.serikov.springSecurityDemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.serikov.springSecurityDemo.model.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * Настройка ролям доступа к http запросам и страницам.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Создание пользователей с ролями доступа
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("root"))
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("root"))
                        .roles(Role.USER.name())
                        .build()
        );
    }

    /**
     * Кодирование пароля
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
