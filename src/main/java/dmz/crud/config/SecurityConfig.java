package dmz.crud.config;

import dmz.crud.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "dmz.crud")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final LoginSuccessHandler loginSuccessHandler;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, LoginSuccessHandler loginSuccessHandler, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.loginSuccessHandler = loginSuccessHandler;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/").authenticated()
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/user/**").hasAuthority("ROLE_USER")
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
                .failureUrl("/login?error")
                .usernameParameter("name")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedPage("/error");

    }
}