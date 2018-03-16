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
@Table(name = "farm_tipo_quantidade", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTipoQuantidade.findAll", query = "SELECT f FROM FarmTipoQuantidade f"),
    @NamedQuery(name = "FarmTipoQuantidade.findByPkIdTipoQuantidade", query = "SELECT f FROM FarmTipoQuantidade f WHERE f.pkIdTipoQuantidade = :pkIdTipoQuantidade"),
    @NamedQuery(name = "FarmTipoQuantidade.findByDescricao", query = "SELECT f FROM FarmTipoQuantidade f WHERE f.descricao = :descricao")})
public class FarmTipoQuantidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_quantidade", nullable = false)
    private Integer pkIdTipoQuantidade;
    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTipoQuantidade")
    private List<FarmMovimentoHasProduto> farmMovimentoHasProdutoList;

    public FarmTipoQuantidade() {
    }

    public FarmTipoQuantidade(Integer pkIdTipoQuantidade) {
        this.pkIdTipoQuantidade = pkIdTipoQuantidade;
    }

    public Integer getPkIdTipoQuantidade() {
        return pkIdTipoQuantidade;
    }

    public void setPkIdTipoQuantidade(Integer pkIdTipoQuantidade) {
        this.pkIdTipoQuantidade = pkIdTipoQuantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmMovimentoHasProduto> getFarmMovimentoHasProdutoList() {
        return farmMovimentoHasProdutoList;
    }

    public void setFarmMovimentoHasProdutoList(List<FarmMovimentoHasProduto> farmMovimentoHasProdutoList) {
        this.farmMovimentoHasProdutoList = farmMovimentoHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoQuantidade != null ? pkIdTipoQuantidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTipoQuantidade)) {
            return false;
        }
        FarmTipoQuantidade other = (FarmTipoQuantidade) object;
        if ((this.pkIdTipoQuantidade == null && other.pkIdTipoQuantidade != null) || (this.pkIdTipoQuantidade != null && !this.pkIdTipoQuantidade.equals(other.pkIdTipoQuantidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTipoQuantidade[ pkIdTipoQuantidade=" + pkIdTipoQuantidade + " ]";
    }
    
}
