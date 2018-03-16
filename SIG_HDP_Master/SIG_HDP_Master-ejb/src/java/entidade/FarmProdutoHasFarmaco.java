/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_produto_has_farmaco", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_farmaco"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasFarmaco.findAll", query = "SELECT f FROM FarmProdutoHasFarmaco f"),
    @NamedQuery(name = "FarmProdutoHasFarmaco.findByPkIdProdutoHasFarmaco", query = "SELECT f FROM FarmProdutoHasFarmaco f WHERE f.pkIdProdutoHasFarmaco = :pkIdProdutoHasFarmaco")})
public class FarmProdutoHasFarmaco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_farmaco", nullable = false)
    private Integer pkIdProdutoHasFarmaco;
    @JoinColumn(name = "fk_id_farmaco", referencedColumnName = "pk_id_farmaco")
    @ManyToOne
    private FarmFarmaco fkIdFarmaco;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto")
    @ManyToOne
    private FarmProduto fkIdProduto;

    public FarmProdutoHasFarmaco() {
    }

    public FarmProdutoHasFarmaco(Integer pkIdProdutoHasFarmaco) {
        this.pkIdProdutoHasFarmaco = pkIdProdutoHasFarmaco;
    }

    public Integer getPkIdProdutoHasFarmaco() {
        return pkIdProdutoHasFarmaco;
    }

    public void setPkIdProdutoHasFarmaco(Integer pkIdProdutoHasFarmaco) {
        this.pkIdProdutoHasFarmaco = pkIdProdutoHasFarmaco;
    }

    public FarmFarmaco getFkIdFarmaco() {
        return fkIdFarmaco;
    }

    public void setFkIdFarmaco(FarmFarmaco fkIdFarmaco) {
        this.fkIdFarmaco = fkIdFarmaco;
    }

    public FarmProduto getFkIdProduto() {
        return fkIdProduto;
    }

    public void setFkIdProduto(FarmProduto fkIdProduto) {
        this.fkIdProduto = fkIdProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProdutoHasFarmaco != null ? pkIdProdutoHasFarmaco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasFarmaco)) {
            return false;
        }
        FarmProdutoHasFarmaco other = (FarmProdutoHasFarmaco) object;
        if ((this.pkIdProdutoHasFarmaco == null && other.pkIdProdutoHasFarmaco != null) || (this.pkIdProdutoHasFarmaco != null && !this.pkIdProdutoHasFarmaco.equals(other.pkIdProdutoHasFarmaco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasFarmaco[ pkIdProdutoHasFarmaco=" + pkIdProdutoHasFarmaco + " ]";
    }
    
}
