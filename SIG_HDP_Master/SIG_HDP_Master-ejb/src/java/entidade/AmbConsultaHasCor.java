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
@Table(name = "amb_consulta_has_cor", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasCor.findAll", query = "SELECT a FROM AmbConsultaHasCor a"),
    @NamedQuery(name = "AmbConsultaHasCor.findByPkIdConsultaCor", query = "SELECT a FROM AmbConsultaHasCor a WHERE a.pkIdConsultaCor = :pkIdConsultaCor")})
public class AmbConsultaHasCor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta_cor", nullable = false)
    private Long pkIdConsultaCor;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_cor", referencedColumnName = "pk_id_cor")
    @ManyToOne
    private AmbCor fkIdCor;

    public AmbConsultaHasCor() {
    }

    public AmbConsultaHasCor(Long pkIdConsultaCor) {
        this.pkIdConsultaCor = pkIdConsultaCor;
    }

    public Long getPkIdConsultaCor() {
        return pkIdConsultaCor;
    }

    public void setPkIdConsultaCor(Long pkIdConsultaCor) {
        this.pkIdConsultaCor = pkIdConsultaCor;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public AmbCor getFkIdCor() {
        return fkIdCor;
    }

    public void setFkIdCor(AmbCor fkIdCor) {
        this.fkIdCor = fkIdCor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultaCor != null ? pkIdConsultaCor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasCor)) {
            return false;
        }
        AmbConsultaHasCor other = (AmbConsultaHasCor) object;
        if ((this.pkIdConsultaCor == null && other.pkIdConsultaCor != null) || (this.pkIdConsultaCor != null && !this.pkIdConsultaCor.equals(other.pkIdConsultaCor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasCor[ pkIdConsultaCor=" + pkIdConsultaCor + " ]";
    }
    
}
