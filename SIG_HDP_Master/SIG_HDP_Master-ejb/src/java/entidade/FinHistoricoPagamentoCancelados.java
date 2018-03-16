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
@Table(name = "fin_historico_pagamento_cancelados", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinHistoricoPagamentoCancelados.findAll", query = "SELECT f FROM FinHistoricoPagamentoCancelados f"),
    @NamedQuery(name = "FinHistoricoPagamentoCancelados.findByPkIdHistoricoPagamentoCancelados", query = "SELECT f FROM FinHistoricoPagamentoCancelados f WHERE f.pkIdHistoricoPagamentoCancelados = :pkIdHistoricoPagamentoCancelados"),
    @NamedQuery(name = "FinHistoricoPagamentoCancelados.findByDataCancelamento", query = "SELECT f FROM FinHistoricoPagamentoCancelados f WHERE f.dataCancelamento = :dataCancelamento"),
    @NamedQuery(name = "FinHistoricoPagamentoCancelados.findByDataRegistoPagamento", query = "SELECT f FROM FinHistoricoPagamentoCancelados f WHERE f.dataRegistoPagamento = :dataRegistoPagamento"),
    @NamedQuery(name = "FinHistoricoPagamentoCancelados.findByValorPago", query = "SELECT f FROM FinHistoricoPagamentoCancelados f WHERE f.valorPago = :valorPago")})
public class FinHistoricoPagamentoCancelados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_historico_pagamento_cancelados", nullable = false)
    private Long pkIdHistoricoPagamentoCancelados;
    @Column(name = "data_cancelamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCancelamento;
    @Column(name = "data_registo_pagamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistoPagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_pago", precision = 17, scale = 17)
    private Double valorPago;
    @JoinColumn(name = "fk_id_subprocesso", referencedColumnName = "pk_id_subprocesso")
    @ManyToOne
    private AdmsSubprocesso fkIdSubprocesso;
    @JoinColumn(name = "fk_id_forma_pagamento", referencedColumnName = "pk_id_forma_pagamento")
    @ManyToOne
    private FinFormaPagamento fkIdFormaPagamento;
    @JoinColumn(name = "fk_id_funcionario_registou_pagamento", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioRegistouPagamento;
    @JoinColumn(name = "fk_id_funcionario_cancelou", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioCancelou;
    @OneToMany(mappedBy = "fkIdPagamentoCancelado")
    private List<FinHistoricoCancelamentoEncargoDevidoPago> finHistoricoCancelamentoEncargoDevidoPagoList;

    public FinHistoricoPagamentoCancelados() {
    }

    public FinHistoricoPagamentoCancelados(Long pkIdHistoricoPagamentoCancelados) {
        this.pkIdHistoricoPagamentoCancelados = pkIdHistoricoPagamentoCancelados;
    }

    public Long getPkIdHistoricoPagamentoCancelados() {
        return pkIdHistoricoPagamentoCancelados;
    }

    public void setPkIdHistoricoPagamentoCancelados(Long pkIdHistoricoPagamentoCancelados) {
        this.pkIdHistoricoPagamentoCancelados = pkIdHistoricoPagamentoCancelados;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Date getDataRegistoPagamento() {
        return dataRegistoPagamento;
    }

    public void setDataRegistoPagamento(Date dataRegistoPagamento) {
        this.dataRegistoPagamento = dataRegistoPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public AdmsSubprocesso getFkIdSubprocesso() {
        return fkIdSubprocesso;
    }

    public void setFkIdSubprocesso(AdmsSubprocesso fkIdSubprocesso) {
        this.fkIdSubprocesso = fkIdSubprocesso;
    }

    public FinFormaPagamento getFkIdFormaPagamento() {
        return fkIdFormaPagamento;
    }

    public void setFkIdFormaPagamento(FinFormaPagamento fkIdFormaPagamento) {
        this.fkIdFormaPagamento = fkIdFormaPagamento;
    }

    public RhFuncionario getFkIdFuncionarioRegistouPagamento() {
        return fkIdFuncionarioRegistouPagamento;
    }

    public void setFkIdFuncionarioRegistouPagamento(RhFuncionario fkIdFuncionarioRegistouPagamento) {
        this.fkIdFuncionarioRegistouPagamento = fkIdFuncionarioRegistouPagamento;
    }

    public RhFuncionario getFkIdFuncionarioCancelou() {
        return fkIdFuncionarioCancelou;
    }

    public void setFkIdFuncionarioCancelou(RhFuncionario fkIdFuncionarioCancelou) {
        this.fkIdFuncionarioCancelou = fkIdFuncionarioCancelou;
    }

    @XmlTransient
    public List<FinHistoricoCancelamentoEncargoDevidoPago> getFinHistoricoCancelamentoEncargoDevidoPagoList() {
        return finHistoricoCancelamentoEncargoDevidoPagoList;
    }

    public void setFinHistoricoCancelamentoEncargoDevidoPagoList(List<FinHistoricoCancelamentoEncargoDevidoPago> finHistoricoCancelamentoEncargoDevidoPagoList) {
        this.finHistoricoCancelamentoEncargoDevidoPagoList = finHistoricoCancelamentoEncargoDevidoPagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdHistoricoPagamentoCancelados != null ? pkIdHistoricoPagamentoCancelados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinHistoricoPagamentoCancelados)) {
            return false;
        }
        FinHistoricoPagamentoCancelados other = (FinHistoricoPagamentoCancelados) object;
        if ((this.pkIdHistoricoPagamentoCancelados == null && other.pkIdHistoricoPagamentoCancelados != null) || (this.pkIdHistoricoPagamentoCancelados != null && !this.pkIdHistoricoPagamentoCancelados.equals(other.pkIdHistoricoPagamentoCancelados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinHistoricoPagamentoCancelados[ pkIdHistoricoPagamentoCancelados=" + pkIdHistoricoPagamentoCancelados + " ]";
    }
    
}
