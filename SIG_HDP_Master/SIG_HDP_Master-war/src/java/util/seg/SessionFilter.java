/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.seg;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidade.SegConta;

/**
 *
 * @author as
 * @author Delcio Benga
 */
@WebFilter(filterName = "SessaoFilter", urlPatterns =
{
    "/sessaoExpiradaSeg.xhtml"
})
public class SessionFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        SegConta contaDoUtilizador = (SegConta) req.getSession().getAttribute("contaUtilizador");

        if (contaDoUtilizador == null)
        {
            //retornar se não existir
            res.sendRedirect(req.getContextPath() + "/sessaoExpiradaSeg.xhtml?s=fail");
        } else
        {

            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy()
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
