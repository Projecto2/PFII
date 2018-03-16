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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_curso", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhCurso.findAll", query = "SELECT r FROM RhCurso r"),
    @NamedQuery(name = "RhCurso.findByPkIdCurso", query = "SELECT r FROM RhCurso r WHERE r.pkIdCurso = :pkIdCurso"),
    @NamedQuery(name = "RhCurso.findByDescricao", query = "SELECT r FROM RhCurso r WHERE r.descricao = :descricao")})
public class RhCurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_curso", nullable = false)
    private Integer pkIdCurso;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCurso")
    private List<RhEspecialidadeCurso> rhEspecialidadeCursoList;

    public RhCurso() {
    }

    public RhCurso(Integer pkIdCurso) {
        this.pkIdCurso = pkIdCurso;
    }

    public Integer getPkIdCurso() {
        return pkIdCurso;
    }

    public void setPkIdCurso(Integer pkIdCurso) {
        this.pkIdCurso = pkIdCurso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhEspecialidadeCurso> getRhEspecialidadeCursoList() {
        return rhEspecialidadeCursoList;
    }

    public void setRhEspecialidadeCursoList(List<RhEspecialidadeCurso> rhEspecialidadeCursoList) {
        this.rhEspecialidadeCursoList = rhEspecialidadeCursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCurso != null ? pkIdCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhCurso)) {
            return false;
        }
        RhCurso other = (RhCurso) object;
        if ((this.pkIdCurso == null && other.pkIdCurso != null) || (this.pkIdCurso != null && !this.pkIdCurso.equals(other.pkIdCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhCurso[ pkIdCurso=" + pkIdCurso + " ]";
    }
    
}
