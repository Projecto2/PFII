/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sessao.GrlPessoaFacade;
import sessao.SegContaFacade;
import sessao.SegHistoricoSessaoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author délcio benga
 */
@ManagedBean
@SessionScoped
public class SegcontroloSessaoBean implements Serializable
{
   @EJB 
   private GrlPessoaFacade pessoaFacade;
   @EJB
   private SegHistoricoSessaoFacade sessaoFacade;
   @EJB
   private SegContaFacade contaFacade;
   
   private SegConta conta;

   FacesContext context = FacesContext.getCurrentInstance();
   HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
   HttpSession sessao = request.getSession();

   /**
    * Creates a new instance of SegcontroloSessaoBean
    */
   public SegcontroloSessaoBean()
   {
   }

   public static SegcontroloSessaoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        SegcontroloSessaoBean segcontroloSessaoBean
            = (SegcontroloSessaoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
                getELContext(), null, "segcontroloSessaoBean");

        return segcontroloSessaoBean;
    }
   
   public void validarSessao()
   {
      SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
      String urlSessaoExpiradaSeg = "http://" + getIpMaquinaServidor() + ":8080/SIG_HDP_Master-war/faces/sessaoExpiradaSeg.xhtml?faces-redirect=true";
      if (sessaoActual == null || sessaoActual.getPkIdConta()== null)
      {
         try
         {          
            sessaoActual = contaFacade.find(Constantes.idUltimaSessao);
            if(sessaoActual != null)
            {
               sessaoActual.setUltimoAcessoConta(new Date());
               contaFacade.edit(sessaoActual);
            }            
            context.getExternalContext().redirect(urlSessaoExpiradaSeg);
         }
         catch (IOException ex)
         {
            Mensagem.warnMsg("Pagina de redireccionamento não encontrada");
         }
      }
      else 
      {
         if(sessaoActual.getFkIdFuncionario().getFkIdPessoa().getNome().isEmpty() || sessaoActual.getFkIdFuncionario().getFkIdPessoa().getNome() == null)
         {
            sessaoActual.getFkIdFuncionario().setFkIdPessoa(pessoaFacade.find(
                 sessaoActual.getFkIdFuncionario().getFkIdPessoa().getPkIdPessoa()
         ));
         }
      }
   }

   public String getIpMaquinaServidor() 
   {
      context = FacesContext.getCurrentInstance();
        
      request = (HttpServletRequest) context.getExternalContext().getRequest();
        
      return request.getLocalAddr();
   }

   public SegConta getInstanciaConta()
   {
      conta = new SegConta();
      conta.setFkIdFuncionario(new RhFuncionario());
      return conta;
   }

   /**
    * @return the conta
    */
   public SegConta getConta()
   {
      if (conta.getPkIdConta() == null)
         conta = getInstanciaConta();
      return conta;
   }

   /**
    * @param conta the conta to set
    */
   public void setConta(SegConta conta)
   {
      this.conta = conta;
   }
}
