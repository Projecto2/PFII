/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.RhFuncionario;
import entidade.SupiEscala;
import entidade.SupiSeccao;
import entidade.SupiSupervisorHasEscala;
import entidade.SupiTipoEscala;
import entidade.SupiTurno;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.RhFuncionarioFacade;
import sessao.SupiEscalaFacade;
import sessao.SupiSeccaoFacade;
import sessao.SupiSupervisorHasEscalaFacade;
import sessao.SupiTipoEscalaFacade;
import sessao.SupiTurnoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiEscalaListarBean implements Serializable
{

    @EJB
    private SupiSeccaoFacade supiSeccaoFacade;
    @EJB
    private SupiTurnoFacade supiTurnoFacade;
    @EJB
    private SupiTipoEscalaFacade supiTipoEscalaFacade;
    @EJB
    private SupiEscalaFacade supiEscalaFacade;
    @EJB
    private SupiSupervisorHasEscalaFacade supiSupervisorHasEscalaFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;

    private List<SupiSupervisorHasEscala> escalaMensalList;

    private int ano;
    private int mes;
    private int pkIdTurno;
    private int pkIdSeccao;
    private int pkIdTipoEscala;
    private int pkIdFuncionario;

    private SupiSupervisorHasEscala supiSupervisorHasEscalaPesquisa;

    private Calendar dataCorrente;

    /**
     * Creates a new instance of EscalaListarBean
     */
    public SupiEscalaListarBean()
    {
    }
    

    public static SupiEscalaListarBean getInstanciaBean()
    {
        return (SupiEscalaListarBean) GeradorCodigo.getInstanciaBean("supiEscalaListarBean");
    }

    public SupiSupervisorHasEscala getInstancia()
    {
        SupiSupervisorHasEscala supervisor = new SupiSupervisorHasEscala();
//        SupiEscala escala = new SupiEscala();
//        escala.setAno(dataCorrente.get(Calendar.YEAR));
//        escala.setMes(dataCorrente.get(Calendar.MONTH));
//        System.out.println("MEs> "+dataCorrente.get(Calendar.MONTH));

        SupiEscala escala = new SupiEscala();
        escala.setFkIdSeccao(new SupiSeccao());
        escala.setFkIdTipoEscala(new SupiTipoEscala());
        supervisor.setFkIdEscala(escala);

        supervisor.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());
        supervisor.setFkIdTurno(new SupiTurno());

        return supervisor;
    }

    public SupiSeccaoFacade getSupiSeccaoFacade()
    {
        return supiSeccaoFacade;
    }

    public void setSupiSeccaoFacade(SupiSeccaoFacade supiSeccaoFacade)
    {
        this.supiSeccaoFacade = supiSeccaoFacade;
    }

    public SupiTurnoFacade getSupiTurnoFacade()
    {
        return supiTurnoFacade;
    }

    public void setSupiTurnoFacade(SupiTurnoFacade supiTurnoFacade)
    {
        this.supiTurnoFacade = supiTurnoFacade;
    }

    public SupiTipoEscalaFacade getSupiTipoEscalaFacade()
    {
        return supiTipoEscalaFacade;
    }

    public void setSupiTipoEscalaFacade(SupiTipoEscalaFacade supiTipoEscalaFacade)
    {
        this.supiTipoEscalaFacade = supiTipoEscalaFacade;
    }

    public SupiEscalaFacade getSupiEscalaFacade()
    {
        return supiEscalaFacade;
    }

    public void setSupiEscalaFacade(SupiEscalaFacade supiEscalaFacade)
    {
        this.supiEscalaFacade = supiEscalaFacade;
    }

    public SupiSupervisorHasEscalaFacade getSupiSupervisorHasEscalaFacade()
    {
        return supiSupervisorHasEscalaFacade;
    }

    public void setSupiSupervisorHasEscalaFacade(SupiSupervisorHasEscalaFacade supiSupervisorHasEscalaFacade)
    {
        this.supiSupervisorHasEscalaFacade = supiSupervisorHasEscalaFacade;
    }

    public RhFuncionarioFacade getRhFuncionarioFacade()
    {
        return rhFuncionarioFacade;
    }

    public void setRhFuncionarioFacade(RhFuncionarioFacade rhFuncionarioFacade)
    {
        this.rhFuncionarioFacade = rhFuncionarioFacade;
    }

    public List<SupiSupervisorHasEscala> getEscalaMensalList()
    {
        if (escalaMensalList == null)
        {
            escalaMensalList = new ArrayList<>();
        }
        return escalaMensalList;
    }

    public void setEscalaMensalList(List<SupiSupervisorHasEscala> escalaMensalList)
    {
        this.escalaMensalList = escalaMensalList;
    }

    public int getAno()
    {
        return ano;
    }

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public int getMes()
    {
        return mes;
    }

    public void setMes(int mes)
    {
        this.mes = mes;
    }

    public int getPkIdTurno()
    {
        return pkIdTurno;
    }

    public void setPkIdTurno(int pkIdTurno)
    {
        this.pkIdTurno = pkIdTurno;
    }

    public int getPkIdSeccao()
    {
        return pkIdSeccao;
    }

    public void setPkIdSeccao(int pkIdSeccao)
    {
        this.pkIdSeccao = pkIdSeccao;
    }

    public int getPkIdTipoEscala()
    {
        return pkIdTipoEscala;
    }

    public void setPkIdTipoEscala(int pkIdTipoEscala)
    {
        this.pkIdTipoEscala = pkIdTipoEscala;
    }

    public int getPkIdFuncionario()
    {
        return pkIdFuncionario;
    }

    public void setPkIdFuncionario(int pkIdFuncionario)
    {
        this.pkIdFuncionario = pkIdFuncionario;
    }

    public SupiSupervisorHasEscala getSupiSupervisorHasEscalaPesquisa()
    {

        if (supiSupervisorHasEscalaPesquisa == null)
        {
            supiSupervisorHasEscalaPesquisa = getInstancia();
        }
        return supiSupervisorHasEscalaPesquisa;
    }

    public void setSupiSupervisorHasEscalaPesquisa(SupiSupervisorHasEscala supiSupervisorHasEscalaPesquisa)
    {
        this.supiSupervisorHasEscalaPesquisa = supiSupervisorHasEscalaPesquisa;
    }

    public void pesquisarEscalas()
    {
        List<SupiEscala> escalaList = supiEscalaFacade.findEscala(supiSupervisorHasEscalaPesquisa.getFkIdEscala());
                
        if (! escalaList.isEmpty())
        {
            SupiEscala escalaEncontrada = null;
            escalaEncontrada = escalaList.get(0);
         
            supiSupervisorHasEscalaPesquisa.setFkIdEscala(escalaEncontrada);
            
            setEscalaMensalList(supiSupervisorHasEscalaFacade.findEscalaMensal(supiSupervisorHasEscalaPesquisa));
        }
        
//        System.out.println("seccao:" + supiSupervisorHasEscalaPesquisa.getFkIdEscala().getFkIdSeccao());
//        System.out.println("Turno:" + supiSupervisorHasEscalaPesquisa.getFkIdTurno());
//        System.out.println("Ano:" + supiSupervisorHasEscalaPesquisa.getFkIdEscala().getAno());
//        System.out.println("Mes:" + supiSupervisorHasEscalaPesquisa.getFkIdEscala().getMes());


        if (escalaMensalList == null || escalaMensalList.isEmpty())
        {
            Mensagem.erroMsg("Nenhum registro encontrado para esta pesquisa");
        } else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + escalaMensalList.size() + " registo(s) retornado(s).");
        }
    }

    //este metodo recebe o funcionario e faz o setfKiDfUNCIONARIO DOA ESCALA
    public List<SupiSupervisorHasEscala> getEscalaPorFuncionario(RhFuncionario funcionario)
    {
//        supiSupervisorHasEscalaPesquisa.setFkIdFuncionario(funcionario);
        System.out.println("getEscalaPorFuncionario: " + funcionario);
        
        funcionario.setSupiSupervisorHasEscalaList(new ArrayList<SupiSupervisorHasEscala>());
        
        if (escalaMensalList != null)
        {
            for (SupiSupervisorHasEscala item : escalaMensalList)
            {
                if (item.getFkIdFuncionario().equals(funcionario))
                {
                    funcionario.getSupiSupervisorHasEscalaList().add(item);
                }
            }
        }        
        return funcionario.getSupiSupervisorHasEscalaList();
//        return supiSupervisorHasEscalaFacade.findEscala(supiSupervisorHasEscalaPesquisa);
//        return supiSupervisorHasEscalaFacade.findDiaTurno(supiSupervisorHasEscalaPesquisa.getFkIdEscala().getPkIdEscala(), funcionario.getPkIdFuncionario());
    }

    public List<RhFuncionario> obterFuncionarios()
    {
        List<RhFuncionario> listaFuncionarios = new ArrayList<>();
        if (escalaMensalList != null)
        {
            for (SupiSupervisorHasEscala item : escalaMensalList)
            {
                if (!listaFuncionarios.contains(item.getFkIdFuncionario()))
                {
                    listaFuncionarios.add(item.getFkIdFuncionario());
                }
            }
        }
        return listaFuncionarios;
    }
}


