/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_criterio_clinico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterCriterioClinico.findAll", query = "SELECT i FROM InterCriterioClinico i"),
    @NamedQuery(name = "InterCriterioClinico.findByPkIdCriterioClinico", query = "SELECT i FROM InterCriterioClinico i WHERE i.pkIdCriterioClinico = :pkIdCriterioClinico"),
    @NamedQuery(name = "InterCriterioClinico.findByDescricao", query = "SELECT i FROM InterCriterioClinico i WHERE i.descricao = :descricao")})
public class InterCriterioClinico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_criterio_clinico", nullable = false)
    private Integer pkIdCriterioClinico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCriterioClinico")
    private List<InterGuiaTransferenciaHasCriteriosClinicos> interGuiaTransferenciaHasCriteriosClinicosList;

    public InterCriterioClinico() {
    }

    public InterCriterioClinico(Integer pkIdCriterioClinico) {
        this.pkIdCriterioClinico = pkIdCriterioClinico;
    }

    public InterCriterioClinico(Integer pkIdCriterioClinico, String descricao) {
        this.pkIdCriterioClinico = pkIdCriterioClinico;
        this.descricao = descricao;
    }

    public Integer getPkIdCriterioClinico() {
        return pkIdCriterioClinico;
    }

    public void setPkIdCriterioClinico(Integer pkIdCriterioClinico) {
        this.pkIdCriterioClinico = pkIdCriterioClinico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaHasCriteriosClinicos> getInterGuiaTransferenciaHasCriteriosClinicosList() {
        return interGuiaTransferenciaHasCriteriosClinicosList;
    }

    public void setInterGuiaTransferenciaHasCriteriosClinicosList(List<InterGuiaTransferenciaHasCriteriosClinicos> interGuiaTransferenciaHasCriteriosClinicosList) {
        this.interGuiaTransferenciaHasCriteriosClinicosList = interGuiaTransferenciaHasCriteriosClinicosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCriterioClinico != null ? pkIdCriterioClinico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterCriterioClinico)) {
            return false;
        }
        InterCriterioClinico other = (InterCriterioClinico) object;
        if ((this.pkIdCriterioClinico == null && other.pkIdCriterioClinico != null) || (this.pkIdCriterioClinico != null && !this.pkIdCriterioClinico.equals(other.pkIdCriterioClinico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterCriterioClinico[ pkIdCriterioClinico=" + pkIdCriterioClinico + " ]";
    }
    
}
