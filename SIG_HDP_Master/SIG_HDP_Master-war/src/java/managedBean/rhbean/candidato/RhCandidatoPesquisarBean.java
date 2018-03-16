/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.candidato;

import entidade.GrlFicheiroAnexado;
import entidade.RhEstadoCandidato;
import entidade.RhCandidato;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhCandidatoFacade;
import util.ItensAjaxBean;
import util.Mensagem;
import sessao.GrlFicheiroAnexadoFacade;
import util.rh.Defs;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhCandidatoPesquisarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    /**
     * Pegando a interface managedbean ItensAjaxBean
     */
    FacesContext context = FacesContext.getCurrentInstance();
    ItensAjaxBean itensAjaxBean = (ItensAjaxBean) context.getELContext().getELResolver()
            .getValue(context.getELContext(), null, "itensAjaxBean");

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;

    @EJB
    private RhCandidatoFacade candidatoFacade;

    /**
     * Entidades
     */
    private RhCandidato candidatoPesquisa, candidatoVisualizar;

    private List<RhCandidato> candidatosPesquisados;

    /**
     * Creates a new instance of candidatoBean
     */
    public RhCandidatoPesquisarBean ()
    {
    }

    public static RhCandidatoPesquisarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhCandidatoPesquisarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhCandidatoPesquisarBean");
    }
    
    public String limparPesquisa ()
    {
        candidatoPesquisa = candidatoVisualizar = null;
        candidatosPesquisados = null;

        return "candidatoPesquisarRh.xhtml?faces-redirect=true";
    }

    public RhCandidato getCandidatoPesquisa ()
    {
        if (candidatoPesquisa == null)
        {
            candidatoPesquisa = RhCandidatoNovoBean.getInstancia();
            candidatoPesquisa.getFkIdEstadoCandidato().setPkIdEstadoCandidato(null);
        }
        return candidatoPesquisa;
    }

    public void setCandidatoPesquisa (RhCandidato candidatoPesquisa)
    {
        this.candidatoPesquisa = candidatoPesquisa;
    }

    public RhCandidato getCandidatoVisualizar ()
    {
        return candidatoVisualizar;
    }

    public void setCandidatoVisualizar (RhCandidato candidatoVisualizar)
    {
        this.candidatoVisualizar = candidatoVisualizar;
    }

    public List<RhCandidato> getCandidatosPesquisados ()
    {
        return candidatosPesquisados;
    }

    public void pesquisarCandidatos ()
    {
        candidatosPesquisados = candidatoFacade.findCandidato(candidatoPesquisa);

        if (candidatosPesquisados.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + candidatosPesquisados.size() + ")");
        }
    }

    public String alterarEstado ()
    {
        try
        {
            userTransaction.begin();

            int idEstado = candidatoVisualizar.getFkIdEstadoCandidato().getPkIdEstadoCandidato();
            candidatoVisualizar.setFkIdEstadoCandidato(new RhEstadoCandidato(idEstado));
            candidatoFacade.edit(candidatoVisualizar);

            userTransaction.commit();

            Mensagem.sucessoMsg("Estado do candidato alterado com sucesso!");
            candidatoVisualizar = null;
            pesquisarCandidatos();
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

        return null;
    }

    public String remove (RhCandidato candidatoRemover)
    {
        try
        {
            userTransaction.begin();

            if (Defs.RH_CONTRATADO.equals(candidatoRemover.getFkIdEstadoCandidato().getDescricao()))
            {
                throw new Exception("Impossível remover candidato contratado");
            }

            candidatoFacade.remove(candidatoRemover);
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdBi());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdCurriculum());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdCertificadoDeHabilitacoes());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdEquivalenciaDoCertificado());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdComprovativoOrdemMedicosEnfermeiros());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdCedulaCarteiraProfissional());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdAtestadoMedico());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdRegistoCriminal());
            ficheiroAnexadoFacade.remove(candidatoRemover.getFkIdDocumentoMilitarHomens());

            removerAnexo(candidatoRemover.getFkIdBi(), false);
            removerAnexo(candidatoRemover.getFkIdCurriculum(), false);
            removerAnexo(candidatoRemover.getFkIdCertificadoDeHabilitacoes(), false);
            removerAnexo(candidatoRemover.getFkIdEquivalenciaDoCertificado(), false);
            removerAnexo(candidatoRemover.getFkIdComprovativoOrdemMedicosEnfermeiros(), false);
            removerAnexo(candidatoRemover.getFkIdCedulaCarteiraProfissional(), false);
            removerAnexo(candidatoRemover.getFkIdAtestadoMedico(), false);
            removerAnexo(candidatoRemover.getFkIdRegistoCriminal(), false);
            removerAnexo(candidatoRemover.getFkIdDocumentoMilitarHomens(), false);

            userTransaction.commit();

            Mensagem.sucessoMsg("Candidato removido com sucesso!");
            pesquisarCandidatos();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este candidato possui registro de actividades, impossível remover !");
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
        boolean b = UploadFicheiro.getInstance().apagarFicheiro(new java.io.File(Defs.DESTINO_ANEXO_CANDIDATO + anexo.getFicheiro()));

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

}
