/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbFaseTratamento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessao.TbFaseTratamentoFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbFaseTratamentoBean implements Serializable
{

    @EJB
    private TbFaseTratamentoFacade tbFaseTratamentoFacade;

    private List<TbFaseTratamento> listaFaseTratamento;
    
    private TbFaseTratamento tbFaseTratamento;

    /**
     * Creates a new instance of FaseTratamentoBean
     */
    public TbFaseTratamentoBean()
    {
    }

    public static TbFaseTratamentoBean getInstanciaBean()
    {
        return (TbFaseTratamentoBean) GeradorCodigo.getInstanciaBean("tbFaseTratamentoBean");
    }

    public void pesquisarTbFaseTratamento()
    {
        listaFaseTratamento = tbFaseTratamentoFacade.findFaseTratamento(new TbFaseTratamento());
        if (listaFaseTratamento.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaFaseTratamento.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbFaseTratamento> getListaFaseTratamento()
    {
        if (listaFaseTratamento == null)
        {
            pesquisarTbFaseTratamento();
        }
        return listaFaseTratamento;
    }
    
    public TbFaseTratamento getInstanciaTbFaseTratamento()
    {
        if (tbFaseTratamento == null)
            tbFaseTratamento = new TbFaseTratamento();
        return tbFaseTratamento;
    }
    
}
