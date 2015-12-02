package mx.sisu.customsec.config;

import javax.servlet.http.HttpServletRequest;

import org.codigoambar.seguridad.spring.extra.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.csrf.CsrfFilter;

import com.mkyong.web.controller.CustomAuthenticationProvider;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin**").hasRole("USER").and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/j_spring_security_check")
		.defaultSuccessUrl("/admin")
		.failureUrl("/login?error")
		.usernameParameter("username")
		.passwordParameter("password")
		.authenticationDetailsSource(customWebAuthenticationDetailsSource())
		.and().logout().logoutSuccessUrl("/login?logout").logoutUrl("/j_spring_security_logout");

	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider());
	}
	
	@Bean
	public AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> customWebAuthenticationDetailsSource(){
		return new CustomWebAuthenticationDetailsSource();
	}
	
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider(){
		return new CustomAuthenticationProvider();
	}
}
