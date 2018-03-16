/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.estagiario;

import entidade.GrlFicheiroAnexado;
import entidade.RhEstadoEstagiario;
import entidade.RhEstagiario;
import entidade.RhEscolaUniversidade;
import entidade.RhEspecialidadeCurso;
import entidade.RhNivelAcademico;
import entidade.RhPeriodoAulas;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.RhTipoEstagio;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
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
import sessao.RhEstadoEstagiarioFacade;
import util.rh.Defs;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhEstagiarioNovoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

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
    private boolean anexosFlag;

    /**
     * Creates a new instance of estagiarioBean
     */
    public RhEstagiarioNovoBean ()
    {
    }

    public static RhEstagiario getInstancia ()
    {
        RhEstagiario estag = new RhEstagiario();
        estag.setFkIdPessoa(RhPessoaBean.getInstancia());
        estag.setFkIdTipoEstagio(new RhTipoEstagio());
        estag.setFkIdEstadoEstagiario(new RhEstadoEstagiario(1));
        estag.setFkIdNivelAcademico(new RhNivelAcademico(4));
        estag.setFkIdEspecialidadeCurso(new RhEspecialidadeCurso());
        estag.setFkIdProfissao(new RhProfissao());
        estag.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        estag.setFkIdEscolaUniversidade(new RhEscolaUniversidade());
        estag.setFkIdPeriodoAulas(new RhPeriodoAulas());

        estag.setFkIdBi(new GrlFicheiroAnexado());
        estag.setFkIdCurriculum(new GrlFicheiroAnexado());
        estag.setFkIdDocumentoEscolar(new GrlFicheiroAnexado());

        return estag;
    }

    public RhEstagiario getEstagiario ()
    {
        if (estagiario == null)
        {
            estagiario = getInstancia();
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

    public boolean isAnexosFlag ()
    {
        return anexosFlag;
    }

    public void activarAnexos ()
    {
        anexosFlag = true;
    }

    public boolean disabledDatas ()
    {
        return !Defs.RH_APROVADO.equals(getEstagiario().getFkIdEstadoEstagiario().getDescricao())
               && !Defs.RH_ACTIVO.equals(getEstagiario().getFkIdEstadoEstagiario().getDescricao())
               && !Defs.RH_ESTAGIO_FINALIZADO.equals(getEstagiario().getFkIdEstadoEstagiario().getDescricao());
    }

    public String limpar ()
    {
        estagiario = null;
        anexoCarregado = null;
        tipoAnexo = null;
        anexosFlag = false;

        return "/faces/rhVisao/rhIngresso/rhEstagiario/estagiarioNovoRh.xhtml?faces-redirect=true";
    }

    public String sair ()
    {
        limpar();

        return RhEstagiarioPesquisarBean.getInstanciaBean().limparPesquisa();
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

    public String create ()
    {
        try
        {
            RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();

            estagiario.setFkIdPessoa(pessoaBean.getPessoa());

            RhEstagiarioValidarBean validarEstagiario = RhEstagiarioValidarBean.getInstanciaBean();

            if (!validarEstagiario.validarNovo(estagiario))
            {
                return null;
            }

            pessoaBean.gravarPessoa();
            userTransaction.begin();

            estagiario.setFkIdPessoa(pessoaBean.getPessoa());
            estagiario.setCodigoEstagiario("E" + estagiario.getFkIdPessoa().getPkIdPessoa());

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

            estagiario.setDataCadastro(Calendar.getInstance().getTime());

            ficheiroAnexadoFacade.create(estagiario.getFkIdBi());
            ficheiroAnexadoFacade.create(estagiario.getFkIdCurriculum());
            ficheiroAnexadoFacade.create(estagiario.getFkIdDocumentoEscolar());

            estagiarioFacade.create(estagiario);

            userTransaction.commit();

            Mensagem.sucessoMsg("Estagiário guardado com sucesso!");
            pessoaBean.setPessoa(null);
            limpar();
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

    public String uploadAnexo ()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, Defs.DESTINO_ANEXO_ESTAGIARIO, tipoAnexo);

            if ("BI".equals(tipoAnexo))
            {
                removerAnexo(estagiario.getFkIdBi(), false);
                estagiario.getFkIdBi().setFicheiro(novoNome);
            }
            else if ("CURRICULUM".equals(tipoAnexo))
            {
                removerAnexo(estagiario.getFkIdCurriculum(), false);
                estagiario.getFkIdCurriculum().setFicheiro(novoNome);
            }
            else if ("DOC_ESCOLAR".equals(tipoAnexo))
            {
                removerAnexo(estagiario.getFkIdDocumentoEscolar(), false);
                estagiario.getFkIdDocumentoEscolar().setFicheiro(novoNome);
            }

            anexoCarregado = null;
            tipoAnexo = null;

            System.out.println("Anexo carregado com sucesso !");
            return "estagiarioNovoRh.xhtml?faces-redirect=true";
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
