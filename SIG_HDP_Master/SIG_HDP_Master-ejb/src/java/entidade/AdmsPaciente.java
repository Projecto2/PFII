/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
@Table(name = "adms_paciente", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero_paciente"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsPaciente.findAll", query = "SELECT a FROM AdmsPaciente a"),
    @NamedQuery(name = "AdmsPaciente.findByPkIdPaciente", query = "SELECT a FROM AdmsPaciente a WHERE a.pkIdPaciente = :pkIdPaciente"),
    @NamedQuery(name = "AdmsPaciente.findByNumeroPaciente", query = "SELECT a FROM AdmsPaciente a WHERE a.numeroPaciente = :numeroPaciente")})
public class AdmsPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_paciente", nullable = false)
    private Long pkIdPaciente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "numero_paciente", nullable = false, length = 2147483647)
    private String numeroPaciente;
    @OneToMany(mappedBy = "fkIdPaciente")
    private List<AdmsSubprocesso> admsSubprocessoList;
    @OneToMany(mappedBy = "fkIdPaciente")
    private List<DiagTipagemDoente> diagTipagemDoenteList;
    @JoinColumn(name = "fk_id_endereco_temporario", referencedColumnName = "pk_id_endereco")
    @ManyToOne
    private GrlEndereco fkIdEnderecoTemporario;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa", nullable = false)
    @ManyToOne(optional = false)
    private GrlPessoa fkIdPessoa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPaciente")
    private List<AdmsSolicitacao> admsSolicitacaoList;
    @OneToMany(mappedBy = "fkIdPaciente")
    private List<InterNotificacao> interNotificacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPaciente")
    private List<EmgExame> emgExameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPaciente")
    private List<FarmDispensa> farmDispensaList;
    @OneToMany(mappedBy = "fkIdPaciente")
    private List<AdmsDesconhecido> admsDesconhecidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPaciente")
    private List<InterCamaReservada> interCamaReservadaList;
    @OneToMany(mappedBy = "fkIdPaciente")
    private List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList;

    public AdmsPaciente() {
    }

    public AdmsPaciente(Long pkIdPaciente) {
        this.pkIdPaciente = pkIdPaciente;
    }

    public AdmsPaciente(Long pkIdPaciente, String numeroPaciente) {
        this.pkIdPaciente = pkIdPaciente;
        this.numeroPaciente = numeroPaciente;
    }

    public Long getPkIdPaciente() {
        return pkIdPaciente;
    }

    public void setPkIdPaciente(Long pkIdPaciente) {
        this.pkIdPaciente = pkIdPaciente;
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    @XmlTransient
    public List<AdmsSubprocesso> getAdmsSubprocessoList() {
        return admsSubprocessoList;
    }

    public void setAdmsSubprocessoList(List<AdmsSubprocesso> admsSubprocessoList) {
        this.admsSubprocessoList = admsSubprocessoList;
    }

    @XmlTransient
    public List<DiagTipagemDoente> getDiagTipagemDoenteList() {
        return diagTipagemDoenteList;
    }

    public void setDiagTipagemDoenteList(List<DiagTipagemDoente> diagTipagemDoenteList) {
        this.diagTipagemDoenteList = diagTipagemDoenteList;
    }

    public GrlEndereco getFkIdEnderecoTemporario() {
        return fkIdEnderecoTemporario;
    }

    public void setFkIdEnderecoTemporario(GrlEndereco fkIdEnderecoTemporario) {
        this.fkIdEnderecoTemporario = fkIdEnderecoTemporario;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    @XmlTransient
    public List<AdmsSolicitacao> getAdmsSolicitacaoList() {
        return admsSolicitacaoList;
    }

    public void setAdmsSolicitacaoList(List<AdmsSolicitacao> admsSolicitacaoList) {
        this.admsSolicitacaoList = admsSolicitacaoList;
    }

    @XmlTransient
    public List<InterNotificacao> getInterNotificacaoList() {
        return interNotificacaoList;
    }

    public void setInterNotificacaoList(List<InterNotificacao> interNotificacaoList) {
        this.interNotificacaoList = interNotificacaoList;
    }

    @XmlTransient
    public List<EmgExame> getEmgExameList() {
        return emgExameList;
    }

    public void setEmgExameList(List<EmgExame> emgExameList) {
        this.emgExameList = emgExameList;
    }

    @XmlTransient
    public List<FarmDispensa> getFarmDispensaList() {
        return farmDispensaList;
    }

    public void setFarmDispensaList(List<FarmDispensa> farmDispensaList) {
        this.farmDispensaList = farmDispensaList;
    }

    @XmlTransient
    public List<AdmsDesconhecido> getAdmsDesconhecidoList() {
        return admsDesconhecidoList;
    }

    public void setAdmsDesconhecidoList(List<AdmsDesconhecido> admsDesconhecidoList) {
        this.admsDesconhecidoList = admsDesconhecidoList;
    }

    @XmlTransient
    public List<InterCamaReservada> getInterCamaReservadaList() {
        return interCamaReservadaList;
    }

    public void setInterCamaReservadaList(List<InterCamaReservada> interCamaReservadaList) {
        this.interCamaReservadaList = interCamaReservadaList;
    }

    @XmlTransient
    public List<DiagTesteCompatibilidade> getDiagTesteCompatibilidadeList() {
        return diagTesteCompatibilidadeList;
    }

    public void setDiagTesteCompatibilidadeList(List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList) {
        this.diagTesteCompatibilidadeList = diagTesteCompatibilidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPaciente != null ? pkIdPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsPaciente)) {
            return false;
        }
        AdmsPaciente other = (AdmsPaciente) object;
        if ((this.pkIdPaciente == null && other.pkIdPaciente != null) || (this.pkIdPaciente != null && !this.pkIdPaciente.equals(other.pkIdPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsPaciente[ pkIdPaciente=" + pkIdPaciente + " ]";
    }
    
}
