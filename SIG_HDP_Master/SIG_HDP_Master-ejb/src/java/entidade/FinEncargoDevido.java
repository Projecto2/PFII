/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_encargo_devido", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinEncargoDevido.findAll", query = "SELECT f FROM FinEncargoDevido f"),
    @NamedQuery(name = "FinEncargoDevido.findByPkIdEncargoDevido", query = "SELECT f FROM FinEncargoDevido f WHERE f.pkIdEncargoDevido = :pkIdEncargoDevido"),
    @NamedQuery(name = "FinEncargoDevido.findByDescricaoEncargoDevido", query = "SELECT f FROM FinEncargoDevido f WHERE f.descricaoEncargoDevido = :descricaoEncargoDevido"),
    @NamedQuery(name = "FinEncargoDevido.findByQuantidade", query = "SELECT f FROM FinEncargoDevido f WHERE f.quantidade = :quantidade"),
    @NamedQuery(name = "FinEncargoDevido.findByValor", query = "SELECT f FROM FinEncargoDevido f WHERE f.valor = :valor"),
    @NamedQuery(name = "FinEncargoDevido.findByFkIdPrecoCategoriaProduto", query = "SELECT f FROM FinEncargoDevido f WHERE f.fkIdPrecoCategoriaProduto = :fkIdPrecoCategoriaProduto"),
    @NamedQuery(name = "FinEncargoDevido.findByFkIdProdutoSolicitado", query = "SELECT f FROM FinEncargoDevido f WHERE f.fkIdProdutoSolicitado = :fkIdProdutoSolicitado"),
    @NamedQuery(name = "FinEncargoDevido.findByData", query = "SELECT f FROM FinEncargoDevido f WHERE f.data = :data")})
public class FinEncargoDevido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_encargo_devido", nullable = false)
    private Long pkIdEncargoDevido;
    @Size(max = 200)
    @Column(name = "descricao_encargo_devido", length = 200)
    private String descricaoEncargoDevido;
    @Column(name = "quantidade")
    private Integer quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 17, scale = 17)
    private Double valor;
    @Column(name = "fk_id_preco_categoria_produto")
    private Integer fkIdPrecoCategoriaProduto;
    @Column(name = "fk_id_produto_solicitado")
    private BigInteger fkIdProdutoSolicitado;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "fk_id_servico_solicitado", referencedColumnName = "pk_id_servico_solicitado")
    @ManyToOne
    private AdmsServicoSolicitado fkIdServicoSolicitado;
    @JoinColumn(name = "fk_id_subprocesso", referencedColumnName = "pk_id_subprocesso")
    @ManyToOne
    private AdmsSubprocesso fkIdSubprocesso;
    @JoinColumn(name = "fk_id_estado_pagamento_pago_naopago", referencedColumnName = "pk_id_estado_pagamento_paga_naopago")
    @ManyToOne
    private FinEstadoPagamentoPagaNaopago fkIdEstadoPagamentoPagoNaopago;
    @JoinColumn(name = "fk_id_preco_categoria_servico", referencedColumnName = "pk_id_preco_categoria_servico")
    @ManyToOne
    private FinPrecoCategoriaServico fkIdPrecoCategoriaServico;
    @JoinColumn(name = "fk_id_tipo_encargo", referencedColumnName = "pk_id_tipo_encargo")
    @ManyToOne
    private FinTipoEncargo fkIdTipoEncargo;
    @OneToMany(mappedBy = "fkIdEncargoDevido")
    private List<FinPagamentoEncargoDevido> finPagamentoEncargoDevidoList;

    public FinEncargoDevido() {
    }

    public FinEncargoDevido(Long pkIdEncargoDevido) {
        this.pkIdEncargoDevido = pkIdEncargoDevido;
    }

    public Long getPkIdEncargoDevido() {
        return pkIdEncargoDevido;
    }

    public void setPkIdEncargoDevido(Long pkIdEncargoDevido) {
        this.pkIdEncargoDevido = pkIdEncargoDevido;
    }

    public String getDescricaoEncargoDevido() {
        return descricaoEncargoDevido;
    }

    public void setDescricaoEncargoDevido(String descricaoEncargoDevido) {
        this.descricaoEncargoDevido = descricaoEncargoDevido;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getFkIdPrecoCategoriaProduto() {
        return fkIdPrecoCategoriaProduto;
    }

    public void setFkIdPrecoCategoriaProduto(Integer fkIdPrecoCategoriaProduto) {
        this.fkIdPrecoCategoriaProduto = fkIdPrecoCategoriaProduto;
    }

    public BigInteger getFkIdProdutoSolicitado() {
        return fkIdProdutoSolicitado;
    }

    public void setFkIdProdutoSolicitado(BigInteger fkIdProdutoSolicitado) {
        this.fkIdProdutoSolicitado = fkIdProdutoSolicitado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public AdmsServicoSolicitado getFkIdServicoSolicitado() {
        return fkIdServicoSolicitado;
    }

    public void setFkIdServicoSolicitado(AdmsServicoSolicitado fkIdServicoSolicitado) {
        this.fkIdServicoSolicitado = fkIdServicoSolicitado;
    }

    public AdmsSubprocesso getFkIdSubprocesso() {
        return fkIdSubprocesso;
    }

    public void setFkIdSubprocesso(AdmsSubprocesso fkIdSubprocesso) {
        this.fkIdSubprocesso = fkIdSubprocesso;
    }

    public FinEstadoPagamentoPagaNaopago getFkIdEstadoPagamentoPagoNaopago() {
        return fkIdEstadoPagamentoPagoNaopago;
    }

    public void setFkIdEstadoPagamentoPagoNaopago(FinEstadoPagamentoPagaNaopago fkIdEstadoPagamentoPagoNaopago) {
        this.fkIdEstadoPagamentoPagoNaopago = fkIdEstadoPagamentoPagoNaopago;
    }

    public FinPrecoCategoriaServico getFkIdPrecoCategoriaServico() {
        return fkIdPrecoCategoriaServico;
    }

    public void setFkIdPrecoCategoriaServico(FinPrecoCategoriaServico fkIdPrecoCategoriaServico) {
        this.fkIdPrecoCategoriaServico = fkIdPrecoCategoriaServico;
    }

    public FinTipoEncargo getFkIdTipoEncargo() {
        return fkIdTipoEncargo;
    }

    public void setFkIdTipoEncargo(FinTipoEncargo fkIdTipoEncargo) {
        this.fkIdTipoEncargo = fkIdTipoEncargo;
    }

    @XmlTransient
    public List<FinPagamentoEncargoDevido> getFinPagamentoEncargoDevidoList() {
        return finPagamentoEncargoDevidoList;
    }

    public void setFinPagamentoEncargoDevidoList(List<FinPagamentoEncargoDevido> finPagamentoEncargoDevidoList) {
        this.finPagamentoEncargoDevidoList = finPagamentoEncargoDevidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEncargoDevido != null ? pkIdEncargoDevido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinEncargoDevido)) {
            return false;
        }
        FinEncargoDevido other = (FinEncargoDevido) object;
        if ((this.pkIdEncargoDevido == null && other.pkIdEncargoDevido != null) || (this.pkIdEncargoDevido != null && !this.pkIdEncargoDevido.equals(other.pkIdEncargoDevido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinEncargoDevido[ pkIdEncargoDevido=" + pkIdEncargoDevido + " ]";
    }
    
}
