# spring-sec-custom-boot

Versión Spring Boot versión 1.3.0 de [https://github.com/rugi/spring-sec-custom](https://github.com/rugi/spring-sec-custom)

## Detalle.
Este código muestra como resolver dos escenarios comunes al usar spring-security más allá del "out-of-the-box".
Todo en un contexto WEB

### Problema a resolver 1.
  Se requiere un mecanismo personalizado para validar las credenciales recibidas del formulario web.
  Esto se resuelve usando un:

```java
  CustomAuthenticationProvider implements AuthenticationProvider 
```


### Problema a resolver 2.
 Se requiere que el formulario de login permita ingresar 2 campos adicionales a:
 user, pwd.
 Esto se soluciona usando:
```java
   CustomWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> 
```

  Y

```java
  CustomWebAuthenticationDetails extends WebAuthenticationDetails 
```

[WebAuthenticationDetails](http://docs.spring.io/autorepo/docs/spring-security/3.2.8.RELEASE/apidocs/org/springframework/security/web/authentication/WebAuthenticationDetails.html) proporciona por default 2 getters:
   * getRemoteAddress() y
   * getSessionId(), 

En escencia, lo que se hace es extender esta clase para permitirle contener los campos extras requeridos y poder ofrecerlos al *AuthenticationProvider*.

Todo se ve mejor en el archivo de configuración de spring:

```java
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

```
 

