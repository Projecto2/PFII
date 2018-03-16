/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_pedido_has_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmPedidoHasProduto.findAll", query = "SELECT f FROM FarmPedidoHasProduto f"),
    @NamedQuery(name = "FarmPedidoHasProduto.findByPkIdPedidoHasProduto", query = "SELECT f FROM FarmPedidoHasProduto f WHERE f.pkIdPedidoHasProduto = :pkIdPedidoHasProduto"),
    @NamedQuery(name = "FarmPedidoHasProduto.findByQuantidade", query = "SELECT f FROM FarmPedidoHasProduto f WHERE f.quantidade = :quantidade")})
public class FarmPedidoHasProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pedido_has_produto", nullable = false)
    private Integer pkIdPedidoHasProduto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    @JoinColumn(name = "fk_id_pedido", referencedColumnName = "pk_id_pedido", nullable = false)
    @ManyToOne(optional = false)
    private FarmPedido fkIdPedido;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;

    public FarmPedidoHasProduto() {
    }

    public FarmPedidoHasProduto(Integer pkIdPedidoHasProduto) {
        this.pkIdPedidoHasProduto = pkIdPedidoHasProduto;
    }

    public FarmPedidoHasProduto(Integer pkIdPedidoHasProduto, int quantidade) {
        this.pkIdPedidoHasProduto = pkIdPedidoHasProduto;
        this.quantidade = quantidade;
    }

    public Integer getPkIdPedidoHasProduto() {
        return pkIdPedidoHasProduto;
    }

    public void setPkIdPedidoHasProduto(Integer pkIdPedidoHasProduto) {
        this.pkIdPedidoHasProduto = pkIdPedidoHasProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public FarmPedido getFkIdPedido() {
        return fkIdPedido;
    }

    public void setFkIdPedido(FarmPedido fkIdPedido) {
        this.fkIdPedido = fkIdPedido;
    }

    public FarmProduto getFkIdProduto() {
        return fkIdProduto;
    }

    public void setFkIdProduto(FarmProduto fkIdProduto) {
        this.fkIdProduto = fkIdProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPedidoHasProduto != null ? pkIdPedidoHasProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmPedidoHasProduto)) {
            return false;
        }
        FarmPedidoHasProduto other = (FarmPedidoHasProduto) object;
        if ((this.pkIdPedidoHasProduto == null && other.pkIdPedidoHasProduto != null) || (this.pkIdPedidoHasProduto != null && !this.pkIdPedidoHasProduto.equals(other.pkIdPedidoHasProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmPedidoHasProduto[ pkIdPedidoHasProduto=" + pkIdPedidoHasProduto + " ]";
    }
    
}
