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
@Table(name = "rh_estado_funcionario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEstadoFuncionario.findAll", query = "SELECT r FROM RhEstadoFuncionario r"),
    @NamedQuery(name = "RhEstadoFuncionario.findByPkIdEstadoFuncionario", query = "SELECT r FROM RhEstadoFuncionario r WHERE r.pkIdEstadoFuncionario = :pkIdEstadoFuncionario"),
    @NamedQuery(name = "RhEstadoFuncionario.findByDescricao", query = "SELECT r FROM RhEstadoFuncionario r WHERE r.descricao = :descricao")})
public class RhEstadoFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_funcionario", nullable = false)
    private Integer pkIdEstadoFuncionario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoFuncionario")
    private List<RhFuncionario> rhFuncionarioList;

    public RhEstadoFuncionario() {
    }

    public RhEstadoFuncionario(Integer pkIdEstadoFuncionario) {
        this.pkIdEstadoFuncionario = pkIdEstadoFuncionario;
    }

    public RhEstadoFuncionario(Integer pkIdEstadoFuncionario, String descricao) {
        this.pkIdEstadoFuncionario = pkIdEstadoFuncionario;
        this.descricao = descricao;
    }

    public Integer getPkIdEstadoFuncionario() {
        return pkIdEstadoFuncionario;
    }

    public void setPkIdEstadoFuncionario(Integer pkIdEstadoFuncionario) {
        this.pkIdEstadoFuncionario = pkIdEstadoFuncionario;
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
        hash += (pkIdEstadoFuncionario != null ? pkIdEstadoFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEstadoFuncionario)) {
            return false;
        }
        RhEstadoFuncionario other = (RhEstadoFuncionario) object;
        if ((this.pkIdEstadoFuncionario == null && other.pkIdEstadoFuncionario != null) || (this.pkIdEstadoFuncionario != null && !this.pkIdEstadoFuncionario.equals(other.pkIdEstadoFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEstadoFuncionario[ pkIdEstadoFuncionario=" + pkIdEstadoFuncionario + " ]";
    }
    
}
