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
import javax.persistence.Id;
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
@Table(name = "farm_forma_farmaceutica", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmFormaFarmaceutica.findAll", query = "SELECT f FROM FarmFormaFarmaceutica f"),
    @NamedQuery(name = "FarmFormaFarmaceutica.findByPkIdFormaFarmaceutica", query = "SELECT f FROM FarmFormaFarmaceutica f WHERE f.pkIdFormaFarmaceutica = :pkIdFormaFarmaceutica"),
    @NamedQuery(name = "FarmFormaFarmaceutica.findByDescricao", query = "SELECT f FROM FarmFormaFarmaceutica f WHERE f.descricao = :descricao")})
public class FarmFormaFarmaceutica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_forma_farmaceutica", nullable = false)
    private Integer pkIdFormaFarmaceutica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao", nullable = false, length = 250)
    private String descricao;
    @OneToMany(mappedBy = "fkIdFormaFarmaceutica")
    private List<FarmProduto> farmProdutoList;

    public FarmFormaFarmaceutica() {
    }

    public FarmFormaFarmaceutica(Integer pkIdFormaFarmaceutica) {
        this.pkIdFormaFarmaceutica = pkIdFormaFarmaceutica;
    }

    public FarmFormaFarmaceutica(Integer pkIdFormaFarmaceutica, String descricao) {
        this.pkIdFormaFarmaceutica = pkIdFormaFarmaceutica;
        this.descricao = descricao;
    }

    public Integer getPkIdFormaFarmaceutica() {
        return pkIdFormaFarmaceutica;
    }

    public void setPkIdFormaFarmaceutica(Integer pkIdFormaFarmaceutica) {
        this.pkIdFormaFarmaceutica = pkIdFormaFarmaceutica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProduto> getFarmProdutoList() {
        return farmProdutoList;
    }

    public void setFarmProdutoList(List<FarmProduto> farmProdutoList) {
        this.farmProdutoList = farmProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFormaFarmaceutica != null ? pkIdFormaFarmaceutica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmFormaFarmaceutica)) {
            return false;
        }
        FarmFormaFarmaceutica other = (FarmFormaFarmaceutica) object;
        if ((this.pkIdFormaFarmaceutica == null && other.pkIdFormaFarmaceutica != null) || (this.pkIdFormaFarmaceutica != null && !this.pkIdFormaFarmaceutica.equals(other.pkIdFormaFarmaceutica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmFormaFarmaceutica[ pkIdFormaFarmaceutica=" + pkIdFormaFarmaceutica + " ]";
    }
    
}
