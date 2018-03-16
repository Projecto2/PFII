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
@Table(name = "rh_periodo_aulas", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhPeriodoAulas.findAll", query = "SELECT r FROM RhPeriodoAulas r"),
    @NamedQuery(name = "RhPeriodoAulas.findByPkIdPeriodoAulas", query = "SELECT r FROM RhPeriodoAulas r WHERE r.pkIdPeriodoAulas = :pkIdPeriodoAulas"),
    @NamedQuery(name = "RhPeriodoAulas.findByDescricao", query = "SELECT r FROM RhPeriodoAulas r WHERE r.descricao = :descricao")})
public class RhPeriodoAulas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_periodo_aulas", nullable = false)
    private Integer pkIdPeriodoAulas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdPeriodoAulas")
    private List<RhEstagiario> rhEstagiarioList;

    public RhPeriodoAulas() {
    }

    public RhPeriodoAulas(Integer pkIdPeriodoAulas) {
        this.pkIdPeriodoAulas = pkIdPeriodoAulas;
    }

    public RhPeriodoAulas(Integer pkIdPeriodoAulas, String descricao) {
        this.pkIdPeriodoAulas = pkIdPeriodoAulas;
        this.descricao = descricao;
    }

    public Integer getPkIdPeriodoAulas() {
        return pkIdPeriodoAulas;
    }

    public void setPkIdPeriodoAulas(Integer pkIdPeriodoAulas) {
        this.pkIdPeriodoAulas = pkIdPeriodoAulas;
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
        hash += (pkIdPeriodoAulas != null ? pkIdPeriodoAulas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhPeriodoAulas)) {
            return false;
        }
        RhPeriodoAulas other = (RhPeriodoAulas) object;
        if ((this.pkIdPeriodoAulas == null && other.pkIdPeriodoAulas != null) || (this.pkIdPeriodoAulas != null && !this.pkIdPeriodoAulas.equals(other.pkIdPeriodoAulas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhPeriodoAulas[ pkIdPeriodoAulas=" + pkIdPeriodoAulas + " ]";
    }
    
}
