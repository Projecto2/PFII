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
@Table(name = "fin_tipo_encargo", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinTipoEncargo.findAll", query = "SELECT f FROM FinTipoEncargo f"),
    @NamedQuery(name = "FinTipoEncargo.findByPkIdTipoEncargo", query = "SELECT f FROM FinTipoEncargo f WHERE f.pkIdTipoEncargo = :pkIdTipoEncargo"),
    @NamedQuery(name = "FinTipoEncargo.findByDescricaoTipoEncargo", query = "SELECT f FROM FinTipoEncargo f WHERE f.descricaoTipoEncargo = :descricaoTipoEncargo")})
public class FinTipoEncargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_encargo", nullable = false)
    private Integer pkIdTipoEncargo;
    @Size(max = 100)
    @Column(name = "descricao_tipo_encargo", length = 100)
    private String descricaoTipoEncargo;
    @OneToMany(mappedBy = "fkIdTipoEncargo")
    private List<FinEncargoDevido> finEncargoDevidoList;

    public FinTipoEncargo() {
    }

    public FinTipoEncargo(Integer pkIdTipoEncargo) {
        this.pkIdTipoEncargo = pkIdTipoEncargo;
    }

    public Integer getPkIdTipoEncargo() {
        return pkIdTipoEncargo;
    }

    public void setPkIdTipoEncargo(Integer pkIdTipoEncargo) {
        this.pkIdTipoEncargo = pkIdTipoEncargo;
    }

    public String getDescricaoTipoEncargo() {
        return descricaoTipoEncargo;
    }

    public void setDescricaoTipoEncargo(String descricaoTipoEncargo) {
        this.descricaoTipoEncargo = descricaoTipoEncargo;
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
        hash += (pkIdTipoEncargo != null ? pkIdTipoEncargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinTipoEncargo)) {
            return false;
        }
        FinTipoEncargo other = (FinTipoEncargo) object;
        if ((this.pkIdTipoEncargo == null && other.pkIdTipoEncargo != null) || (this.pkIdTipoEncargo != null && !this.pkIdTipoEncargo.equals(other.pkIdTipoEncargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinTipoEncargo[ pkIdTipoEncargo=" + pkIdTipoEncargo + " ]";
    }
    
}
