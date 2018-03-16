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
@Table(name = "farm_estado", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmEstado.findAll", query = "SELECT f FROM FarmEstado f"),
    @NamedQuery(name = "FarmEstado.findByPkIdEstado", query = "SELECT f FROM FarmEstado f WHERE f.pkIdEstado = :pkIdEstado"),
    @NamedQuery(name = "FarmEstado.findByDescricao", query = "SELECT f FROM FarmEstado f WHERE f.descricao = :descricao")})
public class FarmEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado", nullable = false)
    private Integer pkIdEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstado")
    private List<FarmEstadoNotificacao> farmEstadoNotificacaoList;

    public FarmEstado() {
    }

    public FarmEstado(Integer pkIdEstado) {
        this.pkIdEstado = pkIdEstado;
    }

    public FarmEstado(Integer pkIdEstado, String descricao) {
        this.pkIdEstado = pkIdEstado;
        this.descricao = descricao;
    }

    public Integer getPkIdEstado() {
        return pkIdEstado;
    }

    public void setPkIdEstado(Integer pkIdEstado) {
        this.pkIdEstado = pkIdEstado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmEstadoNotificacao> getFarmEstadoNotificacaoList() {
        return farmEstadoNotificacaoList;
    }

    public void setFarmEstadoNotificacaoList(List<FarmEstadoNotificacao> farmEstadoNotificacaoList) {
        this.farmEstadoNotificacaoList = farmEstadoNotificacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstado != null ? pkIdEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmEstado)) {
            return false;
        }
        FarmEstado other = (FarmEstado) object;
        if ((this.pkIdEstado == null && other.pkIdEstado != null) || (this.pkIdEstado != null && !this.pkIdEstado.equals(other.pkIdEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmEstado[ pkIdEstado=" + pkIdEstado + " ]";
    }
    
}
