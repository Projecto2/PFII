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
@Table(name = "fin_tipo_pagamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinTipoPagamento.findAll", query = "SELECT f FROM FinTipoPagamento f"),
    @NamedQuery(name = "FinTipoPagamento.findByPkIdTipoPagamento", query = "SELECT f FROM FinTipoPagamento f WHERE f.pkIdTipoPagamento = :pkIdTipoPagamento"),
    @NamedQuery(name = "FinTipoPagamento.findByDescricaoTipoPagamento", query = "SELECT f FROM FinTipoPagamento f WHERE f.descricaoTipoPagamento = :descricaoTipoPagamento"),
    @NamedQuery(name = "FinTipoPagamento.findByFatorDeMultiplicacao", query = "SELECT f FROM FinTipoPagamento f WHERE f.fatorDeMultiplicacao = :fatorDeMultiplicacao")})
public class FinTipoPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_pagamento", nullable = false)
    private Integer pkIdTipoPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao_tipo_pagamento", nullable = false, length = 50)
    private String descricaoTipoPagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fator_de_multiplicacao", precision = 17, scale = 17)
    private Double fatorDeMultiplicacao;
    @OneToMany(mappedBy = "fkIdTipoPagamento")
    private List<AdmsTipoPagamentoSubprocesso> admsTipoPagamentoSubprocessoList;

    public FinTipoPagamento() {
    }

    public FinTipoPagamento(Integer pkIdTipoPagamento) {
        this.pkIdTipoPagamento = pkIdTipoPagamento;
    }

    public FinTipoPagamento(Integer pkIdTipoPagamento, String descricaoTipoPagamento) {
        this.pkIdTipoPagamento = pkIdTipoPagamento;
        this.descricaoTipoPagamento = descricaoTipoPagamento;
    }

    public Integer getPkIdTipoPagamento() {
        return pkIdTipoPagamento;
    }

    public void setPkIdTipoPagamento(Integer pkIdTipoPagamento) {
        this.pkIdTipoPagamento = pkIdTipoPagamento;
    }

    public String getDescricaoTipoPagamento() {
        return descricaoTipoPagamento;
    }

    public void setDescricaoTipoPagamento(String descricaoTipoPagamento) {
        this.descricaoTipoPagamento = descricaoTipoPagamento;
    }

    public Double getFatorDeMultiplicacao() {
        return fatorDeMultiplicacao;
    }

    public void setFatorDeMultiplicacao(Double fatorDeMultiplicacao) {
        this.fatorDeMultiplicacao = fatorDeMultiplicacao;
    }

    @XmlTransient
    public List<AdmsTipoPagamentoSubprocesso> getAdmsTipoPagamentoSubprocessoList() {
        return admsTipoPagamentoSubprocessoList;
    }

    public void setAdmsTipoPagamentoSubprocessoList(List<AdmsTipoPagamentoSubprocesso> admsTipoPagamentoSubprocessoList) {
        this.admsTipoPagamentoSubprocessoList = admsTipoPagamentoSubprocessoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoPagamento != null ? pkIdTipoPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinTipoPagamento)) {
            return false;
        }
        FinTipoPagamento other = (FinTipoPagamento) object;
        if ((this.pkIdTipoPagamento == null && other.pkIdTipoPagamento != null) || (this.pkIdTipoPagamento != null && !this.pkIdTipoPagamento.equals(other.pkIdTipoPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinTipoPagamento[ pkIdTipoPagamento=" + pkIdTipoPagamento + " ]";
    }
    
}
