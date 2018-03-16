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
@Table(name = "fin_forma_pagamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinFormaPagamento.findAll", query = "SELECT f FROM FinFormaPagamento f"),
    @NamedQuery(name = "FinFormaPagamento.findByPkIdFormaPagamento", query = "SELECT f FROM FinFormaPagamento f WHERE f.pkIdFormaPagamento = :pkIdFormaPagamento"),
    @NamedQuery(name = "FinFormaPagamento.findByDescricaoFormaPagamento", query = "SELECT f FROM FinFormaPagamento f WHERE f.descricaoFormaPagamento = :descricaoFormaPagamento")})
public class FinFormaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_forma_pagamento", nullable = false)
    private Integer pkIdFormaPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao_forma_pagamento", nullable = false, length = 50)
    private String descricaoFormaPagamento;
    @OneToMany(mappedBy = "fkIdFormaPagamento")
    private List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList;
    @OneToMany(mappedBy = "fkIdFormaPagamento")
    private List<FinPagamento> finPagamentoList;

    public FinFormaPagamento() {
    }

    public FinFormaPagamento(Integer pkIdFormaPagamento) {
        this.pkIdFormaPagamento = pkIdFormaPagamento;
    }

    public FinFormaPagamento(Integer pkIdFormaPagamento, String descricaoFormaPagamento) {
        this.pkIdFormaPagamento = pkIdFormaPagamento;
        this.descricaoFormaPagamento = descricaoFormaPagamento;
    }

    public Integer getPkIdFormaPagamento() {
        return pkIdFormaPagamento;
    }

    public void setPkIdFormaPagamento(Integer pkIdFormaPagamento) {
        this.pkIdFormaPagamento = pkIdFormaPagamento;
    }

    public String getDescricaoFormaPagamento() {
        return descricaoFormaPagamento;
    }

    public void setDescricaoFormaPagamento(String descricaoFormaPagamento) {
        this.descricaoFormaPagamento = descricaoFormaPagamento;
    }

    @XmlTransient
    public List<FinHistoricoPagamentoCancelados> getFinHistoricoPagamentoCanceladosList() {
        return finHistoricoPagamentoCanceladosList;
    }

    public void setFinHistoricoPagamentoCanceladosList(List<FinHistoricoPagamentoCancelados> finHistoricoPagamentoCanceladosList) {
        this.finHistoricoPagamentoCanceladosList = finHistoricoPagamentoCanceladosList;
    }

    @XmlTransient
    public List<FinPagamento> getFinPagamentoList() {
        return finPagamentoList;
    }

    public void setFinPagamentoList(List<FinPagamento> finPagamentoList) {
        this.finPagamentoList = finPagamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFormaPagamento != null ? pkIdFormaPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinFormaPagamento)) {
            return false;
        }
        FinFormaPagamento other = (FinFormaPagamento) object;
        if ((this.pkIdFormaPagamento == null && other.pkIdFormaPagamento != null) || (this.pkIdFormaPagamento != null && !this.pkIdFormaPagamento.equals(other.pkIdFormaPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinFormaPagamento[ pkIdFormaPagamento=" + pkIdFormaPagamento + " ]";
    }
    
}
