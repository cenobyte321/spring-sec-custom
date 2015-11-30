package org.codigoambar.seguridad.spring.extra;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author RuGI
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String sucursal;
    private final String caja;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        sucursal = request.getParameter("branch");
        caja = request.getParameter("terminal");        
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public String getCaja() {
        return this.caja;
    }

    //TODO override hashCode, equals and toString to include yourParameter
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
