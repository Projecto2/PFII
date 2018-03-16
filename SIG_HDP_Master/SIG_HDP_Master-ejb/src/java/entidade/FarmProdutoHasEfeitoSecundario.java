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
@Table(name = "farm_produto_has_efeito_secundario", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_efeito_secundario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasEfeitoSecundario.findAll", query = "SELECT f FROM FarmProdutoHasEfeitoSecundario f"),
    @NamedQuery(name = "FarmProdutoHasEfeitoSecundario.findByPkIdProdutoHasEfeitoSecundario", query = "SELECT f FROM FarmProdutoHasEfeitoSecundario f WHERE f.pkIdProdutoHasEfeitoSecundario = :pkIdProdutoHasEfeitoSecundario")})
public class FarmProdutoHasEfeitoSecundario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_efeito_secundario", nullable = false)
    private Integer pkIdProdutoHasEfeitoSecundario;
    @JoinColumn(name = "fk_id_efeito_secundario", referencedColumnName = "pk_id_efeito_secundario")
    @ManyToOne
    private FarmEfeitoSecundario fkIdEfeitoSecundario;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto")
    @ManyToOne
    private FarmProduto fkIdProduto;

    public FarmProdutoHasEfeitoSecundario() {
    }

    public FarmProdutoHasEfeitoSecundario(Integer pkIdProdutoHasEfeitoSecundario) {
        this.pkIdProdutoHasEfeitoSecundario = pkIdProdutoHasEfeitoSecundario;
    }

    public Integer getPkIdProdutoHasEfeitoSecundario() {
        return pkIdProdutoHasEfeitoSecundario;
    }

    public void setPkIdProdutoHasEfeitoSecundario(Integer pkIdProdutoHasEfeitoSecundario) {
        this.pkIdProdutoHasEfeitoSecundario = pkIdProdutoHasEfeitoSecundario;
    }

    public FarmEfeitoSecundario getFkIdEfeitoSecundario() {
        return fkIdEfeitoSecundario;
    }

    public void setFkIdEfeitoSecundario(FarmEfeitoSecundario fkIdEfeitoSecundario) {
        this.fkIdEfeitoSecundario = fkIdEfeitoSecundario;
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
        hash += (pkIdProdutoHasEfeitoSecundario != null ? pkIdProdutoHasEfeitoSecundario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasEfeitoSecundario)) {
            return false;
        }
        FarmProdutoHasEfeitoSecundario other = (FarmProdutoHasEfeitoSecundario) object;
        if ((this.pkIdProdutoHasEfeitoSecundario == null && other.pkIdProdutoHasEfeitoSecundario != null) || (this.pkIdProdutoHasEfeitoSecundario != null && !this.pkIdProdutoHasEfeitoSecundario.equals(other.pkIdProdutoHasEfeitoSecundario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasEfeitoSecundario[ pkIdProdutoHasEfeitoSecundario=" + pkIdProdutoHasEfeitoSecundario + " ]";
    }
    
}
