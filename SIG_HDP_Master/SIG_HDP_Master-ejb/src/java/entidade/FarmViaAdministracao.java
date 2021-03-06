/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "farm_via_administracao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmViaAdministracao.findAll", query = "SELECT f FROM FarmViaAdministracao f"),
    @NamedQuery(name = "FarmViaAdministracao.findByPkIdViaAdministracao", query = "SELECT f FROM FarmViaAdministracao f WHERE f.pkIdViaAdministracao = :pkIdViaAdministracao"),
    @NamedQuery(name = "FarmViaAdministracao.findByDescricao", query = "SELECT f FROM FarmViaAdministracao f WHERE f.descricao = :descricao")})
public class FarmViaAdministracao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_via_administracao", nullable = false)
    private Integer pkIdViaAdministracao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(mappedBy = "fkIdViaAdministracao")
    private List<FarmProduto> farmProdutoList;

    public FarmViaAdministracao() {
    }

    public FarmViaAdministracao(Integer pkIdViaAdministracao) {
        this.pkIdViaAdministracao = pkIdViaAdministracao;
    }

    public FarmViaAdministracao(Integer pkIdViaAdministracao, String descricao) {
        this.pkIdViaAdministracao = pkIdViaAdministracao;
        this.descricao = descricao;
    }

    public Integer getPkIdViaAdministracao() {
        return pkIdViaAdministracao;
    }

    public void setPkIdViaAdministracao(Integer pkIdViaAdministracao) {
        this.pkIdViaAdministracao = pkIdViaAdministracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProduto> getFarmProdutoList() {
        return farmProdutoList;
    }

    public void setFarmProdutoList(List<FarmProduto> farmProdutoList) {
        this.farmProdutoList = farmProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdViaAdministracao != null ? pkIdViaAdministracao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmViaAdministracao)) {
            return false;
        }
        FarmViaAdministracao other = (FarmViaAdministracao) object;
        if ((this.pkIdViaAdministracao == null && other.pkIdViaAdministracao != null) || (this.pkIdViaAdministracao != null && !this.pkIdViaAdministracao.equals(other.pkIdViaAdministracao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmViaAdministracao[ pkIdViaAdministracao=" + pkIdViaAdministracao + " ]";
    }
    
}
