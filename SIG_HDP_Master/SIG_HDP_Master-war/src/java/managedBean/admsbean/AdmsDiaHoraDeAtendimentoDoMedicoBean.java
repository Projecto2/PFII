/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsAgendamento;
import entidade.AdmsDiaHoraDeAtendimentoDoMedico;
import entidade.GrlDiaSemana;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsDiaHoraDeAtendimentoDoMedicoFacade;
import sessao.GrlDiaSemanaFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class AdmsDiaHoraDeAtendimentoDoMedicoBean implements Serializable
{
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsDiaHoraDeAtendimentoDoMedicoFacade admsDiaHoraDeAtendimentoDoMedicoFacade;
    @EJB
    private GrlDiaSemanaFacade grlDiaSemanaFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
    
    
    private Integer quantidadeRegistos = 10;
    
    private List<RhFuncionario> medicos;
    
    private RhFuncionario medicoSelecionado;
    
    private GrlDiaSemana grlDiaSemana;
    
    private AdmsDiaHoraDeAtendimentoDoMedico admsDiaHoraDeAtendimentoDoMedico, admsDiaHoraDeAtendimentoDoMedicoEliminar;
    
    private List<AdmsAgendamento> listaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico;
    
    @Resource
    private UserTransaction userTransaction;
    
//    private String nomeCompleto;

    public AdmsDiaHoraDeAtendimentoDoMedicoBean()
    {
    }
    
    public static AdmsDiaHoraDeAtendimentoDoMedicoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsDiaHoraDeAtendimentoDoMedicoBean admsDiaHoraDeAtendimentoDoMedicoBean = 
            (AdmsDiaHoraDeAtendimentoDoMedicoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsDiaHoraDeAtendimentoDoMedicoBean");
        
        return admsDiaHoraDeAtendimentoDoMedicoBean;
    }
    
    
    public String getDocumentoIdentificacao(RhFuncionario paciente)
    {
        if(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty())
            return "";
        return ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getDescricao()+": "
            + ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento();
    }


    public AdmsDiaHoraDeAtendimentoDoMedico getAdmsDiaHoraDeAtendimentoDoMedico()
    {
        if(admsDiaHoraDeAtendimentoDoMedico == null)
        {
            admsDiaHoraDeAtendimentoDoMedico = new AdmsDiaHoraDeAtendimentoDoMedico();
            admsDiaHoraDeAtendimentoDoMedico.setFkIdDiaDaSemana(new GrlDiaSemana());
        }
        return admsDiaHoraDeAtendimentoDoMedico;
    }

    
    
    public void setAdmsDiaHoraDeAtendimentoDoMedico(AdmsDiaHoraDeAtendimentoDoMedico admsDiaHoraDeAtendimentoDoMedico)
    {
        this.admsDiaHoraDeAtendimentoDoMedico = admsDiaHoraDeAtendimentoDoMedico;
    }
    
    
    public GrlDiaSemana getGrlDiaSemana()
    {
        if(grlDiaSemana == null)
        {
            grlDiaSemana = new GrlDiaSemana();
        }
        return grlDiaSemana;
    }

    public void setGrlDiaSemana(GrlDiaSemana grlDiaSemana)
    {
        this.grlDiaSemana = grlDiaSemana;
    }
    
    
    public void pesquisarMedico()
    {
        if(medicos != null)
            medicos.clear();
        medicos = rhFuncionarioFacade.listaDosMedicos();
        if(medicos.isEmpty())
            Mensagem.warnMsg("Nenhum Médico Encontrado");
        else Mensagem.sucessoMsg("Tabela Atualizada. "+medicos.size()+" registos!");
    }

    public List<RhFuncionario> getMedicos()
    {
        return medicos;
    }

    public void setMedicos(List<RhFuncionario> medicos)
    {
        this.medicos = medicos;
    }

    public RhFuncionario getMedicoSelecionado()
    {
        return medicoSelecionado;
    }

    public void setMedicoSelecionado(RhFuncionario medicoSelecionado)
    {
//        System.out.println(" lista "+medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList());
        this.medicoSelecionado = medicoSelecionado;
        this.medicoSelecionado.setAdmsDiaHoraDeAtendimentoDoMedicoList(admsDiaHoraDeAtendimentoDoMedicoFacade.findHorariosByMedico(medicoSelecionado.getPkIdFuncionario()));
    }
    
    
    public void pesquisaAtualizacao()
    {
        if(medicos != null)
            medicos.clear();
        medicos = rhFuncionarioFacade.listaDosMedicos();
    }

    public Integer getQuantidadeRegistos()
    {
        return quantidadeRegistos;
    }

    public void setQuantidadeRegistos(Integer quantidadeRegistos)
    {
        this.quantidadeRegistos = quantidadeRegistos;
    }
    
    
    public void limparResultadosLista()
    {
        if(medicos != null) medicos.clear();
    }
    
    
    
    
//    public String getDocumentoIdentificacao(AdmsPaciente paciente)
//    {
//        if(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty())
//            return "";
//        return ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getDescricao()+": "
//            + ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento();
//    }
    
    
//    public void adicionarDocumentosIdentificacao() throws Exception
//    {
//        for(GrlDocumentoIdentificacao doc:admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList())
//        {
//            doc.setFkIdPessoa(admsPaciente.getFkIdPessoa());
//            grlDocumentoIdentificacaoFacade.create(doc);
//        }
//    }
    
    
    public void gravarHorarioDeAtendimentoMedico()
    {
        if(validarHorarioDeAtendimentoMedico())
        {
            AdmsDiaHoraDeAtendimentoDoMedico admsDiaHoraDeAtendimentoDoMedicoLocal = new AdmsDiaHoraDeAtendimentoDoMedico();
            admsDiaHoraDeAtendimentoDoMedicoLocal.setHoraInicioTrabalho(admsDiaHoraDeAtendimentoDoMedico.getHoraInicioTrabalho());
            admsDiaHoraDeAtendimentoDoMedicoLocal.setHoraFimTrabalho(admsDiaHoraDeAtendimentoDoMedico.getHoraFimTrabalho());
            admsDiaHoraDeAtendimentoDoMedicoLocal.setFkIdDiaDaSemana(grlDiaSemanaFacade.find(admsDiaHoraDeAtendimentoDoMedico.getFkIdDiaDaSemana().getPkIdDiaSemana()));
            admsDiaHoraDeAtendimentoDoMedicoLocal.setFkIdMedico(new RhFuncionario(medicoSelecionado.getPkIdFuncionario()));
            admsDiaHoraDeAtendimentoDoMedicoLocal.setNumeroMaximoPaciente(admsDiaHoraDeAtendimentoDoMedico.getNumeroMaximoPaciente());
            medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList().add(admsDiaHoraDeAtendimentoDoMedicoLocal);
            try
            {
                userTransaction.begin();
                    admsDiaHoraDeAtendimentoDoMedicoFacade.create(admsDiaHoraDeAtendimentoDoMedicoLocal);
                userTransaction.commit();
                Mensagem.sucessoMsg("Gravação Efetuada Com Efetuada");
            }
            catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | NotSupportedException | SystemException ex)
            {
                try
                {
                    userTransaction.rollback();
                    Mensagem.erroMsg(ex.toString());
                }
                catch (IllegalStateException | SecurityException | SystemException excep)
                {
                    Mensagem.erroMsg("Rollback: " + ex.toString());
                }
            }
//            numeroDocumento = "";
        }
    }
//    
    private boolean validarHorarioDeAtendimentoMedico()
    {
        for(int i = 0; i < medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList().size(); i++)
        {
            if(medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList().get(i).getFkIdDiaDaSemana().getPkIdDiaSemana() == 
                admsDiaHoraDeAtendimentoDoMedico.getFkIdDiaDaSemana().getPkIdDiaSemana())
            {
                Mensagem.erroMsg("O Médico Já Possui Um Horário Para Este Dia Semana");
                return false;
            } 
        }
        if(admsDiaHoraDeAtendimentoDoMedico.getHoraInicioTrabalho().compareTo(admsDiaHoraDeAtendimentoDoMedico.getHoraFimTrabalho()) >= 0)
        {
            Mensagem.erroMsg("A Hora de Início de Atendimento Não Pode Ser Superior ou Igual a Hora Fim!");
            return false;
        }
        return true;
    }
    
    
     public void eliminarHorarioDeAtendimentoMedico()
     {
         if((admsDiaHoraDeAtendimentoDoMedico.getPkIdDiaHoraDeAtendimentoDoMedico()!= null) && (admsDiaHoraDeAtendimentoDoMedico.getPkIdDiaHoraDeAtendimentoDoMedico() == admsDiaHoraDeAtendimentoDoMedicoEliminar.getPkIdDiaHoraDeAtendimentoDoMedico()))
         {
             Mensagem.erroMsg("O Horario de Atendimento Está a Ser Editado Não Pode Ser Eliminado");
             return;
         }
        for(int i = 0; i < medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList().size(); i++)
        {
            if(medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList().get(i).getPkIdDiaHoraDeAtendimentoDoMedico() == 
               admsDiaHoraDeAtendimentoDoMedicoEliminar.getPkIdDiaHoraDeAtendimentoDoMedico())
            {
                try
                {
                    userTransaction.begin();
                        admsDiaHoraDeAtendimentoDoMedicoFacade.remove(admsDiaHoraDeAtendimentoDoMedicoEliminar);
                    userTransaction.commit();
                    medicoSelecionado.getAdmsDiaHoraDeAtendimentoDoMedicoList().remove(i);
                    Mensagem.sucessoMsg("Eliminação Efetuada Com Sucesso");
                }
                catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | NotSupportedException | SystemException ex)
                {
                    try
                    {
                        userTransaction.rollback();
                        Mensagem.erroMsg(ex.toString());
                    }
                    catch (IllegalStateException | SecurityException | SystemException excep)
                    {
                        Mensagem.erroMsg("Rollback: " + ex.toString());
                    }
                }
                break;
            }
        }
     }

    public AdmsDiaHoraDeAtendimentoDoMedico getAdmsDiaHoraDeAtendimentoDoMedicoEliminar()
    {
        return admsDiaHoraDeAtendimentoDoMedicoEliminar;
    }

    public void setAdmsDiaHoraDeAtendimentoDoMedicoEliminar(AdmsDiaHoraDeAtendimentoDoMedico admsDiaHoraDeAtendimentoDoMedicoEliminar)
    {
        this.admsDiaHoraDeAtendimentoDoMedicoEliminar = admsDiaHoraDeAtendimentoDoMedicoEliminar;
    }

    public List<AdmsAgendamento> getListaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico()
    {
        if(admsDiaHoraDeAtendimentoDoMedicoEliminar == null)
        {
            return null;
        }
        System.out.println(""+admsDiaHoraDeAtendimentoDoMedicoEliminar);
        int diaDaSemana = -1;
        if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("DOM"))
            diaDaSemana = 0;
        else if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("SEG"))
            diaDaSemana = 1;
        else if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("TER"))
            diaDaSemana = 2;
        else if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("QUA"))
            diaDaSemana = 3;
        else if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("QUI"))
            diaDaSemana = 4;
        else if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("SEX"))
            diaDaSemana = 5;
        else if(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase("SAB"))
            diaDaSemana = 6;
            
        listaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico = admsAgendamentoFacade.findAgendamentoDoMedicoByDiaDaSemana(admsDiaHoraDeAtendimentoDoMedicoEliminar.getFkIdMedico(), diaDaSemana, new Date());
        return listaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico;
    }

    public void setListaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico(List<AdmsAgendamento> listaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico)
    {
        this.listaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico = listaAgendamentosMarcadosNesteDiaDeSemanaParaOMedico;
    }
     
     
    
    
}
