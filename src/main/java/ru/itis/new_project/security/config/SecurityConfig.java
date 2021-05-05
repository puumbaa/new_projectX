package ru.itis.new_project.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.new_project.security.details.PersonDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Вернуть обратно в энт матчерс  ,"/lobbies/{[1-9].[0-9]*}"
        http
                .authorizeRequests()
                    .antMatchers("/myLobbies/**", "/profile").authenticated()
                    .antMatchers("/sign_up/**", "/soon", "/lobbies").permitAll()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/templates/**").permitAll()
                .and()
                    .formLogin()
                        .usernameParameter("login")
                        .defaultSuccessUrl("/myLobbies")
                        .loginPage("/login")
                        .permitAll()
                .and()
                    .logout()
                        .permitAll()
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me");

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
