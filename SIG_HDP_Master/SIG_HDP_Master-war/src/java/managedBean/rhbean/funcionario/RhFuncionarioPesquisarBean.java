/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.funcionario;

import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.RhDepartamento;
import entidade.RhFuncionario;
import entidade.RhFuncionarioHasRhSubsidio;
import entidade.RhProfissao;
import entidade.RhSalarioFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhFuncionarioHasRhSubsidioFacade;
import sessao.RhProfissaoFacade;
import util.Mensagem;
import sessao.RhSalarioFuncionarioFacade;
import util.Constantes;
import util.RelatorioJasper;
import util.UploadFicheiro;
import util.rh.Defs;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFuncionarioPesquisarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhProfissaoFacade rhProfissaoFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    private RhSalarioFuncionarioFacade salarioFuncionarioFacade;
    @EJB
    private RhFuncionarioHasRhSubsidioFacade funcionarioHasRhSubsidioFacade;

    /**
     * Entidades
     */
    private RhFuncionario funcionarioPesquisa, funcionarioVisualizar;

    private List<RhFuncionario> funcionariosPesquisadosList;
    private List<RhFuncionario> funcionariosMedicosPesquisadosList;

    /**
     * Creates a new instance of funcionarioBean
     */
    public RhFuncionarioPesquisarBean ()
    {
    }

    public static RhFuncionarioPesquisarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhFuncionarioPesquisarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhFuncionarioPesquisarBean");
    }

    public RhFuncionarioFacade getFuncionarioFacade ()
    {
        return funcionarioFacade;
    }

    public RhFuncionario getFuncionarioPesquisa ()
    {
        if (funcionarioPesquisa == null)
        {
            funcionarioPesquisa = RhFuncionarioNovoBean.getInstancia();
            funcionarioPesquisa.getFkIdSeccaoTrabalho().setFkIdDepartamento(new RhDepartamento());
            funcionarioPesquisa.setFkIdProfissao(new RhProfissao());
            funcionarioPesquisa.setFkIdEspecialidade1(new GrlEspecialidade());
        }
        return funcionarioPesquisa;
    }

    public void setFuncionarioPesquisa (RhFuncionario funcionarioPesquisa)
    {
        this.funcionarioPesquisa = funcionarioPesquisa;
    }

    public RhFuncionario getFuncionarioVisualizar ()
    {
        return funcionarioVisualizar;
    }

    public void setFuncionarioVisualizar (RhFuncionario funcionarioVisualizar)
    {
        this.funcionarioVisualizar = funcionarioVisualizar;

        if (funcionarioVisualizar != null)
        {
            List<RhSalarioFuncionario> salarios;
            salarios = salarioFuncionarioFacade.findActualPorIdFuncionario(funcionarioVisualizar.getPkIdFuncionario());

            List<RhFuncionarioHasRhSubsidio> subsidios;
            subsidios = funcionarioHasRhSubsidioFacade.pesquisaPorFuncionario(funcionarioVisualizar.getPkIdFuncionario());
            
            this.funcionarioVisualizar.setRhFuncionarioHasRhSubsidioList(subsidios);
            this.funcionarioVisualizar.setRhSalarioFuncionarioList(salarios);

            if (salarios.isEmpty())
            {
                funcionarioVisualizar.getRhSalarioFuncionarioList().add(new RhSalarioFuncionario());
            }
        }
    }

    public void setSalarioFuncionarioFacade (RhSalarioFuncionarioFacade salarioFuncionarioFacade)
    {
        this.salarioFuncionarioFacade = salarioFuncionarioFacade;
    }

    public List<RhFuncionario> getFuncionariosPesquisadosList ()
    {
        return funcionariosPesquisadosList;
    }

    public List<RhFuncionario> getFuncionariosMedicosPesquisadosList ()
    {
        return funcionariosMedicosPesquisadosList;
    }

    public boolean renderCentDepartSeccaoFuncionarioVisual ()
    {
        if (funcionarioVisualizar != null)
        {
            if (funcionarioVisualizar.getFkIdCentroHospitalar().getFkIdInstituicao()
                    .getCodigoInstituicao().equalsIgnoreCase(Constantes.HDP))
            {
                return true;
            }
        }
        return false;
    }

    public void pesquisarFuncionarios ()
    {
        funcionarioPesquisa.setFkIdCentroHospitalar(new GrlCentroHospitalar());
        funcionariosPesquisadosList = funcionarioFacade.findFuncionario(funcionarioPesquisa);

        if (funcionariosPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + funcionariosPesquisadosList.size() + ")");
        }
    }

    public void pesquisarFuncionariosMedicosActivos ()
    {
        try
        {
            funcionarioPesquisa.setFkIdProfissao(rhProfissaoFacade.pesquisaPorDescricao("Médico").get(0));
            funcionarioPesquisa.setFkIdCentroHospitalar(centroHospitalarFacade.findCentroHospitalarDivina());
            funcionarioPesquisa.setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(Defs.RH_ACTIVO).get(0));
            funcionariosMedicosPesquisadosList = funcionarioFacade.findFuncionario(funcionarioPesquisa);
            
        }
        catch (Exception e)
        {
            Mensagem.erroMsg("Ocorreu um erro ao pesquisar os médicos");
            System.err.println("PesquisarFuncionariosMedicos");
            System.err.println(e.getMessage());
            e.printStackTrace();
            funcionariosMedicosPesquisadosList = new ArrayList<>();
        }


        if (funcionariosMedicosPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + funcionariosMedicosPesquisadosList.size() + ")");
        }
    }

    public String limparPesquisa ()
    {
        funcionariosPesquisadosList = null;
        funcionarioPesquisa = funcionarioVisualizar = null;

        return "/rhVisao/rhIngresso/rhFuncionario/funcionarioPesquisarRh.xhtml?faces-redirect=true";
    }

    public String limparPesquisaMedicos ()
    {
        limparPesquisa();
        return null;
    }

    public String remove (RhFuncionario funcionarioRemover)
    {
        try
        {
            userTransaction.begin();

            funcionarioFacade.remove(funcionarioRemover);
            ficheiroAnexadoFacade.remove(funcionarioRemover.getFkIdAnexoGuiaTransferencia());
            removerAnexo(funcionarioRemover.getFkIdAnexoGuiaTransferencia(), false);

            userTransaction.commit();

            Mensagem.sucessoMsg("Funcionário removido com sucesso!");
            pesquisarFuncionarios();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este funcionário possui registro de actividades, impossível remover !");
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    /**
     * Remove um determinado anexo(ficheiro) que foi carregado no servidor
     *
     * @param anexo Entidade que contém o nome do ficheiro
     * @param apresentarMensagem flag booleana que indica se o resultado da
     * operação será apresentado
     */
    public void removerAnexo (GrlFicheiroAnexado anexo, boolean apresentarMensagem)
    {
        boolean b = UploadFicheiro.getInstance().apagarFicheiro(new java.io.File(util.rh.Defs.DESTINO_ANEXO_FUNCIONARIO + anexo.getFicheiro()));

        anexo.setFicheiro(null);

        if (apresentarMensagem)
        {
            if (b)
            {
                Mensagem.sucessoMsg("Anexo removido");
            }
            else
            {
                Mensagem.erroMsg("Não foi possível remover o anexo");
            }
        }
    }

    public void imprimirListaDeFuncionariosPDF (ActionEvent evt)
    {
        HashMap<String, Object> parametrosMap = new HashMap<>();
        RelatorioJasper.exportPDF("rh/listaDeFuncionarios.jasper", parametrosMap, funcionariosPesquisadosList);
    }
    
    
    public List<RhFuncionario> getTodosMedicos()
    {
        return funcionarioFacade.findMedicos();
    }

}
