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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_tipo_pagamento_subprocesso", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsTipoPagamentoSubprocesso.findAll", query = "SELECT a FROM AdmsTipoPagamentoSubprocesso a"),
    @NamedQuery(name = "AdmsTipoPagamentoSubprocesso.findByData", query = "SELECT a FROM AdmsTipoPagamentoSubprocesso a WHERE a.data = :data"),
    @NamedQuery(name = "AdmsTipoPagamentoSubprocesso.findByPkIdTipoPagamentoSubprocesso", query = "SELECT a FROM AdmsTipoPagamentoSubprocesso a WHERE a.pkIdTipoPagamentoSubprocesso = :pkIdTipoPagamentoSubprocesso")})
public class AdmsTipoPagamentoSubprocesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_pagamento_subprocesso", nullable = false)
    private Long pkIdTipoPagamentoSubprocesso;
    @JoinColumn(name = "fk_id_subprocesso", referencedColumnName = "pk_id_subprocesso")
    @ManyToOne
    private AdmsSubprocesso fkIdSubprocesso;
    @JoinColumn(name = "fk_id_tipo_pagamento", referencedColumnName = "pk_id_tipo_pagamento")
    @ManyToOne
    private FinTipoPagamento fkIdTipoPagamento;

    public AdmsTipoPagamentoSubprocesso() {
    }

    public AdmsTipoPagamentoSubprocesso(Long pkIdTipoPagamentoSubprocesso) {
        this.pkIdTipoPagamentoSubprocesso = pkIdTipoPagamentoSubprocesso;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getPkIdTipoPagamentoSubprocesso() {
        return pkIdTipoPagamentoSubprocesso;
    }

    public void setPkIdTipoPagamentoSubprocesso(Long pkIdTipoPagamentoSubprocesso) {
        this.pkIdTipoPagamentoSubprocesso = pkIdTipoPagamentoSubprocesso;
    }

    public AdmsSubprocesso getFkIdSubprocesso() {
        return fkIdSubprocesso;
    }

    public void setFkIdSubprocesso(AdmsSubprocesso fkIdSubprocesso) {
        this.fkIdSubprocesso = fkIdSubprocesso;
    }

    public FinTipoPagamento getFkIdTipoPagamento() {
        return fkIdTipoPagamento;
    }

    public void setFkIdTipoPagamento(FinTipoPagamento fkIdTipoPagamento) {
        this.fkIdTipoPagamento = fkIdTipoPagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoPagamentoSubprocesso != null ? pkIdTipoPagamentoSubprocesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsTipoPagamentoSubprocesso)) {
            return false;
        }
        AdmsTipoPagamentoSubprocesso other = (AdmsTipoPagamentoSubprocesso) object;
        if ((this.pkIdTipoPagamentoSubprocesso == null && other.pkIdTipoPagamentoSubprocesso != null) || (this.pkIdTipoPagamentoSubprocesso != null && !this.pkIdTipoPagamentoSubprocesso.equals(other.pkIdTipoPagamentoSubprocesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsTipoPagamentoSubprocesso[ pkIdTipoPagamentoSubprocesso=" + pkIdTipoPagamentoSubprocesso + " ]";
    }
    
}
