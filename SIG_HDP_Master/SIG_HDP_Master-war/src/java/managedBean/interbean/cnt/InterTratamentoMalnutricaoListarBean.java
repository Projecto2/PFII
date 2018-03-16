/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.cnt;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTratamentoMalnutricaoListarBean implements Serializable
{

    private String nomeEnfermeiro;
    
    private Date dataRegistoPesq1, dataRegistoPesq2;

    
    /**
     * Creates a new instance of InterTratamentoMalnutricaoListarBean
     */
    public InterTratamentoMalnutricaoListarBean()
    {
    }
    
    public static InterTratamentoMalnutricaoListarBean getInstanciaBean()
    {
        return (InterTratamentoMalnutricaoListarBean) GeradorCodigo.getInstanciaBean("interTratamentoMalnutricaoListarBean");
    }

    public String getNomeEnfermeiro()
    {
        return nomeEnfermeiro;
    }

    public void setNomeEnfermeiro(String nomeEnfermeiro)
    {
        this.nomeEnfermeiro = nomeEnfermeiro;
    }

    public Date getDataRegistoPesq1()
    {
        return dataRegistoPesq1;
    }

    public void setDataRegistoPesq1(Date dataRegistoPesq1)
    {
        this.dataRegistoPesq1 = dataRegistoPesq1;
    }

    public Date getDataRegistoPesq2()
    {
        return dataRegistoPesq2;
    }

    public void setDataRegistoPesq2(Date dataRegistoPesq2)
    {
        this.dataRegistoPesq2 = dataRegistoPesq2;
    }
    
    
}
