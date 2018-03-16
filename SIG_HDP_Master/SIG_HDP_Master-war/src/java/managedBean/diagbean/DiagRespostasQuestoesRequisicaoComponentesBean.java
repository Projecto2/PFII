/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagRespostasQuestoesRequisicaoComponentes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagRespostasQuestoesRequisicaoComponentesFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagRespostasQuestoesRequisicaoComponentesBean implements Serializable
{

    @EJB
    private DiagRespostasQuestoesRequisicaoComponentesFacade diagRespostasQuestoesRequisicaoComponentesFacade;
    private DiagRespostasQuestoesRequisicaoComponentes diagRespostasQuestoesRequisicaoComponentesPesquisar;
    private boolean pesquisar;
    List<DiagRespostasQuestoesRequisicaoComponentes> itens;

    public static DiagRespostasQuestoesRequisicaoComponentesBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagRespostasQuestoesRequisicaoComponentesBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagRespostasQuestoesRequisicaoComponentesBean");
    }

    public static DiagRespostasQuestoesRequisicaoComponentes getInstancia()
    {
        DiagRespostasQuestoesRequisicaoComponentes diagRespostasQuestoesRequisicaoComponentes = new DiagRespostasQuestoesRequisicaoComponentes();

        return diagRespostasQuestoesRequisicaoComponentes;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getDiagRespostasQuestoesRequisicaoComponentesPesquisar()
    {
        if (diagRespostasQuestoesRequisicaoComponentesPesquisar == null)
        {
            diagRespostasQuestoesRequisicaoComponentesPesquisar = getInstancia();
        }
        return diagRespostasQuestoesRequisicaoComponentesPesquisar;
    }

    public void setDiagRespostasQuestoesRequisicaoComponentesPesquisar(DiagRespostasQuestoesRequisicaoComponentes diagRespostasQuestoesRequisicaoComponentesPesquisar)
    {
        this.diagRespostasQuestoesRequisicaoComponentesPesquisar = diagRespostasQuestoesRequisicaoComponentesPesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagRespostasQuestoesRequisicaoComponentes> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagRespostasQuestoesRequisicaoComponentes> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagRespostasQuestoesRequisicaoComponentesPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "respostasQuestoesRequisicaoComponentes.xhtml?faces-redirect=true";
    }

    public void pesquisarRespostasQuestoesRequisicaoComponentes()
    {
        itens = diagRespostasQuestoesRequisicaoComponentesFacade.findPesquisa(diagRespostasQuestoesRequisicaoComponentesPesquisar);

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
