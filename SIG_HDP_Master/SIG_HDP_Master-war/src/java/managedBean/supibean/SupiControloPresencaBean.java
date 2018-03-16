/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.RhEstagiario;
import entidade.SupiAgendaAula;
import entidade.SupiControloPresenca;
import entidade.SupiEstadoPresenca;
import entidade.SupiTurmaEstagiario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.estagiario.RhEstagiarioNovoBean;
import sessao.RhEstagiarioFacade;
import sessao.SupiAgendaAulaFacade;
import sessao.SupiControloPresencaFacade;
import sessao.SupiEstadoPresencaFacade;
import sessao.SupiTurmaEstagiarioFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiControloPresencaBean implements Serializable
{
    @EJB
    private SupiEstadoPresencaFacade supiEstadoPresencaFacade;

    @EJB
    private SupiAgendaAulaFacade supiAgendaAulaFacade;

    @EJB
    private SupiTurmaEstagiarioFacade supiTurmaEstagiarioFacade;
    @EJB
    private RhEstagiarioFacade rhEstagiarioFacade;
    @EJB
    private SupiControloPresencaFacade supiControloPresencaFacade;
    

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of SupiControloPresencaBean
     */
    private SupiEstadoPresenca supiEstadoPresenca;
    private SupiControloPresenca supiControloPresenca;
    private List<RhEstagiario> listaRhEstagiario;
    private SupiTurmaEstagiario supiTurmaEstagiario;
    private SupiAgendaAula supiAgendaAula;

    public SupiControloPresencaBean()
    {
        
    }

    public static SupiControloPresencaBean getInstanciaBean()
    {
        return (SupiControloPresencaBean) GeradorCodigo.getInstanciaBean("supiControloPresencaBean");
    }

    public static SupiControloPresenca getInstancia()
    {
        SupiControloPresenca item = new SupiControloPresenca();

        item.setFkIdAgendaAula(SupiCriarAgendaAulaBean.getInstancia());
        item.setFkIdEstadoPresenca(new SupiEstadoPresenca());
        item.setFkIdEstagiario(RhEstagiarioNovoBean.getInstancia());

        return item;
    }

    public SupiControloPresenca getSupiControloPresenca()
    {
        if (supiControloPresenca == null)
        {
            supiControloPresenca = SupiControloPresencaBean.getInstancia();
        }
        return supiControloPresenca;
    }

    public void setSupiControloPresenca(SupiControloPresenca supiControloPresenca)
    {
        this.supiControloPresenca = supiControloPresenca;
    }

    public void prepararEstagiariosPresencaAula(SupiAgendaAula supiAgendaAula)
    {
        List<SupiTurmaEstagiario> listaSupiTurmaEstagiario;

        //Pega os estagiários da turma a qual a aula pertence
        listaSupiTurmaEstagiario = supiTurmaEstagiarioFacade.findEstagiariosPorTurma(supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma());

        listaRhEstagiario = new ArrayList<>();

        //Para cada estagiário da turma, prepara o controle da presença para a aula agendada
        for (SupiTurmaEstagiario ste : listaSupiTurmaEstagiario)
        {
            RhEstagiario estagiario = ste.getFkIdEstagiario();
            estagiario.setSupiControloPresencaList(new ArrayList<SupiControloPresenca>());

            SupiControloPresenca controloPresenca = supiControloPresencaFacade.findPorEstagiarioDataAula(estagiario.getPkIdEstagiario(), supiAgendaAula.getData());

            if (controloPresenca == null)
            {
                SupiControloPresenca scp = new SupiControloPresenca();

                scp.setFkIdEstadoPresenca(new SupiEstadoPresenca());
                scp.setFkIdAgendaAula(supiAgendaAula);
                scp.setFkIdEstagiario(estagiario);

                estagiario.getSupiControloPresencaList().add(scp);

            } else
            {
                estagiario.getSupiControloPresencaList().add(controloPresenca);
            }

            //Adiciona o estagiário a lista, já com o controle da presença preparado
            listaRhEstagiario.add(estagiario);

        }
    }
    
    
    public String buscaSiglaPresenca(Integer presenca) {
        System.out.println("presenca recebido para sigla: "+supiEstadoPresenca);
        if (presenca != null && presenca != 0) {
            return supiEstadoPresencaFacade.find(presenca).getSigla();
        }
        System.out.println("eh null");
        return "";
    }
    
    
    public List<SupiEstadoPresenca> findall()
    {
        return supiEstadoPresencaFacade.findAll();
    }

    public void salvaControloPresencas()
    {
        try
        {
            userTransaction.begin();

            if (listaRhEstagiario == null || listaRhEstagiario.isEmpty())
            {
                throw new Exception("Nenhum estagiário encontrado");
            }

            for (RhEstagiario rhEstagiario : listaRhEstagiario)
            {
                SupiControloPresenca controloPresenca = rhEstagiario.getSupiControloPresencaList().get(0);

                if (controloPresenca.getFkIdEstadoPresenca().getPkIdEstadoPresenca() != null)
                {
                    supiControloPresencaFacade.create(controloPresenca);

                }

            }

            userTransaction.commit();

            Mensagem.sucessoMsg("Turma Gravada com Sucesso!!!");
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
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

    }

}
