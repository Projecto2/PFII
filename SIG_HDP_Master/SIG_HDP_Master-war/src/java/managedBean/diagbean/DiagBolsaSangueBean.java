/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagBolsaSangue;
import entidade.DiagGrupoSanguineo;
import entidade.DiagTipagemDador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagBolsaSangueFacade;
import sessao.DiagTipagemDadorFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagBolsaSangueBean implements Serializable
{

    @EJB
    private DiagTipagemDadorFacade diagTipagemDadorFacade;

    @EJB
    private DiagBolsaSangueFacade diagBolsaSangueFacade;

    private DiagBolsaSangue diagBolsaSanguePesquisar, diagBolsaSangueVisualizar;

    private DiagTipagemDador diagTipagemDadorVisualizar;
    private DiagGrupoSanguineo diagGrupoSanguineoPesquisar;

    private Date dataInicioCadastro, dataFimCadastro, dataInicioExpiracao, dataFimExpiracao;

    private boolean pesquisar;

    List<DiagBolsaSangue> itens;

    private int numeroRegistos = 10;
    
    public static DiagBolsaSangueBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagBolsaSangueBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagBolsaSangueBean");
    }

    public static DiagBolsaSangue getInstancia()
    {
        DiagBolsaSangue diagBolsaSangue = new DiagBolsaSangue();
        diagBolsaSangue.setFkIdCandidatoDador(DiagCandidatoDadorBean.getInstancia());

        return diagBolsaSangue;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }
    
    public List<DiagBolsaSangue> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagBolsaSangue> itens)
    {
        this.itens = itens;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public Date getDataInicioCadastro()
    {
        return dataInicioCadastro;
    }

    public void setDataInicioCadastro(Date dataInicioCadastro)
    {
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public Date getDataFimCadastro()
    {
        return dataFimCadastro;
    }

    public void setDataFimCadastro(Date dataFimCadastro)
    {
        this.dataFimCadastro = dataFimCadastro;
    }

    public Date getDataInicioExpiracao()
    {
        return dataInicioExpiracao;
    }

    public void setDataInicioExpiracao(Date dataInicioExpiracao)
    {
        this.dataInicioExpiracao = dataInicioExpiracao;
    }

    public Date getDataFimExpiracao()
    {
        return dataFimExpiracao;
    }

    public void setDataFimExpiracao(Date dataFimExpiracao)
    {
        this.dataFimExpiracao = dataFimExpiracao;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineoPesquisar()
    {
        if (diagGrupoSanguineoPesquisar == null)
        {
            diagGrupoSanguineoPesquisar = new DiagGrupoSanguineo();
        }
        return diagGrupoSanguineoPesquisar;
    }

    public void setDiagGrupoSanguineoPesquisar(DiagGrupoSanguineo diagGrupoSanguineoPesquisar)
    {
        this.diagGrupoSanguineoPesquisar = diagGrupoSanguineoPesquisar;
    }

    public DiagTipagemDador getDiagTipagemDadorVisualizar()
    {
        if (diagTipagemDadorVisualizar == null)
        {
            diagTipagemDadorVisualizar = DiagTipagemDadorBean.getInstancia();
        }
        return diagTipagemDadorVisualizar;
    }

    public void setDiagTipagemDadorVisualizar(DiagTipagemDador diagTipagemDadorVisualizar)
    {
        this.diagTipagemDadorVisualizar = diagTipagemDadorVisualizar;
    }

    public DiagBolsaSangue getDiagBolsaSanguePesquisar()
    {
        if (diagBolsaSanguePesquisar == null)
        {
            diagBolsaSanguePesquisar = getInstancia();
        }
        return diagBolsaSanguePesquisar;
    }

    public void setDiagBolsaSanguePesquisar(DiagBolsaSangue diagBolsaSanguePesquisar)
    {
        this.diagBolsaSanguePesquisar = diagBolsaSanguePesquisar;
    }

    public DiagBolsaSangue getDiagBolsaSangueVisualizar()
    {
        if (diagBolsaSangueVisualizar == null)
        {
            diagBolsaSangueVisualizar = getInstancia();
        }

        return diagBolsaSangueVisualizar;
    }

    public void setDiagBolsaSangueVisualizar(DiagBolsaSangue diagBolsaSangueVisualizar)
    {
        this.diagBolsaSangueVisualizar = diagBolsaSangueVisualizar;
    }

    public List<DiagBolsaSangue> findAll()
    {
        return diagBolsaSangueFacade.findAll();
    }

    public void selecionarBolsaSangueVisualizar(DiagBolsaSangue diagBolsaSangueAux)
    {
        this.diagBolsaSangueVisualizar = diagBolsaSangueAux;

        if (diagBolsaSangueAux != null)
        {
            diagTipagemDadorVisualizar = diagTipagemDadorFacade.findTipagemDador(diagBolsaSangueAux.getFkIdCandidatoDador());
        }
    }

    public List<DiagBolsaSangue> pesquisarBolsasDeSangue()
    {
        itens = diagBolsaSangueFacade.findBolsasSangue(diagBolsaSanguePesquisar, diagGrupoSanguineoPesquisar, dataInicioCadastro, dataFimCadastro, dataInicioExpiracao, dataFimExpiracao, numeroRegistos);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
        return null;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        itens = new ArrayList<>();
        diagBolsaSanguePesquisar = null;
        diagGrupoSanguineoPesquisar = null;

        dataInicioCadastro = dataFimCadastro = dataInicioExpiracao = dataFimExpiracao = null;

        return "bancoDeSangue.xhtml?faces-redirect=true";
    }
}
