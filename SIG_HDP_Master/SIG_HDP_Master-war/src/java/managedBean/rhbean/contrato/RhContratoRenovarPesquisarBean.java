/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.contrato;

import entidade.RhContrato;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlCentroHospitalarFacade;
import sessao.RhContratoFacade;
import sessao.RhEstadoCandidatoFacade;
import sessao.RhEstadoContratoFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhFuncionarioHasRhSubsidioFacade;
import sessao.RhSubsidioFacade;
import sessao.RhTipoContratoFacade;
import sessao.RhTipoFuncionarioFacade;
import util.rh.Defs;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhContratoRenovarPesquisarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;
    @EJB
    private RhSubsidioFacade subsidioFacade;
    @EJB
    private RhFuncionarioHasRhSubsidioFacade funcionarioHasRhSubsidioFacade;
    @EJB
    private RhTipoContratoFacade tipoContratoFacade;
    @EJB
    private RhEstadoContratoFacade estadoContratoFacade;
    @EJB
    private RhTipoFuncionarioFacade tipoFuncionarioFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhEstadoCandidatoFacade estadoCandidatoFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;

    @EJB
    private RhContratoFacade contratoFacade;

    private RhContrato contratoPesquisa, contratoVisualizar;

    private Date dataCadastroInf, dataCadastroSup, dataInicioInf, dataInicioSup, dataTerminoInf, dataTerminoSup;

    private List<RhContrato> contratosPesquisadosList;

    /**
     * Creates a new instance of contratoBean
     */
    public RhContratoRenovarPesquisarBean ()
    {
    }

    public RhContrato getContratoPesquisa ()
    {
        if (contratoPesquisa == null)
        {
            contratoPesquisa = RhContratoNovoBean.getInstanciaContrato();
        }
        return contratoPesquisa;
    }

    public void setContratoPesquisa (RhContrato contratoPesquisa)
    {
        this.contratoPesquisa = contratoPesquisa;
    }

    public RhContrato getContratoVisualizar ()
    {
        if (contratoVisualizar == null)
        {
            contratoVisualizar = RhContratoNovoBean.getInstanciaContrato();
        }
        return contratoVisualizar;
    }

    public void setContratoVisualizar (RhContrato contratoVisualizar)
    {
        this.contratoVisualizar = contratoVisualizar;
    }

    public Date getDataCadastroInf ()
    {
        return dataCadastroInf;
    }

    public void setDataCadastroInf (Date dataCadastroInf)
    {
        this.dataCadastroInf = dataCadastroInf;
    }

    public Date getDataCadastroSup ()
    {
        return dataCadastroSup;
    }

    public void setDataCadastroSup (Date dataCadastroSup)
    {
        this.dataCadastroSup = dataCadastroSup;
    }

    public Date getDataInicioInf ()
    {
        return dataInicioInf;
    }

    public void setDataInicioInf (Date dataInicioInf)
    {
        this.dataInicioInf = dataInicioInf;
    }

    public Date getDataInicioSup ()
    {
        return dataInicioSup;
    }

    public void setDataInicioSup (Date dataInicioSup)
    {
        this.dataInicioSup = dataInicioSup;
    }

    public Date getDataTerminoInf ()
    {
        return dataTerminoInf;
    }

    public void setDataTerminoInf (Date dataTerminoInf)
    {
        this.dataTerminoInf = dataTerminoInf;
    }

    public Date getDataTerminoSup ()
    {
        return dataTerminoSup;
    }

    public void setDataTerminoSup (Date dataTerminoSup)
    {
        this.dataTerminoSup = dataTerminoSup;
    }

    public List<RhContrato> getContratosPesquisadosList ()
    {
        return contratosPesquisadosList;
    }

    public void cancelarContrato ()
    {
        try
        {
            userTransaction.begin();

            contratoVisualizar.setFkIdEstadoContrato(estadoContratoFacade.pesquisaPorDescricao(Defs.RH_CANCELADO).get(0));
            contratoVisualizar.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(Defs.RH_INACTIVO).get(0));

            funcionarioFacade.edit(contratoVisualizar.getFkIdFuncionario());
            contratoFacade.edit(contratoVisualizar);

            userTransaction.commit();

            Mensagem.sucessoMsg("Contrato cancelado com sucesso!");
            limparPesquisa();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

    }
    
    public void anularCancelamento ()
    {
        try
        {
            userTransaction.begin();

            contratoVisualizar.setFkIdEstadoContrato(estadoContratoFacade.pesquisaPorDescricao(Defs.RH_ACTIVO).get(0));
            
            contratoFacade.edit(contratoVisualizar);

            userTransaction.commit();

            Mensagem.sucessoMsg("Cancelamento anulado com sucesso, contrato reativado!");
            limparPesquisa();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

    }

    public void pesquisarContratos ()
    {
        contratosPesquisadosList = contratoFacade.findContrato(contratoPesquisa);

        if (contratosPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + contratosPesquisadosList.size() + ")");
        }
    }

    public void pesquisarContratosPorIntervaloDeDatas ()
    {
        contratosPesquisadosList = contratoFacade.findContratoPorIntervaloDeDatas(dataCadastroInf, dataCadastroSup,
                                                                                  dataInicioInf, dataInicioSup,
                                                                                  dataTerminoInf, dataTerminoSup);
        if (contratosPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + contratosPesquisadosList.size() + ")");
        }
    }

    public String limparPesquisa ()
    {
        dataCadastroInf = dataCadastroSup = dataInicioInf = dataInicioSup = dataTerminoInf = dataTerminoSup = null;
        contratoVisualizar = contratoPesquisa = null;
        contratosPesquisadosList = null;

        return "contratoRenovarPesquisarRh.xhtml?faces-redirect=true";
    }

    public boolean disabledRenovarContrato ()
    {
        if (contratoVisualizar == null)
        {
            return false;
        }
        return (Defs.RH_CANCELADO).equalsIgnoreCase(contratoVisualizar.getFkIdEstadoContrato().getDescricao());
    }

    public boolean disabledCancelarContrato ()
    {
        if (contratoVisualizar == null)
        {
            return false;
        }
        return !(Defs.RH_ACTIVO).equalsIgnoreCase(contratoVisualizar.getFkIdEstadoContrato().getDescricao());
    }
    
    public boolean disabledAnularCancelamento ()
    {
        if (contratoVisualizar == null)
        {
            return false;
        }
        return !(Defs.RH_CANCELADO).equalsIgnoreCase(contratoVisualizar.getFkIdEstadoContrato().getDescricao());
    }

}
