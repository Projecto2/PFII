/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.SupiCriterioAvaliacao;
import entidade.SupiPontuacao;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.SupiCriterioAvaliacaoFacade;
import sessao.SupiPontuacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiCriterioBean
{

    @EJB
    private SupiPontuacaoFacade supiPontuacaoFacade;
    @EJB
    private SupiCriterioAvaliacaoFacade supiCriterioAvaliacaoFacade;

    SupiCriterioAvaliacao criterio1, criterioPesquisa, criterioVisualizar;
    private List<SupiCriterioAvaliacao> listaCriterio;
    SupiPontuacao pontuacao1;
    Mensagem mensagem;
    private boolean pesquisar = false;

    /**
     * Creates a new instance of CriterioBean
     */
    public SupiCriterioBean()
    {
    }
    
    public static SupiCriterioBean getInstanciaBean()
    {
        return (SupiCriterioBean) GeradorCodigo.getInstanciaBean("supiCriterioBean");
    }

    public static SupiCriterioAvaliacao getInstancia()
    {
        SupiCriterioAvaliacao criterio = new SupiCriterioAvaliacao();

//        criterio.setFkIdPontuacao(new SupiPontuacao());

        return criterio;

    }

    

    public List<SupiCriterioAvaliacao> getListaCriterio()
    {
        return listaCriterio;
    }

    public void setListaCriterio(List<SupiCriterioAvaliacao> listaCriterio)
    {
        this.listaCriterio = listaCriterio;
    }

    public SupiCriterioAvaliacao getCriterioPesquisa()
    {
        if (criterioPesquisa == null)
        {
            criterioPesquisa = getInstancia();
        }
        return criterioPesquisa;
    }

    public void setCriterioPesquisa(SupiCriterioAvaliacao criterioPesquisa)
    {
        this.criterioPesquisa = criterioPesquisa;
    }

    public SupiCriterioAvaliacao getCriterioVisualizar()
    {
        return criterioVisualizar;
    }

    public void setCriterioVisualizar(SupiCriterioAvaliacao criterioVisualizar)
    {
        this.criterioVisualizar = criterioVisualizar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public void pesquisarCriterio()
    {
        listaCriterio = supiCriterioAvaliacaoFacade.findCriterio(criterioPesquisa);

        if (listaCriterio.isEmpty())
        {
            Mensagem.erroMsg("Nenhum Critério encontrado para esta pesquisa");
        }
    }

    public void limparPesquisas()
    {
        setCriterioPesquisa(getInstancia());
        listaCriterio = new ArrayList<>();
    }

    public void eliminar()
    {
        try
        {
            supiCriterioAvaliacaoFacade.remove(criterioPesquisa);
            criterioPesquisa = getInstancia();
            pesquisarCriterio();
            Mensagem.sucessoMsg("O Critério foi eliminado com sucesso!");
        } catch (Exception ex)
        {
            Mensagem.warnMsg("O Critério não pode ser eliminado, porque está a ser utilizado.");
        }

    }

    public SupiCriterioAvaliacao getCriterio1()
    {
        if (criterio1 == null)
        {
            criterio1 = new SupiCriterioAvaliacao();
        }
        return criterio1;
    }

    public void setCriterio1(SupiCriterioAvaliacao criterio1)
    {
        this.criterio1 = criterio1;
    }

    public SupiPontuacao getPontuacao1()
    {
        if(pontuacao1 == null)
            
            pontuacao1 = new SupiPontuacao();
        
        return pontuacao1;
    }

    public void setPontuacao1(SupiPontuacao pontuacao1)
    {
        this.pontuacao1 = pontuacao1;
    }

    public void visualizarDados(Integer idPontuacao)
    {
        criterio1 = supiCriterioAvaliacaoFacade.find(idPontuacao);
    }

    public String prepararEditar(Integer idPontuacao)
    {
        criterio1 = supiCriterioAvaliacaoFacade.find(idPontuacao);
        return "editarCriterio";
    }

    public void limpar()
    {
        criterio1 = new SupiCriterioAvaliacao();
        pontuacao1 = new SupiPontuacao();

    }

    public String limparPesquisa()
    {
        return "criterioListar.xhtml?faces-redirect=true";
    }

    public String salvar()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (criterio1 != null)
        {
            SupiPontuacao pont = supiPontuacaoFacade.find(pontuacao1.getPkIdPontuacao());
           // criterio1.setFkIdPontuacao(pont);
            if (verificarCriterio(criterio1.getDescricaoCriterio()))
            {
                //mensagem.addMessage("Critério Já Existente!", null);
                facesContext.addMessage(null, new FacesMessage("Critério Já Existente!"));
                limpar();
                return "criterio";
            } else
            {
                supiCriterioAvaliacaoFacade.create(criterio1);
                limpar();
                mensagem.addMessage("Dados Registados com sucesso", null);
            }

        }
        return "criterioListar";
    }

    public void editar()
    {

        try
        {

            if (criterio1.getSupiAvaliaCriteriosList() != null)
            {
               // criterio1.setFkIdPontuacao(supiPontuacaoFacade.find(pontuacao1.getPkIdPontuacao()));
                supiCriterioAvaliacaoFacade.edit(criterio1);
                mensagem.addMessage("Dados Actualizados com sucesso", null);

            }
        } catch (Exception e)
        {
            e.getStackTrace();

        }

    }

    /**
     * ***********************
     * ELIMINAR DADOS *** ***********************
     */
    public void eliminar(int idCriterio)
    {

        FacesContext fc = FacesContext.getCurrentInstance();

        try
        {

            criterio1 = supiCriterioAvaliacaoFacade.find(idCriterio);

            if (criterio1.getSupiAvaliaCriteriosList() != null)
            {

                supiCriterioAvaliacaoFacade.remove(criterio1);

                mensagem.addMessage("Dados Removidos com sucesso!", null);
                //fc.addMessage(null, new FacesMessage("Dados Removidos com sucesso!"));

                criterio1 = new SupiCriterioAvaliacao();

            } else
            {
                //fc.addMessage(null, new FacesMessage("Erro ao Eliminar " + ""));
                mensagem.addMessage("Erro ao Eliminar ", null);

            }

        } catch (Exception ex)
        {

            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
    }

    public boolean verificarCriterio(String criterio1)
    {
        List<SupiCriterioAvaliacao> listaEstagiario = supiCriterioAvaliacaoFacade.findAll();

        for (SupiCriterioAvaliacao est : listaEstagiario)
        {
            if (est.getDescricaoCriterio().equals(criterio1))
            {
                return true;
            }
        }

        return false;

    }

    public List<SupiCriterioAvaliacao> listarTodas()
    {
        return supiCriterioAvaliacaoFacade.findAll();
    }

}
