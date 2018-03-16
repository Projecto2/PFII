/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhFerias;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhFeriasFacade;
import util.rh.Defs;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhFeriasValidarBean implements Serializable
{

    @EJB
    private RhFeriasFacade feriasFacade;

    /**
     * Creates a new instance of rhFeriasValidarBean
     */
    public RhFeriasValidarBean ()
    {
    }

    public static RhFeriasValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhFeriasValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhFeriasValidarBean");
    }

    public int maximoDeDiasAGozar (RhFerias ferias)
    {
        int dias = 30 - ferias.getDiasDescontar();

        if (dias < 15)
        {
            return 15;
        }

        return dias;
    }

    public boolean validarNovas (RhFerias ferias) throws Exception
    {
        if (ferias.getFkIdFuncionario().getPkIdFuncionario() == null)
        {
            Mensagem.erroMsg("Indique o funcionário para marcar as férias");
            return false;
        }

        if (Defs.RH_DEMITIDO.equalsIgnoreCase(ferias.getFkIdFuncionario().getFkIdEstadoFuncionario().getDescricao()))
        {
            Mensagem.erroMsg("Este funcionário está demitido, impossível marcar férias");
            return false;
        }

        if (Defs.RH_REFORMADO.equalsIgnoreCase(ferias.getFkIdFuncionario().getFkIdEstadoFuncionario().getDescricao()))
        {
            Mensagem.erroMsg("Este funcionário está reformado, impossível marcar férias");
            return false;
        }

        if (ferias.getDiasGozar() < 15)
        {
            Mensagem.erroMsg("O número mínimo de dias à gozar permitidos são 15");
            return false;
        }

        if (ferias.getDiasGozar() > maximoDeDiasAGozar(ferias))
        {
            Mensagem.erroMsg("O número máximo de dias à gozar permitidos são "
                             + maximoDeDiasAGozar(ferias) + ", por causa dos "
                             + ferias.getDiasDescontar() + " a descontar");
            return false;
        }

        //Verificando se o funcionário já tem férias marcadas no ano corrente
        try
        {
            String anoString = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

            Integer anoActual = Integer.parseInt(anoString);

            Date dataJaneiro = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-" + anoActual);
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + anoActual);

            List<RhFerias> feriasList = feriasFacade.findFeriasActivasOuMarcadasFuncionario(ferias.getFkIdFuncionario().getPkIdFuncionario(), dataJaneiro, dataDezembro);
            if (!feriasList.isEmpty())
            {
                String dataFerias = new SimpleDateFormat("dd-MM-yyyy").format(feriasList.get(0).getDataInicio());
                Mensagem.erroMsg("Este funcionário já tem férias marcadas no ano selecionado, tendo início em " + dataFerias);
                return false;
            }
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
        }

        return true;
    }

}
