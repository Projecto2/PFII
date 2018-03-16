/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.estatistica;

import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import entidade.GrlPessoa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean.getInstanciaAmbDiagnosticoHasDoenca;
import managedBean.ambbean.ce.triagem.AmbTriagemListarBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.AmbDiagnosticoHipoteseHasDoencaFacade;
import util.DataUtils;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbEstatisticaBean implements Serializable
{
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;
    @EJB
    private AmbDiagnosticoHipoteseHasDoencaFacade ambDiagnosticoHipoteseHasDoencaFacade;
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;

    private BarChartModel model;
    private AmbDiagnostico ambDiagnostico;
    private AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca;
    

    /**
     * Creates a new instance of AmbEstatisticaBean
     */
    public AmbEstatisticaBean()
    {
//        historicoGeral();
    }

    public static AmbEstatisticaBean getInstanciaBean()
    {
        return (AmbEstatisticaBean) GeradorCodigo.getInstanciaBean("ambEstatisticaBean");
    }

    public BarChartModel getModel()
    {
        return model;
    }

    public AmbDiagnosticoHasDoenca getAmbDiagnosticoHasDoenca()
    {
        if (ambDiagnosticoHasDoenca == null)
        {
            ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();
        }
        return ambDiagnosticoHasDoenca;
    }

    public void setAmbDiagnosticoHasDoenca(AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca)
    {
        this.ambDiagnosticoHasDoenca = ambDiagnosticoHasDoenca;
    }

    public BarChartModel barraDeEstado()
    {
        model = new BarChartModel();

        ChartSeries Homens = new ChartSeries();
        Homens.setLabel("Homens");
        Homens.set("2004", 120);
        Homens.set("2005", 100);
        Homens.set("2006", 44);
        Homens.set("2007", 150);
        Homens.set("2008", 25);
        
        ChartSeries Mulheres = new ChartSeries();
        Mulheres.setLabel("Mulheres");
        Mulheres.set("2004", 52);
        Mulheres.set("2005", 60);
        Mulheres.set("2006", 110);
        Mulheres.set("2007", 135);
        Mulheres.set("2008", 120);
        
        model.addSeries(Homens);
        model.addSeries(Mulheres);
        model.setTitle("Bar Chart");
        model.setLegendPosition("ne");

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Gender");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Meses do Ano");

        yAxis.setMin(0);
        yAxis.setMax(200);

        return model;
    }
    
    public int verificaJaneiro(String mes)
    {
        if (mes.equals("Jan"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }
   
    public List<Integer> verificaFevereiro(String mes)
    {
        List<Integer> resultado = new ArrayList<>();
                
        if (mes.equals("Fev"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
//                 resultado.add(ambDiagnosticoHasDoencaFacade.findPaciente().size());
                 resultado.add(ambDiagnosticoHasDoencaFacade.findDoentes(adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size());
                   
            return resultado;
        }
        return resultado;
    }    
    
    public int verificaMarco(String mes)
    {
        if (mes.equals("Mar"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }     
    
    public int verificaAbril(String mes)
    {
        if (mes.equals("Abr"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }    
    
    public int verificaMaio(String mes)
    {
        if (mes.equals("Maio"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }    
    
    public int verificaJunho(String mes)
    {
        if (mes.equals("Jun"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }     
    
    public int verificaJulho(String mes)
    {
        if (mes.equals("Jul"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }     
    
    public int verificaAgosto(String mes)
    {
        if (mes.equals("Ago"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }    
    
    public int verificaSetembro(String mes)
    {
        if (mes.equals("Set"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }    
    
    public int verificaOutubro(String mes)
    {
        if (mes.equals("Out"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }     
    
    public int verificaNovembro(String mes)
    {
        if (mes.equals("Nov"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }     
    
    public int verificaDezembro(String mes)
    {
        if (mes.equals("Dez"))
        {
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 return ambDiagnosticoHasDoencaFacade.findPacientes(adhd, adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
                    .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome()).size();
        }
        return -1;
    }     

    public List<AmbDiagnosticoHasDoenca> rever()
    {
System.err.print("Tamanho : " + ambDiagnosticoHasDoencaFacade.teste().size());        
        return ambDiagnosticoHasDoencaFacade.teste();
    }     
    
    public BarChartModel historicoGeral()
    {
        BarChartModel model = new BarChartModel();

        BarChartSeries homens = new BarChartSeries();
        homens.setLabel("Homens       : " + ambDiagnosticoHasDoencaFacade.findPacientesHomens().size());
        
        BarChartSeries mulheres = new BarChartSeries();
        mulheres.setLabel("Mulheres   : " + ambDiagnosticoHasDoencaFacade.findPacientesMulheres().size());
        
        BarChartSeries pacientes = new BarChartSeries();
        pacientes.setLabel("Pacientes: " + ambDiagnosticoHasDoencaFacade.findPaciente().size());
        
//        LineChartSeries mulheres = new LineChartSeries();
        
        for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
        {
//             if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento()
//                 .getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getDescricao()
//                 .equals("Feminino"))//Feminino Masculino
//             {
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 0)
                     pacientes.set("Janeiro", verificaJaneiro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));

                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 1)
                 {
System.err.print("Fev : " + util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico()));                     
                     pacientes.set("Fevereiro", verificaFevereiro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())).size());
                 }
                     
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 2)
                     pacientes.set("Março", verificaMarco(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));  
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 3)
                     pacientes.set("Abril", verificaAbril(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 4)
                     pacientes.set("Maio", verificaMaio(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 5)
                     pacientes.set("Junho", verificaJunho(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 6)
                     pacientes.set("Julho", verificaJulho(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 7)
                     pacientes.set("Agosto", verificaAgosto(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 8)
                     pacientes.set("Setembro", verificaSetembro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 9)
                     pacientes.set("Outubro", verificaOutubro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 10)
                     pacientes.set("Novembro", verificaNovembro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
                 if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 11)
                     pacientes.set("Dezembro", verificaDezembro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
             
System.err.print("Tamanho : " + ambDiagnosticoHasDoencaFacade.findAll().size());
System.err.print("Data : " + util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico()));
//             }
//             else
//             {
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 0)
//                      pacientes.set("Janeiro", verificaJaneiro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 1)
//                      pacientes.set("Fevereiro", verificaFevereiro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//               
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 2)
//                      pacientes.set("Março", verificaMarco(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));  
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 3)
//                      pacientes.set("Abril", verificaAbril(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 4)
//                      pacientes.set("Maio", verificaMaio(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 5)
//                      pacientes.set("Junho", verificaJunho(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 6)
//                      pacientes.set("Julho", verificaJulho(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 7)
//                      pacientes.set("Agosto", verificaAgosto(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 8)
//                      pacientes.set("Setembro", verificaSetembro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 9)
//                      pacientes.set("Outubro", verificaOutubro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 10)
//                      pacientes.set("Novembro", verificaNovembro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//             
//                  if (adhd.getFkIdDiagnostico().getDataHoraDiagnostico().getMonth() == 11)
//                      pacientes.set("Dezembro", verificaDezembro(util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico())));
//                 
//System.err.print("Data : " + util.DataUtils.toStringMes(adhd.getFkIdDiagnostico().getDataHoraDiagnostico()));                 
//             }
    }    

        model.addSeries(pacientes);
//        model.addSeries(homens);
//        model.addSeries(mulheres);
        
        model.setTitle("Gráfico de Doentes");
        model.setLegendPosition("ne");

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Meses do Ano");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("");//Ano

        yAxis.setMin(0);
        yAxis.setMax(500);

        return model;
    }
}
