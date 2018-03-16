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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_pagamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinPagamento.findAll", query = "SELECT f FROM FinPagamento f"),
    @NamedQuery(name = "FinPagamento.findByPkIdPagamento", query = "SELECT f FROM FinPagamento f WHERE f.pkIdPagamento = :pkIdPagamento"),
    @NamedQuery(name = "FinPagamento.findByDataPagamento", query = "SELECT f FROM FinPagamento f WHERE f.dataPagamento = :dataPagamento"),
    @NamedQuery(name = "FinPagamento.findByValorPago", query = "SELECT f FROM FinPagamento f WHERE f.valorPago = :valorPago")})
public class FinPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pagamento", nullable = false)
    private Long pkIdPagamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_pagamento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_pago", nullable = false)
    private double valorPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPagamento")
    private List<FinPagamentoConvenio> finPagamentoConvenioList;
    @OneToMany(mappedBy = "fkIdPagamento")
    private List<FinPagamentoViaBanco> finPagamentoViaBancoList;
    @OneToMany(mappedBy = "fkIdPagamento")
    private List<FinPagamentoEncargoDevido> finPagamentoEncargoDevidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPagamento")
    private List<FinPagamentoDp> finPagamentoDpList;
    @JoinColumn(name = "fk_id_forma_pagamento", referencedColumnName = "pk_id_forma_pagamento")
    @ManyToOne
    private FinFormaPagamento fkIdFormaPagamento;
    @JoinColumn(name = "fk_id_recepcionista", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdRecepcionista;

    public FinPagamento() {
    }

    public FinPagamento(Long pkIdPagamento) {
        this.pkIdPagamento = pkIdPagamento;
    }

    public FinPagamento(Long pkIdPagamento, Date dataPagamento, double valorPago) {
        this.pkIdPagamento = pkIdPagamento;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
    }

    public Long getPkIdPagamento() {
        return pkIdPagamento;
    }

    public void setPkIdPagamento(Long pkIdPagamento) {
        this.pkIdPagamento = pkIdPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    @XmlTransient
    public List<FinPagamentoConvenio> getFinPagamentoConvenioList() {
        return finPagamentoConvenioList;
    }

    public void setFinPagamentoConvenioList(List<FinPagamentoConvenio> finPagamentoConvenioList) {
        this.finPagamentoConvenioList = finPagamentoConvenioList;
    }

    @XmlTransient
    public List<FinPagamentoViaBanco> getFinPagamentoViaBancoList() {
        return finPagamentoViaBancoList;
    }

    public void setFinPagamentoViaBancoList(List<FinPagamentoViaBanco> finPagamentoViaBancoList) {
        this.finPagamentoViaBancoList = finPagamentoViaBancoList;
    }

    @XmlTransient
    public List<FinPagamentoEncargoDevido> getFinPagamentoEncargoDevidoList() {
        return finPagamentoEncargoDevidoList;
    }

    public void setFinPagamentoEncargoDevidoList(List<FinPagamentoEncargoDevido> finPagamentoEncargoDevidoList) {
        this.finPagamentoEncargoDevidoList = finPagamentoEncargoDevidoList;
    }

    @XmlTransient
    public List<FinPagamentoDp> getFinPagamentoDpList() {
        return finPagamentoDpList;
    }

    public void setFinPagamentoDpList(List<FinPagamentoDp> finPagamentoDpList) {
        this.finPagamentoDpList = finPagamentoDpList;
    }

    public FinFormaPagamento getFkIdFormaPagamento() {
        return fkIdFormaPagamento;
    }

    public void setFkIdFormaPagamento(FinFormaPagamento fkIdFormaPagamento) {
        this.fkIdFormaPagamento = fkIdFormaPagamento;
    }

    public RhFuncionario getFkIdRecepcionista() {
        return fkIdRecepcionista;
    }

    public void setFkIdRecepcionista(RhFuncionario fkIdRecepcionista) {
        this.fkIdRecepcionista = fkIdRecepcionista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPagamento != null ? pkIdPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinPagamento)) {
            return false;
        }
        FinPagamento other = (FinPagamento) object;
        if ((this.pkIdPagamento == null && other.pkIdPagamento != null) || (this.pkIdPagamento != null && !this.pkIdPagamento.equals(other.pkIdPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinPagamento[ pkIdPagamento=" + pkIdPagamento + " ]";
    }
    
}
