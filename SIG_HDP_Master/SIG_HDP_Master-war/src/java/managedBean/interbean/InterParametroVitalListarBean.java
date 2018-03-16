/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterParametroVital;
import entidade.InterRegistoInternamentoHasParametroVital;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.InterParametroVitalFacade;
import sessao.InterRegistoInternamentoHasParametroVitalFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterParametroVitalListarBean implements Serializable
{

    @EJB
    private InterRegistoInternamentoHasParametroVitalFacade interRegistoInternamentoHasParametroVitalFacade;
    
    @EJB
    private InterParametroVitalFacade interParametroVitalFacade;

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

    private String descricaoPesq;
    
    private List<InterParametroVital> listaParametrosVitais;

    private InterParametroVital interParametroVital;

    /**
     * Creates a new instance of InterParametroVitalListarBean
     */
    public InterParametroVitalListarBean()
    {
    }

    public static InterParametroVitalListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterParametroVitalListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interParametroVitalListarBean");
    }

    public List<InterParametroVital> findById(Long pkIdRegistoInternamento)
    {
        List<InterParametroVital> listaAux = interParametroVitalFacade.listarTodos();
        List<InterParametroVital> lista = new ArrayList();

        for (int i = 0; i < listaAux.size(); i++)
        {
            if (interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegistoInternamento, listaAux.get(i).getPkIdParametroVital()).isEmpty())
            {
                lista.add(listaAux.get(i));
            }
        }
        return lista;
    }
    
    public List<InterParametroVital> findAllParametrosByIdRegisto(Long pkIdRegistoInternamento)
    {
        List<InterParametroVital> listaAux = interParametroVitalFacade.listarTodos();
        List<InterParametroVital> lista = new ArrayList();

        for (int i = 0; i < listaAux.size(); i++)
        {
            if (!interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegistoInternamento, listaAux.get(i).getPkIdParametroVital()).isEmpty())
            {
                lista.add(listaAux.get(i));
            }
        }
        return lista;
    }
    
    public List<InterRegistoInternamentoHasParametroVital> listarTodos()
    {        
       return interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), 0);          
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public List<InterParametroVital> getListaParametrosVitais()
    {
        return listaParametrosVitais;
    }

    public void setListaParametrosVitais(List<InterParametroVital> listaParametrosVitais)
    {
        this.listaParametrosVitais = listaParametrosVitais;
    }

    public InterParametroVital getInterParametroVital()
    {
        return interParametroVital;
    }

    public void setInterParametroVital(InterParametroVital interParametroVital)
    {
        this.interParametroVital = interParametroVital;
    }
    
    public int findByNome(String nomeParametro)
    {
        return interParametroVitalFacade.listarPorNome(nomeParametro).getPkIdParametroVital();
    }
    
    public String unidadeParametro(String nomeParametro, Long pkIdRegistoInternamento)
    {
        switch (nomeParametro)
        {
            case "Pulso":
                if (InterControloParametrosVitaisListarBean.getInstanciaBean().findByParametro(nomeParametro, pkIdRegistoInternamento) != null)
                    return InterControloParametrosVitaisListarBean.getInstanciaBean().findByParametro(nomeParametro, pkIdRegistoInternamento).getFkIdPulsoUnidade().getDescricao();
            case "Saturação":
                return "%";
            case "Temperatura":
                return "ºC";
            case "Peso":
                return "Kg";
            case "Diuresi":
                return "ml";
            case "Glicemia":
                return "mg/dl";
            default:
                return "";
        }
    }
    
    public void findByDescricao()
    {
        listaParametrosVitais = interParametroVitalFacade.findByDescricao(descricaoPesq);

        if (listaParametrosVitais.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaParametrosVitais.size() + " registo(s) retornado(s).");
        }
    }
}
