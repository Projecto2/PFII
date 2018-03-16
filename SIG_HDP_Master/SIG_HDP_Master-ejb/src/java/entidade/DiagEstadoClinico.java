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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_estado_clinico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagEstadoClinico.findAll", query = "SELECT d FROM DiagEstadoClinico d"),
    @NamedQuery(name = "DiagEstadoClinico.findByPkIdEstadoClinico", query = "SELECT d FROM DiagEstadoClinico d WHERE d.pkIdEstadoClinico = :pkIdEstadoClinico"),
    @NamedQuery(name = "DiagEstadoClinico.findByDescricaoEstadoClinico", query = "SELECT d FROM DiagEstadoClinico d WHERE d.descricaoEstadoClinico = :descricaoEstadoClinico")})
public class DiagEstadoClinico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estado_clinico", nullable = false)
    private Integer pkIdEstadoClinico;
    @Size(max = 20)
    @Column(name = "descricao_estado_clinico", length = 20)
    private String descricaoEstadoClinico;
    @OneToMany(mappedBy = "fkIdEstadoClinico")
    private List<DiagExameCandidato> diagExameCandidatoList;

    public DiagEstadoClinico() {
    }

    public DiagEstadoClinico(Integer pkIdEstadoClinico) {
        this.pkIdEstadoClinico = pkIdEstadoClinico;
    }

    public Integer getPkIdEstadoClinico() {
        return pkIdEstadoClinico;
    }

    public void setPkIdEstadoClinico(Integer pkIdEstadoClinico) {
        this.pkIdEstadoClinico = pkIdEstadoClinico;
    }

    public String getDescricaoEstadoClinico() {
        return descricaoEstadoClinico;
    }

    public void setDescricaoEstadoClinico(String descricaoEstadoClinico) {
        this.descricaoEstadoClinico = descricaoEstadoClinico;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList() {
        return diagExameCandidatoList;
    }

    public void setDiagExameCandidatoList(List<DiagExameCandidato> diagExameCandidatoList) {
        this.diagExameCandidatoList = diagExameCandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoClinico != null ? pkIdEstadoClinico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagEstadoClinico)) {
            return false;
        }
        DiagEstadoClinico other = (DiagEstadoClinico) object;
        if ((this.pkIdEstadoClinico == null && other.pkIdEstadoClinico != null) || (this.pkIdEstadoClinico != null && !this.pkIdEstadoClinico.equals(other.pkIdEstadoClinico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagEstadoClinico[ pkIdEstadoClinico=" + pkIdEstadoClinico + " ]";
    }
    
}
