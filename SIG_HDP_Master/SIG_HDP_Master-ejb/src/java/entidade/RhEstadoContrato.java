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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_estado_contrato", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEstadoContrato.findAll", query = "SELECT r FROM RhEstadoContrato r"),
    @NamedQuery(name = "RhEstadoContrato.findByPkIdEstadoContrato", query = "SELECT r FROM RhEstadoContrato r WHERE r.pkIdEstadoContrato = :pkIdEstadoContrato"),
    @NamedQuery(name = "RhEstadoContrato.findByDescricao", query = "SELECT r FROM RhEstadoContrato r WHERE r.descricao = :descricao")})
public class RhEstadoContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_contrato", nullable = false)
    private Integer pkIdEstadoContrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoContrato")
    private List<RhContrato> rhContratoList;

    public RhEstadoContrato() {
    }

    public RhEstadoContrato(Integer pkIdEstadoContrato) {
        this.pkIdEstadoContrato = pkIdEstadoContrato;
    }

    public RhEstadoContrato(Integer pkIdEstadoContrato, String descricao) {
        this.pkIdEstadoContrato = pkIdEstadoContrato;
        this.descricao = descricao;
    }

    public Integer getPkIdEstadoContrato() {
        return pkIdEstadoContrato;
    }

    public void setPkIdEstadoContrato(Integer pkIdEstadoContrato) {
        this.pkIdEstadoContrato = pkIdEstadoContrato;
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
        hash += (pkIdEstadoContrato != null ? pkIdEstadoContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEstadoContrato)) {
            return false;
        }
        RhEstadoContrato other = (RhEstadoContrato) object;
        if ((this.pkIdEstadoContrato == null && other.pkIdEstadoContrato != null) || (this.pkIdEstadoContrato != null && !this.pkIdEstadoContrato.equals(other.pkIdEstadoContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEstadoContrato[ pkIdEstadoContrato=" + pkIdEstadoContrato + " ]";
    }
    
}
