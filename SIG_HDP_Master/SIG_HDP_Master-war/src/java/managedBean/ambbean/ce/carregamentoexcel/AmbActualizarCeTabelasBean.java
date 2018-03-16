/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbActualizarCeTabelasBean implements Serializable
{

    /**
     * Creates a new instance of AmbActualizarCeTabelasBean
     */
    public AmbActualizarCeTabelasBean()
    {
    }
    
    public void actualizarCeTabelas()
    {
        AmbAderenciaExcelBean ambAderenciaExcelBean = AmbAderenciaExcelBean.getInstanciaBean();
        ambAderenciaExcelBean.carregarAderenciaTabela();
System.err.println("0 : AmbAderenciaExcelBean.carregarAderenciaTabela()");
        
        AmbClassificacaoDorExcelBean ambClassificacaoDorExcelBean = AmbClassificacaoDorExcelBean.getInstanciaBean();
        ambClassificacaoDorExcelBean.carregarClassificacaoDorTabela();
System.err.println("1 : AmbClassificacaoDorExcelBean.carregarClassificacaoDorTabela()");

        AmbColoracaoExcelBean ambColoracaoExcelBean = AmbColoracaoExcelBean.getInstanciaBean();
        ambColoracaoExcelBean.carregarColoracaoTabela();
System.err.println("2 : AmbColoracaoExcelBean.carregarColoracaoTabela()");
        
        AmbConsultorioExcelBean ambConsultorioExcelBean = AmbConsultorioExcelBean.getInstanciaBean();
        ambConsultorioExcelBean.carregarConsultorioTabela();
System.err.println("3 : AmbConsultorioExcelBean.carregarConsultorioTabela()"); 

        AmbConfirmacaoExcelBean ambConfirmacaoExcelBean = AmbConfirmacaoExcelBean.getInstanciaBean();
        ambConfirmacaoExcelBean.carregarConfirmacaoTabela();
System.err.println("4 : AmbConsultorioExcelBean.carregarConfirmacaoTabela()");

        AmbCorExcelBean ambCorExcelBean = AmbCorExcelBean.getInstanciaBean();
        ambCorExcelBean.carregarCorTabela();
System.err.println("5 : AmbCorExcelBean.carregarCorTabela()");
        
        AmbEspessuraExcelBean ambEspessuraExcelBean = AmbEspessuraExcelBean.getInstanciaBean();
        ambEspessuraExcelBean.carregarEspessuraTabela();
System.err.println("6 : AmbEspessuraExcelBean.carregarEspessuraTabela()");
        
        AmbEstadoExcelBean ambEstadoExcelBean = AmbEstadoExcelBean.getInstanciaBean();
        ambEstadoExcelBean.carregarEstadoTabela();
System.err.println("7 : AmbEstadoExcelBean.carregarEstadoTabela()");

        AmbEstadoHidratacaoExcelBean ambEstadoHidratacaoExcelBean = AmbEstadoHidratacaoExcelBean.getInstanciaBean();
        ambEstadoHidratacaoExcelBean.carregarEstadoHidratacaoTabela();
System.err.println("8 : AmbEstadoHidratacaoExcelBean.carregarEstadoHidratacaoTabela()");

        AmbEstadoNotificacaoExcelBean ambEstadoNotificacaoExcelBean = AmbEstadoNotificacaoExcelBean.getInstanciaBean();
        ambEstadoNotificacaoExcelBean.carregarEstadoNotificacaoTabela();
System.err.println("9 : AmbEstadoNotificacaoExcelBean.carregarEstadoNotificacaoTabela()");
        
        AmbEstadoPagamentoExcelBean ambEstadoPagamentoExcelBean = AmbEstadoPagamentoExcelBean.getInstanciaBean();
        ambEstadoPagamentoExcelBean.carregarEstadoPagamentoTabela();
System.err.println("10: AmbEstadoPagamentoExcelBean.carregarEstadoPagamentoTabela()");

        AmbImpressoesGeraisExcelBean ambImpressoesGeraisExcelBean = AmbImpressoesGeraisExcelBean.getInstanciaBean();
        ambImpressoesGeraisExcelBean.carregarImpressoesGeraisTabela();
System.err.println("11: AmbImpressoesGeraisExcelBean.carregarImpressoesGeraisTabela()");

        AmbObservacoesMedicasExcelBean ambObservacoesMedicasExcelBean = AmbObservacoesMedicasExcelBean.getInstanciaBean();
        ambObservacoesMedicasExcelBean.carregarObservacoesMedicasTabela();
System.err.println("12: AmbObservacoesMedicasExcelBean.carregarObservacoesMedicasTabela()");

        AmbSinalExcelBean ambSinalExcelBean = AmbSinalExcelBean.getInstanciaBean();
        ambSinalExcelBean.carregarSinalTabela();
System.err.println("13: AmbSinalExcelBean.carregarSinalTabela()");

        AmbTexturaExcelBean ambTexturaExcelBean = AmbTexturaExcelBean.getInstanciaBean();
        ambTexturaExcelBean.carregarTexturaTabela();
System.err.println("14: AmbTexturaExcelBean.carregarTexturaTabela()");
        
        AmbTurgorExcelBean ambTurgorExcelBean = AmbTurgorExcelBean.getInstanciaBean();
        ambTurgorExcelBean.carregarTurgorTabela();
System.err.println("15: AmbTurgorExcelBean.carregarTurgorTabela()");
    }
}
