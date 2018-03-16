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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_fornecimento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmFornecimento.findAll", query = "SELECT f FROM FarmFornecimento f"),
    @NamedQuery(name = "FarmFornecimento.findByPkIdFornecimento", query = "SELECT f FROM FarmFornecimento f WHERE f.pkIdFornecimento = :pkIdFornecimento"),
    @NamedQuery(name = "FarmFornecimento.findByDataFornecimento", query = "SELECT f FROM FarmFornecimento f WHERE f.dataFornecimento = :dataFornecimento"),
    @NamedQuery(name = "FarmFornecimento.findByCustoTotal", query = "SELECT f FROM FarmFornecimento f WHERE f.custoTotal = :custoTotal")})
public class FarmFornecimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_fornecimento", nullable = false)
    private Integer pkIdFornecimento;
    @Column(name = "data_fornecimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFornecimento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "custo_total", precision = 17, scale = 17)
    private Double custoTotal;
    @JoinColumn(name = "fk_id_tipo_fornecimento", referencedColumnName = "pk_id_tipo_fornecimento", nullable = false)
    @ManyToOne(optional = false)
    private FarmTipoFornecimento fkIdTipoFornecimento;
    @JoinColumn(name = "fk_id_fornecedor", referencedColumnName = "pk_id_fornecedor", nullable = false)
    @ManyToOne(optional = false)
    private GrlFornecedor fkIdFornecedor;
    @JoinColumn(name = "fk_id_funcionario_cadastrou", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionarioCadastrou;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFornecimento")
    private List<FarmFornecimentoHasProduto> farmFornecimentoHasProdutoList;

    public FarmFornecimento() {
    }

    public FarmFornecimento(Integer pkIdFornecimento) {
        this.pkIdFornecimento = pkIdFornecimento;
    }

    public Integer getPkIdFornecimento() {
        return pkIdFornecimento;
    }

    public void setPkIdFornecimento(Integer pkIdFornecimento) {
        this.pkIdFornecimento = pkIdFornecimento;
    }

    public Date getDataFornecimento() {
        return dataFornecimento;
    }

    public void setDataFornecimento(Date dataFornecimento) {
        this.dataFornecimento = dataFornecimento;
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public FarmTipoFornecimento getFkIdTipoFornecimento() {
        return fkIdTipoFornecimento;
    }

    public void setFkIdTipoFornecimento(FarmTipoFornecimento fkIdTipoFornecimento) {
        this.fkIdTipoFornecimento = fkIdTipoFornecimento;
    }

    public GrlFornecedor getFkIdFornecedor() {
        return fkIdFornecedor;
    }

    public void setFkIdFornecedor(GrlFornecedor fkIdFornecedor) {
        this.fkIdFornecedor = fkIdFornecedor;
    }

    public RhFuncionario getFkIdFuncionarioCadastrou() {
        return fkIdFuncionarioCadastrou;
    }

    public void setFkIdFuncionarioCadastrou(RhFuncionario fkIdFuncionarioCadastrou) {
        this.fkIdFuncionarioCadastrou = fkIdFuncionarioCadastrou;
    }

    @XmlTransient
    public List<FarmFornecimentoHasProduto> getFarmFornecimentoHasProdutoList() {
        return farmFornecimentoHasProdutoList;
    }

    public void setFarmFornecimentoHasProdutoList(List<FarmFornecimentoHasProduto> farmFornecimentoHasProdutoList) {
        this.farmFornecimentoHasProdutoList = farmFornecimentoHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFornecimento != null ? pkIdFornecimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmFornecimento)) {
            return false;
        }
        FarmFornecimento other = (FarmFornecimento) object;
        if ((this.pkIdFornecimento == null && other.pkIdFornecimento != null) || (this.pkIdFornecimento != null && !this.pkIdFornecimento.equals(other.pkIdFornecimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmFornecimento[ pkIdFornecimento=" + pkIdFornecimento + " ]";
    }
    
}
