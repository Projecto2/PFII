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
@Table(name = "grl_fabricante", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlFabricante.findAll", query = "SELECT g FROM GrlFabricante g"),
    @NamedQuery(name = "GrlFabricante.findByPkIdFabricante", query = "SELECT g FROM GrlFabricante g WHERE g.pkIdFabricante = :pkIdFabricante"),
    @NamedQuery(name = "GrlFabricante.findByDescricao", query = "SELECT g FROM GrlFabricante g WHERE g.descricao = :descricao")})
public class GrlFabricante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_fabricante", nullable = false)
    private Integer pkIdFabricante;
    @Size(max = 150)
    @Column(name = "descricao", length = 150)
    private String descricao;
    @OneToMany(mappedBy = "fkIdFabricante")
    private List<FarmLoteProduto> farmLoteProdutoList;
    @JoinColumn(name = "fk_id_pais", referencedColumnName = "pk_id_pais")
    @ManyToOne
    private GrlPais fkIdPais;

    public GrlFabricante() {
    }

    public GrlFabricante(Integer pkIdFabricante) {
        this.pkIdFabricante = pkIdFabricante;
    }

    public Integer getPkIdFabricante() {
        return pkIdFabricante;
    }

    public void setPkIdFabricante(Integer pkIdFabricante) {
        this.pkIdFabricante = pkIdFabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmLoteProduto> getFarmLoteProdutoList() {
        return farmLoteProdutoList;
    }

    public void setFarmLoteProdutoList(List<FarmLoteProduto> farmLoteProdutoList) {
        this.farmLoteProdutoList = farmLoteProdutoList;
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
        hash += (pkIdFabricante != null ? pkIdFabricante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlFabricante)) {
            return false;
        }
        GrlFabricante other = (GrlFabricante) object;
        if ((this.pkIdFabricante == null && other.pkIdFabricante != null) || (this.pkIdFabricante != null && !this.pkIdFabricante.equals(other.pkIdFabricante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlFabricante[ pkIdFabricante=" + pkIdFabricante + " ]";
    }
    
}
