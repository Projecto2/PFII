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
@Table(name = "farm_tipo_unidade_medida", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTipoUnidadeMedida.findAll", query = "SELECT f FROM FarmTipoUnidadeMedida f"),
    @NamedQuery(name = "FarmTipoUnidadeMedida.findByPkIdTipoUnidadeMedida", query = "SELECT f FROM FarmTipoUnidadeMedida f WHERE f.pkIdTipoUnidadeMedida = :pkIdTipoUnidadeMedida"),
    @NamedQuery(name = "FarmTipoUnidadeMedida.findByDescricao", query = "SELECT f FROM FarmTipoUnidadeMedida f WHERE f.descricao = :descricao")})
public class FarmTipoUnidadeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_unidade_medida", nullable = false)
    private Integer pkIdTipoUnidadeMedida;
    @Size(max = 250)
    @Column(name = "descricao", length = 250)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTipoUnidadeMedida")
    private List<FarmUnidadeMedida> farmUnidadeMedidaList;

    public FarmTipoUnidadeMedida() {
    }

    public FarmTipoUnidadeMedida(Integer pkIdTipoUnidadeMedida) {
        this.pkIdTipoUnidadeMedida = pkIdTipoUnidadeMedida;
    }

    public Integer getPkIdTipoUnidadeMedida() {
        return pkIdTipoUnidadeMedida;
    }

    public void setPkIdTipoUnidadeMedida(Integer pkIdTipoUnidadeMedida) {
        this.pkIdTipoUnidadeMedida = pkIdTipoUnidadeMedida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmUnidadeMedida> getFarmUnidadeMedidaList() {
        return farmUnidadeMedidaList;
    }

    public void setFarmUnidadeMedidaList(List<FarmUnidadeMedida> farmUnidadeMedidaList) {
        this.farmUnidadeMedidaList = farmUnidadeMedidaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoUnidadeMedida != null ? pkIdTipoUnidadeMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTipoUnidadeMedida)) {
            return false;
        }
        FarmTipoUnidadeMedida other = (FarmTipoUnidadeMedida) object;
        if ((this.pkIdTipoUnidadeMedida == null && other.pkIdTipoUnidadeMedida != null) || (this.pkIdTipoUnidadeMedida != null && !this.pkIdTipoUnidadeMedida.equals(other.pkIdTipoUnidadeMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTipoUnidadeMedida[ pkIdTipoUnidadeMedida=" + pkIdTipoUnidadeMedida + " ]";
    }
    
}
