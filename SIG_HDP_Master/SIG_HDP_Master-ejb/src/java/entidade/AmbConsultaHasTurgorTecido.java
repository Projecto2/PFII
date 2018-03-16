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
@Table(name = "amb_consulta_has_turgor_tecido", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasTurgorTecido.findAll", query = "SELECT a FROM AmbConsultaHasTurgorTecido a"),
    @NamedQuery(name = "AmbConsultaHasTurgorTecido.findByPkIdTurgorTecido", query = "SELECT a FROM AmbConsultaHasTurgorTecido a WHERE a.pkIdTurgorTecido = :pkIdTurgorTecido")})
public class AmbConsultaHasTurgorTecido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_turgor_tecido", nullable = false)
    private Long pkIdTurgorTecido;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_turgor", referencedColumnName = "pk_id_turgor")
    @ManyToOne
    private AmbTurgor fkIdTurgor;

    public AmbConsultaHasTurgorTecido() {
    }

    public AmbConsultaHasTurgorTecido(Long pkIdTurgorTecido) {
        this.pkIdTurgorTecido = pkIdTurgorTecido;
    }

    public Long getPkIdTurgorTecido() {
        return pkIdTurgorTecido;
    }

    public void setPkIdTurgorTecido(Long pkIdTurgorTecido) {
        this.pkIdTurgorTecido = pkIdTurgorTecido;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public AmbTurgor getFkIdTurgor() {
        return fkIdTurgor;
    }

    public void setFkIdTurgor(AmbTurgor fkIdTurgor) {
        this.fkIdTurgor = fkIdTurgor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurgorTecido != null ? pkIdTurgorTecido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasTurgorTecido)) {
            return false;
        }
        AmbConsultaHasTurgorTecido other = (AmbConsultaHasTurgorTecido) object;
        if ((this.pkIdTurgorTecido == null && other.pkIdTurgorTecido != null) || (this.pkIdTurgorTecido != null && !this.pkIdTurgorTecido.equals(other.pkIdTurgorTecido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasTurgorTecido[ pkIdTurgorTecido=" + pkIdTurgorTecido + " ]";
    }
    
}
