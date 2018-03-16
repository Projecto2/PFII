/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhContrato;
import entidade.RhTipoContrato;
import java.io.Serializable;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhContratoFacade;
import sessao.RhTipoContratoFacade;
import util.rh.Defs;
import util.Mensagem;
import util.rh.MetodosGerais;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhContratoValidarBean implements Serializable
{

    @EJB
    private RhTipoContratoFacade tipoContratoFacade;
    @EJB
    private RhContratoFacade contratoFacade;

    /**
     * Creates a new instance of rhContratoValidarBean
     */
    public RhContratoValidarBean ()
    {
    }

    public static RhContratoValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhContratoValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhContratoValidarBean");
    }
    
    public boolean validarNovo (RhContrato contrato) throws Exception
    {
        if (contrato.getDataInicio().before(MetodosGerais.getDataDeHojeSemHora()))
        {
            Mensagem.erroMsg("Data de início do contrato inválida");
            return false;
        }

        if (contrato.getFkIdFuncionario().getFkIdCentroHospitalar().getPkIdCentro() == null)
        {
            Mensagem.erroMsg("Indique o centro hospitalar");
            return false;
        }

        RhTipoContrato tipCont = tipoContratoFacade.find(contrato.getFkIdTipoContrato().getPkIdTipoContrato());
        if (tipCont.getDescricao().equalsIgnoreCase(Defs.RH_DETERMINADO) && contrato.getDuracaoMeses() <= 0)
        {
            Mensagem.erroMsg("Duração inválida para o contrato do tipo determinado, " + contrato.getDuracaoMeses() + " meses !");
            return false;
        }

        return true;
    }

    public boolean validarRenovar (RhContrato contrato, RhContrato contratoAnterior) throws Exception
    {
        if (contratoAnterior.getDataInicio().after(Calendar.getInstance().getTime()))
        {
            Mensagem.erroMsg("Impossível renovar porque o contrato anterior ainda não teve início");
            return false;
        }
        if (contratoAnterior.getDataTermino() == null)
        {
            Mensagem.erroMsg("Impossível renovar porque o contrato anterior é de tempo indeterminado");
            return false;
        }
        if (!contrato.getDataInicio().after(contratoAnterior.getDataTermino()))
        {
            Mensagem.erroMsg("A data de início do contrato de renovação deve ser superior à do contrato anterior");
            return false;
        }
        if (contrato.getFkIdFuncionario().getFkIdCentroHospitalar().getPkIdCentro() == null)
        {
            Mensagem.erroMsg("Indique o centro hospitalar");
            return false;
        }

        if (contrato.getFkIdFuncionario().getFkIdCentroHospitalar().getPkIdCentro() == null)
        {
            Mensagem.erroMsg("Indique o centro hospitalar");
            return false;
        }

        RhTipoContrato tipCont = tipoContratoFacade.find(contrato.getFkIdTipoContrato().getPkIdTipoContrato());
        if (tipCont.getDescricao().equalsIgnoreCase(Defs.RH_DETERMINADO) && contrato.getDuracaoMeses() <= 0)
        {
            Mensagem.erroMsg("Duração inválida para o contrato do tipo determinado, " + contrato.getDuracaoMeses() + " meses !");
            return false;
        }

        return true;
    }

}
