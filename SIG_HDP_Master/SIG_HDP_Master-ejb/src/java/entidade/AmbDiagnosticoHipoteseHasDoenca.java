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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_diagnostico_hipotese_has_doenca", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbDiagnosticoHipoteseHasDoenca.findAll", query = "SELECT a FROM AmbDiagnosticoHipoteseHasDoenca a"),
    @NamedQuery(name = "AmbDiagnosticoHipoteseHasDoenca.findByPkIdDiagnosticoHipoteseDoenca", query = "SELECT a FROM AmbDiagnosticoHipoteseHasDoenca a WHERE a.pkIdDiagnosticoHipoteseDoenca = :pkIdDiagnosticoHipoteseDoenca"),
    @NamedQuery(name = "AmbDiagnosticoHipoteseHasDoenca.findByOutros", query = "SELECT a FROM AmbDiagnosticoHipoteseHasDoenca a WHERE a.outros = :outros")})
public class AmbDiagnosticoHipoteseHasDoenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_diagnostico_hipotese_doenca", nullable = false)
    private Long pkIdDiagnosticoHipoteseDoenca;
    @Size(max = 512)
    @Column(name = "outros", length = 512)
    private String outros;
    @JoinColumn(name = "fk_id_subcategorias", referencedColumnName = "pk_id_subcategorias")
    @ManyToOne
    private AmbCidSubcategorias fkIdSubcategorias;
    @JoinColumn(name = "fk_id_diagnostico_hipotese", referencedColumnName = "pk_id_diagnostico_hipotese")
    @ManyToOne
    private AmbDiagnosticoHipotese fkIdDiagnosticoHipotese;

    public AmbDiagnosticoHipoteseHasDoenca() {
    }

    public AmbDiagnosticoHipoteseHasDoenca(Long pkIdDiagnosticoHipoteseDoenca) {
        this.pkIdDiagnosticoHipoteseDoenca = pkIdDiagnosticoHipoteseDoenca;
    }

    public Long getPkIdDiagnosticoHipoteseDoenca() {
        return pkIdDiagnosticoHipoteseDoenca;
    }

    public void setPkIdDiagnosticoHipoteseDoenca(Long pkIdDiagnosticoHipoteseDoenca) {
        this.pkIdDiagnosticoHipoteseDoenca = pkIdDiagnosticoHipoteseDoenca;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    public AmbCidSubcategorias getFkIdSubcategorias() {
        return fkIdSubcategorias;
    }

    public void setFkIdSubcategorias(AmbCidSubcategorias fkIdSubcategorias) {
        this.fkIdSubcategorias = fkIdSubcategorias;
    }

    public AmbDiagnosticoHipotese getFkIdDiagnosticoHipotese() {
        return fkIdDiagnosticoHipotese;
    }

    public void setFkIdDiagnosticoHipotese(AmbDiagnosticoHipotese fkIdDiagnosticoHipotese) {
        this.fkIdDiagnosticoHipotese = fkIdDiagnosticoHipotese;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDiagnosticoHipoteseDoenca != null ? pkIdDiagnosticoHipoteseDoenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbDiagnosticoHipoteseHasDoenca)) {
            return false;
        }
        AmbDiagnosticoHipoteseHasDoenca other = (AmbDiagnosticoHipoteseHasDoenca) object;
        if ((this.pkIdDiagnosticoHipoteseDoenca == null && other.pkIdDiagnosticoHipoteseDoenca != null) || (this.pkIdDiagnosticoHipoteseDoenca != null && !this.pkIdDiagnosticoHipoteseDoenca.equals(other.pkIdDiagnosticoHipoteseDoenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbDiagnosticoHipoteseHasDoenca[ pkIdDiagnosticoHipoteseDoenca=" + pkIdDiagnosticoHipoteseDoenca + " ]";
    }
    
}
