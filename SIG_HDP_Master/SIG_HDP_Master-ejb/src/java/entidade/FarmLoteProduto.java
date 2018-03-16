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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "farm_lote_produto", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero_lote", "fk_id_produto", "fk_id_fabricante"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmLoteProduto.findAll", query = "SELECT f FROM FarmLoteProduto f"),
    @NamedQuery(name = "FarmLoteProduto.findByPkIdLoteProduto", query = "SELECT f FROM FarmLoteProduto f WHERE f.pkIdLoteProduto = :pkIdLoteProduto"),
    @NamedQuery(name = "FarmLoteProduto.findByDataCadastro", query = "SELECT f FROM FarmLoteProduto f WHERE f.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "FarmLoteProduto.findByDataFabrico", query = "SELECT f FROM FarmLoteProduto f WHERE f.dataFabrico = :dataFabrico"),
    @NamedQuery(name = "FarmLoteProduto.findByDataValidade", query = "SELECT f FROM FarmLoteProduto f WHERE f.dataValidade = :dataValidade"),
    @NamedQuery(name = "FarmLoteProduto.findByNumeroLote", query = "SELECT f FROM FarmLoteProduto f WHERE f.numeroLote = :numeroLote"),
    @NamedQuery(name = "FarmLoteProduto.findByNomeComercial", query = "SELECT f FROM FarmLoteProduto f WHERE f.nomeComercial = :nomeComercial")})
public class FarmLoteProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_lote_produto", nullable = false)
    private Integer pkIdLoteProduto;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Column(name = "data_fabrico")
    @Temporal(TemporalType.DATE)
    private Date dataFabrico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_validade", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "numero_lote", nullable = false, length = 250)
    private String numeroLote;
    @Size(max = 50)
    @Column(name = "nome_comercial", length = 50)
    private String nomeComercial;
    @OneToMany(mappedBy = "fkIdLoteProduto")
    private List<FarmNotificacao> farmNotificacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLoteProduto")
    private List<FarmQuarentena> farmQuarentenaList;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;
    @JoinColumn(name = "fk_id_fabricante", referencedColumnName = "pk_id_fabricante")
    @ManyToOne
    private GrlFabricante fkIdFabricante;
    @JoinColumn(name = "fk_id_marca", referencedColumnName = "pk_id_marca")
    @ManyToOne
    private GrlMarcaProduto fkIdMarca;
    @JoinColumn(name = "fk_id_funcionario_cadastrou", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionarioCadastrou;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLoteProduto")
    private List<FarmMovimentoHasProduto> farmMovimentoHasProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLoteProduto")
    private List<FarmDoacaoHasLoteProduto> farmDoacaoHasLoteProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLoteProduto")
    private List<FarmDispensaHasLoteProduto> farmDispensaHasLoteProdutoList;
    @OneToMany(mappedBy = "fkIdLoteProduto")
    private List<FarmFichaStock> farmFichaStockList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLoteProduto")
    private List<FarmFornecimentoHasProduto> farmFornecimentoHasProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdLoteProduto")
    private List<FarmLoteProdutoHasLocalArmazenamento> farmLoteProdutoHasLocalArmazenamentoList;

    public FarmLoteProduto() {
    }

    public FarmLoteProduto(Integer pkIdLoteProduto) {
        this.pkIdLoteProduto = pkIdLoteProduto;
    }

    public FarmLoteProduto(Integer pkIdLoteProduto, Date dataValidade, String numeroLote) {
        this.pkIdLoteProduto = pkIdLoteProduto;
        this.dataValidade = dataValidade;
        this.numeroLote = numeroLote;
    }

    public Integer getPkIdLoteProduto() {
        return pkIdLoteProduto;
    }

    public void setPkIdLoteProduto(Integer pkIdLoteProduto) {
        this.pkIdLoteProduto = pkIdLoteProduto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataFabrico() {
        return dataFabrico;
    }

    public void setDataFabrico(Date dataFabrico) {
        this.dataFabrico = dataFabrico;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    @XmlTransient
    public List<FarmNotificacao> getFarmNotificacaoList() {
        return farmNotificacaoList;
    }

    public void setFarmNotificacaoList(List<FarmNotificacao> farmNotificacaoList) {
        this.farmNotificacaoList = farmNotificacaoList;
    }

    @XmlTransient
    public List<FarmQuarentena> getFarmQuarentenaList() {
        return farmQuarentenaList;
    }

    public void setFarmQuarentenaList(List<FarmQuarentena> farmQuarentenaList) {
        this.farmQuarentenaList = farmQuarentenaList;
    }

    public FarmProduto getFkIdProduto() {
        return fkIdProduto;
    }

    public void setFkIdProduto(FarmProduto fkIdProduto) {
        this.fkIdProduto = fkIdProduto;
    }

    public GrlFabricante getFkIdFabricante() {
        return fkIdFabricante;
    }

    public void setFkIdFabricante(GrlFabricante fkIdFabricante) {
        this.fkIdFabricante = fkIdFabricante;
    }

    public GrlMarcaProduto getFkIdMarca() {
        return fkIdMarca;
    }

    public void setFkIdMarca(GrlMarcaProduto fkIdMarca) {
        this.fkIdMarca = fkIdMarca;
    }

    public RhFuncionario getFkIdFuncionarioCadastrou() {
        return fkIdFuncionarioCadastrou;
    }

    public void setFkIdFuncionarioCadastrou(RhFuncionario fkIdFuncionarioCadastrou) {
        this.fkIdFuncionarioCadastrou = fkIdFuncionarioCadastrou;
    }

    @XmlTransient
    public List<FarmMovimentoHasProduto> getFarmMovimentoHasProdutoList() {
        return farmMovimentoHasProdutoList;
    }

    public void setFarmMovimentoHasProdutoList(List<FarmMovimentoHasProduto> farmMovimentoHasProdutoList) {
        this.farmMovimentoHasProdutoList = farmMovimentoHasProdutoList;
    }

    @XmlTransient
    public List<FarmDoacaoHasLoteProduto> getFarmDoacaoHasLoteProdutoList() {
        return farmDoacaoHasLoteProdutoList;
    }

    public void setFarmDoacaoHasLoteProdutoList(List<FarmDoacaoHasLoteProduto> farmDoacaoHasLoteProdutoList) {
        this.farmDoacaoHasLoteProdutoList = farmDoacaoHasLoteProdutoList;
    }

    @XmlTransient
    public List<FarmDispensaHasLoteProduto> getFarmDispensaHasLoteProdutoList() {
        return farmDispensaHasLoteProdutoList;
    }

    public void setFarmDispensaHasLoteProdutoList(List<FarmDispensaHasLoteProduto> farmDispensaHasLoteProdutoList) {
        this.farmDispensaHasLoteProdutoList = farmDispensaHasLoteProdutoList;
    }

    @XmlTransient
    public List<FarmFichaStock> getFarmFichaStockList() {
        return farmFichaStockList;
    }

    public void setFarmFichaStockList(List<FarmFichaStock> farmFichaStockList) {
        this.farmFichaStockList = farmFichaStockList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdLoteProduto != null ? pkIdLoteProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmLoteProduto)) {
            return false;
        }
        FarmLoteProduto other = (FarmLoteProduto) object;
        if ((this.pkIdLoteProduto == null && other.pkIdLoteProduto != null) || (this.pkIdLoteProduto != null && !this.pkIdLoteProduto.equals(other.pkIdLoteProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmLoteProduto[ pkIdLoteProduto=" + pkIdLoteProduto + " ]";
    }
    
}
