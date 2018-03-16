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
@Table(name = "rh_nivel_academico", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_nivel_academico"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhNivelAcademico.findAll", query = "SELECT r FROM RhNivelAcademico r"),
    @NamedQuery(name = "RhNivelAcademico.findByPkIdNivelAcademico", query = "SELECT r FROM RhNivelAcademico r WHERE r.pkIdNivelAcademico = :pkIdNivelAcademico"),
    @NamedQuery(name = "RhNivelAcademico.findByDescricaoNivelAcademico", query = "SELECT r FROM RhNivelAcademico r WHERE r.descricaoNivelAcademico = :descricaoNivelAcademico")})
public class RhNivelAcademico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_nivel_academico", nullable = false)
    private Integer pkIdNivelAcademico;
    @Size(max = 40)
    @Column(name = "descricao_nivel_academico", length = 40)
    private String descricaoNivelAcademico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdNivelAcademico")
    private List<RhFuncionario> rhFuncionarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdNivelAcademico")
    private List<RhCandidato> rhCandidatoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdNivelAcademico")
    private List<RhEstagiario> rhEstagiarioList;

    public RhNivelAcademico() {
    }

    public RhNivelAcademico(Integer pkIdNivelAcademico) {
        this.pkIdNivelAcademico = pkIdNivelAcademico;
    }

    public Integer getPkIdNivelAcademico() {
        return pkIdNivelAcademico;
    }

    public void setPkIdNivelAcademico(Integer pkIdNivelAcademico) {
        this.pkIdNivelAcademico = pkIdNivelAcademico;
    }

    public String getDescricaoNivelAcademico() {
        return descricaoNivelAcademico;
    }

    public void setDescricaoNivelAcademico(String descricaoNivelAcademico) {
        this.descricaoNivelAcademico = descricaoNivelAcademico;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    @XmlTransient
    public List<RhCandidato> getRhCandidatoList() {
        return rhCandidatoList;
    }

    public void setRhCandidatoList(List<RhCandidato> rhCandidatoList) {
        this.rhCandidatoList = rhCandidatoList;
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
        hash += (pkIdNivelAcademico != null ? pkIdNivelAcademico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhNivelAcademico)) {
            return false;
        }
        RhNivelAcademico other = (RhNivelAcademico) object;
        if ((this.pkIdNivelAcademico == null && other.pkIdNivelAcademico != null) || (this.pkIdNivelAcademico != null && !this.pkIdNivelAcademico.equals(other.pkIdNivelAcademico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhNivelAcademico[ pkIdNivelAcademico=" + pkIdNivelAcademico + " ]";
    }
    
}
