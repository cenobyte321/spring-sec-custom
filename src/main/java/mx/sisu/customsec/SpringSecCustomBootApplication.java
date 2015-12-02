package mx.sisu.customsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.mkyong.web.controller", "mx.sisu", "org.codigoambar.seguridad.spring.extra"})
public class SpringSecCustomBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecCustomBootApplication.class, args);
    }
}
