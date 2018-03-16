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
@Table(name = "farm_fornecimento_has_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmFornecimentoHasProduto.findAll", query = "SELECT f FROM FarmFornecimentoHasProduto f"),
    @NamedQuery(name = "FarmFornecimentoHasProduto.findByCustoPorUnidade", query = "SELECT f FROM FarmFornecimentoHasProduto f WHERE f.custoPorUnidade = :custoPorUnidade"),
    @NamedQuery(name = "FarmFornecimentoHasProduto.findByQuantidade", query = "SELECT f FROM FarmFornecimentoHasProduto f WHERE f.quantidade = :quantidade"),
    @NamedQuery(name = "FarmFornecimentoHasProduto.findByCustoTotal", query = "SELECT f FROM FarmFornecimentoHasProduto f WHERE f.custoTotal = :custoTotal"),
    @NamedQuery(name = "FarmFornecimentoHasProduto.findByPkIdFornecimentoHasProduto", query = "SELECT f FROM FarmFornecimentoHasProduto f WHERE f.pkIdFornecimentoHasProduto = :pkIdFornecimentoHasProduto")})
public class FarmFornecimentoHasProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "custo_por_unidade", nullable = false)
    private double custoPorUnidade;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "custo_total", precision = 17, scale = 17)
    private Double custoTotal;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_fornecimento_has_produto", nullable = false)
    private Integer pkIdFornecimentoHasProduto;
    @JoinColumn(name = "fk_id_fornecimento", referencedColumnName = "pk_id_fornecimento", nullable = false)
    @ManyToOne(optional = false)
    private FarmFornecimento fkIdFornecimento;
    @JoinColumn(name = "fk_id_local_armazenamento", referencedColumnName = "pk_id_local_armazenamento")
    @ManyToOne
    private FarmLocalArmazenamento fkIdLocalArmazenamento;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmLoteProduto fkIdLoteProduto;

    public FarmFornecimentoHasProduto() {
    }

    public FarmFornecimentoHasProduto(Integer pkIdFornecimentoHasProduto) {
        this.pkIdFornecimentoHasProduto = pkIdFornecimentoHasProduto;
    }

    public FarmFornecimentoHasProduto(Integer pkIdFornecimentoHasProduto, double custoPorUnidade, int quantidade) {
        this.pkIdFornecimentoHasProduto = pkIdFornecimentoHasProduto;
        this.custoPorUnidade = custoPorUnidade;
        this.quantidade = quantidade;
    }

    public double getCustoPorUnidade() {
        return custoPorUnidade;
    }

    public void setCustoPorUnidade(double custoPorUnidade) {
        this.custoPorUnidade = custoPorUnidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Integer getPkIdFornecimentoHasProduto() {
        return pkIdFornecimentoHasProduto;
    }

    public void setPkIdFornecimentoHasProduto(Integer pkIdFornecimentoHasProduto) {
        this.pkIdFornecimentoHasProduto = pkIdFornecimentoHasProduto;
    }

    public FarmFornecimento getFkIdFornecimento() {
        return fkIdFornecimento;
    }

    public void setFkIdFornecimento(FarmFornecimento fkIdFornecimento) {
        this.fkIdFornecimento = fkIdFornecimento;
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
        hash += (pkIdFornecimentoHasProduto != null ? pkIdFornecimentoHasProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmFornecimentoHasProduto)) {
            return false;
        }
        FarmFornecimentoHasProduto other = (FarmFornecimentoHasProduto) object;
        if ((this.pkIdFornecimentoHasProduto == null && other.pkIdFornecimentoHasProduto != null) || (this.pkIdFornecimentoHasProduto != null && !this.pkIdFornecimentoHasProduto.equals(other.pkIdFornecimentoHasProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmFornecimentoHasProduto[ pkIdFornecimentoHasProduto=" + pkIdFornecimentoHasProduto + " ]";
    }
    
}
