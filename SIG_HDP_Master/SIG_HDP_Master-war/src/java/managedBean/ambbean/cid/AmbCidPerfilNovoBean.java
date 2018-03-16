/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;


import entidade.AmbCidConfiguracoes;
import entidade.AmbCidPerfilTipos;
import entidade.AmbCidPerfis;
import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidPerfilTiposFacade;
import sessao.AmbCidPerfisFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;
import util.GeradorCodigo;

import util.amb.AmbCidTreePerfisAbstract;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidPerfilNovoBean implements Serializable
{

    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;

    @EJB
    private AmbCidPerfilTiposFacade ambCidPerfilTiposFacade;

    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @Resource
    private UserTransaction userTransaction;

    private AmbCidPerfis ambCidPerfis;

    private AmbCidConfiguracoes ambCidConfiguracoes;

    //private TreeNode root, root2;
    private AmbCidTreePerfisAbstract ambCidTreePerfis;

    private String nomeDoPerfil, perfilPai;

    private int idConta;

    private boolean doencasDisable, gravarDisable,
        tipoPerfilSORrendered;

    /**
     * Creates a new instance of AmbCidPerfilNovoBean
     */
    public AmbCidPerfilNovoBean()
    {
    }

    public static AmbCidPerfilNovoBean getInstanciaBean()
    {
        return (AmbCidPerfilNovoBean) GeradorCodigo.getInstanciaBean("ambCidPerfilNovoBean");
    }

    public String init(AmbCidPerfis ambCidPerfis)
    {
        this.ambCidPerfis = ambCidPerfis;
        initAmbCidConfiguracoes();
        perfilPai = util.amb.Defs.CID_10;
              
        System.err.println("0: AmbCidPerfilNovoBean.init()");
        activarGravar();
        System.err.println("1: AmbCidPerfilNovoBean.init()");
        tipoPerfilSORrendered = false;
        
        System.err.println("2: AmbCidPerfilNovoBean.init()");
        initAmbCidPerfis();
        System.err.println("3: AmbCidPerfilNovoBean.init()");

        initAmbCidTreePerfis();
        System.err.println("4: AmbCidPerfilNovoBean.init()");
        List<String> listaAmbCidPerfisNomes = AmbCidPerfisBean.getInstanciaBean().findAllOrderByPkIdNome(ambCidPerfis.getFkIdDono(), ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
        System.err.println("5: AmbCidPerfilNovoBean.init()");
        ambCidTreePerfis.initRoot(listaAmbCidPerfisNomes);
        System.err.println("6: AmbCidPerfilNovoBean.init()");
        return util.amb.Defs.AMB_CID_PERFIL_NOVO;
    }

    public void initAmbCidConfiguracoes()
    {
        idConta = this.ambCidPerfis.getFkIdDono().getPkIdConta();
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(idConta);    
    }
    
    public void activarGravar()
    {
        doencasDisable = true;
        gravarDisable = false;
    }

    public void initAmbCidTreePerfis()
    {
        ambCidTreePerfis = new AmbCidTreePerfisAbstract(this.ambCidPerfisFacade)
        {
            @Override
            public void onNodeSelect(NodeSelectEvent event)
            {
                super.onNodeSelect(event);

                perfilPai = this.selectedNode.getData().toString();
                AmbCidPerfisDoencasBean ambCidPerfisDoencasBean = AmbCidPerfisDoencasBean.getInstanciaBean();
                if (! ambCidPerfisDoencasBean.temDoencasRegistadas(perfilPai))
                {
                    util.Mensagem.warnMsg("O perfil '" + perfilPai + "' nao tem doenças cadastradas.\nPor favor, cadastre primeiro pelo menos uma doença nesse perfil!!!");
                   
                    selectedNode.setSelected(false);
                    this.root2.setSelected(true);
                    selectedNode = root2;
                    perfilPai = this.selectedNode.getData().toString();
                    return;
                }
                //System.err.println("0: AmbCidPerfilNovoBean.onNodeSelect()\tnode: " + perfilPai);
                installPerfilPai();
            }

            @Override
            public void onNodeUnSelect(NodeUnselectEvent event)
            {
                //System.err.println("Node Data ::" + event.getTreeNode().getData() + " :: UnSelected");
            }

            @Override
            public void onNodeExpand(NodeExpandEvent event)
            {
                String node = event.getTreeNode().getData().toString();
                //System.err.println("0: AmbCidPerfilNovoBean.onNodeExpand()\tnode: " + node);
            }

            @Override
            public void onNodeCollapse(NodeCollapseEvent event)
            {
                String node = event.getTreeNode().getData().toString();
                //System.err.println("0: AmbCidPerfilNovoBean.onNodeCollapse()\tnode: " + node);
            }

        };
    }

    public void initPerfilPai()
    {
        perfilPai = util.amb.Defs.CID_10;
        AmbCidPerfis ambCidPerfisPai = this.ambCidPerfisFacade.find(perfilPai);
        this.ambCidPerfis.setFkIdPerfilPai(ambCidPerfisPai);
    }
    
    public void initAmbCidPerfis()
    {
        
        System.err.println("0: AmbCidPerfilNovoBean.initAmbCidPerfis()");
        initPerfilPai();
        //ambCidPerfis = AmbCidPerfisBean.getInstancia();
        System.err.println("1: AmbCidPerfilNovoBean.initAmbCidPerfis()");
        // initAmbCidPerfisEspecialidades();
        System.err.println("2: AmbCidPerfilNovoBean.initAmbCidPerfis()");
        //System.err.println("0: AmbCidPerfilNovoBean.initAmbCidPerfis()\tprofissao: " + ambCidPerfis.getFkIdEspecialidades().getFkIdProfissao().getPkIdProfissao());
        //System.err.println("1: AmbCidPerfilNovoBean.initAmbCidPerfis()\tespecialidade: " + ambCidPerfis.getFkIdEspecialidades().getDescricao());
        AmbCidPerfilTipos ambCidPerfilTipos = this.ambCidPerfilTiposFacade.findByNome("Privado");
        System.err.println("3: AmbCidPerfilNovoBean.initAmbCidPerfis()");
        this.ambCidPerfis.setFkIdTipo(ambCidPerfilTipos);
    }

    GrlEspecialidade initEspecialidadePreferencial()
    {
        //System.err.println("0: AmbCidPerfilNovoBean.initEspecialidadePreferencial()\tidConta: " + idConta);
        idConta = this.ambCidPerfis.getFkIdDono().getPkIdConta();
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(idConta);
        int idEspecialidade = ambCidConfiguracoes.getIdEspecialidade();
        if (idEspecialidade == 0)
        {
            return null;
        }
        //System.err.println("1: AmbCidPerfilNovoBean.initEspecialidadePreferencial()\tidConta: " + idConta);
        return this.grlEspecialidadeFacade.find(idEspecialidade);
    }

    public void initAmbCidPerfisEspecialidades()
    {
        System.err.println("0: AmbCidPerfilNovoBean.initAmbCidPerfisEspecialidades()\tidConta: " + idConta);

        GrlEspecialidade especialidadePreferencial = initEspecialidadePreferencial();
        if (especialidadePreferencial != null)
        {
            ambCidPerfis.setFkIdEspecialidades(especialidadePreferencial);
            System.err.println("1: AmbCidPerfilNovoBean.initAmbCidPerfisEspecialidades()\tidConta: " + idConta);
            return;
        }
        RhProfissao rhProfissao = this.rhProfissaoFacade.findByDescricao("Médico");
        ambCidPerfis.getFkIdEspecialidades().setFkIdProfissao(rhProfissao);
        System.err.println("2: AmbCidPerfilNovoBean.initAmbCidPerfisEspecialidades()\tidConta: " + idConta
            + "\nprofissao: " + (rhProfissao == null ? "null" : rhProfissao.getDescricao()));
    }

    public void installTipo(int pkIdTipos)
    {
        AmbCidPerfilTipos ambCidPerfilTipos = this.ambCidPerfilTiposFacade.find(pkIdTipos);
        this.ambCidPerfis.setFkIdTipo(ambCidPerfilTipos);
        //System.err.println("0: AmbCidPerfilNovoBean.installTipo()");
        //this.gravarDisable = !formularioValido(true, false);
        activarGravar();
    }

    public void installEspecialidade(int pkIdEspecialidade)
    {
//System.err.println("0: AmbCidPerfilNovoBean.installEspecialidade()\tpkIdEspecialidade: " + pkIdEspecialidade);
        GrlEspecialidade grlEspecialidade = this.grlEspecialidadeFacade.find(pkIdEspecialidade);
        //grlEspecialidade.setFkIdProfissao(this.ambCidPerfis.getFkIdEspecialidades().getFkIdProfissao());
        this.ambCidPerfis.setFkIdEspecialidades(grlEspecialidade);
        //this.gravarDisable = !formularioValido(true, false);
        activarGravar();
//System.err.println("1: AmbCidPerfilNovoBean.installEspecialidade()\tpkIdEspecialidade: " + ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
    }

    
    public void installPerfilPai()
    {
        //System.err.println("0: AmbCidPerfilNovoBean.installPerfilPai()");
        if (this.ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade()== null)
        {
            perfilPai = null;
            util.Mensagem.warnMsg("Por favor, indique primeiro a especialidade !!!");
            return;
        }
        //System.err.println("1: AmbCidPerfilNovoBean.installPerfilPai()");
        if (this.perfilPai.equals(util.amb.Defs.CID_10))
        {
            this.ambCidPerfis.setFkIdPerfilPai(null);
            // this.ambCidPerfis.setProfundidade(1);
            //this.gravarDisable = !formularioValido(true, false);
            activarGravar();
            this.setTipoPerfilSORrendered(true);
            //System.err.println("2: AmbCidPerfilNovoBean.installPerfilPai()");
            return;
        }

        AmbCidPerfis ambCidPerfisPai = this.ambCidPerfisFacade.find(perfilPai);
        this.ambCidPerfis.setFkIdPerfilPai(ambCidPerfisPai);
        //  this.ambCidPerfis.setProfundidade(ambCidPerfisPai.getProfundidade() + 1);
        //this.gravarDisable = !formularioValido(true, false);
        if (gravarDisable)
        {
            this.doencasDisable = true;
        }

        tipoPerfilSORrendered = !ambCidPerfisPai.getFkIdTipo().getNome().equals("Privado");

        //System.err.println("3: AmbCidPerfilNovoBean.installPerfilPai()");
    }

    @SuppressWarnings("empty-statement")
    public void installNomeDoPerfil()
    {
        if (validarNomeDoPerfil(true))
        {
            formularioValidoExceptNomeDoPerfil(false);
        }
        activarGravar();
    }

    public boolean validarNomeDoPerfil(boolean fillingFlag)
    {
        nomeDoPerfil = (nomeDoPerfil == null) ? null : nomeDoPerfil.trim();
        //System.err.println("0: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()\tnomeDoPerfil: " + nomeDoPerfil);
        if (nomeDoPerfil == null || nomeDoPerfil.isEmpty())
        {
            util.Mensagem.erroMsg("nome do perfil nao definido");
            nomeDoPerfil = "";
            //System.err.println("1: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
            return false;
        }
        char ch;
        //System.err.println("2: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
        int len = util.amb.Defs.TOKENS_SEPARATORS.length();
        for (int i = 0; i < len; i++)
        {
            ch = util.amb.Defs.TOKENS_SEPARATORS.charAt(i);
            if (nomeDoPerfil.indexOf(ch) != -1)
            {
                util.Mensagem.erroMsg("caracter " + i + " '" + ch + "' invalido para o nome do perfil");
                nomeDoPerfil = "";
                //System.err.println("3: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
                return false;
            }
        }
        AmbCidPerfis perfil = this.ambCidPerfisFacade.find(this.nomeDoPerfil);
        if (perfil != null && fillingFlag)
        {
            util.Mensagem.erroMsg("ja existe um perfil com o nome '" + nomeDoPerfil + "'");
            nomeDoPerfil = "";
            //System.err.println("4: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
            return false;
        }
        this.ambCidPerfis.setPkIdNome(nomeDoPerfil);
        //System.err.println("5: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
        return true;
    }

    public boolean validarEspecialidade(boolean msgActivated)
    {
        GrlEspecialidade grlEspecialidade = ambCidPerfis.getFkIdEspecialidades();

        if (grlEspecialidade.equals(new GrlEspecialidade()))
        {

            //System.err.println("1: AmbCidPerfilNovoBean.validarEspecialidade()");
            if (msgActivated)
            {
                util.Mensagem.erroMsg("indique a especialidade");
            }
            return false;
        }
        return true;
    }

    public boolean validarPerfilPai(boolean msgActivated)
    {
        if (perfilPai == null || perfilPai.isEmpty())
        {
            //System.err.println("0: AmbCidPerfilNovoBean.validarPerfilPai()");
            if (msgActivated)
            {
                util.Mensagem.erroMsg("indique o perfil de base");
            }
            return false;
        }
        return true;
    }

    public boolean validarPerfilTipos(boolean msgActivated)
    {
        if (perfilPai == null || perfilPai.isEmpty())
        {
            //System.err.println("3: AmbCidPerfilNovoBean.validarPerfilTipos()");
            if (msgActivated)
            {
                util.Mensagem.erroMsg("indique o perfil de base");
            }
            return false;
        }
        return true;
    }

    public boolean formularioValidoExceptNomeDoPerfil(boolean msgActivated)
    {
//System.err.println("0: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");

        if (!validarEspecialidade(msgActivated))
        {
            return false;
        }
//System.err.println("1: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");

        if (!validarPerfilPai(msgActivated))
        {
            return false;
        }

//System.err.println("2: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
        if (!validarPerfilTipos(msgActivated))
        {
            return false;
        }

//System.err.println("3: AmbCidPerfilNovoBean.formularioValidoExceptNomeDoPerfil()");
        return true;

    }

    public boolean formularioValido(boolean fillingFlag, boolean msgFlag)
    {
        System.err.println("0: AmbCidPerfilNovoBean.formularioValido()");
        if (!validarNomeDoPerfil(fillingFlag))
        {
            System.err.println("1: AmbCidPerfilNovoBean.formularioValido()");
            return false;
        }
        System.err.println("2: AmbCidPerfilNovoBean.formularioValido()");

        return formularioValidoExceptNomeDoPerfil(msgFlag);
    }

    public String irParaDoencasDoPerfil()
    {
        //System.err.println("0: AmbCidPerfilNovoBean.irParaDoencasDoPerfil()");
        //if (!formularioValido(false))
        //{
        //  //System.err.println("1: AmbCidPerfilNovoBean.irParaDoencasDoPerfil()");
        //return "perfilNovoAmb?faces-redirect=true";
        //}
        return "perfilNovoDoencasAmb?faces-redirect=true";
    }

    public void installPerfilPreferencial()
    {
        //System.err.println("0: AmbCidPerfilNovoBean.installPerfilPreferencial()\tperfilPreferencial: " + perfilPreferencial);
        this.activarGravar();
    }

    public String gravar()
    {
        System.err.println("0: AmbCidPerfilNovoBean.gravar()");
        if (!formularioValido(true, true))
        {
            System.err.println("1: AmbCidPerfilNovoBean.gravar()");
            return "perfilNovoAmb?faces-redirect=true";
        }

        System.err.println("2: AmbCidPerfilNovoBean.gravar()");
        try
        {
            this.userTransaction.begin();
            this.ambCidPerfisFacade.create(this.ambCidPerfis);
            this.userTransaction.commit();
System.err.println("2.1: AmbCidPerfilNovoBean.gravar()");
            util.Mensagem.sucessoMsg("perfil '" + this.ambCidPerfis.getPkIdNome()
                + "' gravado com sucesso");
System.err.println("2.2: AmbCidPerfilNovoBean.gravar()");            
            //  util.amb.Mensagem.enviarJanelaMensagemInformacao("Sucesso", "perfil '" + this.ambCidPerfis.getPkIdNome()
            //    + "' gravado com sucesso");
            
System.err.println("2.3: AmbCidPerfilNovoBean.gravar()");
            List<String> listaAmbCidPerfisNomes = AmbCidPerfisBean.getInstanciaBean().findAllOrderByPkIdNome(ambCidPerfis.getFkIdDono(), ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
            ambCidTreePerfis.initRoot(listaAmbCidPerfisNomes);
System.err.println("2.4: AmbCidPerfilNovoBean.gravar()");
            this.doencasDisable = false;
            this.gravarDisable = true;
System.err.println("3: AmbCidPerfilNovoBean.gravar()");
            return "perfilNovoAmb?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println(e.toString());
                util.Mensagem.erroMsg("Falha na gravacao do perfil '" + this.ambCidPerfis.getPkIdNome()
                    + "' , mas a operacao de rollback foi bem sucedida");
                System.err.println("3.1: AmbCidPerfilNovoBean.gravar()");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
                util.Mensagem.erroMsg("Falha na gravacao do perfil '" + this.ambCidPerfis.getPkIdNome()
                    + "' , e a operacao de rollback foi mal sucedida");
                System.err.println("4: AmbCidPerfilNovoBean.gravar()");
            }
        }
        System.err.println("5: AmbCidPerfilNovoBean.gravar()");
        return "perfilNovoAmb?faces-redirect=true";
    }

    public AmbCidPerfis getAmbCidPerfis()
    {
        return ambCidPerfis;
    }

    public void setAmbCidPerfis(AmbCidPerfis ambCidPerfis)
    {
        this.ambCidPerfis = ambCidPerfis;
    }

    public String getPerfilPai()
    {
        return perfilPai;
    }

    public void setPerfilPai(String perfilPai)
    {
        this.perfilPai = perfilPai;
    }

    public String getNomeDoPerfil()
    {
        return nomeDoPerfil;
    }

    public void setNomeDoPerfil(String nomeDoPerfil)
    {
        this.nomeDoPerfil = nomeDoPerfil;
    }

    public boolean isDoencasDisable()
    {
        return doencasDisable;
    }

    public void setDoencasDisable(boolean doencasDisable)
    {
        this.doencasDisable = doencasDisable;
    }

    public boolean isGravarDisable()
    {
        return gravarDisable;
    }

    public void setGravarDisable(boolean gravarDisable)
    {
        this.gravarDisable = gravarDisable;
    }

    public boolean isTipoPerfilSORrendered()
    {
        if (perfilPai == null || this.perfilPai.equals(util.amb.Defs.CID_10))
            return true;

        AmbCidPerfis ambCidPerfisPai = this.ambCidPerfisFacade.find(perfilPai);

        tipoPerfilSORrendered = !ambCidPerfisPai.getFkIdTipo().getNome().equals("Privado");
        return tipoPerfilSORrendered;
    }

    public void setTipoPerfilSORrendered(boolean tipoPerfilSORrendered)
    {
        this.tipoPerfilSORrendered = tipoPerfilSORrendered;
    }

    public AmbCidTreePerfisAbstract getAmbCidTreePerfis()
    {
        return ambCidTreePerfis;
    }

    public void setAmbCidTreePerfis(AmbCidTreePerfisAbstract ambCidTreePerfis)
    {
        this.ambCidTreePerfis = ambCidTreePerfis;
    }

}
