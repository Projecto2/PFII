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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_funcao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhFuncao.findAll", query = "SELECT r FROM RhFuncao r"),
    @NamedQuery(name = "RhFuncao.findByPkIdFuncao", query = "SELECT r FROM RhFuncao r WHERE r.pkIdFuncao = :pkIdFuncao"),
    @NamedQuery(name = "RhFuncao.findByDescricao", query = "SELECT r FROM RhFuncao r WHERE r.descricao = :descricao")})
public class RhFuncao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_funcao", nullable = false)
    private Integer pkIdFuncao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFuncao")
    private List<RhFuncionario> rhFuncionarioList;

    public RhFuncao() {
    }

    public RhFuncao(Integer pkIdFuncao) {
        this.pkIdFuncao = pkIdFuncao;
    }

    public RhFuncao(Integer pkIdFuncao, String descricao) {
        this.pkIdFuncao = pkIdFuncao;
        this.descricao = descricao;
    }

    public Integer getPkIdFuncao() {
        return pkIdFuncao;
    }

    public void setPkIdFuncao(Integer pkIdFuncao) {
        this.pkIdFuncao = pkIdFuncao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFuncao != null ? pkIdFuncao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhFuncao)) {
            return false;
        }
        RhFuncao other = (RhFuncao) object;
        if ((this.pkIdFuncao == null && other.pkIdFuncao != null) || (this.pkIdFuncao != null && !this.pkIdFuncao.equals(other.pkIdFuncao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhFuncao[ pkIdFuncao=" + pkIdFuncao + " ]";
    }
    
}
