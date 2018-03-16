/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbTipoExame;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import sessao.TbTipoExameFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTipoExameBean implements Serializable
{
    @EJB private TbTipoExameFacade tbTipoExameFacade;
    
    private List<TbTipoExame> listaTipoExame;
    
    private TbTipoExame tbTipoExame;
    /**
     * Creates a new instance of TbTipoExameBean
     */
    public TbTipoExameBean()
    {
    }

    public static TbTipoExameBean getInstanciaBean()
    {
        return (TbTipoExameBean) GeradorCodigo.getInstanciaBean("tbTipoExameBean");
    }

    public void pesquisarTipoExame()
    {
        listaTipoExame = tbTipoExameFacade.findTipoExame(new TbTipoExame());
        if (listaTipoExame.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaTipoExame.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbTipoExame> getListaTipoExame()
    {
        if (listaTipoExame == null)
        {
            pesquisarTipoExame();
        }
        return listaTipoExame;
    }
    
    public TbTipoExame getInstcanciaTbTipoExame()
    {
        if (tbTipoExame == null)
            tbTipoExame = new TbTipoExame();
        return tbTipoExame;
    }
    
}
