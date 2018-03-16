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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_pagamento_dp", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinPagamentoDp.findAll", query = "SELECT f FROM FinPagamentoDp f"),
    @NamedQuery(name = "FinPagamentoDp.findByPkIdPagamentoDp", query = "SELECT f FROM FinPagamentoDp f WHERE f.pkIdPagamentoDp = :pkIdPagamentoDp")})
public class FinPagamentoDp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_pagamento_dp", nullable = false)
    private Long pkIdPagamentoDp;
    @JoinColumn(name = "fk_id_pagamento", referencedColumnName = "pk_id_pagamento", nullable = false)
    @ManyToOne(optional = false)
    private FinPagamento fkIdPagamento;

    public FinPagamentoDp() {
    }

    public FinPagamentoDp(Long pkIdPagamentoDp) {
        this.pkIdPagamentoDp = pkIdPagamentoDp;
    }

    public Long getPkIdPagamentoDp() {
        return pkIdPagamentoDp;
    }

    public void setPkIdPagamentoDp(Long pkIdPagamentoDp) {
        this.pkIdPagamentoDp = pkIdPagamentoDp;
    }

    public FinPagamento getFkIdPagamento() {
        return fkIdPagamento;
    }

    public void setFkIdPagamento(FinPagamento fkIdPagamento) {
        this.fkIdPagamento = fkIdPagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPagamentoDp != null ? pkIdPagamentoDp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinPagamentoDp)) {
            return false;
        }
        FinPagamentoDp other = (FinPagamentoDp) object;
        if ((this.pkIdPagamentoDp == null && other.pkIdPagamentoDp != null) || (this.pkIdPagamentoDp != null && !this.pkIdPagamentoDp.equals(other.pkIdPagamentoDp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinPagamentoDp[ pkIdPagamentoDp=" + pkIdPagamentoDp + " ]";
    }
    
}
