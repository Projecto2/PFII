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
@Table(name = "amb_textura", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbTextura.findAll", query = "SELECT a FROM AmbTextura a"),
    @NamedQuery(name = "AmbTextura.findByPkIdTextura", query = "SELECT a FROM AmbTextura a WHERE a.pkIdTextura = :pkIdTextura"),
    @NamedQuery(name = "AmbTextura.findByDescricao", query = "SELECT a FROM AmbTextura a WHERE a.descricao = :descricao")})
public class AmbTextura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_textura", nullable = false)
    private Integer pkIdTextura;
    @Size(max = 19)
    @Column(name = "descricao", length = 19)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTextura")
    private List<AmbConsultaHasTextura> ambConsultaHasTexturaList;

    public AmbTextura() {
    }

    public AmbTextura(Integer pkIdTextura) {
        this.pkIdTextura = pkIdTextura;
    }

    public Integer getPkIdTextura() {
        return pkIdTextura;
    }

    public void setPkIdTextura(Integer pkIdTextura) {
        this.pkIdTextura = pkIdTextura;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsultaHasTextura> getAmbConsultaHasTexturaList() {
        return ambConsultaHasTexturaList;
    }

    public void setAmbConsultaHasTexturaList(List<AmbConsultaHasTextura> ambConsultaHasTexturaList) {
        this.ambConsultaHasTexturaList = ambConsultaHasTexturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTextura != null ? pkIdTextura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbTextura)) {
            return false;
        }
        AmbTextura other = (AmbTextura) object;
        if ((this.pkIdTextura == null && other.pkIdTextura != null) || (this.pkIdTextura != null && !this.pkIdTextura.equals(other.pkIdTextura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbTextura[ pkIdTextura=" + pkIdTextura + " ]";
    }
    
}
