/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.RhEstagiario;
import entidade.RhFuncionario;
import entidade.SupiAgendaAula;
import entidade.SupiControloPresenca;
import entidade.SupiCriacaoTurma;
import entidade.SupiEstadoPresenca;
import entidade.SupiTurmaEstagiario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.RhEstagiarioFacade;
import sessao.RhFuncionarioFacade;
import sessao.SupiAgendaAulaFacade;
import sessao.SupiControloPresencaFacade;
import sessao.SupiCriacaoTurmaFacade;
import sessao.SupiEstadoPresencaFacade;
import sessao.SupiTurmaEstagiarioFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@ViewScoped
public class SupiCriarAgendaAulaBean implements Serializable
{
    
    @EJB
    private SupiControloPresencaFacade supiControloPresencaFacade;

    
    @EJB
    private RhEstagiarioFacade rhEstagiarioFacade;
    @EJB
    private SupiCriacaoTurmaFacade supiCriacaoTurmaFacade;

    @EJB
    private SupiAgendaAulaFacade supiAgendaAulaFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
     @EJB
    private SupiTurmaEstagiarioFacade supiTurmaEstagiarioFacade;

      @EJB
    private SupiEstadoPresencaFacade supiEstadoPresencaFacade;
      
      
      
     
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of SupiCriarAgendaAulaBean
     */
    private SupiAgendaAula supiAgendaAula, pesquisaAgendaAula;
    private List<SupiAgendaAula> listaAgendaAula;
    
    private List<SupiControloPresenca> listaControloPresencas;
   // private SupiControloPresenca supiControloPresenca;
    
    private SupiEstadoPresenca supiEstadoPresenca;
   
   
    private int sig;
    private int idestagiario;
    private Date dataInicio, dataFim;
    private SupiControloPresenca supiControloPresenca = new SupiControloPresenca();
    
    private SupiAgendaAula supiAgendaAulaSelecionada;
    
    private int idagendaAula;
            
    
    public SupiCriarAgendaAulaBean()
    {
    }

    public static SupiCriarAgendaAulaBean getInstanciaBean()
    {
        return (SupiCriarAgendaAulaBean) GeradorCodigo.getInstanciaBean("supiCriarAgendaAulaBean");
    }

    public static SupiAgendaAula getInstancia()
    {
        SupiAgendaAula item = new SupiAgendaAula();

        item.setFkIdCriacaoTurma(new SupiCriacaoTurma());
        item.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return item;
    }

    public SupiAgendaAula getSupiAgendaAula()
    {

        if (supiAgendaAula == null)
        {
            supiAgendaAula = SupiCriarAgendaAulaBean.getInstancia();
        }
        return supiAgendaAula;
    }

    public List<SupiAgendaAula> getListaAgendaAula()
    {
        return listaAgendaAula;
    }

    public void setListaAgendaAula(List<SupiAgendaAula> listaAgendaAula)
    {
        this.listaAgendaAula = listaAgendaAula;
    }


    public void setSupiAgendaAula(SupiAgendaAula supiAgendaAula)
    {
        this.supiAgendaAula = supiAgendaAula;
    }

    public Date getDataInicio()
    {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim()
    {
        dataFim = new Date();
        dataFim = getEndOfDay(dataFim);
        return dataFim;
    }

    public void setDataFim(Date dataFim)
    {
        dataFim = getEndOfDay(dataFim);
        this.dataFim = dataFim;
    }

    public Date getEndOfDay(Date date)
    {

        if (date != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        }
        return new Date();
    }

    public Date getHoje()
    {
        return new Date();
    }

    public void pesquisarAgendaAula()
    {
        setListaAgendaAula(supiAgendaAulaFacade.findAgendaAula(pesquisaAgendaAula, dataInicio, dataFim));
        if (getListaAgendaAula().isEmpty())
        {
            Mensagem.warnMsg("Nenhuma aula encontrada para esta pesquisa");
        }
    }

    public List<RhEstagiario> findall()
    {
        return rhEstagiarioFacade.findAll();
    }

    public List<RhFuncionario> findallFuncionario()
    {
        return rhFuncionarioFacade.findAll();
    }

    public List<SupiCriacaoTurma> findallCriacaoTurma()
    {
        return supiCriacaoTurmaFacade.findAll();
    }

    public List<SupiAgendaAula> findallAgendaAulaPorTurma()
    {

        if (getSupiAgendaAula().getFkIdCriacaoTurma().getPkIdCriacaoTurma() != null)
        {
            return supiAgendaAulaFacade.findAgendaAulaPorTurma(supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma());
        }

        return new ArrayList<>();
    }

    public void salvaAgenda()
    {
        try
        {
            userTransaction.begin();

            if (supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma() == null)
            {
                Mensagem.erroMsg("Indique a turma");
                throw new IllegalArgumentException("Turma");
            }

            supiAgendaAulaFacade.create(supiAgendaAula);

            userTransaction.commit();

            Mensagem.sucessoMsg("Agenda Gravada com Sucesso!!!");

            Integer turma = supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma();

            supiAgendaAula = getInstancia();
            supiAgendaAula.getFkIdCriacaoTurma().setPkIdCriacaoTurma(turma);
            //Actualiza a lista de aulas da turma incluindo a última gravada
            listaAgendaAula = findallAgendaAulaPorTurma();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro!");
                userTransaction.rollback();
            } catch (SystemException | IllegalStateException | SecurityException ex)
            {
                e.printStackTrace();
                System.out.println("Rollback: " + ex.toString());
            }
        }

    }
    
    
    
     public void salvarPresenca()
    {
        try
        {
            userTransaction.begin();
            this.supiControloPresenca.setFkIdAgendaAula(new SupiAgendaAula(idagendaAula));
            this.supiControloPresenca.setFkIdEstadoPresenca(new SupiEstadoPresenca(sig));
            this.supiControloPresenca.setFkIdEstagiario(new RhEstagiario(idestagiario));
            supiControloPresencaFacade.create(supiControloPresenca);

            userTransaction.commit();

            Mensagem.sucessoMsg("Agenda Gravada com Sucesso!!!");

           
            

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro!");
                userTransaction.rollback();
            } catch (SystemException | IllegalStateException | SecurityException ex)
            {
                e.printStackTrace();
                System.out.println("Rollback: " + ex.toString());
            }
        }

    }

    
    
    
    

    public void changeTurma(ValueChangeEvent e)
    {
        Integer turma = (Integer) e.getNewValue();
        supiAgendaAula.getFkIdCriacaoTurma().setPkIdCriacaoTurma(turma);
        listaAgendaAula = findallAgendaAulaPorTurma();
        
        
    }
    
    
   
    
    public List<SupiControloPresenca> listarEstagiariosAgenda(){
    
    return supiControloPresencaFacade.findEstagiariosPorAgenda(idagendaAula);
    }        
    
    public List<SupiTurmaEstagiario> listarEstagiarios(){
    
    return supiTurmaEstagiarioFacade.findEstagiariosPorTurma(supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma());
    }
    
    
     public List<SupiEstadoPresenca> findallestadopresenca()
    {
        return supiEstadoPresencaFacade.findAll();
    }
    
   

    public int getSig() {
        return sig;
    }

    public void setSig(int sig) {
        this.sig = sig;
    }
 
    public void testarPresenca(String b){
    
    
        System.out.println(sig+","+b);
        
    }
    
   
    public SupiEstadoPresenca getSupiEstadoPresenca() {
        return supiEstadoPresenca;
    }

    public void setSupiEstadoPresenca(SupiEstadoPresenca supiEstadoPresenca) {
        this.supiEstadoPresenca = supiEstadoPresenca;
    }

    public SupiControloPresenca getSupiControloPresenca() {
        return supiControloPresenca;
    }

    public void setSupiControloPresenca(SupiControloPresenca supiControloPresenca) {
        this.supiControloPresenca = supiControloPresenca;
    }

    public int getIdestagiario() {
        return idestagiario;
    }

    public void setIdestagiario(int idestagiario) {
        this.idestagiario = idestagiario;
    }
    
    
    public void pegarIdAgenda(int a){
        
       this.idagendaAula=a; 
    
    }

    public SupiAgendaAula getSupiAgendaAulaSelecionada() {
        return supiAgendaAulaSelecionada;
    }

    public void setSupiAgendaAulaSelecionada(SupiAgendaAula supiAgendaAulaSelecionada) {
        this.supiAgendaAulaSelecionada = supiAgendaAulaSelecionada;
    }

    public int getIdagendaAula() {
        return idagendaAula;
    }

    public void setIdagendaAula(int idagendaAula) {
        this.idagendaAula = idagendaAula;
    }

    public List<SupiControloPresenca> getListaControloPresencas() {
        return listaControloPresencas;
    }

    public void setListaControloPresencas(List<SupiControloPresenca> listaControloPresencas) {
        this.listaControloPresencas = listaControloPresencas;
    }

}