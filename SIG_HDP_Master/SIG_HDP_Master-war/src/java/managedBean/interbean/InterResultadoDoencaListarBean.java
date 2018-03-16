/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterResultadoDoenca;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.InterResultadoDoencaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterResultadoDoencaListarBean
{
    @EJB
    private InterResultadoDoencaFacade interResultadoDoencaFacade;

    private String descricaoPesq;
    
    private List<InterResultadoDoenca> listaResultadosDoenca;

    private InterResultadoDoenca interResultadoDoenca;
    
    /**
     * Creates a new instance of InterResultadoDoencaListarBean
     */
    public InterResultadoDoencaListarBean()
    {
    }
    
    public static InterResultadoDoencaListarBean getInstanciaBean()
    {
        return (InterResultadoDoencaListarBean) GeradorCodigo.getInstanciaBean("interResultadoDoencaListarBean");
    }
    
    public List<InterResultadoDoenca> findAll() 
    {
         return interResultadoDoencaFacade.findAll();
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public List<InterResultadoDoenca> getListaResultadosDoenca()
    {
        return listaResultadosDoenca;
    }

    public void setListaResultadosDoenca(List<InterResultadoDoenca> listaResultadosDoenca)
    {
        this.listaResultadosDoenca = listaResultadosDoenca;
    }

    public InterResultadoDoenca getInterResultadoDoenca()
    {
        return interResultadoDoenca;
    }

    public void setInterResultadoDoenca(InterResultadoDoenca interResultadoDoenca)
    {
        this.interResultadoDoenca = interResultadoDoenca;
    }
    
    public void findByDescricao()
    {
        listaResultadosDoenca = interResultadoDoencaFacade.findByDescricao(descricaoPesq);

        if (listaResultadosDoenca.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaResultadosDoenca.size() + " registo(s) retornado(s).");
        }
    }
}
