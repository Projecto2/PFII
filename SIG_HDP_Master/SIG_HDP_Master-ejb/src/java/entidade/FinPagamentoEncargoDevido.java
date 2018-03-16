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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_pagamento_encargo_devido", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_encargo_devido", "fk_id_pagamento"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinPagamentoEncargoDevido.findAll", query = "SELECT f FROM FinPagamentoEncargoDevido f"),
    @NamedQuery(name = "FinPagamentoEncargoDevido.findByPkIdPagamentoEncargoDevido", query = "SELECT f FROM FinPagamentoEncargoDevido f WHERE f.pkIdPagamentoEncargoDevido = :pkIdPagamentoEncargoDevido")})
public class FinPagamentoEncargoDevido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pagamento_encargo_devido", nullable = false)
    private Long pkIdPagamentoEncargoDevido;
    @JoinColumn(name = "fk_id_encargo_devido", referencedColumnName = "pk_id_encargo_devido")
    @ManyToOne
    private FinEncargoDevido fkIdEncargoDevido;
    @JoinColumn(name = "fk_id_pagamento", referencedColumnName = "pk_id_pagamento")
    @ManyToOne
    private FinPagamento fkIdPagamento;

    public FinPagamentoEncargoDevido() {
    }

    public FinPagamentoEncargoDevido(Long pkIdPagamentoEncargoDevido) {
        this.pkIdPagamentoEncargoDevido = pkIdPagamentoEncargoDevido;
    }

    public Long getPkIdPagamentoEncargoDevido() {
        return pkIdPagamentoEncargoDevido;
    }

    public void setPkIdPagamentoEncargoDevido(Long pkIdPagamentoEncargoDevido) {
        this.pkIdPagamentoEncargoDevido = pkIdPagamentoEncargoDevido;
    }

    public FinEncargoDevido getFkIdEncargoDevido() {
        return fkIdEncargoDevido;
    }

    public void setFkIdEncargoDevido(FinEncargoDevido fkIdEncargoDevido) {
        this.fkIdEncargoDevido = fkIdEncargoDevido;
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
        hash += (pkIdPagamentoEncargoDevido != null ? pkIdPagamentoEncargoDevido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinPagamentoEncargoDevido)) {
            return false;
        }
        FinPagamentoEncargoDevido other = (FinPagamentoEncargoDevido) object;
        if ((this.pkIdPagamentoEncargoDevido == null && other.pkIdPagamentoEncargoDevido != null) || (this.pkIdPagamentoEncargoDevido != null && !this.pkIdPagamentoEncargoDevido.equals(other.pkIdPagamentoEncargoDevido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinPagamentoEncargoDevido[ pkIdPagamentoEncargoDevido=" + pkIdPagamentoEncargoDevido + " ]";
    }
    
}
