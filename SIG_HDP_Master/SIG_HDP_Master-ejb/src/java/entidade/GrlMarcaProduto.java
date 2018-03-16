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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_marca_produto", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlMarcaProduto.findAll", query = "SELECT g FROM GrlMarcaProduto g"),
    @NamedQuery(name = "GrlMarcaProduto.findByPkIdMarca", query = "SELECT g FROM GrlMarcaProduto g WHERE g.pkIdMarca = :pkIdMarca"),
    @NamedQuery(name = "GrlMarcaProduto.findByDescricao", query = "SELECT g FROM GrlMarcaProduto g WHERE g.descricao = :descricao")})
public class GrlMarcaProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_marca", nullable = false)
    private Integer pkIdMarca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(mappedBy = "fkIdMarca")
    private List<FarmLoteProduto> farmLoteProdutoList;

    public GrlMarcaProduto() {
    }

    public GrlMarcaProduto(Integer pkIdMarca) {
        this.pkIdMarca = pkIdMarca;
    }

    public GrlMarcaProduto(Integer pkIdMarca, String descricao) {
        this.pkIdMarca = pkIdMarca;
        this.descricao = descricao;
    }

    public Integer getPkIdMarca() {
        return pkIdMarca;
    }

    public void setPkIdMarca(Integer pkIdMarca) {
        this.pkIdMarca = pkIdMarca;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMarca != null ? pkIdMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlMarcaProduto)) {
            return false;
        }
        GrlMarcaProduto other = (GrlMarcaProduto) object;
        if ((this.pkIdMarca == null && other.pkIdMarca != null) || (this.pkIdMarca != null && !this.pkIdMarca.equals(other.pkIdMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlMarcaProduto[ pkIdMarca=" + pkIdMarca + " ]";
    }
    
}
