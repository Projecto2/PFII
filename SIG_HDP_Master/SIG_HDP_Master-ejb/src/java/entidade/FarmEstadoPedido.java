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
@Table(name = "farm_estado_pedido", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmEstadoPedido.findAll", query = "SELECT f FROM FarmEstadoPedido f"),
    @NamedQuery(name = "FarmEstadoPedido.findByPkIdEstadoPedido", query = "SELECT f FROM FarmEstadoPedido f WHERE f.pkIdEstadoPedido = :pkIdEstadoPedido"),
    @NamedQuery(name = "FarmEstadoPedido.findByDescricao", query = "SELECT f FROM FarmEstadoPedido f WHERE f.descricao = :descricao")})
public class FarmEstadoPedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_pedido", nullable = false)
    private Integer pkIdEstadoPedido;
    @Size(max = 50)
    @Column(name = "descricao", length = 50)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoPedido")
    private List<FarmPedido> farmPedidoList;

    public FarmEstadoPedido() {
    }

    public FarmEstadoPedido(Integer pkIdEstadoPedido) {
        this.pkIdEstadoPedido = pkIdEstadoPedido;
    }

    public Integer getPkIdEstadoPedido() {
        return pkIdEstadoPedido;
    }

    public void setPkIdEstadoPedido(Integer pkIdEstadoPedido) {
        this.pkIdEstadoPedido = pkIdEstadoPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmPedido> getFarmPedidoList() {
        return farmPedidoList;
    }

    public void setFarmPedidoList(List<FarmPedido> farmPedidoList) {
        this.farmPedidoList = farmPedidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoPedido != null ? pkIdEstadoPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmEstadoPedido)) {
            return false;
        }
        FarmEstadoPedido other = (FarmEstadoPedido) object;
        if ((this.pkIdEstadoPedido == null && other.pkIdEstadoPedido != null) || (this.pkIdEstadoPedido != null && !this.pkIdEstadoPedido.equals(other.pkIdEstadoPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmEstadoPedido[ pkIdEstadoPedido=" + pkIdEstadoPedido + " ]";
    }
    
}
