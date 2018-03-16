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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_tipo_reagente", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_tipo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipoReagente.findAll", query = "SELECT d FROM DiagTipoReagente d"),
    @NamedQuery(name = "DiagTipoReagente.findByPkIdTipoReagente", query = "SELECT d FROM DiagTipoReagente d WHERE d.pkIdTipoReagente = :pkIdTipoReagente"),
    @NamedQuery(name = "DiagTipoReagente.findByDescricaoTipo", query = "SELECT d FROM DiagTipoReagente d WHERE d.descricaoTipo = :descricaoTipo")})
public class DiagTipoReagente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_reagente", nullable = false)
    private Integer pkIdTipoReagente;
    @Size(max = 15)
    @Column(name = "descricao_tipo", length = 15)
    private String descricaoTipo;
    @OneToMany(mappedBy = "fkIdTipoReagente")
    private List<DiagReagente> diagReagenteList;

    public DiagTipoReagente() {
    }

    public DiagTipoReagente(Integer pkIdTipoReagente) {
        this.pkIdTipoReagente = pkIdTipoReagente;
    }

    public Integer getPkIdTipoReagente() {
        return pkIdTipoReagente;
    }

    public void setPkIdTipoReagente(Integer pkIdTipoReagente) {
        this.pkIdTipoReagente = pkIdTipoReagente;
    }

    public String getDescricaoTipo() {
        return descricaoTipo;
    }

    public void setDescricaoTipo(String descricaoTipo) {
        this.descricaoTipo = descricaoTipo;
    }

    @XmlTransient
    public List<DiagReagente> getDiagReagenteList() {
        return diagReagenteList;
    }

    public void setDiagReagenteList(List<DiagReagente> diagReagenteList) {
        this.diagReagenteList = diagReagenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoReagente != null ? pkIdTipoReagente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipoReagente)) {
            return false;
        }
        DiagTipoReagente other = (DiagTipoReagente) object;
        if ((this.pkIdTipoReagente == null && other.pkIdTipoReagente != null) || (this.pkIdTipoReagente != null && !this.pkIdTipoReagente.equals(other.pkIdTipoReagente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipoReagente[ pkIdTipoReagente=" + pkIdTipoReagente + " ]";
    }
    
}
