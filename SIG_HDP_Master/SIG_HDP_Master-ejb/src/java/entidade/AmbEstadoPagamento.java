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
@Table(name = "amb_estado_pagamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbEstadoPagamento.findAll", query = "SELECT a FROM AmbEstadoPagamento a"),
    @NamedQuery(name = "AmbEstadoPagamento.findByPkIdEstadoPagamento", query = "SELECT a FROM AmbEstadoPagamento a WHERE a.pkIdEstadoPagamento = :pkIdEstadoPagamento"),
    @NamedQuery(name = "AmbEstadoPagamento.findByDescricao", query = "SELECT a FROM AmbEstadoPagamento a WHERE a.descricao = :descricao")})
public class AmbEstadoPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_pagamento", nullable = false)
    private Integer pkIdEstadoPagamento;
    @Size(max = 8)
    @Column(name = "descricao", length = 8)
    private String descricao;
    @OneToMany(mappedBy = "fkIdEstadoPagamento")
    private List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList;

    public AmbEstadoPagamento() {
    }

    public AmbEstadoPagamento(Integer pkIdEstadoPagamento) {
        this.pkIdEstadoPagamento = pkIdEstadoPagamento;
    }

    public Integer getPkIdEstadoPagamento() {
        return pkIdEstadoPagamento;
    }

    public void setPkIdEstadoPagamento(Integer pkIdEstadoPagamento) {
        this.pkIdEstadoPagamento = pkIdEstadoPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbPrescricaoMedicaHasProduto> getAmbPrescricaoMedicaHasProdutoList() {
        return ambPrescricaoMedicaHasProdutoList;
    }

    public void setAmbPrescricaoMedicaHasProdutoList(List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList) {
        this.ambPrescricaoMedicaHasProdutoList = ambPrescricaoMedicaHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoPagamento != null ? pkIdEstadoPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbEstadoPagamento)) {
            return false;
        }
        AmbEstadoPagamento other = (AmbEstadoPagamento) object;
        if ((this.pkIdEstadoPagamento == null && other.pkIdEstadoPagamento != null) || (this.pkIdEstadoPagamento != null && !this.pkIdEstadoPagamento.equals(other.pkIdEstadoPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbEstadoPagamento[ pkIdEstadoPagamento=" + pkIdEstadoPagamento + " ]";
    }
    
}
