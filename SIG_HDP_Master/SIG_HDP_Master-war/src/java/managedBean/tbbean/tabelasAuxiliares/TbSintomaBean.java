/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbSintoma;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessao.TbSintomaFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbSintomaBean implements Serializable
{

    @EJB
    private TbSintomaFacade tbSintomaFacade;

    private List<TbSintoma> listaSintoma;
    
    private TbSintoma tbSintoma;

    /**
     * Creates a new instance of SintomaBean
     */
    public TbSintomaBean()
    {
    }

    public static TbSintomaBean getInstanciaBean()
    {
        return (TbSintomaBean) GeradorCodigo.getInstanciaBean("tbSintomaBean");
    }

    public void pesquisarSintoma()
    {
        listaSintoma = tbSintomaFacade.findSintoma(new TbSintoma());
        if (listaSintoma.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaSintoma.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbSintoma> getListaSintoma()
    {
        if (listaSintoma == null)
        {
            pesquisarSintoma();
        }
        return listaSintoma;
    }
    
    public TbSintoma getInstanciaTbSintoma()
    {
        if (tbSintoma == null)
            tbSintoma = new TbSintoma();
        return tbSintoma;
    }

}
