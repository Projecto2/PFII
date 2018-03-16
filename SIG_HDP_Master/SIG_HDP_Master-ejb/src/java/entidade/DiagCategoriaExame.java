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
@Table(name = "diag_categoria_exame", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_categoria"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagCategoriaExame.findAll", query = "SELECT d FROM DiagCategoriaExame d"),
    @NamedQuery(name = "DiagCategoriaExame.findByPkIdCategoria", query = "SELECT d FROM DiagCategoriaExame d WHERE d.pkIdCategoria = :pkIdCategoria"),
    @NamedQuery(name = "DiagCategoriaExame.findByDescricaoCategoria", query = "SELECT d FROM DiagCategoriaExame d WHERE d.descricaoCategoria = :descricaoCategoria")})
public class DiagCategoriaExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_categoria", nullable = false)
    private Integer pkIdCategoria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao_categoria", nullable = false, length = 45)
    private String descricaoCategoria;
    @JoinColumn(name = "fk_id_sector", referencedColumnName = "pk_id_sector_exame", nullable = false)
    @ManyToOne(optional = false)
    private DiagSectorExame fkIdSector;
    @OneToMany(mappedBy = "fkIdCategoria")
    private List<DiagSubcategoriaExame> diagSubcategoriaExameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCategoriaExame")
    private List<DiagExame> diagExameList;

    public DiagCategoriaExame() {
    }

    public DiagCategoriaExame(Integer pkIdCategoria) {
        this.pkIdCategoria = pkIdCategoria;
    }

    public DiagCategoriaExame(Integer pkIdCategoria, String descricaoCategoria) {
        this.pkIdCategoria = pkIdCategoria;
        this.descricaoCategoria = descricaoCategoria;
    }

    public Integer getPkIdCategoria() {
        return pkIdCategoria;
    }

    public void setPkIdCategoria(Integer pkIdCategoria) {
        this.pkIdCategoria = pkIdCategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }

    public DiagSectorExame getFkIdSector() {
        return fkIdSector;
    }

    public void setFkIdSector(DiagSectorExame fkIdSector) {
        this.fkIdSector = fkIdSector;
    }

    @XmlTransient
    public List<DiagSubcategoriaExame> getDiagSubcategoriaExameList() {
        return diagSubcategoriaExameList;
    }

    public void setDiagSubcategoriaExameList(List<DiagSubcategoriaExame> diagSubcategoriaExameList) {
        this.diagSubcategoriaExameList = diagSubcategoriaExameList;
    }

    @XmlTransient
    public List<DiagExame> getDiagExameList() {
        return diagExameList;
    }

    public void setDiagExameList(List<DiagExame> diagExameList) {
        this.diagExameList = diagExameList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCategoria != null ? pkIdCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagCategoriaExame)) {
            return false;
        }
        DiagCategoriaExame other = (DiagCategoriaExame) object;
        if ((this.pkIdCategoria == null && other.pkIdCategoria != null) || (this.pkIdCategoria != null && !this.pkIdCategoria.equals(other.pkIdCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagCategoriaExame[ pkIdCategoria=" + pkIdCategoria + " ]";
    }
    
}
