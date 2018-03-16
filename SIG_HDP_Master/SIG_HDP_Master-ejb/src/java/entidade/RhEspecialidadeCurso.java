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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rh_especialidade_curso", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao", "fk_id_curso"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEspecialidadeCurso.findAll", query = "SELECT r FROM RhEspecialidadeCurso r"),
    @NamedQuery(name = "RhEspecialidadeCurso.findByPkIdEspecialidadeCurso", query = "SELECT r FROM RhEspecialidadeCurso r WHERE r.pkIdEspecialidadeCurso = :pkIdEspecialidadeCurso"),
    @NamedQuery(name = "RhEspecialidadeCurso.findByDescricao", query = "SELECT r FROM RhEspecialidadeCurso r WHERE r.descricao = :descricao")})
public class RhEspecialidadeCurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_especialidade_curso", nullable = false)
    private Integer pkIdEspecialidadeCurso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @JoinColumn(name = "fk_id_curso", referencedColumnName = "pk_id_curso", nullable = false)
    @ManyToOne(optional = false)
    private RhCurso fkIdCurso;
    @OneToMany(mappedBy = "fkIdEspecialidadeCurso")
    private List<RhEstagiario> rhEstagiarioList;

    public RhEspecialidadeCurso() {
    }

    public RhEspecialidadeCurso(Integer pkIdEspecialidadeCurso) {
        this.pkIdEspecialidadeCurso = pkIdEspecialidadeCurso;
    }

    public RhEspecialidadeCurso(Integer pkIdEspecialidadeCurso, String descricao) {
        this.pkIdEspecialidadeCurso = pkIdEspecialidadeCurso;
        this.descricao = descricao;
    }

    public Integer getPkIdEspecialidadeCurso() {
        return pkIdEspecialidadeCurso;
    }

    public void setPkIdEspecialidadeCurso(Integer pkIdEspecialidadeCurso) {
        this.pkIdEspecialidadeCurso = pkIdEspecialidadeCurso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public RhCurso getFkIdCurso() {
        return fkIdCurso;
    }

    public void setFkIdCurso(RhCurso fkIdCurso) {
        this.fkIdCurso = fkIdCurso;
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
        hash += (pkIdEspecialidadeCurso != null ? pkIdEspecialidadeCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEspecialidadeCurso)) {
            return false;
        }
        RhEspecialidadeCurso other = (RhEspecialidadeCurso) object;
        if ((this.pkIdEspecialidadeCurso == null && other.pkIdEspecialidadeCurso != null) || (this.pkIdEspecialidadeCurso != null && !this.pkIdEspecialidadeCurso.equals(other.pkIdEspecialidadeCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEspecialidadeCurso[ pkIdEspecialidadeCurso=" + pkIdEspecialidadeCurso + " ]";
    }
    
}
