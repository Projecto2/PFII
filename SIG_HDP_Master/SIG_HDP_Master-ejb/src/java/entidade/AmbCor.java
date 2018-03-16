/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "amb_cor", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCor.findAll", query = "SELECT a FROM AmbCor a"),
    @NamedQuery(name = "AmbCor.findByPkIdCor", query = "SELECT a FROM AmbCor a WHERE a.pkIdCor = :pkIdCor"),
    @NamedQuery(name = "AmbCor.findByDescricao", query = "SELECT a FROM AmbCor a WHERE a.descricao = :descricao")})
public class AmbCor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_cor", nullable = false)
    private Integer pkIdCor;
    @Size(max = 13)
    @Column(name = "descricao", length = 13)
    private String descricao;
    @OneToMany(mappedBy = "fkIdCor")
    private List<AmbConsultaHasCor> ambConsultaHasCorList;

    public AmbCor() {
    }

    public AmbCor(Integer pkIdCor) {
        this.pkIdCor = pkIdCor;
    }

    public Integer getPkIdCor() {
        return pkIdCor;
    }

    public void setPkIdCor(Integer pkIdCor) {
        this.pkIdCor = pkIdCor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsultaHasCor> getAmbConsultaHasCorList() {
        return ambConsultaHasCorList;
    }

    public void setAmbConsultaHasCorList(List<AmbConsultaHasCor> ambConsultaHasCorList) {
        this.ambConsultaHasCorList = ambConsultaHasCorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCor != null ? pkIdCor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCor)) {
            return false;
        }
        AmbCor other = (AmbCor) object;
        if ((this.pkIdCor == null && other.pkIdCor != null) || (this.pkIdCor != null && !this.pkIdCor.equals(other.pkIdCor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCor[ pkIdCor=" + pkIdCor + " ]";
    }
    
}
