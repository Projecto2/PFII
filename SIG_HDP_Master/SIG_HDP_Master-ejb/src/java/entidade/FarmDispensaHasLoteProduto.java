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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_dispensa_has_lote_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmDispensaHasLoteProduto.findAll", query = "SELECT f FROM FarmDispensaHasLoteProduto f"),
    @NamedQuery(name = "FarmDispensaHasLoteProduto.findByQuantidade", query = "SELECT f FROM FarmDispensaHasLoteProduto f WHERE f.quantidade = :quantidade"),
    @NamedQuery(name = "FarmDispensaHasLoteProduto.findByPkIdDispensaHasLoteProduto", query = "SELECT f FROM FarmDispensaHasLoteProduto f WHERE f.pkIdDispensaHasLoteProduto = :pkIdDispensaHasLoteProduto")})
public class FarmDispensaHasLoteProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_dispensa_has_lote_produto", nullable = false)
    private Integer pkIdDispensaHasLoteProduto;
    @JoinColumn(name = "fk_id_dispensa", referencedColumnName = "pk_id_dispensa")
    @ManyToOne
    private FarmDispensa fkIdDispensa;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmLoteProduto fkIdLoteProduto;

    public FarmDispensaHasLoteProduto() {
    }

    public FarmDispensaHasLoteProduto(Integer pkIdDispensaHasLoteProduto) {
        this.pkIdDispensaHasLoteProduto = pkIdDispensaHasLoteProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getPkIdDispensaHasLoteProduto() {
        return pkIdDispensaHasLoteProduto;
    }

    public void setPkIdDispensaHasLoteProduto(Integer pkIdDispensaHasLoteProduto) {
        this.pkIdDispensaHasLoteProduto = pkIdDispensaHasLoteProduto;
    }

    public FarmDispensa getFkIdDispensa() {
        return fkIdDispensa;
    }

    public void setFkIdDispensa(FarmDispensa fkIdDispensa) {
        this.fkIdDispensa = fkIdDispensa;
    }

    public FarmLoteProduto getFkIdLoteProduto() {
        return fkIdLoteProduto;
    }

    public void setFkIdLoteProduto(FarmLoteProduto fkIdLoteProduto) {
        this.fkIdLoteProduto = fkIdLoteProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDispensaHasLoteProduto != null ? pkIdDispensaHasLoteProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmDispensaHasLoteProduto)) {
            return false;
        }
        FarmDispensaHasLoteProduto other = (FarmDispensaHasLoteProduto) object;
        if ((this.pkIdDispensaHasLoteProduto == null && other.pkIdDispensaHasLoteProduto != null) || (this.pkIdDispensaHasLoteProduto != null && !this.pkIdDispensaHasLoteProduto.equals(other.pkIdDispensaHasLoteProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmDispensaHasLoteProduto[ pkIdDispensaHasLoteProduto=" + pkIdDispensaHasLoteProduto + " ]";
    }
    
}
