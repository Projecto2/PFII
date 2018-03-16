/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.tabelasAuxiliares;

import entidade.InterTipoAlta;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import managedBean.interbean.carregamentoExcel.InterTipoAltaExcelBean;
import sessao.InterTipoAltaFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTipoAltaBean implements Serializable
{

    @EJB
    private InterTipoAltaFacade interTipoAltaFacade;

    private List<InterTipoAlta> listaTipoAlta;

    /**
     * Creates a new instance of TbTipoAltaBean
     */
    public TbTipoAltaBean()
    {
    }

    public static TbTipoAltaBean getInstanciaBean()
    {
        return (TbTipoAltaBean) GeradorCodigo.getInstanciaBean("tbTipoAltaBean");
    }
    
    public void pesquisarTipoAlta ()
    {
        listaTipoAlta = interTipoAltaFacade.findAll();
        if (listaTipoAlta.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaTipoAlta.size() + TbMensagens.REGISTO);
        }
    }
    
    public void carregarInterTipoAlta ()
    {
        InterTipoAltaExcelBean.getInstanciaBean().carregarTipoAltaTabela();
        Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);

        pesquisarTipoAlta();
    }
    
    public List<InterTipoAlta> getListaCaracteristica()
    {
        if (listaTipoAlta == null)
        {
            pesquisarTipoAlta();
        }
        return listaTipoAlta;
    }

}
