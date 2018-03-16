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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_movimento_has_produto", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_movimento", "fk_id_lote_produto"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmMovimentoHasProduto.findAll", query = "SELECT f FROM FarmMovimentoHasProduto f"),
    @NamedQuery(name = "FarmMovimentoHasProduto.findByPkIdMovimentoHasLoteProduto", query = "SELECT f FROM FarmMovimentoHasProduto f WHERE f.pkIdMovimentoHasLoteProduto = :pkIdMovimentoHasLoteProduto"),
    @NamedQuery(name = "FarmMovimentoHasProduto.findByQuantidadeMovimentada", query = "SELECT f FROM FarmMovimentoHasProduto f WHERE f.quantidadeMovimentada = :quantidadeMovimentada")})
public class FarmMovimentoHasProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_movimento_has_lote_produto", nullable = false)
    private Integer pkIdMovimentoHasLoteProduto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade_movimentada", nullable = false)
    private int quantidadeMovimentada;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmLoteProduto fkIdLoteProduto;
    @JoinColumn(name = "fk_id_movimento", referencedColumnName = "pk_id_movimento", nullable = false)
    @ManyToOne(optional = false)
    private FarmMovimento fkIdMovimento;
    @JoinColumn(name = "fk_id_tipo_quantidade", referencedColumnName = "pk_id_tipo_quantidade")
    @ManyToOne
    private FarmTipoQuantidade fkIdTipoQuantidade;

    public FarmMovimentoHasProduto() {
    }

    public FarmMovimentoHasProduto(Integer pkIdMovimentoHasLoteProduto) {
        this.pkIdMovimentoHasLoteProduto = pkIdMovimentoHasLoteProduto;
    }

    public FarmMovimentoHasProduto(Integer pkIdMovimentoHasLoteProduto, int quantidadeMovimentada) {
        this.pkIdMovimentoHasLoteProduto = pkIdMovimentoHasLoteProduto;
        this.quantidadeMovimentada = quantidadeMovimentada;
    }

    public Integer getPkIdMovimentoHasLoteProduto() {
        return pkIdMovimentoHasLoteProduto;
    }

    public void setPkIdMovimentoHasLoteProduto(Integer pkIdMovimentoHasLoteProduto) {
        this.pkIdMovimentoHasLoteProduto = pkIdMovimentoHasLoteProduto;
    }

    public int getQuantidadeMovimentada() {
        return quantidadeMovimentada;
    }

    public void setQuantidadeMovimentada(int quantidadeMovimentada) {
        this.quantidadeMovimentada = quantidadeMovimentada;
    }

    public FarmLoteProduto getFkIdLoteProduto() {
        return fkIdLoteProduto;
    }

    public void setFkIdLoteProduto(FarmLoteProduto fkIdLoteProduto) {
        this.fkIdLoteProduto = fkIdLoteProduto;
    }

    public FarmMovimento getFkIdMovimento() {
        return fkIdMovimento;
    }

    public void setFkIdMovimento(FarmMovimento fkIdMovimento) {
        this.fkIdMovimento = fkIdMovimento;
    }

    public FarmTipoQuantidade getFkIdTipoQuantidade() {
        return fkIdTipoQuantidade;
    }

    public void setFkIdTipoQuantidade(FarmTipoQuantidade fkIdTipoQuantidade) {
        this.fkIdTipoQuantidade = fkIdTipoQuantidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMovimentoHasLoteProduto != null ? pkIdMovimentoHasLoteProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmMovimentoHasProduto)) {
            return false;
        }
        FarmMovimentoHasProduto other = (FarmMovimentoHasProduto) object;
        if ((this.pkIdMovimentoHasLoteProduto == null && other.pkIdMovimentoHasLoteProduto != null) || (this.pkIdMovimentoHasLoteProduto != null && !this.pkIdMovimentoHasLoteProduto.equals(other.pkIdMovimentoHasLoteProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmMovimentoHasProduto[ pkIdMovimentoHasLoteProduto=" + pkIdMovimentoHasLoteProduto + " ]";
    }
    
}
