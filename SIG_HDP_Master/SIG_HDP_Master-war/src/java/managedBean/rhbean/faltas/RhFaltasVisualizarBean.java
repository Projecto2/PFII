/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.faltas;

import entidade.RhFuncionario;
import entidade.RhFuncionarioHasRhTipoFalta;
import entidade.RhTipoFalta;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessao.RhFuncionarioFacade;
import sessao.RhFuncionarioHasRhTipoFaltaFacade;
import sessao.RhTipoFaltaFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhFaltasVisualizarBean implements Serializable
{

    @EJB
    RhTipoFaltaFacade tipoFaltaFacade;
    @EJB
    RhFuncionarioHasRhTipoFaltaFacade funcionarioHasRhTipoFaltaFacade;
    @EJB
    RhFuncionarioFacade funcionarioFacade;

    /**
     * Entidades
     */
    private Integer mesPesquisa, anoPesquisa;
    private List<RhFuncionario> funcionariosList;

    /**
     * Creates a new instance of assiduidadeVisualizarBean
     */
    public RhFaltasVisualizarBean ()
    {
    }

    public Integer getMesPesquisa ()
    {
        return mesPesquisa;
    }

    public void setMesPesquisa (Integer mesPesquisa)
    {
        this.mesPesquisa = mesPesquisa;
    }

    public Integer getAnoPesquisa ()
    {
        return anoPesquisa;
    }

    public void setAnoPesquisa (Integer anoPesquisa)
    {
        this.anoPesquisa = anoPesquisa;
    }

    public List<RhFuncionario> getFuncionariosList ()
    {
        return funcionariosList;
    }

    public void setFuncionariosList (List<RhFuncionario> funcionariosList)
    {
        this.funcionariosList = funcionariosList;
    }

    public String limparPesquisa ()
    {
        mesPesquisa = anoPesquisa = null;
        funcionariosList = null;

        return "faltasVisualizarRh.xhtml?faces-redirect=true";
    }

    public void pesquisar ()
    {
        try
        {
            //Obtém o total de dias do mês selecionado
            Integer diasDoMes = totalDeDiasDoMes(anoPesquisa, mesPesquisa);

            //Forma a data inferior com o dia 01 do mês selecionado 
            String dataInf = "01-" + mesPesquisa + "-" + anoPesquisa;

            //Forma a data superior com o último dia do mês selecionado 
            String dataSup = diasDoMes + "-" + mesPesquisa + "-" + anoPesquisa;

            Date dataInferior = new SimpleDateFormat("dd-MM-yyyy").parse(dataInf);
            Date dataSuperior = new SimpleDateFormat("dd-MM-yyyy").parse(dataSup);

            //Pesquisa todas as faltas de todos os funcionários num intervalo de datas
            List<RhFuncionarioHasRhTipoFalta> faltas = funcionarioHasRhTipoFaltaFacade.findFaltas(dataInferior, dataSuperior);

            if (faltas.isEmpty())
            {
                funcionariosList = null;
                Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
            }
            else
            {
                funcionariosList = funcionarioFacade.findFuncionariosPorDataDeFaltas(dataInferior, dataSuperior);

                for (RhFuncionario f : funcionariosList)
                {
                    //Pegando o registo de faltas do funcionário
                    f.setRhFuncionarioHasRhTipoFaltaList(new ArrayList<RhFuncionarioHasRhTipoFalta>());
                    for (RhFuncionarioHasRhTipoFalta falta : faltas)
                    {
                        //Se for falta deste funcionário e o tipo de falta não for null, adiciona no seu array de faltas
                        if (falta.getFkIdFuncionario().getPkIdFuncionario() == f.getPkIdFuncionario() && falta.getFkIdTipoFalta() != null)
                        {
                            f.getRhFuncionarioHasRhTipoFaltaList().add(falta);
                        }
                    }
                }

                Mensagem.sucessoMsg("Número de registros encontrados (" + faltas.size() + ")");
            }
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String visualizarMes (Integer mes)
    {
        if (mes != null)
        {
            switch (mes)
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

    private int totalDeDiasDoMes (int ano, int mes)
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

    public String corDaFalta (RhTipoFalta tipoFalta)
    {
        switch (tipoFalta.getDescricao())
        {
            case "Justificada":
                return "#00ba8b";//Verde
            case "Não Justificada":
                return "tomato";
        }

        return "null";
    }

    public String verHora (Date data)
    {
        if (data == null)
        {
            return "-----------";
        }
        return new SimpleDateFormat("HH : mm").format(data);
    }

}
