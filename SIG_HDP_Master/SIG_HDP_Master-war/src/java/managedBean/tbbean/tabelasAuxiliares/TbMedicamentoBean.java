/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbMedicamento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessao.TbMedicamentoFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbMedicamentoBean implements Serializable
{
    @EJB
    private TbMedicamentoFacade tbMedicamentoFacade;

    private List<TbMedicamento> listaMedicamento;
    
    private TbMedicamento tbMedicamento;
    
    /**
     * Creates a new instance of TbMedicamentoBean
     */
    public TbMedicamentoBean()
    {
    }
    
    public static TbMedicamentoBean getInstanciaBean()
    {
        return (TbMedicamentoBean) GeradorCodigo.getInstanciaBean("tbMedicamentoBean");
    }
    
    public TbMedicamento getInstanciaTbMedicamento()
    {
        if (tbMedicamento == null)
            tbMedicamento = new TbMedicamento();
        return tbMedicamento;
    }
    
    

    public void pesquisarMedicamento()
    {
        listaMedicamento = tbMedicamentoFacade.findMedicamento(new TbMedicamento());
        if (listaMedicamento.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaMedicamento.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbMedicamento> getListaMedicamento()
    {
        if (listaMedicamento == null)
        {
            pesquisarMedicamento();
        }
        return listaMedicamento;
    }
    
    
    
}
