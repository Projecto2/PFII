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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_consulta", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsulta.findAll", query = "SELECT a FROM AmbConsulta a"),
    @NamedQuery(name = "AmbConsulta.findByPkIdConsulta", query = "SELECT a FROM AmbConsulta a WHERE a.pkIdConsulta = :pkIdConsulta"),
    @NamedQuery(name = "AmbConsulta.findByObservacoes", query = "SELECT a FROM AmbConsulta a WHERE a.observacoes = :observacoes"),
    @NamedQuery(name = "AmbConsulta.findByDataHoraConsulta", query = "SELECT a FROM AmbConsulta a WHERE a.dataHoraConsulta = :dataHoraConsulta"),
    @NamedQuery(name = "AmbConsulta.findBySintoma", query = "SELECT a FROM AmbConsulta a WHERE a.sintoma = :sintoma"),
    @NamedQuery(name = "AmbConsulta.findByHistoriaDoencaActual", query = "SELECT a FROM AmbConsulta a WHERE a.historiaDoencaActual = :historiaDoencaActual"),
    @NamedQuery(name = "AmbConsulta.findByLocalizacao", query = "SELECT a FROM AmbConsulta a WHERE a.localizacao = :localizacao"),
    @NamedQuery(name = "AmbConsulta.findByOutrosDados", query = "SELECT a FROM AmbConsulta a WHERE a.outrosDados = :outrosDados"),
    @NamedQuery(name = "AmbConsulta.findByMedicacaoAnterior", query = "SELECT a FROM AmbConsulta a WHERE a.medicacaoAnterior = :medicacaoAnterior")})
public class AmbConsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta", nullable = false)
    private Long pkIdConsulta;
    @Size(max = 500)
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    @Column(name = "data_hora_consulta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraConsulta;
    @Size(max = 500)
    @Column(name = "sintoma", length = 500)
    private String sintoma;
    @Size(max = 255)
    @Column(name = "historia_doenca_actual", length = 255)
    private String historiaDoencaActual;
    @Size(max = 45)
    @Column(name = "localizacao", length = 45)
    private String localizacao;
    @Size(max = 45)
    @Column(name = "outros_dados", length = 45)
    private String outrosDados;
    @Size(max = 512)
    @Column(name = "medicacao_anterior", length = 512)
    private String medicacaoAnterior;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasEspessura> ambConsultaHasEspessuraList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbPrescricaoMedica> ambPrescricaoMedicaList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasTurgorTecido> ambConsultaHasTurgorTecidoList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasTurgorPele> ambConsultaHasTurgorPeleList;
    @OneToMany(mappedBy = "fkConsulta")
    private List<TbTriagem> tbTriagemList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasImpressoesGerais> ambConsultaHasImpressoesGeraisList;
    @JoinColumn(name = "fk_id_classificacao_dor", referencedColumnName = "pk_id_classificacao_dor")
    @ManyToOne
    private AmbClassificacaoDor fkIdClassificacaoDor;
    @JoinColumn(name = "fk_id_tipo_solicitacao_servico", referencedColumnName = "pk_id_tipo_solicitacao")
    @ManyToOne
    private AdmsTipoSolicitacaoServico fkIdTipoSolicitacaoServico;
    @JoinColumn(name = "fk_id_aderencia", referencedColumnName = "pk_id_aderencia")
    @ManyToOne
    private AmbAderencia fkIdAderencia;
    @JoinColumn(name = "fk_id_confirmacao", referencedColumnName = "pk_id_confirmacao")
    @ManyToOne
    private AmbConfirmacao fkIdConfirmacao;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_consultorio_atendimento", referencedColumnName = "pk_id_consultorio_atendimento")
    @ManyToOne
    private AmbConsultorioAtendimento fkIdConsultorioAtendimento;
    @JoinColumn(name = "fk_id_estado", referencedColumnName = "pk_id_estado")
    @ManyToOne
    private AmbEstado fkIdEstado;
    @JoinColumn(name = "fk_id_estado_hidratacao", referencedColumnName = "pk_id_estado_hidratacao")
    @ManyToOne
    private AmbEstadoHidratacao fkIdEstadoHidratacao;
    @JoinColumn(name = "fk_id_observacoes_medicas", referencedColumnName = "pk_id_observacoes_medicas")
    @ManyToOne
    private AmbObservacoesMedicas fkIdObservacoesMedicas;
    @JoinColumn(name = "fk_id_centro", referencedColumnName = "pk_id_centro")
    @ManyToOne
    private GrlCentroHospitalar fkIdCentro;
    @JoinColumn(name = "fk_id_especialidade", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidade;
    @JoinColumn(name = "fk_id_tamanho", referencedColumnName = "pk_id_tamanho")
    @ManyToOne
    private GrlTamanho fkIdTamanho;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasColoracao> ambConsultaHasColoracaoList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasTextura> ambConsultaHasTexturaList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbConsultaHasCor> ambConsultaHasCorList;
    @OneToMany(mappedBy = "fkIdConsulta")
    private List<AmbDiagnosticoHipotese> ambDiagnosticoHipoteseList;

    public AmbConsulta() {
    }

    public AmbConsulta(Long pkIdConsulta) {
        this.pkIdConsulta = pkIdConsulta;
    }

    public Long getPkIdConsulta() {
        return pkIdConsulta;
    }

    public void setPkIdConsulta(Long pkIdConsulta) {
        this.pkIdConsulta = pkIdConsulta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getDataHoraConsulta() {
        return dataHoraConsulta;
    }

    public void setDataHoraConsulta(Date dataHoraConsulta) {
        this.dataHoraConsulta = dataHoraConsulta;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getHistoriaDoencaActual() {
        return historiaDoencaActual;
    }

    public void setHistoriaDoencaActual(String historiaDoencaActual) {
        this.historiaDoencaActual = historiaDoencaActual;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getOutrosDados() {
        return outrosDados;
    }

    public void setOutrosDados(String outrosDados) {
        this.outrosDados = outrosDados;
    }

    public String getMedicacaoAnterior() {
        return medicacaoAnterior;
    }

    public void setMedicacaoAnterior(String medicacaoAnterior) {
        this.medicacaoAnterior = medicacaoAnterior;
    }

    @XmlTransient
    public List<AmbConsultaHasEspessura> getAmbConsultaHasEspessuraList() {
        return ambConsultaHasEspessuraList;
    }

    public void setAmbConsultaHasEspessuraList(List<AmbConsultaHasEspessura> ambConsultaHasEspessuraList) {
        this.ambConsultaHasEspessuraList = ambConsultaHasEspessuraList;
    }

    @XmlTransient
    public List<AmbPrescricaoMedica> getAmbPrescricaoMedicaList() {
        return ambPrescricaoMedicaList;
    }

    public void setAmbPrescricaoMedicaList(List<AmbPrescricaoMedica> ambPrescricaoMedicaList) {
        this.ambPrescricaoMedicaList = ambPrescricaoMedicaList;
    }

    @XmlTransient
    public List<AmbConsultaHasTurgorTecido> getAmbConsultaHasTurgorTecidoList() {
        return ambConsultaHasTurgorTecidoList;
    }

    public void setAmbConsultaHasTurgorTecidoList(List<AmbConsultaHasTurgorTecido> ambConsultaHasTurgorTecidoList) {
        this.ambConsultaHasTurgorTecidoList = ambConsultaHasTurgorTecidoList;
    }

    @XmlTransient
    public List<AmbConsultaHasTurgorPele> getAmbConsultaHasTurgorPeleList() {
        return ambConsultaHasTurgorPeleList;
    }

    public void setAmbConsultaHasTurgorPeleList(List<AmbConsultaHasTurgorPele> ambConsultaHasTurgorPeleList) {
        this.ambConsultaHasTurgorPeleList = ambConsultaHasTurgorPeleList;
    }

    @XmlTransient
    public List<TbTriagem> getTbTriagemList() {
        return tbTriagemList;
    }

    public void setTbTriagemList(List<TbTriagem> tbTriagemList) {
        this.tbTriagemList = tbTriagemList;
    }

    @XmlTransient
    public List<AmbConsultaHasImpressoesGerais> getAmbConsultaHasImpressoesGeraisList() {
        return ambConsultaHasImpressoesGeraisList;
    }

    public void setAmbConsultaHasImpressoesGeraisList(List<AmbConsultaHasImpressoesGerais> ambConsultaHasImpressoesGeraisList) {
        this.ambConsultaHasImpressoesGeraisList = ambConsultaHasImpressoesGeraisList;
    }

    public AmbClassificacaoDor getFkIdClassificacaoDor() {
        return fkIdClassificacaoDor;
    }

    public void setFkIdClassificacaoDor(AmbClassificacaoDor fkIdClassificacaoDor) {
        this.fkIdClassificacaoDor = fkIdClassificacaoDor;
    }

    public AdmsTipoSolicitacaoServico getFkIdTipoSolicitacaoServico() {
        return fkIdTipoSolicitacaoServico;
    }

    public void setFkIdTipoSolicitacaoServico(AdmsTipoSolicitacaoServico fkIdTipoSolicitacaoServico) {
        this.fkIdTipoSolicitacaoServico = fkIdTipoSolicitacaoServico;
    }

    public AmbAderencia getFkIdAderencia() {
        return fkIdAderencia;
    }

    public void setFkIdAderencia(AmbAderencia fkIdAderencia) {
        this.fkIdAderencia = fkIdAderencia;
    }

    public AmbConfirmacao getFkIdConfirmacao() {
        return fkIdConfirmacao;
    }

    public void setFkIdConfirmacao(AmbConfirmacao fkIdConfirmacao) {
        this.fkIdConfirmacao = fkIdConfirmacao;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public AmbConsultorioAtendimento getFkIdConsultorioAtendimento() {
        return fkIdConsultorioAtendimento;
    }

    public void setFkIdConsultorioAtendimento(AmbConsultorioAtendimento fkIdConsultorioAtendimento) {
        this.fkIdConsultorioAtendimento = fkIdConsultorioAtendimento;
    }

    public AmbEstado getFkIdEstado() {
        return fkIdEstado;
    }

    public void setFkIdEstado(AmbEstado fkIdEstado) {
        this.fkIdEstado = fkIdEstado;
    }

    public AmbEstadoHidratacao getFkIdEstadoHidratacao() {
        return fkIdEstadoHidratacao;
    }

    public void setFkIdEstadoHidratacao(AmbEstadoHidratacao fkIdEstadoHidratacao) {
        this.fkIdEstadoHidratacao = fkIdEstadoHidratacao;
    }

    public AmbObservacoesMedicas getFkIdObservacoesMedicas() {
        return fkIdObservacoesMedicas;
    }

    public void setFkIdObservacoesMedicas(AmbObservacoesMedicas fkIdObservacoesMedicas) {
        this.fkIdObservacoesMedicas = fkIdObservacoesMedicas;
    }

    public GrlCentroHospitalar getFkIdCentro() {
        return fkIdCentro;
    }

    public void setFkIdCentro(GrlCentroHospitalar fkIdCentro) {
        this.fkIdCentro = fkIdCentro;
    }

    public GrlEspecialidade getFkIdEspecialidade() {
        return fkIdEspecialidade;
    }

    public void setFkIdEspecialidade(GrlEspecialidade fkIdEspecialidade) {
        this.fkIdEspecialidade = fkIdEspecialidade;
    }

    public GrlTamanho getFkIdTamanho() {
        return fkIdTamanho;
    }

    public void setFkIdTamanho(GrlTamanho fkIdTamanho) {
        this.fkIdTamanho = fkIdTamanho;
    }

    @XmlTransient
    public List<AmbConsultaHasColoracao> getAmbConsultaHasColoracaoList() {
        return ambConsultaHasColoracaoList;
    }

    public void setAmbConsultaHasColoracaoList(List<AmbConsultaHasColoracao> ambConsultaHasColoracaoList) {
        this.ambConsultaHasColoracaoList = ambConsultaHasColoracaoList;
    }

    @XmlTransient
    public List<AmbConsultaHasTextura> getAmbConsultaHasTexturaList() {
        return ambConsultaHasTexturaList;
    }

    public void setAmbConsultaHasTexturaList(List<AmbConsultaHasTextura> ambConsultaHasTexturaList) {
        this.ambConsultaHasTexturaList = ambConsultaHasTexturaList;
    }

    @XmlTransient
    public List<AmbConsultaHasCor> getAmbConsultaHasCorList() {
        return ambConsultaHasCorList;
    }

    public void setAmbConsultaHasCorList(List<AmbConsultaHasCor> ambConsultaHasCorList) {
        this.ambConsultaHasCorList = ambConsultaHasCorList;
    }

    @XmlTransient
    public List<AmbDiagnosticoHipotese> getAmbDiagnosticoHipoteseList() {
        return ambDiagnosticoHipoteseList;
    }

    public void setAmbDiagnosticoHipoteseList(List<AmbDiagnosticoHipotese> ambDiagnosticoHipoteseList) {
        this.ambDiagnosticoHipoteseList = ambDiagnosticoHipoteseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsulta != null ? pkIdConsulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsulta)) {
            return false;
        }
        AmbConsulta other = (AmbConsulta) object;
        if ((this.pkIdConsulta == null && other.pkIdConsulta != null) || (this.pkIdConsulta != null && !this.pkIdConsulta.equals(other.pkIdConsulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsulta[ pkIdConsulta=" + pkIdConsulta + " ]";
    }
    
}
