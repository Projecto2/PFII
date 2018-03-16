/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.amb;

import util.amb.*;
import entidade.AmbCidAgrupamentos;
import entidade.AmbCidCapitulos;
import entidade.AmbCidCategorias;
import entidade.AmbCidConfiguracoes;
import entidade.AmbCidDoencasPrioridades;
import entidade.AmbCidPerfis;
import entidade.AmbCidPerfisDoencas;
import entidade.AmbCidSubcategorias;
import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import entidade.SegConta;
import java.util.ArrayList;
import java.util.List;
import managedBean.ambbean.cid.AmbCidAgrupamentosBean;
import managedBean.ambbean.cid.AmbCidCapitulosBean;
import managedBean.ambbean.cid.AmbCidCategoriasBean;
import managedBean.ambbean.cid.AmbCidDoencasPrioridadesBean;
import managedBean.ambbean.cid.AmbCidPerfisBean;
import managedBean.ambbean.cid.AmbCidPerfisDoencasBean;
import managedBean.ambbean.cid.AmbCidSubcategoriasBean;
import managedBean.segbean.SegLoginBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidPerfisFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;

/**
 *
 * @author aires
 */
public abstract class AmbCidHipoteseDiagnosticoAbstract
{

    protected AmbCidConfiguracoes ambCidConfiguracoes;

    protected AmbCidPerfisDoencas ambCidPerfisDoencas;
    protected AmbCidPerfis ambCidPerfisPreferencial;
    protected AmbCidTreePerfisAbstract ambCidTreePerfisPreferencial;

    protected GrlEspecialidade especialidadePreferencial;

    protected AmbCidDoencasPrioridades ambCidDoencasPrioridadesPreferencial;

    protected int idEspecialidadePreferencial;

    protected SegConta segConta;

    protected PickListAbstract doencasPickList;

    protected String perfilPreferencial;

    protected int idConta, idDoencasPrioridadesPreferencial;

    protected List<String> subcategoriasDoencasSeleccionadas;

    protected boolean capitulosDoencasSOMdisabled;
    protected boolean perfilPreferencialSOMrendered, prioridadeDoencaSOMrendered;
    protected boolean capitulosDoencasSOMrendered, agrupamentosDoencasSOMrendered;
    protected boolean categoriasDoencasSOMrendered;

    protected AmbCidHipoteseDiagnosticoAbstract()
    {
    }

    public void init()
    {
        initSegConta();
        subcategoriasDoencasSeleccionadas = new ArrayList();
        inicDoencasPickList();

        //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.init()");
        capitulosDoencasSOMdisabled = perfilPreferencialSOMrendered = true;
        prioridadeDoencaSOMrendered = capitulosDoencasSOMrendered = true;
        agrupamentosDoencasSOMrendered = categoriasDoencasSOMrendered = true;

        //System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.init()");
        initAmbCidPerfis();
        //System.err.println("2: AmbCidHipoteseDiagnosticoAbstract.init()");
        initEspecialidadePreferencial();
        //System.err.println("2.1: AmbCidHipoteseDiagnosticoAbstract.init()");

        initAmbCidTreePerfisPreferencial();
        //System.err.println("2.2: AmbCidHipoteseDiagnosticoAbstract.init()");
        gerarAmbCidTreePerfis();

        //System.err.println("3: AmbCidHipoteseDiagnosticoAbstract.init()");
        initAmbCidPerfisDoencas();
        //System.err.println("4: AmbCidHipoteseDiagnosticoAbstract.init()");
        initDoencasPrioridadesPreferencial();
        //System.err.println("5: AmbCidHipoteseDiagnosticoAbstract.init()");
        initCapituloPreferencial();
    }

    public void gerarAmbCidTreePerfis()
    {
        List<String> listaAmbCidPerfisNomes = AmbCidPerfisBean.getInstanciaBean().findAllOrderByPkIdNome(segConta, especialidadePreferencial.getPkIdEspecialidade());
        //System.err.println("2.3: AmbCidHipoteseDiagnosticoAbstract.init()");
        ambCidTreePerfisPreferencial.initRoot(listaAmbCidPerfisNomes);
    }

    // retorna a lista dos nomes das doencas
    public List<String> getListaNomeDoencasSeleccionadas()
    {
        return this.doencasPickList.getListTarget();
    }

    // retorna a lista das doencas
    public List<AmbCidSubcategorias> getListaAmbCidSubcategoriasDasDoencasSeleccionadas()
    {
        List<String> listaNomeDoencasSeleccionadas = getListaNomeDoencasSeleccionadas();
        return this.getAmbCidSubcategoriasFacade().getListaAmbCidSubcategoriasDasDoencasSeleccionadas(listaNomeDoencasSeleccionadas);
    }

    // retorna a lista dos codigos (AmbCidSubcategorias.pkIdSubcategorias) das doencas
    public List<String> getListaPkIdSubcategoriasDasDoencasSeleccionadas()
    {
        List<AmbCidSubcategorias> listaAmbCidSubcategoriasDasDoencasSeleccionadas = getListaAmbCidSubcategoriasDasDoencasSeleccionadas();
        if (listaAmbCidSubcategoriasDasDoencasSeleccionadas == null || listaAmbCidSubcategoriasDasDoencasSeleccionadas.isEmpty())
        {
            return null;
        }
        List<String> lista = new ArrayList();
        for (AmbCidSubcategorias ambCidSubcategorias : listaAmbCidSubcategoriasDasDoencasSeleccionadas)
        {
            lista.add(ambCidSubcategorias.getPkIdSubcategorias());
        }
        return lista;
    }

    public void inicDoencasPickList()
    {
        doencasPickList = new PickListAbstract()
        {
            @Override
            public void inicializar()
            {
                super.inicializar();
            }

            @Override
            public List<String> geraAllSourceNames()
            {
                //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()\tperfilPreferencial: " + perfilPreferencial);
                //System.err.println("01: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()\tidDoencasPrioridadesPreferencial: " + idDoencasPrioridadesPreferencial);
                //System.err.println("02: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()\tambCidPerfisDoencas.getFkIdSubcategorias(): " + (ambCidPerfisDoencas.getFkIdSubcategorias() == null ? "null" : "not null"));
                if (ambCidPerfisDoencas.getFkIdSubcategorias() != null)
                {
                    //System.err.println("03: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()\tambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias(): " + (ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias() == null ? "null" : "not null"));
                }
                List<AmbCidSubcategorias> listAmbCidSubcategorias = AmbCidSubcategoriasBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
                //System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()");
                AmbCidSubcategoriasFacade ambCidSubcategoriasFacade = AmbCidHipoteseDiagnosticoAbstract.this.getAmbCidSubcategoriasFacade();
                //System.err.println("2: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()");
                List<String> lista = ambCidSubcategoriasFacade.obterListaAmbCidSubcategoriasNomes(listAmbCidSubcategorias);
                //System.err.println("3: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()\tlista: " + ((lista == null || lista.isEmpty()) ? "null" : lista.size()));
                if (lista != null && !lista.isEmpty())
                {
                    for (String doenca : lista)
                    {
                        //System.err.println("4: AmbCidHipoteseDiagnosticoAbstract.inicDoencasPickList()\tdoenca: " + doenca);
                    }
                }
                //System.err.println("5: AmbCidHipoteseDiagnosticoAbstract.geraAllSourceNames()");
                return lista;
            }

            @Override
            public List<String> geraAllTargetNames()
            {
                return new ArrayList();
            }

            @Override
            public void inicListSource()
            {
                super.inicListSource();
            }

            @Override
            public void gravar()
            {
            }
        };
    }

    public GrlEspecialidade obterEspecialidadePreferencial()
    {
        this.idEspecialidadePreferencial = ambCidConfiguracoes.getIdEspecialidade();
        if (idEspecialidadePreferencial == 0)
        {
            return null;
        }
        return this.getGrlEspecialidadeFacade().find(idEspecialidadePreferencial);
    }

    public boolean initSubcategoriaPreferencial()
    {
////System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.initSubcategoriaPreferencial()");        
        List<AmbCidSubcategorias> listAmbCidSubcategorias = AmbCidSubcategoriasBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
        if (listAmbCidSubcategorias == null || listAmbCidSubcategorias.isEmpty())
        {
            return false;
        }
        this.doencasPickList.inicializar();
////System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.initSubcaegoriaPreferencial()");                
        AmbCidSubcategorias ambCidSubcategorias = listAmbCidSubcategorias.get(0);
////System.err.println("2: AmbCidHipoteseDiagnosticoAbstract.initSubcategoriaPreferencial()");                
        ambCidPerfisDoencas.setFkIdSubcategorias(ambCidSubcategorias);
////System.err.println("3: AmbCidHipoteseDiagnosticoAbstract.initSubcategoriaPreferencial()");                
        return true;
    }

    public boolean initCategoriaPreferencial()
    {
        List<AmbCidCategorias> listAmbCidCategorias = AmbCidCategoriasBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getPkIdAgrupamentos());
        if (listAmbCidCategorias == null || listAmbCidCategorias.isEmpty())
        {
            return false;
        }
        for (AmbCidCategorias ambCidCategorias : listAmbCidCategorias)
        {
            ambCidPerfisDoencas.getFkIdSubcategorias().setFkIdCategorias(ambCidCategorias);
            if (initSubcategoriaPreferencial())
            {
                return true;
            }
        }
        return false;
    }

    public boolean initAgrupamentoPreferencial()
    {
        List<AmbCidAgrupamentos> listAmbCidAgrupamentos = AmbCidAgrupamentosBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getFkIdCapitulos().getPkIdCapitulos());
        if (listAmbCidAgrupamentos == null || listAmbCidAgrupamentos.isEmpty())
        {
            return false;
        }
        for (AmbCidAgrupamentos ambCidAgrupamentos : listAmbCidAgrupamentos)
        {
            ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().setFkIdAgrupamentos(ambCidAgrupamentos);
            if (initCategoriaPreferencial())
            {
                return true;
            }
        }
        return false;
    }

    public boolean initCapituloPreferencial()
    {
        List<AmbCidCapitulos> listAmbCidCapitulos = AmbCidCapitulosBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial);
        if (listAmbCidCapitulos == null || listAmbCidCapitulos.isEmpty())
        {
            return false;
        }
        for (AmbCidCapitulos ambCidCapitulos : listAmbCidCapitulos)
        {
            ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().setFkIdCapitulos(ambCidCapitulos);
            if (initAgrupamentoPreferencial())
            {
                return true;
            }
        }
        return false;
    }

    public void initEspecialidadePreferencial()
    {
        //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.initEspecialidadePreferencial()\tidConta: " + this.idConta);
        especialidadePreferencial = obterEspecialidadePreferencial();
        if (especialidadePreferencial != null)
        {
            this.idEspecialidadePreferencial = this.especialidadePreferencial.getPkIdEspecialidade();
            //System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.initEspecialidadePreferencial()\tidConta: " + this.idConta);
            return;
        }

        RhProfissao rhProfissao = this.getRhProfissaoFacade().findByDescricao("Médico");
        this.especialidadePreferencial = new GrlEspecialidade();
        especialidadePreferencial.setFkIdProfissao(rhProfissao);
        //System.err.println("2: AmbCidHipoteseDiagnosticoAbstract.initEspecialidadePreferencial()\tidConta: " + this.idConta);
    }

    public void initDoencasPrioridadesPreferencial()
    {
        this.idDoencasPrioridadesPreferencial = ambCidConfiguracoes.getIdDoencasPrioridades();
        ambCidDoencasPrioridadesPreferencial = this.getAmbCidDoencasPrioridadesFacade().find(idDoencasPrioridadesPreferencial);
//System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.initDoencasPrioridadesPreferencial()\tidDoencasPrioridadesPreferencial: " + this.idDoencasPrioridadesPreferencial);
    }

    public void initAmbCidPerfisDoencas()
    {
        AmbCidPerfisDoencasBean ambCidPerfisDoencasBean = AmbCidPerfisDoencasBean.getInstanciaBean();
        this.ambCidPerfisDoencas = ambCidPerfisDoencasBean.getInstancia();
        ambCidPerfisDoencas.setFkIdPerfil(ambCidPerfisPreferencial);
    }

    public void initAmbCidTreePerfisPreferencial()
    {
        //88888888888888888888888 
        ambCidTreePerfisPreferencial = new AmbCidTreePerfisAbstract(this.getAmbCidPerfisFacade())
        {
            @Override
            public void onNodeSelect(NodeSelectEvent event)
            {
                super.onNodeSelect(event);

                perfilPreferencial = selectedNode.getData().toString();
//System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.onNodeSelect()\tperfilPreferencial: " + perfilPreferencial);
                AmbCidPerfisDoencasBean ambCidPerfisDoencasBean = AmbCidPerfisDoencasBean.getInstanciaBean();
                if (! ambCidPerfisDoencasBean.temDoencasRegistadas(perfilPreferencial))
                {
          util.Mensagem.warnMsg("O perfil '" + perfilPreferencial + "' nao tem doenças cadastradas.\nPor favor, cadastre primeiro pelo menos uma doença nesse perfil !!!");
                    selectedNode.setSelected(false);
                    this.prevSelectedNode.setSelected(true);
                    selectedNode = prevSelectedNode;
                    perfilPreferencial = this.selectedNode.getData().toString();
                    return;
                }
//System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.onNodeSelect()\tperfilPreferencial: " + perfilPreferencial);                
                gravarPerfilPreferencial();
//System.err.println("2: AmbCidHipoteseDiagnosticoAbstract.onNodeSelect()\tperfilPreferencial: " + perfilPreferencial);                
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
                //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.onNodeExpand()\tnode: " + node);
            }

            @Override
            public void onNodeCollapse(NodeCollapseEvent event)
            {
                String node = event.getTreeNode().getData().toString();
                //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.onNodeCollapse()\tnode: " + node);
            }

            @Override
            public void initSelectedNode()
            {
                TreeNode treeNodePerfilPreferencial = util.amb.TreeNodeUtilities.getTreeNode(root, perfilPreferencial);
                if (treeNodePerfilPreferencial == null)
                {
                    return;
                }
                util.amb.TreeNodeUtilities.setSelected(root, treeNodePerfilPreferencial);
                selectedNode = treeNodePerfilPreferencial;
                //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.inicializarTree()");
            }

        };

    }

    public void initAmbCidPerfis()
    {
        this.perfilPreferencial = this.ambCidConfiguracoes.getIdNomePerfil();
        if (perfilPreferencial == null)
        {
            ambCidPerfisPreferencial = null;
            perfilPreferencial = util.amb.Defs.CID_10;
        }
        else
        {
            this.ambCidPerfisPreferencial = this.getAmbCidPerfisFacade().find(perfilPreferencial);
        }
    }

    public SegConta initSegConta()
    {
        segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        this.idConta = segConta.getPkIdConta();
        ambCidConfiguracoes = this.getAmbCidConfiguracoesFacade().loadAmbCidConfiguracoes(idConta);
        return segConta;
    }

    public void installAmbCidCategorias(String pkIdCategorias)
    {
////System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.installAmbCidCategorias()\tpkIdCategorias: " + pkIdCategorias);
        AmbCidCategorias ambCidCategorias = this.getAmbCidCategoriasFacade().find(pkIdCategorias);
        this.ambCidPerfisDoencas.getFkIdSubcategorias().setFkIdCategorias(ambCidCategorias);
////System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.installAmbCidCategorias()\tpkIdCategorias: "
        //           + ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
        resetAmbiCidSubcategorias();
    }

    public void installAmbCidAgrupamentos(String pkIdAgrupamentos)
    {
        AmbCidAgrupamentos ambCidAgrupamentos = this.getAmbCidAgrupamentosFacade().find(pkIdAgrupamentos);
        this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().setFkIdAgrupamentos(ambCidAgrupamentos);
        resetAmbiCidCategorias();
    }

    public void resetAmbiCidSubcategorias()
    {
        //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.resetAmbiCidSubcategorias()");
        this.doencasPickList.inicListSource();
        List<AmbCidSubcategorias> list = AmbCidSubcategoriasBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
        //System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.resetAmbiCidSubcategorias()\tlist.size: " + (list == null ? "null" : list.size()));
    }

    public void resetAmbiCidCategorias()
    {
        List<AmbCidCategorias> list = AmbCidCategoriasBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getPkIdAgrupamentos());

        String pkIdCategorias = list.get(0).getPkIdCategorias();
        installAmbCidCategorias(pkIdCategorias);
    }

    public void resetAmbiCidAgrupamentos()
    {
        List<AmbCidAgrupamentos> list = AmbCidAgrupamentosBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getFkIdCapitulos().getPkIdCapitulos());

        String pkIdAgrupamentos = list.get(0).getPkIdAgrupamentos();
        installAmbCidAgrupamentos(pkIdAgrupamentos);
    }

    public void installAmbCidCapitulos(String pkIdCapitulos)
    {
        AmbCidCapitulos ambCidCapitulos = this.getAmbCidCapitulosFacade().find(pkIdCapitulos);
        this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().setFkIdCapitulos(ambCidCapitulos);
        resetAmbiCidAgrupamentos();
    }

    public void resettingCapitulos()
    {
        List<AmbCidCapitulos> list = AmbCidCapitulosBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial);
        String pkIdCapitulos = list.get(0).getPkIdCapitulos();
        installAmbCidCapitulos(pkIdCapitulos);
    }

    public void resettingPrioridadeDoencas()
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
        //System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.resettingPrioridadeDoencas()\tidDoencasPrioridadesPreferencial: " + idDoencasPrioridadesPreferencial);
        this.gravarDoencasPrioridades();
    }

    public void gravarDoencasPrioridades()
    {
        ambCidConfiguracoes = this.getAmbCidConfiguracoesFacade().loadAmbCidConfiguracoes(this.idConta);
        ambCidConfiguracoes.setIdDoencasPrioridades(idDoencasPrioridadesPreferencial);
        this.getAmbCidConfiguracoesFacade().edit(ambCidConfiguracoes);
        ambCidDoencasPrioridadesPreferencial = this.getAmbCidDoencasPrioridadesFacade().find(idDoencasPrioridadesPreferencial);
        resettingCapitulos();
    }

    public void gravarPerfilPreferencial()
    {
        ambCidConfiguracoes = this.getAmbCidConfiguracoesFacade().loadAmbCidConfiguracoes(this.idConta);
        ambCidConfiguracoes.setIdNomePerfil((perfilPreferencial == null || perfilPreferencial.equals(util.amb.Defs.CID_10)) ? null : perfilPreferencial);
        this.getAmbCidConfiguracoesFacade().edit(ambCidConfiguracoes);
        resettingPrioridadeDoencas();
    }

    // metodos get e set
    public abstract AmbCidSubcategoriasFacade getAmbCidSubcategoriasFacade();

    public abstract AmbCidCategoriasFacade getAmbCidCategoriasFacade();

    public abstract AmbCidAgrupamentosFacade getAmbCidAgrupamentosFacade();

    public abstract AmbCidCapitulosFacade getAmbCidCapitulosFacade();

    public abstract RhProfissaoFacade getRhProfissaoFacade();

    public abstract GrlEspecialidadeFacade getGrlEspecialidadeFacade();

    public abstract AmbCidPerfisFacade getAmbCidPerfisFacade();

    public abstract AmbCidConfiguracoesFacade getAmbCidConfiguracoesFacade();

    public abstract AmbCidDoencasPrioridadesFacade getAmbCidDoencasPrioridadesFacade();

    // fim dos metodos get abstractos
    public AmbCidPerfisDoencas getAmbCidPerfisDoencas()
    {
        return ambCidPerfisDoencas;
    }

    public void setAmbCidPerfisDoencas(AmbCidPerfisDoencas ambCidPerfisDoencas)
    {
        this.ambCidPerfisDoencas = ambCidPerfisDoencas;
    }

    public AmbCidPerfis getAmbCidPerfisPreferencial()
    {
        return ambCidPerfisPreferencial;
    }

    public void setAmbCidPerfisPreferencial(AmbCidPerfis ambCidPerfisPreferencial)
    {
        this.ambCidPerfisPreferencial = ambCidPerfisPreferencial;
    }

    public String getPerfilPreferencial()
    {
        return perfilPreferencial;
    }

    public void setPerfilPreferencial(String perfilPreferencial)
    {
        this.perfilPreferencial = perfilPreferencial;
    }

    public int getIdConta()
    {
        return idConta;
    }

    public void setIdConta(int idConta)
    {
        this.idConta = idConta;
    }

    public int getIdDoencasPrioridadesPreferencial()
    {
        return idDoencasPrioridadesPreferencial;
    }

    public void setIdDoencasPrioridadesPreferencial(int idDoencasPrioridadesPreferencial)
    {
        this.idDoencasPrioridadesPreferencial = idDoencasPrioridadesPreferencial;
    }

    public SegConta getSegConta()
    {
        return segConta;
    }

    public void setSegConta(SegConta segConta)
    {
        this.segConta = segConta;
    }

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

    public List<String> getSubcategoriasDoencasSeleccionadas()
    {
        return subcategoriasDoencasSeleccionadas;
    }

    public void setSubcategoriasDoencasSeleccionadas(List<String> subcategoriasDoencasSeleccionadas)
    {
        this.subcategoriasDoencasSeleccionadas = subcategoriasDoencasSeleccionadas;
    }

    public boolean isCapitulosDoencasSOMdisabled()
    {
        AmbCidCapitulosBean ambCidCapitulosBean = AmbCidCapitulosBean.getInstanciaBean();
        List<AmbCidCapitulos> list = ambCidCapitulosBean.findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial);
        capitulosDoencasSOMdisabled = (list == null || list.isEmpty());
        /*
         ////System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.isCapitulosDoencasSOMdisabled()\tcapitulosDoencasSOMdisabled: " + capitulosDoencasSOMdisabled);         
         ////System.err.println("1: AmbCidHipoteseDiagnosticoAbstract.isCapitulosDoencasSOMdisabled()\tperfilPreferencial: " + perfilPreferencial);         
         ////System.err.println("2: AmbCidHipoteseDiagnosticoAbstract.isCapitulosDoencasSOMdisabled()\tidDoencasPrioridadesPreferencial: " + idDoencasPrioridadesPreferencial);         
         ////System.err.println("3: AmbCidHipoteseDiagnosticoAbstract.isCapitulosDoencasSOMdisabled()\tlist: " + (list == null ? "null" : "not null"));

         if (list != null)
         //System.err.println("4: AmbCidHipoteseDiagnosticoAbstract.isCapitulosDoencasSOMdisabled()\tlist.size: " + list.size());
         */
        return capitulosDoencasSOMdisabled;

    }

    public void setCapitulosDoencasSOMdisabled(boolean capitulosDoencasSOMdisabled)
    {
        this.capitulosDoencasSOMdisabled = capitulosDoencasSOMdisabled;
    }

    public boolean isPerfilPreferencialSOMrendered()
    {
        List<String> listAmbCidPerfisBean = AmbCidPerfisBean.getInstanciaBean().findAllOrderByPkIdNome(segConta, especialidadePreferencial.getPkIdEspecialidade());
        if (listAmbCidPerfisBean == null)
        {
            return false;
        }
        perfilPreferencialSOMrendered = (listAmbCidPerfisBean.size() > 1);
        return perfilPreferencialSOMrendered;
    }

    public boolean isPrioridadeDoencaSOMrendered()
    {
        List<AmbCidDoencasPrioridades> listAmbCidDoencasPrioridades = AmbCidDoencasPrioridadesBean.getInstanciaBean().findAllFromPerfilPreferencial(perfilPreferencial);
        prioridadeDoencaSOMrendered = (listAmbCidDoencasPrioridades.size() > 1);
////System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.isPrioridadeDoencaSOMrendered()\tidDoencasPrioridadesPreferencial: " + this.idDoencasPrioridadesPreferencial);
        return prioridadeDoencaSOMrendered;
    }

    public AmbCidDoencasPrioridades getAmbCidDoencasPrioridadesPreferencial()
    {
        return ambCidDoencasPrioridadesPreferencial;
    }

    public boolean isCapitulosDoencasSOMrendered()
    {
        List<AmbCidCapitulos> listAmbCidCapitulos = AmbCidCapitulosBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial);
        capitulosDoencasSOMrendered = (listAmbCidCapitulos.size() > 1);
        return capitulosDoencasSOMrendered;
    }

    public boolean isAgrupamentosDoencasSOMrendered()
    {
        List<AmbCidAgrupamentos> listAmbCidAgrupamentos = AmbCidAgrupamentosBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getFkIdCapitulos().getPkIdCapitulos());
        agrupamentosDoencasSOMrendered = (listAmbCidAgrupamentos.size() > 1);
        return agrupamentosDoencasSOMrendered;
    }

    public boolean isCategoriasDoencasSOMrendered()
    {
        List<AmbCidCategorias> listAmbCidCategorias = AmbCidCategoriasBean.getInstanciaBean().findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getPkIdAgrupamentos());
        categoriasDoencasSOMrendered = (listAmbCidCategorias.size() > 1);
        return categoriasDoencasSOMrendered;
    }

    public PickListAbstract getDoencasPickList()
    {
        return doencasPickList;
    }

    public void setDoencasPickList(PickListAbstract doencasPickList)
    {
        this.doencasPickList = doencasPickList;
    }

    public AmbCidTreePerfisAbstract getAmbCidTreePerfisPreferencial()
    {
        return ambCidTreePerfisPreferencial;
    }

    public void setAmbCidTreePerfisPreferencial(AmbCidTreePerfisAbstract ambCidTreePerfisPreferencial)
    {
        this.ambCidTreePerfisPreferencial = ambCidTreePerfisPreferencial;
    }

}
