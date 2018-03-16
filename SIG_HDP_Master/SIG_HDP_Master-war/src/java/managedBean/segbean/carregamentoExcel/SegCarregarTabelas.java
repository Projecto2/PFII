/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean.carregamentoExcel;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class SegCarregarTabelas implements Serializable
{

    /**
     * Creates a new instance of SegCarregarTabelas
     */
    public SegCarregarTabelas()
    {
    }

    public static void actualizarSegurancaTabelas()
    {
        SegTipoFuncionalidadeExcelBean.getInstanciaBean().carregarTipoFuncionalidadeTabela();
        SegFuncionalidadeExcelBean.getInstanciaBean().carregarFuncionalidadeTabela();
    }
}
