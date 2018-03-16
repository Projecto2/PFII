/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterOpcaoMedicacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.InterOpcaoMedicacaoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterOpcaoMedicacaoListarBean implements Serializable
{

    @EJB
    private InterOpcaoMedicacaoFacade interOpcaoMedicacaoFacade;

    private String descricaoPesq, codPesq;
    
    private List<InterOpcaoMedicacao> listaOpcaoMedicacao;

    private InterOpcaoMedicacao interOpcaoMedicacao;
    
    /**
     * Creates a new instance of InterOpcaoMedicacaoListarBean
     */
    public InterOpcaoMedicacaoListarBean()
    {
    }

    public static InterOpcaoMedicacaoListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterOpcaoMedicacaoListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interOpcaoMedicacaoListarBean");
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public String getCodPesq()
    {
        return codPesq;
    }

    public void setCodPesq(String codPesq)
    {
        this.codPesq = codPesq;
    }

    public List<InterOpcaoMedicacao> getListaOpcaoMedicacao()
    {
        return listaOpcaoMedicacao;
    }

    public void setListaOpcaoMedicacao(List<InterOpcaoMedicacao> listaOpcaoMedicacao)
    {
        this.listaOpcaoMedicacao = listaOpcaoMedicacao;
    }

    public InterOpcaoMedicacao getInterOpcaoMedicacao()
    {
        return interOpcaoMedicacao;
    }

    public void setInterOpcaoMedicacao(InterOpcaoMedicacao interOpcaoMedicacao)
    {
        this.interOpcaoMedicacao = interOpcaoMedicacao;
    }

    
    
    public List<InterOpcaoMedicacao> getListarTodas()
    {
        return interOpcaoMedicacaoFacade.findAll();
    }
    
    public List<InterOpcaoMedicacao> findByDescricao()
    {
        return interOpcaoMedicacaoFacade.findByDescricao();
    }
    
    public void findAllByDescricao()
    {
        listaOpcaoMedicacao = interOpcaoMedicacaoFacade.findAllByDescricao(descricaoPesq, codPesq);

        if (listaOpcaoMedicacao.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaOpcaoMedicacao.size() + " registo(s) retornado(s).");
        }
    }
}
