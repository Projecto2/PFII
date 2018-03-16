/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.RhFuncionario;
import entidade.SegConta;
import entidade.SegFuncionalidade;
import entidade.SegHistoricoSessao;
import entidade.SegLogAcesso;
import entidade.SegPerfil;
import entidade.SegPerfilHasFuncionalidade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedBean.admsbean.AdmsCarregamentoInicialPacientesBean;
import managedBean.ambbean.cid.AmbBean;
import managedBean.grlbean.carregamentoExcel.GrlAreaInternaExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlComunaExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlEstadoCivilExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlMunicipioExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlPaisExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlProvinciaExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlReligiaoExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlSexoExcelBean;
import managedBean.rhbean.carregamentoExcel.RhCarregarTabelasBean;
import managedBean.segbean.carregamentoExcel.SegCarregarTabelas;
import org.apache.log4j.Logger;
import sessao.SegContaFacade;
import sessao.SegFuncionalidadeFacade;
import sessao.SegHistoricoSessaoFacade;
import sessao.SegLogAcessoFacade;
import sessao.SegPerfilFacade;
import sessao.SegPerfilHasFuncionalidadeFacade;
import util.seg.EncriptacaoDecriptacao;

/**
 *
 * @author adalberto
 */
@ManagedBean
@SessionScoped
public class SegLoginBean implements Serializable
{

    @EJB
    private SegHistoricoSessaoFacade sessaoFacade;
    @EJB
    private SegLogAcessoFacade logAcessoFacade;
    @EJB
    private SegContaFacade contaFacade;
    @EJB
    private SegPerfilFacade perfilFacade;
    @EJB
    private SegFuncionalidadeFacade funcionalidadeFacade;
    @EJB
    private SegPerfilHasFuncionalidadeFacade perfilFuncionalidadeFacade;

    private SegHistoricoSessao sessaoActual1;

    private SegLogAcesso logAcesso;
    private SegConta sessaoActual;
    private boolean validateError;
//    private String nomeUtilizador;
//    private String palavraPasse;
    private int tentativas = 0;
    private ControloDeAcesso controloDeAcesso;

    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    HttpSession sessao = request.getSession();

    private String username;
    private String password;
    String key = "92AE31A79FEEB2A3";
    String iv = "0123456789ABCDEF";
    private final static Logger log = Logger.getLogger(SegLoginBean.class);

    /**
     * Creates a new instance of SegLoginBean
     */
    public SegLoginBean()
    {
        inicializar();
    }
    
    public void preRenderView() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public static SegLoginBean obterSegLoginBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (SegLoginBean) c.getELContext().getELResolver()
            .getValue(c.getELContext(), null, "segLoginBean");
    }

    public void init()
    {
        inicializarAmbBean();
        System.out.println("0: SegLoginBean.init()");
    }

    public void inicializarAmbBean()
    {
        AmbBean ambBean = AmbBean.getInstanciaBean();
        System.out.println("0: SegLoginBean.inicializarAmbBean()");
        ambBean.inicializar();
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public SegConta getSessaoActual()
    {
        return sessaoActual;
    }

    public void setSessaoActual(SegConta sessaoActual)
    {
        this.sessaoActual = sessaoActual;
    }

    public SegLogAcesso getLogAcesso()
    {
        return logAcesso;
    }

    public void setLogAcesso(SegLogAcesso logAcesso)
    {
        this.logAcesso = logAcesso;
    }

    @PostConstruct
    public void inicializar()
    {
        //Conexao.getConection();
        //sessaoActual = getInstanciaSessao();
        controloDeAcesso = new ControloDeAcesso();

    }

    public static SegLoginBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        SegLoginBean segLoginBean
            = (SegLoginBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
                getELContext(), null, "segLoginBean");

        return segLoginBean;
    }

    /*  
     public SegHistoricoSessao getInstanciaSessao()
     {
     sessaoActual = new SegHistoricoSessao();
     SegConta conta = new SegConta();
     GrlPessoa pessoa = new GrlPessoa();
     RhFuncionario funcionario = new RhFuncionario();
     GrlEspecialidade especialidade = new GrlEspecialidade();
     funcionario.setFkIdPessoa(pessoa);
     funcionario.setFkIdEspecialidade1(especialidade);
     conta.setFkIdFuncionario(funcionario);
     sessaoActual.setFkIdConta(conta);

     return sessaoActual;
     }
   
     */
//    NOVO METODO
    public void entraNoSistema() throws Exception
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faceContext.getExternalContext();
        if (password.equals("") || username.equals(""))
        {
            try
            {
                validateError = true;
                externalContext.redirect("login.xhtml?validar=true");
            }
            catch (IOException ex)
            {
                log.error(" SegLoginBean exception: " + ex.getMessage());
            }
        }
        else
        {
            //encriptar a password para login
            System.out.println("tipo id:" + username);
            login(username, EncriptacaoDecriptacao.encrypt(key, iv, password));
        }
    }

    public void login(String username, String password)
    {
        sessaoActual = contaFacade.getUtilizadorBypalavraPasseAndnomeUtilizador(username, password);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        if (sessaoActual == null)
        {
            tentativas++;
            //logs de acesso ao sistema
            SegLogAcesso logAcesso = new SegLogAcesso();
            logAcesso.setEventoLogAcesso("Acesso ao servidor");
            logAcesso.setRiscoLogAcesso("Baixo");
            logAcesso.setOperador(username);
            logAcesso.setTipoUsuario("Desconhecido");
            logAcesso.setDataRegistoLogAcesso(new Date());
            logAcesso.setIpLogAcess0(getIpAdressClient());
            logAcesso.setResultadoLogAcesso("Insucesso");
            logAcesso.setDetalhesLogAcesso("Causa do Probelma: Nome do usuario e senha inválida, nome do utilizador: " + username);
            logAcesso.setFkIdConta(null);

            logAcessoFacade.create(logAcesso);

            if (tentativas > 2)
            {
                redirectedForPage("loginError.xhtml");
            }

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome ou senha do usuario errado", "Nao foi possivel fazer o login! Por favor Tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        else if (sessaoActual != null)
        {
            //System.out.println("tipo id:" + sessaoActual.getIdSegTipoUtil().getIdSegTipoUtil());
            if (sessaoActual.getEstadoConta() == true)
            {

                // para funcionario
                if (sessaoActual.getPrimeiroLoginConta() == true)
                {
                    // mudar a senha
                    try
                    {
                        validateError = false;
                        externalContext.redirect("firstLogin.xhtml");
                    }
                    catch (IOException ex)
                    {
                        log.error(" SegLoginBean exception: " + ex.getMessage());
                    }
                }
                else
                {

                    tentativas = 0;

                    //logs de acesso ao sistema
                    SegLogAcesso logAcesso = new SegLogAcesso();
                    logAcesso.setEventoLogAcesso("Acesso ao servidor");
                    logAcesso.setRiscoLogAcesso("Aviso");
                    logAcesso.setOperador(username);
                    logAcesso.setDataRegistoLogAcesso(new Date());
                    logAcesso.setIpLogAcess0(getIpAdressClient());
                    logAcesso.setResultadoLogAcesso("Sucesso");
                    logAcesso.setDetalhesLogAcesso("Acesso ao servidor efectuado com sucesso, Nome do " + sessaoActual.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + sessaoActual.getFkIdFuncionario().getFkIdPessoa().getSobreNome());
                    logAcesso.setFkIdConta(sessaoActual);

                    logAcessoFacade.create(logAcesso);

                    ExternalContext externalContext2 = FacesContext.getCurrentInstance().getExternalContext();
                    Map<String, Object> sessionMap = externalContext2.getSessionMap();
                    sessionMap.put("sessaoActual", sessaoActual);
                    //init();
                    try
                    {
                        validateError = false;
                        init();
                        externalContext.redirect("home.xhtml");
                    }
                    catch (IOException ex)
                    {
                        log.error(" SegLoginBean exception: " + ex.getMessage());
                    }
                }

            }
            else
            {

                try
                {
                    validateError = false;
                    externalContext.redirect("login.xhtml?error=true");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conta bloqueda contacte o administrador", "Nao foi possivel fazer o login! Por favor Tente novamente");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                catch (IOException ex)
                {
                    log.error(" SegLoginBean exception: " + ex.getMessage());
                }

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro a efetuar o acesso ao servidor, o utilizador possui a conta desactivada", "Contacte o Administrador!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        else
        {
            try
            {
                validateError = false;
                externalContext.redirect("login.xhtml?error=true");
            }
            catch (IOException ex)
            {
                log.error(" SegLoginBean exception: " + ex.getMessage());
            }

        }
    }

    public void logout()
    {

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();

        String path = context.getContextPath();
        ExternalContext ec = aFacesContext.getExternalContext();
        try
        {
            ec.redirect(path + "/faces/login.xhtml");
            //System.out.println(path);
        }
        catch (IOException ex)
        {
            //log.error(" LoginBean exception: "+ex.getMessage());
        }

        //gravar o registo de logs na base de dados
        SegLogAcesso logAcesso = new SegLogAcesso();
        logAcesso.setEventoLogAcesso("Termino de sessão");
        logAcesso.setRiscoLogAcesso("Aviso");
        logAcesso.setOperador(username);
        logAcesso.setDataRegistoLogAcesso(new Date());
        logAcesso.setIpLogAcess0(getIpAdressClient());
        logAcesso.setResultadoLogAcesso("Sucesso");
        logAcesso.setFkIdConta(sessaoActual);
        logAcesso.setDetalhesLogAcesso("Termino de sessão ao servidor efectuado com sucesso, Nome do " + sessaoActual.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + sessaoActual.getFkIdFuncionario().getFkIdPessoa().getSobreNome());

        logAcessoFacade.create(logAcesso);

        //gravar o ultimo acesso do user
        sessaoActual.setUltimoAcessoConta(new Date());
        contaFacade.edit(sessaoActual);

        sessaoActual = null;
        username = "";
        password = "";

    }

    public SegConta obterContaDaCorrenteSessao()
    {
        sessaoActual = contaFacade.existeContaUtilizador(username);
        if (sessaoActual == null)
        {
            return null;
        }

        return sessaoActual;

//         String result1 = "Nenhum Funcioanrio Logado";
//        if (sessaoActual != null) {
//            String result2 = sessaoActual.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + sessaoActual.getFkIdFuncionario().getFkIdPessoa().getSobreNome();
//            return result2.toUpperCase();
//        }
//        return result1.toUpperCase();
    }

    public String getIpMaquinaCliente()
    {
        context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();

        return request.getRemoteAddr();
    }

    public String getIpMaquinaServidor()
    {
        context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();

        return request.getLocalAddr();
    }

    public String getIdSessao()
    {
        context = FacesContext.getCurrentInstance();
        sessao = (HttpSession) context.getExternalContext().getSession(false);
        return sessao.getId();
    }

    public int getIntervaloMaximoInactividade()
    {
        context = FacesContext.getCurrentInstance();
        sessao = (HttpSession) context.getExternalContext().getSession(false);
        return sessao.getMaxInactiveInterval();
    }

    public void setIntervaloMaximoInactividade(HttpSession sessao, int tempoEmSegundos)
    {
        sessao.setMaxInactiveInterval(tempoEmSegundos);
    }

    /**
     * @return the sessaoActual
     */
    /*
     public SegHistoricoSessao getSessaoActual()
     {
     sessaoActual = (SegHistoricoSessao) sessao.getAttribute("sessaoActual");
     if (sessaoActual == null)
     {
     sessaoActual = getInstanciaSessao();
     }

     return sessaoActual;
     }
   

     public void setSessaoActual(SegHistoricoSessao sessaoActual)
     {
     this.sessaoActual = sessaoActual;
     } 
    
     */
    public SegConta getContaUtilizador()
    {
        return sessaoActual;
    }

    public void setContaUtilizador(SegConta sessaoActual)
    {
        this.sessaoActual = sessaoActual;
    }

    public boolean isValidateError()
    {
        return validateError;
    }

    public void setValidateError(boolean validateError)
    {
        this.validateError = validateError;
    }

//    public String getNomeUtilizador()
//    {
//        return nomeUtilizador;
//    }
//
//    public void setNomeUtilizador(String nomeUtilizador)
//    {
//        this.nomeUtilizador = nomeUtilizador;
//    }
//
//    public String getPalavraPasse()
//    {
//        return palavraPasse;
//    }
//
//    public void setPalavraPasse(String palavraPasse)
//    {
//        this.palavraPasse = palavraPasse;
//    }

    public int getTentativas()
    {
        return tentativas;
    }

    public void setTentativas(int tentativas)
    {
        this.tentativas = tentativas;
    }

    public ControloDeAcesso getControloDeAcesso()
    {
        return controloDeAcesso;
    }

    public void setControloDeAcesso(ControloDeAcesso controloDeAcesso)
    {
        this.controloDeAcesso = controloDeAcesso;
    }

    public void redirectedForPage(String page)throws IllegalStateException
    {
        try
        {

            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        }
        catch (IOException e)
        {
            java.util.logging.Logger.getLogger(SegLoginBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private String getIpAdressClient()throws IllegalStateException
    {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null)
        {
            ipAddress = request.getRemoteAddr();
        }
        System.out.println("ipAddress:" + ipAddress);
        return ipAddress;
    }

    public String ipAdressClient()
    {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        /* String ipAddress = request.getHeader("X-FORWARDED-FOR");
         if (ipAddress == null) {
         ipAddress = request.getRemoteAddr();
         }
         */
        String ipAddress = request.getRemoteAddr();
        System.out.println("ipAddress:" + ipAddress);
        return ipAddress;
    }

    /*verifica as autorizacoes para determinada funcionalidade (p:menu/p:menuItem )
     cadeiaMenus: sequencia de menus ate ao menuItem separadas com o carater ponto e virgula (;) */
    public boolean renderedMenu(String cadeiaMenus)
    {
        System.out.println("sessaoActual Verif: " + sessaoActual);
        if (sessaoActual != null)
        {
            SegFuncionalidade modulos = perfilFuncionalidadeFacade.existeFuncionalidadePerfil(sessaoActual.getFkIdPerfil().getPkIdPerfil(), cadeiaMenus);
            if (modulos != null)
            {
                return true;
            }
        }
        else
        {
            //termino da sessao
            redirectedForPage("../sessaoExpiradaSeg.xhtml"); 
            return false;
        }
        return false;
    }

    /*verifica as autorizacoes para  renderizar formulario e dialog (p:form,h:form e p:dialog )
     urlPattern padrao do url que univocamente especifica o formulario */
    public boolean renderedFormulario(String urlPattern)
    {
        //System.out.println("sessaoActual id perfil:"+sessaoActual.getIdSegPerf().getIdSegPerf() + " " +permissao);
        boolean temPermissao = perfilFuncionalidadeFacade.temAcessoPermissaoUtilizador(sessaoActual.getFkIdPerfil().getPkIdPerfil(), urlPattern);
        if (temPermissao)
        {
            System.out.println("tem");
            return true;

        }
        else
        {
            System.out.println("nao");
            return false;
        }
    }

    public void carrgamentoInicialAplicacao() throws Exception
    {
        
        List<SegConta> contas = contaFacade.findAll();
        if (contas == null || contas.isEmpty())
        {
            System.out.println("Iniciando Carregamento Inicial de Dados...");
            System.out.println("Verificando conta Root...");
            GrlSexoExcelBean.getInstanciaBean().carregarSexoTabela();
            GrlEstadoCivilExcelBean.getInstanciaBean().carregarEstadoCivilTabela();
            GrlReligiaoExcelBean.getInstanciaBean().carregarReligiaoTabela();
            GrlPaisExcelBean.getInstanciaBean().carregarPaisTabela();
            GrlProvinciaExcelBean.getInstanciaBean().carregarProvinciaTabela();
            GrlMunicipioExcelBean.getInstanciaBean().carregarMunicipioTabela();
            GrlComunaExcelBean.getInstanciaBean().carregarComunaTabela();
            GrlAreaInternaExcelBean.getInstanciaBean().carregarAreaInternaTabela();
//            GrlDistritoExcelBean.getInstanciaBean().carregarDistritoTabela();
            AdmsCarregamentoInicialPacientesBean.getInstanciaBean().carregarPacientesCasoAindaNaoTenhamSidoCarregados();

            System.out.println("Verificando Funcionario...");
            if (funcionalidadeFacade.findAll().isEmpty())
            {
                RhCarregarTabelasBean.getInstanciaBean().criarFuncionarioRoot();
            }

            System.out.println("Verificando Funcionalidades...");
            if (funcionalidadeFacade.findAll().isEmpty())
            {
                SegCarregarTabelas.actualizarSegurancaTabelas();
            }

            System.out.println("Verificando Perfil...");
            criarPerfilRoot();

            System.out.println("Verificando Conta...");
            criarContaRoot();

            System.out.println("terminou");
        }
    }

    public void adicionarFuncionalidadesAoPerfil(SegPerfil perfilRoot)
    {
        for (SegFuncionalidade funcionalidade : funcionalidadeFacade.findAll())
        {
            SegPerfilHasFuncionalidade perfilHasFuncionalidade = new SegPerfilHasFuncionalidade();
            System.out.println("Adicionando funcionalidade " + funcionalidade.getNome() + " ao perfil");
            perfilHasFuncionalidade.setFkIdPerfil(perfilRoot);
            perfilHasFuncionalidade.setFkIdFuncionalidade(funcionalidade);
            perfilFuncionalidadeFacade.create(perfilHasFuncionalidade);
        }
        System.out.println("funcionalidade adicionadas com sucesso.");
    }

    public void criarPerfilRoot()
    {
        SegPerfil perfilRoot = new SegPerfil();
        if (perfilFacade.findAll().isEmpty())
        {
            System.out.println("criando o perfil root");
            perfilRoot.setDescricao("Administrador");
            perfilFacade.create(perfilRoot);
            System.out.println("perfil root criado com suceso");
        }

        if (perfilFuncionalidadeFacade.findAll().isEmpty())
        {
            adicionarFuncionalidadesAoPerfil(perfilRoot);
        }
    }

    public void criarContaRoot() throws Exception
    {
        if (contaFacade.findAll().isEmpty())
        {
            
                System.out.println("criando conta root");
                int PK_ID_FUNCIONARIO = 1;
                int FK_ID_PERFIL = 1;
                String nomeUtilizador = "root";
                String palavraPasse = "root.admin001";
                
                SegConta contaRoot = new SegConta();
                contaRoot.setDataCadastro(new Date());
                contaRoot.setEstadoConta(true);
                contaRoot.setFkIdFuncionario(new RhFuncionario(PK_ID_FUNCIONARIO));
                contaRoot.setNomeUtilizador(nomeUtilizador);
                contaRoot.setPalavraPasse(EncriptacaoDecriptacao.encrypt(key, iv, palavraPasse));
                contaRoot.setPrimeiroLoginConta(false);
                contaRoot.setFkIdPerfil(new SegPerfil(FK_ID_PERFIL));
                contaRoot.setUltimoAcessoConta(new Date());
                
                contaFacade.create(contaRoot);
                System.out.println("conta root criada com suceso");
            
            
        }
    }

}
