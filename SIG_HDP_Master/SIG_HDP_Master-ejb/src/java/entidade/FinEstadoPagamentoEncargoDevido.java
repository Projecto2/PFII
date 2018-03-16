/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_estado_pagamento_encargo_devido", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_estado_pagamento"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinEstadoPagamentoEncargoDevido.findAll", query = "SELECT f FROM FinEstadoPagamentoEncargoDevido f"),
    @NamedQuery(name = "FinEstadoPagamentoEncargoDevido.findByPkIdEstadoPagamentoEncargoDevido", query = "SELECT f FROM FinEstadoPagamentoEncargoDevido f WHERE f.pkIdEstadoPagamentoEncargoDevido = :pkIdEstadoPagamentoEncargoDevido"),
    @NamedQuery(name = "FinEstadoPagamentoEncargoDevido.findByDescricaoEstadoPagamento", query = "SELECT f FROM FinEstadoPagamentoEncargoDevido f WHERE f.descricaoEstadoPagamento = :descricaoEstadoPagamento")})
public class FinEstadoPagamentoEncargoDevido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estado_pagamento_encargo_devido", nullable = false)
    private Integer pkIdEstadoPagamentoEncargoDevido;
    @Size(max = 100)
    @Column(name = "descricao_estado_pagamento", length = 100)
    private String descricaoEstadoPagamento;

    public FinEstadoPagamentoEncargoDevido() {
    }

    public FinEstadoPagamentoEncargoDevido(Integer pkIdEstadoPagamentoEncargoDevido) {
        this.pkIdEstadoPagamentoEncargoDevido = pkIdEstadoPagamentoEncargoDevido;
    }

    public Integer getPkIdEstadoPagamentoEncargoDevido() {
        return pkIdEstadoPagamentoEncargoDevido;
    }

    public void setPkIdEstadoPagamentoEncargoDevido(Integer pkIdEstadoPagamentoEncargoDevido) {
        this.pkIdEstadoPagamentoEncargoDevido = pkIdEstadoPagamentoEncargoDevido;
    }

    public String getDescricaoEstadoPagamento() {
        return descricaoEstadoPagamento;
    }

    public void setDescricaoEstadoPagamento(String descricaoEstadoPagamento) {
        this.descricaoEstadoPagamento = descricaoEstadoPagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoPagamentoEncargoDevido != null ? pkIdEstadoPagamentoEncargoDevido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinEstadoPagamentoEncargoDevido)) {
            return false;
        }
        FinEstadoPagamentoEncargoDevido other = (FinEstadoPagamentoEncargoDevido) object;
        if ((this.pkIdEstadoPagamentoEncargoDevido == null && other.pkIdEstadoPagamentoEncargoDevido != null) || (this.pkIdEstadoPagamentoEncargoDevido != null && !this.pkIdEstadoPagamentoEncargoDevido.equals(other.pkIdEstadoPagamentoEncargoDevido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinEstadoPagamentoEncargoDevido[ pkIdEstadoPagamentoEncargoDevido=" + pkIdEstadoPagamentoEncargoDevido + " ]";
    }
    
}
