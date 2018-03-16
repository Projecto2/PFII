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
@Table(name = "farm_produto_has_aviso", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_aviso"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasAviso.findAll", query = "SELECT f FROM FarmProdutoHasAviso f"),
    @NamedQuery(name = "FarmProdutoHasAviso.findByPkIdProdutoHasAviso", query = "SELECT f FROM FarmProdutoHasAviso f WHERE f.pkIdProdutoHasAviso = :pkIdProdutoHasAviso")})
public class FarmProdutoHasAviso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_aviso", nullable = false)
    private Integer pkIdProdutoHasAviso;
    @JoinColumn(name = "fk_id_aviso", referencedColumnName = "pk_id_aviso", nullable = false)
    @ManyToOne(optional = false)
    private FarmAviso fkIdAviso;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;

    public FarmProdutoHasAviso() {
    }

    public FarmProdutoHasAviso(Integer pkIdProdutoHasAviso) {
        this.pkIdProdutoHasAviso = pkIdProdutoHasAviso;
    }

    public Integer getPkIdProdutoHasAviso() {
        return pkIdProdutoHasAviso;
    }

    public void setPkIdProdutoHasAviso(Integer pkIdProdutoHasAviso) {
        this.pkIdProdutoHasAviso = pkIdProdutoHasAviso;
    }

    public FarmAviso getFkIdAviso() {
        return fkIdAviso;
    }

    public void setFkIdAviso(FarmAviso fkIdAviso) {
        this.fkIdAviso = fkIdAviso;
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
        hash += (pkIdProdutoHasAviso != null ? pkIdProdutoHasAviso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasAviso)) {
            return false;
        }
        FarmProdutoHasAviso other = (FarmProdutoHasAviso) object;
        if ((this.pkIdProdutoHasAviso == null && other.pkIdProdutoHasAviso != null) || (this.pkIdProdutoHasAviso != null && !this.pkIdProdutoHasAviso.equals(other.pkIdProdutoHasAviso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasAviso[ pkIdProdutoHasAviso=" + pkIdProdutoHasAviso + " ]";
    }
    
}
