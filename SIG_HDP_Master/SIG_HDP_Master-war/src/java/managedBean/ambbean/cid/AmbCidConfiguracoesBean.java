/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidConfiguracoes;
import entidade.AmbCidDoencasPrioridades;
import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidPerfisFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;
import util.amb.AmbCidTreePerfisAbstract;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidConfiguracoesBean implements Serializable
{

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;

    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    @Resource
    private UserTransaction userTransaction;

    private GrlEspecialidade especialidadePreferencial;

    private SegConta segConta;

    private String perfilPreferencial;

    private int idEspecialidadePreferencial;
    private int idConta, idDoencasPrioridadesPreferencial;

    private AmbCidConfiguracoes ambCidConfiguracoes;

//    private TreeNode root, root2, selectedNode;
    private AmbCidTreePerfisAbstract ambCidTreePerfis;

    private boolean prioridadeDoencaSOMdisable;

    /**
     * Creates a new instance of AmbCidConfiguracoesBean
     */
    public AmbCidConfiguracoesBean()
    {
    }

    public static AmbCidConfiguracoesBean getInstanciaBean()
    {
        return (AmbCidConfiguracoesBean) GeradorCodigo.getInstanciaBean("ambCidConfiguracoesBean");
    }

    public static AmbCidConfiguracoes getInstancia()
    {
        AmbCidConfiguracoesBean ambCidConfiguracoesBean = AmbCidConfiguracoesBean.getInstanciaBean();
        return ambCidConfiguracoesBean.getAmbCidConfiguracoesFacade().loadAmbCidConfiguracoes(ambCidConfiguracoesBean.initSegConta().getPkIdConta());
    }
    
    public void init()
    {
        inicializar();
    }

    public void inicializar()
    {
        prioridadeDoencaSOMdisable = true;
        idDoencasPrioridadesPreferencial = util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA;
        //System.err.println("0: AmbCidConfiguracoesBean.inicializar()");
        initSegConta();
        //System.err.println("1: AmbCidConfiguracoesBean.inicializar()");
        initEspecialidadePreferencial();

        initPerfilPreferencial();
        initAmbCidTreePerfis();

        gerarAmbCidTreePerfis();
        
        //System.err.println("2: AmbCidConfiguracoesBean.inicializar()");
        initDoencasPrioridades();

    }
    
    public void gerarAmbCidTreePerfis()
    {
        List<String> listaAmbCidPerfisNomes = AmbCidPerfisBean.getInstanciaBean().findAllOrderByPkIdNome(segConta, especialidadePreferencial.getPkIdEspecialidade());
        ambCidTreePerfis.initRoot(listaAmbCidPerfisNomes);
    }

    public void createUpdateSegContaOnConfiguracoes()
    {
System.err.println("0: AmbCidConfiguracoesBean.createUpdateSegContaOnConfiguracoes()\tidConta: " + idConta);
        this.ambCidConfiguracoes = this.ambCidConfiguracoesFacade.findAllByIdConta(idConta);
        if (ambCidConfiguracoes != null)
        {
System.err.println("1: AmbCidConfiguracoesBean.createUpdateSegContaOnConfiguracoes()");            
            return;
        }
        this.ambCidConfiguracoes = new AmbCidConfiguracoes();
        ambCidConfiguracoes.setIdConta(idConta);
        ambCidConfiguracoes.setIdEspecialidade(0);
        ambCidConfiguracoes.setIdDoencasPrioridades(idDoencasPrioridadesPreferencial);
        this.createRegister(ambCidConfiguracoes);
System.err.println("2: AmbCidConfiguracoesBean.createUpdateSegContaOnConfiguracoes()");        
    }

    public GrlEspecialidade obterEspecialidadePreferencial()
    {
        this.idEspecialidadePreferencial = ambCidConfiguracoes.getIdEspecialidade();
        if (idEspecialidadePreferencial == 0)
        {
            return null;
        }
        return this.grlEspecialidadeFacade.find(idEspecialidadePreferencial);
    }

    public void initAmbCidTreePerfis()
    {
        ambCidTreePerfis = new AmbCidTreePerfisAbstract(this.ambCidPerfisFacade)
        {
            @Override
            public void onNodeSelect(NodeSelectEvent event)
            {
                super.onNodeSelect(event);
                
                perfilPreferencial = selectedNode.getData().toString();
System.err.println("0: AmbCidConfiguracoesBean.onNodeSelect()\tnode: " + perfilPreferencial);
                gravarPerfilPreferencial();
System.err.println("1: AmbCidConfiguracoesBean.onNodeSelect()\tnode: " + perfilPreferencial);                
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
                //System.err.println("0: AmbCidConfiguracoesBean.onNodeExpand()\tnode: " + node);
            }

            @Override
            public void onNodeCollapse(NodeCollapseEvent event)
            {
                String node = event.getTreeNode().getData().toString();
                //System.err.println("0: AmbCidConfiguracoesBean.onNodeCollapse()\tnode: " + node);
            }

            @Override
            public void initSelectedNode()
            {
                TreeNode treeNodePerfilPreferencial = util.amb.TreeNodeUtilities.getTreeNode(root, perfilPreferencial);
                if (treeNodePerfilPreferencial == null)
                    return;
                util.amb.TreeNodeUtilities.setSelected(root, treeNodePerfilPreferencial);
                selectedNode = treeNodePerfilPreferencial;
                //System.err.println("0: AmbCidConfiguracoesBean.inicializarTree()");
            }

        };
    }

    public SegConta initSegConta()
    {
        segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        this.idConta = segConta.getPkIdConta();
        createUpdateSegContaOnConfiguracoes();
        return segConta;
    }

    public void initPerfilPreferencial()
    {
        this.perfilPreferencial = ambCidConfiguracoes.getIdNomePerfil();
    }

    public void initDoencasPrioridades()
    {
//System.err.println("0: AmbCidConfiguracoesBean.initDoencasPrioridades()\tambCidConfiguracoes: " + (ambCidConfiguracoes == null ? "null" : "not null"));
//System.err.println("00: AmbCidConfiguracoesBean.initDoencasPrioridades()\tIdDoencasPrioridades: " + ambCidConfiguracoes.getIdDoencasPrioridades());
        this.idDoencasPrioridadesPreferencial = ambCidConfiguracoes.getIdDoencasPrioridades();
//System.err.println("1: AmbCidConfiguracoesBean.initDoencasPrioridades()");
        if (this.idEspecialidadePreferencial != 0 || perfilPreferencial != null)
        {
//System.err.println("2: AmbCidConfiguracoesBean.initDoencasPrioridades()");            
            prioridadeDoencaSOMdisable = false;
        }
//System.err.println("3: AmbCidConfiguracoesBean.initDoencasPrioridades()");        
    }

    public void initEspecialidadePreferencial()
    {
        //System.err.println("0: AmbCidConfiguracoesBean.initEspecialidadePreferencial()\tidConta: " + this.idConta);
        especialidadePreferencial = obterEspecialidadePreferencial();
        if (especialidadePreferencial != null)
        {
            this.idEspecialidadePreferencial = this.especialidadePreferencial.getPkIdEspecialidade();
            //System.err.println("1: AmbCidConfiguracoesBean.initEspecialidadePreferencial()\tidConta: " + this.idConta);
            return;
        }

        RhProfissao rhProfissao = this.rhProfissaoFacade.findByDescricao("Médico");
        this.especialidadePreferencial = new GrlEspecialidade();
        especialidadePreferencial.setFkIdProfissao(rhProfissao);
        //System.err.println("2: AmbCidConfiguracoesBean.initEspecialidadePreferencial()\tidConta: " + this.idConta);
    }

    public void gravarDoencasPrioridades()
    {
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(this.idConta);
        ambCidConfiguracoes.setIdDoencasPrioridades(idDoencasPrioridadesPreferencial);
        this.editRegister(ambCidConfiguracoes);
    }

    public void gravarPerfilPreferencial(boolean estado, String idNomePerfil)
    {
        //System.err.println("0: AAmbCidConfiguracoesBean.gravarPerfilPreferencial()\testado: " + estado);
        //System.err.println("1: AAmbCidConfiguracoesBean.gravarPerfilPreferencial()\tidNomePerfil: " + idNomePerfil);
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(this.idConta);
        if (estado)
        {
            ambCidConfiguracoes.setIdNomePerfil(idNomePerfil);
            this.editRegister(ambCidConfiguracoes);
            //System.err.println("2: AAmbCidConfiguracoesBean.gravarPerfilPreferencial()\tidNomePerfil: " + idNomePerfil);
        }
    }

    public void gravarPerfilPreferencial()
    {
System.err.println("0: AmbCidConfiguracoesBean.gravarPerfilPreferencial()\tperfilPreferencial: " + perfilPreferencial);        
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(this.idConta);
        ambCidConfiguracoes.setIdNomePerfil((perfilPreferencial == null || perfilPreferencial.equals(util.amb.Defs.CID_10)) ? null : perfilPreferencial);
System.err.println("1: AmbCidConfiguracoesBean.gravarPerfilPreferencial()\tAmbCidConfiguracoesBean.perfilPreferencial: " + ambCidConfiguracoes.getIdNomePerfil());                
        this.editRegister(ambCidConfiguracoes);
System.err.println("2: AmbCidConfiguracoesBean.gravarPerfilPreferencial()\tAmbCidConfiguracoesBean.perfilPreferencial: " + ambCidConfiguracoes.getIdNomePerfil());                        
        settingPrioridadeDoencas();
    }

    public void settingPrioridadeDoencas()
    {
        List<AmbCidDoencasPrioridades> ambCidDoencasPrioridades = AmbCidDoencasPrioridadesBean.getInstanciaBean().findAllFromPerfilPreferencial(perfilPreferencial);
        idDoencasPrioridadesPreferencial = util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA;
        int prioridadeDoenca;
        for (AmbCidDoencasPrioridades ambCidDoencasPrioridadeTmp : ambCidDoencasPrioridades)
        {
            prioridadeDoenca = ambCidDoencasPrioridadeTmp.getPkIdDoencasPrioridades();
            if (prioridadeDoenca < idDoencasPrioridadesPreferencial)
            {
                idDoencasPrioridadesPreferencial = prioridadeDoenca;
            }
        }
        //System.err.println("0: AmbCidConfiguracoesBean.settingPrioridadeDoencas()\tidDoencasPrioridadesPreferencial: " + idDoencasPrioridadesPreferencial);
        this.gravarDoencasPrioridades();
        this.prioridadeDoencaSOMdisable = false;
    }

    public void gravarEspecialidade()
    {
        //System.err.println("0: AmbCidConfiguracoesBean.gravarEspecialidade()");
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(this.idConta);
        //System.err.println("1: AmbCidConfiguracoesBean.gravarEspecialidade()\tpkIdEspecialidade: " + especialidadePreferencial.getPkIdEspecialidade());
        this.idEspecialidadePreferencial = this.especialidadePreferencial.getPkIdEspecialidade();

        int idEspecialidadePreferencial_prev = ambCidConfiguracoes.getIdEspecialidade();
        ambCidConfiguracoes.setIdEspecialidade(idEspecialidadePreferencial);
        this.editRegister(ambCidConfiguracoes);
        this.especialidadePreferencial = this.grlEspecialidadeFacade.find(this.idEspecialidadePreferencial);
        if (idEspecialidadePreferencial_prev != idEspecialidadePreferencial)
        {
            //System.err.println("2: AmbCidConfiguracoesBean.gravarEspecialidade()\tpkIdEspecialidade: " + especialidadePreferencial.getPkIdEspecialidade());
            this.perfilPreferencial = null;
            this.gravarPerfilPreferencial();
        }
        this.prioridadeDoencaSOMdisable = true;
        //System.err.println("3: AmbCidConfiguracoesBean.gravarEspecialidade()\tespecialidadePreferencial: " + (especialidadePreferencial == null ? "null" : especialidadePreferencial.getDescricao()));
        gerarAmbCidTreePerfis();
    }

    public boolean createRegister(AmbCidConfiguracoes reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidConfiguracoesFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidConfiguracoesBean.createRegister()");
            return true;
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        //System.err.println("1: AmbCidConfiguracoesBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidConfiguracoes reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidConfiguracoesFacade.edit(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    // metodos get e set
    public GrlEspecialidade getEspecialidadePreferencial()
    {
        return especialidadePreferencial;
    }

    public void setEspecialidadePreferencial(GrlEspecialidade especialidadePreferencial)
    {
        this.especialidadePreferencial = especialidadePreferencial;
    }

    public int getIdEspecialidadePreferencial()
    {
        return idEspecialidadePreferencial;
    }

    public void setIdEspecialidadePreferencial(int idEspecialidadePreferencial)
    {
        this.idEspecialidadePreferencial = idEspecialidadePreferencial;
    }

    public SegConta getSegConta()
    {
        return segConta;
    }

    public void setSegConta(SegConta segConta)
    {
        this.segConta = segConta;
    }

    public int getIdConta()
    {
        return idConta;
    }

    public void setIdConta(int idConta)
    {
        this.idConta = idConta;
    }

    public String getPerfilPreferencial()
    {
        return perfilPreferencial;
    }

    public void setPerfilPreferencial(String perfilPreferencial)
    {
        this.perfilPreferencial = perfilPreferencial;
    }

    public int getIdDoencasPrioridadesPreferencial()
    {
        return idDoencasPrioridadesPreferencial;
    }

    public void setIdDoencasPrioridadesPreferencial(int idDoencasPrioridadesPreferencial)
    {
        this.idDoencasPrioridadesPreferencial = idDoencasPrioridadesPreferencial;
    }

    public boolean isPrioridadeDoencaSOMdisable()
    {
        return prioridadeDoencaSOMdisable;
    }

    public void setPrioridadeDoencaSOMdisable(boolean prioridadeDoencaSOMdisable)
    {
        this.prioridadeDoencaSOMdisable = prioridadeDoencaSOMdisable;
    }

    public AmbCidTreePerfisAbstract getAmbCidTreePerfis()
    {
        return ambCidTreePerfis;
    }

    public void setAmbCidTreePerfis(AmbCidTreePerfisAbstract ambCidTreePerfis)
    {
        this.ambCidTreePerfis = ambCidTreePerfis;
    }

    public AmbCidConfiguracoesFacade getAmbCidConfiguracoesFacade()
    {
        return ambCidConfiguracoesFacade;
    }
}
