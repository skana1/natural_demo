package natural_demo.Config;

import lombok.RequiredArgsConstructor;
import natural_demo.Filter.CustomAuthenticationFilter;
import natural_demo.Filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html");
//                .antMatchers("/v2/api-docs",
//                "/swagger-ui/",
//                "/swagger-ui",
//                "/configuration/ui",
//                "/swagger-resources/**",
//                "/configuration/security",
//                "/swagger-ui.html",
//                "/webjars/**")
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/controller/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests().antMatchers("/controller/users", "/controller/token/refresh/**").permitAll();

        http.authorizeHttpRequests().antMatchers("/controller/login", "/controller/token/refresh/**").permitAll();
        http.authorizeHttpRequests().antMatchers(String.valueOf(POST), "/controller/user/**").permitAll();
        //http.authorizeHttpRequests().antMatchers(String.valueOf(POST),"/controller/user/**").hasAnyAuthority("adm");
        http.authorizeHttpRequests().antMatchers(String.valueOf(POST), "/controller/user/save/**").permitAll();

        http
                .authorizeHttpRequests()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html").permitAll();
//                .antMatchers(
//                        "/v2/api-docs",
//                        "/swagger-resources/**",
//                        "/swagger-ui.html",
//                        "/webjars/**",
//                        /*Probably not needed*/ "/swagger.json")


        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
