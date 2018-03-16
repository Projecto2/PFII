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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_reagente", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero_lote"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagReagente.findAll", query = "SELECT d FROM DiagReagente d"),
    @NamedQuery(name = "DiagReagente.findByDataValidade", query = "SELECT d FROM DiagReagente d WHERE d.dataValidade = :dataValidade"),
    @NamedQuery(name = "DiagReagente.findByNumeroLote", query = "SELECT d FROM DiagReagente d WHERE d.numeroLote = :numeroLote"),
    @NamedQuery(name = "DiagReagente.findByPkIdReagente", query = "SELECT d FROM DiagReagente d WHERE d.pkIdReagente = :pkIdReagente"),
    @NamedQuery(name = "DiagReagente.findByQuantidade", query = "SELECT d FROM DiagReagente d WHERE d.quantidade = :quantidade"),
    @NamedQuery(name = "DiagReagente.findByDataCadastro", query = "SELECT d FROM DiagReagente d WHERE d.dataCadastro = :dataCadastro")})
public class DiagReagente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "data_validade")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;
    @Size(max = 45)
    @Column(name = "numero_lote", length = 45)
    private String numeroLote;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_reagente", nullable = false)
    private Integer pkIdReagente;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @OneToMany(mappedBy = "fkIdReagente")
    private List<DiagTipagemMensalHasReagente> diagTipagemMensalHasReagenteList;
    @JoinColumn(name = "fk_id_tipo_reagente", referencedColumnName = "pk_id_tipo_reagente")
    @ManyToOne
    private DiagTipoReagente fkIdTipoReagente;

    public DiagReagente() {
    }

    public DiagReagente(Integer pkIdReagente) {
        this.pkIdReagente = pkIdReagente;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public Integer getPkIdReagente() {
        return pkIdReagente;
    }

    public void setPkIdReagente(Integer pkIdReagente) {
        this.pkIdReagente = pkIdReagente;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @XmlTransient
    public List<DiagTipagemMensalHasReagente> getDiagTipagemMensalHasReagenteList() {
        return diagTipagemMensalHasReagenteList;
    }

    public void setDiagTipagemMensalHasReagenteList(List<DiagTipagemMensalHasReagente> diagTipagemMensalHasReagenteList) {
        this.diagTipagemMensalHasReagenteList = diagTipagemMensalHasReagenteList;
    }

    public DiagTipoReagente getFkIdTipoReagente() {
        return fkIdTipoReagente;
    }

    public void setFkIdTipoReagente(DiagTipoReagente fkIdTipoReagente) {
        this.fkIdTipoReagente = fkIdTipoReagente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdReagente != null ? pkIdReagente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagReagente)) {
            return false;
        }
        DiagReagente other = (DiagReagente) object;
        if ((this.pkIdReagente == null && other.pkIdReagente != null) || (this.pkIdReagente != null && !this.pkIdReagente.equals(other.pkIdReagente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagReagente[ pkIdReagente=" + pkIdReagente + " ]";
    }
    
}
