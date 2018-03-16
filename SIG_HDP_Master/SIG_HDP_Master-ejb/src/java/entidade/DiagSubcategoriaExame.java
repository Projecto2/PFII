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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_subcategoria_exame", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagSubcategoriaExame.findAll", query = "SELECT d FROM DiagSubcategoriaExame d"),
    @NamedQuery(name = "DiagSubcategoriaExame.findByPkIdSubcategoriaExame", query = "SELECT d FROM DiagSubcategoriaExame d WHERE d.pkIdSubcategoriaExame = :pkIdSubcategoriaExame"),
    @NamedQuery(name = "DiagSubcategoriaExame.findByDescricaoSubcategoria", query = "SELECT d FROM DiagSubcategoriaExame d WHERE d.descricaoSubcategoria = :descricaoSubcategoria")})
public class DiagSubcategoriaExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_subcategoria_exame", nullable = false)
    private Integer pkIdSubcategoriaExame;
    @Size(max = 45)
    @Column(name = "descricao_subcategoria", length = 45)
    private String descricaoSubcategoria;
    @JoinColumn(name = "fk_id_categoria", referencedColumnName = "pk_id_categoria")
    @ManyToOne
    private DiagCategoriaExame fkIdCategoria;
    @OneToMany(mappedBy = "fkIdSubcategoriaExame")
    private List<DiagExame> diagExameList;

    public DiagSubcategoriaExame() {
    }

    public DiagSubcategoriaExame(Integer pkIdSubcategoriaExame) {
        this.pkIdSubcategoriaExame = pkIdSubcategoriaExame;
    }

    public Integer getPkIdSubcategoriaExame() {
        return pkIdSubcategoriaExame;
    }

    public void setPkIdSubcategoriaExame(Integer pkIdSubcategoriaExame) {
        this.pkIdSubcategoriaExame = pkIdSubcategoriaExame;
    }

    public String getDescricaoSubcategoria() {
        return descricaoSubcategoria;
    }

    public void setDescricaoSubcategoria(String descricaoSubcategoria) {
        this.descricaoSubcategoria = descricaoSubcategoria;
    }

    public DiagCategoriaExame getFkIdCategoria() {
        return fkIdCategoria;
    }

    public void setFkIdCategoria(DiagCategoriaExame fkIdCategoria) {
        this.fkIdCategoria = fkIdCategoria;
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
        hash += (pkIdSubcategoriaExame != null ? pkIdSubcategoriaExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagSubcategoriaExame)) {
            return false;
        }
        DiagSubcategoriaExame other = (DiagSubcategoriaExame) object;
        if ((this.pkIdSubcategoriaExame == null && other.pkIdSubcategoriaExame != null) || (this.pkIdSubcategoriaExame != null && !this.pkIdSubcategoriaExame.equals(other.pkIdSubcategoriaExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagSubcategoriaExame[ pkIdSubcategoriaExame=" + pkIdSubcategoriaExame + " ]";
    }
    
}
