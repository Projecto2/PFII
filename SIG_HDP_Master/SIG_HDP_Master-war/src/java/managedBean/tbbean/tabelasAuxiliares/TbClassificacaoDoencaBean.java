/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbClassificacaoDoenca;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessao.TbClassificacaoDoencaFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbClassificacaoDoencaBean implements Serializable
{
    @EJB
    private TbClassificacaoDoencaFacade tbClassificacaoDoencaFacade;

    private List<TbClassificacaoDoenca> listaClassificacaoDoenca;
    
    private TbClassificacaoDoenca tbClassificacaoDoenca;
    
    /**
     * Creates a new instance of TbClassificacaoDoencaBean
     */
    public TbClassificacaoDoencaBean()
    {
    }
    
    public static TbClassificacaoDoencaBean getInstanciaBean()
    {
        return (TbClassificacaoDoencaBean) GeradorCodigo.getInstanciaBean("tbClassificacaoDoencaBean");
    }

    public void pesquisarClassificacaoDoenca()
    {
        listaClassificacaoDoenca = tbClassificacaoDoencaFacade.findClassificacaoDoenca(new TbClassificacaoDoenca());
        if (listaClassificacaoDoenca.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaClassificacaoDoenca.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbClassificacaoDoenca> getListaClassificacaoDoenca()
    {
        if (listaClassificacaoDoenca == null)
        {
            pesquisarClassificacaoDoenca();
        }
        return listaClassificacaoDoenca;
    }
    
    public TbClassificacaoDoenca getInstanciaTbClassificacaoDoenca()
    {
        if (tbClassificacaoDoenca == null)
            tbClassificacaoDoenca = new TbClassificacaoDoenca();
        return tbClassificacaoDoenca;
    }
    
}
