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
@Table(name = "amb_diagnostico_has_doenca", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbDiagnosticoHasDoenca.findAll", query = "SELECT a FROM AmbDiagnosticoHasDoenca a"),
    @NamedQuery(name = "AmbDiagnosticoHasDoenca.findByPkIdDiagnosticoDoenca", query = "SELECT a FROM AmbDiagnosticoHasDoenca a WHERE a.pkIdDiagnosticoDoenca = :pkIdDiagnosticoDoenca"),
    @NamedQuery(name = "AmbDiagnosticoHasDoenca.findByOutros", query = "SELECT a FROM AmbDiagnosticoHasDoenca a WHERE a.outros = :outros")})
public class AmbDiagnosticoHasDoenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_diagnostico_doenca", nullable = false)
    private Long pkIdDiagnosticoDoenca;
    @Size(max = 512)
    @Column(name = "outros", length = 512)
    private String outros;
    @JoinColumn(name = "fk_id_subcategorias", referencedColumnName = "pk_id_subcategorias")
    @ManyToOne
    private AmbCidSubcategorias fkIdSubcategorias;
    @JoinColumn(name = "fk_id_diagnostico", referencedColumnName = "pk_id_diagnostico")
    @ManyToOne
    private AmbDiagnostico fkIdDiagnostico;

    public AmbDiagnosticoHasDoenca() {
    }

    public AmbDiagnosticoHasDoenca(Long pkIdDiagnosticoDoenca) {
        this.pkIdDiagnosticoDoenca = pkIdDiagnosticoDoenca;
    }

    public Long getPkIdDiagnosticoDoenca() {
        return pkIdDiagnosticoDoenca;
    }

    public void setPkIdDiagnosticoDoenca(Long pkIdDiagnosticoDoenca) {
        this.pkIdDiagnosticoDoenca = pkIdDiagnosticoDoenca;
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

    public AmbDiagnostico getFkIdDiagnostico() {
        return fkIdDiagnostico;
    }

    public void setFkIdDiagnostico(AmbDiagnostico fkIdDiagnostico) {
        this.fkIdDiagnostico = fkIdDiagnostico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDiagnosticoDoenca != null ? pkIdDiagnosticoDoenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbDiagnosticoHasDoenca)) {
            return false;
        }
        AmbDiagnosticoHasDoenca other = (AmbDiagnosticoHasDoenca) object;
        if ((this.pkIdDiagnosticoDoenca == null && other.pkIdDiagnosticoDoenca != null) || (this.pkIdDiagnosticoDoenca != null && !this.pkIdDiagnosticoDoenca.equals(other.pkIdDiagnosticoDoenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbDiagnosticoHasDoenca[ pkIdDiagnosticoDoenca=" + pkIdDiagnosticoDoenca + " ]";
    }
    
}
