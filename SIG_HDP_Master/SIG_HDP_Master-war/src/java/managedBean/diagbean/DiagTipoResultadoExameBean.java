/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagTipoResultadoExame;
import entidade.DiagTipoResultadoExame;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import static managedBean.diagbean.DiagTipoResultadoExameBean.getInstancia;
import sessao.DiagTipoResultadoExameFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoResultadoExameBean implements Serializable
{

    @EJB
    private DiagTipoResultadoExameFacade diagTipoResultadoExameFacade;
    private DiagTipoResultadoExame diagTipoResultadoExamePesquisar;
    private boolean pesquisar;
    List<DiagTipoResultadoExame> itens;

    public static DiagTipoResultadoExameBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipoResultadoExameBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipoResultadoExameBean");
    }

    public static DiagTipoResultadoExame getInstancia()
    {
        DiagTipoResultadoExame diagTipoResultadoExame = new DiagTipoResultadoExame();

        return diagTipoResultadoExame;
    }

    public DiagTipoResultadoExame getDiagTipoResultadoExamePesquisar()
    {
        if (diagTipoResultadoExamePesquisar == null)
        {
            diagTipoResultadoExamePesquisar = getInstancia();
        }
        return diagTipoResultadoExamePesquisar;
    }

    public void setDiagTipoResultadoExamePesquisar(DiagTipoResultadoExame diagTipoResultadoExamePesquisar)
    {
        this.diagTipoResultadoExamePesquisar = diagTipoResultadoExamePesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagTipoResultadoExame> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagTipoResultadoExame> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagTipoResultadoExamePesquisar = getInstancia();
        itens = new ArrayList<>();

        return "tipoResultadoExame.xhtml?faces-redirect=true";
    }

    public void pesquisarTipoResultadoExame()
    {
        itens = diagTipoResultadoExameFacade.findPesquisa(diagTipoResultadoExamePesquisar);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }
}
