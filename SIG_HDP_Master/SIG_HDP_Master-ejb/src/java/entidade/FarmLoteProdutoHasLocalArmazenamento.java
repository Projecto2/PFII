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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_lote_produto_has_local_armazenamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmLoteProdutoHasLocalArmazenamento.findAll", query = "SELECT f FROM FarmLoteProdutoHasLocalArmazenamento f"),
    @NamedQuery(name = "FarmLoteProdutoHasLocalArmazenamento.findByPkIdLoteProdutoHasLocalArmazenamento", query = "SELECT f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = :pkIdLoteProdutoHasLocalArmazenamento"),
    @NamedQuery(name = "FarmLoteProdutoHasLocalArmazenamento.findByQuantidadeStock", query = "SELECT f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.quantidadeStock = :quantidadeStock"),
    @NamedQuery(name = "FarmLoteProdutoHasLocalArmazenamento.findByQuantidadeMinimaPermitida", query = "SELECT f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.quantidadeMinimaPermitida = :quantidadeMinimaPermitida"),
    @NamedQuery(name = "FarmLoteProdutoHasLocalArmazenamento.findByPosicao", query = "SELECT f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.posicao = :posicao")})
public class FarmLoteProdutoHasLocalArmazenamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_lote_produto_has_local_armazenamento", nullable = false)
    private Integer pkIdLoteProdutoHasLocalArmazenamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade_stock", nullable = false)
    private int quantidadeStock;
    @Column(name = "quantidade_minima_permitida")
    private Integer quantidadeMinimaPermitida;
    @Size(max = 20)
    @Column(name = "posicao", length = 20)
    private String posicao;
    @JoinColumn(name = "fk_id_local_armazenamento", referencedColumnName = "pk_id_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmLocalArmazenamento fkIdLocalArmazenamento;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmLoteProduto fkIdLoteProduto;

    public FarmLoteProdutoHasLocalArmazenamento() {
    }

    public FarmLoteProdutoHasLocalArmazenamento(Integer pkIdLoteProdutoHasLocalArmazenamento) {
        this.pkIdLoteProdutoHasLocalArmazenamento = pkIdLoteProdutoHasLocalArmazenamento;
    }

    public FarmLoteProdutoHasLocalArmazenamento(Integer pkIdLoteProdutoHasLocalArmazenamento, int quantidadeStock) {
        this.pkIdLoteProdutoHasLocalArmazenamento = pkIdLoteProdutoHasLocalArmazenamento;
        this.quantidadeStock = quantidadeStock;
    }

    public Integer getPkIdLoteProdutoHasLocalArmazenamento() {
        return pkIdLoteProdutoHasLocalArmazenamento;
    }

    public void setPkIdLoteProdutoHasLocalArmazenamento(Integer pkIdLoteProdutoHasLocalArmazenamento) {
        this.pkIdLoteProdutoHasLocalArmazenamento = pkIdLoteProdutoHasLocalArmazenamento;
    }

    public int getQuantidadeStock() {
        return quantidadeStock;
    }

    public void setQuantidadeStock(int quantidadeStock) {
        this.quantidadeStock = quantidadeStock;
    }

    public Integer getQuantidadeMinimaPermitida() {
        return quantidadeMinimaPermitida;
    }

    public void setQuantidadeMinimaPermitida(Integer quantidadeMinimaPermitida) {
        this.quantidadeMinimaPermitida = quantidadeMinimaPermitida;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public FarmLocalArmazenamento getFkIdLocalArmazenamento() {
        return fkIdLocalArmazenamento;
    }

    public void setFkIdLocalArmazenamento(FarmLocalArmazenamento fkIdLocalArmazenamento) {
        this.fkIdLocalArmazenamento = fkIdLocalArmazenamento;
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
        hash += (pkIdLoteProdutoHasLocalArmazenamento != null ? pkIdLoteProdutoHasLocalArmazenamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmLoteProdutoHasLocalArmazenamento)) {
            return false;
        }
        FarmLoteProdutoHasLocalArmazenamento other = (FarmLoteProdutoHasLocalArmazenamento) object;
        if ((this.pkIdLoteProdutoHasLocalArmazenamento == null && other.pkIdLoteProdutoHasLocalArmazenamento != null) || (this.pkIdLoteProdutoHasLocalArmazenamento != null && !this.pkIdLoteProdutoHasLocalArmazenamento.equals(other.pkIdLoteProdutoHasLocalArmazenamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmLoteProdutoHasLocalArmazenamento[ pkIdLoteProdutoHasLocalArmazenamento=" + pkIdLoteProdutoHasLocalArmazenamento + " ]";
    }
    
}
