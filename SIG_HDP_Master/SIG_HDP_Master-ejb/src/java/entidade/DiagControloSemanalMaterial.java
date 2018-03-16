/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_controlo_semanal_material", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagControloSemanalMaterial.findAll", query = "SELECT d FROM DiagControloSemanalMaterial d"),
    @NamedQuery(name = "DiagControloSemanalMaterial.findByPkIdControloSemanalMaterial", query = "SELECT d FROM DiagControloSemanalMaterial d WHERE d.pkIdControloSemanalMaterial = :pkIdControloSemanalMaterial"),
    @NamedQuery(name = "DiagControloSemanalMaterial.findByData", query = "SELECT d FROM DiagControloSemanalMaterial d WHERE d.data = :data")})
public class DiagControloSemanalMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_controlo_semanal_material", nullable = false)
    private Integer pkIdControloSemanalMaterial;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @OneToMany(mappedBy = "fkIdControloSemanalMaterial")
    private List<DiagControloSemanalMaterialHasMaterial> diagControloSemanalMaterialHasMaterialList;

    public DiagControloSemanalMaterial() {
    }

    public DiagControloSemanalMaterial(Integer pkIdControloSemanalMaterial) {
        this.pkIdControloSemanalMaterial = pkIdControloSemanalMaterial;
    }

    public Integer getPkIdControloSemanalMaterial() {
        return pkIdControloSemanalMaterial;
    }

    public void setPkIdControloSemanalMaterial(Integer pkIdControloSemanalMaterial) {
        this.pkIdControloSemanalMaterial = pkIdControloSemanalMaterial;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @XmlTransient
    public List<DiagControloSemanalMaterialHasMaterial> getDiagControloSemanalMaterialHasMaterialList() {
        return diagControloSemanalMaterialHasMaterialList;
    }

    public void setDiagControloSemanalMaterialHasMaterialList(List<DiagControloSemanalMaterialHasMaterial> diagControloSemanalMaterialHasMaterialList) {
        this.diagControloSemanalMaterialHasMaterialList = diagControloSemanalMaterialHasMaterialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdControloSemanalMaterial != null ? pkIdControloSemanalMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagControloSemanalMaterial)) {
            return false;
        }
        DiagControloSemanalMaterial other = (DiagControloSemanalMaterial) object;
        if ((this.pkIdControloSemanalMaterial == null && other.pkIdControloSemanalMaterial != null) || (this.pkIdControloSemanalMaterial != null && !this.pkIdControloSemanalMaterial.equals(other.pkIdControloSemanalMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagControloSemanalMaterial[ pkIdControloSemanalMaterial=" + pkIdControloSemanalMaterial + " ]";
    }
    
}
