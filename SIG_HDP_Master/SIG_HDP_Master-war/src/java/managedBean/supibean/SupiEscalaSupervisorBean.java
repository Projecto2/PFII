
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
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
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
public class SupiEscalaSupervisorBean
{

    @Resource
    private UserTransaction userTransaction;

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
    private SupiEscala escala, escalaPesquisa;
    private List<SupiEscala> escalasPesquisadasList;
    private List<RhFuncionario> supervisoresList = new ArrayList();
    private List<SupiTurno> turnoList = new ArrayList();
    private List<Object> diasDoMesList;
    private SupiSupervisorHasEscala diaTurno, escalaSupervisor;
    private List<SupiSupervisorHasEscala> escalaMensalList;

    //lista de escalas do supervisor no mes (ESM)
    private List<SupiSupervisorHasEscala> listasEscalaSupervisor = new ArrayList();
    //escala mensal de supervisores (EMS)
    private List<Object> listasEscalaSupervisorMes = new ArrayList();
    
    private int mesanterior;

    /**
     * Creates a new instance of SupiEscalaSupervisor
     */
    public SupiEscalaSupervisorBean()
    {
    }
    
     public static SupiEscalaSupervisorBean getInstanciaBean()
    {
        return (SupiEscalaSupervisorBean) GeradorCodigo.getInstanciaBean("supiEscalaSupervisorBean");
    }
     
    public static SupiSupervisorHasEscala getInstancia () {
        
         SupiSupervisorHasEscala item = new SupiSupervisorHasEscala();
                SupiEscala escalaAux = new SupiEscala();
                escalaAux.setFkIdSeccao(new SupiSeccao());
                escalaAux.setFkIdTipoEscala(new SupiTipoEscala());
                item.setFkIdEscala(escalaAux);
                item.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());
                item.setFkIdTurno(new SupiTurno());
                
        return item;
    } 
    
    @PostConstruct
    public void postConstruct(){
        // inicializar o formulario com a data do mes anterior relativo a data corrente (ano, mês) x
        Calendar cal2 = Calendar.getInstance();
        mesanterior = cal2.get(Calendar.MONTH) ;
        System.out.println("mes anterior ahhahahah:" + mesanterior);
    }
    

    public int getMesanterior() {
        return mesanterior;
    }

    public void setMesanterior(int mesanterior) {
        this.mesanterior = mesanterior;
    }
    
    

    public void inicializarListasEscalaSupervisor()
    {
        if (supervisoresList != null)
        {
            for (RhFuncionario func : supervisoresList)
            {
                SupiSupervisorHasEscala item = new SupiSupervisorHasEscala();
                SupiEscala escalaAux = new SupiEscala();
                escalaAux.setFkIdSeccao(new SupiSeccao());
                escalaAux.setFkIdTipoEscala(new SupiTipoEscala());
                item.setFkIdEscala(escalaAux);
                item.setFkIdFuncionario(func);
                item.setFkIdTurno(new SupiTurno());
                listasEscalaSupervisor.add(item);
            }
        } else
        {
            listasEscalaSupervisor = new ArrayList<>();
        }

    }

    public SupiEscala getInstanciaEscala()
    {
        SupiEscala escala = new SupiEscala();
        escala.setFkIdSeccao(null);
        escala.setFkIdTipoEscala(new SupiTipoEscala());

        return escala;
    }

    public SupiEscala getEscala()
    {
        if (escala == null)
        {
            escala = getInstanciaEscala();
        }
        return escala;
    }

    public void setEscala(SupiEscala escala)
    {
        this.escala = escala;
    }

    public SupiEscala getEscalaPesquisa()
    {
        if (escalaPesquisa == null)
        {
            escalaPesquisa = getInstanciaEscala();
        }
        return escalaPesquisa;
    }

//    public void pesquisarEstagiarios()
//    {
//        listEstagiariosPesquisados = rhEstagiarioFacade.findEstagiario(pesquisaEstagiario); 
//
//        if (listEstagiariosPesquisados.isEmpty())
//        {
//            Mensagem.erroMsg("Nenhum estagiario encontrado para esta pesquisa");
//        }
//    }
    
    public void setEscalaPesquisa(SupiEscala escalaPesquisa)
    {
        this.escalaPesquisa = escalaPesquisa;
    }

    public List<SupiEscala> getEscalasPesquisadasList()
    {
        return escalasPesquisadasList;
    }

    public List<RhFuncionario> getSupervisoresList()
    {
        System.out.println("lista de supervisoresList do repeat dos supervisores" + supervisoresList);
        return supervisoresList;
    }
    
    public List<SupiTurno> getTurnoList()
    {
        System.out.println("lista de Turnos do repeat dos supervisores" + turnoList);
        return turnoList;
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

    public void pesquisarEscalas()
    {
        escalasPesquisadasList = supiEscalaFacade.findEscala(escalaPesquisa);

        if (escalasPesquisadasList.isEmpty())
        {
            Mensagem.erroMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }
    
    public List<SupiSeccao> listarSeccao() {
        return supiSeccaoFacade.findAll();
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

    public void alterarDiaTurno()
    {
        //Procurando o supervisor cujo dia de turno será alterado
        for (RhFuncionario supervisor : supervisoresList) //Encontrou o supervisor procurado
        {
            if (supervisor.equals(diaTurno.getFkIdFuncionario())) //Procura a presença a alterar
            {
                for (SupiSupervisorHasEscala dia : supervisor.getSupiSupervisorHasEscalaList())
                {
                    if (dia.getData() == diaTurno.getData())//Encontrou o dia de turno
                    {
                        //Altera a presenca e passa a ter o tipo de presença que foi modificado
                        if (diaTurno.getFkIdTurno().getPkIdTurno() == null)
                        {
                            dia.setFkIdTurno(new SupiTurno());
                        } else
                        {
                            dia.setFkIdTurno(supiTurnoFacade.find(diaTurno.getFkIdTurno().getPkIdTurno()));
                        }
                    }
                }
            }
        }
        setDiaTurno(null);
    }

    public String create()
    {
        try
        {
            userTransaction.begin();

            escala.setDataEscala(Calendar.getInstance().getTime());
            escala.setFkIdTipoEscala(supiTipoEscalaFacade.find(1));

            supiEscalaFacade.create(escala);

            for (RhFuncionario supervisor : supervisoresList)
            {
                for (SupiSupervisorHasEscala diaDeTurno : supervisor.getSupiSupervisorHasEscalaList())
                {
                    if (diaDeTurno.getFkIdTurno().getPkIdTurno() == null)
                    {
                        diaDeTurno.setFkIdTurno(null);
                    }
                    supiSupervisorHasEscalaFacade.create(diaDeTurno);
                }
            }

            userTransaction.commit();

            Mensagem.sucessoMsg("Escala guardada com sucesso!");
            limpar();
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public void actualizarDiasDoMesEscala()
    {
        if (escala == null)
        {
            return;
        }

        diasDoMesList = new ArrayList<>();

        for (int i = 1; i <= numeroDiasDoMes(escala.getAno(), escala.getMes()); i++)
        {
            diasDoMesList.add(i);
            System.out.println("criando dia " + i);
            getSupervisoresList();
//            for (RhFuncionario funcio : supervisoresList)
//            {
//                System.out.println("analisando funcionario "+funcio.getFkIdPessoa().getNome() +" no dia "+i);
//                System.out.println("criando vector de escala para ele.");
//                
//            }
        }

        prepararListaSupervisores();
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


    private void prepararListaSupervisores()
    {
        supervisoresList = null;
        List<RhFuncionario> funcionarios = new ArrayList<>();

        funcionarios = supiEscalaFacade.findEnfermeiros();

        //Para cada funcionário
        for (RhFuncionario superv : funcionarios)
        {
            List<SupiSupervisorHasEscala> listaDiasTurnos = new ArrayList<>();

            //Prepara uma lista de turnos com todos os dias do mês que irá se fazer o controlo
            //dos dias em que o supervisor será escalado
            for (int i = 1; i <= numeroDiasDoMes(escala.getAno(), escala.getMes()); i++)
            {
                try
                {
                    SupiSupervisorHasEscala diaTurno = new SupiSupervisorHasEscala();
                    diaTurno.setFkIdTurno(new SupiTurno());

                    //O i representa o número do dia
                    String dt = i + "-" + escala.getMes() + "-" + escala.getAno();
                    long dtLong = new SimpleDateFormat("dd-MM-yyyy").parse(dt).getTime();
                    Date data = new Date(dtLong);

                    //Indica que o turno feito nesta data por este supervisor
                    //pertence a esta escala
                    diaTurno.setFkIdEscala(escala);
                    diaTurno.setFkIdFuncionario(superv);
                    diaTurno.setData(data);

                    listaDiasTurnos.add(diaTurno);
                } catch (ParseException ex)
                {
                    System.out.println(ex);
                }
            }

            superv.setSupiSupervisorHasEscalaList(listaDiasTurnos);
        }
        supervisoresList = funcionarios;
    }

    public boolean habilitarBotaoTurno(SupiSupervisorHasEscala bt)
    {
        System.out.println("renderizar para " + bt.getFkIdTurno().getSigla());
        System.out.println("func " + bt.getFkIdFuncionario());
        System.out.println("dia " + bt.getData());
        for (int i = 0; i < escalaMensalList.size(); i++)
        {
            if (escalaMensalList.get(i) == bt)
            {
                if (i == 0)//se o item anterior eh o primeiro NAO DESABILITA
                {
                    return false;
                }

                if (escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("D")) // se o item anterior eh DIA entao desabilita
                {
                    if (i + 1 < escalaMensalList.size())
                    {
                        if (bt.getFkIdFuncionario() == escalaMensalList.get(i + 1).getFkIdFuncionario())
                        {
                            escalaMensalList.get(i).getFkIdTurno().setSigla("-");
                            return true;
                        }
                    }

                }

                if (escalaMensalList.get(i).getFkIdTurno().getSigla().equals("-"))//se o item actual ja tem valor "-" entao ja deve estar desabilitado
                {
                    if (escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("-") || escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("V") || escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("D"))
                    {
                        return true;
                    } else if (escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("M"))
                    {
                        escalaMensalList.get(i).getFkIdTurno().setSigla("----");
                        return false;
                    }
//                    
//                    habilitarBotaoTurno(escalaMensalList.get(i - 1));
////                    if (escalaMensalList.get(i - 1).getFkIdTurno().getDescricaoTurno().equals("M"))
////                        return false;
////                    else return true;
                }

                if (escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("V")) // se o item anterior eh VELA entao desabilita
                {
                    escalaMensalList.get(i).getFkIdTurno().setSigla("-");
                    if (i + 1 < escalaMensalList.size())
                    {
                        escalaMensalList.get(i + 1).getFkIdTurno().setSigla("-");
                    }
                    if (i + 2 < escalaMensalList.size())
                    {
                        escalaMensalList.get(i + 2).getFkIdTurno().setSigla("-");
                    }
                    return true;
                 }

                if (escalaMensalList.get(i - 1).getFkIdTurno().getSigla().equals("M")) // se o item anterior eh MANHA entao NAO DESABILITA
                {
                    escalaMensalList.get(i).getFkIdTurno().setSigla("----");
                    return false;
                }
            }
        }
        return false;
    }

    public void setEscalaSupervisor(SupiSupervisorHasEscala escalaSupervisor)
    {
        System.out.println("recebeu " + escalaSupervisor.getFkIdFuncionario().getFkIdPessoa().getNome());
        System.out.println("no dia " + escalaSupervisor.getData());
        this.escalaSupervisor = escalaSupervisor;
    }

    public String limpar()
    {
        escala = null;
        supervisoresList = null;

        //return "/faces/supiVisao/escala/cadastroEscalaSupervisor.xhtml?faces-redirect=true";
        return "/faces/supiVisao/escala/escalaTeste.xhtml?faces-redirect=true";
    }

    /**
     * @return the listasEscalaSupervisor
     */
    public List<SupiSupervisorHasEscala> getListasEscalaSupervisor()
    {
        System.out.println("lista Antes: " + listasEscalaSupervisor);
        if (listasEscalaSupervisor == null)
        {
            inicializarListasEscalaSupervisor();
        }
        System.out.println("lista Depois: " + listasEscalaSupervisor);
        return listasEscalaSupervisor;
    }

    /**
     * @param listasEscalaSupervisor the listasEscalaSupervisor to set
     */
    public void setListasEscalaSupervisor(List<SupiSupervisorHasEscala> listasEscalaSupervisor)
    {
        this.listasEscalaSupervisor = listasEscalaSupervisor;
    }

    public List<Object> getListasEscalaSupervisorMes()
    {
        return listasEscalaSupervisorMes;
    }

    public void setListasEscalaSupervisorMes(List<Object> listasEscalaSupervisorMes)
    {
        this.listasEscalaSupervisorMes = listasEscalaSupervisorMes;
    }
}
