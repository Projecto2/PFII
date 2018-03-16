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
@Table(name = "farm_tipo_produto", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTipoProduto.findAll", query = "SELECT f FROM FarmTipoProduto f"),
    @NamedQuery(name = "FarmTipoProduto.findByDescricao", query = "SELECT f FROM FarmTipoProduto f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "FarmTipoProduto.findByPkIdTipoProduto", query = "SELECT f FROM FarmTipoProduto f WHERE f.pkIdTipoProduto = :pkIdTipoProduto")})
public class FarmTipoProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_produto", nullable = false)
    private Integer pkIdTipoProduto;
    @OneToMany(mappedBy = "fkIdTipoProduto")
    private List<FarmProduto> farmProdutoList;

    public FarmTipoProduto() {
    }

    public FarmTipoProduto(Integer pkIdTipoProduto) {
        this.pkIdTipoProduto = pkIdTipoProduto;
    }

    public FarmTipoProduto(Integer pkIdTipoProduto, String descricao) {
        this.pkIdTipoProduto = pkIdTipoProduto;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdTipoProduto() {
        return pkIdTipoProduto;
    }

    public void setPkIdTipoProduto(Integer pkIdTipoProduto) {
        this.pkIdTipoProduto = pkIdTipoProduto;
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
        hash += (pkIdTipoProduto != null ? pkIdTipoProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTipoProduto)) {
            return false;
        }
        FarmTipoProduto other = (FarmTipoProduto) object;
        if ((this.pkIdTipoProduto == null && other.pkIdTipoProduto != null) || (this.pkIdTipoProduto != null && !this.pkIdTipoProduto.equals(other.pkIdTipoProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTipoProduto[ pkIdTipoProduto=" + pkIdTipoProduto + " ]";
    }
    
}
