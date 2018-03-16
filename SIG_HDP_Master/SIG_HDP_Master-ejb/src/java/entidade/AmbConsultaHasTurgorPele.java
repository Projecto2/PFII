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
@Table(name = "amb_consulta_has_turgor_pele", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasTurgorPele.findAll", query = "SELECT a FROM AmbConsultaHasTurgorPele a"),
    @NamedQuery(name = "AmbConsultaHasTurgorPele.findByPkIdConsultaTurgor", query = "SELECT a FROM AmbConsultaHasTurgorPele a WHERE a.pkIdConsultaTurgor = :pkIdConsultaTurgor")})
public class AmbConsultaHasTurgorPele implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta_turgor", nullable = false)
    private Long pkIdConsultaTurgor;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_turgor", referencedColumnName = "pk_id_turgor")
    @ManyToOne
    private AmbTurgor fkIdTurgor;

    public AmbConsultaHasTurgorPele() {
    }

    public AmbConsultaHasTurgorPele(Long pkIdConsultaTurgor) {
        this.pkIdConsultaTurgor = pkIdConsultaTurgor;
    }

    public Long getPkIdConsultaTurgor() {
        return pkIdConsultaTurgor;
    }

    public void setPkIdConsultaTurgor(Long pkIdConsultaTurgor) {
        this.pkIdConsultaTurgor = pkIdConsultaTurgor;
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
        hash += (pkIdConsultaTurgor != null ? pkIdConsultaTurgor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasTurgorPele)) {
            return false;
        }
        AmbConsultaHasTurgorPele other = (AmbConsultaHasTurgorPele) object;
        if ((this.pkIdConsultaTurgor == null && other.pkIdConsultaTurgor != null) || (this.pkIdConsultaTurgor != null && !this.pkIdConsultaTurgor.equals(other.pkIdConsultaTurgor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasTurgorPele[ pkIdConsultaTurgor=" + pkIdConsultaTurgor + " ]";
    }
    
}
