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
@Table(name = "amb_consulta_has_espessura", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasEspessura.findAll", query = "SELECT a FROM AmbConsultaHasEspessura a"),
    @NamedQuery(name = "AmbConsultaHasEspessura.findByPkIdConsultaEspessura", query = "SELECT a FROM AmbConsultaHasEspessura a WHERE a.pkIdConsultaEspessura = :pkIdConsultaEspessura")})
public class AmbConsultaHasEspessura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta_espessura", nullable = false)
    private Long pkIdConsultaEspessura;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_espessura", referencedColumnName = "pk_id_espessura")
    @ManyToOne
    private AmbEspessura fkIdEspessura;

    public AmbConsultaHasEspessura() {
    }

    public AmbConsultaHasEspessura(Long pkIdConsultaEspessura) {
        this.pkIdConsultaEspessura = pkIdConsultaEspessura;
    }

    public Long getPkIdConsultaEspessura() {
        return pkIdConsultaEspessura;
    }

    public void setPkIdConsultaEspessura(Long pkIdConsultaEspessura) {
        this.pkIdConsultaEspessura = pkIdConsultaEspessura;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public AmbEspessura getFkIdEspessura() {
        return fkIdEspessura;
    }

    public void setFkIdEspessura(AmbEspessura fkIdEspessura) {
        this.fkIdEspessura = fkIdEspessura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultaEspessura != null ? pkIdConsultaEspessura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasEspessura)) {
            return false;
        }
        AmbConsultaHasEspessura other = (AmbConsultaHasEspessura) object;
        if ((this.pkIdConsultaEspessura == null && other.pkIdConsultaEspessura != null) || (this.pkIdConsultaEspessura != null && !this.pkIdConsultaEspessura.equals(other.pkIdConsultaEspessura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasEspessura[ pkIdConsultaEspessura=" + pkIdConsultaEspessura + " ]";
    }
    
}
