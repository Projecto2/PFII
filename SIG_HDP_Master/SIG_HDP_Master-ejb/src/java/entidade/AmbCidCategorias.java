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
@Table(name = "amb_cid_categorias", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidCategorias.findAll", query = "SELECT a FROM AmbCidCategorias a"),
    @NamedQuery(name = "AmbCidCategorias.findByPkIdCategorias", query = "SELECT a FROM AmbCidCategorias a WHERE a.pkIdCategorias = :pkIdCategorias"),
    @NamedQuery(name = "AmbCidCategorias.findByNome", query = "SELECT a FROM AmbCidCategorias a WHERE a.nome = :nome")})
public class AmbCidCategorias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "pk_id_categorias", nullable = false, length = 5)
    private String pkIdCategorias;
    @Size(max = 768)
    @Column(name = "nome", length = 768)
    private String nome;
    @JoinColumn(name = "fk_id_agrupamentos", referencedColumnName = "pk_id_agrupamentos")
    @ManyToOne
    private AmbCidAgrupamentos fkIdAgrupamentos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCategorias")
    private List<AmbCidSubcategorias> ambCidSubcategoriasList;

    public AmbCidCategorias() {
    }

    public AmbCidCategorias(String pkIdCategorias) {
        this.pkIdCategorias = pkIdCategorias;
    }

    public String getPkIdCategorias() {
        return pkIdCategorias;
    }

    public void setPkIdCategorias(String pkIdCategorias) {
        this.pkIdCategorias = pkIdCategorias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AmbCidAgrupamentos getFkIdAgrupamentos() {
        return fkIdAgrupamentos;
    }

    public void setFkIdAgrupamentos(AmbCidAgrupamentos fkIdAgrupamentos) {
        this.fkIdAgrupamentos = fkIdAgrupamentos;
    }

    @XmlTransient
    public List<AmbCidSubcategorias> getAmbCidSubcategoriasList() {
        return ambCidSubcategoriasList;
    }

    public void setAmbCidSubcategoriasList(List<AmbCidSubcategorias> ambCidSubcategoriasList) {
        this.ambCidSubcategoriasList = ambCidSubcategoriasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCategorias != null ? pkIdCategorias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidCategorias)) {
            return false;
        }
        AmbCidCategorias other = (AmbCidCategorias) object;
        if ((this.pkIdCategorias == null && other.pkIdCategorias != null) || (this.pkIdCategorias != null && !this.pkIdCategorias.equals(other.pkIdCategorias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidCategorias[ pkIdCategorias=" + pkIdCategorias + " ]";
    }
    
}
