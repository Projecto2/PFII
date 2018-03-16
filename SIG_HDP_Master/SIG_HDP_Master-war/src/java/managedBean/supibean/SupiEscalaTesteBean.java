package managedBean.supibean;

import com.sun.faces.component.visit.FullVisitContext;
import util.supi.MapaEscala;
import entidade.RhFuncionario;
import entidade.SupiEscala;
import entidade.SupiSeccao;
import entidade.SupiSupervisorHasEscala;
import entidade.SupiTipoEscala;
import entidade.SupiTurno;
import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import sessao.SupiEscalaFacade;
import sessao.SupiSeccaoFacade;
import sessao.SupiSupervisorHasEscalaFacade;
import sessao.SupiTipoEscalaFacade;
import sessao.SupiTurnoFacade;
import util.supi.EscalaModelo;
import util.Data;
import util.GeradorCodigo;
import util.Mensagem;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;
import util.supi.SupiEscalaAuxiliarUtil;

/**
 *
 * @author helga
 */
@ManagedBean
@ViewScoped
public class SupiEscalaTesteBean implements Serializable
{

    /**
     * Creates a new instance of SupiEscalaTesteBean
     */
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

    private SupiEscala escala, escalaPesquisa;
    private List<Object> diasDoMesList;
    private List<RhFuncionario> funcionriosList, supervisoresList = new ArrayList();
    private List<SupiSupervisorHasEscala> escalaMensalList;
    private SupiSupervisorHasEscala escalaSupervisor, diaTurno;
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

    private double cargaHoraria;

    private double cargaH;
    private Date dataInicio, dataFim;

    private SupiTurno supiTurno;
    private EscalaModelo escalaModelo;
    private List<EscalaModelo> listaEscala;
    private List<EscalaModelo> listaEscalateste;

    private List<SupiEscalaAuxiliarUtil> escalaMensal;
    private SupiEscalaAuxiliarUtil escalaMensalSelecionado;

    public SupiEscalaTesteBean()
    {
        supiTurno = new SupiTurno();
        escalaModelo = new EscalaModelo();
        listaEscalateste = new ArrayList<>();
        escalaMensal = new ArrayList<SupiEscalaAuxiliarUtil>();
        escala = new SupiEscala();
        ano = dataCorrente.get(Calendar.YEAR);

    }

    public double getCargaH()
    {
        return cargaH;
    }

    public void setCargaH(double cargaH)
    {
        this.cargaH = cargaH;
    }

    public static SupiEscalaTesteBean getInstanciaBean()
    {
        return (SupiEscalaTesteBean) GeradorCodigo.getInstanciaBean("supiEscalaTesteBean");
    }

    public static SupiSupervisorHasEscala getInstancia()
    {
        SupiSupervisorHasEscala item = new SupiSupervisorHasEscala();
        SupiEscala escalaAux = new SupiEscala();
        escalaAux.setFkIdSeccao(new SupiSeccao());
        escalaAux.setFkIdTipoEscala(new SupiTipoEscala());
        item.setFkIdEscala(escalaAux);
        item.setFkIdTurno(new SupiTurno());
        item.getFkIdTurno().setSigla("--");
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

    public List<SupiEscalaAuxiliarUtil> getEscalaMensal()
    {
        return escalaMensal;
    }

    public void setEscalaMensal(List<SupiEscalaAuxiliarUtil> escalaMensal)
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
        return cargaHoraria;
    }

    public void setCargaHoraria(double cargaHoraria)
    {
        this.cargaHoraria = cargaHoraria;
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

    public SupiEscalaAuxiliarUtil getEscalaMensalSelecionado()
    {
        return escalaMensalSelecionado;
    }

    public void setEscalaMensalSelecionado(SupiEscalaAuxiliarUtil escalaMensalSelecionado)
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

    public List<SupiEscalaAuxiliarUtil> prepararEscalaMensalSupervisor()
    {
        escalaMensal = new ArrayList<>();
        //System.out.println("numeroDias(mes):" + numeroDias(mes));
        totalDiaD = new int[numeroDias(mes)];
        totalDiaDA = new int[numeroDias(mes)];
        totalDiaManha = new int[numeroDias(mes)];
        totalDiaTarde = new int[numeroDias(mes)];
        totalDiaVela = new int[numeroDias(mes)];
        for (int i = 0; i < getFuncionarios().size(); i++)
        {
            SupiEscalaAuxiliarUtil novaEscala = new SupiEscalaAuxiliarUtil();
            novaEscala.setFuncionario(getFuncionarios().get(i));
            novaEscala.setCargaHoraria(cargaHoraria);

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

        cargaHoraria = 0;
    }

    public String salvaEscala() throws ParseException
    {
        try
        {
            
             List<SupiEscala> listaux = supiEscalaFacade.findAll();
            
            int flag = 0;
            int month = Integer.parseInt(this.getMonth(new Date()));
            if (this.mes < month) {
                Mensagem.erroMsg("Não é possível marcar uma escala no mês selecionado!");
                return "";
            }

            for (SupiEscala item : listaux) {

                if (item.getFkIdSeccao().getPkIdSeccao() == this.pkIdSeccao) {

                    if (item.getMes() == this.mes) {

                        if (item.getFkIdTipoEscala().getPkIdTipoEscala() == this.pkIdTipoEscala) {

                            flag++;

                        }
                    }

                }

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

            escala = new SupiEscala();

            //escalas.setPkIdEscala(0);
            escala.setAno(ano);
            escala.setMes(mes);
            escala.setDataEscala(new Date());
            escala.setFkIdSeccao(new SupiSeccao(pkIdSeccao));
            escala.setFkIdTipoEscala(new SupiTipoEscala(pkIdTipoEscala));

            //salvar a escala
            supiEscalaFacade.create(escala);

//        for (int i = 0; i < /*getFuncionarios().size()*/ 1; i++) {
            for (int i = 0; i < escalaMensal.size(); i++)
            {

                for (int j = 0; j < numeroDias(mes); j++)
                {//j é o dia selecionado

                    String data2 = ano + "/" + mes + "/" + (j + 1);
                    Date dateEscalaTurno = new SimpleDateFormat("yyyy/MM/dd").parse(data2);

                    SupiSupervisorHasEscala supiSupervisorHasEscala = new SupiSupervisorHasEscala();
                    supiSupervisorHasEscala.setFkIdEscala(escala);
                    supiSupervisorHasEscala.setFkIdFuncionario(escalaMensal.get(i).getFuncionario());
                    supiSupervisorHasEscala.setObservacao(null);
                    supiSupervisorHasEscala.setData(dateEscalaTurno);

                    if (escalaMensal.get(i).getTurnos()[j] != null)
                    {
                        supiSupervisorHasEscala.setFkIdTurno(new SupiTurno(escalaMensal.get(i).getTurnos()[j]));
                    } else
                    {
                        supiSupervisorHasEscala.setFkIdTurno(null);
                    }

                    supiSupervisorHasEscalaFacade.create(supiSupervisorHasEscala);
                    // System.out.println("valor:" + escalaMensal.get(i).getTurnos()[j]);
                }

            }

            userTransaction.commit();

            ano = dataCorrente.get(Calendar.YEAR);
            escala = new SupiEscala();
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

    public boolean verificaFuncionario(List<MapaEscala> lista, RhFuncionario funcionario)
    {
        for (MapaEscala fm : lista)
        {
            if (Objects.equals(fm.getFuncionario().getCodigoFuncionario(), funcionario.getCodigoFuncionario()))
            {
                return true;
            }
        }
        return false;
    }

    public int verificaFuncionarioRetornaIndexOf(List<MapaEscala> listaFalta, RhFuncionario funcionario)
    {
        for (int i = 0; i < listaFalta.size(); i++)
        {
            if (Objects.equals(listaFalta.get(i).getFuncionario().getCodigoFuncionario(), funcionario.getPkIdFuncionario()))
            {
                return i;
            }
        }
        return -1;
    }

//    public List<MapaEscala> imprimirEscala()
//    {
//        Data_1 data = new Data_1();
//
//        List<MapaEscala> listaM = new ArrayList<>();
//        List<SupiSupervisorHasEscala> lista = supiSupervisorHasEscalaFacade.findAll();
//        for (int i = 0; i < lista.size(); i++)
//        {
//            if (lista.get(i).getFkIdEscala().getMes() == 1)
//            {
//                if (!verificaFuncionario(listaM, lista.get(i).getFkIdFuncionario()))
//                {
//
//                    MapaEscala mapa = new MapaEscala();
//                    mapa.setFuncionario(lista.get(i).getFkIdFuncionario());
//                    mapa.setNome(lista.get(i).getFkIdFuncionario().getFkIdPessoa().getNome());
//
//                    System.out.println("DIAAA: " + data.retornaDia(lista.get(i).getData()));
//                    if (data.retornaDia(lista.get(i).getData()) == 1)
//                    {
//                        mapa.setDia1(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 2)
//                    {
//                        mapa.setDia2(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 3)
//                    {
//                        mapa.setDia3(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 4)
//                    {
//                        mapa.setDia4(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 5)
//                    {
//                        mapa.setDia5(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 6)
//                    {
//                        mapa.setDia6(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 7)
//                    {
//                        mapa.setDia7(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 8)
//                    {
//                        mapa.setDia8(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 9)
//                    {
//                        mapa.setDia9(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 10)
//                    {
//                        mapa.setDia10(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 11)
//                    {
//                        mapa.setDia11(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 12)
//                    {
//                        mapa.setDia12(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    listaM.add(mapa);
//                } else if (verificaFuncionarioRetornaIndexOf(listaM, lista.get(i).getFkIdFuncionario()) >= 0)
//                {
//
//                    int posicao = verificaFuncionarioRetornaIndexOf(listaM, lista.get(i).getFkIdFuncionario());
//                    MapaEscala mapa1;
//
//                    mapa1 = listaM.get(posicao);
//                    System.out.println("DIAAA 2: " + data.retornaDia(lista.get(i).getData()));
//                    mapa1.setFuncionario(lista.get(i).getFkIdFuncionario());
//                    if (data.retornaDia(lista.get(i).getData()) == 1)
//                    {
//                        mapa1.setDia1(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 2)
//                    {
//                        mapa1.setDia2(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 3)
//                    {
//                        mapa1.setDia3(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 4)
//                    {
//                        mapa1.setDia4(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 5)
//                    {
//                        mapa1.setDia5(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 6)
//                    {
//                        mapa1.setDia6(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 7)
//                    {
//                        mapa1.setDia7(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 8)
//                    {
//                        mapa1.setDia8(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 9)
//                    {
//                        mapa1.setDia9(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 10)
//                    {
//                        mapa1.setDia10(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 11)
//                    {
//                        mapa1.setDia11(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    if (data.retornaDia(lista.get(i).getData()) == 12)
//                    {
//                        mapa1.setDia12(lista.get(i).getFkIdTurno().getSigla());
//                    }
//                    listaM.add(posicao, mapa1);
//                }
//            }
//        }
//
//        return listaM;
//    }
    
    public void imprimirMapaEscala()
   {
      ConexaoPostgresSQL conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      HashMap<String, Object> parametrosMap = new HashMap<>();
      parametrosMap.put("REPORT_CONNECTION", conn);
      RelatorioJasper.exportPDF("supi/relatorioEscala.jasper", parametrosMap,escalaMensalList);
      dataInicio = null;
   } 

//    public void imprimirMapaEscala()
//    {
//
//        try
//        {
//            String path = "";
//            String reportPath = "/WEB-INF/relatorios/supi/relatorioEscala.jasper";
//            List<MapaEscala> listaDados = imprimirEscala();
//            
//             Relatorio.geraRelatorio(listaDados, reportPath);
//            /*FacesContext facesContext = FacesContext.getCurrentInstance();
//
//            facesContext.responseComplete();
//
//            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
//
//            List<MapaEscala> listaDados = imprimirEscala();
//
//            System.out.println("Tamanho da Lista: " + listaDados.size());
//
//            JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(listaDados);
//
//            Map<String, Object> parametros = new HashMap<>();
//
//            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/relatorios/supi/relatorioEscala.jasper");
////            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("relatorios/relatorioEscala.jasper");
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parametros, dados);
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            JRPdfExporter exporter = new JRPdfExporter();
//
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//
//            exporter.exportReport();
//
//            byte[] bytes = baos.toByteArray();
//
//            if (bytes != null && bytes.length > 0)
//            {
//
//                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//
//                response.setContentType("application/pdf");
//
//                response.setHeader("Content-disposition", "inline; filename=\"relatorio.pdf\"");
//
//                response.setContentLength(bytes.length);
//
//                ServletOutputStream outputStream = response.getOutputStream();
//
//                outputStream.write(bytes, 0, bytes.length);
//
//                outputStream.flush();
//
//                outputStream.close();
//
//            }*/
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            //System.out.println("Bag: " + e.getMessage());
//        }
//
//    }

//    public void setChangedSOI(Integer turno, int dia, int linha)
//    {
//        if (turno != null)
//        {
//
//            switch (buscaSiglaTurno(turno))
//            {
//                case "D":
//                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    totalDiaD[dia - 1] = totalDiaD[dia - 1] + 1;
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
//
////                    UIComponent component = findComponent("dia" + (dia + 1));
////                    if (component != null) {
////                        HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
////                        sel.setValue(null);
////                        sel.setDisabled(true);
////                    }
//                    break;
//
//                case "M":
//                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    totalDiaManha[dia - 1] = totalDiaManha[dia - 1] + 1;
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
//
//                    break;
//
//                case "DA":
//                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    totalDiaDA[dia - 1] = totalDiaDA[dia - 1] + 1;
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
//
////                    if (diaSemana(dia + 1).equals("Sab") && diaSemana(dia + 2).equals("Dom")) {
////                        UIComponent component11 = findComponent("dia" + (dia + 1));
////                        UIComponent component22 = findComponent("dia" + (dia + 2));
////                        if (component11 != null && component22 != null) {
////                            HtmlSelectOneMenu sel1 = (HtmlSelectOneMenu) component11;
////                            sel1.setValue(null);
////                            sel1.setDisabled(true);
////
////                            HtmlSelectOneMenu sel2 = (HtmlSelectOneMenu) component22;
////                            sel2.setValue(null);
////                            sel2.setDisabled(true);
////                        }
////                    }
//                    break;
//
//                case "T":
//                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    totalDiaTarde[dia - 1] = totalDiaTarde[dia - 1] + 1;
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
//
//                    break;
//
//                case "V":
//                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    totalDiaVela[dia - 1] = totalDiaVela[dia - 1] + 1;
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
////                    UIComponent component1 = findComponent("dia" + (dia + 1));
////                    UIComponent component2 = findComponent("dia" + (dia + 2));
////                    UIComponent component3 = findComponent("dia" + (dia + 3));
////                    if (component1 != null && component2 != null && component3 != null) {
////                        HtmlSelectOneMenu sel1 = (HtmlSelectOneMenu) component1;
////                        sel1.setValue(null);
////                        sel1.setDisabled(true);
////
////                        HtmlSelectOneMenu sel2 = (HtmlSelectOneMenu) component2;
////                        sel2.setValue(null);
////                        sel2.setDisabled(true);
////
////                        HtmlSelectOneMenu sel3 = (HtmlSelectOneMenu) component3;
////                        sel3.setValue(null);
////                        sel3.setDisabled(true);
////                    }
//                    break;
//                default:
//                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
//
//                    break;
//
//            }
//
//            /* for (int j = 0; j < numeroDias(mes); j++) {
//             System.out.println("turnosss:"+escalaMensal.get(linha).getTurnos()[j]);
//             cargaHoraria = cargaHoraria + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[j]).getCargaHoraria();
//             }
//             escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
//             */
//        }
//        /*else {
//
//         UIComponent component = findComponent("dia" + (dia + 1));
//         if (component != null) {
//         HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
//         sel.setValue(null);
//         sel.setDisabled(false);
//         }
//         }
//         */
//    }

    public double cargaHorariaLinha(int linha, int dia, int turno)
    {
        cargaH += supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();

        return cargaH;
    }

    public void setChangedSOI1(Integer turno, int dia, int linha)
    {
//        System.out.println("Linha: " + linha);
//        System.out.println("Turno: " + turno);
//        System.out.println("Dia: " + dia);
//        System.out.println("linhaSelecionada:" + linha);
//        System.out.println("linhaSelecionada:" + cargaHoraria);

        if (turno != null)
        {
//            String sigla = buscaSiglaTurno(turno);
//            if (sigla.equals("D"))
//            {
//                double cargaH = 0;
//                cargaH += cargaHoraria + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
//                cargaHoraria = cargaH;
//                totalDiaD[dia - 1] = totalDiaD[dia - 1] + 1;
//                escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
//                escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
////
////                    UIComponent component = findComponent("dia" + (dia + 1));
////                    if (component != null) {
////                        HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
////                        sel.setValue(null);
////                        sel.setDisabled(true);
////                    }
//
//            }

            switch (buscaSiglaTurno(turno))
            {
                case "D":
                    cargaHoraria = /*cargaHoraria*/escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaD[dia - 1] = totalDiaD[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);

                    //Descomentei apartir daqui
                    
                    /*UIComponent component = findComponent("dia" + (dia + 1));
                    if (component != null) {
                        HtmlSelectOneMenu sel = (HtmlSelectOneMenu) component;
                        sel.setValue(null);
                        sel.setDisabled(true);
                    }*/
                    break;
                    //Até aqui

                case "M":
                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaManha[dia - 1] = totalDiaManha[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);

                    break;

                case "DA":
                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaDA[dia - 1] = totalDiaDA[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
                    
                    //Descomentei apartir daqui
                    
                    if (diaSemana(dia + 1).equals("Sab") && diaSemana(dia + 2).equals("Dom")) {
                        UIComponent component11 = findComponent("dia" + (dia + 1));
                        UIComponent component22 = findComponent("dia" + (dia + 2));
                        if (component11 != null && component22 != null) {
                            HtmlSelectOneMenu sel1 = (HtmlSelectOneMenu) component11;
                            sel1.setValue(null);
                            sel1.setDisabled(true);

                            HtmlSelectOneMenu sel2 = (HtmlSelectOneMenu) component22;
                            sel2.setValue(null);
                            sel2.setDisabled(true);
                        }
                    }
                    // Até aqui
                    break;

                case "T":
                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaTarde[dia - 1] = totalDiaTarde[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);

                    break;

                case "V":
                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    totalDiaVela[dia - 1] = totalDiaVela[dia - 1] + 1;
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);
                    
                    //Descomentei apartir daqui
                    
                    /*UIComponent component1 = findComponent("dia" + (dia + 1));
                    UIComponent component2 = findComponent("dia" + (dia + 2));
                    UIComponent component3 = findComponent("dia" + (dia + 3));
                    if (component1 != null && component2 != null && component3 != null) {
                        HtmlSelectOneMenu sel1 = (HtmlSelectOneMenu) component1;
                        sel1.setValue(null);
                        sel1.setDisabled(true);

                        HtmlSelectOneMenu sel2 = (HtmlSelectOneMenu) component2;
                        sel2.setValue(null);
                        sel2.setDisabled(true);

                        HtmlSelectOneMenu sel3 = (HtmlSelectOneMenu) component3;
                        sel3.setValue(null);
                        sel3.setDisabled(true);
                    }*/
                    
                     // Até aqui
                    
                    break;
                default:
                    cargaHoraria = escalaMensal.get(linha).getCargaHoraria() + supiTurnoFacade.find(escalaMensal.get(linha).getTurnos()[dia - 1]).getCargaHoraria();
                    escalaMensal.get(linha).setTurnos(escalaMensal.get(linha).getTurnos());
                    escalaMensal.get(linha).setCargaHoraria(cargaHoraria);

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
        System.out.println("turno recebido para sigla: " + turno);
        if (turno != null && turno != 0)
        {
            return supiTurnoFacade.find(turno).getSigla();
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

    public SupiTurno getSupiTurno()
    {
        return supiTurno;
    }

    public void setSupiTurno(SupiTurno supiTurno)
    {
        this.supiTurno = supiTurno;
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
            SupiSupervisorHasEscala item = getInstancia();
            item.setFkIdFuncionario(func);

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

    public SupiEscala getInstanciaEscala()
    {
        SupiEscala escala = new SupiEscala();
        escala.setFkIdSeccao(null);
        escala.setFkIdTipoEscala(new SupiTipoEscala());

        return escala;
    }

    public List<RhFuncionario> getSupervisoresList()
    {
        // System.out.println("lista de supervisoresList do repeat dos supervisores" + supervisoresList);
        return supervisoresList;
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
    public List<SupiSupervisorHasEscala> getEscalaMensalList()
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
        supervisoresList = null;

        return "/faces/supiVisao/escala/escalaTeste.xhtml?faces-redirect=true";
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

    /**
     * @param escalaMensalList the escalaMensalList to set
     */
    public void setEscalaMensalList(List<SupiSupervisorHasEscala> escalaMensalList)
    {
        this.escalaMensalList = escalaMensalList;
    }

    public SupiSupervisorHasEscala getEscalaSupervisor()
    {
        if (escalaSupervisor == null)
        {
            escalaSupervisor = getInstancia();
        }

        return escalaSupervisor;
    }

    /*public String contadorTurnos(){
       
     SupiTurno turnoConta = new SupiTurno();
     for(int i = 1;i< turnoConta.getDescricaoTurno();i++){
            
        
     }
    
     }*/
    public void setEscalaSupervisor(SupiSupervisorHasEscala escalaSupervisor)
    {
        //System.out.println("recebeu " + escalaSupervisor.getFkIdFuncionario().getFkIdPessoa().getNome());
        //System.out.println("no dia " + escalaSupervisor.getData());
        this.escalaSupervisor = escalaSupervisor;
    }

    public List<RhFuncionario> getFuncionrios()
    {
        return rhFuncionarioFacade.findAll();
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
