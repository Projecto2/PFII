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
@Table(name = "grl_comuna", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlComuna.findAll", query = "SELECT g FROM GrlComuna g"),
    @NamedQuery(name = "GrlComuna.findByPkIdComuna", query = "SELECT g FROM GrlComuna g WHERE g.pkIdComuna = :pkIdComuna"),
    @NamedQuery(name = "GrlComuna.findByDescricaoComuna", query = "SELECT g FROM GrlComuna g WHERE g.descricaoComuna = :descricaoComuna")})
public class GrlComuna implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id__comuna", nullable = false)
    private Integer pkIdComuna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao_comuna", nullable = false, length = 100)
    private String descricaoComuna;
    @OneToMany(mappedBy = "fkIdComuna")
    private List<GrlEndereco> grlEnderecoList;
    @JoinColumn(name = "fk_id_municipio", referencedColumnName = "pk_id_municipio")
    @ManyToOne
    private GrlMunicipio fkIdMunicipio;

    public GrlComuna() {
    }

    public GrlComuna(Integer pkIdComuna) {
        this.pkIdComuna = pkIdComuna;
    }

    public GrlComuna(Integer pkIdComuna, String descricaoComuna) {
        this.pkIdComuna = pkIdComuna;
        this.descricaoComuna = descricaoComuna;
    }

    public Integer getPkIdComuna() {
        return pkIdComuna;
    }

    public void setPkIdComuna(Integer pkIdComuna) {
        this.pkIdComuna = pkIdComuna;
    }

    public String getDescricaoComuna() {
        return descricaoComuna;
    }

    public void setDescricaoComuna(String descricaoComuna) {
        this.descricaoComuna = descricaoComuna;
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
        hash += (pkIdComuna != null ? pkIdComuna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlComuna)) {
            return false;
        }
        GrlComuna other = (GrlComuna) object;
        if ((this.pkIdComuna == null && other.pkIdComuna != null) || (this.pkIdComuna != null && !this.pkIdComuna.equals(other.pkIdComuna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlComuna[ pkIdComuna=" + pkIdComuna + " ]";
    }
    
}
