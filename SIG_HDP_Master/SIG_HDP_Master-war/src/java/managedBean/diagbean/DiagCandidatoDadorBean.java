/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.GrlInstituicaoBean;
import managedBean.segbean.SegLoginBean;
import org.primefaces.context.RequestContext;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import sessao.*;
import util.DataUtils;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagCandidatoDadorBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagResultadoExameCandidatoFacade diagResultadoExameCandidatoFacade;
    @EJB
    private DiagEstadoClinicoFacade diagEstadoClinicoFacade;
    @EJB
    private GrlInstituicaoFacade grlInstituicaoFacade;
    @EJB
    private DiagTriagemFacade diagTriagemFacade;
    @EJB
    private DiagTipoDoacaoFacade diagTipoDoacaoFacade;
    @EJB
    private DiagGrupoSanguineoFacade diagGrupoSanguineoFacade;
    @EJB
    private DiagNumeroDoacaoFacade diagNumeroDoacaoFacade;
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    @EJB
    private DiagBolsaSangueFacade diagBolsaSangueFacade;
    @EJB
    private DiagExameCandidatoFacade diagExameCandidatoFacade;
    @EJB
    private DiagTipagemDadorFacade diagTipagemDadorFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;

    private GrlInstituicao grlInstituicao;
    private DiagGrupoSanguineo diagGrupoSanguineo, diagGrupoSanguineoPesquisa;
    private DiagNumeroDoacao diagNumeroDoacao;
    private DiagTipoDoacao diagTipoDoacao;

    private DiagBolsaSangue diagBolsaSangue;
    private DiagExameCandidato diagExameCandidato, diagExameCandidatoVisualizar;
    private DiagTipagemDador diagTipagemDador, diagTipagemDadorVisualizar;

    private DiagTriagem diagTriagem, diagTriagemVisualizar;

    @EJB
    private GrlContactoFacade contactoFacade;
    @EJB
    private GrlEnderecoFacade enderecoFacade;
    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;
    @EJB
    private GrlDocumentoIdentificacaoFacade documentoIdentificacaoFacade;
    @EJB
    private GrlPessoaFacade pessoaFacade;

    @EJB
    private DiagResultadoTriagemFacade diagResultadoTriagemFacade;

    @EJB
    private GrlPessoaFacade grlPessoaFacade;
    @EJB
    private DiagCandidatoDadorFacade diagCandidatoDadorFacade;

    private GrlPessoa grlPessoa, grlPessoaDocumento;
    private DiagCandidatoDador diagCandidatoDador, diagCandidatoDadorPesquisa, diagCandidatoDadorVisualizar, diagCandidatoDadorEditar;
    private DiagResultadoTriagem diagResultadoTriagem, diagConclusaoExame;
    private DiagEstadoClinico diagEstadoClinico;
    private DiagResultadoExameCandidato mal, vdrl, hiv, aghbs, hcv;

    private GrlReligiao religiaoPessoaEditar;

    private int totalTriagens = 0;
    private int totalExames = 0;
    private int totalBolsasDeSangue = 0;

    private boolean pesquisar, flagMostrarDialogAdicionarBolsaDeSangue = false;

    private int tipoDocumento;

    private String numeroDocumento;

    private SegLoginBean segLoginBean = new SegLoginBean();

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private TimelineModel modeloTimelineExames, modeloTimelineTriagens;

    private String paginaAnterior;

    private boolean renderCamposOpcionaisRegistoCandidato = false;

    private Date dataInicioCadastro, dataFimCadastro;

    List<DiagCandidatoDador> itens;

    private int numeroRegistos = 10;

    public static DiagCandidatoDadorBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagCandidatoDadorBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagCandidatoDadorBean");
    }

    public static DiagCandidatoDador getInstancia()
    {
        DiagCandidatoDador diagCandidatoDador = new DiagCandidatoDador();
        diagCandidatoDador.setFkIdPessoa(DiagPessoaBean.getInstancia());
        diagCandidatoDador.setFkIdInstituicaoUltimaDadiva(GrlInstituicaoBean.getInstanciaInstituicao());
        diagCandidatoDador.setFkIdTipoDoacao(DiagTipoDoacaoBean.getInstancia());
        diagCandidatoDador.setFkIdNumeroDoacao(DiagNumeroDoacaoBean.getInstancia());

        return diagCandidatoDador;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }

    public Date getDataNascimentoValida()
    {
        Date data = new Date();
        Date dataValida = DataUtils.addAnos(data, -18);

        return dataValida;
    }

    public List<DiagCandidatoDador> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagCandidatoDador> itens)
    {
        this.itens = itens;
    }

    public Date getDataInicioCadastro()
    {
        return dataInicioCadastro;
    }

    public void setDataInicioCadastro(Date dataInicioCadastro)
    {
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public Date getDataFimCadastro()
    {
        return dataFimCadastro;
    }

    public void setDataFimCadastro(Date dataFimCadastro)
    {
        this.dataFimCadastro = dataFimCadastro;
    }

    public boolean isRenderCamposOpcionaisRegistoCandidato()
    {
        return renderCamposOpcionaisRegistoCandidato;
    }

    public void setRenderCamposOpcionaisRegistoCandidato(boolean renderCamposOpcionaisRegistoCandidato)
    {
        this.renderCamposOpcionaisRegistoCandidato = renderCamposOpcionaisRegistoCandidato;
    }

    public GrlPessoa getGrlPessoa()
    {
        if (grlPessoa == null)
        {
            grlPessoa = DiagPessoaBean.getInstancia();
//            grlPessoa.setFkIdFoto(new GrlFicheiroAnexado());
            grlPessoa.setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
        }
        return grlPessoa;
    }

    public void setGrlPessoa(GrlPessoa grlPessoa)
    {
        this.grlPessoa = grlPessoa;
    }

    public GrlReligiao getReligiaoPessoaEditar()
    {
        if (religiaoPessoaEditar == null)
        {
            religiaoPessoaEditar = new GrlReligiao();
        }
        return religiaoPessoaEditar;
    }

    public void setReligiaoPessoaEditar(GrlReligiao religiaoPessoaEditar)
    {
        this.religiaoPessoaEditar = religiaoPessoaEditar;
    }

    public DiagCandidatoDador getDiagCandidatoDadorEditar()
    {
        if (diagCandidatoDadorEditar == null)
        {
            diagCandidatoDadorEditar = getInstancia();
            diagCandidatoDadorEditar.getFkIdPessoa().setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
        }

        return diagCandidatoDadorEditar;
    }

    public void setDiagCandidatoDadorEditar(DiagCandidatoDador diagCandidatoDadorEditar)
    {
        this.diagCandidatoDadorEditar = diagCandidatoDadorEditar;
    }

    public DiagCandidatoDador getDiagCandidatoDador()
    {
        if (diagCandidatoDador == null)
        {
            diagCandidatoDador = getInstancia();
        }
        return diagCandidatoDador;
    }

    public void setDiagCandidatoDador(DiagCandidatoDador diagCandidatoDador)
    {
        this.diagCandidatoDador = diagCandidatoDador;
    }

    public DiagTriagem getDiagTriagem()
    {
        if (diagTriagem == null)
        {
            diagTriagem = new DiagTriagem();
        }
        return diagTriagem;
    }

    public void setDiagTriagem(DiagTriagem diagTriagem)
    {
        this.diagTriagem = diagTriagem;
    }

    public DiagResultadoTriagem getDiagResultadoTriagem()
    {
        if (diagResultadoTriagem == null)
        {
            diagResultadoTriagem = new DiagResultadoTriagem();
        }

        return diagResultadoTriagem;
    }

    public void setDiagResultadoTriagem(DiagResultadoTriagem diagResultadoTriagem)
    {
        this.diagResultadoTriagem = diagResultadoTriagem;
    }

    public DiagResultadoTriagem getDiagConclusaoExame()
    {
        if (diagConclusaoExame == null)
        {
            diagConclusaoExame = new DiagResultadoTriagem();
        }
        return diagConclusaoExame;
    }

    public void setDiagConclusaoExame(DiagResultadoTriagem diagConclusaoExame)
    {
        this.diagConclusaoExame = diagConclusaoExame;
    }

    public DiagEstadoClinico getDiagEstadoClinico()
    {
        if (diagEstadoClinico == null)
        {
            diagEstadoClinico = new DiagEstadoClinico();
        }
        return diagEstadoClinico;
    }

    public void setDiagEstadoClinico(DiagEstadoClinico diagEstadoClinico)
    {
        this.diagEstadoClinico = diagEstadoClinico;
    }

    public GrlInstituicao getGrlInstituicao()
    {
        if (grlInstituicao == null)
        {
            grlInstituicao = new GrlInstituicao();
        }
        return grlInstituicao;
    }

    public void setGrlInstituicao(GrlInstituicao grlInstituicao)
    {
        this.grlInstituicao = grlInstituicao;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineo()
    {
        if (diagGrupoSanguineo == null)
        {
            diagGrupoSanguineo = new DiagGrupoSanguineo();
        }
        return diagGrupoSanguineo;
    }

    public void setDiagGrupoSanguineo(DiagGrupoSanguineo diagGrupoSanguineo)
    {
        this.diagGrupoSanguineo = diagGrupoSanguineo;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineoPesquisa()
    {
        if (diagGrupoSanguineoPesquisa == null)
        {
            diagGrupoSanguineoPesquisa = new DiagGrupoSanguineo();
        }
        return diagGrupoSanguineoPesquisa;
    }

    public void setDiagGrupoSanguineoPesquisa(DiagGrupoSanguineo diagGrupoSanguineoPesquisa)
    {
        this.diagGrupoSanguineoPesquisa = diagGrupoSanguineoPesquisa;
    }

    public DiagNumeroDoacao getDiagNumeroDoacao()
    {
        if (diagNumeroDoacao == null)
        {
            diagNumeroDoacao = new DiagNumeroDoacao();
        }
        return diagNumeroDoacao;
    }

    public void setDiagNumeroDoacao(DiagNumeroDoacao diagNumeroDoacao)
    {
        this.diagNumeroDoacao = diagNumeroDoacao;
    }

    public DiagTipoDoacao getDiagTipoDoacao()
    {
        if (diagTipoDoacao == null)
        {
            diagTipoDoacao = new DiagTipoDoacao();
        }
        return diagTipoDoacao;
    }

    public void setDiagTipoDoacao(DiagTipoDoacao diagTipoDoacao)
    {
        this.diagTipoDoacao = diagTipoDoacao;
    }

    public DiagBolsaSangue getDiagBolsaSangue()
    {
        if (diagBolsaSangue == null)
        {
            diagBolsaSangue = new DiagBolsaSangue();
        }
        return diagBolsaSangue;
    }

    public void setDiagBolsaSangue(DiagBolsaSangue diagBolsaSangue)
    {
        this.diagBolsaSangue = diagBolsaSangue;
    }

    public DiagExameCandidato getDiagExameCandidato()
    {
        if (diagExameCandidato == null)
        {
            diagExameCandidato = new DiagExameCandidato();
        }
        return diagExameCandidato;
    }

    public void setDiagExameCandidato(DiagExameCandidato diagExameCandidato)
    {
        this.diagExameCandidato = diagExameCandidato;
    }

    public DiagTipagemDador getDiagTipagemDador()
    {
        if (diagTipagemDador == null)
        {
            diagTipagemDador = new DiagTipagemDador();
        }
        return diagTipagemDador;
    }

    public void setDiagTipagemDador(DiagTipagemDador diagTipagemDador)
    {
        this.diagTipagemDador = diagTipagemDador;
    }

    public DiagTipagemDador getDiagTipagemDadorVisualizar()
    {
        if (diagTipagemDadorVisualizar == null)
        {
            diagTipagemDadorVisualizar = new DiagTipagemDador();
        }
        return diagTipagemDadorVisualizar;
    }

    public void setDiagTipagemDadorVisualizar(DiagTipagemDador diagTipagemDadorVisualizar)
    {
        this.diagTipagemDadorVisualizar = diagTipagemDadorVisualizar;
    }

    public int getTipoDocumento()
    {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento)
    {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento()
    {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento;
    }

    public DiagResultadoExameCandidato getMal()
    {
        if (mal == null)
        {
            mal = new DiagResultadoExameCandidato();
            mal.setPkIdResultadoExameCandidato(Defs.RESULTADO_EXAME_CANDIDATO_DEFAULT_ID);

        }
        return mal;
    }

    public void setMal(DiagResultadoExameCandidato mal)
    {
        this.mal = mal;
    }

    public DiagResultadoExameCandidato getVdrl()
    {
        if (vdrl == null)
        {
            vdrl = new DiagResultadoExameCandidato();
            vdrl.setPkIdResultadoExameCandidato(Defs.RESULTADO_EXAME_CANDIDATO_DEFAULT_ID);

        }
        return vdrl;
    }

    public void setVdrl(DiagResultadoExameCandidato vdrl)
    {
        this.vdrl = vdrl;
    }

    public DiagResultadoExameCandidato getHiv()
    {
        if (hiv == null)
        {
            hiv = new DiagResultadoExameCandidato();
            hiv.setPkIdResultadoExameCandidato(Defs.RESULTADO_EXAME_CANDIDATO_DEFAULT_ID);

        }
        return hiv;
    }

    public void setHiv(DiagResultadoExameCandidato hiv)
    {
        this.hiv = hiv;
    }

    public DiagResultadoExameCandidato getAghbs()
    {
        if (aghbs == null)
        {
            aghbs = new DiagResultadoExameCandidato();
            aghbs.setPkIdResultadoExameCandidato(Defs.RESULTADO_EXAME_CANDIDATO_DEFAULT_ID);

        }
        return aghbs;
    }

    public void setAghbs(DiagResultadoExameCandidato aghbs)
    {
        this.aghbs = aghbs;
    }

    public DiagResultadoExameCandidato getHcv()
    {
        if (hcv == null)
        {
            hcv = new DiagResultadoExameCandidato();
            hcv.setPkIdResultadoExameCandidato(Defs.RESULTADO_EXAME_CANDIDATO_DEFAULT_ID);
        }
        return hcv;
    }

    public void setHcv(DiagResultadoExameCandidato hcv)
    {
        this.hcv = hcv;
    }

    public int getTotalTriagens()
    {
        return totalTriagens;
    }

    public void setTotalTriagens(int totalTriagens)
    {
        this.totalTriagens = totalTriagens;
    }

    public int getTotalExames()
    {
        return totalExames;
    }

    public void setTotalExames(int totalExames)
    {
        this.totalExames = totalExames;
    }

    public int getTotalBolsasDeSangue()
    {
        return totalBolsasDeSangue;
    }

    public void setTotalBolsasDeSangue(int totalBolsasDeSangue)
    {
        this.totalBolsasDeSangue = totalBolsasDeSangue;
    }

    public DiagCandidatoDador getDiagCandidatoDadorPesquisa()
    {
        if (diagCandidatoDadorPesquisa == null)
        {
            diagCandidatoDadorPesquisa = getInstancia();
        }
        return diagCandidatoDadorPesquisa;
    }

    public void setDiagCandidatoDadorPesquisa(DiagCandidatoDador diagCandidatoDadorPesquisa)
    {
        this.diagCandidatoDadorPesquisa = diagCandidatoDadorPesquisa;
    }

    public DiagCandidatoDador getDiagCandidatoDadorVisualizar()
    {
        if (diagCandidatoDadorVisualizar == null)
        {
            diagCandidatoDadorVisualizar = getInstancia();
        }
        return diagCandidatoDadorVisualizar;
    }

    public void setDiagCandidatoDadorVisualizar(DiagCandidatoDador diagCandidatoDadorVisualizar)
    {
        this.diagCandidatoDadorVisualizar = diagCandidatoDadorVisualizar;
    }

    public boolean getPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public DiagExameCandidato getDiagExameCandidatoVisualizar()
    {
        if (diagExameCandidatoVisualizar == null)
        {
            diagExameCandidatoVisualizar = new DiagExameCandidato();
        }
        return diagExameCandidatoVisualizar;
    }

    public void setDiagExameCandidatoVisualizar(DiagExameCandidato diagExameCandidatoVisualizar)
    {
        this.diagExameCandidatoVisualizar = diagExameCandidatoVisualizar;
    }

    public DiagTriagem getDiagTriagemVisualizar()
    {
        if (diagTriagemVisualizar == null)
        {
            diagTriagemVisualizar = new DiagTriagem();
        }
        return diagTriagemVisualizar;
    }

    public void setDiagTriagemVisualizar(DiagTriagem diagTriagemVisualizar)
    {
        this.diagTriagemVisualizar = diagTriagemVisualizar;
    }

    public boolean isTemMensagemPendente()
    {
        return temMensagemPendente;
    }

    public void setTemMensagemPendente(boolean temMensagemPendente)
    {
        this.temMensagemPendente = temMensagemPendente;
    }

    public String getMensagemPendente()
    {
        return mensagemPendente;
    }

    public void setMensagemPendente(String mensagemPendente)
    {
        this.mensagemPendente = mensagemPendente;
    }

    public String getTipoMensagemPendente()
    {
        return tipoMensagemPendente;
    }

    public void setTipoMensagemPendente(String tipoMensagemPendente)
    {
        this.tipoMensagemPendente = tipoMensagemPendente;
    }

    public TimelineModel getModeloTimelineExames()
    {
        return modeloTimelineExames;
    }

    public void setModeloTimelineExames(TimelineModel modeloTimelineExames)
    {
        this.modeloTimelineExames = modeloTimelineExames;
    }

    public TimelineModel getModeloTimelineTriagens()
    {
        return modeloTimelineTriagens;
    }

    public void setModeloTimelineTriagens(TimelineModel modeloTimelineTriagens)
    {
        this.modeloTimelineTriagens = modeloTimelineTriagens;
    }

    public DiagTriagem getInstanciaTriagem()
    {
        DiagTriagem instanciaTriagem = new DiagTriagem();

        instanciaTriagem.setFkIdCandidatoDador(new DiagCandidatoDador());

        instanciaTriagem.getFkIdCandidatoDador().setFkIdPessoa(new GrlPessoa());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().setFkIdEstadoCivil(new GrlEstadoCivil());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().setFkIdEndereco(new GrlEndereco());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna());
//        instanciaTriagem.getFkIdCandidaturaDador().getFkIdCandidatoDador().getFkIdPessoa().setFkIdDocumentoIdentificacao(new GrlDocumentoIdentificacao());
//        instanciaTriagem.getFkIdCandidaturaDador().getFkIdCandidatoDador().getFkIdPessoa().getFkIdDocumentoIdentificacao().setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().setFkIdContacto(new GrlContacto());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().setFkIdSexo(new GrlSexo());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().setFkIdReligiao(new GrlReligiao());
        instanciaTriagem.getFkIdCandidatoDador().getFkIdPessoa().setFkIdNacionalidade(new GrlPais());

        instanciaTriagem.setFkIdResultadoTriagem(new DiagResultadoTriagem());

        return instanciaTriagem;
    }

    public List<GrlInstituicao> getListaGrlInstituicao()
    {
        return diagCandidatoDadorFacade.findAllInstituicoes();
    }

    public List<DiagTipoDoacao> getListaDiagTipoDoacao()
    {
        return diagTipoDoacaoFacade.findAll();
    }

    public List<DiagGrupoSanguineo> getListaDiagGrupoSanguineo()
    {
        return diagGrupoSanguineoFacade.findAll();
    }

    public List<DiagNumeroDoacao> getListaDiagNumeroDoacoes()
    {
        return diagNumeroDoacaoFacade.findAll();
    }

    public List<GrlCentroHospitalar> getListaGrlCentrosHospitalares()
    {
        return grlCentroHospitalarFacade.findAll();
    }

    public List<RhFuncionario> getListaFuncionarios()
    {
        return rhFuncionarioFacade.findAll();
    }

    public List<GrlPais> getListaPais()
    {
        return diagCandidatoDadorFacade.findAllPais();
    }

    public boolean validarIdade(GrlPessoa pessoaAux)
    {
        int idade = new Date().getYear() - pessoaAux.getDataNascimento().getYear();

        return idade >= 18;
    }

    private void carregarTimelineTriagens(List<DiagTriagem> listaTriagensAux)
    {
        modeloTimelineTriagens = new TimelineModel();

        Calendar cal = Calendar.getInstance();

        for (DiagTriagem triagem : listaTriagensAux)
        {
            cal.setTime(triagem.getDataTriagem());

            modeloTimelineTriagens.add(new TimelineEvent(triagem.getFkIdResultadoTriagem().getDescricaoResultadoTriagem(), cal.getTime()));
        }
    }

    public List<DiagTriagem> findTriagensByIdCandidato(GrlPessoa pessoaAux)
    {
        List<DiagTriagem> listaTriagensAux = diagTriagemFacade.findTriagensPorCodigoPessoa(pessoaAux);

        totalTriagens = listaTriagensAux.size();

        carregarTimelineTriagens(listaTriagensAux);

        return listaTriagensAux;
    }

    private void carregarTimelineExames(List<DiagExameCandidato> listaExamesAux)
    {
        modeloTimelineExames = new TimelineModel();

        Calendar cal = Calendar.getInstance();

        for (DiagExameCandidato exame : listaExamesAux)
        {
            cal.setTime(exame.getDataExame());

            modeloTimelineExames.add(new TimelineEvent(exame.getConclusao().getDescricaoResultadoTriagem(), cal.getTime()));
        }
    }

    public List<DiagExameCandidato> findExamesByIdCandidato(GrlPessoa pessoaAux)
    {
        List<DiagExameCandidato> listaExamesAux = diagExameCandidatoFacade.findExamesCandidatoPorCodigoPessoa(pessoaAux);

        totalExames = listaExamesAux.size();

        carregarTimelineExames(listaExamesAux);

        return listaExamesAux;
    }

    public List<DiagBolsaSangue> findBolsasDeSangueByIdCandidato(GrlPessoa pessoaAux)
    {
        List<DiagBolsaSangue> listaBolsasDeSangueAux = diagBolsaSangueFacade.findBolsasDeSangueDador(pessoaAux);

        totalBolsasDeSangue = listaBolsasDeSangueAux.size();
        return listaBolsasDeSangueAux;
    }

    public boolean candidatoDadorJaTipado(DiagCandidatoDador candidatoDador)
    {
        return diagTipagemDadorFacade.findTipagemDador(candidatoDador) != null;
    }

    public boolean candidatoJaCadastrado()
    {
        for (DiagCandidatoDador candidato : diagCandidatoDadorFacade.findAll())
        {
            if (candidato.getPkIdCandidatoDador() == diagCandidatoDador.getPkIdCandidatoDador())
            {
                return true;
            }
        }

        return false;
    }

    public int diferencaDiasEntreDuasDatas(Date dataUltimaTriagem)
    {
        try
        {
            GregorianCalendar dataCalendar = new GregorianCalendar();

            Date dataActual = dataCalendar.getTime();

            SimpleDateFormat formatoData = new SimpleDateFormat("MM/dd/yyyy");

            String stringD1 = formatoData.format(dataActual);
            String stringD2 = formatoData.format(dataUltimaTriagem);

            Date d1 = formatoData.parse(stringD1);
            Date d2 = formatoData.parse(stringD2);

            long diff = d2.getTime() - d1.getTime();

            long diffDays = diff / (24 * 60 * 60 * 1000);

            return Math.abs((int) diffDays);
        }
        catch (ParseException ex)
        {
            Logger.getLogger(DiagCandidatoDadorBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public boolean dataUltimaTriagemValida()
    {
        boolean dataValida = false;

        if (diagTriagemFacade.findDataUltimaTriagem(diagCandidatoDador) == null)
        {
            return true;
        }

        Date dataUltimaTriagem = diagTriagemFacade.findDataUltimaTriagem(diagCandidatoDador);

        if (diagCandidatoDador.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("M") && diferencaDiasEntreDuasDatas(dataUltimaTriagem) >= 90)
        {
            dataValida = true;
        }
        else if (diagCandidatoDador.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("F") && diferencaDiasEntreDuasDatas(dataUltimaTriagem) >= 120)
        {
            dataValida = true;
        }

        return dataValida;
    }

    public boolean dataUltimoExameValida()
    {
        boolean dataValida = false;

        if (diagExameCandidatoFacade.findDataUltimoExame(diagCandidatoDadorVisualizar) == null)
        {
            return true;
        }

        Date dataUltimoExame = diagExameCandidatoFacade.findDataUltimoExame(diagCandidatoDadorVisualizar);

        if (diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("M") && diferencaDiasEntreDuasDatas(dataUltimoExame) >= 90)
        {
            dataValida = true;
        }
        else if (diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("F") && diferencaDiasEntreDuasDatas(dataUltimoExame) >= 120)
        {
            dataValida = true;
        }

        return dataValida;
    }

    public boolean dataUltimaBolsaValida()
    {
        boolean dataValida = false;

        if (diagBolsaSangueFacade.findDataUltimaBolsa(diagCandidatoDadorVisualizar) == null)
        {
            return true;
        }

        Date dataUltimaBolsa = diagBolsaSangueFacade.findDataUltimaBolsa(diagCandidatoDadorVisualizar);

        if (diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("M") && diferencaDiasEntreDuasDatas(dataUltimaBolsa) >= 90)
        {
            dataValida = true;
        }
        else if (diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("F") && diferencaDiasEntreDuasDatas(dataUltimaBolsa) >= 120)
        {
            dataValida = true;
        }

        return dataValida;
    }

    public String registarCandidatoDador()
    {

        GregorianCalendar data = new GregorianCalendar();

        diagCandidatoDador.setDataCadastro(new Date(data.getTimeInMillis()));

        try
        {
            userTransaction.begin();

            diagCandidatoDador.setFkIdPessoa(grlPessoa);
            diagCandidatoDador.setFkIdNumeroDoacao(diagNumeroDoacao);

            diagCandidatoDador.setFkIdInstituicaoUltimaDadiva(grlInstituicao);
            diagCandidatoDador.setFkIdTipoDoacao(diagTipoDoacao);

            diagCandidatoDadorFacade.create(diagCandidatoDador);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Candidato salvo com sucesso!";

        }
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = ex.toString();
            }
        }

        return "registarTriagemCandidato.xhtml?faces-redirect=true";

    }

    public List<GrlPessoa> getListaPessoas()
    {
        return grlPessoaFacade.findAll();
    }

    public List<DiagResultadoTriagem> getListaResultadoTriagems()
    {
        return diagResultadoTriagemFacade.findAll();
    }

    public List<DiagEstadoClinico> getListaEstadoClinico()
    {
        return diagEstadoClinicoFacade.findAll();
    }

    public List<DiagResultadoExameCandidato> getListaResultadosExamesCandidatos()
    {
        return diagResultadoExameCandidatoFacade.findAll();
    }

    public String selecionarPessoaCandidatoNovo(GrlPessoa pessoaAux)
    {
        this.grlPessoa = pessoaAux;

        return redirecionarRegistarCandidatoDador();
    }

    public List<DiagCandidatoDador> findCandidatoDadorByPessoa(GrlPessoa pessoa)
    {
        return diagCandidatoDadorFacade.findCandidatoDadorByPessoa(pessoa);
    }

    public List<DiagTriagem> findAllTriagem()
    {
        return diagTriagemFacade.findAll();
    }

    public List<DiagExameCandidato> findAllExames()
    {
        return diagExameCandidatoFacade.findAll();
    }

    public List<DiagBolsaSangue> findAllBolsas()
    {
        return diagBolsaSangueFacade.findAll();
    }

    public void pesquisarCandidatos()
    {
        itens = diagCandidatoDadorFacade.findPessoaCandidato(diagCandidatoDadorPesquisa, dataInicioCadastro, dataFimCadastro, numeroRegistos);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

    public void selecionarTriagemVisualizar(DiagTriagem triagemAux)
    {
        diagTriagemVisualizar = triagemAux;

        temMensagemPendente = false;
    }

    public void selecionarExameCandidatoVisualizar(DiagExameCandidato exameCandidatoAux)
    {
        diagExameCandidatoVisualizar = exameCandidatoAux;

        temMensagemPendente = false;
    }

    public String respostaTriagem(boolean resposta)
    {
        if (resposta == true)
        {
            return "Sim";
        }
        return "Não";
    }

    public String visualizarCandidatoAposTipagem(GrlPessoa pes)
    {
        diagCandidatoDadorVisualizar = findCandidatoDadorByPessoa(pes).get(0);

        diagTipagemDadorVisualizar = diagTipagemDadorFacade.findTipagemDador(diagCandidatoDadorVisualizar);

        return "visualizarCandidatoDador.xhtml?faces-redirect=true";
    }

    public String visualizarCandidato(GrlPessoa pes)
    {
        System.out.println("pes: " + pes.toString());

        diagCandidatoDadorVisualizar = findCandidatoDadorByPessoa(pes).get(0);

        diagTipagemDadorVisualizar = diagTipagemDadorFacade.findTipagemDador(diagCandidatoDadorVisualizar);

        List<DiagTriagem> listaTriagensAux = diagTriagemFacade.findTriagensPorCodigoPessoa(diagCandidatoDadorVisualizar.getFkIdPessoa());

        totalTriagens = listaTriagensAux.size();

        List<DiagExameCandidato> listaExamesAux = diagExameCandidatoFacade.findExamesCandidatoPorCodigoPessoa(diagCandidatoDadorVisualizar.getFkIdPessoa());

        totalExames = listaExamesAux.size();

        return "visualizarCandidatoDador.xhtml?faces-redirect=true";
    }

    public void selecionarCandidatoEditar(DiagCandidatoDador pes)
    {        
        diagCandidatoDadorEditar = diagCandidatoDadorFacade.find(pes.getPkIdCandidatoDador());
//        diagCandidatoDadorEditar = pes;

        System.out.println("diagCandidatoDadorEditar " + diagCandidatoDadorEditar.getFkIdPessoa().toString());
        System.out.println("pes " + pes.getFkIdPessoa().toString());
        
        System.out.println("candidato selecionado: " + diagCandidatoDadorEditar.getFkIdPessoa().getNome());
    }

    public String redirecionarVisualizarCandidato()
    {
        diagExameCandidato = null;
        diagConclusaoExame = null;
        diagEstadoClinico = null;
        diagBolsaSangue = null;

        diagCandidatoDadorEditar = getInstancia();

        return "visualizarCandidatoDador.xhtml?faces-redirect=true";
    }

    public String limparPesquisaRedirecionarPesquisaCandidato()
    {
        temMensagemPendente = false;

        pesquisar = false;
        itens = new ArrayList<>();
        diagCandidatoDador = diagCandidatoDadorPesquisa = diagCandidatoDadorVisualizar = null;

        diagCandidatoDadorEditar = null;

        diagGrupoSanguineoPesquisa = null;

        return "candidatoDador.xhtml?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        temMensagemPendente = false;

        if (diagCandidatoDadorVisualizar == null)
        {
            paginaAnterior = "";
        }

        if ("visualizar".equals(paginaAnterior))
        {
            paginaAnterior = "";

            return "visualizarCandidatoDador.xhtml?faces-redirect=true";
        }

        pesquisar = false;
        diagCandidatoDador = diagCandidatoDadorPesquisa = diagCandidatoDadorVisualizar = null;

        diagGrupoSanguineoPesquisa = null;

        return "candidatoDador.xhtml?faces-redirect=true";
    }

    public String limparPesquisaPessoa()
    {
        grlPessoa = null;

        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    public String redirecionarRegistarCandidatosDadosPessoais()
    {
        grlPessoa = null;

        return "registarCandidatoDadosPessoais.xhtml?faces-redirect=true";
    }

    public String redirecionarRegistarCandidatoDador()
    {
        diagCandidatoDador = diagCandidatoDadorPesquisa = diagCandidatoDadorVisualizar = null;

        return "registarCandidatoDador.xhtml?faces-redirect=true";
    }

    public String redirecionarRegistarTriagemCandidatoExistente()
    {
        temMensagemPendente = false;

        diagCandidatoDador = diagCandidatoDadorVisualizar;

        paginaAnterior = "visualizar";

        if (!dataUltimaTriagemValida() && diagCandidatoDador.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("M"))
        {
            Mensagem.erroMsg("Erro: A ultima triagem foi há menos de 3 meses!");

            return null;
        }
        if (!dataUltimaTriagemValida() && diagCandidatoDador.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("F"))
        {
            Mensagem.erroMsg("Erro: A ultima triagem foi há menos de 4 meses!");

            return null;
        }
        else
        {
            return "registarTriagemCandidato.xhtml?faces-redirect=true";
        }
    }

    public String redirecionarRegistarExameCandidatoDador()
    {
        temMensagemPendente = false;

        if (!dataUltimoExameValida() && diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("M"))
        {
            Mensagem.erroMsg("Erro: Os ultimos exames foram há menos de 3 meses!");

            return null;
        }
        if (!dataUltimoExameValida() && diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("F"))
        {
            Mensagem.erroMsg("Erro: Os ultimos exames foram há menos de 4 meses!");

            return null;
        }
        else
        {
            if (diagTriagemFacade.findUltimaTriagem(diagCandidatoDadorVisualizar) == null)
            {
                Mensagem.erroMsg("Erro: O candidato ainda não foi submetido a uma triagem!");

                return null;
            }

            if (diagTriagemFacade.findUltimaTriagem(diagCandidatoDadorVisualizar).getFkIdResultadoTriagem().getPkIdResultadoTriagem() == 1)
            {
                diagCandidatoDador = diagCandidatoDadorVisualizar;

                return "registarExameCandidatoDador.xhtml?faces-redirect=true";
            }
            else
            {
                Mensagem.erroMsg("Erro: O candidato foi reprovado na ultima triagem!");

                return null;
            }
        }
    }

    public String redirecionarRegistarTipagemCandidatoDador()
    {
        temMensagemPendente = false;

        diagCandidatoDador = diagCandidatoDadorVisualizar;

        return "registarTipagemCandidato.xhtml?faces-redirect=true";
    }

    public String redirecionarRegistarBolsaDeSangueCandidatoDador()
    {
        temMensagemPendente = false;

        if (!dataUltimaBolsaValida() && diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("M"))
        {
            Mensagem.erroMsg("Erro: A ultima bolsa de sangue foi adicionada há menos de 3 meses!");

            flagMostrarDialogAdicionarBolsaDeSangue = false;

            return null;
        }
        if (!dataUltimaBolsaValida() && diagCandidatoDadorVisualizar.getFkIdPessoa().getFkIdSexo().getCodigoSexo().equals("F"))
        {
            Mensagem.erroMsg("Erro: A ultima bolsa de sangue foi adicionada há menos de 4 meses!");

            return null;
        }
        else
        {
            if (diagExameCandidatoFacade.findUltimoExame(diagCandidatoDadorVisualizar) == null)
            {
                Mensagem.erroMsg("Erro: Ainda não foram realizados os exames para este candidato!");

                flagMostrarDialogAdicionarBolsaDeSangue = false;

                return null;
            }
            if (diagExameCandidatoFacade.findUltimoExame(diagCandidatoDadorVisualizar).getConclusao().getPkIdResultadoTriagem() == 1)
            {
                if (diagExameCandidatoFacade.findUltimoExame(diagCandidatoDadorVisualizar).getFkIdEstadoClinico().getPkIdEstadoClinico() == 1)
                {
                    //Data actual + 30 dias
                    //diagBolsaSangue.setDataExpiracao(null);

                    diagCandidatoDador = diagCandidatoDadorVisualizar;

                    flagMostrarDialogAdicionarBolsaDeSangue = true;

                    return null;
                }
                else
                {
                    Mensagem.erroMsg("Erro: O candidato não foi considerado estável nos últimos exames!");

                    flagMostrarDialogAdicionarBolsaDeSangue = false;

                    return null;
                }
            }
            else
            {
                Mensagem.erroMsg("Erro: O candidato não foi considerado apto nos últimos exames!");

                flagMostrarDialogAdicionarBolsaDeSangue = false;

                return null;
            }
        }
    }

    public void mostrarDialogAdicionarBolsaDeSangue()
    {
        RequestContext context = RequestContext.getCurrentInstance();

        if (flagMostrarDialogAdicionarBolsaDeSangue == true)
        {
            context.execute("PF('dialogRegistarBolsa').show();");
        }
        else
        {

            context.execute(";");
        }
    }

    public void getMensagem()
    {
        if (tipoMensagemPendente == "Sucesso")
        {
            Mensagem.sucessoMsg(mensagemPendente);

            temMensagemPendente = false;
        }
        else
        {
            Mensagem.erroMsg(mensagemPendente);

            temMensagemPendente = false;
        }
    }

    public void apresentarCamposOpcionaisRegistoCandidato()
    {
        DiagNumeroDoacao numeroDoacao = diagNumeroDoacaoFacade.find(diagCandidatoDador.getFkIdNumeroDoacao().getPkIdNumeroDoacao());
        renderCamposOpcionaisRegistoCandidato = numeroDoacao.getDescricao().equals("Sim");
    }

    public String registarCandidatosDadosPessoais()
    {
        if (validarIdade(grlPessoa))
        {
            try
            {
                userTransaction.begin();

                contactoFacade.create(grlPessoa.getFkIdContacto());
//                enderecoFacade.create(grlPessoa.getFkIdEndereco());
                gravarEndereco(grlPessoa);
                if (grlPessoa.getFkIdReligiao().getPkIdReligiao() == null)
                {
                    grlPessoa.setFkIdReligiao(null);
                }
                grlPessoa.setFkIdGrupoSanguineo(null);
                pessoaFacade.create(grlPessoa);
                adicionarDocumentosIdentificacao(grlPessoa);
                userTransaction.commit();

                temMensagemPendente = true;

                tipoMensagemPendente = "Sucesso";

                mensagemPendente = "Pessoa gravada com sucesso!";

                numeroDocumento = "";
            }
            catch (Exception e)
            {
                try
                {
                    System.out.println("userTransaction " + userTransaction.toString());

                    userTransaction.rollback();

                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = e.toString();
                }
                catch (IllegalStateException | SecurityException | SystemException ex)
                {
                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = "Rollback: " + ex.toString();
                }

            }

            return "registarCandidatoDador.xhtml?faces-redirect=true";
        }
        else
        {
            Mensagem.erroMsg("O candidato a dador deve possuir idade igual ou superior a 18!");

            return null;
        }

    }

    public String validarTriagem()
    {
        if (diagResultadoTriagem.getPkIdResultadoTriagem() == 1
                && (diagTriagem.getAlimentacao() == false
                || diagTriagem.getSono() == false
                || diagTriagem.getTatuagem() == true
                || diagTriagem.getSifilis() == true
                || diagTriagem.getEpilepsia() == true
                || diagTriagem.getGravida() == true
                || diagTriagem.getHepatite() == true
                || diagTriagem.getParceiros() == true
                || diagTriagem.getUltimaDoacao() == false
                || diagTriagem.getPeso() == false))
        {
            Mensagem.erroMsg("Pelas respostas introduzidas o candidato não satisfaz os requisitos para ser considerado apto. Por favor mude o resultado da triagem para 'Não Apto'.");

            diagTriagem = new DiagTriagem();
            diagResultadoTriagem = new DiagResultadoTriagem();

            return null;
        }
        else
        {
            return registarTriagemCandidato();
        }
    }

    public String registarTriagemCandidato()
    {
        GregorianCalendar data = new GregorianCalendar();

        if (candidatoJaCadastrado())
        {
            try
            {
                userTransaction.begin();

                diagTriagem.setDataTriagem(new Date(data.getTimeInMillis()));
//               documentoIdentificacaoFacade.create(grlPessoa.getFkIdDocumentoIdentificacao());
                diagTriagem.setFkIdCandidatoDador(diagCandidatoDador);
                diagTriagem.setFkIdResultadoTriagem(diagResultadoTriagem);

                diagTriagemFacade.create(diagTriagem);

                userTransaction.commit();

                temMensagemPendente = true;

                tipoMensagemPendente = "Sucesso";

                mensagemPendente = "Triagem salva com sucesso!";

//                grlPessoa = null;
                diagTriagem = null;
                diagResultadoTriagem = null;

                List<DiagTriagem> listaTriagensAux = diagTriagemFacade.findTriagensPorCodigoPessoa(diagCandidatoDador.getFkIdPessoa());

                totalTriagens = listaTriagensAux.size();

            }
            catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
            {
                try
                {
                    userTransaction.rollback();

                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = e.toString();
                }
                catch (IllegalStateException | SecurityException | SystemException ex)
                {
                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = "Rollback: " + ex.toString();
                }

            }

            return visualizarCandidato(diagCandidatoDador.getFkIdPessoa());

        }
        else
        {
            try
            {
                userTransaction.begin();

                diagTriagem.setDataTriagem(new Date(data.getTimeInMillis()));
//               documentoIdentificacaoFacade.create(grlPessoa.getFkIdDocumentoIdentificacao());
                diagTriagem.setFkIdCandidatoDador(diagCandidatoDador);
                diagTriagem.setFkIdResultadoTriagem(diagResultadoTriagem);

                diagTriagemFacade.create(diagTriagem);

                userTransaction.commit();

                temMensagemPendente = true;

                tipoMensagemPendente = "Sucesso";

                mensagemPendente = "Triagem salva com sucesso!";

                diagTriagem = null;
                diagResultadoTriagem = null;
            }
            catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
            {
                try
                {
                    userTransaction.rollback();

                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = e.toString();
                }
                catch (IllegalStateException | SecurityException | SystemException ex)
                {
                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = "Rollback: " + ex.toString();
                }
            }

            return "candidatoDador.xhtml?faces-redirect=true";
        }
    }

    public String validarExames()
    {
        if (diagConclusaoExame.getPkIdResultadoTriagem() == 1
                && (hcv.getPkIdResultadoExameCandidato() == 1
                || hiv.getPkIdResultadoExameCandidato() == 1
                || aghbs.getPkIdResultadoExameCandidato() == 1
                || vdrl.getPkIdResultadoExameCandidato() == 1
                || mal.getPkIdResultadoExameCandidato() == 1
                || diagExameCandidato.getPeso() < 50 || diagExameCandidato.getPeso() > 120))

        {
            Mensagem.erroMsg("Pelos resultados introduzidos o candidato não satisfaz os requisitos para ser considerado apto. Por favor mude a conclusão para 'Não Apto'.");

            diagExameCandidato = new DiagExameCandidato();
            diagConclusaoExame = new DiagResultadoTriagem();

            return null;
        }
        else
        {
            return registarExameCandidato();
        }
    }

    public String registarExameCandidato()
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagExameCandidato.setHcv(hcv);
            diagExameCandidato.setHiv(hiv);
            diagExameCandidato.setAghbs(aghbs);
            diagExameCandidato.setMal(mal);
            diagExameCandidato.setVdrl(vdrl);

            diagExameCandidato.setDataExame(new Date(data.getTimeInMillis()));
            diagExameCandidato.setFkIdCandidatoDador(diagCandidatoDador);
            diagExameCandidato.setFkIdEstadoClinico(diagEstadoClinico);
            diagExameCandidato.setConclusao(diagConclusaoExame);
            diagExameCandidatoFacade.create(diagExameCandidato);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Exames salvos com sucesso!";

            diagExameCandidato = null;
            diagConclusaoExame = null;
            diagEstadoClinico = null;

            List<DiagExameCandidato> listaExamesAux = diagExameCandidatoFacade.findExamesCandidatoPorCodigoPessoa(diagCandidatoDador.getFkIdPessoa());

            totalExames = listaExamesAux.size();
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return "visualizarCandidatoDador.xhtml?faces-redirect=true";
    }

    public String registarTipagemCandidato()
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagTipagemDador.setDataTipagem(new Date(data.getTimeInMillis()));
            diagTipagemDador.setFkIdCandidatoDador(diagCandidatoDador);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");

            diagTipagemDador.setFkRealizadoPor(sessaoActual.getFkIdFuncionario());
            diagTipagemDador.setConclusao(diagGrupoSanguineo);
            diagTipagemDadorFacade.create(diagTipagemDador);

            diagCandidatoDador.getFkIdPessoa().setFkIdGrupoSanguineo(diagGrupoSanguineo);

            grlPessoaFacade.edit(diagCandidatoDador.getFkIdPessoa());

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Tipagem sanguínea registada com sucesso!";

            diagTipagemDador = null;
            diagGrupoSanguineo = null;
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return visualizarCandidatoAposTipagem(diagCandidatoDador.getFkIdPessoa());
    }

    public String registarBolsaDeSangueCandidato()
    {
        GregorianCalendar data = new GregorianCalendar();
        Date dataExpiracao = DataUtils.addDias(data.getTime(), 30);

        System.out.println("data actual: " + data.toString());
        System.out.println("data exp: " + dataExpiracao.toString());

        try
        {
            userTransaction.begin();

            diagBolsaSangue.setDataCadastro(new Date(data.getTimeInMillis()));
            diagBolsaSangue.setDataExpiracao(dataExpiracao);
            diagBolsaSangue.setFkIdCandidatoDador(diagCandidatoDador);

            diagBolsaSangueFacade.create(diagBolsaSangue);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Bolsa de Sangue salva com sucesso!";

            diagBolsaSangue = null;
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return visualizarCandidato(diagCandidatoDador.getFkIdPessoa());
    }

    public String editarDadosPessoais()
    {
        if (validarIdade(diagCandidatoDadorEditar.getFkIdPessoa()))
        {
            try
            {
//                userTransaction.begin();

                System.out.println("antes diagCandidatoDadorEditar " + diagCandidatoDadorEditar.getFkIdPessoa().toString());
                
                contactoFacade.edit(diagCandidatoDadorEditar.getFkIdPessoa().getFkIdContacto());
                enderecoFacade.edit(diagCandidatoDadorEditar.getFkIdPessoa().getFkIdEndereco());

                gravarEndereco(diagCandidatoDadorEditar.getFkIdPessoa());

//                if (diagCandidatoDadorEditar.getFkIdReligiao().getPkIdReligiao() == null)
//                {
//                    diagCandidatoDadorEditar.setFkIdReligiao(getReligiaoPessoaEditar());
//                }
                diagCandidatoDadorFacade.edit(diagCandidatoDadorEditar);
                adicionarDocumentosIdentificacao(diagCandidatoDadorEditar.getFkIdPessoa());
//                userTransaction.commit();

                System.out.println("depois edit diagCandidatoDadorEditar " + diagCandidatoDadorEditar.getFkIdPessoa().toString());
                
                temMensagemPendente = true;

                tipoMensagemPendente = "Sucesso";

                mensagemPendente = "Dados editados com sucesso!";

                numeroDocumento = "";
            }
            catch (Exception e)
            {
                try
                {
                    userTransaction.rollback();

                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = e.toString();
                }
                catch (IllegalStateException | SecurityException | SystemException ex)
                {
                    temMensagemPendente = true;

                    tipoMensagemPendente = "Erro";

                    mensagemPendente = "Rollback: " + ex.toString();
                }

            }

//            diagCandidatoDadorEditar = DiagPessoaBean.getInstancia();
            return visualizarCandidato(diagCandidatoDadorEditar.getFkIdPessoa());
//            return "visualizarCandidatoDador.xhtml?faces-redirect=true";
        }
        else
        {
            Mensagem.erroMsg("O candidato a dador deve possuir idade igual ou superior a 18!");

            return null;
        }
    }

    public void gravarEndereco(GrlPessoa pessoaAux)
    {
        if (pessoaAux.getFkIdEndereco().getFkIdDistrito().getPkIdDistrito() == null)
        {
            pessoaAux.getFkIdEndereco().setFkIdDistrito(null);
        }
        if (pessoaAux.getFkIdEndereco().getFkIdComuna().getPkIdComuna() == null)
        {
            pessoaAux.getFkIdEndereco().setFkIdComuna(null);
        }

        enderecoFacade.create(pessoaAux.getFkIdEndereco());
    }

    public void adicionarDocumentosIdentificacao(GrlPessoa pessoaAux) throws Exception
    {
        for (GrlDocumentoIdentificacao doc : pessoaAux.getGrlDocumentoIdentificacaoList())
        {
            doc.setFkIdPessoa(pessoaAux);
            documentoIdentificacaoFacade.create(doc);
        }
    }

    public void adicionarDocumentoDeIdentificacao(GrlPessoa pessoaAux)
    {
        GrlTipoDocumentoIdentificacao tipoDocumentoObj = grlTipoDocumentoIdentificacaoFacade.find(tipoDocumento);
        if (validarDocumentoIdentificacao(pessoaAux, tipoDocumentoObj))
        {
            GrlDocumentoIdentificacao documentoIdentificacao = new GrlDocumentoIdentificacao();
            documentoIdentificacao.setNumeroDocumento(numeroDocumento);
            documentoIdentificacao.setFkTipoDocumentoIdentificacao(tipoDocumentoObj);
            pessoaAux.getGrlDocumentoIdentificacaoList().add(documentoIdentificacao);
        }
    }

    private boolean validarDocumentoIdentificacao(GrlPessoa pessoaAux, GrlTipoDocumentoIdentificacao tipoDocumentoObj)
    {
        if (!numeroDocumento.equals(""))
        {
            for (int i = 0; i < pessoaAux.getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if (pessoaAux.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
                        == tipoDocumentoObj.getPkTipoDocumentoIdentificacao())
                {
                    Mensagem.erroMsg("Tipo de documento ja adicionado, so pode ser adicionado uma vez");
                    return false;
                }
            }
        }
        else
        {
            Mensagem.erroMsg("O numero do documento nao pode ser um texto em branco");
            return false;
        }
        return true;
    }

    public void removerDocumentoIdentificacao(GrlPessoa pessoaAux, GrlDocumentoIdentificacao documento)
    {
        for (int i = 0; i < pessoaAux.getGrlDocumentoIdentificacaoList().size(); i++)
        {
            if (pessoaAux.getGrlDocumentoIdentificacaoList().get(i).getNumeroDocumento().equalsIgnoreCase(documento.getNumeroDocumento())
                    && pessoaAux.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
                    == documento.getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao())
            {
                pessoaAux.getGrlDocumentoIdentificacaoList().remove(i);
                break;
            }
        }
        //pessoa.getGrlDocumentoIdentificacaoList().remove(documento);
    }

    public void definirPessoaDocumentoId(GrlPessoa pessoaDocumento)
    {
        this.grlPessoaDocumento = pessoaDocumento;
    }

    public String documentosDeIdentificacao(GrlPessoa pessoaAux)
    {
        String documentos;
        if (pessoaAux.getGrlDocumentoIdentificacaoList().isEmpty() || pessoaAux.getGrlDocumentoIdentificacaoList() == null)
        {
            return "Sem Documentos Ainda";
        }
        documentos = "Os Documentos São :";
        for (int i = 0; i < pessoaAux.getGrlDocumentoIdentificacaoList().size(); i++)
        {
            documentos += "\n   " + pessoaAux.getGrlDocumentoIdentificacaoList().get(i)
                    .getFkTipoDocumentoIdentificacao().getDescricao() + ": " + pessoaAux.getGrlDocumentoIdentificacaoList().get(i)
                    .getNumeroDocumento();
        }
        return documentos;
    }
}
