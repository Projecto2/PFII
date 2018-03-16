/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegTipoFuncionalidade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.SegTipoFuncionalidadeFacade;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class SegTipoFuncionalidadeBean implements Serializable
{

    @EJB
    private SegTipoFuncionalidadeFacade segTipoFuncionalidadeFacade;

    /**
     * Creates a new instance of SegTipoFuncionalidadeBean
     */
    public SegTipoFuncionalidadeBean()
    {
    }

    public static SegTipoFuncionalidadeBean getInstanciaBean()
    {
        return (SegTipoFuncionalidadeBean) GeradorCodigo.getInstanciaBean("segTipoFuncionalidadeBean");
    }
    
    public List<SegTipoFuncionalidade> findAllOrderByNome()
    {
        return this.segTipoFuncionalidadeFacade.findAllOrderByNome();
    }
}
