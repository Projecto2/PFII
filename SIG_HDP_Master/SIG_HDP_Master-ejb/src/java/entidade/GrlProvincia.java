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
@Table(name = "grl_provincia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlProvincia.findAll", query = "SELECT g FROM GrlProvincia g"),
    @NamedQuery(name = "GrlProvincia.findByPkIdProvincia", query = "SELECT g FROM GrlProvincia g WHERE g.pkIdProvincia = :pkIdProvincia"),
    @NamedQuery(name = "GrlProvincia.findByNomeProvincia", query = "SELECT g FROM GrlProvincia g WHERE g.nomeProvincia = :nomeProvincia")})
public class GrlProvincia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_provincia", nullable = false)
    private Integer pkIdProvincia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_provincia", nullable = false, length = 50)
    private String nomeProvincia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProvincia")
    private List<GrlMunicipio> grlMunicipioList;
    @JoinColumn(name = "fk_id_pais", referencedColumnName = "pk_id_pais", nullable = false)
    @ManyToOne(optional = false)
    private GrlPais fkIdPais;

    public GrlProvincia() {
    }

    public GrlProvincia(Integer pkIdProvincia) {
        this.pkIdProvincia = pkIdProvincia;
    }

    public GrlProvincia(Integer pkIdProvincia, String nomeProvincia) {
        this.pkIdProvincia = pkIdProvincia;
        this.nomeProvincia = nomeProvincia;
    }

    public Integer getPkIdProvincia() {
        return pkIdProvincia;
    }

    public void setPkIdProvincia(Integer pkIdProvincia) {
        this.pkIdProvincia = pkIdProvincia;
    }

    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    @XmlTransient
    public List<GrlMunicipio> getGrlMunicipioList() {
        return grlMunicipioList;
    }

    public void setGrlMunicipioList(List<GrlMunicipio> grlMunicipioList) {
        this.grlMunicipioList = grlMunicipioList;
    }

    public GrlPais getFkIdPais() {
        return fkIdPais;
    }

    public void setFkIdPais(GrlPais fkIdPais) {
        this.fkIdPais = fkIdPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProvincia != null ? pkIdProvincia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlProvincia)) {
            return false;
        }
        GrlProvincia other = (GrlProvincia) object;
        if ((this.pkIdProvincia == null && other.pkIdProvincia != null) || (this.pkIdProvincia != null && !this.pkIdProvincia.equals(other.pkIdProvincia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlProvincia[ pkIdProvincia=" + pkIdProvincia + " ]";
    }
    
}
