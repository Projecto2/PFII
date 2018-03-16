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
@Table(name = "rh_tipo_estagio", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhTipoEstagio.findAll", query = "SELECT r FROM RhTipoEstagio r"),
    @NamedQuery(name = "RhTipoEstagio.findByPkIdTipoEstagio", query = "SELECT r FROM RhTipoEstagio r WHERE r.pkIdTipoEstagio = :pkIdTipoEstagio"),
    @NamedQuery(name = "RhTipoEstagio.findByDescricao", query = "SELECT r FROM RhTipoEstagio r WHERE r.descricao = :descricao")})
public class RhTipoEstagio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_estagio", nullable = false)
    private Integer pkIdTipoEstagio;
    @Size(max = 60)
    @Column(name = "descricao", length = 60)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoEstagio")
    private List<RhEstagiario> rhEstagiarioList;

    public RhTipoEstagio() {
    }

    public RhTipoEstagio(Integer pkIdTipoEstagio) {
        this.pkIdTipoEstagio = pkIdTipoEstagio;
    }

    public Integer getPkIdTipoEstagio() {
        return pkIdTipoEstagio;
    }

    public void setPkIdTipoEstagio(Integer pkIdTipoEstagio) {
        this.pkIdTipoEstagio = pkIdTipoEstagio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhEstagiario> getRhEstagiarioList() {
        return rhEstagiarioList;
    }

    public void setRhEstagiarioList(List<RhEstagiario> rhEstagiarioList) {
        this.rhEstagiarioList = rhEstagiarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoEstagio != null ? pkIdTipoEstagio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhTipoEstagio)) {
            return false;
        }
        RhTipoEstagio other = (RhTipoEstagio) object;
        if ((this.pkIdTipoEstagio == null && other.pkIdTipoEstagio != null) || (this.pkIdTipoEstagio != null && !this.pkIdTipoEstagio.equals(other.pkIdTipoEstagio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhTipoEstagio[ pkIdTipoEstagio=" + pkIdTipoEstagio + " ]";
    }
    
}
