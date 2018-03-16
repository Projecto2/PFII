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
@Table(name = "farm_produto_has_observacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_observacao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasObservacao.findAll", query = "SELECT f FROM FarmProdutoHasObservacao f"),
    @NamedQuery(name = "FarmProdutoHasObservacao.findByPkIdProdutoHasObservacao", query = "SELECT f FROM FarmProdutoHasObservacao f WHERE f.pkIdProdutoHasObservacao = :pkIdProdutoHasObservacao")})
public class FarmProdutoHasObservacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_observacao", nullable = false)
    private Integer pkIdProdutoHasObservacao;
    @JoinColumn(name = "fk_id_observacao", referencedColumnName = "pk_id_observacao", nullable = false)
    @ManyToOne(optional = false)
    private FarmObservacao fkIdObservacao;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;

    public FarmProdutoHasObservacao() {
    }

    public FarmProdutoHasObservacao(Integer pkIdProdutoHasObservacao) {
        this.pkIdProdutoHasObservacao = pkIdProdutoHasObservacao;
    }

    public Integer getPkIdProdutoHasObservacao() {
        return pkIdProdutoHasObservacao;
    }

    public void setPkIdProdutoHasObservacao(Integer pkIdProdutoHasObservacao) {
        this.pkIdProdutoHasObservacao = pkIdProdutoHasObservacao;
    }

    public FarmObservacao getFkIdObservacao() {
        return fkIdObservacao;
    }

    public void setFkIdObservacao(FarmObservacao fkIdObservacao) {
        this.fkIdObservacao = fkIdObservacao;
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
        hash += (pkIdProdutoHasObservacao != null ? pkIdProdutoHasObservacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasObservacao)) {
            return false;
        }
        FarmProdutoHasObservacao other = (FarmProdutoHasObservacao) object;
        if ((this.pkIdProdutoHasObservacao == null && other.pkIdProdutoHasObservacao != null) || (this.pkIdProdutoHasObservacao != null && !this.pkIdProdutoHasObservacao.equals(other.pkIdProdutoHasObservacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasObservacao[ pkIdProdutoHasObservacao=" + pkIdProdutoHasObservacao + " ]";
    }
    
}
