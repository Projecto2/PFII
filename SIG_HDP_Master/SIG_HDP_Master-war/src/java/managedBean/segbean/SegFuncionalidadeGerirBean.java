/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import sessao.SegFuncionalidadeFacade;
import entidade.SegFuncionalidade;
import entidade.SegTipoFuncionalidade;
import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.model.TreeNode;
import sessao.SegTipoFuncionalidadeFacade;
import util.GeradorCodigo;

import util.seg.*;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class SegFuncionalidadeGerirBean implements Serializable
{

    @EJB
    private SegTipoFuncionalidadeFacade segTipoFuncionalidadeFacade;

    @EJB
    private SegFuncionalidadeFacade segFuncionalidadeFacade;

    @Resource
    private UserTransaction userTransaction;

    private SegFuncionalidade segFuncionalidade;

    private String segFuncionalidadeNome, segFuncionalidadeUrlPadrao;

    private SegTreeFuncionalidadesAbstract seqTreeFuncionalidades;

    private boolean funcionalidadesNovaRedered, funcionalidadesMenuRedered;

    /**
     * Creates a new instance of SegFuncionalidadeCadastrarBean
     */
    public SegFuncionalidadeGerirBean()
    {
    }

    public static SegFuncionalidadeGerirBean getInstanciaBean()
    {
        return (SegFuncionalidadeGerirBean) GeradorCodigo.getInstanciaBean("segFuncionalidadeGerirBean");
    }

    public void init()
    {
        inicializar();
    }

    public void inicializar()
    {
        funcionalidadesNovaRedered = funcionalidadesMenuRedered = false;
        segFuncionalidade = SegFuncionalidadeBean.getInstancia();
        initSeqTreeFuncionalidades();

        gerarSeqTreeFuncionalidades();

        //System.err.println("2: SegFuncionalidadeGerirBean.inicializar()");
    }

    public void gerarSeqTreeFuncionalidades()
    {
        seqTreeFuncionalidades.initRoot();
    }

    public void initSeqTreeFuncionalidades()
    {
        seqTreeFuncionalidades = new SegTreeFuncionalidadesAbstract(this.segFuncionalidadeFacade)
        {
            @Override
            public void onNodeSelect(NodeSelectEvent event)
            {
                super.onNodeSelect(event);

                funcionalidadesMenuRedered = true;
                System.err.println("0: SegFuncionalidadeGerirBean.onNodeSelect()");
            }

            @Override
            public void onNodeUnSelect(NodeUnselectEvent event)
            {
                //System.err.println("Node Data ::" + event.getTreeNode().getData() + " :: UnSelected");
            }

            @Override
            public void onNodeExpand(NodeExpandEvent event)
            {
                String node = ((SegFuncionalidade)event.getTreeNode().getData()).getNome();
                //System.err.println("0: SegFuncionalidadeGerirBean.onNodeExpand()\tnode: " + node);
            }

            @Override
            public void onNodeCollapse(NodeCollapseEvent event)
            {
                String node = ((SegFuncionalidade)event.getTreeNode().getData()).getNome();
                //System.err.println("0: SegFuncionalidadeGerirBean.onNodeCollapse()\tnode: " + node);
            }

            @Override
            public void initSelectedNode()
            {
                //System.err.println("0: SegFuncionalidadeGerirBean.inicializarTree()");
            }

        };
    }

    public void criarNovaFuncionalidade()
    {
System.err.println("0: SegFuncionalidadeGerirBean.criarNovaFuncionalidade()");
        funcionalidadesNovaRedered = true;
        inicSegFuncionalidadePai();
System.err.println("1: SegFuncionalidadeGerirBean.criarNovaFuncionalidade()");        
    }

    public void installFuncionalidadeNome()
    {
        segFuncionalidade.setNome(segFuncionalidadeNome.trim());
        System.err.println("0: SegFuncionalidadeGerirBean.actualizaTipoFuncionalidade()\tsegFuncionalidadeNome: " + segFuncionalidadeNome);
    }
    
    public void installFuncionalidadeUrlPadrao()
    {
        this.segFuncionalidade.setUrlPadrao(segFuncionalidadeUrlPadrao.trim());
        System.err.println("0: SegFuncionalidadeGerirBean.installFuncionalidadeUrlPadrao()\tsegFuncionalidadeUrlPadrao: " + segFuncionalidadeUrlPadrao);
        System.err.println("1: SegFuncionalidadeGerirBean.installFuncionalidadeUrlPadrao()\tsegFuncionalidade.urlPadrao: " + segFuncionalidade.getUrlPadrao());
    }

    public void installTipoFuncionalidade()
    {
        int pkIdTipoFuncionalidade = this.segFuncionalidade.getFkIdTipoFuncionalidade().getPkIdTipoFuncionalidade();
        SegTipoFuncionalidade segTipoFuncionalidade = this.segTipoFuncionalidadeFacade.find(pkIdTipoFuncionalidade);
        segFuncionalidade.setFkIdTipoFuncionalidade(segTipoFuncionalidade);
        System.err.println("0: SegFuncionalidadeGerirBean.actualizaTipoFuncionalidade()\tsegFuncionalidade.nome: " + segFuncionalidade.getNome());
    }

    public void gravarNovaFuncionalidade() throws SystemException
    {
System.err.println("0: SegFuncionalidadeGerirBean.gravarNovaFuncionalidade()");        
        if (!isWellFilledNovaFuncionalidade())
        {
System.err.println("1: SegFuncionalidadeGerirBean.gravarNovaFuncionalidade()");            
            return;
        }
System.err.println("2: SegFuncionalidadeGerirBean.gravarNovaFuncionalidade()");        
        try
        {
            this.userTransaction.begin();
            this.segFuncionalidadeFacade.create(this.segFuncionalidade);
            this.userTransaction.commit();

            util.Mensagem.sucessoMsg("Funcionalidade '" + this.segFuncionalidade.getNome()
                + "' gravada com sucesso");

            this.seqTreeFuncionalidades.gerarChildNodeInSelectedNode(segFuncionalidade);
            this.funcionalidadesNovaRedered = false;
            //System.err.println("3: SegFuncionalidadeGerirBean.gravarNovaFuncionalidade()");
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println(e.toString());
                util.Mensagem.erroMsg("Falha na gravacao da Funcionalidade '" + this.segFuncionalidade.getNome()
                    + "' , mas a operacao de rollback foi bem sucedida");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
                util.Mensagem.erroMsg("Falha na gravacao da Funcionalidade '" + this.segFuncionalidade.getNome()
                    + "' , e a operacao de rollback foi mal sucedida");
            }
        }
    }

    public boolean isWellFilledNovaFuncionalidade()
    {
        if (this.segFuncionalidade.getNome() == null)
        {
            util.Mensagem.erroMsg("Nome da Funcionalidade não está definido");
            return false;
        }
        SegTipoFuncionalidade segTipoFuncionalidade = segFuncionalidade.getFkIdTipoFuncionalidade();
        if (segTipoFuncionalidade == null || segTipoFuncionalidade.equals(new SegTipoFuncionalidade()))
        {
            util.Mensagem.erroMsg("Tipo da Funcionalidade não está definido");
            return false;
        }
        if (this.segFuncionalidade.getUrlPadrao() == null)
        {
            util.Mensagem.erroMsg("Padrão do URL da Funcionalidade não está definido");
            return false;
        }
        return true;
    }

    public void inicSegFuncionalidadePai()
    {
System.err.println("0: SegFuncionalidadeGerirBean.inicSegFuncionalidadePai()");
        String funcionalidadePaiNome = this.seqTreeFuncionalidades.getSelectedNodeName();
        
        if (funcionalidadePaiNome.equals(util.Defs.APPLICATION_NAME))
        {
System.err.println("1: SegFuncionalidadeGerirBean.inicSegFuncionalidadePai()\tfuncionalidadePaiNome: " + funcionalidadePaiNome);            
            this.segFuncionalidade.setFkIdFuncionalidadePai(null);
System.err.println("1.1: SegFuncionalidadeGerirBean.inicSegFuncionalidadePai()");
        }
        else
        {
            SegFuncionalidade segFuncionalidadePai;
System.err.println("2: SegFuncionalidadeGerirBean.inicSegFuncionalidadePai()\tfuncionalidadePaiNome: " + funcionalidadePaiNome);                        
            segFuncionalidadePai = this.seqTreeFuncionalidades.getSegFuncionalidadeSelectedNode();
            this.segFuncionalidade.setFkIdFuncionalidadePai(segFuncionalidadePai);
System.err.println("3: SegFuncionalidadeGerirBean.inicSegFuncionalidadePai()");
        }
    }

    

    public SegFuncionalidadeFacade getSegFuncionalidadeFacade()
    {
        return segFuncionalidadeFacade;
    }

    public void setSegFuncionalidadeFacade(SegFuncionalidadeFacade segFuncionalidadeFacade)
    {
        this.segFuncionalidadeFacade = segFuncionalidadeFacade;
    }

    public SegTreeFuncionalidadesAbstract getSeqTreeFuncionalidades()
    {
        return seqTreeFuncionalidades;
    }

    public void setSeqTreeFuncionalidades(SegTreeFuncionalidadesAbstract seqTreeFuncionalidades)
    {
        this.seqTreeFuncionalidades = seqTreeFuncionalidades;
    }

    public boolean isFuncionalidadesNovaRedered()
    {
        return funcionalidadesNovaRedered;
    }

    public void setFuncionalidadesNovaRedered(boolean funcionalidadesNovaRedered)
    {
        this.funcionalidadesNovaRedered = funcionalidadesNovaRedered;
    }

    public boolean isFuncionalidadesMenuRedered()
    {
        return funcionalidadesMenuRedered;
    }

    public void setFuncionalidadesMenuRedered(boolean funcionalidadesMenuRedered)
    {
        this.funcionalidadesMenuRedered = funcionalidadesMenuRedered;
    }

    public SegFuncionalidade getSegFuncionalidade()
    {
        return segFuncionalidade;
    }

    public void setSegFuncionalidade(SegFuncionalidade segFuncionalidade)
    {
        this.segFuncionalidade = segFuncionalidade;
    }

    public String getSegFuncionalidadeNome()
    {
        return segFuncionalidadeNome;
    }

    public void setSegFuncionalidadeNome(String segFuncionalidadeNome)
    {
        this.segFuncionalidadeNome = segFuncionalidadeNome;
    }

    public String getSegFuncionalidadeUrlPadrao()
    {
        return segFuncionalidadeUrlPadrao;
    }

    public void setSegFuncionalidadeUrlPadrao(String segFuncionalidadeUrlPadrao)
    {
        this.segFuncionalidadeUrlPadrao = segFuncionalidadeUrlPadrao;
    }

}
