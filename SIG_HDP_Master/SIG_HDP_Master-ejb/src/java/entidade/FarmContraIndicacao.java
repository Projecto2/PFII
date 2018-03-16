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
@Table(name = "farm_contra_indicacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmContraIndicacao.findAll", query = "SELECT f FROM FarmContraIndicacao f"),
    @NamedQuery(name = "FarmContraIndicacao.findByPkIdContraIndicacao", query = "SELECT f FROM FarmContraIndicacao f WHERE f.pkIdContraIndicacao = :pkIdContraIndicacao"),
    @NamedQuery(name = "FarmContraIndicacao.findByDescricao", query = "SELECT f FROM FarmContraIndicacao f WHERE f.descricao = :descricao")})
public class FarmContraIndicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_contra_indicacao", nullable = false)
    private Integer pkIdContraIndicacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdContraIndicacao")
    private List<FarmProdutoHasContraIndicacao> farmProdutoHasContraIndicacaoList;

    public FarmContraIndicacao() {
    }

    public FarmContraIndicacao(Integer pkIdContraIndicacao) {
        this.pkIdContraIndicacao = pkIdContraIndicacao;
    }

    public FarmContraIndicacao(Integer pkIdContraIndicacao, String descricao) {
        this.pkIdContraIndicacao = pkIdContraIndicacao;
        this.descricao = descricao;
    }

    public Integer getPkIdContraIndicacao() {
        return pkIdContraIndicacao;
    }

    public void setPkIdContraIndicacao(Integer pkIdContraIndicacao) {
        this.pkIdContraIndicacao = pkIdContraIndicacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProdutoHasContraIndicacao> getFarmProdutoHasContraIndicacaoList() {
        return farmProdutoHasContraIndicacaoList;
    }

    public void setFarmProdutoHasContraIndicacaoList(List<FarmProdutoHasContraIndicacao> farmProdutoHasContraIndicacaoList) {
        this.farmProdutoHasContraIndicacaoList = farmProdutoHasContraIndicacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdContraIndicacao != null ? pkIdContraIndicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmContraIndicacao)) {
            return false;
        }
        FarmContraIndicacao other = (FarmContraIndicacao) object;
        if ((this.pkIdContraIndicacao == null && other.pkIdContraIndicacao != null) || (this.pkIdContraIndicacao != null && !this.pkIdContraIndicacao.equals(other.pkIdContraIndicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmContraIndicacao[ pkIdContraIndicacao=" + pkIdContraIndicacao + " ]";
    }
    
}
