/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsServico;
import entidade.AdmsCategoriaServico;
import entidade.FinPrecoCategoriaServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.AdmsCategoriaServicoFacade;
import sessao.FinTipoPrecoCategoriaServicoFacade;
import sessao.FinPrecoCategoriaServicoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsCategoriaServicoBean implements Serializable
{
    @EJB
    private FinTipoPrecoCategoriaServicoFacade finTipoPrecoCategoriaServicoFacade;
    @EJB
    private AdmsCategoriaServicoFacade finPrecoFacade;
    @EJB
    private FinPrecoCategoriaServicoFacade finPrecoCategoriaServicoFacade;
    @EJB
    private AdmsCategoriaServicoFacade precoFacade;
    
    @Resource
    private UserTransaction userTransaction;

    /**
     *
     * Entidades
     */
    private AdmsCategoriaServico preco,

    /**
     * Entidades
     */
    precoPesquisa,

    /**
     * Entidades
     */
    precoValores,

    /**
     * Entidades
     */
    precoEditar;
    
//    private boolean pesquisar = false;
    
    private AdmsServico servico = new AdmsServico();
    
    private List<AdmsCategoriaServico> precos;
    
    private FinPrecoCategoriaServico valorPreco, valorPrecoNovo, valorPrecoNovo1Vez, valorPrecoNovoRetorno;
    
    private List<FinPrecoCategoriaServico> valoresDoPreco;

    public AdmsCategoriaServicoBean ()
    {
    }

    public AdmsCategoriaServico getPreco ()
    {
        if (this.preco == null)
            this.preco = getInstancia();
        return preco;
    }

    public void setPreco (AdmsCategoriaServico preco)
    {
        this.preco = preco;
    }
    
    public AdmsCategoriaServico getPrecoPesquisa()
    {
        if (this.precoPesquisa == null)
            this.precoPesquisa = getInstancia();
        return precoPesquisa;
    }

    public void setPrecoPesquisa(AdmsCategoriaServico precoPesquisa)
    {
        this.precoPesquisa = precoPesquisa;
    }

    public AdmsCategoriaServico getPrecoEditar()
    {
        if (this.precoEditar == null)
            this.precoEditar = getInstancia();
        return precoEditar;
    }

    public void setPrecoEditar(AdmsCategoriaServico precoEditar)
    {
        this.precoEditar = precoEditar;
    }
    

    public FinPrecoCategoriaServico getValorPreco()
    {
        return valorPreco;
    }

    public void setValorPreco(FinPrecoCategoriaServico valorPreco)
    {
        this.valorPreco = valorPreco;
    }

    public AdmsCategoriaServico getPrecoValores()
    {
        return precoValores;
    }

    public void setPrecoValores(AdmsCategoriaServico precoValores, String tipo)
    {
        this.precoValores = precoValores;
        carregarPrecoValores(tipo);
    }

//    public AdmsCategoriaServico getPrecoDosValores()
//    {
//        return precoDosValores;
//    }
//
//    public void setPrecoDosValores(AdmsCategoriaServico precoDosValores)
//    {
//        this.precoDosValores = precoDosValores;
//    }
    
    
    public AdmsCategoriaServico getInstancia()
    {
        AdmsCategoriaServico precoInstancia = new AdmsCategoriaServico();
        precoInstancia.setFkIdServico(servico);
        valorPreco = new FinPrecoCategoriaServico();
        return precoInstancia;
    }

    public AdmsServico getServico()
    {
        return servico;
    }

    public void setServico(AdmsServico servico)
    {
        this.servico = servico;
    }
    
    public void definirServico(AdmsServico servico)
    {
        this.servico = servico;
        limpar();
//        pesquisar = false;
    }

    public FinPrecoCategoriaServico getValorPrecoNovo()
    {
        if(valorPrecoNovo == null)
            valorPrecoNovo = new FinPrecoCategoriaServico();
        return valorPrecoNovo;
    }

    public void setValorPrecoNovo(FinPrecoCategoriaServico valorPrecoNovo)
    {
        this.valorPrecoNovo = valorPrecoNovo;
    }

    public FinPrecoCategoriaServico getValorPrecoNovo1Vez()
    {
        if(valorPrecoNovo1Vez == null)
            valorPrecoNovo1Vez = new FinPrecoCategoriaServico();
        return valorPrecoNovo1Vez;
    }

    public void setValorPrecoNovo1Vez(FinPrecoCategoriaServico valorPrecoNovo1Vez)
    {
        this.valorPrecoNovo1Vez = valorPrecoNovo1Vez;
    }

    public FinPrecoCategoriaServico getValorPrecoNovoRetorno()
    {
        if(valorPrecoNovoRetorno == null)
            valorPrecoNovoRetorno = new FinPrecoCategoriaServico();
        return valorPrecoNovoRetorno;
    }

    public void setValorPrecoNovoRetorno(FinPrecoCategoriaServico valorPrecoNovoRetorno)
    {
        this.valorPrecoNovoRetorno = valorPrecoNovoRetorno;
    }
    
    
//    public boolean isPesquisar()
//    {
//        return pesquisar;
//    }
//
//    public void setPesquisar(boolean pesquisar)
//    {
//        this.pesquisar = pesquisar;
//    }
    
    public void pesquisar()
    {        
        if (precos == null)
        {
            precos = new ArrayList<>();
        }
        
        precos = precoFacade.findCategoriaServico(precoPesquisa);
        
        if(precos.isEmpty())
            Mensagem.warnMsg("Nenhum Preço Encontrado");
    }

    
    public void gravarPrecoPesquisar()
    {
        create();
        pesquisar();
    }
    
    
    public void create()
    {
        try
        {
            userTransaction.begin();
                precoFacade.create(preco);
                
                valorPreco.setFkIdCategoriaServico(preco);
                valorPreco.setEstadoPreco(true);
                valorPreco.setFkIdTipoPrecoCategoriaServico(finTipoPrecoCategoriaServicoFacade.findTipoUnico());
                
                valorPrecoNovo1Vez.setFkIdCategoriaServico(preco);
                valorPrecoNovo1Vez.setEstadoPreco(true);
                valorPrecoNovo1Vez.setFkIdTipoPrecoCategoriaServico(finTipoPrecoCategoriaServicoFacade.findTipoPrimeiraVez());
                
                valorPrecoNovoRetorno.setFkIdCategoriaServico(preco);
                valorPrecoNovoRetorno.setEstadoPreco(true);
                valorPrecoNovoRetorno.setFkIdTipoPrecoCategoriaServico(finTipoPrecoCategoriaServicoFacade.findTipoRetorno());
                
                finPrecoCategoriaServicoFacade.create(valorPreco);
                finPrecoCategoriaServicoFacade.create(valorPrecoNovo1Vez);
                finPrecoCategoriaServicoFacade.create(valorPrecoNovoRetorno);
            userTransaction.commit();
            Mensagem.sucessoMsg("Preco guardado com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        preco = null;
        valorPreco = new FinPrecoCategoriaServico();
        valorPrecoNovo1Vez = new FinPrecoCategoriaServico();
        valorPrecoNovoRetorno = new FinPrecoCategoriaServico();
    }

    
    public void editarPrecoPesquisar()
    {
        edit();
        pesquisar();
    }
    
    public void edit()
    {
        
        try
        {
            userTransaction.begin();
            if (precoEditar.getPkIdCategoriaServico()== null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            precoFacade.edit(precoEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Preco editado com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public List<AdmsCategoriaServico> findAll ()
    {
        return precoFacade.findAll();
    }
    
//    public List<AdmsCategoriaServico> findPreco()
//    {
//        if(pesquisar)
//        {
//            precos = precoFacade.findPreco(precoPesquisa);
//            return precos;
//        }
//        pesquisar = false;
//        return null;
//    }
    
    public void limpar()
    {
        preco = null;
        precoPesquisa = null;
        precos = null;
    }
    
    public String getValorPreco(AdmsCategoriaServico preco)
    {
        List<FinPrecoCategoriaServico> valoresAtivo = finPrecoCategoriaServicoFacade.findValorAtivo(preco);
        if(valoresAtivo.isEmpty())
            return "Nenhum Valor Ativo";
        
        if(/*valoresAtivo.size() > 1*/!preco.getTipoUnico()&& valoresAtivo.size() > 1)
        {
            return ""+valoresAtivo.get(0).getFkIdTipoPrecoCategoriaServico().getDescricao()+": ("+valoresAtivo.get(0).getValor()+" / "+valoresAtivo.get(0).getValorPreco2()+" / "+valoresAtivo.get(0).getValorPrecoDp()+" / "+valoresAtivo.get(0).getValorPrecoDpfs()+")     ------///------      "
                + ""+valoresAtivo.get(1).getFkIdTipoPrecoCategoriaServico().getDescricao()+": ("+valoresAtivo.get(1).getValor()+" / "+valoresAtivo.get(1).getValorPreco2()+" / "+valoresAtivo.get(1).getValorPrecoDp()+" / "+valoresAtivo.get(1).getValorPrecoDpfs()+")";
        }
        else return ""+valoresAtivo.get(0).getFkIdTipoPrecoCategoriaServico().getDescricao()+": "+valoresAtivo.get(0).getValor()+" / "+valoresAtivo.get(0).getValorPreco2()+" / "+valoresAtivo.get(0).getValorPrecoDp()+" / "+valoresAtivo.get(0).getValorPrecoDpfs();
    }
    
    
    public void createValorPreco (String tipo)
    {
        try
        {
            userTransaction.begin();
                if(verificarSeValorJaExiste(valorPrecoNovo))
                {
                    Mensagem.erroMsg("Valor ja existente para este preco, basta mudar o estado!");
                    return;
                }
                valorPrecoNovo.setFkIdCategoriaServico(precoValores);
                definirTipo(tipo);
                removerEstadoAtivoAnterior();
                valorPrecoNovo.setEstadoPreco(true);
                finPrecoCategoriaServicoFacade.create(valorPrecoNovo);
            userTransaction.commit();
            Mensagem.sucessoMsg("Valor preco guardado com sucesso!");
            pesquisar();
        }
        catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        valorPrecoNovo = null;
    }
    
    
    public void definirTipo(String tipo)
    {
        if(tipo.equalsIgnoreCase("unico"))
        {
            valorPrecoNovo.setFkIdTipoPrecoCategoriaServico(finTipoPrecoCategoriaServicoFacade.findTipoUnico());
        }
        
        if(tipo.equalsIgnoreCase("primeira vez"))
        {
            valorPrecoNovo.setFkIdTipoPrecoCategoriaServico(finTipoPrecoCategoriaServicoFacade.findTipoPrimeiraVez());
        }
        
        if(tipo.equalsIgnoreCase("retorno"))
        {
            valorPrecoNovo.setFkIdTipoPrecoCategoriaServico(finTipoPrecoCategoriaServicoFacade.findTipoRetorno());
        }
    }
    
    public boolean verificarSeValorJaExiste(FinPrecoCategoriaServico valorPrecoNovo)
    {
        for(int i = 0; i < valoresDoPreco.size(); i++)
        {
            if(valoresDoPreco.get(i).getValor() == valorPrecoNovo.getValor())
                return true;
        }
        return false;
    }
    
    public void removerEstadoAtivoAnterior()
    {
        for(int i = 0; i < valoresDoPreco.size(); i++)
        {
            if(valoresDoPreco.get(i).getEstadoPreco()== true)
            {
                valoresDoPreco.get(i).setEstadoPreco(false);
                finPrecoCategoriaServicoFacade.edit(valoresDoPreco.get(i));
                return;
            }
        }
    }
    
    public String getEstadoPreco(boolean estado)
    {
        if(estado) return "Ativo";
        return "Inativo";
    }
    
    public void editEstadoValorPreco (FinPrecoCategoriaServico valorPrecoEdit)
    {
        try
        {
            userTransaction.begin();
                if(valorPrecoEdit.getEstadoPreco())
                {
                    valorPrecoEdit.setEstadoPreco(false);
                    valorPrecoEdit.getFkIdCategoriaServico().setEstadoCategoriaServico(false);
                    finPrecoFacade.edit(valorPrecoEdit.getFkIdCategoriaServico());
                }
                else{
                    removerEstadoAtivoAnterior();
                    valorPrecoEdit.setEstadoPreco(true);
                }
                finPrecoCategoriaServicoFacade.edit(valorPrecoEdit);
            userTransaction.commit();
            Mensagem.sucessoMsg("Valor alterado Com Sucesso! "+valorPrecoEdit.getEstadoPreco());
            pesquisar();
        }
        catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    
    public List<FinPrecoCategoriaServico> findValoresPreco()
    {
//        System.out.println("passou ");
        if(precoValores == null) precoValores = new AdmsCategoriaServico();
        return valoresDoPreco;
    }
    
    public void carregarPrecoValores(String tipo){
        if(precoValores == null)
        {
//            System.out.println("e nulo");
            precoValores = new AdmsCategoriaServico();
        }
        if(!(precoValores.getPkIdCategoriaServico() == null))
        {
            valoresDoPreco = finPrecoCategoriaServicoFacade.findValoresPreco(precoValores, tipo);
        }
    }
    
    public String getTipoAserUsado(AdmsCategoriaServico preco)
    {
        if(preco.getTipoUnico())
        {
            return "Único";
        }
        return "Primeira Vez / Retorno";
    }

    public List<AdmsCategoriaServico> getPrecos()
    {
        return precos;
    }

    public void setPrecos(List<AdmsCategoriaServico> precos)
    {
        this.precos = precos;
    }
}