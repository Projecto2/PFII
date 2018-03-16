/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_funcionario", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_anexo_guia_transferencia"}),
    @UniqueConstraint(columnNames = {"numero_bi"}),
    @UniqueConstraint(columnNames = {"codigo_funcionario"}),
    @UniqueConstraint(columnNames = {"fk_id_pessoa"}),
    @UniqueConstraint(columnNames = {"numero_seguranca_social"}),
    @UniqueConstraint(columnNames = {"numero_cartao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhFuncionario.findAll", query = "SELECT r FROM RhFuncionario r"),
    @NamedQuery(name = "RhFuncionario.findByPkIdFuncionario", query = "SELECT r FROM RhFuncionario r WHERE r.pkIdFuncionario = :pkIdFuncionario"),
    @NamedQuery(name = "RhFuncionario.findByNumeroGuiaTransferencia", query = "SELECT r FROM RhFuncionario r WHERE r.numeroGuiaTransferencia = :numeroGuiaTransferencia"),
    @NamedQuery(name = "RhFuncionario.findByDataAdmissao", query = "SELECT r FROM RhFuncionario r WHERE r.dataAdmissao = :dataAdmissao"),
    @NamedQuery(name = "RhFuncionario.findByNumeroBi", query = "SELECT r FROM RhFuncionario r WHERE r.numeroBi = :numeroBi"),
    @NamedQuery(name = "RhFuncionario.findByCodigoFuncionario", query = "SELECT r FROM RhFuncionario r WHERE r.codigoFuncionario = :codigoFuncionario"),
    @NamedQuery(name = "RhFuncionario.findByNumeroSegurancaSocial", query = "SELECT r FROM RhFuncionario r WHERE r.numeroSegurancaSocial = :numeroSegurancaSocial"),
    @NamedQuery(name = "RhFuncionario.findByNumeroContribuinte", query = "SELECT r FROM RhFuncionario r WHERE r.numeroContribuinte = :numeroContribuinte"),
    @NamedQuery(name = "RhFuncionario.findByDataCadastro", query = "SELECT r FROM RhFuncionario r WHERE r.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "RhFuncionario.findByDataDemissao", query = "SELECT r FROM RhFuncionario r WHERE r.dataDemissao = :dataDemissao"),
    @NamedQuery(name = "RhFuncionario.findByDataReforma", query = "SELECT r FROM RhFuncionario r WHERE r.dataReforma = :dataReforma"),
    @NamedQuery(name = "RhFuncionario.findByNumeroCartao", query = "SELECT r FROM RhFuncionario r WHERE r.numeroCartao = :numeroCartao")})
public class RhFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_funcionario", nullable = false)
    private Integer pkIdFuncionario;
    @Size(max = 45)
    @Column(name = "numero_guia_transferencia", length = 45)
    private String numeroGuiaTransferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_admissao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "numero_bi", nullable = false, length = 40)
    private String numeroBi;
    @Size(max = 2147483647)
    @Column(name = "codigo_funcionario", length = 2147483647)
    private String codigoFuncionario;
    @Size(max = 2147483647)
    @Column(name = "numero_seguranca_social", length = 2147483647)
    private String numeroSegurancaSocial;
    @Size(max = 2147483647)
    @Column(name = "numero_contribuinte", length = 2147483647)
    private String numeroContribuinte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Column(name = "data_demissao")
    @Temporal(TemporalType.DATE)
    private Date dataDemissao;
    @Column(name = "data_reforma")
    @Temporal(TemporalType.DATE)
    private Date dataReforma;
    @Column(name = "numero_cartao")
    private Integer numeroCartao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<SupiFormacaoFuncionarioPk> supiFormacaoFuncionarioPkList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbDiagnostico> ambDiagnosticoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhSalarioFuncionario> rhSalarioFuncionarioList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbPrescricaoMedica> ambPrescricaoMedicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterControloParametrosVitais> interControloParametrosVitaisList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<SupiCriacaoTurma> supiCriacaoTurmaList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<FarmQuarentena> farmQuarentenaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkChefePosto")
    private List<SupiProgramaPosto> supiProgramaPostoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMedico")
    private List<SupiProgramaPosto> supiProgramaPostoList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkResponsavelActividade")
    private List<SupiProgramaPosto> supiProgramaPostoList2;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<FarmEstadoNotificacao> farmEstadoNotificacaoList;
    @OneToMany(mappedBy = "fkIdFuncionarioRegistouPagamento")
    private List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList;
    @OneToMany(mappedBy = "fkIdFuncionarioCancelou")
    private List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhContrato> rhContratoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterAnotacaoMedica> interAnotacaoMedicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionarioCadastrou")
    private List<FarmLoteProduto> farmLoteProdutoList;
    @OneToMany(mappedBy = "fkEnfermeiro")
    private List<TbTriagem> tbTriagemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList;
    @JoinColumn(name = "fk_id_horario_geral_de_trabalho", referencedColumnName = "pk_id_horario_geral_de_trabalho")
    @ManyToOne
    private RhHorarioGeralDeTrabalho fkIdHorarioGeralDeTrabalho;
    @JoinColumn(name = "fk_id_especialidade1", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidade1;
    @JoinColumn(name = "fk_id_centro_hospitalar", referencedColumnName = "pk_id_centro")
    @ManyToOne
    private GrlCentroHospitalar fkIdCentroHospitalar;
    @JoinColumn(name = "fk_id_especialidade2", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidade2;
    @JoinColumn(name = "fk_id_anexo_guia_transferencia", referencedColumnName = "pk_id_ficheiro_anexado")
    @OneToOne
    private GrlFicheiroAnexado fkIdAnexoGuiaTransferencia;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa", nullable = false)
    @OneToOne(optional = false)
    private GrlPessoa fkIdPessoa;
    @JoinColumn(name = "fk_id_categoria", referencedColumnName = "pk_id_categoria_profissional", nullable = false)
    @ManyToOne(optional = false)
    private RhCategoriaProfissional fkIdCategoria;
    @JoinColumn(name = "fk_id_tipo_funcionario", referencedColumnName = "pk_id_tipo_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhTipoFuncionario fkIdTipoFuncionario;
    @JoinColumn(name = "fk_id_estado_funcionario", referencedColumnName = "pk_id_estado_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhEstadoFuncionario fkIdEstadoFuncionario;
    @JoinColumn(name = "fk_id_funcao", referencedColumnName = "pk_id_funcao", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncao fkIdFuncao;
    @JoinColumn(name = "fk_id_nivel_academico", referencedColumnName = "pk_id_nivel_academico", nullable = false)
    @ManyToOne(optional = false)
    private RhNivelAcademico fkIdNivelAcademico;
    @JoinColumn(name = "fk_id_tipo_de_horario_trabalho", referencedColumnName = "pk_id__tipo_de_horario_trabalho")
    @ManyToOne
    private RhTipoDeHorarioTrabalho fkIdTipoDeHorarioTrabalho;
    @JoinColumn(name = "fk_id_seccao_trabalho", referencedColumnName = "pk_id_seccao_trabalho")
    @ManyToOne
    private RhSeccaoTrabalho fkIdSeccaoTrabalho;
    @JoinColumn(name = "fk_id_profissao", referencedColumnName = "pk_id_profissao")
    @ManyToOne
    private RhProfissao fkIdProfissao;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<SupiFormacaoFormando> supiFormacaoFormandoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<SupiFormadorAux> supiFormadorAuxList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList;
    @OneToMany(mappedBy = "fkRealizadoPor")
    private List<DiagTipagemDoente> diagTipagemDoenteList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbTriagem> ambTriagemList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbTransferencia> ambTransferenciaList;
    @OneToMany(mappedBy = "fkIdFuncionarioCadastrou")
    private List<FarmProduto> farmProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<EmgUrgencia> emgUrgenciaList;
    @OneToMany(mappedBy = "fkIdMedico")
    private List<AdmsDiaHoraDeAtendimentoDoMedico> admsDiaHoraDeAtendimentoDoMedicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterMedicacao> interMedicacaoList;
    @OneToMany(mappedBy = "fkIdFuncionarioSolicitante")
    private List<AdmsSolicitacao> admsSolicitacaoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<DiagExameRealizado> diagExameRealizadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterRealizarMedicacao> interRealizarMedicacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMedico")
    private List<SupiMedicoHasEscala> supiMedicoHasEscalaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<EmgTratamento> emgTratamentoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<DiagControloSemanalMaterial> diagControloSemanalMaterialList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<SupiAgendaAula> supiAgendaAulaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionarioQueAbriu")
    private List<FarmTurnoDispensa> farmTurnoDispensaList;
    @OneToMany(mappedBy = "fkIdFuncionarioQueFechou")
    private List<FarmTurnoDispensa> farmTurnoDispensaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhFuncionarioHasRhSubsidio> rhFuncionarioHasRhSubsidioList;
    @OneToMany(mappedBy = "fkIdRecepcionista")
    private List<AdmsServicoSolicitado> admsServicoSolicitadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhFuncionarioHasRhTipoFalta> rhFuncionarioHasRhTipoFaltaList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbConsulta> ambConsultaList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<FarmFichaStock> farmFichaStockList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<SupiFormador> supiFormadorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<SupiFormacaoFuncionario> supiFormacaoFuncionarioList;
    @OneToMany(mappedBy = "fkIdMedico")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhFerias> rhFeriasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhDependente> rhDependenteList;
    @OneToMany(mappedBy = "fkIdFuncionarioAssistente")
    private List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionarioChefeEquipe")
    private List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<FarmDispensa> farmDispensaList;
    @OneToMany(mappedBy = "fkIdFuncionarioSupervisor")
    private List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEnfermeiro")
    private List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList1;
    @OneToMany(mappedBy = "fkRealizadoPor")
    private List<DiagTipagemDador> diagTipagemDadorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionarioCadastrou")
    private List<FarmFornecimento> farmFornecimentoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<DiagSolicitacaoTipagemDoente> diagSolicitacaoTipagemDoenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMedico")
    private List<AdmsAgendamentoMedico> admsAgendamentoMedicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<RhAvaliacaoDeDesempenho> rhAvaliacaoDeDesempenhoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<InterConclusao> interConclusaoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<DiagColecta> diagColectaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<SupiProgramaFuncionario> supiProgramaFuncionarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterRegistoSaida> interRegistoSaidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterTituloAlta> interTituloAltaList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<DiagRespostaRequisicaoComponenteSanguineo> diagRespostaRequisicaoComponenteSanguineoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterInscricaoInternamento> interInscricaoInternamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<FarmDoacao> farmDoacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterTratamentoMalnutricao> interTratamentoMalnutricaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList;
    @OneToMany(mappedBy = "fkIdRecepcionista")
    private List<FinPagamento> finPagamentoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<InterVacinacao> interVacinacaoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbConsultorioAtendimento> ambConsultorioAtendimentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionarioEnfermeiro")
    private List<InterRegistoInternamento> interRegistoInternamentoList;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<SupiAvaliacaoDesempenho> supiAvaliacaoDesempenhoList;
    @OneToMany(mappedBy = "fkRealizadoPor")
    private List<DiagTransfusao> diagTransfusaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionario")
    private List<InterAnotacaoEnfermagem> interAnotacaoEnfermagemList;
    @OneToMany(mappedBy = "fkIdFuncionarioAtendeu")
    private List<FarmPedido> farmPedidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncionarioSolicitou")
    private List<FarmPedido> farmPedidoList1;
    @OneToMany(mappedBy = "fkIdFuncionario")
    private List<AmbDiagnosticoHipotese> ambDiagnosticoHipoteseList;
    @OneToOne(mappedBy = "fkIdFuncionario")
    private SegConta segConta;

    public RhFuncionario() {
    }

    public RhFuncionario(Integer pkIdFuncionario) {
        this.pkIdFuncionario = pkIdFuncionario;
    }

    public RhFuncionario(Integer pkIdFuncionario, Date dataAdmissao, String numeroBi, Date dataCadastro) {
        this.pkIdFuncionario = pkIdFuncionario;
        this.dataAdmissao = dataAdmissao;
        this.numeroBi = numeroBi;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdFuncionario() {
        return pkIdFuncionario;
    }

    public void setPkIdFuncionario(Integer pkIdFuncionario) {
        this.pkIdFuncionario = pkIdFuncionario;
    }

    public String getNumeroGuiaTransferencia() {
        return numeroGuiaTransferencia;
    }

    public void setNumeroGuiaTransferencia(String numeroGuiaTransferencia) {
        this.numeroGuiaTransferencia = numeroGuiaTransferencia;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getNumeroBi() {
        return numeroBi;
    }

    public void setNumeroBi(String numeroBi) {
        this.numeroBi = numeroBi;
    }

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getNumeroSegurancaSocial() {
        return numeroSegurancaSocial;
    }

    public void setNumeroSegurancaSocial(String numeroSegurancaSocial) {
        this.numeroSegurancaSocial = numeroSegurancaSocial;
    }

    public String getNumeroContribuinte() {
        return numeroContribuinte;
    }

    public void setNumeroContribuinte(String numeroContribuinte) {
        this.numeroContribuinte = numeroContribuinte;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Date getDataReforma() {
        return dataReforma;
    }

    public void setDataReforma(Date dataReforma) {
        this.dataReforma = dataReforma;
    }

    public Integer getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(Integer numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @XmlTransient
    public List<SupiFormacaoFuncionarioPk> getSupiFormacaoFuncionarioPkList() {
        return supiFormacaoFuncionarioPkList;
    }

    public void setSupiFormacaoFuncionarioPkList(List<SupiFormacaoFuncionarioPk> supiFormacaoFuncionarioPkList) {
        this.supiFormacaoFuncionarioPkList = supiFormacaoFuncionarioPkList;
    }

    @XmlTransient
    public List<AmbDiagnostico> getAmbDiagnosticoList() {
        return ambDiagnosticoList;
    }

    public void setAmbDiagnosticoList(List<AmbDiagnostico> ambDiagnosticoList) {
        this.ambDiagnosticoList = ambDiagnosticoList;
    }

    @XmlTransient
    public List<RhSalarioFuncionario> getRhSalarioFuncionarioList() {
        return rhSalarioFuncionarioList;
    }

    public void setRhSalarioFuncionarioList(List<RhSalarioFuncionario> rhSalarioFuncionarioList) {
        this.rhSalarioFuncionarioList = rhSalarioFuncionarioList;
    }

    @XmlTransient
    public List<AmbPrescricaoMedica> getAmbPrescricaoMedicaList() {
        return ambPrescricaoMedicaList;
    }

    public void setAmbPrescricaoMedicaList(List<AmbPrescricaoMedica> ambPrescricaoMedicaList) {
        this.ambPrescricaoMedicaList = ambPrescricaoMedicaList;
    }

    @XmlTransient
    public List<InterControloParametrosVitais> getInterControloParametrosVitaisList() {
        return interControloParametrosVitaisList;
    }

    public void setInterControloParametrosVitaisList(List<InterControloParametrosVitais> interControloParametrosVitaisList) {
        this.interControloParametrosVitaisList = interControloParametrosVitaisList;
    }

    @XmlTransient
    public List<SupiCriacaoTurma> getSupiCriacaoTurmaList() {
        return supiCriacaoTurmaList;
    }

    public void setSupiCriacaoTurmaList(List<SupiCriacaoTurma> supiCriacaoTurmaList) {
        this.supiCriacaoTurmaList = supiCriacaoTurmaList;
    }

    @XmlTransient
    public List<FarmQuarentena> getFarmQuarentenaList() {
        return farmQuarentenaList;
    }

    public void setFarmQuarentenaList(List<FarmQuarentena> farmQuarentenaList) {
        this.farmQuarentenaList = farmQuarentenaList;
    }

    @XmlTransient
    public List<SupiProgramaPosto> getSupiProgramaPostoList() {
        return supiProgramaPostoList;
    }

    public void setSupiProgramaPostoList(List<SupiProgramaPosto> supiProgramaPostoList) {
        this.supiProgramaPostoList = supiProgramaPostoList;
    }

    @XmlTransient
    public List<SupiProgramaPosto> getSupiProgramaPostoList1() {
        return supiProgramaPostoList1;
    }

    public void setSupiProgramaPostoList1(List<SupiProgramaPosto> supiProgramaPostoList1) {
        this.supiProgramaPostoList1 = supiProgramaPostoList1;
    }

    @XmlTransient
    public List<SupiProgramaPosto> getSupiProgramaPostoList2() {
        return supiProgramaPostoList2;
    }

    public void setSupiProgramaPostoList2(List<SupiProgramaPosto> supiProgramaPostoList2) {
        this.supiProgramaPostoList2 = supiProgramaPostoList2;
    }

    @XmlTransient
    public List<FarmEstadoNotificacao> getFarmEstadoNotificacaoList() {
        return farmEstadoNotificacaoList;
    }

    public void setFarmEstadoNotificacaoList(List<FarmEstadoNotificacao> farmEstadoNotificacaoList) {
        this.farmEstadoNotificacaoList = farmEstadoNotificacaoList;
    }

    @XmlTransient
    public List<FinHistoricoPagamentoCancelados> getFinHistoricoPagamentoCanceladosList() {
        return finHistoricoPagamentoCanceladosList;
    }

    public void setFinHistoricoPagamentoCanceladosList(List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList) {
        this.finHistoricoPagamentoCanceladosList = finHistoricoPagamentoCanceladosList;
    }

    @XmlTransient
    public List<FinHistoricoPagamentoCancelados> getFinHistoricoPagamentoCanceladosList1() {
        return finHistoricoPagamentoCanceladosList1;
    }

    public void setFinHistoricoPagamentoCanceladosList1(List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList1) {
        this.finHistoricoPagamentoCanceladosList1 = finHistoricoPagamentoCanceladosList1;
    }

    @XmlTransient
    public List<RhContrato> getRhContratoList() {
        return rhContratoList;
    }

    public void setRhContratoList(List<RhContrato> rhContratoList) {
        this.rhContratoList = rhContratoList;
    }

    @XmlTransient
    public List<InterAnotacaoMedica> getInterAnotacaoMedicaList() {
        return interAnotacaoMedicaList;
    }

    public void setInterAnotacaoMedicaList(List<InterAnotacaoMedica> interAnotacaoMedicaList) {
        this.interAnotacaoMedicaList = interAnotacaoMedicaList;
    }

    @XmlTransient
    public List<FarmLoteProduto> getFarmLoteProdutoList() {
        return farmLoteProdutoList;
    }

    public void setFarmLoteProdutoList(List<FarmLoteProduto> farmLoteProdutoList) {
        this.farmLoteProdutoList = farmLoteProdutoList;
    }

    @XmlTransient
    public List<TbTriagem> getTbTriagemList() {
        return tbTriagemList;
    }

    public void setTbTriagemList(List<TbTriagem> tbTriagemList) {
        this.tbTriagemList = tbTriagemList;
    }

    @XmlTransient
    public List<InterRegistoInternamentoHasParametroVital> getInterRegistoInternamentoHasParametroVitalList() {
        return interRegistoInternamentoHasParametroVitalList;
    }

    public void setInterRegistoInternamentoHasParametroVitalList(List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList) {
        this.interRegistoInternamentoHasParametroVitalList = interRegistoInternamentoHasParametroVitalList;
    }

    public RhHorarioGeralDeTrabalho getFkIdHorarioGeralDeTrabalho() {
        return fkIdHorarioGeralDeTrabalho;
    }

    public void setFkIdHorarioGeralDeTrabalho(RhHorarioGeralDeTrabalho fkIdHorarioGeralDeTrabalho) {
        this.fkIdHorarioGeralDeTrabalho = fkIdHorarioGeralDeTrabalho;
    }

    public GrlEspecialidade getFkIdEspecialidade1() {
        return fkIdEspecialidade1;
    }

    public void setFkIdEspecialidade1(GrlEspecialidade fkIdEspecialidade1) {
        this.fkIdEspecialidade1 = fkIdEspecialidade1;
    }

    public GrlCentroHospitalar getFkIdCentroHospitalar() {
        return fkIdCentroHospitalar;
    }

    public void setFkIdCentroHospitalar(GrlCentroHospitalar fkIdCentroHospitalar) {
        this.fkIdCentroHospitalar = fkIdCentroHospitalar;
    }

    public GrlEspecialidade getFkIdEspecialidade2() {
        return fkIdEspecialidade2;
    }

    public void setFkIdEspecialidade2(GrlEspecialidade fkIdEspecialidade2) {
        this.fkIdEspecialidade2 = fkIdEspecialidade2;
    }

    public GrlFicheiroAnexado getFkIdAnexoGuiaTransferencia() {
        return fkIdAnexoGuiaTransferencia;
    }

    public void setFkIdAnexoGuiaTransferencia(GrlFicheiroAnexado fkIdAnexoGuiaTransferencia) {
        this.fkIdAnexoGuiaTransferencia = fkIdAnexoGuiaTransferencia;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    public RhCategoriaProfissional getFkIdCategoria() {
        return fkIdCategoria;
    }

    public void setFkIdCategoria(RhCategoriaProfissional fkIdCategoria) {
        this.fkIdCategoria = fkIdCategoria;
    }

    public RhTipoFuncionario getFkIdTipoFuncionario() {
        return fkIdTipoFuncionario;
    }

    public void setFkIdTipoFuncionario(RhTipoFuncionario fkIdTipoFuncionario) {
        this.fkIdTipoFuncionario = fkIdTipoFuncionario;
    }

    public RhEstadoFuncionario getFkIdEstadoFuncionario() {
        return fkIdEstadoFuncionario;
    }

    public void setFkIdEstadoFuncionario(RhEstadoFuncionario fkIdEstadoFuncionario) {
        this.fkIdEstadoFuncionario = fkIdEstadoFuncionario;
    }

    public RhFuncao getFkIdFuncao() {
        return fkIdFuncao;
    }

    public void setFkIdFuncao(RhFuncao fkIdFuncao) {
        this.fkIdFuncao = fkIdFuncao;
    }

    public RhNivelAcademico getFkIdNivelAcademico() {
        return fkIdNivelAcademico;
    }

    public void setFkIdNivelAcademico(RhNivelAcademico fkIdNivelAcademico) {
        this.fkIdNivelAcademico = fkIdNivelAcademico;
    }

    public RhTipoDeHorarioTrabalho getFkIdTipoDeHorarioTrabalho() {
        return fkIdTipoDeHorarioTrabalho;
    }

    public void setFkIdTipoDeHorarioTrabalho(RhTipoDeHorarioTrabalho fkIdTipoDeHorarioTrabalho) {
        this.fkIdTipoDeHorarioTrabalho = fkIdTipoDeHorarioTrabalho;
    }

    public RhSeccaoTrabalho getFkIdSeccaoTrabalho() {
        return fkIdSeccaoTrabalho;
    }

    public void setFkIdSeccaoTrabalho(RhSeccaoTrabalho fkIdSeccaoTrabalho) {
        this.fkIdSeccaoTrabalho = fkIdSeccaoTrabalho;
    }

    public RhProfissao getFkIdProfissao() {
        return fkIdProfissao;
    }

    public void setFkIdProfissao(RhProfissao fkIdProfissao) {
        this.fkIdProfissao = fkIdProfissao;
    }

    @XmlTransient
    public List<SupiFormacaoFormando> getSupiFormacaoFormandoList() {
        return supiFormacaoFormandoList;
    }

    public void setSupiFormacaoFormandoList(List<SupiFormacaoFormando> supiFormacaoFormandoList) {
        this.supiFormacaoFormandoList = supiFormacaoFormandoList;
    }

    @XmlTransient
    public List<SupiFormadorAux> getSupiFormadorAuxList() {
        return supiFormadorAuxList;
    }

    public void setSupiFormadorAuxList(List<SupiFormadorAux> supiFormadorAuxList) {
        this.supiFormadorAuxList = supiFormadorAuxList;
    }

    @XmlTransient
    public List<InterDoencaInternamentoPaciente> getInterDoencaInternamentoPacienteList() {
        return interDoencaInternamentoPacienteList;
    }

    public void setInterDoencaInternamentoPacienteList(List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList) {
        this.interDoencaInternamentoPacienteList = interDoencaInternamentoPacienteList;
    }

    @XmlTransient
    public List<DiagTipagemDoente> getDiagTipagemDoenteList() {
        return diagTipagemDoenteList;
    }

    public void setDiagTipagemDoenteList(List<DiagTipagemDoente> diagTipagemDoenteList) {
        this.diagTipagemDoenteList = diagTipagemDoenteList;
    }

    @XmlTransient
    public List<AmbTriagem> getAmbTriagemList() {
        return ambTriagemList;
    }

    public void setAmbTriagemList(List<AmbTriagem> ambTriagemList) {
        this.ambTriagemList = ambTriagemList;
    }

    @XmlTransient
    public List<AmbTransferencia> getAmbTransferenciaList() {
        return ambTransferenciaList;
    }

    public void setAmbTransferenciaList(List<AmbTransferencia> ambTransferenciaList) {
        this.ambTransferenciaList = ambTransferenciaList;
    }

    @XmlTransient
    public List<FarmProduto> getFarmProdutoList() {
        return farmProdutoList;
    }

    public void setFarmProdutoList(List<FarmProduto> farmProdutoList) {
        this.farmProdutoList = farmProdutoList;
    }

    @XmlTransient
    public List<EmgUrgencia> getEmgUrgenciaList() {
        return emgUrgenciaList;
    }

    public void setEmgUrgenciaList(List<EmgUrgencia> emgUrgenciaList) {
        this.emgUrgenciaList = emgUrgenciaList;
    }

    @XmlTransient
    public List<AdmsDiaHoraDeAtendimentoDoMedico> getAdmsDiaHoraDeAtendimentoDoMedicoList() {
        return admsDiaHoraDeAtendimentoDoMedicoList;
    }

    public void setAdmsDiaHoraDeAtendimentoDoMedicoList(List<AdmsDiaHoraDeAtendimentoDoMedico> admsDiaHoraDeAtendimentoDoMedicoList) {
        this.admsDiaHoraDeAtendimentoDoMedicoList = admsDiaHoraDeAtendimentoDoMedicoList;
    }

    @XmlTransient
    public List<InterMedicacao> getInterMedicacaoList() {
        return interMedicacaoList;
    }

    public void setInterMedicacaoList(List<InterMedicacao> interMedicacaoList) {
        this.interMedicacaoList = interMedicacaoList;
    }

    @XmlTransient
    public List<AdmsSolicitacao> getAdmsSolicitacaoList() {
        return admsSolicitacaoList;
    }

    public void setAdmsSolicitacaoList(List<AdmsSolicitacao> admsSolicitacaoList) {
        this.admsSolicitacaoList = admsSolicitacaoList;
    }

    @XmlTransient
    public List<DiagExameRealizado> getDiagExameRealizadoList() {
        return diagExameRealizadoList;
    }

    public void setDiagExameRealizadoList(List<DiagExameRealizado> diagExameRealizadoList) {
        this.diagExameRealizadoList = diagExameRealizadoList;
    }

    @XmlTransient
    public List<InterRealizarMedicacao> getInterRealizarMedicacaoList() {
        return interRealizarMedicacaoList;
    }

    public void setInterRealizarMedicacaoList(List<InterRealizarMedicacao> interRealizarMedicacaoList) {
        this.interRealizarMedicacaoList = interRealizarMedicacaoList;
    }

    @XmlTransient
    public List<SupiMedicoHasEscala> getSupiMedicoHasEscalaList() {
        return supiMedicoHasEscalaList;
    }

    public void setSupiMedicoHasEscalaList(List<SupiMedicoHasEscala> supiMedicoHasEscalaList) {
        this.supiMedicoHasEscalaList = supiMedicoHasEscalaList;
    }

    @XmlTransient
    public List<EmgTratamento> getEmgTratamentoList() {
        return emgTratamentoList;
    }

    public void setEmgTratamentoList(List<EmgTratamento> emgTratamentoList) {
        this.emgTratamentoList = emgTratamentoList;
    }

    @XmlTransient
    public List<DiagControloSemanalMaterial> getDiagControloSemanalMaterialList() {
        return diagControloSemanalMaterialList;
    }

    public void setDiagControloSemanalMaterialList(List<DiagControloSemanalMaterial> diagControloSemanalMaterialList) {
        this.diagControloSemanalMaterialList = diagControloSemanalMaterialList;
    }

    @XmlTransient
    public List<SupiAgendaAula> getSupiAgendaAulaList() {
        return supiAgendaAulaList;
    }

    public void setSupiAgendaAulaList(List<SupiAgendaAula> supiAgendaAulaList) {
        this.supiAgendaAulaList = supiAgendaAulaList;
    }

    @XmlTransient
    public List<FarmTurnoDispensa> getFarmTurnoDispensaList() {
        return farmTurnoDispensaList;
    }

    public void setFarmTurnoDispensaList(List<FarmTurnoDispensa> farmTurnoDispensaList) {
        this.farmTurnoDispensaList = farmTurnoDispensaList;
    }

    @XmlTransient
    public List<FarmTurnoDispensa> getFarmTurnoDispensaList1() {
        return farmTurnoDispensaList1;
    }

    public void setFarmTurnoDispensaList1(List<FarmTurnoDispensa> farmTurnoDispensaList1) {
        this.farmTurnoDispensaList1 = farmTurnoDispensaList1;
    }

    @XmlTransient
    public List<RhFuncionarioHasRhSubsidio> getRhFuncionarioHasRhSubsidioList() {
        return rhFuncionarioHasRhSubsidioList;
    }

    public void setRhFuncionarioHasRhSubsidioList(List<RhFuncionarioHasRhSubsidio> rhFuncionarioHasRhSubsidioList) {
        this.rhFuncionarioHasRhSubsidioList = rhFuncionarioHasRhSubsidioList;
    }

    @XmlTransient
    public List<AdmsServicoSolicitado> getAdmsServicoSolicitadoList() {
        return admsServicoSolicitadoList;
    }

    public void setAdmsServicoSolicitadoList(List<AdmsServicoSolicitado> admsServicoSolicitadoList) {
        this.admsServicoSolicitadoList = admsServicoSolicitadoList;
    }

    @XmlTransient
    public List<RhFuncionarioHasRhTipoFalta> getRhFuncionarioHasRhTipoFaltaList() {
        return rhFuncionarioHasRhTipoFaltaList;
    }

    public void setRhFuncionarioHasRhTipoFaltaList(List<RhFuncionarioHasRhTipoFalta> rhFuncionarioHasRhTipoFaltaList) {
        this.rhFuncionarioHasRhTipoFaltaList = rhFuncionarioHasRhTipoFaltaList;
    }

    @XmlTransient
    public List<AmbConsulta> getAmbConsultaList() {
        return ambConsultaList;
    }

    public void setAmbConsultaList(List<AmbConsulta> ambConsultaList) {
        this.ambConsultaList = ambConsultaList;
    }

    @XmlTransient
    public List<FarmFichaStock> getFarmFichaStockList() {
        return farmFichaStockList;
    }

    public void setFarmFichaStockList(List<FarmFichaStock> farmFichaStockList) {
        this.farmFichaStockList = farmFichaStockList;
    }

    @XmlTransient
    public List<SupiFormador> getSupiFormadorList() {
        return supiFormadorList;
    }

    public void setSupiFormadorList(List<SupiFormador> supiFormadorList) {
        this.supiFormadorList = supiFormadorList;
    }

    @XmlTransient
    public List<SupiFormacaoFuncionario> getSupiFormacaoFuncionarioList() {
        return supiFormacaoFuncionarioList;
    }

    public void setSupiFormacaoFuncionarioList(List<SupiFormacaoFuncionario> supiFormacaoFuncionarioList) {
        this.supiFormacaoFuncionarioList = supiFormacaoFuncionarioList;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList() {
        return diagRequisicaoComponenteSanguineoList;
    }

    public void setDiagRequisicaoComponenteSanguineoList(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList) {
        this.diagRequisicaoComponenteSanguineoList = diagRequisicaoComponenteSanguineoList;
    }

    @XmlTransient
    public List<RhFerias> getRhFeriasList() {
        return rhFeriasList;
    }

    public void setRhFeriasList(List<RhFerias> rhFeriasList) {
        this.rhFeriasList = rhFeriasList;
    }

    @XmlTransient
    public List<RhDependente> getRhDependenteList() {
        return rhDependenteList;
    }

    public void setRhDependenteList(List<RhDependente> rhDependenteList) {
        this.rhDependenteList = rhDependenteList;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaDoentes> getInterGuiaTransferenciaDoentesList() {
        return interGuiaTransferenciaDoentesList;
    }

    public void setInterGuiaTransferenciaDoentesList(List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList) {
        this.interGuiaTransferenciaDoentesList = interGuiaTransferenciaDoentesList;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaDoentes> getInterGuiaTransferenciaDoentesList1() {
        return interGuiaTransferenciaDoentesList1;
    }

    public void setInterGuiaTransferenciaDoentesList1(List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList1) {
        this.interGuiaTransferenciaDoentesList1 = interGuiaTransferenciaDoentesList1;
    }

    @XmlTransient
    public List<FarmDispensa> getFarmDispensaList() {
        return farmDispensaList;
    }

    public void setFarmDispensaList(List<FarmDispensa> farmDispensaList) {
        this.farmDispensaList = farmDispensaList;
    }

    @XmlTransient
    public List<SupiEnfermeiroHasEscala> getSupiEnfermeiroHasEscalaList() {
        return supiEnfermeiroHasEscalaList;
    }

    public void setSupiEnfermeiroHasEscalaList(List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList) {
        this.supiEnfermeiroHasEscalaList = supiEnfermeiroHasEscalaList;
    }

    @XmlTransient
    public List<SupiEnfermeiroHasEscala> getSupiEnfermeiroHasEscalaList1() {
        return supiEnfermeiroHasEscalaList1;
    }

    public void setSupiEnfermeiroHasEscalaList1(List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList1) {
        this.supiEnfermeiroHasEscalaList1 = supiEnfermeiroHasEscalaList1;
    }

    @XmlTransient
    public List<DiagTipagemDador> getDiagTipagemDadorList() {
        return diagTipagemDadorList;
    }

    public void setDiagTipagemDadorList(List<DiagTipagemDador> diagTipagemDadorList) {
        this.diagTipagemDadorList = diagTipagemDadorList;
    }

    @XmlTransient
    public List<FarmFornecimento> getFarmFornecimentoList() {
        return farmFornecimentoList;
    }

    public void setFarmFornecimentoList(List<FarmFornecimento> farmFornecimentoList) {
        this.farmFornecimentoList = farmFornecimentoList;
    }

    @XmlTransient
    public List<DiagSolicitacaoTipagemDoente> getDiagSolicitacaoTipagemDoenteList() {
        return diagSolicitacaoTipagemDoenteList;
    }

    public void setDiagSolicitacaoTipagemDoenteList(List<DiagSolicitacaoTipagemDoente> diagSolicitacaoTipagemDoenteList) {
        this.diagSolicitacaoTipagemDoenteList = diagSolicitacaoTipagemDoenteList;
    }

    @XmlTransient
    public List<AdmsAgendamentoMedico> getAdmsAgendamentoMedicoList() {
        return admsAgendamentoMedicoList;
    }

    public void setAdmsAgendamentoMedicoList(List<AdmsAgendamentoMedico> admsAgendamentoMedicoList) {
        this.admsAgendamentoMedicoList = admsAgendamentoMedicoList;
    }

    @XmlTransient
    public List<RhAvaliacaoDeDesempenho> getRhAvaliacaoDeDesempenhoList() {
        return rhAvaliacaoDeDesempenhoList;
    }

    public void setRhAvaliacaoDeDesempenhoList(List<RhAvaliacaoDeDesempenho> rhAvaliacaoDeDesempenhoList) {
        this.rhAvaliacaoDeDesempenhoList = rhAvaliacaoDeDesempenhoList;
    }

    @XmlTransient
    public List<InterConclusao> getInterConclusaoList() {
        return interConclusaoList;
    }

    public void setInterConclusaoList(List<InterConclusao> interConclusaoList) {
        this.interConclusaoList = interConclusaoList;
    }

    @XmlTransient
    public List<DiagColecta> getDiagColectaList() {
        return diagColectaList;
    }

    public void setDiagColectaList(List<DiagColecta> diagColectaList) {
        this.diagColectaList = diagColectaList;
    }

    @XmlTransient
    public List<SupiProgramaFuncionario> getSupiProgramaFuncionarioList() {
        return supiProgramaFuncionarioList;
    }

    public void setSupiProgramaFuncionarioList(List<SupiProgramaFuncionario> supiProgramaFuncionarioList) {
        this.supiProgramaFuncionarioList = supiProgramaFuncionarioList;
    }

    @XmlTransient
    public List<InterRegistoSaida> getInterRegistoSaidaList() {
        return interRegistoSaidaList;
    }

    public void setInterRegistoSaidaList(List<InterRegistoSaida> interRegistoSaidaList) {
        this.interRegistoSaidaList = interRegistoSaidaList;
    }

    @XmlTransient
    public List<InterTituloAlta> getInterTituloAltaList() {
        return interTituloAltaList;
    }

    public void setInterTituloAltaList(List<InterTituloAlta> interTituloAltaList) {
        this.interTituloAltaList = interTituloAltaList;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoComponenteSanguineo> getDiagRespostaRequisicaoComponenteSanguineoList() {
        return diagRespostaRequisicaoComponenteSanguineoList;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineoList(List<DiagRespostaRequisicaoComponenteSanguineo> diagRespostaRequisicaoComponenteSanguineoList) {
        this.diagRespostaRequisicaoComponenteSanguineoList = diagRespostaRequisicaoComponenteSanguineoList;
    }

    @XmlTransient
    public List<InterInscricaoInternamento> getInterInscricaoInternamentoList() {
        return interInscricaoInternamentoList;
    }

    public void setInterInscricaoInternamentoList(List<InterInscricaoInternamento> interInscricaoInternamentoList) {
        this.interInscricaoInternamentoList = interInscricaoInternamentoList;
    }

    @XmlTransient
    public List<FarmDoacao> getFarmDoacaoList() {
        return farmDoacaoList;
    }

    public void setFarmDoacaoList(List<FarmDoacao> farmDoacaoList) {
        this.farmDoacaoList = farmDoacaoList;
    }

    @XmlTransient
    public List<InterTratamentoMalnutricao> getInterTratamentoMalnutricaoList() {
        return interTratamentoMalnutricaoList;
    }

    public void setInterTratamentoMalnutricaoList(List<InterTratamentoMalnutricao> interTratamentoMalnutricaoList) {
        this.interTratamentoMalnutricaoList = interTratamentoMalnutricaoList;
    }

    @XmlTransient
    public List<SupiSupervisorHasEscala> getSupiSupervisorHasEscalaList() {
        return supiSupervisorHasEscalaList;
    }

    public void setSupiSupervisorHasEscalaList(List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList) {
        this.supiSupervisorHasEscalaList = supiSupervisorHasEscalaList;
    }

    @XmlTransient
    public List<FinPagamento> getFinPagamentoList() {
        return finPagamentoList;
    }

    public void setFinPagamentoList(List<FinPagamento> finPagamentoList) {
        this.finPagamentoList = finPagamentoList;
    }

    @XmlTransient
    public List<InterVacinacao> getInterVacinacaoList() {
        return interVacinacaoList;
    }

    public void setInterVacinacaoList(List<InterVacinacao> interVacinacaoList) {
        this.interVacinacaoList = interVacinacaoList;
    }

    @XmlTransient
    public List<AmbConsultorioAtendimento> getAmbConsultorioAtendimentoList() {
        return ambConsultorioAtendimentoList;
    }

    public void setAmbConsultorioAtendimentoList(List<AmbConsultorioAtendimento> ambConsultorioAtendimentoList) {
        this.ambConsultorioAtendimentoList = ambConsultorioAtendimentoList;
    }

    @XmlTransient
    public List<InterRegistoInternamento> getInterRegistoInternamentoList() {
        return interRegistoInternamentoList;
    }

    public void setInterRegistoInternamentoList(List<InterRegistoInternamento> interRegistoInternamentoList) {
        this.interRegistoInternamentoList = interRegistoInternamentoList;
    }

    @XmlTransient
    public List<SupiAvaliacaoDesempenho> getSupiAvaliacaoDesempenhoList() {
        return supiAvaliacaoDesempenhoList;
    }

    public void setSupiAvaliacaoDesempenhoList(List<SupiAvaliacaoDesempenho> supiAvaliacaoDesempenhoList) {
        this.supiAvaliacaoDesempenhoList = supiAvaliacaoDesempenhoList;
    }

    @XmlTransient
    public List<DiagTransfusao> getDiagTransfusaoList() {
        return diagTransfusaoList;
    }

    public void setDiagTransfusaoList(List<DiagTransfusao> diagTransfusaoList) {
        this.diagTransfusaoList = diagTransfusaoList;
    }

    @XmlTransient
    public List<InterAnotacaoEnfermagem> getInterAnotacaoEnfermagemList() {
        return interAnotacaoEnfermagemList;
    }

    public void setInterAnotacaoEnfermagemList(List<InterAnotacaoEnfermagem> interAnotacaoEnfermagemList) {
        this.interAnotacaoEnfermagemList = interAnotacaoEnfermagemList;
    }

    @XmlTransient
    public List<FarmPedido> getFarmPedidoList() {
        return farmPedidoList;
    }

    public void setFarmPedidoList(List<FarmPedido> farmPedidoList) {
        this.farmPedidoList = farmPedidoList;
    }

    @XmlTransient
    public List<FarmPedido> getFarmPedidoList1() {
        return farmPedidoList1;
    }

    public void setFarmPedidoList1(List<FarmPedido> farmPedidoList1) {
        this.farmPedidoList1 = farmPedidoList1;
    }

    @XmlTransient
    public List<AmbDiagnosticoHipotese> getAmbDiagnosticoHipoteseList() {
        return ambDiagnosticoHipoteseList;
    }

    public void setAmbDiagnosticoHipoteseList(List<AmbDiagnosticoHipotese> ambDiagnosticoHipoteseList) {
        this.ambDiagnosticoHipoteseList = ambDiagnosticoHipoteseList;
    }

    public SegConta getSegConta() {
        return segConta;
    }

    public void setSegConta(SegConta segConta) {
        this.segConta = segConta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFuncionario != null ? pkIdFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhFuncionario)) {
            return false;
        }
        RhFuncionario other = (RhFuncionario) object;
        if ((this.pkIdFuncionario == null && other.pkIdFuncionario != null) || (this.pkIdFuncionario != null && !this.pkIdFuncionario.equals(other.pkIdFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhFuncionario[ pkIdFuncionario=" + pkIdFuncionario + " ]";
    }
    
}
