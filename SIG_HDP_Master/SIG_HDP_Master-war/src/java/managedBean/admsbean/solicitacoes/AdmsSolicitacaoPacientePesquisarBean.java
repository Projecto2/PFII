/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.solicitacoes;

import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
//import entidade.AdmsServicoSolicitado_;
import entidade.AdmsSolicitacao;
import entidade.AdmsSubprocesso;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.AdmsSolicitacaoFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsSolicitacaoPacientePesquisarBean implements Serializable
{
    @EJB
    private AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private AdmsSolicitacaoFacade admsSolicitacaoFacade;
    
    

    private AdmsSolicitacao solicitacaoPesquisar, solicitacaoDetalhes;
    
    private List<AdmsSolicitacao> listaSolicitacoes;
    
    private Date dataInicial, dataFinal;
    
    private int solicitacaoMedica;
    
    private List<RhFuncionario> funcionariosLista;
    
    private int especialidade;
    
    private boolean solicitacaoMedicaBoolean = false;
    
    private RhFuncionario medico;
//    private Integer quantidadeRegistos = 10;
    /**
     * Creates a new instance of AdmsSolicitacoesBean
     */
    public AdmsSolicitacaoPacientePesquisarBean()
    {
    }
    
    public static AdmsSolicitacaoPacientePesquisarBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsSolicitacaoPacientePesquisarBean admsSolicitacoesBean = 
            (AdmsSolicitacaoPacientePesquisarBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsSolicitacoesBean");
        
        return admsSolicitacoesBean;
    }
    
    
    public AdmsSolicitacao getSolicitacaoPesquisar()
    {
        if(solicitacaoPesquisar == null)
        {
            solicitacaoPesquisar = new AdmsSolicitacao();
            solicitacaoPesquisar.setFkIdSubprocesso(new AdmsSubprocesso());
            solicitacaoPesquisar.getFkIdSubprocesso().setNumeroSubprocesso(0);
        }
        return solicitacaoPesquisar;
    }

    public void setSolicitacaoPesquisar(AdmsSolicitacao solicitacao)
    {
        this.solicitacaoPesquisar = solicitacao;
    }
    
    public Date getDataInicial()
    {
        if(dataInicial == null)
        {
            dataInicial = new Date();
        }
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial)
    {
        this.dataInicial = dataInicial;
    }
    
    
    public Date getDataFinal()
    {
        if(dataFinal == null)
        {
            dataFinal = new Date();
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        dataFinal = getEndOfDay(dataFinal);
        this.dataFinal = dataFinal;
    }
    
    
    public Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    public void setPaciente(AdmsPaciente paciente)
    {
        if(solicitacaoPesquisar == null)
        {
            solicitacaoPesquisar = new AdmsSolicitacao();
        }
        
        dataInicial = null;
        dataFinal = null;
        
        dataInicial = new Date();
        dataFinal = new Date();
        dataFinal = getEndOfDay(dataFinal);
        
        listaSolicitacoes = null;
        listaSolicitacoes = new ArrayList<>();
        solicitacaoPesquisar.setFkIdPaciente(paciente);
        solicitacaoPesquisar.setFkIdSubprocesso(new AdmsSubprocesso());
        solicitacaoPesquisar.getFkIdSubprocesso().setNumeroSubprocesso(0);
    }

    public int getSolicitacaoMedica()
    {
        return solicitacaoMedica;
    }
    
    public void changeTipoDeSolicitacao(ValueChangeEvent e)
    {
        Integer tipo = ((Integer) e.getNewValue());
        if(tipo.intValue() != 2) medico.setPkIdFuncionario(null);
        solicitacaoMedicaBoolean = tipo.intValue() == 2;
    }

    public void setSolicitacaoMedica(int solicitacaoMedica)
    {
        this.solicitacaoMedica = solicitacaoMedica;
    }
    
    
    public void pesquisarSolicitacoes()
    {
        if(validarDadosAPesquisar())
        {
//System.out.println(""+quantidadeRegistos);
            listaSolicitacoes = admsSolicitacaoFacade.findSolicitacao(solicitacaoPesquisar ,dataInicial, dataFinal, solicitacaoMedica, null, medico);
        }
        if(listaSolicitacoes.isEmpty())
            Mensagem.warnMsg("Nenhuma Solicitação Encontrada");
        else Mensagem.sucessoMsg("Tabela Atualizada. "+listaSolicitacoes.size()+" registos!");
    }
    
    public List<AdmsSolicitacao> getListaSolicitacoes()
    {
        return listaSolicitacoes;
    }
    
    public boolean validarDadosAPesquisar()
    {
        if(dataInicial.compareTo(dataFinal) > 0)
        {
            Mensagem.erroMsg("A Data Inicial Nao Pode Ser Superior A Data Final");
            return false;
        }
        return true;
    }
    
    
    public void carregarServicosSolicitados(AdmsSolicitacao solicitacao)
    {
        List<AdmsServicoSolicitado> lista = admsServicoSolicitadoFacade.findServicosSolicitadosBySolicitacao(solicitacao.getPkIdSolicitacao());
        System.out.println("nada "+lista);
        solicitacao.setAdmsServicoSolicitadoList(lista);
    }
    

     public void limparSolicitacoes()
     {
         listaSolicitacoes = null;
     }
     
     public Date getDataAtual()
     {
         return new Date();
     }
    
    public void limparResultadosLista()
    {
        if(listaSolicitacoes != null) listaSolicitacoes.clear();
    }
    
    
    public int getEspecialidade()
    {
        return especialidade;
    }

    public void setEspecialidade(int especialidade)
    {
        this.especialidade = especialidade;
    }
    
    public void changeEspecialidade(ValueChangeEvent e)
    {
        this.especialidade = ((Integer) e.getNewValue());
        carregarMedicos(especialidade);
    }
    
    
    public void carregarMedicos(int especialidade)
    {
        if(funcionariosLista == null)
        {
            funcionariosLista = new ArrayList<>();
        } else funcionariosLista.clear();
        funcionariosLista.addAll(rhFuncionarioFacade.findMedicosAtivosEspecialidade(especialidade));
    }

    public List<RhFuncionario> getFuncionariosLista()
    {
        if(funcionariosLista == null)
        {
            funcionariosLista = new ArrayList<>();
            carregarMedicos(0);
        }
        return funcionariosLista;
    }

    public boolean isSolicitacaoMedicaBoolean()
    {
        return solicitacaoMedicaBoolean;
    }

    public void setSolicitacaoMedicaBoolean(boolean solicitacaoMedicaBoolean)
    {
        this.solicitacaoMedicaBoolean = solicitacaoMedicaBoolean;
    }

    public RhFuncionario getMedico()
    {
        if(medico == null)
        {
            medico = new RhFuncionario();
        }
        return medico;
    }

    public void setMedico(RhFuncionario medico)
    {
        this.medico = medico;
    }

    public AdmsSolicitacao getSolicitacaoDetalhes()
    {
        return solicitacaoDetalhes;
    }

    public void setSolicitacaoDetalhes(AdmsSolicitacao solicitacaoDetalhes)
    {
        this.solicitacaoDetalhes = solicitacaoDetalhes;
    }
    
    public List<AdmsServicoSolicitado> getServicosSolicitadosDetalhes()
    {
        if(solicitacaoDetalhes == null) return null;
        return solicitacaoDetalhes.getAdmsServicoSolicitadoList();
    }

    
}
