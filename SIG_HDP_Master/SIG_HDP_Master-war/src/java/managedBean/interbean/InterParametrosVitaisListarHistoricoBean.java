/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterControloParametrosVitais;
import entidade.SegConta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.segbean.SegLoginBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import sessao.InterControloParametrosVitaisFacade;
import util.Data;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterParametrosVitaisListarHistoricoBean implements Serializable
{

    @EJB
    private InterControloParametrosVitaisFacade interControloParametrosVitaisFacade;

    private int ta1, ta2, pulso, saturacao, fr, diuresi, glicemia, periodo;

    private float peso, temperatura;

    private String parametroAtivo, tipoServico;

    private LineChartModel historico;

    private Date dataRegisto1, dataRegisto2;

    private List<InterControloParametrosVitais> listaParametros;

    /**
     * Creates a new instance of InterParametrosVitaisListarHistoricoBean
     */
    public InterParametrosVitaisListarHistoricoBean()
    {

    }

    public static InterParametrosVitaisListarHistoricoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterParametrosVitaisListarHistoricoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interParametrosVitaisListarHistoricoBean");
    }

    public void init()
    {
        inicializar();
    }

    public void inicializar()
    {
        tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        historico = new LineChartModel();
    }

    public Date getDataRegisto1()
    {
        return dataRegisto1;
    }

    public void setDataRegisto1(Date dataRegisto1)
    {
        this.dataRegisto1 = dataRegisto1;
    }

    public Date getDataRegisto2()
    {
        return dataRegisto2;
    }

    public void setDataRegisto2(Date dataRegisto2)
    {
        this.dataRegisto2 = dataRegisto2;
    }

    public int getTa1()
    {
        return ta1;
    }

    public void setTa1(int ta1)
    {
        this.ta1 = ta1;
    }

    public int getTa2()
    {
        return ta2;
    }

    public void setTa2(int ta2)
    {
        this.ta2 = ta2;
    }

    public float getPeso()
    {
        return peso;
    }

    public void setPeso(float peso)
    {
        this.peso = peso;
    }

    public float getTemperatura()
    {
        return temperatura;
    }

    public void setTemperatura(float temperatura)
    {
        this.temperatura = temperatura;
    }

    public int getPulso()
    {
        return pulso;
    }

    public void setPulso(int pulso)
    {
        this.pulso = pulso;
    }

    public int getSaturacao()
    {
        return saturacao;
    }

    public void setSaturacao(int saturacao)
    {
        this.saturacao = saturacao;
    }

    public int getFr()
    {
        return fr;
    }

    public void setFr(int fr)
    {
        this.fr = fr;
    }

    public int getDiuresi()
    {
        return diuresi;
    }

    public void setDiuresi(int diuresi)
    {
        this.diuresi = diuresi;
    }

    public int getGlicemia()
    {
        return glicemia;
    }

    public void setGlicemia(int glicemia)
    {
        this.glicemia = glicemia;
    }

    public int getPeriodo()
    {
        return periodo;
    }

    public void setPeriodo(int periodo)
    {
        this.periodo = periodo;
    }

    public String getParametroAtivo()
    {
        return parametroAtivo;
    }

    public void setParametroAtivo(String parametroAtivo)
    {
        this.parametroAtivo = parametroAtivo;
    }    

    public void pesquisar(String parametro)
    {

        init();

        Data dataFormato = new Data();

        parametroAtivo = parametro;

        listaParametros = interControloParametrosVitaisFacade.pesquisarParametro(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametroAtivo, null, 0, dataRegisto1, dataRegisto2, null);

        if (!listaParametros.isEmpty())
        {
            LineChartSeries series1 = new LineChartSeries();
            LineChartSeries series2 = new LineChartSeries();

            if (parametroAtivo.equals("Tensão Arterial"))
            {
                series1.setLabel("T.A (Alta)");
            }
            else
            {
                series1.setLabel(parametroAtivo);
            }

            for (int i = 0; i < listaParametros.size(); i++)
            {
                if (parametroAtivo.equals("Tensão Arterial"))
                {
                    if (listaParametros.get(i).getTaValor1() != null)
                    {
                        series1.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h", listaParametros.get(i).getTaValor1());
                        series2.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h", listaParametros.get(i).getTaValor2());
                    }
                }
                else if (parametroAtivo.equals("Pulso") && listaParametros.get(i).getFkIdRegistoInternamentoHasParametroVital().getFkIdParametroVital().getDescricao().equals("Pulso"))
                {
                    series1.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h" + "(" + listaParametros.get(i).getFkIdPulsoUnidade().getDescricao() + ")", listaParametros.get(i).getValor());
                }
                else
                {
                    series1.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h", listaParametros.get(i).getValor());
                }
            }

            historico.addSeries(series1);

            if (parametroAtivo.equals("Tensão Arterial"))
            {
                series2.setLabel("T.A (Baixa)");
                historico.addSeries(series2);
            }
            historico.setTitle("Histórico " + parametroAtivo);
            historico.setLegendPosition("e");
            historico.setShowPointLabels(true);
            historico.getAxes().put(AxisType.X, new CategoryAxis("Data"));
            Axis yAxis = historico.getAxis(AxisType.Y);
            yAxis.setLabel(parametroAtivo);
        }
        else
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }

    }

    public void pesquisar2(String parametro, int opcao)
    {

        init();

        Data dataFormato = new Data();

        parametroAtivo = parametro;

        if (opcao == 1)
        {
            listaParametros = interControloParametrosVitaisFacade.pesquisarParametro(InterAnotacaoMedicaListarBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametroAtivo, null, 0, dataRegisto1, dataRegisto2, null);
        }
        else
        {
            listaParametros = interControloParametrosVitaisFacade.pesquisarParametro(InterAnotacaoEnfermagemListarBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametroAtivo, null, 0, dataRegisto1, dataRegisto2, null);
        }

        if (!listaParametros.isEmpty())
        {
            LineChartSeries series1 = new LineChartSeries();
            LineChartSeries series2 = new LineChartSeries();

            if (parametroAtivo.equals("Tensão Arterial"))
            {
                series1.setLabel("T.A (Alta)");
            }
            else
            {
                series1.setLabel(parametroAtivo);
            }

            for (int i = 0; i < listaParametros.size(); i++)
            {
                if (parametroAtivo.equals("Tensão Arterial"))
                {
                    if (listaParametros.get(i).getTaValor1() != null)
                    {
                        series1.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h", listaParametros.get(i).getTaValor1());
                        series2.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h", listaParametros.get(i).getTaValor2());
                    }
                }
                else if (parametroAtivo.equals("Pulso") && listaParametros.get(i).getFkIdRegistoInternamentoHasParametroVital().getFkIdParametroVital().getDescricao().equals("Pulso"))
                {
                    series1.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h" + "(" + listaParametros.get(i).getFkIdPulsoUnidade().getDescricao() + ")", listaParametros.get(i).getValor());
                }
                else
                {
                    series1.set(dataFormato.dateToStr(listaParametros.get(i).getDataRegistadaNoPaciente()) + " " + listaParametros.get(i).getHora() + "h", listaParametros.get(i).getValor());
                }
            }

            historico.addSeries(series1);

            if (parametroAtivo.equals("Tensão Arterial"))
            {
                series2.setLabel("T.A (Baixa)");
                historico.addSeries(series2);
            }
            historico.setTitle("Histórico " + parametroAtivo);
            historico.setLegendPosition("e");
            historico.setShowPointLabels(true);
            historico.getAxes().put(AxisType.X, new CategoryAxis("Data"));
            Axis yAxis = historico.getAxis(AxisType.Y);
            yAxis.setLabel(parametroAtivo);
        }
        else
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }

    }

    public LineChartModel getHistorico()
    {
        if (historico == null)
        {
            historico = new LineChartModel();
        }
        return historico;
    }

    public void limparCampos()
    {
        historico = null;
        dataRegisto1 = null;
        dataRegisto2 = null;
        parametroAtivo = null;
    }

}
