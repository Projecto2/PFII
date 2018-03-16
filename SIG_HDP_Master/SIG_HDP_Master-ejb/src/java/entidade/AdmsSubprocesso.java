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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_subprocesso", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsSubprocesso.findAll", query = "SELECT a FROM AdmsSubprocesso a"),
    @NamedQuery(name = "AdmsSubprocesso.findByPkIdSubprocesso", query = "SELECT a FROM AdmsSubprocesso a WHERE a.pkIdSubprocesso = :pkIdSubprocesso"),
    @NamedQuery(name = "AdmsSubprocesso.findByNumeroSubprocesso", query = "SELECT a FROM AdmsSubprocesso a WHERE a.numeroSubprocesso = :numeroSubprocesso"),
    @NamedQuery(name = "AdmsSubprocesso.findByDataInicioSubprocesso", query = "SELECT a FROM AdmsSubprocesso a WHERE a.dataInicioSubprocesso = :dataInicioSubprocesso"),
    @NamedQuery(name = "AdmsSubprocesso.findByDataFimSubprocesso", query = "SELECT a FROM AdmsSubprocesso a WHERE a.dataFimSubprocesso = :dataFimSubprocesso")})
public class AdmsSubprocesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_subprocesso", nullable = false)
    private Long pkIdSubprocesso;
    @Column(name = "numero_subprocesso")
    private Integer numeroSubprocesso;
    @Column(name = "data_inicio_subprocesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicioSubprocesso;
    @Column(name = "data_fim_subprocesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFimSubprocesso;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente")
    @ManyToOne
    private AdmsPaciente fkIdPaciente;
    @OneToMany(mappedBy = "fkIdSubprocesso")
    private List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList;
    @OneToMany(mappedBy = "fkIdSubprocesso")
    private List<AdmsSolicitacao> admsSolicitacaoList;
    @OneToMany(mappedBy = "fkIdSubprocesso")
    private List<FinEncargoDevido> finEncargoDevidoList;
    @OneToMany(mappedBy = "fkIdSubprocesso")
    private List<AdmsTipoPagamentoSubprocesso> admsTipoPagamentoSubprocessoList;

    public AdmsSubprocesso() {
    }

    public AdmsSubprocesso(Long pkIdSubprocesso) {
        this.pkIdSubprocesso = pkIdSubprocesso;
    }

    public Long getPkIdSubprocesso() {
        return pkIdSubprocesso;
    }

    public void setPkIdSubprocesso(Long pkIdSubprocesso) {
        this.pkIdSubprocesso = pkIdSubprocesso;
    }

    public Integer getNumeroSubprocesso() {
        return numeroSubprocesso;
    }

    public void setNumeroSubprocesso(Integer numeroSubprocesso) {
        this.numeroSubprocesso = numeroSubprocesso;
    }

    public Date getDataInicioSubprocesso() {
        return dataInicioSubprocesso;
    }

    public void setDataInicioSubprocesso(Date dataInicioSubprocesso) {
        this.dataInicioSubprocesso = dataInicioSubprocesso;
    }

    public Date getDataFimSubprocesso() {
        return dataFimSubprocesso;
    }

    public void setDataFimSubprocesso(Date dataFimSubprocesso) {
        this.dataFimSubprocesso = dataFimSubprocesso;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    @XmlTransient
    public List<FinHistoricoPagamentoCancelados> getFinHistoricoPagamentoCanceladosList() {
        return finHistoricoPagamentoCanceladosList;
    }

    public void setFinHistoricoPagamentoCanceladosList(List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList) {
        this.finHistoricoPagamentoCanceladosList = finHistoricoPagamentoCanceladosList;
    }

    @XmlTransient
    public List<AdmsSolicitacao> getAdmsSolicitacaoList() {
        return admsSolicitacaoList;
    }

    public void setAdmsSolicitacaoList(List<AdmsSolicitacao> admsSolicitacaoList) {
        this.admsSolicitacaoList = admsSolicitacaoList;
    }

    @XmlTransient
    public List<FinEncargoDevido> getFinEncargoDevidoList() {
        return finEncargoDevidoList;
    }

    public void setFinEncargoDevidoList(List<FinEncargoDevido> finEncargoDevidoList) {
        this.finEncargoDevidoList = finEncargoDevidoList;
    }

    @XmlTransient
    public List<AdmsTipoPagamentoSubprocesso> getAdmsTipoPagamentoSubprocessoList() {
        return admsTipoPagamentoSubprocessoList;
    }

    public void setAdmsTipoPagamentoSubprocessoList(List<AdmsTipoPagamentoSubprocesso> admsTipoPagamentoSubprocessoList) {
        this.admsTipoPagamentoSubprocessoList = admsTipoPagamentoSubprocessoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSubprocesso != null ? pkIdSubprocesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsSubprocesso)) {
            return false;
        }
        AdmsSubprocesso other = (AdmsSubprocesso) object;
        if ((this.pkIdSubprocesso == null && other.pkIdSubprocesso != null) || (this.pkIdSubprocesso != null && !this.pkIdSubprocesso.equals(other.pkIdSubprocesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsSubprocesso[ pkIdSubprocesso=" + pkIdSubprocesso + " ]";
    }
    
}
