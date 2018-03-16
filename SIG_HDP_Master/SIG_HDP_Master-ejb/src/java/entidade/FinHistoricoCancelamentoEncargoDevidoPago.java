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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_historico_cancelamento_encargo_devido_pago", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinHistoricoCancelamentoEncargoDevidoPago.findAll", query = "SELECT f FROM FinHistoricoCancelamentoEncargoDevidoPago f"),
    @NamedQuery(name = "FinHistoricoCancelamentoEncargoDevidoPago.findByDescricaoEncargoDevido", query = "SELECT f FROM FinHistoricoCancelamentoEncargoDevidoPago f WHERE f.descricaoEncargoDevido = :descricaoEncargoDevido"),
    @NamedQuery(name = "FinHistoricoCancelamentoEncargoDevidoPago.findByQuantidade", query = "SELECT f FROM FinHistoricoCancelamentoEncargoDevidoPago f WHERE f.quantidade = :quantidade"),
    @NamedQuery(name = "FinHistoricoCancelamentoEncargoDevidoPago.findByValor", query = "SELECT f FROM FinHistoricoCancelamentoEncargoDevidoPago f WHERE f.valor = :valor"),
    @NamedQuery(name = "FinHistoricoCancelamentoEncargoDevidoPago.findByDataEncargoDevido", query = "SELECT f FROM FinHistoricoCancelamentoEncargoDevidoPago f WHERE f.dataEncargoDevido = :dataEncargoDevido"),
    @NamedQuery(name = "FinHistoricoCancelamentoEncargoDevidoPago.findByPkIdHistoricoCancelamentoEncargoDevidoPago", query = "SELECT f FROM FinHistoricoCancelamentoEncargoDevidoPago f WHERE f.pkIdHistoricoCancelamentoEncargoDevidoPago = :pkIdHistoricoCancelamentoEncargoDevidoPago")})
public class FinHistoricoCancelamentoEncargoDevidoPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 150)
    @Column(name = "descricao_encargo_devido", length = 150)
    private String descricaoEncargoDevido;
    @Column(name = "quantidade")
    private Integer quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 17, scale = 17)
    private Double valor;
    @Column(name = "data_encargo_devido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEncargoDevido;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_historico_cancelamento_encargo_devido_pago", nullable = false)
    private Long pkIdHistoricoCancelamentoEncargoDevidoPago;
    @JoinColumn(name = "fk_id_pagamento_cancelado", referencedColumnName = "pk_id_historico_pagamento_cancelados")
    @ManyToOne
    private FinHistoricoPagamentoCancelados fkIdPagamentoCancelado;

    public FinHistoricoCancelamentoEncargoDevidoPago() {
    }

    public FinHistoricoCancelamentoEncargoDevidoPago(Long pkIdHistoricoCancelamentoEncargoDevidoPago) {
        this.pkIdHistoricoCancelamentoEncargoDevidoPago = pkIdHistoricoCancelamentoEncargoDevidoPago;
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

    public Date getDataEncargoDevido() {
        return dataEncargoDevido;
    }

    public void setDataEncargoDevido(Date dataEncargoDevido) {
        this.dataEncargoDevido = dataEncargoDevido;
    }

    public Long getPkIdHistoricoCancelamentoEncargoDevidoPago() {
        return pkIdHistoricoCancelamentoEncargoDevidoPago;
    }

    public void setPkIdHistoricoCancelamentoEncargoDevidoPago(Long pkIdHistoricoCancelamentoEncargoDevidoPago) {
        this.pkIdHistoricoCancelamentoEncargoDevidoPago = pkIdHistoricoCancelamentoEncargoDevidoPago;
    }

    public FinHistoricoPagamentoCancelados getFkIdPagamentoCancelado() {
        return fkIdPagamentoCancelado;
    }

    public void setFkIdPagamentoCancelado(FinHistoricoPagamentoCancelados fkIdPagamentoCancelado) {
        this.fkIdPagamentoCancelado = fkIdPagamentoCancelado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdHistoricoCancelamentoEncargoDevidoPago != null ? pkIdHistoricoCancelamentoEncargoDevidoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinHistoricoCancelamentoEncargoDevidoPago)) {
            return false;
        }
        FinHistoricoCancelamentoEncargoDevidoPago other = (FinHistoricoCancelamentoEncargoDevidoPago) object;
        if ((this.pkIdHistoricoCancelamentoEncargoDevidoPago == null && other.pkIdHistoricoCancelamentoEncargoDevidoPago != null) || (this.pkIdHistoricoCancelamentoEncargoDevidoPago != null && !this.pkIdHistoricoCancelamentoEncargoDevidoPago.equals(other.pkIdHistoricoCancelamentoEncargoDevidoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinHistoricoCancelamentoEncargoDevidoPago[ pkIdHistoricoCancelamentoEncargoDevidoPago=" + pkIdHistoricoCancelamentoEncargoDevidoPago + " ]";
    }
    
}
