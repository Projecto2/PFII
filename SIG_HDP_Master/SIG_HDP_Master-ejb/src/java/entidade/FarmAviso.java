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
@Table(name = "farm_aviso", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmAviso.findAll", query = "SELECT f FROM FarmAviso f"),
    @NamedQuery(name = "FarmAviso.findByPkIdAviso", query = "SELECT f FROM FarmAviso f WHERE f.pkIdAviso = :pkIdAviso"),
    @NamedQuery(name = "FarmAviso.findByDescricao", query = "SELECT f FROM FarmAviso f WHERE f.descricao = :descricao")})
public class FarmAviso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_aviso", nullable = false)
    private Integer pkIdAviso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdAviso")
    private List<FarmProdutoHasAviso> farmProdutoHasAvisoList;

    public FarmAviso() {
    }

    public FarmAviso(Integer pkIdAviso) {
        this.pkIdAviso = pkIdAviso;
    }

    public FarmAviso(Integer pkIdAviso, String descricao) {
        this.pkIdAviso = pkIdAviso;
        this.descricao = descricao;
    }

    public Integer getPkIdAviso() {
        return pkIdAviso;
    }

    public void setPkIdAviso(Integer pkIdAviso) {
        this.pkIdAviso = pkIdAviso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProdutoHasAviso> getFarmProdutoHasAvisoList() {
        return farmProdutoHasAvisoList;
    }

    public void setFarmProdutoHasAvisoList(List<FarmProdutoHasAviso> farmProdutoHasAvisoList) {
        this.farmProdutoHasAvisoList = farmProdutoHasAvisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAviso != null ? pkIdAviso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmAviso)) {
            return false;
        }
        FarmAviso other = (FarmAviso) object;
        if ((this.pkIdAviso == null && other.pkIdAviso != null) || (this.pkIdAviso != null && !this.pkIdAviso.equals(other.pkIdAviso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmAviso[ pkIdAviso=" + pkIdAviso + " ]";
    }
    
}
