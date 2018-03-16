/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagTriagem;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagTriagemFacade;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTriagemBean implements Serializable
{

    @EJB
    private DiagTriagemFacade diagTriagemFacade;

    private DiagTriagem diagTriagem, diagTriagemPesquisa, diagTriagemVisualizar;

    private boolean pesquisar;

    public static DiagTriagemBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTriagemBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTriagemBean");
    }

    public static DiagTriagem getInstancia()
    {
        DiagTriagem diagTriagem = new DiagTriagem();
        diagTriagem.setFkIdCandidatoDador(DiagCandidatoDadorBean.getInstancia());
        diagTriagem.setFkIdResultadoTriagem(DiagResultadoTriagemBean.getInstancia());
        
        return diagTriagem;
    }
    
    public DiagTriagem getDiagTriagem()
    {
        if (diagTriagem == null)
        {
            diagTriagem = getInstancia();
        }
        return diagTriagem;
    }

    public void setDiagTriagem(DiagTriagem diagTriagem)
    {
        this.diagTriagem = diagTriagem;
    }

    public DiagTriagem getDiagTriagemPesquisa()
    {
        if (diagTriagemPesquisa == null)
        {
            diagTriagemPesquisa = getInstancia();
        }
        return diagTriagemPesquisa;
    }

    public void setDiagTriagemPesquisa(DiagTriagem diagTriagemPesquisa)
    {
        this.diagTriagemPesquisa = diagTriagemPesquisa;
    }

    public DiagTriagem getDiagTriagemVisualizar()
    {
        if (diagTriagemVisualizar == null)
        {
            diagTriagemVisualizar = getInstancia();
        }
        return diagTriagemVisualizar;
    }

    public void setDiagTriagemVisualizar(DiagTriagem diagTriagemVisualizar)
    {
        this.diagTriagemVisualizar = diagTriagemVisualizar;
    }

    public boolean getPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagTriagem = diagTriagemPesquisa = diagTriagemVisualizar = null;

        return "dadorCandidatoDadorPesquisar.xhtml?faces-redirect=true";
    }
}
