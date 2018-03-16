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
@Table(name = "rh_estado_estagiario", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEstadoEstagiario.findAll", query = "SELECT r FROM RhEstadoEstagiario r"),
    @NamedQuery(name = "RhEstadoEstagiario.findByPkIdEstadoEstagiario", query = "SELECT r FROM RhEstadoEstagiario r WHERE r.pkIdEstadoEstagiario = :pkIdEstadoEstagiario"),
    @NamedQuery(name = "RhEstadoEstagiario.findByDescricao", query = "SELECT r FROM RhEstadoEstagiario r WHERE r.descricao = :descricao")})
public class RhEstadoEstagiario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_estagiario", nullable = false)
    private Integer pkIdEstadoEstagiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "descricao", nullable = false, length = 60)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoEstagiario")
    private List<RhEstagiario> rhEstagiarioList;

    public RhEstadoEstagiario() {
    }

    public RhEstadoEstagiario(Integer pkIdEstadoEstagiario) {
        this.pkIdEstadoEstagiario = pkIdEstadoEstagiario;
    }

    public RhEstadoEstagiario(Integer pkIdEstadoEstagiario, String descricao) {
        this.pkIdEstadoEstagiario = pkIdEstadoEstagiario;
        this.descricao = descricao;
    }

    public Integer getPkIdEstadoEstagiario() {
        return pkIdEstadoEstagiario;
    }

    public void setPkIdEstadoEstagiario(Integer pkIdEstadoEstagiario) {
        this.pkIdEstadoEstagiario = pkIdEstadoEstagiario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhEstagiario> getRhEstagiarioList() {
        return rhEstagiarioList;
    }

    public void setRhEstagiarioList(List<RhEstagiario> rhEstagiarioList) {
        this.rhEstagiarioList = rhEstagiarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoEstagiario != null ? pkIdEstadoEstagiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEstadoEstagiario)) {
            return false;
        }
        RhEstadoEstagiario other = (RhEstadoEstagiario) object;
        if ((this.pkIdEstadoEstagiario == null && other.pkIdEstadoEstagiario != null) || (this.pkIdEstadoEstagiario != null && !this.pkIdEstadoEstagiario.equals(other.pkIdEstadoEstagiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEstadoEstagiario[ pkIdEstadoEstagiario=" + pkIdEstadoEstagiario + " ]";
    }
    
}
