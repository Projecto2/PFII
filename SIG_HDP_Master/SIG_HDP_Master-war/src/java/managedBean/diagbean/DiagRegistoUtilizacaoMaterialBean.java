/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagControloSemanalMaterial;
import entidade.DiagControloSemanalMaterialHasMaterial;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.farmbean.armazenamento.FarmVisualizarItensNoLocalBean;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.DiagControloSemanalMaterialFacade;
import sessao.DiagControloSemanalMaterialHasMaterialFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagRegistoUtilizacaoMaterialBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagControloSemanalMaterialFacade diagControloSemanalMaterialFacade;
    @EJB
    private DiagControloSemanalMaterialHasMaterialFacade diagControloSemanalMaterialHasMaterialFacade;
    @EJB
    private FarmLocalArmazenamentoFacade farmLocalArmazenamentoFacade;
    @EJB
    private FarmLoteProdutoHasLocalArmazenamentoFacade farmLoteProdutoHasLocalArmazenamentoFacade;

    private List<DiagControloSemanalMaterialHasMaterial> listaProdutosSelecionados;

    private DiagControloSemanalMaterial diagControloSemanalMaterial, diagControloSemanalMaterialPesquisar, diagControloSemanalMaterialVisualizar;

    private List<FarmProduto> listaProdutosPesquisados;

    private FarmProduto produtoPesquisa, produtoAdicionarNaLista;

    private SegLoginBean segLoginBean = new SegLoginBean();

    private FarmVisualizarItensNoLocalBean farmVisualizarItensNoLocalBean = FarmVisualizarItensNoLocalBean.getInstanciaBean();

    private List<DiagControloSemanalMaterial> listaPesquisaHistorico;
    
    private List<DiagControloSemanalMaterialHasMaterial> listaProdutosVisualizar;

    private int numeroRegistos = 10;

    private Date dataInicioCadastro, dataFimCadastro;

    public static DiagRegistoUtilizacaoMaterialBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagRegistoUtilizacaoMaterialBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagRegistoUtilizacaoMaterialBean");
    }

    public static DiagControloSemanalMaterial getInstancia()
    {
        DiagControloSemanalMaterial diagControloSemanalMaterial = new DiagControloSemanalMaterial();
        diagControloSemanalMaterial.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return diagControloSemanalMaterial;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }

    public Date getDataInicioCadastro()
    {
        return dataInicioCadastro;
    }

    public void setDataInicioCadastro(Date dataInicioCadastro)
    {
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public Date getDataFimCadastro()
    {
        return dataFimCadastro;
    }

    public void setDataFimCadastro(Date dataFimCadastro)
    {
        this.dataFimCadastro = dataFimCadastro;
    }

    public DiagControloSemanalMaterial getDiagControloSemanalMaterialPesquisar()
    {
        if (diagControloSemanalMaterialPesquisar == null)
        {
            diagControloSemanalMaterialPesquisar = getInstancia();
        }
        return diagControloSemanalMaterialPesquisar;
    }

    public void setDiagControloSemanalMaterialPesquisar(DiagControloSemanalMaterial diagControloSemanalMaterialPesquisar)
    {
        this.diagControloSemanalMaterialPesquisar = diagControloSemanalMaterialPesquisar;
    }

    public DiagControloSemanalMaterial getDiagControloSemanalMaterialVisualizar()
    {
        if (diagControloSemanalMaterialVisualizar == null)
        {
            diagControloSemanalMaterialVisualizar = getInstancia();
        }
        return diagControloSemanalMaterialVisualizar;
    }

    public void setDiagControloSemanalMaterialVisualizar(DiagControloSemanalMaterial diagControloSemanalMaterialVisualizar)
    {
        this.diagControloSemanalMaterialVisualizar = diagControloSemanalMaterialVisualizar;
        listaProdutosVisualizar = diagControloSemanalMaterialHasMaterialFacade.findProdutosByRegisto(diagControloSemanalMaterialVisualizar);
    }

    public List<DiagControloSemanalMaterialHasMaterial> getListaProdutosVisualizar()
    {
        if (listaProdutosVisualizar == null)
        {
            listaProdutosVisualizar = new ArrayList<>();
        }
        return listaProdutosVisualizar;
    }

    public void setListaProdutosVisualizar(List<DiagControloSemanalMaterialHasMaterial> listaProdutosVisualizar)
    {
        this.listaProdutosVisualizar = listaProdutosVisualizar;
    }

    public List<DiagControloSemanalMaterial> getListaPesquisaHistorico()
    {
        if (listaPesquisaHistorico == null)
        {
            listaPesquisaHistorico = new ArrayList<>();
        }
        return listaPesquisaHistorico;
    }

    public void setListaPesquisaHistorico(List<DiagControloSemanalMaterial> listaPesquisaHistorico)
    {
        this.listaPesquisaHistorico = listaPesquisaHistorico;
    }

    public DiagControloSemanalMaterial getDiagControloSemanalMaterial()
    {
        if (diagControloSemanalMaterial == null)
        {
            diagControloSemanalMaterial = getInstancia();
        }
        return diagControloSemanalMaterial;
    }

    public void setDiagControloSemanalMaterial(DiagControloSemanalMaterial diagControloSemanalMaterial)
    {
        this.diagControloSemanalMaterial = diagControloSemanalMaterial;
    }

    public FarmProduto getProdutoPesquisa()
    {
        if (produtoPesquisa == null)
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return produtoPesquisa;
    }

    public void setProdutoPesquisa(FarmProduto produtoPesquisa)
    {
        this.produtoPesquisa = produtoPesquisa;
    } 
    
    public FarmProduto getProdutoAdicionarNaLista()
    {
        if (produtoAdicionarNaLista == null)
        {
            produtoAdicionarNaLista = FarmProdutoNovoBean.getInstancia();
        }
        return produtoAdicionarNaLista;
    }

    public void setProdutoAdicionarNaLista(FarmProduto produtoAdicionarNaLista)
    {
        this.produtoAdicionarNaLista = produtoAdicionarNaLista;
    }

    public void setListaProdutosPesquisados(List<FarmProduto> listaProdutosPesquisados)
    {
        this.listaProdutosPesquisados = listaProdutosPesquisados;
    }

    public List<FarmProduto> getListaProdutosPesquisados()
    {
        return listaProdutosPesquisados;
    }

    public List<DiagControloSemanalMaterialHasMaterial> getListaProdutosSelecionados()
    {
        if (listaProdutosSelecionados == null)
        {
            listaProdutosSelecionados = new ArrayList<>();
        }
        return listaProdutosSelecionados;
    }

    public FarmLocalArmazenamento getLocalArmazenamentoByName(String nome)
    {
        for (FarmLocalArmazenamento aux : farmLocalArmazenamentoFacade.findAll())
        {
            if (aux.getDescricao().equals(nome))
            {
                return aux;
            }
        }
        return null;
    }

    public void pesquisarProdutos()
    {

        farmVisualizarItensNoLocalBean.definirLocalOrigemPedido("Diagnósticos");
        farmVisualizarItensNoLocalBean.setLocal(getLocalArmazenamentoByName("Diagnósticos"));
        farmVisualizarItensNoLocalBean.setDescricao(getProdutoPesquisa().getDescricao());
        farmVisualizarItensNoLocalBean.pesquisarItensNoLocal();

        setListaProdutosPesquisados(farmVisualizarItensNoLocalBean.getListaProdutos());

        if (getListaProdutosPesquisados().isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaProdutosPesquisados().size() + " registo(s) retornado(s).");
        }

        eliminarDuplicados();
    }

    public void eliminarDuplicados()
    {
        for (DiagControloSemanalMaterialHasMaterial controlo_has_material : getListaProdutosSelecionados())
        {
            if (getListaProdutosPesquisados().contains(controlo_has_material.getFkIdMaterial()))
            {
                getListaProdutosPesquisados().remove(controlo_has_material.getFkIdMaterial());
            }
        }
    }

    public void adicionarProduto()
    {
        DiagControloSemanalMaterialHasMaterial itemAux = new DiagControloSemanalMaterialHasMaterial();
        itemAux.setFkIdControloSemanalMaterial(diagControloSemanalMaterial);
        itemAux.setFkIdMaterial(getProdutoAdicionarNaLista());
        itemAux.setQuantidade(1);
        listaProdutosSelecionados.add(itemAux);

        pesquisarProdutos();
    }

    public void removerComponente()
    {
        DiagControloSemanalMaterialHasMaterial itemAux = new DiagControloSemanalMaterialHasMaterial();
        itemAux.setFkIdControloSemanalMaterial(diagControloSemanalMaterial);
        itemAux.setFkIdMaterial(getProdutoAdicionarNaLista());
        itemAux.setQuantidade(1);
        listaProdutosSelecionados.remove(itemAux);

        pesquisarProdutos();
    }

    public String create()
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagControloSemanalMaterial = getInstancia();

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");

            diagControloSemanalMaterial.setFkIdFuncionario(sessaoActual.getFkIdFuncionario());

            diagControloSemanalMaterial.setData(new Date(data.getTimeInMillis()));

            diagControloSemanalMaterialFacade.create(diagControloSemanalMaterial);

            userTransaction.commit();

            adicionarProdutosDoRegisto(diagControloSemanalMaterial);

            Mensagem.sucessoMsg("Registo de utilização efectuado com sucesso!");

            diagControloSemanalMaterial = new DiagControloSemanalMaterial();

            listaProdutosSelecionados = new ArrayList<>();
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                System.out.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println(ex.toString());
            }
        }

        return "/faces/diagVisao/homeDiag.xhtml?faces-redirect=true";
    }

    public String cancelarRegisto()
    {
        diagControloSemanalMaterial = getInstancia();
        produtoPesquisa = FarmProdutoNovoBean.getInstancia();

        listaProdutosSelecionados = new ArrayList<>();

        return "/faces/diagVisao/homeDiag.xhtml?faces-redirect=true";
    }

    private void adicionarProdutosDoRegisto(DiagControloSemanalMaterial controloSemanalMaterialAux)
    {
        DiagControloSemanalMaterialHasMaterial controloSemanalMaterialHasMaterial;

        for (DiagControloSemanalMaterialHasMaterial produtoAux : listaProdutosSelecionados)
        {
            controloSemanalMaterialHasMaterial = new DiagControloSemanalMaterialHasMaterial();

            controloSemanalMaterialHasMaterial.setFkIdMaterial(produtoAux.getFkIdMaterial());
            controloSemanalMaterialHasMaterial.setFkIdControloSemanalMaterial(controloSemanalMaterialAux);

            controloSemanalMaterialHasMaterial.setQuantidade(produtoAux.getQuantidade());

            diagControloSemanalMaterialHasMaterialFacade.create(controloSemanalMaterialHasMaterial);
            //Reduzir as quantidades utilizadas

            FarmLoteProdutoHasLocalArmazenamento farmLoteProdutoHasLocalArmazenamento = farmLoteProdutoHasLocalArmazenamentoFacade.findProdutoNoLocal(farmVisualizarItensNoLocalBean.getLocal(), produtoAux.getFkIdMaterial()).get(0);
            farmLoteProdutoHasLocalArmazenamento.setQuantidadeStock(farmLoteProdutoHasLocalArmazenamento.getQuantidadeStock() - produtoAux.getQuantidade());

            farmLoteProdutoHasLocalArmazenamentoFacade.edit(farmLoteProdutoHasLocalArmazenamento);
        }
    }

    public void pesquisarHistorico()
    {
        listaPesquisaHistorico = diagControloSemanalMaterialFacade.findPesquisaHistorico(diagControloSemanalMaterialPesquisar, dataInicioCadastro, dataFimCadastro, numeroRegistos);

        if (listaPesquisaHistorico.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaPesquisaHistorico.size() + " registo(s) retornado(s).");
        }
    }

    public String limparPesquisa()
    {
        listaPesquisaHistorico = new ArrayList<>();
        diagControloSemanalMaterialPesquisar = diagControloSemanalMaterialVisualizar = null;
        
        dataInicioCadastro = dataFimCadastro = null;

        return "historicoUtilizacaoMaterial.xhtml?faces-redirect=true";
    }
}
