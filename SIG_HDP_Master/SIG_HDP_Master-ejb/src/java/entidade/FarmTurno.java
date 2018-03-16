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
@Table(name = "farm_turno", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTurno.findAll", query = "SELECT f FROM FarmTurno f"),
    @NamedQuery(name = "FarmTurno.findByPkIdTurno", query = "SELECT f FROM FarmTurno f WHERE f.pkIdTurno = :pkIdTurno"),
    @NamedQuery(name = "FarmTurno.findByDescricao", query = "SELECT f FROM FarmTurno f WHERE f.descricao = :descricao")})
public class FarmTurno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_turno", nullable = false)
    private Integer pkIdTurno;
    @Size(max = 60)
    @Column(name = "descricao", length = 60)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTurno")
    private List<FarmTurnoDispensa> farmTurnoDispensaList;

    public FarmTurno() {
    }

    public FarmTurno(Integer pkIdTurno) {
        this.pkIdTurno = pkIdTurno;
    }

    public Integer getPkIdTurno() {
        return pkIdTurno;
    }

    public void setPkIdTurno(Integer pkIdTurno) {
        this.pkIdTurno = pkIdTurno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmTurnoDispensa> getFarmTurnoDispensaList() {
        return farmTurnoDispensaList;
    }

    public void setFarmTurnoDispensaList(List<FarmTurnoDispensa> farmTurnoDispensaList) {
        this.farmTurnoDispensaList = farmTurnoDispensaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurno != null ? pkIdTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTurno)) {
            return false;
        }
        FarmTurno other = (FarmTurno) object;
        if ((this.pkIdTurno == null && other.pkIdTurno != null) || (this.pkIdTurno != null && !this.pkIdTurno.equals(other.pkIdTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTurno[ pkIdTurno=" + pkIdTurno + " ]";
    }
    
}
