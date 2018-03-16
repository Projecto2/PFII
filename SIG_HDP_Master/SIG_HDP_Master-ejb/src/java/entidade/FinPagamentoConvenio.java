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
@Table(name = "fin_pagamento_convenio", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinPagamentoConvenio.findAll", query = "SELECT f FROM FinPagamentoConvenio f"),
    @NamedQuery(name = "FinPagamentoConvenio.findByPkIdPagamentoConvenio", query = "SELECT f FROM FinPagamentoConvenio f WHERE f.pkIdPagamentoConvenio = :pkIdPagamentoConvenio")})
public class FinPagamentoConvenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pagamento_convenio", nullable = false)
    private Long pkIdPagamentoConvenio;
    @JoinColumn(name = "fk_id_pagamento", referencedColumnName = "pk_id_pagamento", nullable = false)
    @ManyToOne(optional = false)
    private FinPagamento fkIdPagamento;
    @JoinColumn(name = "fk_id_convenio", referencedColumnName = "pk_id_convenio")
    @ManyToOne
    private GrlConvenio fkIdConvenio;
    @JoinColumn(name = "fk_id_projeto_convenio", referencedColumnName = "pk_id_projeto_convenio")
    @ManyToOne
    private GrlProjetoConvenio fkIdProjetoConvenio;

    public FinPagamentoConvenio() {
    }

    public FinPagamentoConvenio(Long pkIdPagamentoConvenio) {
        this.pkIdPagamentoConvenio = pkIdPagamentoConvenio;
    }

    public Long getPkIdPagamentoConvenio() {
        return pkIdPagamentoConvenio;
    }

    public void setPkIdPagamentoConvenio(Long pkIdPagamentoConvenio) {
        this.pkIdPagamentoConvenio = pkIdPagamentoConvenio;
    }

    public FinPagamento getFkIdPagamento() {
        return fkIdPagamento;
    }

    public void setFkIdPagamento(FinPagamento fkIdPagamento) {
        this.fkIdPagamento = fkIdPagamento;
    }

    public GrlConvenio getFkIdConvenio() {
        return fkIdConvenio;
    }

    public void setFkIdConvenio(GrlConvenio fkIdConvenio) {
        this.fkIdConvenio = fkIdConvenio;
    }

    public GrlProjetoConvenio getFkIdProjetoConvenio() {
        return fkIdProjetoConvenio;
    }

    public void setFkIdProjetoConvenio(GrlProjetoConvenio fkIdProjetoConvenio) {
        this.fkIdProjetoConvenio = fkIdProjetoConvenio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPagamentoConvenio != null ? pkIdPagamentoConvenio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinPagamentoConvenio)) {
            return false;
        }
        FinPagamentoConvenio other = (FinPagamentoConvenio) object;
        if ((this.pkIdPagamentoConvenio == null && other.pkIdPagamentoConvenio != null) || (this.pkIdPagamentoConvenio != null && !this.pkIdPagamentoConvenio.equals(other.pkIdPagamentoConvenio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinPagamentoConvenio[ pkIdPagamentoConvenio=" + pkIdPagamentoConvenio + " ]";
    }
    
}
