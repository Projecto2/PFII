/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbTipoNotificacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import sessao.TbTipoNotificacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTipoNotificacaoBean implements Serializable
{

    @EJB
    private TbTipoNotificacaoFacade tbTipoNotificacaoFacade;

    private List<TbTipoNotificacao> listaTipoNotificacao;

    private TbTipoNotificacao tbTipoNotificacao;

    /**
     * Creates a new instance of TbTipoNotificacaoBean
     */
    public TbTipoNotificacaoBean()
    {
    }

    public static TbTipoNotificacaoBean getInstanciaBean()
    {
        return (TbTipoNotificacaoBean) GeradorCodigo.getInstanciaBean("tbTipoNotificacaoBean");
    }

    public void pesquisarTipoNotificacao()
    {
        listaTipoNotificacao = tbTipoNotificacaoFacade.findTipoNotificacao(new TbTipoNotificacao());
        if (listaTipoNotificacao.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaTipoNotificacao.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbTipoNotificacao> getListaTipoNotificacao()
    {
        if (listaTipoNotificacao == null)
        {
            pesquisarTipoNotificacao();
        }
        return listaTipoNotificacao;
    }

    public TbTipoNotificacao getInstanciaTbTipoNotificacao()
    {
        if (tbTipoNotificacao == null)
        {
            tbTipoNotificacao = new TbTipoNotificacao();
        }
        return tbTipoNotificacao;
    }

}
