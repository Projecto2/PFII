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
@Table(name = "farm_produto_has_outro_componente", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_outro_componente"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasOutroComponente.findAll", query = "SELECT f FROM FarmProdutoHasOutroComponente f"),
    @NamedQuery(name = "FarmProdutoHasOutroComponente.findByPkIdProdutoHasOutroComponente", query = "SELECT f FROM FarmProdutoHasOutroComponente f WHERE f.pkIdProdutoHasOutroComponente = :pkIdProdutoHasOutroComponente")})
public class FarmProdutoHasOutroComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_outro_componente", nullable = false)
    private Integer pkIdProdutoHasOutroComponente;
    @JoinColumn(name = "fk_id_outro_componente", referencedColumnName = "pk_id_componente")
    @ManyToOne
    private FarmOutroComponente fkIdOutroComponente;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto")
    @ManyToOne
    private FarmProduto fkIdProduto;

    public FarmProdutoHasOutroComponente() {
    }

    public FarmProdutoHasOutroComponente(Integer pkIdProdutoHasOutroComponente) {
        this.pkIdProdutoHasOutroComponente = pkIdProdutoHasOutroComponente;
    }

    public Integer getPkIdProdutoHasOutroComponente() {
        return pkIdProdutoHasOutroComponente;
    }

    public void setPkIdProdutoHasOutroComponente(Integer pkIdProdutoHasOutroComponente) {
        this.pkIdProdutoHasOutroComponente = pkIdProdutoHasOutroComponente;
    }

    public FarmOutroComponente getFkIdOutroComponente() {
        return fkIdOutroComponente;
    }

    public void setFkIdOutroComponente(FarmOutroComponente fkIdOutroComponente) {
        this.fkIdOutroComponente = fkIdOutroComponente;
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
        hash += (pkIdProdutoHasOutroComponente != null ? pkIdProdutoHasOutroComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasOutroComponente)) {
            return false;
        }
        FarmProdutoHasOutroComponente other = (FarmProdutoHasOutroComponente) object;
        if ((this.pkIdProdutoHasOutroComponente == null && other.pkIdProdutoHasOutroComponente != null) || (this.pkIdProdutoHasOutroComponente != null && !this.pkIdProdutoHasOutroComponente.equals(other.pkIdProdutoHasOutroComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasOutroComponente[ pkIdProdutoHasOutroComponente=" + pkIdProdutoHasOutroComponente + " ]";
    }
    
}
