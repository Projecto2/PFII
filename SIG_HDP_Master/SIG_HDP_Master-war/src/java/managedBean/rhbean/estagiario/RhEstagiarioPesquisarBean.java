/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.estagiario;

import entidade.GrlFicheiroAnexado;
import entidade.RhEstadoEstagiario;
import entidade.RhEstagiario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhEstagiarioFacade;
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
public class RhEstagiarioPesquisarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;

    @EJB
    private RhEstagiarioFacade estagiarioFacade;

    /**
     * Entidades
     */
    private RhEstagiario estagiarioPesquisa, estagiarioVisualizar;

    private List<RhEstagiario> estagiariosPesquisados;

    /**
     * Creates a new instance of estagiarioBean
     */
    public RhEstagiarioPesquisarBean ()
    {
    }

    public static RhEstagiarioPesquisarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhEstagiarioPesquisarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhEstagiarioPesquisarBean");
    }

    public RhEstagiario getEstagiarioPesquisa ()
    {
        if (estagiarioPesquisa == null)
        {
            estagiarioPesquisa = RhEstagiarioNovoBean.getInstancia();
            estagiarioPesquisa.getFkIdEstadoEstagiario().setPkIdEstadoEstagiario(null);

        }
        return estagiarioPesquisa;
    }

    public void setEstagiarioPesquisa (RhEstagiario estagiarioPesquisa)
    {
        this.estagiarioPesquisa = estagiarioPesquisa;
    }

    public RhEstagiario getEstagiarioVisualizar ()
    {
        return estagiarioVisualizar;
    }

    public void setEstagiarioVisualizar (RhEstagiario estagiarioVisualizar)
    {
        this.estagiarioVisualizar = estagiarioVisualizar;
    }

    public List<RhEstagiario> getEstagiariosPesquisados ()
    {
        return estagiariosPesquisados;
    }

    public void pesquisarEstagiarios ()
    {
        estagiariosPesquisados = estagiarioFacade.findEstagiario(estagiarioPesquisa);

        if (estagiariosPesquisados.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + estagiariosPesquisados.size() + ")");
        }
    }

    public String limparPesquisa ()
    {
        estagiarioPesquisa = estagiarioVisualizar = null;
        estagiariosPesquisados = null;

        return "estagiarioPesquisarRh.xhtml?faces-redirect=true";
    }

    public String alterarEstado ()
    {
        try
        {
            userTransaction.begin();

            int idEstado = estagiarioVisualizar.getFkIdEstadoEstagiario().getPkIdEstadoEstagiario();
            estagiarioVisualizar.setFkIdEstadoEstagiario(new RhEstadoEstagiario(idEstado));
            estagiarioFacade.edit(estagiarioVisualizar);

            userTransaction.commit();

            Mensagem.sucessoMsg("Estado do estagiário alterado com sucesso!");
            estagiarioVisualizar = null;
            pesquisarEstagiarios();
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

    public String remove (RhEstagiario estagiarioRemover)
    {
        try
        {
            userTransaction.begin();

            if ("Activo".equals(estagiarioRemover.getFkIdEstadoEstagiario().getDescricao()))
            {
                throw new Exception("Impossível remover estagiário contratado");
            }

            estagiarioFacade.remove(estagiarioRemover);
            ficheiroAnexadoFacade.remove(estagiarioRemover.getFkIdBi());
            ficheiroAnexadoFacade.remove(estagiarioRemover.getFkIdCurriculum());
            ficheiroAnexadoFacade.remove(estagiarioRemover.getFkIdDocumentoEscolar());
            removerAnexo(estagiarioRemover.getFkIdBi(), false);
            removerAnexo(estagiarioRemover.getFkIdCurriculum(), false);
            removerAnexo(estagiarioRemover.getFkIdDocumentoEscolar(), false);

            userTransaction.commit();

            Mensagem.sucessoMsg("Estagiário removido com sucesso!");
            pesquisarEstagiarios();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este estagiário possui registro de actividades, impossível remover !");
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
        boolean b = UploadFicheiro.getInstance().apagarFicheiro(new java.io.File(Defs.DESTINO_ANEXO_ESTAGIARIO + anexo.getFicheiro()));

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
