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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_pagamento_via_banco", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinPagamentoViaBanco.findAll", query = "SELECT f FROM FinPagamentoViaBanco f"),
    @NamedQuery(name = "FinPagamentoViaBanco.findByPkIdPagamentoViaBanco", query = "SELECT f FROM FinPagamentoViaBanco f WHERE f.pkIdPagamentoViaBanco = :pkIdPagamentoViaBanco")})
public class FinPagamentoViaBanco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pagamento_via_banco", nullable = false)
    private Long pkIdPagamentoViaBanco;
    @JoinColumn(name = "fk_id_conta_bancaria", referencedColumnName = "pk_id_conta_bancaria", nullable = false)
    @ManyToOne(optional = false)
    private FinContaBancaria fkIdContaBancaria;
    @JoinColumn(name = "fk_id_pagamento", referencedColumnName = "pk_id_pagamento")
    @ManyToOne
    private FinPagamento fkIdPagamento;

    public FinPagamentoViaBanco() {
    }

    public FinPagamentoViaBanco(Long pkIdPagamentoViaBanco) {
        this.pkIdPagamentoViaBanco = pkIdPagamentoViaBanco;
    }

    public Long getPkIdPagamentoViaBanco() {
        return pkIdPagamentoViaBanco;
    }

    public void setPkIdPagamentoViaBanco(Long pkIdPagamentoViaBanco) {
        this.pkIdPagamentoViaBanco = pkIdPagamentoViaBanco;
    }

    public FinContaBancaria getFkIdContaBancaria() {
        return fkIdContaBancaria;
    }

    public void setFkIdContaBancaria(FinContaBancaria fkIdContaBancaria) {
        this.fkIdContaBancaria = fkIdContaBancaria;
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
        hash += (pkIdPagamentoViaBanco != null ? pkIdPagamentoViaBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinPagamentoViaBanco)) {
            return false;
        }
        FinPagamentoViaBanco other = (FinPagamentoViaBanco) object;
        if ((this.pkIdPagamentoViaBanco == null && other.pkIdPagamentoViaBanco != null) || (this.pkIdPagamentoViaBanco != null && !this.pkIdPagamentoViaBanco.equals(other.pkIdPagamentoViaBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinPagamentoViaBanco[ pkIdPagamentoViaBanco=" + pkIdPagamentoViaBanco + " ]";
    }
    
}
