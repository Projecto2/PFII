/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_controlo_semanal_material_has_material", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagControloSemanalMaterialHasMaterial.findAll", query = "SELECT d FROM DiagControloSemanalMaterialHasMaterial d"),
    @NamedQuery(name = "DiagControloSemanalMaterialHasMaterial.findByPkIdControloSemanalMaterialHasMaterial", query = "SELECT d FROM DiagControloSemanalMaterialHasMaterial d WHERE d.pkIdControloSemanalMaterialHasMaterial = :pkIdControloSemanalMaterialHasMaterial"),
    @NamedQuery(name = "DiagControloSemanalMaterialHasMaterial.findByQuantidade", query = "SELECT d FROM DiagControloSemanalMaterialHasMaterial d WHERE d.quantidade = :quantidade")})
public class DiagControloSemanalMaterialHasMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_controlo_semanal_material_has_material", nullable = false)
    private Integer pkIdControloSemanalMaterialHasMaterial;
    @Column(name = "quantidade")
    private Integer quantidade;
    @JoinColumn(name = "fk_id_controlo_semanal_material", referencedColumnName = "pk_id_controlo_semanal_material")
    @ManyToOne
    private DiagControloSemanalMaterial fkIdControloSemanalMaterial;
    @JoinColumn(name = "fk_id_material", referencedColumnName = "pk_id_produto")
    @ManyToOne
    private FarmProduto fkIdMaterial;

    public DiagControloSemanalMaterialHasMaterial() {
    }

    public DiagControloSemanalMaterialHasMaterial(Integer pkIdControloSemanalMaterialHasMaterial) {
        this.pkIdControloSemanalMaterialHasMaterial = pkIdControloSemanalMaterialHasMaterial;
    }

    public Integer getPkIdControloSemanalMaterialHasMaterial() {
        return pkIdControloSemanalMaterialHasMaterial;
    }

    public void setPkIdControloSemanalMaterialHasMaterial(Integer pkIdControloSemanalMaterialHasMaterial) {
        this.pkIdControloSemanalMaterialHasMaterial = pkIdControloSemanalMaterialHasMaterial;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public DiagControloSemanalMaterial getFkIdControloSemanalMaterial() {
        return fkIdControloSemanalMaterial;
    }

    public void setFkIdControloSemanalMaterial(DiagControloSemanalMaterial fkIdControloSemanalMaterial) {
        this.fkIdControloSemanalMaterial = fkIdControloSemanalMaterial;
    }

    public FarmProduto getFkIdMaterial() {
        return fkIdMaterial;
    }

    public void setFkIdMaterial(FarmProduto fkIdMaterial) {
        this.fkIdMaterial = fkIdMaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdControloSemanalMaterialHasMaterial != null ? pkIdControloSemanalMaterialHasMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagControloSemanalMaterialHasMaterial)) {
            return false;
        }
        DiagControloSemanalMaterialHasMaterial other = (DiagControloSemanalMaterialHasMaterial) object;
        if ((this.pkIdControloSemanalMaterialHasMaterial == null && other.pkIdControloSemanalMaterialHasMaterial != null) || (this.pkIdControloSemanalMaterialHasMaterial != null && !this.pkIdControloSemanalMaterialHasMaterial.equals(other.pkIdControloSemanalMaterialHasMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagControloSemanalMaterialHasMaterial[ pkIdControloSemanalMaterialHasMaterial=" + pkIdControloSemanalMaterialHasMaterial + " ]";
    }
    
}
