/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_movimento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_pedido"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmMovimento.findAll", query = "SELECT f FROM FarmMovimento f"),
    @NamedQuery(name = "FarmMovimento.findByPkIdMovimento", query = "SELECT f FROM FarmMovimento f WHERE f.pkIdMovimento = :pkIdMovimento"),
    @NamedQuery(name = "FarmMovimento.findByDataMovimento", query = "SELECT f FROM FarmMovimento f WHERE f.dataMovimento = :dataMovimento")})
public class FarmMovimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_movimento", nullable = false)
    private Integer pkIdMovimento;
    @Column(name = "data_movimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMovimento;
    @JoinColumn(name = "fk_id_pedido", referencedColumnName = "pk_id_pedido", nullable = false)
    @OneToOne(optional = false)
    private FarmPedido fkIdPedido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMovimento")
    private List<FarmMovimentoHasProduto> farmMovimentoHasProdutoList;

    public FarmMovimento() {
    }

    public FarmMovimento(Integer pkIdMovimento) {
        this.pkIdMovimento = pkIdMovimento;
    }

    public Integer getPkIdMovimento() {
        return pkIdMovimento;
    }

    public void setPkIdMovimento(Integer pkIdMovimento) {
        this.pkIdMovimento = pkIdMovimento;
    }

    public Date getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(Date dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public FarmPedido getFkIdPedido() {
        return fkIdPedido;
    }

    public void setFkIdPedido(FarmPedido fkIdPedido) {
        this.fkIdPedido = fkIdPedido;
    }

    @XmlTransient
    public List<FarmMovimentoHasProduto> getFarmMovimentoHasProdutoList() {
        return farmMovimentoHasProdutoList;
    }

    public void setFarmMovimentoHasProdutoList(List<FarmMovimentoHasProduto> farmMovimentoHasProdutoList) {
        this.farmMovimentoHasProdutoList = farmMovimentoHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMovimento != null ? pkIdMovimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmMovimento)) {
            return false;
        }
        FarmMovimento other = (FarmMovimento) object;
        if ((this.pkIdMovimento == null && other.pkIdMovimento != null) || (this.pkIdMovimento != null && !this.pkIdMovimento.equals(other.pkIdMovimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmMovimento[ pkIdMovimento=" + pkIdMovimento + " ]";
    }
    
}
