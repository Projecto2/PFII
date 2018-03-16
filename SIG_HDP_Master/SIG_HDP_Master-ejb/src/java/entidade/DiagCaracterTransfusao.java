/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_caracter_transfusao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagCaracterTransfusao.findAll", query = "SELECT d FROM DiagCaracterTransfusao d"),
    @NamedQuery(name = "DiagCaracterTransfusao.findByPkIdCaracterTransfusao", query = "SELECT d FROM DiagCaracterTransfusao d WHERE d.pkIdCaracterTransfusao = :pkIdCaracterTransfusao"),
    @NamedQuery(name = "DiagCaracterTransfusao.findByDescricao", query = "SELECT d FROM DiagCaracterTransfusao d WHERE d.descricao = :descricao")})
public class DiagCaracterTransfusao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_caracter_transfusao", nullable = false)
    private Integer pkIdCaracterTransfusao;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdCaracterTransfusao")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList;

    public DiagCaracterTransfusao() {
    }

    public DiagCaracterTransfusao(Integer pkIdCaracterTransfusao) {
        this.pkIdCaracterTransfusao = pkIdCaracterTransfusao;
    }

    public Integer getPkIdCaracterTransfusao() {
        return pkIdCaracterTransfusao;
    }

    public void setPkIdCaracterTransfusao(Integer pkIdCaracterTransfusao) {
        this.pkIdCaracterTransfusao = pkIdCaracterTransfusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList() {
        return diagRequisicaoComponenteSanguineoList;
    }

    public void setDiagRequisicaoComponenteSanguineoList(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList) {
        this.diagRequisicaoComponenteSanguineoList = diagRequisicaoComponenteSanguineoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCaracterTransfusao != null ? pkIdCaracterTransfusao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagCaracterTransfusao)) {
            return false;
        }
        DiagCaracterTransfusao other = (DiagCaracterTransfusao) object;
        if ((this.pkIdCaracterTransfusao == null && other.pkIdCaracterTransfusao != null) || (this.pkIdCaracterTransfusao != null && !this.pkIdCaracterTransfusao.equals(other.pkIdCaracterTransfusao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagCaracterTransfusao[ pkIdCaracterTransfusao=" + pkIdCaracterTransfusao + " ]";
    }
    
}
