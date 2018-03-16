/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean.carregamentoExcel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagCarregarTabelasBean
{

    /**
     * Creates a new instance of DiagCarregarTabelasBean
     */
    public DiagCarregarTabelasBean()
    {
    }

    public static DiagCarregarTabelasBean getInstanciaBean()
    {
        return (DiagCarregarTabelasBean) GeradorCodigo.getInstanciaBean("diagCarregarTabelasBean");
    }

    public void carregarDiagnosticosTabelas()
    {
        DiagCategoriaExameExcelBean.getInstanciaBean().carregarCategoriaExameTabela();
        DiagComponenteSanguineoExcelBean.getInstanciaBean().carregarComponenteSanguineoTabela();
        DiagExameExcelBean.getInstanciaBean().carregarExameTabela();
        DiagGrupoSanguineoExcelBean.getInstanciaBean().carregarGrupoSanguineoTabela();
        DiagSubcategoriaExameExcelBean.getInstanciaBean().carregarSubcategoriaExameTabela();
        DiagTipoAmostraExcelBean.getInstanciaBean().carregarTipoAmostraTabela();
        DiagCaracterTransfusaoExcelBean.getInstanciaBean().carregarCaracterTransfusaoTabela();
        DiagEstadoClinicoExcelBean.getInstanciaBean().carregarEstadoClinicoTabela();
        DiagNumeroDoacaoExcelBean.getInstanciaBean().carregarNumeroDoacaoTabela();
        DiagRespostasQuestoesRequisicaoComponentesExcelBean.getInstanciaBean().carregarRespostasQuestoesRequisicaoComponentesTabela();
        DiagResultadoExameCandidatoExcelBean.getInstanciaBean().carregarResultadoExameCandidatoTabela();
        DiagResultadoTesteCompatibilidadeExcelBean.getInstanciaBean().carregarResultadoTesteCompatibilidadeTabela();
        DiagResultadoTriagemExcelBean.getInstanciaBean().carregarResultadoTriagemTabela();
        DiagSectorExameExcelBean.getInstanciaBean().carregarSectorExameTabela();
        DiagTipoDoacaoExcelBean.getInstanciaBean().carregarTipoDoacaoTabela();
        DiagTipoResultadoExameExcelBean.getInstanciaBean().carregarTipoResultadoExameTabela();
    }

}
