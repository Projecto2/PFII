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
@Table(name = "adms_solicitacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsSolicitacao.findAll", query = "SELECT a FROM AdmsSolicitacao a"),
    @NamedQuery(name = "AdmsSolicitacao.findByPkIdSolicitacao", query = "SELECT a FROM AdmsSolicitacao a WHERE a.pkIdSolicitacao = :pkIdSolicitacao"),
    @NamedQuery(name = "AdmsSolicitacao.findByData", query = "SELECT a FROM AdmsSolicitacao a WHERE a.data = :data"),
    @NamedQuery(name = "AdmsSolicitacao.findByObservacao", query = "SELECT a FROM AdmsSolicitacao a WHERE a.observacao = :observacao")})
public class AdmsSolicitacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_solicitacao", nullable = false)
    private Long pkIdSolicitacao;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Size(max = 500)
    @Column(name = "observacao", length = 500)
    private String observacao;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente", nullable = false)
    @ManyToOne(optional = false)
    private AdmsPaciente fkIdPaciente;
    @JoinColumn(name = "fk_id_responsavel_paciente", referencedColumnName = "pk_id_responsavel_paciente")
    @ManyToOne
    private AdmsResponsavelPaciente fkIdResponsavelPaciente;
    @JoinColumn(name = "fk_id_servico_efetuado", referencedColumnName = "pk_id_servico_efetuado")
    @ManyToOne
    private AdmsServicoEfetuado fkIdServicoEfetuado;
    @JoinColumn(name = "fk_id_subprocesso", referencedColumnName = "pk_id_subprocesso")
    @ManyToOne
    private AdmsSubprocesso fkIdSubprocesso;
    @JoinColumn(name = "fk_id_centro", referencedColumnName = "pk_id_centro")
    @ManyToOne
    private GrlCentroHospitalar fkIdCentro;
    @JoinColumn(name = "fk_id_funcionario_solicitante", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioSolicitante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdSolicitacao")
    private List<AdmsServicoSolicitado> admsServicoSolicitadoList;

    public AdmsSolicitacao() {
    }

    public AdmsSolicitacao(Long pkIdSolicitacao) {
        this.pkIdSolicitacao = pkIdSolicitacao;
    }

    public Long getPkIdSolicitacao() {
        return pkIdSolicitacao;
    }

    public void setPkIdSolicitacao(Long pkIdSolicitacao) {
        this.pkIdSolicitacao = pkIdSolicitacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public AdmsResponsavelPaciente getFkIdResponsavelPaciente() {
        return fkIdResponsavelPaciente;
    }

    public void setFkIdResponsavelPaciente(AdmsResponsavelPaciente fkIdResponsavelPaciente) {
        this.fkIdResponsavelPaciente = fkIdResponsavelPaciente;
    }

    public AdmsServicoEfetuado getFkIdServicoEfetuado() {
        return fkIdServicoEfetuado;
    }

    public void setFkIdServicoEfetuado(AdmsServicoEfetuado fkIdServicoEfetuado) {
        this.fkIdServicoEfetuado = fkIdServicoEfetuado;
    }

    public AdmsSubprocesso getFkIdSubprocesso() {
        return fkIdSubprocesso;
    }

    public void setFkIdSubprocesso(AdmsSubprocesso fkIdSubprocesso) {
        this.fkIdSubprocesso = fkIdSubprocesso;
    }

    public GrlCentroHospitalar getFkIdCentro() {
        return fkIdCentro;
    }

    public void setFkIdCentro(GrlCentroHospitalar fkIdCentro) {
        this.fkIdCentro = fkIdCentro;
    }

    public RhFuncionario getFkIdFuncionarioSolicitante() {
        return fkIdFuncionarioSolicitante;
    }

    public void setFkIdFuncionarioSolicitante(RhFuncionario fkIdFuncionarioSolicitante) {
        this.fkIdFuncionarioSolicitante = fkIdFuncionarioSolicitante;
    }

    @XmlTransient
    public List<AdmsServicoSolicitado> getAdmsServicoSolicitadoList() {
        return admsServicoSolicitadoList;
    }

    public void setAdmsServicoSolicitadoList(List<AdmsServicoSolicitado> admsServicoSolicitadoList) {
        this.admsServicoSolicitadoList = admsServicoSolicitadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSolicitacao != null ? pkIdSolicitacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsSolicitacao)) {
            return false;
        }
        AdmsSolicitacao other = (AdmsSolicitacao) object;
        if ((this.pkIdSolicitacao == null && other.pkIdSolicitacao != null) || (this.pkIdSolicitacao != null && !this.pkIdSolicitacao.equals(other.pkIdSolicitacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsSolicitacao[ pkIdSolicitacao=" + pkIdSolicitacao + " ]";
    }
    
}
