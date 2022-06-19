package bifast.outbound.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {


//	@Autowired
//	private UserDetailsService userDetailsService;

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}

//	@Bean
//	public AuthenticationManager authMgr(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		return auth.build();
//	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}

	@Bean
    protected SecurityFilterChain filterChain (HttpSecurity http) throws Exception
    {
		http
			.httpBasic()
			.and()
	        .authorizeRequests()
//	        	.antMatchers(
//	        		"/komi-outbound/report/**")
//	        		.permitAll()
	        	.anyRequest().authenticated()
	        .and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.csrf().disable();
		http
//		.headers().frameOptions().disable();
			.headers()
				// do not use any default headers unless explicitly listed
				.defaultsDisabled()
				.cacheControl();
	

		return http.build();

    }

}