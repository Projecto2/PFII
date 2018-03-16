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
@Table(name = "diag_solicitacao_tipagem_doente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagSolicitacaoTipagemDoente.findAll", query = "SELECT d FROM DiagSolicitacaoTipagemDoente d"),
    @NamedQuery(name = "DiagSolicitacaoTipagemDoente.findByDataSolicitacao", query = "SELECT d FROM DiagSolicitacaoTipagemDoente d WHERE d.dataSolicitacao = :dataSolicitacao"),
    @NamedQuery(name = "DiagSolicitacaoTipagemDoente.findByPkIdSolicitacaoTipagemDoente", query = "SELECT d FROM DiagSolicitacaoTipagemDoente d WHERE d.pkIdSolicitacaoTipagemDoente = :pkIdSolicitacaoTipagemDoente")})
public class DiagSolicitacaoTipagemDoente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "data_solicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_solicitacao_tipagem_doente", nullable = false)
    private Integer pkIdSolicitacaoTipagemDoente;
    @OneToMany(mappedBy = "fkIdSolicitacaoTipagemDoente")
    private List<DiagTipagemDoente> diagTipagemDoenteList;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento")
    @ManyToOne
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public DiagSolicitacaoTipagemDoente() {
    }

    public DiagSolicitacaoTipagemDoente(Integer pkIdSolicitacaoTipagemDoente) {
        this.pkIdSolicitacaoTipagemDoente = pkIdSolicitacaoTipagemDoente;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Integer getPkIdSolicitacaoTipagemDoente() {
        return pkIdSolicitacaoTipagemDoente;
    }

    public void setPkIdSolicitacaoTipagemDoente(Integer pkIdSolicitacaoTipagemDoente) {
        this.pkIdSolicitacaoTipagemDoente = pkIdSolicitacaoTipagemDoente;
    }

    @XmlTransient
    public List<DiagTipagemDoente> getDiagTipagemDoenteList() {
        return diagTipagemDoenteList;
    }

    public void setDiagTipagemDoenteList(List<DiagTipagemDoente> diagTipagemDoenteList) {
        this.diagTipagemDoenteList = diagTipagemDoenteList;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSolicitacaoTipagemDoente != null ? pkIdSolicitacaoTipagemDoente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagSolicitacaoTipagemDoente)) {
            return false;
        }
        DiagSolicitacaoTipagemDoente other = (DiagSolicitacaoTipagemDoente) object;
        if ((this.pkIdSolicitacaoTipagemDoente == null && other.pkIdSolicitacaoTipagemDoente != null) || (this.pkIdSolicitacaoTipagemDoente != null && !this.pkIdSolicitacaoTipagemDoente.equals(other.pkIdSolicitacaoTipagemDoente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagSolicitacaoTipagemDoente[ pkIdSolicitacaoTipagemDoente=" + pkIdSolicitacaoTipagemDoente + " ]";
    }
    
}
