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
@Table(name = "amb_triagem", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbTriagem.findAll", query = "SELECT a FROM AmbTriagem a"),
    @NamedQuery(name = "AmbTriagem.findByPkIdTriagem", query = "SELECT a FROM AmbTriagem a WHERE a.pkIdTriagem = :pkIdTriagem"),
    @NamedQuery(name = "AmbTriagem.findByTemperatura", query = "SELECT a FROM AmbTriagem a WHERE a.temperatura = :temperatura"),
    @NamedQuery(name = "AmbTriagem.findByPeso", query = "SELECT a FROM AmbTriagem a WHERE a.peso = :peso"),
    @NamedQuery(name = "AmbTriagem.findByAltura", query = "SELECT a FROM AmbTriagem a WHERE a.altura = :altura"),
    @NamedQuery(name = "AmbTriagem.findByGlicemia", query = "SELECT a FROM AmbTriagem a WHERE a.glicemia = :glicemia"),
    @NamedQuery(name = "AmbTriagem.findByDataHoraTriagem", query = "SELECT a FROM AmbTriagem a WHERE a.dataHoraTriagem = :dataHoraTriagem"),
    @NamedQuery(name = "AmbTriagem.findByObservacoes", query = "SELECT a FROM AmbTriagem a WHERE a.observacoes = :observacoes"),
    @NamedQuery(name = "AmbTriagem.findByLocalDaDor", query = "SELECT a FROM AmbTriagem a WHERE a.localDaDor = :localDaDor"),
    @NamedQuery(name = "AmbTriagem.findByFrequenciaRespiratoria", query = "SELECT a FROM AmbTriagem a WHERE a.frequenciaRespiratoria = :frequenciaRespiratoria"),
    @NamedQuery(name = "AmbTriagem.findByFrequenciaCardiaca", query = "SELECT a FROM AmbTriagem a WHERE a.frequenciaCardiaca = :frequenciaCardiaca"),
    @NamedQuery(name = "AmbTriagem.findByTensaoArterialMaxima", query = "SELECT a FROM AmbTriagem a WHERE a.tensaoArterialMaxima = :tensaoArterialMaxima"),
    @NamedQuery(name = "AmbTriagem.findByTensaoArterialMinima", query = "SELECT a FROM AmbTriagem a WHERE a.tensaoArterialMinima = :tensaoArterialMinima"),
    @NamedQuery(name = "AmbTriagem.findByPulso", query = "SELECT a FROM AmbTriagem a WHERE a.pulso = :pulso")})
public class AmbTriagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_triagem", nullable = false)
    private Long pkIdTriagem;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "temperatura", precision = 17, scale = 17)
    private Double temperatura;
    @Column(name = "peso", precision = 17, scale = 17)
    private Double peso;
    @Column(name = "altura", precision = 17, scale = 17)
    private Double altura;
    @Size(max = 45)
    @Column(name = "glicemia", length = 45)
    private String glicemia;
    @Column(name = "data_hora_triagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraTriagem;
    @Size(max = 500)
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    @Size(max = 70)
    @Column(name = "local_da_dor", length = 70)
    private String localDaDor;
    @Size(max = 7)
    @Column(name = "frequencia_respiratoria", length = 7)
    private String frequenciaRespiratoria;
    @Size(max = 7)
    @Column(name = "frequencia_cardiaca", length = 7)
    private String frequenciaCardiaca;
    @Size(max = 8)
    @Column(name = "tensao_arterial_maxima", length = 8)
    private String tensaoArterialMaxima;
    @Size(max = 8)
    @Column(name = "tensao_arterial_minima", length = 8)
    private String tensaoArterialMinima;
    @Size(max = 7)
    @Column(name = "pulso", length = 7)
    private String pulso;
    @OneToMany(mappedBy = "fkIdTriagem")
    private List<AmbTriagemHasSinal> ambTriagemHasSinalList;
    @JoinColumn(name = "fk_id_agendamento", referencedColumnName = "pk_id_agendamento")
    @ManyToOne
    private AdmsAgendamento fkIdAgendamento;
    @JoinColumn(name = "fk_id_classificacao_servico_solicitado", referencedColumnName = "pk_id_classificacao_servico_solicitado")
    @ManyToOne
    private AdmsClassificacaoServicoSolicitado fkIdClassificacaoServicoSolicitado;
    @JoinColumn(name = "fk_id_classificacao_dor", referencedColumnName = "pk_id_classificacao_dor")
    @ManyToOne
    private AmbClassificacaoDor fkIdClassificacaoDor;
    @JoinColumn(name = "fk_id_estado", referencedColumnName = "pk_id_estado")
    @ManyToOne
    private AmbEstado fkIdEstado;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @OneToMany(mappedBy = "fkIdTriagem")
    private List<AmbConsultorioAtendimento> ambConsultorioAtendimentoList;

    public AmbTriagem() {
    }

    public AmbTriagem(Long pkIdTriagem) {
        this.pkIdTriagem = pkIdTriagem;
    }

    public Long getPkIdTriagem() {
        return pkIdTriagem;
    }

    public void setPkIdTriagem(Long pkIdTriagem) {
        this.pkIdTriagem = pkIdTriagem;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(String glicemia) {
        this.glicemia = glicemia;
    }

    public Date getDataHoraTriagem() {
        return dataHoraTriagem;
    }

    public void setDataHoraTriagem(Date dataHoraTriagem) {
        this.dataHoraTriagem = dataHoraTriagem;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getLocalDaDor() {
        return localDaDor;
    }

    public void setLocalDaDor(String localDaDor) {
        this.localDaDor = localDaDor;
    }

    public String getFrequenciaRespiratoria() {
        return frequenciaRespiratoria;
    }

    public void setFrequenciaRespiratoria(String frequenciaRespiratoria) {
        this.frequenciaRespiratoria = frequenciaRespiratoria;
    }

    public String getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(String frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public String getTensaoArterialMaxima() {
        return tensaoArterialMaxima;
    }

    public void setTensaoArterialMaxima(String tensaoArterialMaxima) {
        this.tensaoArterialMaxima = tensaoArterialMaxima;
    }

    public String getTensaoArterialMinima() {
        return tensaoArterialMinima;
    }

    public void setTensaoArterialMinima(String tensaoArterialMinima) {
        this.tensaoArterialMinima = tensaoArterialMinima;
    }

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

    @XmlTransient
    public List<AmbTriagemHasSinal> getAmbTriagemHasSinalList() {
        return ambTriagemHasSinalList;
    }

    public void setAmbTriagemHasSinalList(List<AmbTriagemHasSinal> ambTriagemHasSinalList) {
        this.ambTriagemHasSinalList = ambTriagemHasSinalList;
    }

    public AdmsAgendamento getFkIdAgendamento() {
        return fkIdAgendamento;
    }

    public void setFkIdAgendamento(AdmsAgendamento fkIdAgendamento) {
        this.fkIdAgendamento = fkIdAgendamento;
    }

    public AdmsClassificacaoServicoSolicitado getFkIdClassificacaoServicoSolicitado() {
        return fkIdClassificacaoServicoSolicitado;
    }

    public void setFkIdClassificacaoServicoSolicitado(AdmsClassificacaoServicoSolicitado fkIdClassificacaoServicoSolicitado) {
        this.fkIdClassificacaoServicoSolicitado = fkIdClassificacaoServicoSolicitado;
    }

    public AmbClassificacaoDor getFkIdClassificacaoDor() {
        return fkIdClassificacaoDor;
    }

    public void setFkIdClassificacaoDor(AmbClassificacaoDor fkIdClassificacaoDor) {
        this.fkIdClassificacaoDor = fkIdClassificacaoDor;
    }

    public AmbEstado getFkIdEstado() {
        return fkIdEstado;
    }

    public void setFkIdEstado(AmbEstado fkIdEstado) {
        this.fkIdEstado = fkIdEstado;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @XmlTransient
    public List<AmbConsultorioAtendimento> getAmbConsultorioAtendimentoList() {
        return ambConsultorioAtendimentoList;
    }

    public void setAmbConsultorioAtendimentoList(List<AmbConsultorioAtendimento> ambConsultorioAtendimentoList) {
        this.ambConsultorioAtendimentoList = ambConsultorioAtendimentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTriagem != null ? pkIdTriagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbTriagem)) {
            return false;
        }
        AmbTriagem other = (AmbTriagem) object;
        if ((this.pkIdTriagem == null && other.pkIdTriagem != null) || (this.pkIdTriagem != null && !this.pkIdTriagem.equals(other.pkIdTriagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbTriagem[ pkIdTriagem=" + pkIdTriagem + " ]";
    }
    
}
