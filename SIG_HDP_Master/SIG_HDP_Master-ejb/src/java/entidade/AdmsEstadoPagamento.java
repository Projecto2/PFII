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
@Table(name = "adms_estado_pagamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsEstadoPagamento.findAll", query = "SELECT a FROM AdmsEstadoPagamento a"),
    @NamedQuery(name = "AdmsEstadoPagamento.findByPkIdEstadoPagamento", query = "SELECT a FROM AdmsEstadoPagamento a WHERE a.pkIdEstadoPagamento = :pkIdEstadoPagamento"),
    @NamedQuery(name = "AdmsEstadoPagamento.findByDescricaoEstadoPagamento", query = "SELECT a FROM AdmsEstadoPagamento a WHERE a.descricaoEstadoPagamento = :descricaoEstadoPagamento")})
public class AdmsEstadoPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_pagamento", nullable = false)
    private Integer pkIdEstadoPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao_estado_pagamento", nullable = false, length = 50)
    private String descricaoEstadoPagamento;
    @OneToMany(mappedBy = "fkIdEstadoPagamento")
    private List<AdmsServicoSolicitado> admsServicoSolicitadoList;

    public AdmsEstadoPagamento() {
    }

    public AdmsEstadoPagamento(Integer pkIdEstadoPagamento) {
        this.pkIdEstadoPagamento = pkIdEstadoPagamento;
    }

    public AdmsEstadoPagamento(Integer pkIdEstadoPagamento, String descricaoEstadoPagamento) {
        this.pkIdEstadoPagamento = pkIdEstadoPagamento;
        this.descricaoEstadoPagamento = descricaoEstadoPagamento;
    }

    public Integer getPkIdEstadoPagamento() {
        return pkIdEstadoPagamento;
    }

    public void setPkIdEstadoPagamento(Integer pkIdEstadoPagamento) {
        this.pkIdEstadoPagamento = pkIdEstadoPagamento;
    }

    public String getDescricaoEstadoPagamento() {
        return descricaoEstadoPagamento;
    }

    public void setDescricaoEstadoPagamento(String descricaoEstadoPagamento) {
        this.descricaoEstadoPagamento = descricaoEstadoPagamento;
    }

    @XmlTransient
    public List<AdmsServicoSolicitado> getAdmsServicoSolicitadoList() {
        return admsServicoSolicitadoList;
    }

    public void setAdmsServicoSolicitadoList(List<AdmsServicoSolicitado> admsServicoSolicitadoList) {
        this.admsServicoSolicitadoList = admsServicoSolicitadoList;
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
        if (!(object instanceof AdmsEstadoPagamento)) {
            return false;
        }
        AdmsEstadoPagamento other = (AdmsEstadoPagamento) object;
        if ((this.pkIdEstadoPagamento == null && other.pkIdEstadoPagamento != null) || (this.pkIdEstadoPagamento != null && !this.pkIdEstadoPagamento.equals(other.pkIdEstadoPagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsEstadoPagamento[ pkIdEstadoPagamento=" + pkIdEstadoPagamento + " ]";
    }
    
}
