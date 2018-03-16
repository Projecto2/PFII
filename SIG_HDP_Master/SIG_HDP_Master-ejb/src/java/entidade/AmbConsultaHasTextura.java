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
@Table(name = "amb_consulta_has_textura", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasTextura.findAll", query = "SELECT a FROM AmbConsultaHasTextura a"),
    @NamedQuery(name = "AmbConsultaHasTextura.findByPkIdConsultaTextura", query = "SELECT a FROM AmbConsultaHasTextura a WHERE a.pkIdConsultaTextura = :pkIdConsultaTextura")})
public class AmbConsultaHasTextura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta_textura", nullable = false)
    private Long pkIdConsultaTextura;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_textura", referencedColumnName = "pk_id_textura")
    @ManyToOne
    private AmbTextura fkIdTextura;

    public AmbConsultaHasTextura() {
    }

    public AmbConsultaHasTextura(Long pkIdConsultaTextura) {
        this.pkIdConsultaTextura = pkIdConsultaTextura;
    }

    public Long getPkIdConsultaTextura() {
        return pkIdConsultaTextura;
    }

    public void setPkIdConsultaTextura(Long pkIdConsultaTextura) {
        this.pkIdConsultaTextura = pkIdConsultaTextura;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public AmbTextura getFkIdTextura() {
        return fkIdTextura;
    }

    public void setFkIdTextura(AmbTextura fkIdTextura) {
        this.fkIdTextura = fkIdTextura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultaTextura != null ? pkIdConsultaTextura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasTextura)) {
            return false;
        }
        AmbConsultaHasTextura other = (AmbConsultaHasTextura) object;
        if ((this.pkIdConsultaTextura == null && other.pkIdConsultaTextura != null) || (this.pkIdConsultaTextura != null && !this.pkIdConsultaTextura.equals(other.pkIdConsultaTextura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasTextura[ pkIdConsultaTextura=" + pkIdConsultaTextura + " ]";
    }
    
}
