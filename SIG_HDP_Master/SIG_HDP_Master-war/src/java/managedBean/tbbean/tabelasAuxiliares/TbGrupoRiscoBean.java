/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbGrupoRisco;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessao.TbGrupoRiscoFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbGrupoRiscoBean implements Serializable
{
    @EJB
    private TbGrupoRiscoFacade tbGrupoRiscoFacade;

    private List<TbGrupoRisco> listaGrupoRisco;
    
    private TbGrupoRisco tbGrupoRisco;

    /**
     * Creates a new instance of GrupoRiscoBean
     */
    public TbGrupoRiscoBean()
    {
    }

    public static TbGrupoRiscoBean getInstanciaBean()
    {
        return (TbGrupoRiscoBean) GeradorCodigo.getInstanciaBean("tbGrupoRiscoBean");
    }
    
    public void pesquisarGrupoRisco ()
    {
        listaGrupoRisco = tbGrupoRiscoFacade.findGrupoRisco(new TbGrupoRisco());
        if (listaGrupoRisco.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaGrupoRisco.size() + TbMensagens.REGISTO);
        }
    }
    
    public List<TbGrupoRisco> getListaTbGrupoRisco()
    {
        if (listaGrupoRisco == null)
        {
            pesquisarGrupoRisco();
        }
        return listaGrupoRisco;
    }
    
    public TbGrupoRisco getInstanciaTbGrupoRisco()
    {
        if (tbGrupoRisco == null)
            tbGrupoRisco = new TbGrupoRisco();
        return tbGrupoRisco;
    }
    
}
