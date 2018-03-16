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
@Table(name = "fin_estado_pagamento_paga_naopago", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinEstadoPagamentoPagaNaopago.findAll", query = "SELECT f FROM FinEstadoPagamentoPagaNaopago f"),
    @NamedQuery(name = "FinEstadoPagamentoPagaNaopago.findByPkIdEstadoPagamentoPagaNaopago", query = "SELECT f FROM FinEstadoPagamentoPagaNaopago f WHERE f.pkIdEstadoPagamentoPagaNaopago = :pkIdEstadoPagamentoPagaNaopago"),
    @NamedQuery(name = "FinEstadoPagamentoPagaNaopago.findByDescricao", query = "SELECT f FROM FinEstadoPagamentoPagaNaopago f WHERE f.descricao = :descricao")})
public class FinEstadoPagamentoPagaNaopago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_pagamento_paga_naopago", nullable = false)
    private Integer pkIdEstadoPagamentoPagaNaopago;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdEstadoPagamentoPagoNaopago")
    private List<FinEncargoDevido> finEncargoDevidoList;

    public FinEstadoPagamentoPagaNaopago() {
    }

    public FinEstadoPagamentoPagaNaopago(Integer pkIdEstadoPagamentoPagaNaopago) {
        this.pkIdEstadoPagamentoPagaNaopago = pkIdEstadoPagamentoPagaNaopago;
    }

    public Integer getPkIdEstadoPagamentoPagaNaopago() {
        return pkIdEstadoPagamentoPagaNaopago;
    }

    public void setPkIdEstadoPagamentoPagaNaopago(Integer pkIdEstadoPagamentoPagaNaopago) {
        this.pkIdEstadoPagamentoPagaNaopago = pkIdEstadoPagamentoPagaNaopago;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash += (pkIdEstadoPagamentoPagaNaopago != null ? pkIdEstadoPagamentoPagaNaopago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinEstadoPagamentoPagaNaopago)) {
            return false;
        }
        FinEstadoPagamentoPagaNaopago other = (FinEstadoPagamentoPagaNaopago) object;
        if ((this.pkIdEstadoPagamentoPagaNaopago == null && other.pkIdEstadoPagamentoPagaNaopago != null) || (this.pkIdEstadoPagamentoPagaNaopago != null && !this.pkIdEstadoPagamentoPagaNaopago.equals(other.pkIdEstadoPagamentoPagaNaopago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinEstadoPagamentoPagaNaopago[ pkIdEstadoPagamentoPagaNaopago=" + pkIdEstadoPagamentoPagaNaopago + " ]";
    }
    
}
