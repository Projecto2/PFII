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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_grau_parentesco", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlGrauParentesco.findAll", query = "SELECT g FROM GrlGrauParentesco g"),
    @NamedQuery(name = "GrlGrauParentesco.findByPkIdGrauParentesco", query = "SELECT g FROM GrlGrauParentesco g WHERE g.pkIdGrauParentesco = :pkIdGrauParentesco"),
    @NamedQuery(name = "GrlGrauParentesco.findByDescricaoGrauParentesco", query = "SELECT g FROM GrlGrauParentesco g WHERE g.descricaoGrauParentesco = :descricaoGrauParentesco")})
public class GrlGrauParentesco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_grau_parentesco", nullable = false)
    private Integer pkIdGrauParentesco;
    @Size(max = 100)
    @Column(name = "descricao_grau_parentesco", length = 100)
    private String descricaoGrauParentesco;
    @OneToMany(mappedBy = "fkIdGrauParentesco")
    private List<AdmsResponsavelPaciente> admsResponsavelPacienteList;

    public GrlGrauParentesco() {
    }

    public GrlGrauParentesco(Integer pkIdGrauParentesco) {
        this.pkIdGrauParentesco = pkIdGrauParentesco;
    }

    public Integer getPkIdGrauParentesco() {
        return pkIdGrauParentesco;
    }

    public void setPkIdGrauParentesco(Integer pkIdGrauParentesco) {
        this.pkIdGrauParentesco = pkIdGrauParentesco;
    }

    public String getDescricaoGrauParentesco() {
        return descricaoGrauParentesco;
    }

    public void setDescricaoGrauParentesco(String descricaoGrauParentesco) {
        this.descricaoGrauParentesco = descricaoGrauParentesco;
    }

    @XmlTransient
    public List<AdmsResponsavelPaciente> getAdmsResponsavelPacienteList() {
        return admsResponsavelPacienteList;
    }

    public void setAdmsResponsavelPacienteList(List<AdmsResponsavelPaciente> admsResponsavelPacienteList) {
        this.admsResponsavelPacienteList = admsResponsavelPacienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdGrauParentesco != null ? pkIdGrauParentesco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlGrauParentesco)) {
            return false;
        }
        GrlGrauParentesco other = (GrlGrauParentesco) object;
        if ((this.pkIdGrauParentesco == null && other.pkIdGrauParentesco != null) || (this.pkIdGrauParentesco != null && !this.pkIdGrauParentesco.equals(other.pkIdGrauParentesco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlGrauParentesco[ pkIdGrauParentesco=" + pkIdGrauParentesco + " ]";
    }
    
}
