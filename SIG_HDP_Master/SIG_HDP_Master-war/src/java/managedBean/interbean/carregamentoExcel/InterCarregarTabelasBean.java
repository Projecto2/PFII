/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean.carregamentoExcel;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterCarregarTabelasBean implements Serializable
{

    /**
     * Creates a new instance of InterCarregarTabelasBean
     */
    public InterCarregarTabelasBean()
    {
    }

    public static InterCarregarTabelasBean getInstanciaBean()
    {
        return (InterCarregarTabelasBean) GeradorCodigo.getInstanciaBean("interCarregarTabelasBean");
    }
    
    public void actualizarInternamentoTabelas()
    {

        InterEstadoCamaExcelBean.getInstanciaBean().carregarEstadoCamaTabela(2);
        InterCamaExcelBean.getInstanciaBean().carregarCamaInternamentoTabela();
        InterEnfermariaExcelBean.getInstanciaBean().carregarEnfermariaTabela();
        InterHoraMedicacaoExcelBean.getInstanciaBean().carregarHoraMedicacaoTabela();
        InterOpcaoMedicacaoExcelBean.getInstanciaBean().carregarOpcaoMedicacaoTabela();
        InterParametroVitalExcelBean.getInstanciaBean().carregarParametroVitalTabela();
        InterPulsoUnidadeExcelBean.getInstanciaBean().carregarPulsoUnidadeTabela();
        InterResultadoDoencaExcelBean.getInstanciaBean().carregarResultadoDoencaTabela();
        InterSalaExcelBean.getInstanciaBean().carregarSalaTabela();
        InterTipoAltaExcelBean.getInstanciaBean().carregarTipoAltaTabela();
        InterTipoDoencaInternamentoExcelBean.getInstanciaBean().carregarTipoDoencaInternamentoTabela();
        InterTipoNotificacaoExcelBean.getInstanciaBean().carregarTipoNotificacaoTabela();
    }
}
