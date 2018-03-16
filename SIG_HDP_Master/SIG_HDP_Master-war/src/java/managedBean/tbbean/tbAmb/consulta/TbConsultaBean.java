/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tbAmb.consulta;

import entidade.GrlCentroHospitalar;
import entidade.SupiMedicoHasEscala;
import entidade.TbConsulta;
import entidade.TbObservacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import managedBean.tbbean.tbAmb.triagem.TbTriagemBean;
import sessao.TbConsultaFacade;
import util.GeradorCodigo;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbConsultaBean implements Serializable
{
    @EJB
    private TbConsultaFacade tbConsultaFacade;
    
    private TbConsulta tbConsulta;
    
    /**
     * Creates a new instance of TbConsultaBean
     */
    public TbConsultaBean()
    {
    }
    
    public static TbConsultaBean getInstanciaBean()
    {
        return (TbConsultaBean) GeradorCodigo.getInstanciaBean("tbConsultaBean");
    }
    
    public static TbConsulta getInstancia()
    {
        TbConsulta c = new TbConsulta();
        c.setFkCentro(new GrlCentroHospitalar());
        c.setFkMedico(new SupiMedicoHasEscala());
        c.setFkObservacao(new TbObservacao());
        c.setFkTriagem(TbTriagemBean.getInstancia());
        return c;
    }
    
    public List<TbConsulta> getTbConsultas()
    {
        return tbConsultaFacade.findAll();
    }
    
    public TbConsulta getTbConsulta()
    {
        return tbConsulta;
    }
    
    
    
}
