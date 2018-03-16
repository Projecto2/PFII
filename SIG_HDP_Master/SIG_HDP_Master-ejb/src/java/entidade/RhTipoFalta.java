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
@Table(name = "rh_tipo_falta", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhTipoFalta.findAll", query = "SELECT r FROM RhTipoFalta r"),
    @NamedQuery(name = "RhTipoFalta.findByPkIdTipoFalta", query = "SELECT r FROM RhTipoFalta r WHERE r.pkIdTipoFalta = :pkIdTipoFalta"),
    @NamedQuery(name = "RhTipoFalta.findByDescricao", query = "SELECT r FROM RhTipoFalta r WHERE r.descricao = :descricao")})
public class RhTipoFalta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_falta", nullable = false)
    private Integer pkIdTipoFalta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTipoFalta")
    private List<RhFuncionarioHasRhTipoFalta> rhFuncionarioHasRhTipoFaltaList;

    public RhTipoFalta() {
    }

    public RhTipoFalta(Integer pkIdTipoFalta) {
        this.pkIdTipoFalta = pkIdTipoFalta;
    }

    public RhTipoFalta(Integer pkIdTipoFalta, String descricao) {
        this.pkIdTipoFalta = pkIdTipoFalta;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoFalta() {
        return pkIdTipoFalta;
    }

    public void setPkIdTipoFalta(Integer pkIdTipoFalta) {
        this.pkIdTipoFalta = pkIdTipoFalta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhFuncionarioHasRhTipoFalta> getRhFuncionarioHasRhTipoFaltaList() {
        return rhFuncionarioHasRhTipoFaltaList;
    }

    public void setRhFuncionarioHasRhTipoFaltaList(List<RhFuncionarioHasRhTipoFalta> rhFuncionarioHasRhTipoFaltaList) {
        this.rhFuncionarioHasRhTipoFaltaList = rhFuncionarioHasRhTipoFaltaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoFalta != null ? pkIdTipoFalta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhTipoFalta)) {
            return false;
        }
        RhTipoFalta other = (RhTipoFalta) object;
        if ((this.pkIdTipoFalta == null && other.pkIdTipoFalta != null) || (this.pkIdTipoFalta != null && !this.pkIdTipoFalta.equals(other.pkIdTipoFalta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhTipoFalta[ pkIdTipoFalta=" + pkIdTipoFalta + " ]";
    }
    
}
