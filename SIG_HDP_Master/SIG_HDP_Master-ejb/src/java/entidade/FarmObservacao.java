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
@Table(name = "farm_observacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmObservacao.findAll", query = "SELECT f FROM FarmObservacao f"),
    @NamedQuery(name = "FarmObservacao.findByPkIdObservacao", query = "SELECT f FROM FarmObservacao f WHERE f.pkIdObservacao = :pkIdObservacao"),
    @NamedQuery(name = "FarmObservacao.findByDescricao", query = "SELECT f FROM FarmObservacao f WHERE f.descricao = :descricao")})
public class FarmObservacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_observacao", nullable = false)
    private Integer pkIdObservacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdObservacao")
    private List<FarmProdutoHasObservacao> farmProdutoHasObservacaoList;

    public FarmObservacao() {
    }

    public FarmObservacao(Integer pkIdObservacao) {
        this.pkIdObservacao = pkIdObservacao;
    }

    public FarmObservacao(Integer pkIdObservacao, String descricao) {
        this.pkIdObservacao = pkIdObservacao;
        this.descricao = descricao;
    }

    public Integer getPkIdObservacao() {
        return pkIdObservacao;
    }

    public void setPkIdObservacao(Integer pkIdObservacao) {
        this.pkIdObservacao = pkIdObservacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProdutoHasObservacao> getFarmProdutoHasObservacaoList() {
        return farmProdutoHasObservacaoList;
    }

    public void setFarmProdutoHasObservacaoList(List<FarmProdutoHasObservacao> farmProdutoHasObservacaoList) {
        this.farmProdutoHasObservacaoList = farmProdutoHasObservacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdObservacao != null ? pkIdObservacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmObservacao)) {
            return false;
        }
        FarmObservacao other = (FarmObservacao) object;
        if ((this.pkIdObservacao == null && other.pkIdObservacao != null) || (this.pkIdObservacao != null && !this.pkIdObservacao.equals(other.pkIdObservacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmObservacao[ pkIdObservacao=" + pkIdObservacao + " ]";
    }
    
}
