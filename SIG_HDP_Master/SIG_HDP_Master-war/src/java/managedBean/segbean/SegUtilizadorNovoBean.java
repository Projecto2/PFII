/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.RhFuncionario;
import entidade.SegAuditoria;
import entidade.SegConta;
//import entidade.SegConta_;
import entidade.SegPerfil;
import java.io.IOException;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.RhFuncionarioFacade;
import sessao.SegAuditoriaFacade;
import sessao.SegContaFacade;
import sessao.SegPerfilFacade;
import util.Mensagem;
import util.seg.EncriptacaoDecriptacao;

/**
 *
 * @author délcio benga
 */
@ManagedBean
@SessionScoped
public class SegUtilizadorNovoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;
    @EJB
    private SegContaFacade contaFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private SegPerfilFacade perfilFacade;
    @EJB
    private SegAuditoriaFacade logAuditoriaFacade;

    private SegConta conta;
    private RhFuncionario funcionarioPesquisa;
    private List<RhFuncionario> funcionariosPesquisados;
    private List<SegPerfil> perfisSeleccionados;

    private String palavaraPasseVizualizar;
    private String password2;
    private String senhaActual;
    private String senhaConfirma;
    private String opcaoRenderizar;
    private int idPerfil;

    String key = "92AE31A79FEEB2A3";
    String iv = "0123456789ABCDEF";

    @ManagedProperty(value = "#{segLoginBean}")
    private SegLoginBean segLoginBean;

    /**
     * Creates a new instance of SegUtilizadorNovoBean
     */
    public SegUtilizadorNovoBean()
    {

    }

    public void preRenderView()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public SegConta getInstanciaConta()
    {
        conta = new SegConta();
        conta.setFkIdFuncionario(new RhFuncionario());
        return conta;
    }

    public String removerAcentos(String str)
    {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        System.out.println("a retornar: " + str);
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public void definirParametrosDaConta() throws Exception
    {
        System.out.println(" definirParametrosDaConta()");
        String nomeUtilizador = (removerAcentos(conta.getFkIdFuncionario().getFkIdPessoa().getNome()) + "." + removerAcentos(conta.getFkIdFuncionario().getFkIdPessoa().getSobreNome()) + conta.getFkIdFuncionario().getFkIdPessoa().getPkIdPessoa()).toLowerCase();
        conta.setNomeUtilizador(nomeUtilizador.replaceAll(" ", ""));
        System.out.println("nome utilizador: " + conta.getNomeUtilizador());
        
        palavaraPasseVizualizar = removerAcentos(conta.getFkIdFuncionario().getFkIdPessoa().getNome()).toUpperCase().replaceAll(" ", "") + "" + conta.getFkIdFuncionario().getFkIdPessoa().getPkIdPessoa();
        System.out.println("palavra passe sem encript" + palavaraPasseVizualizar);
        
        
        conta.setPalavraPasse(EncriptacaoDecriptacao.encrypt(key, iv,palavaraPasseVizualizar));
        System.out.println("password encript: " + conta.getPalavraPasse());

        conta.setDataCadastro(new Date());
        System.out.println("data: " + conta.getDataCadastro());

        conta.setEstadoConta(Boolean.TRUE);
        System.out.println("estado da conta" + conta.getEstadoConta());

        conta.setPrimeiroLoginConta(Boolean.TRUE);

        System.out.println("funcionario: " + conta.getFkIdFuncionario());
    }

    public void criarConta()
    {
        try
        {
            userTransaction.begin();

            conta.setFkIdPerfil(new SegPerfil(idPerfil));
            contaFacade.create(conta);

            userTransaction.commit();
            Mensagem.sucessoMsg("Conta Criada com Sucesso. \n Nome de utilizador: " + conta.getNomeUtilizador() + "\n Palavra-passe: " + palavaraPasseVizualizar);
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public List<RhFuncionario> getFuncionariosPesquisados()
    {
        return funcionariosPesquisados;
    }

    public void pesquisarFuncionarios()
    {
        funcionariosPesquisados = funcionarioFacade.findFuncionarioSemConta(funcionarioPesquisa);

        if (funcionariosPesquisados.isEmpty())
        {
            Mensagem.erroMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }

    public String limparPesquisa()
    {
        funcionariosPesquisados = null;
        funcionarioPesquisa = null;

        return "utilizadorNovoSeg.xhtml?faces-redirect=true";
    }

    public void primeiroLogin() throws Exception
    {
        FacesContext context = FacesContext.getCurrentInstance();
        SegConta mudarPassContaUtilizador = contaFacade.find(segLoginBean.getContaUtilizador().getPkIdConta());
        if (!password2.equals(senhaConfirma)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas têm de ser iguais!", ""));
        } else if (!EncriptacaoDecriptacao.encrypt(key, iv, senhaActual).equals(mudarPassContaUtilizador.getPalavraPasse())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha inserida não corrensponde com a actual!", ""));
        } else if (mudarPassContaUtilizador.getNomeUtilizador().equals("")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Insira um nome de usuario", ""));
        } else {

            mudarPassContaUtilizador.setPalavraPasse(EncriptacaoDecriptacao.encrypt(key, iv, password2));
            mudarPassContaUtilizador.setPrimeiroLoginConta(false);
            contaFacade.edit(mudarPassContaUtilizador);
            
            //log de auditoria
            SegAuditoria logauditoria = new SegAuditoria();
            logauditoria.setNome("Mudar senha Conta Utilizador");
            logauditoria.setNivelRisco("Baixo");
            logauditoria.setOperadorRegisto(segLoginBean.getContaUtilizador().getNomeUtilizador());
            logauditoria.setDataRegisto(new Date());
            logauditoria.setCategoria("Segurança");
            logauditoria.setIpAuditoria(getIpAdressClient());
            logauditoria.setResultado("Sucesso");
            logauditoria.setDetalhes("Conta do utilizador " + mudarPassContaUtilizador.getNomeUtilizador()+ " editado com sucesso, pelo utilizador: " + segLoginBean.getContaUtilizador().getFkIdFuncionario().getFkIdPessoa().getNome() + " " + segLoginBean.getContaUtilizador().getFkIdFuncionario().getFkIdPessoa().getSobreNome());
            logauditoria.setFkIdConta(new SegConta(segLoginBean.getContaUtilizador().getPkIdConta()));

            logAuditoriaFacade.create(logauditoria);
            
            mudarPassContaUtilizador.setPalavraPasse(EncriptacaoDecriptacao.encrypt(key, iv, password2));
            contaFacade.edit(mudarPassContaUtilizador);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta do Utilizador editada com sucesso!", ""));

            redirecionaTo("login.xhtml");
        }
        
        
        
//        FacesContext context = FacesContext.getCurrentInstance();
//        SegConta mudarPassContaUtilizador = contaFacade.find(segLoginBean.getContaUtilizador().getPkIdConta());
//        if (!password2.equals(senhaConfirma))
//        {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas têm de ser iguais!", ""));
//        }
//        else if (mudarPassContaUtilizador.getNomeUtilizador().equals(""))
//        {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Insira um nome de usuario", ""));
//        }
//        else
//        {
//
//            mudarPassContaUtilizador.setPalavraPasse(password2);
//            mudarPassContaUtilizador.setPrimeiroLoginConta(false);
//            contaFacade.edit(mudarPassContaUtilizador);
//
//            mudarPassContaUtilizador.setPalavraPasse(password2);
//            contaFacade.edit(mudarPassContaUtilizador);
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta do Utilizador editada com sucesso!", ""));
//
//            redirecionaTo("login.xhtml");
//        }
    }

    public void redirecionaTo(String page)
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        }
        catch (IOException ex)
        {
            Logger.getLogger(SegUtilizadorNovoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void definirFuncionario(RhFuncionario funcionario)
    {
        conta.setFkIdFuncionario(funcionarioFacade.find(funcionario.getPkIdFuncionario()));
    }

    /**
     * @return the conta
     */
    public SegConta getConta()
    {
        if (conta == null)
        {
            conta = getInstanciaConta();
        }
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    public void setConta(SegConta conta)
    {
        this.conta = conta;
    }

    public RhFuncionario getFuncionarioPesquisa()
    {
        if (funcionarioPesquisa == null)
        {
            funcionarioPesquisa = getInstanciaFuncionario();
        }
        return funcionarioPesquisa;
    }

    public void setFuncionarioPesquisa(RhFuncionario funcionarioPesquisa)
    {
        this.funcionarioPesquisa = funcionarioPesquisa;
    }

    public RhFuncionario getInstanciaFuncionario()
    {
        funcionarioPesquisa = RhFuncionarioNovoBean.getInstancia();
        return funcionarioPesquisa;
    }

    public List<SegPerfil> getPerfisSeleccionados()
    {
        return perfisSeleccionados;
    }

    public List<SegPerfil> getListarPerfis()
    {
        return perfilFacade.findAll();
    }

    public List<SegPerfil> getListaPerfisDisponiveis()
    {
        return perfilFacade.findPerfil(new SegPerfil());
    }

    public void setPerfisSeleccionados(List<SegPerfil> perfisSeleccionados)
    {
        this.perfisSeleccionados = perfisSeleccionados;
    }

    public String getPalavaraPasseVizualizar()
    {
        return palavaraPasseVizualizar;
    }

    public void setPalavaraPasseVizualizar(String palavaraPasseVizualizar)
    {
        this.palavaraPasseVizualizar = palavaraPasseVizualizar;
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

    public String getOpcaoRenderizar()
    {
        if (opcaoRenderizar == null || opcaoRenderizar.isEmpty())
        {
            opcaoRenderizar = "Atribuir Perfil";
        }
        return opcaoRenderizar;
    }

    public void setOpcaoRenderizar(String opcaoRenderizar)
    {
        this.opcaoRenderizar = opcaoRenderizar;
    }

    public int getIdPerfil()
    {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil)
    {
        this.idPerfil = idPerfil;
    }

    public SegLoginBean getSegLoginBean()
    {
        return segLoginBean;
    }

    public void setSegLoginBean(SegLoginBean segLoginBean)
    {
        this.segLoginBean = segLoginBean;
    }
    
    /*
     * pegar o endereço ip da
     * maquina cliente logado
     */
    private String getIpAdressClient() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        //System.out.println("ipAddress:" + ipAddress);
        return ipAddress;
    }
}
