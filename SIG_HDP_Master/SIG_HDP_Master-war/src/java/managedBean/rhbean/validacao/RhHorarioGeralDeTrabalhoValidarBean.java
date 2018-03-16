/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhHorarioGeralDeTrabalho;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhHorarioGeralDeTrabalhoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhHorarioGeralDeTrabalhoValidarBean implements Serializable
{

    @EJB
    private RhHorarioGeralDeTrabalhoFacade horarioGeralDeTrabalhoFacade;

    /**
     * Creates a new instance of rhHorarioGeralDeTrabalhoValidarBean
     */
    public RhHorarioGeralDeTrabalhoValidarBean ()
    {
    }

    public static RhHorarioGeralDeTrabalhoValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhHorarioGeralDeTrabalhoValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhHorarioGeralDeTrabalhoValidarBean");
    }

    public boolean validarNovo (RhHorarioGeralDeTrabalho horarioGeralDeTrabalho) throws Exception
    {

        return true;
    }

}
