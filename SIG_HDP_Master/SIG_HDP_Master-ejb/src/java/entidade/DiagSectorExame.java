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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_sector_exame", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_sector"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagSectorExame.findAll", query = "SELECT d FROM DiagSectorExame d"),
    @NamedQuery(name = "DiagSectorExame.findByPkIdSectorExame", query = "SELECT d FROM DiagSectorExame d WHERE d.pkIdSectorExame = :pkIdSectorExame"),
    @NamedQuery(name = "DiagSectorExame.findByDescricaoSector", query = "SELECT d FROM DiagSectorExame d WHERE d.descricaoSector = :descricaoSector")})
public class DiagSectorExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_sector_exame", nullable = false)
    private Integer pkIdSectorExame;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao_sector", nullable = false, length = 45)
    private String descricaoSector;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdSector")
    private List<DiagCategoriaExame> diagCategoriaExameList;

    public DiagSectorExame() {
    }

    public DiagSectorExame(Integer pkIdSectorExame) {
        this.pkIdSectorExame = pkIdSectorExame;
    }

    public DiagSectorExame(Integer pkIdSectorExame, String descricaoSector) {
        this.pkIdSectorExame = pkIdSectorExame;
        this.descricaoSector = descricaoSector;
    }

    public Integer getPkIdSectorExame() {
        return pkIdSectorExame;
    }

    public void setPkIdSectorExame(Integer pkIdSectorExame) {
        this.pkIdSectorExame = pkIdSectorExame;
    }

    public String getDescricaoSector() {
        return descricaoSector;
    }

    public void setDescricaoSector(String descricaoSector) {
        this.descricaoSector = descricaoSector;
    }

    @XmlTransient
    public List<DiagCategoriaExame> getDiagCategoriaExameList() {
        return diagCategoriaExameList;
    }

    public void setDiagCategoriaExameList(List<DiagCategoriaExame> diagCategoriaExameList) {
        this.diagCategoriaExameList = diagCategoriaExameList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSectorExame != null ? pkIdSectorExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagSectorExame)) {
            return false;
        }
        DiagSectorExame other = (DiagSectorExame) object;
        if ((this.pkIdSectorExame == null && other.pkIdSectorExame != null) || (this.pkIdSectorExame != null && !this.pkIdSectorExame.equals(other.pkIdSectorExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagSectorExame[ pkIdSectorExame=" + pkIdSectorExame + " ]";
    }
    
}
