/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean.encargo;

import entidade.AdmsCategoriaServico;
import entidade.AdmsPaciente;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.FinEncargoDevido;
import entidade.FinPrecoCategoriaServico;
import entidade.FinTipoEncargo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import managedBean.finbean.FinPagamentoBean;
import sessao.AdmsCategoriaServicoFacade;
import sessao.FinEncargoDevidoFacade;
import sessao.FinTipoEncargoFacade;
import util.Mensagem;
//import util.adms.ConstantesAdms;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinEncargoDevidoGenericoBean
{
    @EJB
    private FinTipoEncargoFacade finTipoEncargoFacade;
    @EJB
    private FinEncargoDevidoFacade finEncargoDevidoFacade;
    @EJB
    private AdmsCategoriaServicoFacade admsCategoriaServicoFacade;
    
    protected FinEncargoDevido encargoDevidoPesquisa;
    
    protected Date dataInicial, dataFinal, dataAgendadaInicial, dataAgendadaFinal;
    
    protected Integer idServico, subProcesso = 0;
    
    protected List<AdmsCategoriaServico> categoriasServicos;
    
    protected boolean escolhaServico = false;
    
    protected AdmsPaciente paciente;
    
    protected List<FinEncargoDevido> listaEncargosDevidos, listaEncargosDevidosEscolhidosParaPagar;
    
    

    /**
     * Creates a new instance of FinEncargoGenericoBean
     */
    public FinEncargoDevidoGenericoBean()
    {
    }
    
    public static FinEncargoDevidoGenericoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FinEncargoDevidoGenericoBean finEncargoDevidoGenericoBean = 
            (FinEncargoDevidoGenericoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "finEncargoDevidoGenericoBean");
        
        return finEncargoDevidoGenericoBean;
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
            dataFinal = AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(new Date());
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        if(dataFinal != null)
        {
            dataFinal = AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(dataFinal);
            this.dataFinal = dataFinal;
        }
    }
    

    public Date getMomentoActual()
    {
        return new Date();
    }

    public FinEncargoDevido getEncargoDevidoPesquisa()
    {
        if(encargoDevidoPesquisa == null)
        {
            encargoDevidoPesquisa = getInstanciaEncargoDevido();
        }
        return encargoDevidoPesquisa;
    }

    public void setEncargoDevidoPesquisa(FinEncargoDevido encargoDevidoPesquisa)
    {
        this.encargoDevidoPesquisa = encargoDevidoPesquisa;
    }
    
    
    public FinEncargoDevido getInstanciaEncargoDevido()
    {
        FinEncargoDevido encargo = new FinEncargoDevido();
        encargo.setFkIdTipoEncargo(new FinTipoEncargo());
        encargo.setFkIdServicoSolicitado(new AdmsServicoSolicitado());
        encargo.setFkIdPrecoCategoriaServico(new FinPrecoCategoriaServico());
        encargo.getFkIdPrecoCategoriaServico().setFkIdCategoriaServico(new AdmsCategoriaServico());
        encargo.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().setFkIdServico(new AdmsServico());
        return encargo;
    }

    public Integer getSubProcesso()
    {
        if(subProcesso == null)
            subProcesso = 0;
        return subProcesso;
    }

    public void setSubProcesso(Integer subProcesso)
    {
        this.subProcesso = subProcesso;
    }

    public Integer getIdServico()
    {
        return idServico;
    }

    public void setIdServico(Integer idServico)
    {
        this.idServico = idServico;
        categoriasServicos = admsCategoriaServicoFacade.findTodasCategoriasServico(new AdmsServico(idServico));
    }

    public List<AdmsCategoriaServico> getCategoriasServicos()
    {
        return categoriasServicos;
    }

    public void setCategoriasServicos(List<AdmsCategoriaServico> categoriasServicos)
    {
        this.categoriasServicos = categoriasServicos;
    }
    
    
    public void changeTipoEncargo(ValueChangeEvent e)
    {
        if(e.getNewValue() == null)
        {
            escolhaServico = false;
            idServico = null;
            return;
        }
        System.out.println("mudou para "+e.getNewValue().toString());
        FinTipoEncargo tipoEncargo = finTipoEncargoFacade.find(Integer.parseInt(e.getNewValue().toString()));
        String tipo = e.getNewValue().toString();
        if(tipoEncargo.getDescricaoTipoEncargo().equalsIgnoreCase("Serviço"))
        {
            escolhaServico = true;
        }
        else{
            escolhaServico = false;
            idServico = null;
        }
    }

    public boolean isEscolhaServico()
    {
        return escolhaServico;
    }

    public void setEscolhaServico(boolean escolhaServico)
    {
        this.escolhaServico = escolhaServico;
    }
    
    
    
//    
    public void setPaciente(AdmsPaciente paciente)
    {
        this.paciente = paciente;
    }
    
    public void setPacienteEncargoPaciente(AdmsPaciente paciente)
    {
        encargoDevidoPesquisa = null;
        
        dataInicial = null;
        dataFinal = null;
        
        escolhaServico = false;
        
        this.paciente = paciente;
        
        listaEncargosDevidos = null;
        listaEncargosDevidosEscolhidosParaPagar = null;
    }
    
    public AdmsPaciente getPaciente()
    {
        if(paciente == null)
        {
            paciente = new AdmsPaciente();
        }
        return paciente;
    }

    public List<FinEncargoDevido> getListaEncargosDevidos()
    {
        return listaEncargosDevidos;
    }

    public void setListaEncargosDevidos(List<FinEncargoDevido> listaEncargosDevidos)
    {
        this.listaEncargosDevidos = listaEncargosDevidos;
    }

    public List<FinEncargoDevido> getListaEncargosDevidosEscolhidosParaPagar()
    {
        if(listaEncargosDevidosEscolhidosParaPagar == null)
        {
            listaEncargosDevidosEscolhidosParaPagar = new ArrayList<>();
        }
        return listaEncargosDevidosEscolhidosParaPagar;
    }

    public void setListaEncargosDevidosEscolhidosParaPagar(List<FinEncargoDevido> listaEncargosDevidosEscolhidosParaPagar)
    {
        this.listaEncargosDevidosEscolhidosParaPagar = listaEncargosDevidosEscolhidosParaPagar;
    }
    
    
    
    
    
    public void pesquisar()
    {
        
        if (listaEncargosDevidos == null)
        {
            listaEncargosDevidos = new ArrayList<>();
        }
        if (validarDadosAPesquisar())
        {
            encargoDevidoPesquisa.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getFkIdServico().setPkIdServico(idServico);
            listaEncargosDevidos = finEncargoDevidoFacade.findEncargosDevidos
           (encargoDevidoPesquisa, subProcesso, dataInicial, dataFinal, paciente, 100);
            if(listaEncargosDevidos == null || listaEncargosDevidos.isEmpty())
                Mensagem.warnMsg("Nenhum Encargo Devido Encontrado Para Este Paciente");
            else Mensagem.sucessoMsg("Tabela Atualizada. "+listaEncargosDevidos.size()+" registos!");
        }
    }
    
    public boolean validarDadosAPesquisar()
    {
        if(dataInicial != null && dataFinal != null)
        {
            if(dataInicial.compareTo(dataFinal) > 0)
            {
                Mensagem.erroMsg("A Data Inicial Nao Pode Ser Superior A Data Final");
                return false;
            }
        }
        return true;
    }
    
    
    public void atualizarPesquisa()
    {
        
        if (listaEncargosDevidos == null)
        {
            listaEncargosDevidos = new ArrayList<>();
        }

        encargoDevidoPesquisa.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getFkIdServico().setPkIdServico(idServico);
        listaEncargosDevidos = finEncargoDevidoFacade.findEncargosDevidos
       (encargoDevidoPesquisa, subProcesso, dataInicial, dataFinal, paciente, 100);
    }
    
    
    public void definirEncargosASeremPagos()
    {
//        ArrayList listaEncargos = (ArrayList) listaEncargosDevidos.;
        ArrayList<FinEncargoDevido> listaEncargos = new ArrayList<>();
        listaEncargos.addAll(listaEncargosDevidosEscolhidosParaPagar);
        FinPagamentoBean.getInstanciaBean().setListaDeEncargosDevidosASerPagos(listaEncargos);
        FinPagamentoBean.getInstanciaBean().setPaciente(paciente);
    }
    
}
