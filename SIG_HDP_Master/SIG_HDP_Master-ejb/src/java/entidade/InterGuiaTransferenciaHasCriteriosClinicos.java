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
@Table(name = "inter_guia_transferencia_has_criterios_clinicos", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterGuiaTransferenciaHasCriteriosClinicos.findAll", query = "SELECT i FROM InterGuiaTransferenciaHasCriteriosClinicos i"),
    @NamedQuery(name = "InterGuiaTransferenciaHasCriteriosClinicos.findByPkIdGuiaTransferenciaHasCriteriosClinicos", query = "SELECT i FROM InterGuiaTransferenciaHasCriteriosClinicos i WHERE i.pkIdGuiaTransferenciaHasCriteriosClinicos = :pkIdGuiaTransferenciaHasCriteriosClinicos")})
public class InterGuiaTransferenciaHasCriteriosClinicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_guia_transferencia_has_criterios_clinicos", nullable = false)
    private Integer pkIdGuiaTransferenciaHasCriteriosClinicos;
    @JoinColumn(name = "fk_id_criterio_clinico", referencedColumnName = "pk_id_criterio_clinico", nullable = false)
    @ManyToOne(optional = false)
    private InterCriterioClinico fkIdCriterioClinico;
    @JoinColumn(name = "fk_id_guia_transferencia_doentes", referencedColumnName = "pk_id_guia_transferencia_doentes", nullable = false)
    @ManyToOne(optional = false)
    private InterGuiaTransferenciaDoentes fkIdGuiaTransferenciaDoentes;

    public InterGuiaTransferenciaHasCriteriosClinicos() {
    }

    public InterGuiaTransferenciaHasCriteriosClinicos(Integer pkIdGuiaTransferenciaHasCriteriosClinicos) {
        this.pkIdGuiaTransferenciaHasCriteriosClinicos = pkIdGuiaTransferenciaHasCriteriosClinicos;
    }

    public Integer getPkIdGuiaTransferenciaHasCriteriosClinicos() {
        return pkIdGuiaTransferenciaHasCriteriosClinicos;
    }

    public void setPkIdGuiaTransferenciaHasCriteriosClinicos(Integer pkIdGuiaTransferenciaHasCriteriosClinicos) {
        this.pkIdGuiaTransferenciaHasCriteriosClinicos = pkIdGuiaTransferenciaHasCriteriosClinicos;
    }

    public InterCriterioClinico getFkIdCriterioClinico() {
        return fkIdCriterioClinico;
    }

    public void setFkIdCriterioClinico(InterCriterioClinico fkIdCriterioClinico) {
        this.fkIdCriterioClinico = fkIdCriterioClinico;
    }

    public InterGuiaTransferenciaDoentes getFkIdGuiaTransferenciaDoentes() {
        return fkIdGuiaTransferenciaDoentes;
    }

    public void setFkIdGuiaTransferenciaDoentes(InterGuiaTransferenciaDoentes fkIdGuiaTransferenciaDoentes) {
        this.fkIdGuiaTransferenciaDoentes = fkIdGuiaTransferenciaDoentes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdGuiaTransferenciaHasCriteriosClinicos != null ? pkIdGuiaTransferenciaHasCriteriosClinicos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterGuiaTransferenciaHasCriteriosClinicos)) {
            return false;
        }
        InterGuiaTransferenciaHasCriteriosClinicos other = (InterGuiaTransferenciaHasCriteriosClinicos) object;
        if ((this.pkIdGuiaTransferenciaHasCriteriosClinicos == null && other.pkIdGuiaTransferenciaHasCriteriosClinicos != null) || (this.pkIdGuiaTransferenciaHasCriteriosClinicos != null && !this.pkIdGuiaTransferenciaHasCriteriosClinicos.equals(other.pkIdGuiaTransferenciaHasCriteriosClinicos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterGuiaTransferenciaHasCriteriosClinicos[ pkIdGuiaTransferenciaHasCriteriosClinicos=" + pkIdGuiaTransferenciaHasCriteriosClinicos + " ]";
    }
    
}
