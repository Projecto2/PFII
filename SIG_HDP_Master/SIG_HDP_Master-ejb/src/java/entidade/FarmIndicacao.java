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
@Table(name = "farm_indicacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmIndicacao.findAll", query = "SELECT f FROM FarmIndicacao f"),
    @NamedQuery(name = "FarmIndicacao.findByPkIdIndicacao", query = "SELECT f FROM FarmIndicacao f WHERE f.pkIdIndicacao = :pkIdIndicacao"),
    @NamedQuery(name = "FarmIndicacao.findByDescricao", query = "SELECT f FROM FarmIndicacao f WHERE f.descricao = :descricao")})
public class FarmIndicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_indicacao", nullable = false)
    private Integer pkIdIndicacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdIndicacao")
    private List<FarmProdutoHasIndicacao> farmProdutoHasIndicacaoList;

    public FarmIndicacao() {
    }

    public FarmIndicacao(Integer pkIdIndicacao) {
        this.pkIdIndicacao = pkIdIndicacao;
    }

    public FarmIndicacao(Integer pkIdIndicacao, String descricao) {
        this.pkIdIndicacao = pkIdIndicacao;
        this.descricao = descricao;
    }

    public Integer getPkIdIndicacao() {
        return pkIdIndicacao;
    }

    public void setPkIdIndicacao(Integer pkIdIndicacao) {
        this.pkIdIndicacao = pkIdIndicacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProdutoHasIndicacao> getFarmProdutoHasIndicacaoList() {
        return farmProdutoHasIndicacaoList;
    }

    public void setFarmProdutoHasIndicacaoList(List<FarmProdutoHasIndicacao> farmProdutoHasIndicacaoList) {
        this.farmProdutoHasIndicacaoList = farmProdutoHasIndicacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdIndicacao != null ? pkIdIndicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmIndicacao)) {
            return false;
        }
        FarmIndicacao other = (FarmIndicacao) object;
        if ((this.pkIdIndicacao == null && other.pkIdIndicacao != null) || (this.pkIdIndicacao != null && !this.pkIdIndicacao.equals(other.pkIdIndicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmIndicacao[ pkIdIndicacao=" + pkIdIndicacao + " ]";
    }
    
}
