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
@Table(name = "supi_seccao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiSeccao.findAll", query = "SELECT s FROM SupiSeccao s"),
    @NamedQuery(name = "SupiSeccao.findByPkIdSeccao", query = "SELECT s FROM SupiSeccao s WHERE s.pkIdSeccao = :pkIdSeccao"),
    @NamedQuery(name = "SupiSeccao.findByDescricao", query = "SELECT s FROM SupiSeccao s WHERE s.descricao = :descricao")})
public class SupiSeccao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_seccao", nullable = false)
    private Integer pkIdSeccao;
    @Size(max = 60)
    @Column(name = "descricao", length = 60)
    private String descricao;
    @OneToMany(mappedBy = "fkIdSeccao")
    private List<SupiEscala> supiEscalaList;

    public SupiSeccao() {
    }

    public SupiSeccao(Integer pkIdSeccao) {
        this.pkIdSeccao = pkIdSeccao;
    }

    public Integer getPkIdSeccao() {
        return pkIdSeccao;
    }

    public void setPkIdSeccao(Integer pkIdSeccao) {
        this.pkIdSeccao = pkIdSeccao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiEscala> getSupiEscalaList() {
        return supiEscalaList;
    }

    public void setSupiEscalaList(List<SupiEscala> supiEscalaList) {
        this.supiEscalaList = supiEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSeccao != null ? pkIdSeccao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiSeccao)) {
            return false;
        }
        SupiSeccao other = (SupiSeccao) object;
        if ((this.pkIdSeccao == null && other.pkIdSeccao != null) || (this.pkIdSeccao != null && !this.pkIdSeccao.equals(other.pkIdSeccao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiSeccao[ pkIdSeccao=" + pkIdSeccao + " ]";
    }
    
}
