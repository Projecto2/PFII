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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_local_armazenamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmLocalArmazenamento.findAll", query = "SELECT f FROM FarmLocalArmazenamento f"),
    @NamedQuery(name = "FarmLocalArmazenamento.findByPkIdLocalArmazenamento", query = "SELECT f FROM FarmLocalArmazenamento f WHERE f.pkIdLocalArmazenamento = :pkIdLocalArmazenamento"),
    @NamedQuery(name = "FarmLocalArmazenamento.findByDescricao", query = "SELECT f FROM FarmLocalArmazenamento f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "FarmLocalArmazenamento.findByAbreviatura", query = "SELECT f FROM FarmLocalArmazenamento f WHERE f.abreviatura = :abreviatura"),
    @NamedQuery(name = "FarmLocalArmazenamento.findByCapacidadeAlocacao", query = "SELECT f FROM FarmLocalArmazenamento f WHERE f.capacidadeAlocacao = :capacidadeAlocacao")})
public class FarmLocalArmazenamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_local_armazenamento", nullable = false)
    private Integer pkIdLocalArmazenamento;
    @Size(max = 250)
    @Column(name = "descricao", length = 250)
    private String descricao;
    @Size(max = 10)
    @Column(name = "abreviatura", length = 10)
    private String abreviatura;
    @Column(name = "capacidade_alocacao")
    private Integer capacidadeAlocacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLocalOrigem")
    private List<FarmQuarentena> farmQuarentenaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLocalDeAtendimento")
    private List<FarmTurnoDispensa> farmTurnoDispensaList;
    @OneToMany(mappedBy = "fkIdLocalArmazenamento")
    private List<FarmFichaStock> farmFichaStockList;
    @JoinColumn(name = "fk_id_tipo_local_armazenamento", referencedColumnName = "pk_id_tipo_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmTipoLocalArmazenamento fkIdTipoLocalArmazenamento;
    @JoinColumn(name = "fk_id_instituicao", referencedColumnName = "pk_id_instituicao")
    @ManyToOne
    private GrlInstituicao fkIdInstituicao;
    @OneToMany(mappedBy = "fkIdLocalArmazenamento")
    private List<FarmFornecimentoHasProduto> farmFornecimentoHasProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLocalArmazenamento")
    private List<FarmLoteProdutoHasLocalArmazenamento> farmLoteProdutoHasLocalArmazenamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLocalArmazenamento")
    private List<FarmDoacao> farmDoacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLocalDestinoPedido")
    private List<FarmPedido> farmPedidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLocalOrigemPedido")
    private List<FarmPedido> farmPedidoList1;

    public FarmLocalArmazenamento() {
    }

    public FarmLocalArmazenamento(Integer pkIdLocalArmazenamento) {
        this.pkIdLocalArmazenamento = pkIdLocalArmazenamento;
    }

    public Integer getPkIdLocalArmazenamento() {
        return pkIdLocalArmazenamento;
    }

    public void setPkIdLocalArmazenamento(Integer pkIdLocalArmazenamento) {
        this.pkIdLocalArmazenamento = pkIdLocalArmazenamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getCapacidadeAlocacao() {
        return capacidadeAlocacao;
    }

    public void setCapacidadeAlocacao(Integer capacidadeAlocacao) {
        this.capacidadeAlocacao = capacidadeAlocacao;
    }

    @XmlTransient
    public List<FarmQuarentena> getFarmQuarentenaList() {
        return farmQuarentenaList;
    }

    public void setFarmQuarentenaList(List<FarmQuarentena> farmQuarentenaList) {
        this.farmQuarentenaList = farmQuarentenaList;
    }

    @XmlTransient
    public List<FarmTurnoDispensa> getFarmTurnoDispensaList() {
        return farmTurnoDispensaList;
    }

    public void setFarmTurnoDispensaList(List<FarmTurnoDispensa> farmTurnoDispensaList) {
        this.farmTurnoDispensaList = farmTurnoDispensaList;
    }

    @XmlTransient
    public List<FarmFichaStock> getFarmFichaStockList() {
        return farmFichaStockList;
    }

    public void setFarmFichaStockList(List<FarmFichaStock> farmFichaStockList) {
        this.farmFichaStockList = farmFichaStockList;
    }

    public FarmTipoLocalArmazenamento getFkIdTipoLocalArmazenamento() {
        return fkIdTipoLocalArmazenamento;
    }

    public void setFkIdTipoLocalArmazenamento(FarmTipoLocalArmazenamento fkIdTipoLocalArmazenamento) {
        this.fkIdTipoLocalArmazenamento = fkIdTipoLocalArmazenamento;
    }

    public GrlInstituicao getFkIdInstituicao() {
        return fkIdInstituicao;
    }

    public void setFkIdInstituicao(GrlInstituicao fkIdInstituicao) {
        this.fkIdInstituicao = fkIdInstituicao;
    }

    @XmlTransient
    public List<FarmFornecimentoHasProduto> getFarmFornecimentoHasProdutoList() {
        return farmFornecimentoHasProdutoList;
    }

    public void setFarmFornecimentoHasProdutoList(List<FarmFornecimentoHasProduto> farmFornecimentoHasProdutoList) {
        this.farmFornecimentoHasProdutoList = farmFornecimentoHasProdutoList;
    }

    @XmlTransient
    public List<FarmLoteProdutoHasLocalArmazenamento> getFarmLoteProdutoHasLocalArmazenamentoList() {
        return farmLoteProdutoHasLocalArmazenamentoList;
    }

    public void setFarmLoteProdutoHasLocalArmazenamentoList(List<FarmLoteProdutoHasLocalArmazenamento> farmLoteProdutoHasLocalArmazenamentoList) {
        this.farmLoteProdutoHasLocalArmazenamentoList = farmLoteProdutoHasLocalArmazenamentoList;
    }

    @XmlTransient
    public List<FarmDoacao> getFarmDoacaoList() {
        return farmDoacaoList;
    }

    public void setFarmDoacaoList(List<FarmDoacao> farmDoacaoList) {
        this.farmDoacaoList = farmDoacaoList;
    }

    @XmlTransient
    public List<FarmPedido> getFarmPedidoList() {
        return farmPedidoList;
    }

    public void setFarmPedidoList(List<FarmPedido> farmPedidoList) {
        this.farmPedidoList = farmPedidoList;
    }

    @XmlTransient
    public List<FarmPedido> getFarmPedidoList1() {
        return farmPedidoList1;
    }

    public void setFarmPedidoList1(List<FarmPedido> farmPedidoList1) {
        this.farmPedidoList1 = farmPedidoList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdLocalArmazenamento != null ? pkIdLocalArmazenamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmLocalArmazenamento)) {
            return false;
        }
        FarmLocalArmazenamento other = (FarmLocalArmazenamento) object;
        if ((this.pkIdLocalArmazenamento == null && other.pkIdLocalArmazenamento != null) || (this.pkIdLocalArmazenamento != null && !this.pkIdLocalArmazenamento.equals(other.pkIdLocalArmazenamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmLocalArmazenamento[ pkIdLocalArmazenamento=" + pkIdLocalArmazenamento + " ]";
    }
    
}
