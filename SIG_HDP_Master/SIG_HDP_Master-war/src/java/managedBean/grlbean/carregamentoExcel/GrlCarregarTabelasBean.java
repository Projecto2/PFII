/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean.carregamentoExcel;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlCarregarTabelasBean implements Serializable
{

    /**
     * Creates a new instance of RhCarregarTabelasBean
     */
    public GrlCarregarTabelasBean ()
    {
    }

    public void actualizarGeraisTabelas ()
    {
        //Tabelas independentes
        GrlDiaSemanaExcelBean.getInstanciaBean().carregarDiaSemanaTabela();
        GrlEstadoCivilExcelBean.getInstanciaBean().carregarEstadoCivilTabela();
        GrlGrauParentescoExcelBean.getInstanciaBean().carregarGrauParentescoTabela();
        GrlSexoExcelBean.getInstanciaBean().carregarSexoTabela();
        GrlTamanhoExcelBean.getInstanciaBean().carregarTamanhoTabela();
        GrlTipoDocumentoIdentificacaoExcelBean.getInstanciaBean().carregarTipoDocumentoIdentificacaoTabela();
        GrlAreaInternaExcelBean.getInstanciaBean().carregarAreaInternaTabela();
        GrlReligiaoExcelBean.getInstanciaBean().carregarReligiaoTabela();
//        GrlMunicipioExcelBean.getInstanciaBean().carregarMunicipioTabela();

        //Tabelas dependentes
    }

}
