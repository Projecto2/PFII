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
@Table(name = "amb_turgor", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbTurgor.findAll", query = "SELECT a FROM AmbTurgor a"),
    @NamedQuery(name = "AmbTurgor.findByPkIdTurgor", query = "SELECT a FROM AmbTurgor a WHERE a.pkIdTurgor = :pkIdTurgor"),
    @NamedQuery(name = "AmbTurgor.findByDescricao", query = "SELECT a FROM AmbTurgor a WHERE a.descricao = :descricao")})
public class AmbTurgor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_turgor", nullable = false)
    private Integer pkIdTurgor;
    @Size(max = 11)
    @Column(name = "descricao", length = 11)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTurgor")
    private List<AmbConsultaHasTurgorTecido> ambConsultaHasTurgorTecidoList;
    @OneToMany(mappedBy = "fkIdTurgor")
    private List<AmbConsultaHasTurgorPele> ambConsultaHasTurgorPeleList;

    public AmbTurgor() {
    }

    public AmbTurgor(Integer pkIdTurgor) {
        this.pkIdTurgor = pkIdTurgor;
    }

    public Integer getPkIdTurgor() {
        return pkIdTurgor;
    }

    public void setPkIdTurgor(Integer pkIdTurgor) {
        this.pkIdTurgor = pkIdTurgor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsultaHasTurgorTecido> getAmbConsultaHasTurgorTecidoList() {
        return ambConsultaHasTurgorTecidoList;
    }

    public void setAmbConsultaHasTurgorTecidoList(List<AmbConsultaHasTurgorTecido> ambConsultaHasTurgorTecidoList) {
        this.ambConsultaHasTurgorTecidoList = ambConsultaHasTurgorTecidoList;
    }

    @XmlTransient
    public List<AmbConsultaHasTurgorPele> getAmbConsultaHasTurgorPeleList() {
        return ambConsultaHasTurgorPeleList;
    }

    public void setAmbConsultaHasTurgorPeleList(List<AmbConsultaHasTurgorPele> ambConsultaHasTurgorPeleList) {
        this.ambConsultaHasTurgorPeleList = ambConsultaHasTurgorPeleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurgor != null ? pkIdTurgor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbTurgor)) {
            return false;
        }
        AmbTurgor other = (AmbTurgor) object;
        if ((this.pkIdTurgor == null && other.pkIdTurgor != null) || (this.pkIdTurgor != null && !this.pkIdTurgor.equals(other.pkIdTurgor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbTurgor[ pkIdTurgor=" + pkIdTurgor + " ]";
    }
    
}
