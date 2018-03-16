/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import com.sun.faces.component.visit.FullVisitContext;
import entidade.RhFuncionario;
import entidade.RhSeccaoTrabalho;
import entidade.SupiEscala;
import entidade.SupiEscalaMedico;
import entidade.SupiMedicoHasEscala;
import entidade.SupiSeccao;
import entidade.SupiTurno;
import entidade.SupiTurnoMedico;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import sessao.RhFuncionarioFacade;
import sessao.RhSeccaoTrabalhoFacade;
import sessao.SupiEscalaFacade;
import sessao.SupiEscalaMedicoFacade;
import sessao.SupiMedicoHasEscalaFacade;
import sessao.SupiSupervisorHasEscalaFacade;
import sessao.SupiTurnoMedicoFacade;
import util.supi.EscalaModelo;
import util.Data;
import util.GeradorCodigo;
import util.Mensagem;
import util.supi.SupiEscalaAuxiliarUtil;
import util.supi.SupiEscalaMedicoAuxiliar;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiEscalaMedicoCadastrarBean implements Serializable
{
    
  
    /**
     * Creates a new instance of SupiEscalaMedicoCadastrarBean
     */

    @Resource
    private UserTransaction userTransaction;
    @EJB
    
    private SupiEscalaFacade supiEscalaFacade;
    @EJB
    private SupiMedicoHasEscalaFacade supiMedicoHasEscalaFacade;

    @EJB
    private SupiTurnoMedicoFacade supiTurnoMedicoFacade;

    @EJB
    private SupiSupervisorHasEscalaFacade supiSupervisorHasEscalaFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
     @EJB
    private SupiEscalaMedicoFacade supiEscalaMedicoFacade;
     @EJB
    private RhSeccaoTrabalhoFacade rhSeccaoTrabalhoFacade;
    
    

    private SupiEscalaMedico escala, escalaPesquisa;
    private RhSeccaoTrabalho rhSeccaoTrabalho;
    private List<Object> diasDoMesList;
    private List<RhFuncionario> funcionriosList, medicoList = new ArrayList();
    private List<SupiMedicoHasEscala> escalaMensalList;
    private SupiMedicoHasEscala escalaMedico, diaTurno;
    private SupiTurnoMedico supiTurnoMedico;
    private Calendar dataCorrente = Calendar.getInstance();
    private int ano;
    private int cont;
    private int desabilitados;
    private int mes;
    private int pkIdTurno;
    private int pkIdSeccao;
    private int pkIdTipoEscala;
    private int[] totalDiaD;
    private int[] totalDiaDA;
    private int[] totalDiaManha;
    private int[] totalDiaTarde;
    private int[] totalDiaVela;
    private int[] dia = new int[31];

    private String diaBlocked;
    private String turno = "";
    private String turno1;
    private String observacao;

    private double cargaHorariaMedico;

    private SupiTurno supiTurno;
    private EscalaModelo escalaModelo;
    private List<EscalaModelo> listaEscala;
    private List<EscalaModelo> listaEscalateste;

    private List<SupiEscalaMedicoAuxiliar> escalaMensal;
    private SupiEscalaMedicoAuxiliar escalaMensalSelecionado;

    public SupiEscalaMedicoCadastrarBean()
    {
        ano = dataCorrente.get(Calendar.YEAR);
    }

    public static SupiEscalaMedicoCadastrarBean getInstanciaBean()
    {
        return (SupiEscalaMedicoCadastrarBean) GeradorCodigo.getInstanciaBean("supiEscalaMedicoCadastrarBean");
    }

    public static SupiMedicoHasEscala getInstancia()
    {

        SupiMedicoHasEscala item = new SupiMedicoHasEscala();
        SupiEscala escalaAux = new SupiEscala();
        escalaAux.setFkIdSeccao(new SupiSeccao());
        //item.setFkIdEscala(escalaAux);
        item.setFkIdTurnoMedico(new SupiTurnoMedico());
        item.getFkIdTurnoMedico().setSigla("--");
        return item;
    }

    @PostConstruct
    public void postConstruct()
    {
        // inicializar o formulario com a data do mes actual relativo a data corrente (ano, mês) x
        Calendar cal = Calendar.getInstance();
        mes = cal.get(Calendar.MONTH) + 1;
       // System.out.println("mes ahhahahah:" + mes);

    }

    public List<SupiEscalaMedicoAuxiliar> getEscalaMensal()
    {
        return escalaMensal;
    }

    public void setEscalaMensal(List<SupiEscalaMedicoAuxiliar> escalaMensal)
    {
        this.escalaMensal = escalaMensal;
    }

    public String getObservacao()
    {
        return observacao;
    }

    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
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

    public double getCargaHoraria()
    {
        return cargaHorariaMedico;
    }

    public void setCargaHoraria(double cargaHorariaMedico)
    {
        this.cargaHorariaMedico = cargaHorariaMedico;
    }

    public int[] getTotalDiaD()
    {
        return totalDiaD;
    }

    public void setTotalDiaD(int[] totalDiaD)
    {
        this.totalDiaD = totalDiaD;
    }

    public int[] getTotalDiaDA()
    {
        return totalDiaDA;
    }

    public void setTotalDiaDA(int[] totalDiaDA)
    {
        this.totalDiaDA = totalDiaDA;
    }

    public int[] getTotalDiaManha()
    {
        return totalDiaManha;
    }

    public void setTotalDiaManha(int[] totalDiaManha)
    {
        this.totalDiaManha = totalDiaManha;
    }

    public int[] getTotalDiaTarde()
    {
        return totalDiaTarde;
    }

    public void setTotalDiaTarde(int[] totalDiaTarde)
    {
        this.totalDiaTarde = totalDiaTarde;
    }

    public int[] getTotalDiaVela()
    {
        return totalDiaVela;
    }

    public void setTotalDiaVela(int[] totalDiaVela)
    {
        this.totalDiaVela = totalDiaVela;
    }

    public SupiEscalaMedicoAuxiliar getEscalaMensalSelecionado()
    {
        return escalaMensalSelecionado;
    }

    public void setEscalaMensalSelecionado(SupiEscalaMedicoAuxiliar escalaMensalSelecionado)
    {
        this.escalaMensalSelecionado = escalaMensalSelecionado;
    }

    //listar os funcionarios sem escala do mes e ano selecionado
    private List<RhFuncionario> getFuncionariosSemEscalaMensal()
    {
        if (ano != 0 && mes != 0)
        {
            //System.out.println("Funcionario:" + supiEscalaFacade.findSupervisoresSemEscala(ano, mes));
            return supiEscalaFacade.findSupervisoresSemEscala(ano, mes);
        }
        return null;
    }

    public List<SupiEscalaMedicoAuxiliar> prepararEscalaMensalMedico()
    {
        escalaMensal = new ArrayList<>();
        //System.out.println("numeroDias(mes):" + numeroDias(mes));
        totalDiaD = new int[numeroDias(mes)];
        totalDiaDA = new int[numeroDias(mes)];
        totalDiaManha = new int[numeroDias(mes)];
        totalDiaTarde = new int[numeroDias(mes)];
        totalDiaVela = new int[numeroDias(mes)];
        for (int i = 0; i < getMedicos().size(); i++)
        {
            SupiEscalaMedicoAuxiliar novaEscala = new SupiEscalaMedicoAuxiliar();
            novaEscala.setMedico(getMedicos().get(i));// .setFuncionario(getMedicos().get(i));
            novaEscala.setCargaHorariaMedico(cargaHorariaMedico);

            escalaMensal.add(novaEscala);
            //list.add(new Car(getFuncionrios().get(i), turno, ano));
        }

        return escalaMensal;
    }

    public void updateEscalaMensalSupervisor(SupiEscalaAuxiliarUtil novaEscala)
    {
        // List<Car> list = new ArrayList<Car>();
        //escalaMensal = new ArrayList<>();

        /*for (int i = 0; i < getFuncionarios().size(); i++) {
         if (novaEscala.getFuncionario().getFkIdPessoa().getPkIdPessoa() == getFuncionarios().get(i).getFkIdPessoa().getPkIdPessoa()) {
         for (int j = 0; j < numeroDias(mes); j++) {
         if (novaEscala.getTurnos()[j] != null) {

         //calcular o total da carga horaria
         cargaHoraria = cargaHoraria + supiTurnoFacade.find(novaEscala.getTurnos()[j]).getCargaHoraria();
         //actualizar os totais dos dia feitos
         System.out.println("j: " + j + " busca: " + buscaSiglaTurno(novaEscala.getTurnos()[j]));

                       
         switch (buscaSiglaTurno(novaEscala.getTurnos()[j])) {
         case "D":
         totalDiaD[j] = totalDiaD[j] + 1;
         case "DA":
         totalDiaDA[j] = totalDiaDA[j] + 1;
         case "M":
         totalDiaManha[j] = totalDiaManha[j] + 1;
         case "T":
         totalDiaTarde[j] = totalDiaTarde[j] + 1;
         case "V":
         totalDiaVela[j] = totalDiaVela[j] + 1;
         }
                        
         }

         }

         novaEscala.setFuncionario(getFuncionarios().get(i));
         novaEscala.setTurnos(escalaMensal.get(i).getTurnos());
         novaEscala.setCargaHoraria(cargaHoraria);
         escalaMensal.add(i, novaEscala);

         //renicialiar as variaveis
         cargaHoraria = 0;
         }

         //list.add(new Car(getFuncionrios().get(i), turno, ano));
         }
         */
    }

    public void inicializarTotalDias()
    {
        for (int i = 0; i < numeroDias(mes); i++)
        {
            totalDiaD[i] = 0;
            totalDiaDA[i] = 0;
        }

        cargaHorariaMedico = 0;
    }

    public String salvaEscala() throws ParseException
    {
        try
        {
             List<SupiEscalaMedico> listaux = supiEscalaMedicoFacade.findAll();
            
            int flag = 0;
            int month = Integer.parseInt(this.getMonth(new Date()));
            if (this.mes < month) {
                Mensagem.erroMsg("Não é possível marcar uma escala no mês selecionado!");
                return "";
            }

            for (SupiEscalaMedico item : listaux) {

//                if (item.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho()== this.pkIdSeccao) {
//
//                    
//
//                }

            }

            if (flag != 0) {
                Mensagem.erroMsg("Essa escala já foi marcada! Seleciona outra por favor.");
                return "";
            }
            
            
            userTransaction.begin();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ano = dataCorrente.get(Calendar.YEAR);
//        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        String data1 = "" + ano + "/" + "" + mes + "/" + "1";
//        Date dateEscala = (Date) formatter.parse(data1);

            //salvar a escala
            SupiEscalaMedico escalas = new SupiEscalaMedico();
            //escalas.setPkIdEscala(0);
            escalas.setAno(ano);
            escalas.setMes(mes);
            escalas.setDataEscala(new Date());
//            escalas.setFkIdSeccao(new SupiSeccao(pkIdSeccao));
//            escalas.setFkIdTipoEscala(new SupiTipoEscala(pkIdTipoEscala));
            supiEscalaMedicoFacade.create(escalas);
            for (int i = 0; i < escalaMensal.size(); i++)
            {

                for (int j = 0; j < numeroDias(mes); j++)
                {//j é o dia selecionado

                    //DateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");
                    String data2 = ano + "/" + mes + "/" + (j + 1);
                    Date dateEscalaTurno = new SimpleDateFormat("yyyy/MM/dd").parse(data2);

                    SupiMedicoHasEscala supiMedicoHasEscala = new SupiMedicoHasEscala();
                    supiMedicoHasEscala.setFkIdEscalaMedico(escalas);
                    supiMedicoHasEscala.setFkIdMedico(escalaMensal.get(i).getMedico());
                    supiMedicoHasEscala.setData(dateEscalaTurno);
                    if (escalaMensal.get(i).getTurnos()[j] != null)
                    {
                        supiMedicoHasEscala.setFkIdTurnoMedico(new SupiTurnoMedico(escalaMensal.get(i).getTurnos()[j]));
                    } else
                    {
                        supiMedicoHasEscala.setFkIdTurnoMedico(null);
                    }

                    supiMedicoHasEscalaFacade.create(supiMedicoHasEscala);
                    // System.out.println("valor:" + escalaMensal.get(i).getTurnos()[j]);
                }
            }

           // escalas = new SupiEscala();
            userTransaction.commit();

            ano = dataCorrente.get(Calendar.YEAR);
            escala = new SupiEscalaMedico();
            escalaMensal = new ArrayList<>();
            facesContext.addMessage(null, new FacesMessage("Escala gravada com sucesso"));
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro!");
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        return "";

    }

    public void setChangedSOI(Integer turno, int dia, int linha)
    {
        System.out.println("linhaSelecionada:" + linha);
        if (turno != null)
        {
            switch (buscaSiglaTurno(turno))
            {

                case "DN":
                    cargaHorariaMedico = /*cargaHorariaMedico*/escalaMensal.get(linha).getCargaHorariaMedico()+ supiTurnoMedicoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaD[dia - 1] = totalDiaD[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHorariaMedico(cargaHorariaMedico);

//                    UIComponent component = findComponent("dia" + (dia + 1));
//                    if (component != null) {
//                        HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
//                        sel.setValue(null);
//                        sel.setDisabled(true);
//                    }
                    break;

                case "V":
                    cargaHorariaMedico =/*cargaHorariaMedico*/escalaMensal.get(linha).getCargaHorariaMedico()+ supiTurnoMedicoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaVela[dia - 1] = totalDiaVela[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHorariaMedico(cargaHorariaMedico);
//                    UIComponent component1 = findComponent("dia" + (dia + 1));
//                    UIComponent component2 = findComponent("dia" + (dia + 2));
//                    UIComponent component3 = findComponent("dia" + (dia + 3));
//                    if (component1 != null && component2 != null && component3 != null) {
//                        HtmlSelectOneMenu sel1 = (HtmlSelectOneMenu) component1;
//                        sel1.setValue(null);
//                        sel1.setDisabled(true);
//
//                        HtmlSelectOneMenu sel2 = (HtmlSelectOneMenu) component2;
//                        sel2.setValue(null);
//                        sel2.setDisabled(true);
//
//                        HtmlSelectOneMenu sel3 = (HtmlSelectOneMenu) component3;
//                        sel3.setValue(null);
//                        sel3.setDisabled(true);
//                    }
                    break;

//                case "DF":
//                    cargaHorariaMedico = cargaHorariaMedico + supiTurnoMedicoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    totalDiaD[dia - 1] = totalDiaD[dia - 1] + 1;
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHorariaMedico(cargaHorariaMedico);
//
//                    UIComponent component = findComponent("dia" + (dia));
//                    if (component != null)
//                    {
//                        HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
//                        sel.setValue(null);
//                        sel.setDisabled(true);
//                    }
//                    break;
                default:
                    cargaHorariaMedico =/*cargaHorariaMedico*/escalaMensal.get(linha).getCargaHorariaMedico()+ supiTurnoMedicoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHorariaMedico(cargaHorariaMedico);

                    break;

            }

            /* for (int j = 0; j < numeroDias(mes); j++) {
             System.out.println("turnosss:"+escalaMensal.get(linha).getTurnos()[j]);
             cargaHoraria = cargaHoraria + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[j]).getCargaHoraria();
             }
             escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
             */
        }
        /*else {

         UIComponent component = findComponent("dia" + (dia + 1));
         if (component != null) {
         HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
         sel.setValue(null);
         sel.setDisabled(false);
         }
         }
         */
    }

    public UIComponent findComponent(final String id)
    {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback()
        {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component)
            {
                if (component.getId().equals(id))
                {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }

    public String getDiaBlocked()
    {
        if (numeroDias(mes) != 0)
        {
            if (numeroDias(mes) == 28)
            {
                diaBlocked = "dia1 dia2 dia3 dia4 dia5 dia6 dia7 dia8 dia9 dia10 dia11 dia12 dia13 dia14 dia15 dia16 dia17 dia18 dia19 dia20 dia21 dia22 dia23 dia24 dia25 dia26 dia27 dia28";
            } else if (numeroDias(mes) == 29)
            {
                diaBlocked = "dia1 dia2 dia3 dia4 dia5 dia6 dia7 dia8 dia9 dia10 dia11 dia12 dia13 dia14 dia15 dia16 dia17 dia18 dia19 dia20 dia21 dia22 dia23 dia24 dia25 dia26 dia27 dia28 dia29";
            } else if (numeroDias(mes) == 30)
            {
                diaBlocked = "dia1 dia2 dia3 dia4 dia5 dia6 dia7 dia8 dia9 dia10 dia11 dia12 dia13 dia14 dia15 dia16 dia17 dia18 dia19 dia20 dia21 dia22 dia23 dia24 dia25 dia26 dia27 dia28 dia29 dia30";

            } else if (numeroDias(mes) == 31)
            {
                diaBlocked = "dia1 dia2 dia3 dia4 dia5 dia6 dia7 dia8 dia9 dia10 dia11 dia12 dia13 dia14 dia15 dia16 dia17 dia18 dia19 dia20 dia21 dia22 dia23 dia24 dia25 dia26 dia27 dia28 dia29 dia30 dia31";

            }
        }
        return diaBlocked;
    }

    public void setDiaBlocked(String diaBlocked)
    {
        this.diaBlocked = diaBlocked;
    }

    public String diaSemana(int dia)
    {
        Locale locale = new Locale("pt", "BR");
        SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd", locale);
        sdfEntrada.setLenient(false);
        Date date;
        try
        {
            int anoActual = dataCorrente.get(Calendar.YEAR);

            String data = anoActual + "-" + mes + "-" + dia;
            date = sdfEntrada.parse(data);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
            switch (diaSemana)
            {
                case 1:
                    return "Dom";
                case 2:
                    return "Seg";
                case 3:
                    return "Ter";
                case 4:
                    return "Qua";
                case 5:
                    return "Qui";
                case 6:
                    return "Sex";
                default:
                    return "Sab";
            }
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String buscaSiglaTurno(Integer turno)
    {
        System.out.println("turno medico recebido para sigla: " + turno);
        if (turno != null && turno != 0)
        {
            return supiTurnoMedicoFacade.find(turno).getSigla();
        }
        System.out.println("eh null");
        return "";
    }

    public void onRowEdit(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Escala Editada", ((SupiEscalaAuxiliarUtil) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Edição Cancelada", ((SupiEscalaAuxiliarUtil) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event)
    {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //listar todos os supervisores
    private List<RhFuncionario> getFuncionarios()
    {
        //System.out.println("supiEscalaFacade.findEnfermeiros():" + supiEscalaFacade.findEnfermeiros());
        return supiEscalaFacade.findEnfermeiros();
    }
    
    private List<RhFuncionario> getMedicos()
    {
        //System.out.println("supiEscalaFacade.findEnfermeiros():" + supiEscalaFacade.findEnfermeiros());
        return supiEscalaFacade.findMedicos();
    }
 
    public List<EscalaModelo> getListaEscalateste()
    {
        return listaEscalateste;
    }

    public void setListaEscalateste(List<EscalaModelo> listaEscalateste)
    {
        this.listaEscalateste = listaEscalateste;
    }

    public List<EscalaModelo> getListaEscala()
    {
        return listaEscala;
    }

    public void setListaEscala(List<EscalaModelo> listaEscala)
    {
        this.listaEscala = listaEscala;
    }

    public EscalaModelo getEscalaModelo()
    {
        return escalaModelo;
    }

    public void setEscalaModelo(EscalaModelo escalaModelo)
    {
        this.escalaModelo = escalaModelo;
    }

    public SupiTurnoMedico getSupiTurno()
    {
        return supiTurnoMedico;
    }

    public void setSupiTurno(SupiTurnoMedico supiTurnoMedico)
    {
        this.supiTurnoMedico = supiTurnoMedico;
    }

    public String getTurno()
    {
        return turno;
    }

    public void setTurno(String turno)
    {
        this.turno = turno;
    }

    public int getPkIdTurno()
    {
        return pkIdTurno;
    }

    public String getTurno1()
    {
        return turno1;
    }

    public void setTurno1(String turno1)
    {
        this.turno1 = turno1;
    }

    public void setPkIdTurno(int pkIdTurno)
    {
        this.pkIdTurno = pkIdTurno;
    }

    public String getMesString()
    {
        if (mes > 0)
        {
            return Data.getMes(mes);
        }
        return "Janeiro";
    }

    public int[] getDia()
    {
        return dia;
    }

    public void setDia(int[] dia)
    {
        this.dia = dia;
    }

    public int getMes()
    {
        return mes;
    }

    public void setMes(int mes)
    {
        this.mes = mes;
    }

    public int getAno()
    {
        return ano;
    }

    public void setAno(int ano)
    {
        this.ano = ano;
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

    private void prepararListaSupervisores()
    {
        funcionriosList = supiEscalaFacade.findEnfermeiros();
    }

    public SupiMedicoHasEscala getDiaTurno()
    {
        if (diaTurno == null)
        {
            diaTurno = new SupiMedicoHasEscala();
            diaTurno.setFkIdTurnoMedico(new SupiTurnoMedico());
        }
        return diaTurno;
    }

    public void setDiaTurno(SupiMedicoHasEscala diaTurno)
    {
        this.diaTurno = diaTurno;
    }

    public boolean habilitarBotaoTurno(RhFuncionario bt)
    {
        if (desabilitados <= cont && turno.equals("V"))
        {
            desabilitados += 1;
            return true;
        } else if (cont == 1 && desabilitados <= 1)
        {
            desabilitados += 1;
            return true;
        }
        return false;
    }

    public boolean habilitarBotaoTurno1(EscalaModelo bt)
    {

        if (desabilitados <= cont && turno.equals("V"))
        {
            desabilitados += 1;
            return true;
        } else if (cont == 1 && desabilitados <= 1)
        {
            desabilitados += 1;
            return true;
        }
        return false;
    }

    public void definirTurno(RhFuncionario escalaSup)
    {
        turno = "V";

        if (turno.equals("V"))
        {
            cont = 13;
            desabilitados = 0;
        } else if (turno.equals("D"))
        {
            cont = 1;
            desabilitados = 0;
        }
    }

    public void definirTurno1(EscalaModelo escalaSup)
    {
        turno = "V";

        if (turno.equals("V"))
        {
            cont = 13;
            desabilitados = 0;
        } else if (turno.equals("D"))
        {
            cont = 1;
            desabilitados = 0;
        }
    }

    public void prepararEscalaMensal()
    {
//        actualizarDiasDoMesEscala();
        prepararListaSupervisores();

        escalaMensalList = new ArrayList<>();
        listaEscala = new ArrayList<>();
        for (RhFuncionario func : funcionriosList)
        {
            EscalaModelo modelo = new EscalaModelo();
            SupiMedicoHasEscala item = getInstancia();
            item.setFkIdMedico(func);

            /*            modelo.setPk_Funcionarios(func.getPkIdFuncionario());
             modelo.setFuncionario(func);
             modelo.setTurno(item.getFkIdTurno());
             item.setData(dataCorrente.getTime());
             */
            escalaMensalList.add(item);
            listaEscala.add(modelo);
        }
    }

    public void actualizarDiasDoMesEscala()
    {
        if (escala == null)
        {
            return;
        }

        diasDoMesList = new ArrayList<>();

        for (int i = 1; i <= numeroDiasDoMes(); i++)
        {
            diasDoMesList.add(i);
        }

        prepararEscalaMensal();

        // System.out.println("escala");
    }

    public int numeroDiasDoMes()
    {
        int anoCorrente = dataCorrente.get(Calendar.YEAR);

        if (mes > 12 || mes < 1)
        {
            return 0;
        }

        if (mes == 2)
        {
            return (anoCorrente % 400 == 0) || (anoCorrente % 4 == 0 && anoCorrente % 100 != 0) ? 29 : 28;

        }

        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 10 || mes == 12)
        {
            return 31;
        }

        return 30;
    }

    public int numeroDias(int mesParametro)
    {
        int anoCorrente;

        anoCorrente = dataCorrente.get(Calendar.YEAR);

        if (mesParametro > 12 || mesParametro < 1)
        {
            return 0;
        }

        if (mesParametro == 2)
        {
            return (anoCorrente % 400 == 0) || (anoCorrente % 4 == 0 && anoCorrente % 100 != 0) ? 29 : 28;
        }

        if (mesParametro == 1 || mesParametro == 3 || mesParametro == 5 || mesParametro == 7 || mesParametro == 10 || mesParametro == 12)
        {
            return 31;
        }

        return 30;
    }

    public SupiEscalaMedico getEscala()
    {
        if (escala == null)
        {
            escala = getInstanciaEscala();
        }
        return escala;
    }

    public void setEscala(SupiEscalaMedico escala)
    {
        this.escala = escala;
    }

    public SupiEscalaMedico getInstanciaEscala()
    {
        SupiEscalaMedico escala = new SupiEscalaMedico();
        escala.setFkIdSeccaoTrabalho(null);
        

        return escala;
    }

    public List<RhFuncionario> getMedicoList()
    {
        // System.out.println("lista de supervisoresList do repeat dos supervisores" + supervisoresList);
        return medicoList;
    }

    /**
     * @return the diasDoMesList
     */
    public List<Object> getDiasDoMesList()
    {
        if (diasDoMesList == null)
        {
            diasDoMesList = new ArrayList<>();
        }
        return diasDoMesList;
    }

    /**
     * @param diasDoMesList the diasDoMesList to set
     */
    public void setDiasDoMesList(List<Object> diasDoMesList)
    {
        this.diasDoMesList = diasDoMesList;
    }

    /**
     * M
     *
     * @return the funcionriosList
     */
    public List<RhFuncionario> getFuncionriosList()
    {
        if (funcionriosList == null)
        {
            funcionriosList = new ArrayList<>();
        }
        return funcionriosList;
    }

    /**
     * @param funcionriosList the funcionriosList to set
     */
    public void setFuncionriosList(List<RhFuncionario> funcionriosList)
    {
        this.funcionriosList = funcionriosList;
    }

    /**
     * @return the escalaMensalList
     */
    public List<SupiMedicoHasEscala> getEscalaMensalList()
    {
        if (escalaMensalList == null)
        {
            escalaMensalList = new ArrayList<>();
        }
        return escalaMensalList;
    }

    public String limpar()
    {
        escala = null;
        medicoList = null;

        return "/faces/supiVisao/escala/escalaNovoMedicoSupi.xhtml?faces-redirect=true";
    }

    public void alterarDiaTurno()
    {
        //Procurando o supervisor cujo dia de turno será alterado
//        for (RhFuncionario medico : medicoList) //Encontrou o supervisor procurado
//        {
//            if (medico.equals(diaTurno.getFkIdMedico())) //Procura a presença a alterar
//            {
//                for (SupiMedicoHasEscala dia: medico.getSupiMedicoHasEscalaList())
//                {
//                    if (dia.getData() == diaTurno.getData())//Encontrou o dia de turno
//                    {
//                        //Altera a presenca e passa a ter o tipo de presença que foi modificado
//                        if (diaTurno.getFkIdTurnoMedico().getPkIdTurnoMedico() == null)
//                        {
//                            dia.setFkIdTurnoMedico(new SupiTurnoMedico());
//                        } else
//                        {
//                            dia.setFkIdTurnoMedico(supiTurnoMedicoFacade.find(diaTurno.getFkIdTurnoMedico().getPkIdTurnoMedico()));
//                        }
//                    }
//                }
//            }
//        }
        if (diaTurno.getFkIdTurnoMedico().getPkIdTurnoMedico() == null)
        {
            diaTurno.setFkIdTurnoMedico(new SupiTurnoMedico());
        } else
        {
            diaTurno.setFkIdTurnoMedico(supiTurnoMedicoFacade.find(diaTurno.getFkIdTurnoMedico().getPkIdTurnoMedico()));
        }
        setDiaTurno(null);
    }

    public String create()
    {
        try
        {
            userTransaction.begin();

            escala.setDataEscala(Calendar.getInstance().getTime());
            // escala.setFkIdTipoEscala(supiTipoEscalaFacade.find(1));

            supiEscalaMedicoFacade.create(escala);

            for (RhFuncionario medico : medicoList)
            {
                for (SupiMedicoHasEscala diaDeTurno : medico.getSupiMedicoHasEscalaList())
                {
                    if (diaDeTurno.getFkIdTurnoMedico().getPkIdTurnoMedico() == null)
                    {
                        diaDeTurno.setFkIdTurnoMedico(null);
                    }
                    supiMedicoHasEscalaFacade.create(diaDeTurno);
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

    /**
     * @param escalaMensalList the escalaMensalList to set
     */
    public void setEscalaMensalList(List<SupiMedicoHasEscala> escalaMensalList)
    {
        this.escalaMensalList = escalaMensalList;
    }

    public SupiMedicoHasEscala getEscalaMedico()
    {
        if (escalaMedico == null)
        {
            escalaMedico = getInstancia();
        }

        return escalaMedico;
    }

    /*public String contadorTurnos(){
       
     SupiTurno turnoConta = new SupiTurno();
     for(int i = 1;i< turnoConta.getDescricaoTurno();i++){
            
        
     }
    
     }*/
    public void setEscalaMedico(SupiMedicoHasEscala escalaMedico)
    {
        //System.out.println("recebeu " + escalaSupervisor.getFkIdFuncionario().getFkIdPessoa().getNome());
        //System.out.println("no dia " + escalaSupervisor.getData());
        this.escalaMedico = escalaMedico;
    }

    public List<RhFuncionario> getFuncionrios()
    {
        return rhFuncionarioFacade.findAll();
    }
    
    public List<RhSeccaoTrabalho> findSeccaoTrabalho()
    {
        return rhSeccaoTrabalhoFacade.findAll();
    }
    
    // Implementação do exemplo do site com CellEditor

    /*public void onRowEdit(RowEditEvent event)
     {
     FacesMessage msg = new FacesMessage("Turno Editado", ((SupiTurno) event.getObject()).getDescricao());
     FacesContext.getCurrentInstance().addMessage(null, msg);
     }

     public void onRowCancel(RowEditEvent event)
     {
     FacesMessage msg = new FacesMessage("Turno Cancelado", ((SupiTurno) event.getObject()).getDescricao());
     FacesContext.getCurrentInstance().addMessage(null, msg);
     }
     */
    public String getMonth(Date data) {

        DateFormat dateFormat = new SimpleDateFormat("MM");

        return dateFormat.format(data);
    }
}
