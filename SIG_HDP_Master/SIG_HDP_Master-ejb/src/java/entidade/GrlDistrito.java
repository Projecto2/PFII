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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "grl_distrito", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlDistrito.findAll", query = "SELECT g FROM GrlDistrito g"),
    @NamedQuery(name = "GrlDistrito.findByPkIdDistrito", query = "SELECT g FROM GrlDistrito g WHERE g.pkIdDistrito = :pkIdDistrito"),
    @NamedQuery(name = "GrlDistrito.findByDescricao", query = "SELECT g FROM GrlDistrito g WHERE g.descricao = :descricao")})
public class GrlDistrito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_distrito", nullable = false)
    private Integer pkIdDistrito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdDistrito")
    private List<GrlEndereco> grlEnderecoList;
    @JoinColumn(name = "fk_id_municipio", referencedColumnName = "pk_id_municipio", nullable = false)
    @ManyToOne(optional = false)
    private GrlMunicipio fkIdMunicipio;

    public GrlDistrito() {
    }

    public GrlDistrito(Integer pkIdDistrito) {
        this.pkIdDistrito = pkIdDistrito;
    }

    public GrlDistrito(Integer pkIdDistrito, String descricao) {
        this.pkIdDistrito = pkIdDistrito;
        this.descricao = descricao;
    }

    public Integer getPkIdDistrito() {
        return pkIdDistrito;
    }

    public void setPkIdDistrito(Integer pkIdDistrito) {
        this.pkIdDistrito = pkIdDistrito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<GrlEndereco> getGrlEnderecoList() {
        return grlEnderecoList;
    }

    public void setGrlEnderecoList(List<GrlEndereco> grlEnderecoList) {
        this.grlEnderecoList = grlEnderecoList;
    }

    public GrlMunicipio getFkIdMunicipio() {
        return fkIdMunicipio;
    }

    public void setFkIdMunicipio(GrlMunicipio fkIdMunicipio) {
        this.fkIdMunicipio = fkIdMunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDistrito != null ? pkIdDistrito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlDistrito)) {
            return false;
        }
        GrlDistrito other = (GrlDistrito) object;
        if ((this.pkIdDistrito == null && other.pkIdDistrito != null) || (this.pkIdDistrito != null && !this.pkIdDistrito.equals(other.pkIdDistrito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlDistrito[ pkIdDistrito=" + pkIdDistrito + " ]";
    }
    
}
