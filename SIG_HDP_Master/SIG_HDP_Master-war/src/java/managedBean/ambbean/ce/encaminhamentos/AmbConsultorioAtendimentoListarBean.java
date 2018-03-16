/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.encaminhamentos;

import entidade.AmbConsultorio;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbTriagem;
import entidade.RhFuncionario;
import entidade.SupiMedicoHasEscala;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaAmbConsultorioAtendimento;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbConsultorioFacade;
import sessao.AmbTriagemFacade;
import sessao.RhFuncionarioFacade;
import sessao.SupiMedicoHasEscalaFacade;
import util.DataUtils;
import util.GeradorCodigo;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultorioAtendimentoListarBean implements Serializable
{
    @EJB
    private AmbConsultorioFacade ambConsultorioFacade;
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private SupiMedicoHasEscalaFacade supiMedicoHasEscalaFacade;

    private AmbConsultorioAtendimento ambConsultorioAtendimento
                                    , ambConsultorioAtendimentoAux;
    
    private DataTable rowKey;
    
    private Date dataInicio
               , dataFinal;
    
    private int codigoMedico;

    private long codigoTriagem;
    
    private String numeroPaciente;

    /**
     * Creates a new instance of AmbConsultorioAtendimentoListarBean
     */
    public AmbConsultorioAtendimentoListarBean()
    {
    }

    public DataTable getRowKey()
    {
        return rowKey;
    }

    public void setRowKey(DataTable rowKey)
    {
        this.rowKey = rowKey;
    }

    public void teste(/*DataTable var*/)
    {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent uic = FacesContext.getCurrentInstance().getViewRoot();
        String id = viewRoot.getViewId();
System.out.print("1:AmbConsultorioAtendimento.teste()\nValor   : " + rowKey.findComponent("tabela"));//uic.getId()        
        getRowKey().getColumnSelectionMode();
    }
    
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbConsultorioAtendimentoListarBean getInstanciaBean()
    {
        return (AmbConsultorioAtendimentoListarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoListarBean");
    }

    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/
    
    public AmbConsultorioAtendimento getAmbConsultorioAtendimentoAux()
    {
        if (this.ambConsultorioAtendimentoAux == null)
        {
            this.ambConsultorioAtendimentoAux = getInstanciaAmbConsultorioAtendimento();
        }
        return ambConsultorioAtendimentoAux;
    }

    public void setAmbConsultorioAtendimentoAux(AmbConsultorioAtendimento ambConsultorioAtendimentoAux)
    {
        this.ambConsultorioAtendimentoAux = ambConsultorioAtendimentoAux;
    }

    public Date getDataFinal()
    {
        if (dataFinal == null)
        {
            dataFinal = new Date();
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        this.dataFinal = dataFinal;
    }
    
    public Date getDataInicio()
    {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }    
    
    public int getCodigoMedico()
    {
        return codigoMedico;
    }

    public void setCodigoMedico(int codigoMedico)
    {
        this.codigoMedico = codigoMedico;
    }

    public long getCodigoTriagem()
    {
        return codigoTriagem;
    }

    public void setCodigoTriagem(long codigoTriagem)
    {
        this.codigoTriagem = codigoTriagem;
    }

    public String getNumeroPaciente()
    {
        if (numeroPaciente == null)
        {
            numeroPaciente = new String();
        }
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente)
    {
        this.numeroPaciente = numeroPaciente;
    }
    
    /*********************Fim dos métodos setters e getters********************/
    
    
    /*********************Início dos métodos para pesquisas********************/
    
    public List<AmbConsultorioAtendimento> findAll()
    {
        return ambConsultorioAtendimentoFacade.findAll();
    }

    public List<AmbTriagem> listarTodosDadosTriagensDescrescente()
    {
        return ambTriagemFacade.findAllOrderByDataDecrescente("Em espera");
    }

    public List<AmbTriagem> listarDadosTriagens()
    {
//        int i = 0;
//
//        List<AmbTriagem> resultado = new ArrayList<>();
//
//        resultado.clear();
//
//        for (AmbTriagem at : ambTriagemFacade.findAll())
//            if (at != null)
//                if (at.getFkIdEstado().getDescricao().equals("Em espera"))
//                    if (DataUtils.toString(at.getDataHoraTriagem()).equals(DataUtils.dataTimeAgora()))
//                        resultado.add(at);
//        return resultado;
        return ambTriagemFacade.listarDadosTriagens();
    }

    public List<AmbConsultorioAtendimento> pesquisarPacienteEncaminhado(String np)
    {
        List<AmbConsultorioAtendimento> resultado = new ArrayList<>();
        
        resultado.clear();
        
        if (!ambConsultorioAtendimentoFacade.findAll().isEmpty())
            for (AmbConsultorioAtendimento aca : ambConsultorioAtendimentoFacade.findAll())
                 if (aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                     resultado.add(aca);
        
        return resultado;
    }    
    
    public List<AmbConsultorioAtendimento> pesquisarEncaminhamentos()
    {
        return ambConsultorioAtendimentoFacade.findEncaminhamentos(ambConsultorioAtendimento, dataInicio, dataFinal);
    }    
    
    public List<SupiMedicoHasEscala> listarMedicosEscalados()
    {
        /**
         * @author Ivandro dos Santos Colombo
         *
         * Antes de validar o Funcionário, verificar primeiro se ano é o
         * corrente, se é o mês corrente posteriormente verificar a hora de
         * entrada com a hora de saída, isto é, comparando com a data do
         * Sistema.
         */
        List<SupiMedicoHasEscala> resultado = new ArrayList<>();
//        
        if (supiMedicoHasEscalaFacade.findAll() != null)
            for (SupiMedicoHasEscala sme : supiMedicoHasEscalaFacade.findAll())
                 if (sme.getFkIdConsultorio() != null)
                     if ((DataUtils.toString(sme.getData()).equals(DataUtils.dataTimeAgora())))
                         for (RhFuncionario rf : rhFuncionarioFacade.findAll())
                              if (rf != null)
                                  if (rf.getPkIdFuncionario().equals(sme.getFkIdMedico().getPkIdFuncionario()))
                                      if (rf.getFkIdProfissao().getDescricao().equals("Médico"))
                                          resultado.add(sme);
        return resultado;
    }

    public void seleccionar(AmbConsultorioAtendimento aca)
    {
        ambConsultorioAtendimentoAux = aca;
    }

    public AmbConsultorioAtendimento seleccionarDetalhesEncaminhamento(AmbConsultorioAtendimento aca)
    {
        ambConsultorioAtendimentoAux = aca;

//System.out.print("1:AmbConsultorioAtendimento.seleccionarDetalhesEncaminhamento()\nId Triagem   : " + aca.getPkIdConsultorioAtendimento());
//System.out.print("2:AmbConsultorioAtendimento.seleccionarDetalhesEncaminhamento()\nNome         : " + aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
//System.out.print("3:AmbConsultorioAtendimento.seleccionarDetalhesEncaminhamento()\nData         : " + aca.getDataHoraCadastro());
        return ambConsultorioAtendimentoAux;
    }    
    
    public String dataSistema()
    {
        return DataUtils.dataTimeAgoraFull();
    }

    public String dateToString(Date data)
    {
        return DataUtils.dateToString(data);
    }

    /**********************Fim dos métodos para pesquisas**********************/
}
