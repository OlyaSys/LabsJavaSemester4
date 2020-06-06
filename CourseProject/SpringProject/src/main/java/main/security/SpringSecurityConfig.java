package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/bt/auth/singin").permitAll()

                .antMatchers(HttpMethod.GET, "/bt/cars").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/cars/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/cars/byNum/{num}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/cars/filterByColor/{color}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/cars/filterByMark/{mark}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/addCar").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/setForeignFlag/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/setCar/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/bt/masters").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/masters/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/masters/byName/{name}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/addMaster").permitAll()

                .antMatchers(HttpMethod.GET, "/bt/services").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/services/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/services/byName/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/services/byCostOur/{costOur}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/services/byCostForeign/{costForeign}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/addService").permitAll()

                .antMatchers(HttpMethod.GET, "/bt/works").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/addWork").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/works/byId/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/bt/works/byDate/{date}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/setDate/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/setCar/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/setMaster/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/bt/setService/{id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/bt/deleteWork/{id}").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
