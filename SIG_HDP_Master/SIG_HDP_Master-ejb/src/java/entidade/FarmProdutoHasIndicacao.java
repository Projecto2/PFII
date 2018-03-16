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
@Table(name = "farm_produto_has_indicacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_indicacao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasIndicacao.findAll", query = "SELECT f FROM FarmProdutoHasIndicacao f"),
    @NamedQuery(name = "FarmProdutoHasIndicacao.findByPkIdProdutoHasIndicacao", query = "SELECT f FROM FarmProdutoHasIndicacao f WHERE f.pkIdProdutoHasIndicacao = :pkIdProdutoHasIndicacao")})
public class FarmProdutoHasIndicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_indicacao", nullable = false)
    private Integer pkIdProdutoHasIndicacao;
    @JoinColumn(name = "fk_id_indicacao", referencedColumnName = "pk_id_indicacao", nullable = false)
    @ManyToOne(optional = false)
    private FarmIndicacao fkIdIndicacao;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;

    public FarmProdutoHasIndicacao() {
    }

    public FarmProdutoHasIndicacao(Integer pkIdProdutoHasIndicacao) {
        this.pkIdProdutoHasIndicacao = pkIdProdutoHasIndicacao;
    }

    public Integer getPkIdProdutoHasIndicacao() {
        return pkIdProdutoHasIndicacao;
    }

    public void setPkIdProdutoHasIndicacao(Integer pkIdProdutoHasIndicacao) {
        this.pkIdProdutoHasIndicacao = pkIdProdutoHasIndicacao;
    }

    public FarmIndicacao getFkIdIndicacao() {
        return fkIdIndicacao;
    }

    public void setFkIdIndicacao(FarmIndicacao fkIdIndicacao) {
        this.fkIdIndicacao = fkIdIndicacao;
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
        hash += (pkIdProdutoHasIndicacao != null ? pkIdProdutoHasIndicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasIndicacao)) {
            return false;
        }
        FarmProdutoHasIndicacao other = (FarmProdutoHasIndicacao) object;
        if ((this.pkIdProdutoHasIndicacao == null && other.pkIdProdutoHasIndicacao != null) || (this.pkIdProdutoHasIndicacao != null && !this.pkIdProdutoHasIndicacao.equals(other.pkIdProdutoHasIndicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasIndicacao[ pkIdProdutoHasIndicacao=" + pkIdProdutoHasIndicacao + " ]";
    }
    
}
