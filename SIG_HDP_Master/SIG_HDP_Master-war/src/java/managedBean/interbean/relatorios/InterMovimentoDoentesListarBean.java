/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean.relatorios;

import entidade.InterRegistoInternamento;
import entidade.InterRegistoSaida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import managedBean.interbean.InterObjetosSessaoBean;
import sessao.InterRegistoInternamentoFacade;
import sessao.InterRegistoSaidaFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.RelatorioJasper;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterMovimentoDoentesListarBean implements Serializable
{

    @EJB
    private InterRegistoSaidaFacade interRegistoSaidaFacade;

    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    private final Calendar dataCorrente = Calendar.getInstance();
    private Date dataPesq1, dataPesq2;
    private ArrayList<InterMovimentoDoentes> lista;

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

    /**
     * Creates a new instance of InterMovimentoDoentesBean
     */
    public InterMovimentoDoentesListarBean()
    {
    }

    public static InterMovimentoDoentesListarBean getInstanciaBean()
    {
        return (InterMovimentoDoentesListarBean) GeradorCodigo.getInstanciaBean("interMovimentoDoentesListarBean");
    }

    public Date getData()
    {
        return dataCorrente.getTime();
    }

    public Date getDataPesq1()
    {
        if (dataPesq1 == null)
        {
            dataPesq1 = dataCorrente.getTime();
        }
        return dataPesq1;
    }

    public void setDataPesq1(Date dataPesq1)
    {
        this.dataPesq1 = dataPesq1;
    }

    public Date getDataPesq2()
    {
        if (dataPesq2 == null)
        {
            dataPesq2 = dataCorrente.getTime();
        }
        return dataPesq2;
    }

    public void setDataPesq2(Date dataPesq2)
    {
        this.dataPesq2 = dataPesq2;
    }

    public void exportPDF(ActionEvent evt)
    {
        lista = new ArrayList();

        int totalAlta = 0, totalTransferido = 0, totalFalecido = 0, totalAbandono = 0;

        List<InterRegistoInternamento> listaRegistos;
        List<InterRegistoSaida> listaRegistosSaida;

        listaRegistos = interRegistoInternamentoFacade.pesquisarRegisto(null, tipoServico, null, null, null, null, null, null, 0, 0, 0, null, dataPesq1, dataPesq1);

        for (int i = 0; i < listaRegistos.size(); i++)
        {
            InterMovimentoDoentes movimento = new InterMovimentoDoentes();
            movimento.setCont(lista.size() + 1);
            movimento.setNomePaciente(listaRegistos.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + listaRegistos.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() + " " + listaRegistos.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome());
            movimento.setNumeroCama(listaRegistos.get(i).getFkIdCamaInternamento().getCodigoCamaInternamento());
            movimento.setNumeroInternamento(listaRegistos.get(i).getFkIdInscricaoInternamento().getNumeroInscricao());
            movimento.setTipoMovimento("Baixa");

            lista.add(movimento);
        }

        listaRegistosSaida = interRegistoSaidaFacade.pesquisarRegistoSaida(tipoServico, null, dataPesq1, dataPesq2, 0);

        for (int i = 0; i < listaRegistosSaida.size(); i++)
        {
            InterMovimentoDoentes movimento = new InterMovimentoDoentes();
            movimento.setCont(lista.size() + 1);
            movimento.setNomePaciente(listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() + " " + listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome());
            movimento.setNumeroInternamento(listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdInscricaoInternamento().getNumeroInscricao());

            if (listaRegistosSaida.get(i).getFkIdTipoAlta().getPkIdTipoAlta() == 1)
            {
                movimento.setNumeroCama(listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdCamaInternamento().getCodigoCamaInternamento());
                movimento.setTipoMovimento("Alta");
                totalAlta++;
            }
            else if (listaRegistosSaida.get(i).getFkIdTipoAlta().getPkIdTipoAlta() == 2)
            {
                movimento.setNumeroCama(listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdCamaInternamento().getCodigoCamaInternamento());
                movimento.setTipoMovimento("Transferido");
                totalTransferido++;

            }
            else if (listaRegistosSaida.get(i).getFkIdTipoAlta().getPkIdTipoAlta() == 3)
            {
                movimento.setNumeroCama(listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdCamaInternamento().getCodigoCamaInternamento());
                movimento.setTipoMovimento("Falecido");
                totalFalecido++;
            }
            else
            {
                movimento.setNumeroCama(listaRegistosSaida.get(i).getFkIdRegistoInternamento().getFkIdCamaInternamento().getCodigoCamaInternamento());
                movimento.setTipoMovimento("Abandono");
                totalAbandono++;
            }

            lista.add(movimento);
        }

        if (lista.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            HashMap<String, Object> parametrosMap = new HashMap<>();
            parametrosMap.put("servico", tipoServico);
            parametrosMap.put("data1", dataPesq1);
            parametrosMap.put("data2", dataPesq2);
            parametrosMap.put("TotEntrada", listaRegistos.size());
            parametrosMap.put("TotAlta", totalAlta);
            parametrosMap.put("TotFalecidos", totalFalecido);
            parametrosMap.put("TotTransferidos", totalTransferido);
            parametrosMap.put("TotAbandonados", totalAbandono);
            parametrosMap.put("TotFinal", (listaRegistos.size() + totalAlta + totalFalecido + totalTransferido + totalAbandono));
            RelatorioJasper.exportPDF("inter/movimentoDoentes.jasper", parametrosMap, lista);

        }

//        System.out.println("DATA : "+dataPesq);
//        System.out.println("SERVIÇO : "+tipoServico);
//        System.out.println("TAM 1 : "+lista.size());
//        System.out.println("TAM 2 : "+listaRegistos.size());
    }
}
