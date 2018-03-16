/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbTipoDoente;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessao.TbTipoDoenteFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTipoDoenteBean implements Serializable
{

    @EJB
    private TbTipoDoenteFacade tbTipoDoenteFacade;

    private List<TbTipoDoente> listaTipoDoente;
    
    private TbTipoDoente tbTipoDoente;

    /**
     * Creates a new instance of TbTipoDoenteBean
     */
    public TbTipoDoenteBean()
    {
    }

    public static TbTipoDoenteBean getInstanciaBean()
    {
        return (TbTipoDoenteBean) GeradorCodigo.getInstanciaBean("tbTipoDoenteBean");
    }
    
    public void pesquisarTipoDoente ()
    {
        listaTipoDoente = tbTipoDoenteFacade.findTipoDoente(new TbTipoDoente());
        if (listaTipoDoente.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaTipoDoente.size() + TbMensagens.REGISTO);
        }
    }
    
    public List<TbTipoDoente> getListaTipoDoente()
    {
        if (listaTipoDoente == null)
        {
            pesquisarTipoDoente();
        }
        return listaTipoDoente;
    }
    
    public TbTipoDoente getInstanciaTbTipoDoente()
    {
        if (tbTipoDoente == null)
            tbTipoDoente = new TbTipoDoente();
        return tbTipoDoente;
    }
    
}
