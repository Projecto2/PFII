/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.candidato;

import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.RhCategoriaProfissional;
import entidade.RhEstadoCandidato;
import entidade.RhCandidato;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhCandidatoFacade;
import util.Mensagem;
import managedBean.rhbean.RhPessoaBean;
import managedBean.rhbean.validacao.RhCandidatoValidarBean;
import sessao.GrlFicheiroAnexadoFacade;
import util.rh.Defs;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhCandidatoNovoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;

    @EJB
    private RhCandidatoFacade candidatoFacade;

    /**
     * Entidades
     */
    private RhCandidato candidato;

    private Part anexoCarregado;

    private String tipoAnexo;
    private boolean anexosFlag;

    /**
     * Creates a new instance of candidatoBean
     */
    public RhCandidatoNovoBean ()
    {
    }

    public static RhCandidatoNovoBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhCandidatoNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhCandidatoNovoBean");
    }

    public static RhCandidato getInstancia ()
    {
        RhCandidato cand = new RhCandidato();
        cand.setFkIdPessoa(RhPessoaBean.getInstancia());
        cand.setFkIdEstadoCandidato(new RhEstadoCandidato(1));
        cand.setFkIdNivelAcademico(new RhNivelAcademico(4));
        cand.setFkIdProfissao(new RhProfissao());
        cand.setFkIdCategoriaPretendida(new RhCategoriaProfissional());
        cand.setFkIdEspecialidade1(new GrlEspecialidade());
        cand.setFkIdEspecialidade2(new GrlEspecialidade());

        cand.setFkIdBi(new GrlFicheiroAnexado());
        cand.setFkIdCurriculum(new GrlFicheiroAnexado());
        cand.setFkIdCertificadoDeHabilitacoes(new GrlFicheiroAnexado());
        cand.setFkIdEquivalenciaDoCertificado(new GrlFicheiroAnexado());
        cand.setFkIdComprovativoOrdemMedicosEnfermeiros(new GrlFicheiroAnexado());
        cand.setFkIdCedulaCarteiraProfissional(new GrlFicheiroAnexado());
        cand.setFkIdAtestadoMedico(new GrlFicheiroAnexado());
        cand.setFkIdRegistoCriminal(new GrlFicheiroAnexado());
        cand.setFkIdDocumentoMilitarHomens(new GrlFicheiroAnexado());

        return cand;
    }

    public RhCandidato getCandidato ()
    {
        if (candidato == null)
        {
            candidato = getInstancia();
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

    public boolean isAnexosFlag ()
    {
        return anexosFlag;
    }

    public void activarAnexos ()
    {
        anexosFlag = true;
    }

    public String limpar ()
    {
        candidato = null;
        anexosFlag = false;

        return "/faces/rhVisao/rhIngresso/rhCandidato/candidatoNovoRh.xhtml?faces-redirect=true";
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
    
    public String sair ()
    {
        limpar();
        return RhCandidatoPesquisarBean.getInstanciaBean().limparPesquisa();
    }

    public String create ()
    {
        try
        {
            RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();

            candidato.setFkIdPessoa(pessoaBean.getPessoa());

            RhCandidatoValidarBean candidatoValidar = RhCandidatoValidarBean.getInstanciaBean();

            //Realiza as validações
            if (!candidatoValidar.validarNovo(candidato))
            {
                return null;
            }

            pessoaBean.gravarPessoa();

            userTransaction.begin();

            candidato.setFkIdPessoa(pessoaBean.getPessoa());
            candidato.setCodigoCandidato("C" + candidato.getFkIdPessoa().getPkIdPessoa());

            if (candidato.getFkIdEspecialidade1().getPkIdEspecialidade() == null)
            {
                candidato.setFkIdEspecialidade1(null);
            }
            if (candidato.getFkIdEspecialidade2().getPkIdEspecialidade() == null)
            {
                candidato.setFkIdEspecialidade2(null);
            }

            candidato.setDataCadastro(Calendar.getInstance().getTime());

            ficheiroAnexadoFacade.create(candidato.getFkIdBi());
            ficheiroAnexadoFacade.create(candidato.getFkIdCurriculum());
            ficheiroAnexadoFacade.create(candidato.getFkIdCertificadoDeHabilitacoes());
            ficheiroAnexadoFacade.create(candidato.getFkIdEquivalenciaDoCertificado());
            ficheiroAnexadoFacade.create(candidato.getFkIdComprovativoOrdemMedicosEnfermeiros());
            ficheiroAnexadoFacade.create(candidato.getFkIdCedulaCarteiraProfissional());
            ficheiroAnexadoFacade.create(candidato.getFkIdAtestadoMedico());
            ficheiroAnexadoFacade.create(candidato.getFkIdRegistoCriminal());
            ficheiroAnexadoFacade.create(candidato.getFkIdDocumentoMilitarHomens());

            candidatoFacade.create(candidato);

            userTransaction.commit();

            Mensagem.sucessoMsg("Candidato guardado com sucesso!");
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

            String novoNome = upload.gravar(anexoCarregado, Defs.DESTINO_ANEXO_CANDIDATO, tipoAnexo);

            if ("BI".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdBi(), true);
                candidato.getFkIdBi().setFicheiro(novoNome);
            }
            else if ("CURRICULUM".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdCurriculum(), true);
                candidato.getFkIdCurriculum().setFicheiro(novoNome);
            }
            else if ("CERTIFICADO_DE_HABILITACOES".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdCertificadoDeHabilitacoes(), true);
                candidato.getFkIdCertificadoDeHabilitacoes().setFicheiro(novoNome);
            }
            else if ("EQUIVALENCIA_DO_CERTIFICADO".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdCertificadoDeHabilitacoes(), true);
                candidato.getFkIdCertificadoDeHabilitacoes().setFicheiro(novoNome);
            }
            else if ("COMPROVATIVO_ORDEM_MEDICOS_ENFERMEIROS".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdComprovativoOrdemMedicosEnfermeiros(), true);
                candidato.getFkIdComprovativoOrdemMedicosEnfermeiros().setFicheiro(novoNome);
            }
            else if ("CEDULA_CARTEIRA_PROFISSIONAL".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdCedulaCarteiraProfissional(), true);
                candidato.getFkIdCedulaCarteiraProfissional().setFicheiro(novoNome);
            }
            else if ("ATESTADO_MEDICO".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdAtestadoMedico(), true);
                candidato.getFkIdAtestadoMedico().setFicheiro(novoNome);
            }
            else if ("REGISTO_CRIMINAL".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdRegistoCriminal(), true);
                candidato.getFkIdRegistoCriminal().setFicheiro(novoNome);
            }
            else if ("DOCUMENTO_MILITAR_HOMENS".equals(tipoAnexo))
            {
                removerAnexo(candidato.getFkIdDocumentoMilitarHomens(), true);
                candidato.getFkIdDocumentoMilitarHomens().setFicheiro(novoNome);
            }

            anexoCarregado = null;
            tipoAnexo = null;

            System.out.println("Anexo carregado com sucesso !");
            return "candidatoNovoRh.xhtml?faces-redirect=true";
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
