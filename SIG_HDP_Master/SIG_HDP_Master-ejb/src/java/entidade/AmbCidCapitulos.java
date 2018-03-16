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
@Table(name = "amb_cid_capitulos", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidCapitulos.findAll", query = "SELECT a FROM AmbCidCapitulos a"),
    @NamedQuery(name = "AmbCidCapitulos.findByPkIdCapitulos", query = "SELECT a FROM AmbCidCapitulos a WHERE a.pkIdCapitulos = :pkIdCapitulos"),
    @NamedQuery(name = "AmbCidCapitulos.findByNome", query = "SELECT a FROM AmbCidCapitulos a WHERE a.nome = :nome")})
public class AmbCidCapitulos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "pk_id_capitulos", nullable = false, length = 9)
    private String pkIdCapitulos;
    @Size(max = 107)
    @Column(name = "nome", length = 107)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCapitulos")
    private List<AmbCidAgrupamentos> ambCidAgrupamentosList;

    public AmbCidCapitulos() {
    }

    public AmbCidCapitulos(String pkIdCapitulos) {
        this.pkIdCapitulos = pkIdCapitulos;
    }

    public String getPkIdCapitulos() {
        return pkIdCapitulos;
    }

    public void setPkIdCapitulos(String pkIdCapitulos) {
        this.pkIdCapitulos = pkIdCapitulos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<AmbCidAgrupamentos> getAmbCidAgrupamentosList() {
        return ambCidAgrupamentosList;
    }

    public void setAmbCidAgrupamentosList(List<AmbCidAgrupamentos> ambCidAgrupamentosList) {
        this.ambCidAgrupamentosList = ambCidAgrupamentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCapitulos != null ? pkIdCapitulos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidCapitulos)) {
            return false;
        }
        AmbCidCapitulos other = (AmbCidCapitulos) object;
        if ((this.pkIdCapitulos == null && other.pkIdCapitulos != null) || (this.pkIdCapitulos != null && !this.pkIdCapitulos.equals(other.pkIdCapitulos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidCapitulos[ pkIdCapitulos=" + pkIdCapitulos + " ]";
    }
    
}
