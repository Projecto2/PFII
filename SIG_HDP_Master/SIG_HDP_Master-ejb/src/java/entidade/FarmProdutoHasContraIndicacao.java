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
@Table(name = "farm_produto_has_contra_indicacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_produto", "fk_id_contra_indicacao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProdutoHasContraIndicacao.findAll", query = "SELECT f FROM FarmProdutoHasContraIndicacao f"),
    @NamedQuery(name = "FarmProdutoHasContraIndicacao.findByPkIdProdutoHasContraIndicacao", query = "SELECT f FROM FarmProdutoHasContraIndicacao f WHERE f.pkIdProdutoHasContraIndicacao = :pkIdProdutoHasContraIndicacao")})
public class FarmProdutoHasContraIndicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto_has_contra_indicacao", nullable = false)
    private Integer pkIdProdutoHasContraIndicacao;
    @JoinColumn(name = "fk_id_contra_indicacao", referencedColumnName = "pk_id_contra_indicacao", nullable = false)
    @ManyToOne(optional = false)
    private FarmContraIndicacao fkIdContraIndicacao;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;

    public FarmProdutoHasContraIndicacao() {
    }

    public FarmProdutoHasContraIndicacao(Integer pkIdProdutoHasContraIndicacao) {
        this.pkIdProdutoHasContraIndicacao = pkIdProdutoHasContraIndicacao;
    }

    public Integer getPkIdProdutoHasContraIndicacao() {
        return pkIdProdutoHasContraIndicacao;
    }

    public void setPkIdProdutoHasContraIndicacao(Integer pkIdProdutoHasContraIndicacao) {
        this.pkIdProdutoHasContraIndicacao = pkIdProdutoHasContraIndicacao;
    }

    public FarmContraIndicacao getFkIdContraIndicacao() {
        return fkIdContraIndicacao;
    }

    public void setFkIdContraIndicacao(FarmContraIndicacao fkIdContraIndicacao) {
        this.fkIdContraIndicacao = fkIdContraIndicacao;
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
        hash += (pkIdProdutoHasContraIndicacao != null ? pkIdProdutoHasContraIndicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProdutoHasContraIndicacao)) {
            return false;
        }
        FarmProdutoHasContraIndicacao other = (FarmProdutoHasContraIndicacao) object;
        if ((this.pkIdProdutoHasContraIndicacao == null && other.pkIdProdutoHasContraIndicacao != null) || (this.pkIdProdutoHasContraIndicacao != null && !this.pkIdProdutoHasContraIndicacao.equals(other.pkIdProdutoHasContraIndicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProdutoHasContraIndicacao[ pkIdProdutoHasContraIndicacao=" + pkIdProdutoHasContraIndicacao + " ]";
    }
    
}
