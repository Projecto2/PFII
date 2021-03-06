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
@Table(name = "rh_tipo_contrato", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhTipoContrato.findAll", query = "SELECT r FROM RhTipoContrato r"),
    @NamedQuery(name = "RhTipoContrato.findByPkIdTipoContrato", query = "SELECT r FROM RhTipoContrato r WHERE r.pkIdTipoContrato = :pkIdTipoContrato"),
    @NamedQuery(name = "RhTipoContrato.findByDescricao", query = "SELECT r FROM RhTipoContrato r WHERE r.descricao = :descricao")})
public class RhTipoContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_contrato", nullable = false)
    private Integer pkIdTipoContrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTipoContrato")
    private List<RhContrato> rhContratoList;

    public RhTipoContrato() {
    }

    public RhTipoContrato(Integer pkIdTipoContrato) {
        this.pkIdTipoContrato = pkIdTipoContrato;
    }

    public RhTipoContrato(Integer pkIdTipoContrato, String descricao) {
        this.pkIdTipoContrato = pkIdTipoContrato;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoContrato() {
        return pkIdTipoContrato;
    }

    public void setPkIdTipoContrato(Integer pkIdTipoContrato) {
        this.pkIdTipoContrato = pkIdTipoContrato;
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
        hash += (pkIdTipoContrato != null ? pkIdTipoContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhTipoContrato)) {
            return false;
        }
        RhTipoContrato other = (RhTipoContrato) object;
        if ((this.pkIdTipoContrato == null && other.pkIdTipoContrato != null) || (this.pkIdTipoContrato != null && !this.pkIdTipoContrato.equals(other.pkIdTipoContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhTipoContrato[ pkIdTipoContrato=" + pkIdTipoContrato + " ]";
    }
    
}
