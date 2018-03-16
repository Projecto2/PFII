/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegFuncionalidade;
import entidade.SegTipoFuncionalidade;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class SegFuncionalidadeBean implements Serializable
{

    /**
     * Creates a new instance of SegFuncionalidadeBean
     */
    public SegFuncionalidadeBean()
    {
    }

    public static SegFuncionalidadeBean getInstanciaBean()
    {
        return (SegFuncionalidadeBean) GeradorCodigo.getInstanciaBean("segFuncionalidadeBean");
    }

    public static SegFuncionalidade getInstancia()
    {
        SegFuncionalidade segFuncionalidade = new SegFuncionalidade();
        segFuncionalidade.setFkIdFuncionalidadePai(null);
        segFuncionalidade.setFkIdTipoFuncionalidade(new SegTipoFuncionalidade());
        return segFuncionalidade;
    }
}
