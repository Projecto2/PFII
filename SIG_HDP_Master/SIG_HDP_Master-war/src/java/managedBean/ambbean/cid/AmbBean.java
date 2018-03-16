/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

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
public class AmbBean implements Serializable
{

    /**
     * Creates a new instance of AmbBean
     */
    public AmbBean()
    {
    }
    
    public static AmbBean getInstanciaBean()
    { 
        return (AmbBean) GeradorCodigo.getInstanciaBean("ambBean");
    }

    public void inicializar()
    {
        //actualizarCid10Tabelas();
        inicializarAmbCidConfiguracoes();
    }

    public void inicializarAmbCidConfiguracoes()
    {
        AmbCidConfiguracoesBean ambCidConfiguracoesBean = AmbCidConfiguracoesBean.getInstanciaBean();
        ambCidConfiguracoesBean.inicializar();
    }
    
    public void actualizarCid10Tabelas()
    {
        AmbCidCapitulosBean ambCidCapitulosBean = AmbCidCapitulosBean.getInstanciaBean();
System.err.println("0: AmbBean.actualizarCid10Tabelas()");
        ambCidCapitulosBean.actualizarCid10Tabelas();
System.err.println("1: AmbBean.actualizarCid10Tabelas()");        
    }

}