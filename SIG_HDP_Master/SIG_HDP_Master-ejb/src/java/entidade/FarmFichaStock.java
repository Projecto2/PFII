/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_ficha_stock", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmFichaStock.findAll", query = "SELECT f FROM FarmFichaStock f"),
    @NamedQuery(name = "FarmFichaStock.findByDataMovimento", query = "SELECT f FROM FarmFichaStock f WHERE f.dataMovimento = :dataMovimento"),
    @NamedQuery(name = "FarmFichaStock.findByEntradas", query = "SELECT f FROM FarmFichaStock f WHERE f.entradas = :entradas"),
    @NamedQuery(name = "FarmFichaStock.findBySaidas", query = "SELECT f FROM FarmFichaStock f WHERE f.saidas = :saidas"),
    @NamedQuery(name = "FarmFichaStock.findByQuantidadeRestante", query = "SELECT f FROM FarmFichaStock f WHERE f.quantidadeRestante = :quantidadeRestante"),
    @NamedQuery(name = "FarmFichaStock.findByPkIdFichaStock", query = "SELECT f FROM FarmFichaStock f WHERE f.pkIdFichaStock = :pkIdFichaStock"),
    @NamedQuery(name = "FarmFichaStock.findByOrigemOuDestino", query = "SELECT f FROM FarmFichaStock f WHERE f.origemOuDestino = :origemOuDestino")})
public class FarmFichaStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "data_movimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMovimento;
    @Column(name = "entradas")
    private Integer entradas;
    @Column(name = "saidas")
    private Integer saidas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade_restante", nullable = false)
    private int quantidadeRestante;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_ficha_stock", nullable = false)
    private Integer pkIdFichaStock;
    @Size(max = 200)
    @Column(name = "origem_ou_destino", length = 200)
    private String origemOuDestino;
    @JoinColumn(name = "fk_id_local_armazenamento", referencedColumnName = "pk_id_local_armazenamento")
    @ManyToOne
    private FarmLocalArmazenamento fkIdLocalArmazenamento;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto")
    @ManyToOne
    private FarmLoteProduto fkIdLoteProduto;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public FarmFichaStock() {
    }

    public FarmFichaStock(Integer pkIdFichaStock) {
        this.pkIdFichaStock = pkIdFichaStock;
    }

    public FarmFichaStock(Integer pkIdFichaStock, int quantidadeRestante) {
        this.pkIdFichaStock = pkIdFichaStock;
        this.quantidadeRestante = quantidadeRestante;
    }

    public Date getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(Date dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    public Integer getSaidas() {
        return saidas;
    }

    public void setSaidas(Integer saidas) {
        this.saidas = saidas;
    }

    public int getQuantidadeRestante() {
        return quantidadeRestante;
    }

    public void setQuantidadeRestante(int quantidadeRestante) {
        this.quantidadeRestante = quantidadeRestante;
    }

    public Integer getPkIdFichaStock() {
        return pkIdFichaStock;
    }

    public void setPkIdFichaStock(Integer pkIdFichaStock) {
        this.pkIdFichaStock = pkIdFichaStock;
    }

    public String getOrigemOuDestino() {
        return origemOuDestino;
    }

    public void setOrigemOuDestino(String origemOuDestino) {
        this.origemOuDestino = origemOuDestino;
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

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFichaStock != null ? pkIdFichaStock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmFichaStock)) {
            return false;
        }
        FarmFichaStock other = (FarmFichaStock) object;
        if ((this.pkIdFichaStock == null && other.pkIdFichaStock != null) || (this.pkIdFichaStock != null && !this.pkIdFichaStock.equals(other.pkIdFichaStock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmFichaStock[ pkIdFichaStock=" + pkIdFichaStock + " ]";
    }
    
}
