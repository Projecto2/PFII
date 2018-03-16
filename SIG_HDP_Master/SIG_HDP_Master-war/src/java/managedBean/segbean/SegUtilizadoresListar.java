/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.segbean;

import entidade.GrlEspecialidade;
import entidade.GrlPessoa;
import entidade.RhEstadoFuncionario;
import entidade.RhFuncionario;
import entidade.RhSeccaoTrabalho;
import entidade.SegAuditoria;
import entidade.SegConta;
import entidade.SegPerfil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.SegContaFacade;
import sessao.SegPerfilFacade;
import util.seg.EncriptacaoDecriptacao;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class SegUtilizadoresListar implements Serializable
{
   @EJB
    private SegContaFacade contaFacade;
    @EJB
    private SegPerfilFacade perfilFacade;
    private SegConta contaPesquisar, contaEditar;
    private List<SegConta> contasPesquisadas;

    private String resposta2;
    private int idPerfil;
    private String password2;
    private String senhaActual;
    private String senhaConfirma;
    
    String key = "92AE31A79FEEB2A3";
    String iv = "0123456789ABCDEF";
    
    @ManagedProperty(value = "#{segLoginBean}")
    private SegLoginBean segLoginBean;

   /**
    * Creates a new instance of SegUtilizadoresListar
    */
   public SegUtilizadoresListar()
   {
      contaPesquisar = getInstanciaConta();
   }
   
   public SegConta getInstanciaConta()
   {
      contaPesquisar = new SegConta();
      RhFuncionario funcionario = new RhFuncionario();
      funcionario.setFkIdEstadoFuncionario(new RhEstadoFuncionario());
      funcionario.setFkIdEspecialidade1(new GrlEspecialidade());
      funcionario.setFkIdEspecialidade2(new GrlEspecialidade());
      funcionario.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
      funcionario.setFkIdPessoa(new GrlPessoa());      
      
      contaPesquisar.setFkIdFuncionario(funcionario);
      return contaPesquisar;
   }
   
   /*
     *Editar a conta do usuario no sistema
     *Utilizador nao logados
     */
//    public void editarContaUtilizador() throws Exception {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        if (!password2.equals(senhaConfirma)) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas têm de ser iguais!", ""));
//        } else if (!EncriptacaoDecriptacao.decrypt(key, iv, senhaActual).equals(contaEditar.getPalavraPasse())) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha inserida não corrensponde com a actual!", ""));
//        } else if (contaEditar.getNomeUtilizador().equals("")) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Insira um nome de usuario", ""));
//        } else {
//            if (contaEditar != null) {
//                System.out.println("resposta2" + resposta2);
//                if (resposta2.equals("1")) {//se pretende alterar o perfil ou seja mover o usuario para outro perfil
//
//                    contaEditar.setFkIdPerfil(new SegPerfil(idPerfil));
//                    contaEditar.setPalavraPasse(EncriptacaoDecriptacao.encrypt(key, iv, password2));
//                    contaFacade.edit(contaEditar);
//
//                    //log de auditoria
//                    SegAuditoria logauditoria = new SegAuditoria();
//                    logauditoria.setNome("Editar Conta Utilizador");
//                    logauditoria.setNivelRisco("Baixo");
//                    logauditoria.setOperadorRegisto(SegLoginBean.getContaUtilizador().getNomeUtilizador());
//                    logauditoria.setDataRegisto(new Date());
//                    logauditoria.setCategoria("Segurança");
//                    logauditoria.setIpAuditoria(getIpAdressClient());
//                    logauditoria.setResultado("Sucesso");
//                    logauditoria.setDetalhes("Conta do utilizador " + contaEditar.getNomeUtilizador()+ " editado com sucesso, pelo utilizador: " + SegLoginBean.getContaUtilizador().getFkIdFuncionario().getFkIdPessoa().getNome() + " " + SegLoginBean.getContaUtilizador().getFkIdFuncionario().getFkIdPessoa().getSobreNome());
//                    logauditoria.setFkIdConta(new SegConta(SegLoginBean.getContaUtilizador().getPkIdConta()));
//
//                    logAuditoriaFacade.create(logauditoria);
//
//                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta do Utilizador e Perfil editada com sucesso!", ""));
//
//                } else {
//                    contaUtilizadorSelecionado.setPasssegcontutil(EncriptacaoDecriptacao.encrypt(key, iv, password2));
//                    contaUtilizadorFacade.edit(contaUtilizadorSelecionado);
//                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta do Utilizador editada com sucesso!", ""));
//
//                }
//                redirecionaTo("seguranca_ListarContaUtilizadores.xhtml");
//
//            } else {
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome de usuario já existe, insira um outro nome de usuario!", ""));
//            }
//        }
//    }
   
       /*
     *Editar a conta do usuario no sistema
     *Utilizador nao logados
     */
    public void editarContaUtilizador() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!password2.equals(senhaConfirma)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas têm de ser iguais!", ""));
        } else if (!senhaActual.equals(contaEditar.getPalavraPasse())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha inserida não corrensponde com a actual!", ""));
        } else if (contaEditar.getNomeUtilizador().equals("")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Insira um nome de usuario", ""));
        } else {
            if (contaEditar != null) {
                System.out.println("resposta2" + resposta2);
                if (resposta2.equals("1")) {//se pretende alterar o perfil ou seja mover o usuario para outro perfil

                    contaEditar.setFkIdPerfil(new SegPerfil(idPerfil));
                    contaEditar.setPalavraPasse(password2);
                    contaFacade.edit(contaEditar);

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta do Utilizador e Perfil editada com sucesso!", ""));

                } else {
                    contaEditar.setPalavraPasse(password2);
                    contaFacade.edit(contaEditar);
                    
                    //contasPesquisadas = contaFacade.findPerfil(contaPesquisar);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta do Utilizador editada com sucesso!", ""));

                }

            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome de usuario já existe, insira um outro nome de usuario!", ""));
            }
        }
    }

   

   public void pesquisarConta()
   {
      contasPesquisadas = contaFacade.findContas(contaPesquisar);
   }
   /**
    * @return the contaPesquisar
    */
   public SegConta getContaPesquisar()
   {
      return contaPesquisar;
   }

   /**
    * @param contaPesquisar the contaPesquisar to set
    */
   public void setContaPesquisar(SegConta contaPesquisar)
   {
      this.contaPesquisar = contaPesquisar;
   }

   /**
    * @return the contasPesquisadas
    */
   public List<SegConta> getContasPesquisadas()
   {
      return contasPesquisadas;
   }

   /**
    * @param contasPesquisadas the contasPesquisadas to set
    */
   public void setContasPesquisadas(List<SegConta> contasPesquisadas)
   {
      this.contasPesquisadas = contasPesquisadas;
   }
   
    public List<SegPerfil> getListarPerfis() {
        return perfilFacade.findAll();
    }


    public SegConta getContaEditar()
    {
         if (contaEditar == null)
        {
            contaEditar = getInstanciaConta();
        }
        return contaEditar;
    }

    public void setContaEditar(SegConta contaEditar)
    {
        this.contaEditar = contaEditar;
    }

    public String getResposta2()
    {
        return resposta2;
    }

    public void setResposta2(String resposta2)
    {
        this.resposta2 = resposta2;
    }

    public int getIdPerfil()
    {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil)
    {
        this.idPerfil = idPerfil;
    }

    public String getPassword2()
    {
        return password2;
    }

    public void setPassword2(String password2)
    {
        this.password2 = password2;
    }

    public String getSenhaActual()
    {
        return senhaActual;
    }

    public void setSenhaActual(String senhaActual)
    {
        this.senhaActual = senhaActual;
    }

    public String getSenhaConfirma()
    {
        return senhaConfirma;
    }

    public void setSenhaConfirma(String senhaConfirma)
    {
        this.senhaConfirma = senhaConfirma;
    }

    public SegLoginBean getSegLoginBean()
    {
        return segLoginBean;
    }

    public void setSegLoginBean(SegLoginBean segLoginBean)
    {
        this.segLoginBean = segLoginBean;
    }
    
    
}
