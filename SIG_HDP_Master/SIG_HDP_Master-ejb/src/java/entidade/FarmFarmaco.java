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
@Table(name = "farm_farmaco", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmFarmaco.findAll", query = "SELECT f FROM FarmFarmaco f"),
    @NamedQuery(name = "FarmFarmaco.findByDescricao", query = "SELECT f FROM FarmFarmaco f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "FarmFarmaco.findByPkIdFarmaco", query = "SELECT f FROM FarmFarmaco f WHERE f.pkIdFarmaco = :pkIdFarmaco")})
public class FarmFarmaco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao", nullable = false, length = 250)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_farmaco", nullable = false)
    private Integer pkIdFarmaco;
    @OneToMany(mappedBy = "fkIdFarmaco")
    private List<FarmProdutoHasFarmaco> farmProdutoHasFarmacoList;

    public FarmFarmaco() {
    }

    public FarmFarmaco(Integer pkIdFarmaco) {
        this.pkIdFarmaco = pkIdFarmaco;
    }

    public FarmFarmaco(Integer pkIdFarmaco, String descricao) {
        this.pkIdFarmaco = pkIdFarmaco;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdFarmaco() {
        return pkIdFarmaco;
    }

    public void setPkIdFarmaco(Integer pkIdFarmaco) {
        this.pkIdFarmaco = pkIdFarmaco;
    }

    @XmlTransient
    public List<FarmProdutoHasFarmaco> getFarmProdutoHasFarmacoList() {
        return farmProdutoHasFarmacoList;
    }

    public void setFarmProdutoHasFarmacoList(List<FarmProdutoHasFarmaco> farmProdutoHasFarmacoList) {
        this.farmProdutoHasFarmacoList = farmProdutoHasFarmacoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFarmaco != null ? pkIdFarmaco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmFarmaco)) {
            return false;
        }
        FarmFarmaco other = (FarmFarmaco) object;
        if ((this.pkIdFarmaco == null && other.pkIdFarmaco != null) || (this.pkIdFarmaco != null && !this.pkIdFarmaco.equals(other.pkIdFarmaco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmFarmaco[ pkIdFarmaco=" + pkIdFarmaco + " ]";
    }
    
}
