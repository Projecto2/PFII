/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_forma_pagamento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhFormaPagamento.findAll", query = "SELECT r FROM RhFormaPagamento r"),
    @NamedQuery(name = "RhFormaPagamento.findByPkIdFormaPagamento", query = "SELECT r FROM RhFormaPagamento r WHERE r.pkIdFormaPagamento = :pkIdFormaPagamento"),
    @NamedQuery(name = "RhFormaPagamento.findByDescricao", query = "SELECT r FROM RhFormaPagamento r WHERE r.descricao = :descricao")})
public class RhFormaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_forma_pagamento", nullable = false)
    private Integer pkIdFormaPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFormaPagamento")
    private List<RhContrato> rhContratoList;

    public RhFormaPagamento() {
    }

    public RhFormaPagamento(Integer pkIdFormaPagamento) {
        this.pkIdFormaPagamento = pkIdFormaPagamento;
    }

    public RhFormaPagamento(Integer pkIdFormaPagamento, String descricao) {
        this.pkIdFormaPagamento = pkIdFormaPagamento;
        this.descricao = descricao;
    }

    public Integer getPkIdFormaPagamento() {
        return pkIdFormaPagamento;
    }

    public void setPkIdFormaPagamento(Integer pkIdFormaPagamento) {
        this.pkIdFormaPagamento = pkIdFormaPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhContrato> getRhContratoList() {
        return rhContratoList;
    }

    public void setRhContratoList(List<RhContrato> rhContratoList) {
        this.rhContratoList = rhContratoList;
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
        if (!(object instanceof RhFormaPagamento)) {
            return false;
        }
        RhFormaPagamento other = (RhFormaPagamento) object;
        if ((this.pkIdFormaPagamento == null && other.pkIdFormaPagamento != null) || (this.pkIdFormaPagamento != null && !this.pkIdFormaPagamento.equals(other.pkIdFormaPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhFormaPagamento[ pkIdFormaPagamento=" + pkIdFormaPagamento + " ]";
    }
    
}
