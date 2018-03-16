/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.candidato;

import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.RhCandidato;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhCandidatoFacade;
import util.ItensAjaxBean;
import util.Mensagem;
import managedBean.rhbean.RhPessoaBean;
import managedBean.rhbean.validacao.RhCandidatoValidarBean;
import sessao.GrlEspecialidadeFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhCategoriaProfissionalFacade;
import sessao.RhEstadoCandidatoFacade;
import sessao.RhFuncaoFacade;
import sessao.RhNivelAcademicoFacade;
import sessao.RhProfissaoFacade;
import util.rh.Defs;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhCandidatoEditarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private RhCandidatoFacade candidatoFacade;
    @EJB
    private RhNivelAcademicoFacade nivelAcademicoFacade;
    @EJB
    private RhProfissaoFacade profissaoFacade;
    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;
    @EJB
    private RhEstadoCandidatoFacade estadoCandidatoFacade;
    @EJB
    private RhFuncaoFacade funcaoFacade;
    @EJB
    private GrlEspecialidadeFacade especialidadeFacade;

    /**
     * Entidades
     */
    private RhCandidato candidato;

    private Part anexoCarregado;
    private String tipoAnexo;

    /**
     * Creates a new instance of candidatoBean
     */
    public RhCandidatoEditarBean ()
    {
    }

    public String limpar ()
    {
        candidato = null;
        anexoCarregado = null;
        tipoAnexo = null;

        return "candidatoEditarRh.xhtml?faces-redirect=true";
    }

    public RhCandidato getCandidato ()
    {
        if (candidato == null)
        {
            candidato = RhCandidatoNovoBean.getInstancia();
        }
        if (candidato.getFkIdEspecialidade1() == null)
        {
            candidato.setFkIdEspecialidade1(new GrlEspecialidade());
        }
        if (candidato.getFkIdEspecialidade2() == null)
        {
            candidato.setFkIdEspecialidade2(new GrlEspecialidade());
        }
        return candidato;
    }

    public void setCandidato (RhCandidato candidato)
    {
        this.candidato = candidato;
    }

    public Part getAnexoCarregado ()
    {
        return anexoCarregado;
    }

    public void setAnexoCarregado (Part anexoCarregado)
    {
        this.anexoCarregado = anexoCarregado;
    }

    public String getTipoAnexo ()
    {
        return tipoAnexo;
    }

    public void setTipoAnexo (String tipoAnexo)
    {
        this.tipoAnexo = tipoAnexo;
    }

    public String sair ()
    {
        limpar();
        return RhCandidatoPesquisarBean.getInstanciaBean().limparPesquisa();
    }

    public void limparEspecialidadesChange (javax.faces.event.AjaxBehaviorEvent e)
    {
        candidato.getFkIdEspecialidade1().setPkIdEspecialidade(null);
        candidato.getFkIdEspecialidade2().setPkIdEspecialidade(null);
    }

    public void limparEspecialidade2Change (javax.faces.event.AjaxBehaviorEvent e)
    {
        candidato.getFkIdEspecialidade2().setPkIdEspecialidade(null);
    }

    public String prepararEditar (RhCandidato candidato)
    {
        limpar();

        if (candidato.getFkIdProfissao() == null)
        {
            candidato.setFkIdProfissao(new RhProfissao());
        }
        if (candidato.getFkIdEspecialidade1() == null)
        {
            candidato.setFkIdEspecialidade1(new GrlEspecialidade());
        }
        if (candidato.getFkIdEspecialidade2() == null)
        {
            candidato.setFkIdEspecialidade2(new GrlEspecialidade());
        }

        ItensAjaxBean itensAjaxBean = ItensAjaxBean.getInstanciaBean();

        if (candidato.getFkIdProfissao().getPkIdProfissao() != null)
        {
            itensAjaxBean.setProfissao(candidato.getFkIdProfissao().getPkIdProfissao());
        }

        this.candidato = candidato;

        return "candidatoEditarRh.xhtml?faces-redirect=true";
    }

    public String edit ()
    {
        try
        {
            RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();

            candidato.setFkIdPessoa(pessoaBean.getPessoa());

            RhCandidatoValidarBean validarCandidato = RhCandidatoValidarBean.getInstanciaBean();

            if (!validarCandidato.validarEditar(candidato))
            {
                return null;
            }

            pessoaBean.gravarPessoa();

            userTransaction.begin();

            candidato.setFkIdPessoa(pessoaBean.getPessoa());

            actualizarForeignKeys();

            if (candidato.getFkIdEspecialidade1().getPkIdEspecialidade() == null)
            {
                candidato.setFkIdEspecialidade1(null);
            }
            if (candidato.getFkIdEspecialidade2().getPkIdEspecialidade() == null)
            {
                candidato.setFkIdEspecialidade2(null);
            }

            ficheiroAnexadoFacade.edit(candidato.getFkIdBi());
            ficheiroAnexadoFacade.edit(candidato.getFkIdCurriculum());
            ficheiroAnexadoFacade.edit(candidato.getFkIdCertificadoDeHabilitacoes());
            ficheiroAnexadoFacade.edit(candidato.getFkIdEquivalenciaDoCertificado());
            ficheiroAnexadoFacade.edit(candidato.getFkIdComprovativoOrdemMedicosEnfermeiros());
            ficheiroAnexadoFacade.edit(candidato.getFkIdCedulaCarteiraProfissional());
            ficheiroAnexadoFacade.edit(candidato.getFkIdAtestadoMedico());
            ficheiroAnexadoFacade.edit(candidato.getFkIdRegistoCriminal());
            ficheiroAnexadoFacade.edit(candidato.getFkIdDocumentoMilitarHomens());

            candidatoFacade.edit(candidato);

            userTransaction.commit();

            Mensagem.sucessoMsg("Candidato editado com sucesso!");
            candidato.setPkIdCandidato(null);

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

    private void actualizarForeignKeys ()
    {
        if (candidato.getFkIdEstadoCandidato().getPkIdEstadoCandidato() != null)
        {
            candidato.setFkIdEstadoCandidato(estadoCandidatoFacade.find(candidato.getFkIdEstadoCandidato().getPkIdEstadoCandidato()));
        }
        if (candidato.getFkIdNivelAcademico().getPkIdNivelAcademico() != null)
        {
            candidato.setFkIdNivelAcademico(nivelAcademicoFacade.find(candidato.getFkIdNivelAcademico().getPkIdNivelAcademico()));
        }
        if (candidato.getFkIdEspecialidade1().getPkIdEspecialidade() != null)
        {
            candidato.setFkIdEspecialidade1(especialidadeFacade.find(candidato.getFkIdEspecialidade1().getPkIdEspecialidade()));
        }
        if (candidato.getFkIdEspecialidade2().getPkIdEspecialidade() != null)
        {
            candidato.setFkIdEspecialidade2(especialidadeFacade.find(candidato.getFkIdEspecialidade2().getPkIdEspecialidade()));
        }
        if (candidato.getFkIdProfissao().getPkIdProfissao() != null)
        {
            candidato.setFkIdProfissao(profissaoFacade.find(candidato.getFkIdProfissao().getPkIdProfissao()));
        }
        if (candidato.getFkIdCategoriaPretendida().getPkIdCategoriaProfissional() != null)
        {
            candidato.setFkIdCategoriaPretendida(categoriaProfissionalFacade.find(candidato.getFkIdCategoriaPretendida().getPkIdCategoriaProfissional()));
        }
    }

    public String uploadAnexo ()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, Defs.DESTINO_ANEXO_CANDIDATO, tipoAnexo);

            if ("BI".equals(tipoAnexo))
            {
                candidato.getFkIdBi().setFicheiro(novoNome);
            }
            else if ("CURRICULUM".equals(tipoAnexo))
            {
                candidato.getFkIdCurriculum().setFicheiro(novoNome);
            }
            else if ("CERTIFICADO_DE_HABILITACOES".equals(tipoAnexo))
            {
                candidato.getFkIdCertificadoDeHabilitacoes().setFicheiro(novoNome);
            }
            else if ("EQUIVALENCIA_DO_CERTIFICADO".equals(tipoAnexo))
            {
                candidato.getFkIdCertificadoDeHabilitacoes().setFicheiro(novoNome);
            }
            else if ("COMPROVATIVO_ORDEM_MEDICOS_ENFERMEIROS".equals(tipoAnexo))
            {
                candidato.getFkIdComprovativoOrdemMedicosEnfermeiros().setFicheiro(novoNome);
            }
            else if ("CEDULA_CARTEIRA_PROFISSIONAL".equals(tipoAnexo))
            {
                candidato.getFkIdCedulaCarteiraProfissional().setFicheiro(novoNome);
            }
            else if ("ATESTADO_MEDICO".equals(tipoAnexo))
            {
                candidato.getFkIdAtestadoMedico().setFicheiro(novoNome);
            }
            else if ("REGISTO_CRIMINAL".equals(tipoAnexo))
            {
                candidato.getFkIdRegistoCriminal().setFicheiro(novoNome);
            }
            else if ("DOCUMENTO_MILITAR_HOMENS".equals(tipoAnexo))
            {
                candidato.getFkIdDocumentoMilitarHomens().setFicheiro(novoNome);
            }

            anexoCarregado = null;
            tipoAnexo = null;

            System.out.println("Anexo carregado com sucesso !");
            return "candidatoEditarRh.xhtml?faces-redirect=true";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Mensagem.erroMsg(e.getMessage());
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
