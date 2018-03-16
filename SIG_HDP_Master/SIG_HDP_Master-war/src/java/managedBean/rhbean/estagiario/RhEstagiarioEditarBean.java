/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.estagiario;

import entidade.GrlFicheiroAnexado;
import entidade.RhSeccaoTrabalho;
import entidade.RhEstagiario;
import entidade.RhEscolaUniversidade;
import entidade.RhEspecialidadeCurso;
import entidade.RhEstadoEstagiario;
import entidade.RhFuncao;
import entidade.RhPeriodoAulas;
import entidade.RhProfissao;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhEstagiarioFacade;
import util.Mensagem;
import managedBean.rhbean.RhPessoaBean;
import managedBean.rhbean.validacao.RhEstagiarioValidarBean;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhSeccaoTrabalhoFacade;
import sessao.RhEscolaUniversidadeFacade;
import sessao.RhEspecialidadeCursoFacade;
import sessao.RhEstadoEstagiarioFacade;
import sessao.RhFuncaoFacade;
import sessao.RhNivelAcademicoFacade;
import sessao.RhProfissaoFacade;
import sessao.RhTipoEstagioFacade;
import sessao.RhPeriodoAulasFacade;
import util.rh.Defs;
import util.ItensAjaxBean;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhEstagiarioEditarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhProfissaoFacade profissaoFacade;
    @EJB
    private RhNivelAcademicoFacade nivelAcademicoFacade;
    @EJB
    private RhTipoEstagioFacade tipoEstagioFacade;
    @EJB
    private RhSeccaoTrabalhoFacade seccaoTrabalhoFacade;
    @EJB
    private RhFuncaoFacade funcaoFacade;
    @EJB
    private RhEscolaUniversidadeFacade escolaUniversidadeFacade;
    @EJB
    private RhEspecialidadeCursoFacade especialidadeCursoFacade;
    @EJB
    private RhPeriodoAulasFacade periodoAulasFacade;
    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private RhEstadoEstagiarioFacade estadoEstagiarioFacade;
    @EJB
    private RhEstagiarioFacade estagiarioFacade;

    /**
     * Entidades
     */
    private RhEstagiario estagiario;

    private Part anexoCarregado;

    private String tipoAnexo;

    /**
     * Creates a new instance of estagiarioEditarBean
     */
    public RhEstagiarioEditarBean ()
    {
    }

    public RhEstagiario getEstagiario ()
    {
        if (estagiario == null)
        {
            estagiario = RhEstagiarioNovoBean.getInstancia();
        }
        return estagiario;
    }

    public void setEstagiario (RhEstagiario estagiario)
    {
        this.estagiario = estagiario;
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

    public String limpar ()
    {
        estagiario = null;
        anexoCarregado = null;
        tipoAnexo = null;

        return "estagiarioEditarRh.xhtml?faces-redirect=true";
    }

    public boolean disabledDatas ()
    {
        return !Defs.RH_APROVADO.equals(getEstagiario().getFkIdEstadoEstagiario().getDescricao())
               && !Defs.RH_ACTIVO.equals(getEstagiario().getFkIdEstadoEstagiario().getDescricao())
               && !Defs.RH_ESTAGIO_FINALIZADO.equals(getEstagiario().getFkIdEstadoEstagiario().getDescricao());
    }

    public String sair ()
    {
        limpar();

        return RhEstagiarioPesquisarBean.getInstanciaBean().limparPesquisa();
    }

    public String prepararEditar (RhEstagiario estagiario)
    {
        limpar();
        if (estagiario.getFkIdEscolaUniversidade() == null)
        {
            estagiario.setFkIdEscolaUniversidade(new RhEscolaUniversidade());
        }
        if (estagiario.getFkIdEspecialidadeCurso() == null)
        {
            estagiario.setFkIdEspecialidadeCurso(new RhEspecialidadeCurso());
        }
        if (estagiario.getFkIdSeccaoTrabalho() == null)
        {
            estagiario.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        }
        if (estagiario.getFkIdProfissao() == null)
        {
            estagiario.setFkIdProfissao(new RhProfissao());
        }
        if (estagiario.getFkIdPeriodoAulas() == null)
        {
            estagiario.setFkIdPeriodoAulas(new RhPeriodoAulas());
        }

        ItensAjaxBean itensAjaxBean = ItensAjaxBean.getInstanciaBean();

        if (estagiario.getFkIdEspecialidadeCurso().getPkIdEspecialidadeCurso() != null)
        {
            itensAjaxBean.setCurso(estagiario.getFkIdEspecialidadeCurso().getFkIdCurso().getPkIdCurso());
        }
        else
        {
            itensAjaxBean.setCurso(null);
        }

        this.estagiario = estagiario;

        return "estagiarioEditarRh.xhtml?faces-redirect=true";
    }

    public void changeEstadoEstagiario (ValueChangeEvent e)
    {
        Integer idEstado = (Integer) e.getNewValue();

        if (idEstado != null)
        {
            RhEstadoEstagiario estad = estadoEstagiarioFacade.find(idEstado);
            estagiario.setFkIdEstadoEstagiario(estad);
            estagiario.setDataInicioEstagio(null);
            estagiario.setDataFimEstagio(null);
        }
    }

    public String edit ()
    {
        try
        {
            RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();

            estagiario.setFkIdPessoa(pessoaBean.getPessoa());

            RhEstagiarioValidarBean validarEstagiario = RhEstagiarioValidarBean.getInstanciaBean();

            if (!validarEstagiario.validarEditar(estagiario))
            {
                return null;
            }

            pessoaBean.gravarPessoa();

            userTransaction.begin();

            estagiario.setFkIdPessoa(pessoaBean.getPessoa());

            actualizarForeignKeys();

            if (estagiario.getFkIdEscolaUniversidade().getPkIdEscolaUniversidade() == null)
            {
                estagiario.setFkIdEscolaUniversidade(null);
            }
            if (estagiario.getFkIdEspecialidadeCurso().getPkIdEspecialidadeCurso() == null)
            {
                estagiario.setFkIdEspecialidadeCurso(null);
            }
            if (estagiario.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() == null)
            {
                estagiario.setFkIdSeccaoTrabalho(null);
            }
            if (estagiario.getFkIdProfissao().getPkIdProfissao() == null)
            {
                estagiario.setFkIdProfissao(null);
            }
            if (estagiario.getFkIdPeriodoAulas().getPkIdPeriodoAulas() == null)
            {
                estagiario.setFkIdPeriodoAulas(null);
            }

            ficheiroAnexadoFacade.edit(estagiario.getFkIdBi());
            ficheiroAnexadoFacade.edit(estagiario.getFkIdCurriculum());
            ficheiroAnexadoFacade.edit(estagiario.getFkIdDocumentoEscolar());

            estagiarioFacade.edit(estagiario);

            userTransaction.commit();

            Mensagem.sucessoMsg("Estagiário editado com sucesso!");
            estagiario.setPkIdEstagiario(null);
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
        if (estagiario.getFkIdEstadoEstagiario().getPkIdEstadoEstagiario() != null)
        {
            estagiario.setFkIdEstadoEstagiario(estadoEstagiarioFacade.find(estagiario.getFkIdEstadoEstagiario().getPkIdEstadoEstagiario()));
        }
        if (estagiario.getFkIdTipoEstagio().getPkIdTipoEstagio() != null)
        {
            estagiario.setFkIdTipoEstagio(tipoEstagioFacade.find(estagiario.getFkIdTipoEstagio().getPkIdTipoEstagio()));
        }
        if (estagiario.getFkIdNivelAcademico().getPkIdNivelAcademico() != null)
        {
            estagiario.setFkIdNivelAcademico(nivelAcademicoFacade.find(estagiario.getFkIdNivelAcademico().getPkIdNivelAcademico()));
        }
        if (estagiario.getFkIdEscolaUniversidade().getPkIdEscolaUniversidade() != null)
        {
            estagiario.setFkIdEscolaUniversidade(escolaUniversidadeFacade.find(estagiario.getFkIdEscolaUniversidade().getPkIdEscolaUniversidade()));
        }
        if (estagiario.getFkIdEspecialidadeCurso().getPkIdEspecialidadeCurso() != null)
        {
            estagiario.setFkIdEspecialidadeCurso(especialidadeCursoFacade.find(estagiario.getFkIdEspecialidadeCurso().getPkIdEspecialidadeCurso()));
        }
        if (estagiario.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() != null)
        {
            estagiario.setFkIdSeccaoTrabalho(seccaoTrabalhoFacade.find(estagiario.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho()));
        }
        if (estagiario.getFkIdProfissao().getPkIdProfissao() != null)
        {
            estagiario.setFkIdProfissao(profissaoFacade.find(estagiario.getFkIdProfissao().getPkIdProfissao()));
        }
        if (estagiario.getFkIdPeriodoAulas().getPkIdPeriodoAulas() != null)
        {
            estagiario.setFkIdPeriodoAulas(periodoAulasFacade.find(estagiario.getFkIdPeriodoAulas().getPkIdPeriodoAulas()));
        }
    }

    public String uploadAnexo ()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, Defs.DESTINO_ANEXO_ESTAGIARIO, tipoAnexo);

            if ("BI".equals(tipoAnexo))
            {
                estagiario.getFkIdBi().setFicheiro(novoNome);
            }
            else if ("CURRICULUM".equals(tipoAnexo))
            {
                estagiario.getFkIdCurriculum().setFicheiro(novoNome);
            }
            else if ("DOC_ESCOLAR".equals(tipoAnexo))
            {
                estagiario.getFkIdDocumentoEscolar().setFicheiro(novoNome);
            }

            anexoCarregado = null;
            tipoAnexo = null;

            System.out.println("Anexo carregado com sucesso !");
            return "estagiarioEditarRh.xhtml?faces-redirect=true";
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
