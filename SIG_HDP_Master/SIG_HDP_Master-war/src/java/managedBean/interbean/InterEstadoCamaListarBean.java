/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterEstadoCama;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.InterEstadoCamaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterEstadoCamaListarBean implements Serializable
{

    @EJB
    private InterEstadoCamaFacade interEstadoCamaFacade;

    private String estadoPesq;

    private InterEstadoCama interEstadoCama;
    
    private List<InterEstadoCama> listaEstadosCama;

    /**
     * Creates a new instance of StatusInterBean
     */
    public InterEstadoCamaListarBean()
    {
    }

    public static InterEstadoCamaListarBean getInstanciaBean()
    {
        return (InterEstadoCamaListarBean) GeradorCodigo.getInstanciaBean("interEstadoCamaListarBean");
    }

    public InterEstadoCama getInterEstadoCama()
    {
        return interEstadoCama;
    }

    public void setInterEstadoCama(InterEstadoCama interEstadoCama)
    {
        this.interEstadoCama = interEstadoCama;
    }

    public String getEstadoPesq()
    {
        return estadoPesq;
    }

    public void setEstadoPesq(String estadoPesq)
    {
        this.estadoPesq = estadoPesq;
    }

    public List<InterEstadoCama> getListaEstadosCama()
    {
        return listaEstadosCama;
    }

    public void setListaEstadosCama(List<InterEstadoCama> listaEstadosCama)
    {
        this.listaEstadosCama = listaEstadosCama;
    }

    public List<InterEstadoCama> findAll()
    {
        return interEstadoCamaFacade.findAll();
    }
    
    public List<InterEstadoCama> findAllLivre()
    {
        return interEstadoCamaFacade.findAllLivre();
    }

    public void findByDescricao()
    {
        listaEstadosCama = interEstadoCamaFacade.findByDescricao(estadoPesq);

        if (listaEstadosCama.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaEstadosCama.size() + " registo(s) retornado(s).");
        }
    }

    public InterEstadoCamaFacade getInterEstadoCamaFacade()
    {
        return interEstadoCamaFacade;
    }

    public void setInterEstadoCamaFacade(InterEstadoCamaFacade interEstadoCamaFacade)
    {
        this.interEstadoCamaFacade = interEstadoCamaFacade;
    }
    
    
}
