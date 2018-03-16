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
@Table(name = "rh_tipo_funcionario", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhTipoFuncionario.findAll", query = "SELECT r FROM RhTipoFuncionario r"),
    @NamedQuery(name = "RhTipoFuncionario.findByPkIdTipoFuncionario", query = "SELECT r FROM RhTipoFuncionario r WHERE r.pkIdTipoFuncionario = :pkIdTipoFuncionario"),
    @NamedQuery(name = "RhTipoFuncionario.findByDescricao", query = "SELECT r FROM RhTipoFuncionario r WHERE r.descricao = :descricao")})
public class RhTipoFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_funcionario", nullable = false)
    private Integer pkIdTipoFuncionario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "descricao", nullable = false, length = 60)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoFuncionario")
    private List<RhFuncionario> rhFuncionarioList;

    public RhTipoFuncionario() {
    }

    public RhTipoFuncionario(Integer pkIdTipoFuncionario) {
        this.pkIdTipoFuncionario = pkIdTipoFuncionario;
    }

    public RhTipoFuncionario(Integer pkIdTipoFuncionario, String descricao) {
        this.pkIdTipoFuncionario = pkIdTipoFuncionario;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoFuncionario() {
        return pkIdTipoFuncionario;
    }

    public void setPkIdTipoFuncionario(Integer pkIdTipoFuncionario) {
        this.pkIdTipoFuncionario = pkIdTipoFuncionario;
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
        hash += (pkIdTipoFuncionario != null ? pkIdTipoFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhTipoFuncionario)) {
            return false;
        }
        RhTipoFuncionario other = (RhTipoFuncionario) object;
        if ((this.pkIdTipoFuncionario == null && other.pkIdTipoFuncionario != null) || (this.pkIdTipoFuncionario != null && !this.pkIdTipoFuncionario.equals(other.pkIdTipoFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhTipoFuncionario[ pkIdTipoFuncionario=" + pkIdTipoFuncionario + " ]";
    }
    
}
