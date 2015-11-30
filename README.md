# spring-sec-custom
Ejemplo de configuracion custom para SpringSecurity 3.2.8.RELEASE.

## Detalle.
Este código muestra como resolver dos escenarios comunes al usar spring-security más allá del "out-of-the-box".
Todo en un contexto WEB

Problema a resolver 1.
  Se requiere un mecanismo personalizado para validar las credenciales recibidas del formulario web.
  Esto se resuelve usando un: CustomAuthenticationProvider implements AuthenticationProvider 

Problema a resolver 2.
 Se requiere que el formulario de login permita ingresar 2 campos adicionales a:
 user, pwd.
 Esto se soluciona usando:
  * CustomWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> 
  Y
  * CustomWebAuthenticationDetails extends WebAuthenticationDetails 


[WebAuthenticationDetails](http://docs.spring.io/autorepo/docs/spring-security/3.2.8.RELEASE/apidocs/org/springframework/security/web/authentication/WebAuthenticationDetails.html) proporciona por default getters:  *getRemoteAddress()* y *getSessionId()*, en escencia, lo que se hace es extender esta clase para permitirle contener los campos extras requeridos y poder ofrecerlos al *AuthenticationProvider*.

Todo se ve mejor en el archivo de configuración de spring:

```xml
<beans:beans xmlns="http://www.springframework.org/schema/security"
             xmlns:beans="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	http://www.springframework.org/schema/security
	http://www.springframework.org/schema/security/spring-security-3.2.xsd">
    
    <http auto-config="true">

        <intercept-url pattern="/admin**" access="ROLE_USER" />
		
        <form-login 
            login-page="/login" 
            default-target-url="/welcome" 
            authentication-failure-url="/login?error" 
            username-parameter="username"
            authentication-details-source-ref="customWebAuthenticationDetailsSource"
            password-parameter="password"/>
        <logout logout-success-url="/login?logout"  />
        <!-- enable csrf protection -->
        <csrf/>
    </http>
    <authentication-manager alias="authenticationManager">
        <authentication-provider ref="customAuthenticationProvider" >            
        </authentication-provider>        
    </authentication-manager>
    <beans:bean id="customAuthenticationProvider" class="com.mkyong.web.controller.CustomAuthenticationProvider"/>
    <beans:bean id="customWebAuthenticationDetailsSource" class="org.codigoambar.seguridad.spring.extra.CustomWebAuthenticationDetailsSource"/>
</beans:beans>

```
 

