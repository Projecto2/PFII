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
@Table(name = "amb_cid_agrupamentos", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidAgrupamentos.findAll", query = "SELECT a FROM AmbCidAgrupamentos a"),
    @NamedQuery(name = "AmbCidAgrupamentos.findByPkIdAgrupamentos", query = "SELECT a FROM AmbCidAgrupamentos a WHERE a.pkIdAgrupamentos = :pkIdAgrupamentos"),
    @NamedQuery(name = "AmbCidAgrupamentos.findByNome", query = "SELECT a FROM AmbCidAgrupamentos a WHERE a.nome = :nome")})
public class AmbCidAgrupamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "pk_id_agrupamentos", nullable = false, length = 9)
    private String pkIdAgrupamentos;
    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;
    @JoinColumn(name = "fk_id_capitulos", referencedColumnName = "pk_id_capitulos", nullable = false)
    @ManyToOne(optional = false)
    private AmbCidCapitulos fkIdCapitulos;
    @OneToMany(mappedBy = "fkIdAgrupamentos")
    private List<AmbCidCategorias> ambCidCategoriasList;

    public AmbCidAgrupamentos() {
    }

    public AmbCidAgrupamentos(String pkIdAgrupamentos) {
        this.pkIdAgrupamentos = pkIdAgrupamentos;
    }

    public String getPkIdAgrupamentos() {
        return pkIdAgrupamentos;
    }

    public void setPkIdAgrupamentos(String pkIdAgrupamentos) {
        this.pkIdAgrupamentos = pkIdAgrupamentos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AmbCidCapitulos getFkIdCapitulos() {
        return fkIdCapitulos;
    }

    public void setFkIdCapitulos(AmbCidCapitulos fkIdCapitulos) {
        this.fkIdCapitulos = fkIdCapitulos;
    }

    @XmlTransient
    public List<AmbCidCategorias> getAmbCidCategoriasList() {
        return ambCidCategoriasList;
    }

    public void setAmbCidCategoriasList(List<AmbCidCategorias> ambCidCategoriasList) {
        this.ambCidCategoriasList = ambCidCategoriasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAgrupamentos != null ? pkIdAgrupamentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidAgrupamentos)) {
            return false;
        }
        AmbCidAgrupamentos other = (AmbCidAgrupamentos) object;
        if ((this.pkIdAgrupamentos == null && other.pkIdAgrupamentos != null) || (this.pkIdAgrupamentos != null && !this.pkIdAgrupamentos.equals(other.pkIdAgrupamentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidAgrupamentos[ pkIdAgrupamentos=" + pkIdAgrupamentos + " ]";
    }
    
}
