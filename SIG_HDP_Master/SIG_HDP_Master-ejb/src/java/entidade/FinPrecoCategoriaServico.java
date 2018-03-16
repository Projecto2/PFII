/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_preco_categoria_servico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinPrecoCategoriaServico.findAll", query = "SELECT f FROM FinPrecoCategoriaServico f"),
    @NamedQuery(name = "FinPrecoCategoriaServico.findByPkIdPrecoCategoriaServico", query = "SELECT f FROM FinPrecoCategoriaServico f WHERE f.pkIdPrecoCategoriaServico = :pkIdPrecoCategoriaServico"),
    @NamedQuery(name = "FinPrecoCategoriaServico.findByEstadoPreco", query = "SELECT f FROM FinPrecoCategoriaServico f WHERE f.estadoPreco = :estadoPreco"),
    @NamedQuery(name = "FinPrecoCategoriaServico.findByValor", query = "SELECT f FROM FinPrecoCategoriaServico f WHERE f.valor = :valor"),
    @NamedQuery(name = "FinPrecoCategoriaServico.findByValorPreco2", query = "SELECT f FROM FinPrecoCategoriaServico f WHERE f.valorPreco2 = :valorPreco2"),
    @NamedQuery(name = "FinPrecoCategoriaServico.findByValorPrecoDp", query = "SELECT f FROM FinPrecoCategoriaServico f WHERE f.valorPrecoDp = :valorPrecoDp"),
    @NamedQuery(name = "FinPrecoCategoriaServico.findByValorPrecoDpfs", query = "SELECT f FROM FinPrecoCategoriaServico f WHERE f.valorPrecoDpfs = :valorPrecoDpfs")})
public class FinPrecoCategoriaServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_preco_categoria_servico", nullable = false)
    private Integer pkIdPrecoCategoriaServico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_preco", nullable = false)
    private boolean estadoPreco;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false)
    private double valor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_preco2", precision = 17, scale = 17)
    private Double valorPreco2;
    @Column(name = "valor_preco_dp", precision = 17, scale = 17)
    private Double valorPrecoDp;
    @Column(name = "valor_preco_dpfs", precision = 17, scale = 17)
    private Double valorPrecoDpfs;
    @JoinColumn(name = "fk_id_categoria_servico", referencedColumnName = "pk_id_categoria_servico")
    @ManyToOne
    private AdmsCategoriaServico fkIdCategoriaServico;
    @JoinColumn(name = "fk_id_tipo_preco_categoria_servico", referencedColumnName = "pk_id_tipo_preco_categoria_servico")
    @ManyToOne
    private FinTipoPrecoCategoriaServico fkIdTipoPrecoCategoriaServico;
    @OneToMany(mappedBy = "fkIdPrecoCategoriaServico")
    private List<AdmsServicoSolicitado> admsServicoSolicitadoList;
    @OneToMany(mappedBy = "fkIdPrecoCategoriaServico")
    private List<FinEncargoDevido> finEncargoDevidoList;

    public FinPrecoCategoriaServico() {
    }

    public FinPrecoCategoriaServico(Integer pkIdPrecoCategoriaServico) {
        this.pkIdPrecoCategoriaServico = pkIdPrecoCategoriaServico;
    }

    public FinPrecoCategoriaServico(Integer pkIdPrecoCategoriaServico, boolean estadoPreco, double valor) {
        this.pkIdPrecoCategoriaServico = pkIdPrecoCategoriaServico;
        this.estadoPreco = estadoPreco;
        this.valor = valor;
    }

    public Integer getPkIdPrecoCategoriaServico() {
        return pkIdPrecoCategoriaServico;
    }

    public void setPkIdPrecoCategoriaServico(Integer pkIdPrecoCategoriaServico) {
        this.pkIdPrecoCategoriaServico = pkIdPrecoCategoriaServico;
    }

    public boolean getEstadoPreco() {
        return estadoPreco;
    }

    public void setEstadoPreco(boolean estadoPreco) {
        this.estadoPreco = estadoPreco;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Double getValorPreco2() {
        return valorPreco2;
    }

    public void setValorPreco2(Double valorPreco2) {
        this.valorPreco2 = valorPreco2;
    }

    public Double getValorPrecoDp() {
        return valorPrecoDp;
    }

    public void setValorPrecoDp(Double valorPrecoDp) {
        this.valorPrecoDp = valorPrecoDp;
    }

    public Double getValorPrecoDpfs() {
        return valorPrecoDpfs;
    }

    public void setValorPrecoDpfs(Double valorPrecoDpfs) {
        this.valorPrecoDpfs = valorPrecoDpfs;
    }

    public AdmsCategoriaServico getFkIdCategoriaServico() {
        return fkIdCategoriaServico;
    }

    public void setFkIdCategoriaServico(AdmsCategoriaServico fkIdCategoriaServico) {
        this.fkIdCategoriaServico = fkIdCategoriaServico;
    }

    public FinTipoPrecoCategoriaServico getFkIdTipoPrecoCategoriaServico() {
        return fkIdTipoPrecoCategoriaServico;
    }

    public void setFkIdTipoPrecoCategoriaServico(FinTipoPrecoCategoriaServico fkIdTipoPrecoCategoriaServico) {
        this.fkIdTipoPrecoCategoriaServico = fkIdTipoPrecoCategoriaServico;
    }

    @XmlTransient
    public List<AdmsServicoSolicitado> getAdmsServicoSolicitadoList() {
        return admsServicoSolicitadoList;
    }

    public void setAdmsServicoSolicitadoList(List<AdmsServicoSolicitado> admsServicoSolicitadoList) {
        this.admsServicoSolicitadoList = admsServicoSolicitadoList;
    }

    @XmlTransient
    public List<FinEncargoDevido> getFinEncargoDevidoList() {
        return finEncargoDevidoList;
    }

    public void setFinEncargoDevidoList(List<FinEncargoDevido> finEncargoDevidoList) {
        this.finEncargoDevidoList = finEncargoDevidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPrecoCategoriaServico != null ? pkIdPrecoCategoriaServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinPrecoCategoriaServico)) {
            return false;
        }
        FinPrecoCategoriaServico other = (FinPrecoCategoriaServico) object;
        if ((this.pkIdPrecoCategoriaServico == null && other.pkIdPrecoCategoriaServico != null) || (this.pkIdPrecoCategoriaServico != null && !this.pkIdPrecoCategoriaServico.equals(other.pkIdPrecoCategoriaServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinPrecoCategoriaServico[ pkIdPrecoCategoriaServico=" + pkIdPrecoCategoriaServico + " ]";
    }
    
}
