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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhFuncionarioFacade;
import sessao.SupiEscalaFacade;
import sessao.SupiSeccaoFacade;
import sessao.SupiSupervisorHasEscalaFacade;
import sessao.SupiTipoEscalaFacade;
import sessao.SupiTurnoFacade;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiVisualizarSuperviorBean
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

    /**
     * Entidades
     */
    private SupiEscala escalaVisualizar;
    private List<RhFuncionario> supervisoresList;
    private List<Object> diasDoMesList;
    private SupiSupervisorHasEscala diaTurno;

    /**
     * Creates a new instance of SupiEscalaSupervisor
     */
    public SupiVisualizarSuperviorBean()
    {
    }

    public SupiEscala getInstanciaEscala()
    {
        SupiEscala escala = new SupiEscala();
        escala.setFkIdSeccao(null);
        escala.setFkIdTipoEscala(new SupiTipoEscala());

        return escala;
    }

    public SupiEscala getEscalaVisualizar()
    {
        if (escalaVisualizar == null)
        {
            escalaVisualizar = getInstanciaEscala();
        }
        return escalaVisualizar;
    }

    public void setEscalaVisualizar(SupiEscala escalaVisualizar)
    {
        this.escalaVisualizar = escalaVisualizar;
    }

    public List<RhFuncionario> getSupervisoresList()
    {
        return supervisoresList;
    }

    public List<Object> getDiasDoMesList()
    {
        return diasDoMesList;
    }

    public SupiSupervisorHasEscala getDiaTurno()
    {
        if (diaTurno == null)
        {
            diaTurno = new SupiSupervisorHasEscala();
            diaTurno.setFkIdTurno(new SupiTurno());
        }
        return diaTurno;
    }

    public void setDiaTurno(SupiSupervisorHasEscala diaTurno)
    {
        this.diaTurno = diaTurno;
    }

    public ArrayList<SelectItem> selectMeses()
    {
        ArrayList<SelectItem> itens = new ArrayList<>();

        itens.add(new SelectItem(1, "Janeiro"));
        itens.add(new SelectItem(2, "Fevereiro"));
        itens.add(new SelectItem(3, "Março"));
        itens.add(new SelectItem(4, "Abril"));
        itens.add(new SelectItem(5, "Maio"));
        itens.add(new SelectItem(6, "Junho"));
        itens.add(new SelectItem(7, "Julho"));
        itens.add(new SelectItem(8, "Agosto"));
        itens.add(new SelectItem(9, "Setembro"));
        itens.add(new SelectItem(10, "Outubro"));
        itens.add(new SelectItem(11, "Novembro"));
        itens.add(new SelectItem(12, "Dezembro"));

        return itens;
    }

    public void actualizarDiasDoMesEscala()
    {
        if (escalaVisualizar == null)
        {
            return;
        }

        diasDoMesList = new ArrayList<>();

        for (int i = 1; i <= numeroDiasDoMes(escalaVisualizar.getAno(), escalaVisualizar.getMes()); i++)
        {
            diasDoMesList.add(i);
        }

    }

    public String visualizarMes(SupiEscala escala)
    {
        if (escala != null)
        {
            switch (escala.getMes())
            {
                case 1:
                    return "Janeiro";
                case 2:
                    return "Fevereiro";
                case 3:
                    return "Março";
                case 4:
                    return "Abril";
                case 5:
                    return "Maio";
                case 6:
                    return "Junho";
                case 7:
                    return "Julho";
                case 8:
                    return "Agosto";
                case 9:
                    return "Setembro";
                case 10:
                    return "Outubro";
                case 11:
                    return "Novembro";
                case 12:
                    return "Dezembro";
                default:
                    return "";
            }
        }
        return "";
    }

    private int numeroDiasDoMes(int ano, int mes)
    {
        if (mes > 12 || mes < 1)
        {
            return 0;
        }

        if (mes == 2)
        {
            return (ano % 400 == 0) || (ano % 4 == 0 && ano % 100 != 0) ? 29 : 28;
        }

        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 10 || mes == 12)
        {
            return 31;
        }

        return 30;
    }

    public List<SupiSeccao> listarSeccao()
    {
        return supiSeccaoFacade.findAll();
    }

    public String prepararVisualizacaoAssiduidade(SupiEscala esc)
    {
        setEscalaVisualizar(esc);
        supervisoresList = null;

        if (esc == null)
        {
            return null;
        }

        List<RhFuncionario> funcionarios = supiEscalaFacade.findSupervisoresPorEscala(esc.getPkIdEscala());

        //Para cada funcionário
        for (RhFuncionario f : funcionarios)
        {
            f.setSupiSupervisorHasEscalaList(supiSupervisorHasEscalaFacade.findDiaTurno(esc.getPkIdEscala(), f.getPkIdFuncionario()));
        }
        supervisoresList = funcionarios;
        actualizarDiasDoMesEscala();

        return "visualizarEscalaSupervisores.xhtml?faces-redirect=true";
    }
}
