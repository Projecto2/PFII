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
@Table(name = "amb_espessura", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbEspessura.findAll", query = "SELECT a FROM AmbEspessura a"),
    @NamedQuery(name = "AmbEspessura.findByPkIdEspessura", query = "SELECT a FROM AmbEspessura a WHERE a.pkIdEspessura = :pkIdEspessura"),
    @NamedQuery(name = "AmbEspessura.findByDescricao", query = "SELECT a FROM AmbEspessura a WHERE a.descricao = :descricao")})
public class AmbEspessura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_espessura", nullable = false)
    private Integer pkIdEspessura;
    @Size(max = 19)
    @Column(name = "descricao", length = 19)
    private String descricao;
    @OneToMany(mappedBy = "fkIdEspessura")
    private List<AmbConsultaHasEspessura> ambConsultaHasEspessuraList;

    public AmbEspessura() {
    }

    public AmbEspessura(Integer pkIdEspessura) {
        this.pkIdEspessura = pkIdEspessura;
    }

    public Integer getPkIdEspessura() {
        return pkIdEspessura;
    }

    public void setPkIdEspessura(Integer pkIdEspessura) {
        this.pkIdEspessura = pkIdEspessura;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsultaHasEspessura> getAmbConsultaHasEspessuraList() {
        return ambConsultaHasEspessuraList;
    }

    public void setAmbConsultaHasEspessuraList(List<AmbConsultaHasEspessura> ambConsultaHasEspessuraList) {
        this.ambConsultaHasEspessuraList = ambConsultaHasEspessuraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEspessura != null ? pkIdEspessura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbEspessura)) {
            return false;
        }
        AmbEspessura other = (AmbEspessura) object;
        if ((this.pkIdEspessura == null && other.pkIdEspessura != null) || (this.pkIdEspessura != null && !this.pkIdEspessura.equals(other.pkIdEspessura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbEspessura[ pkIdEspessura=" + pkIdEspessura + " ]";
    }
    
}
