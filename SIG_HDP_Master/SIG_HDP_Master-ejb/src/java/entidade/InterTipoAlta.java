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
@Table(name = "inter_tipo_alta", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterTipoAlta.findAll", query = "SELECT i FROM InterTipoAlta i"),
    @NamedQuery(name = "InterTipoAlta.findByDescricao", query = "SELECT i FROM InterTipoAlta i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterTipoAlta.findByPkIdTipoAlta", query = "SELECT i FROM InterTipoAlta i WHERE i.pkIdTipoAlta = :pkIdTipoAlta")})
public class InterTipoAlta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_alta", nullable = false)
    private Integer pkIdTipoAlta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoAlta")
    private List<InterRegistoSaida> interRegistoSaidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoAlta")
    private List<InterTituloAlta> interTituloAltaList;

    public InterTipoAlta() {
    }

    public InterTipoAlta(Integer pkIdTipoAlta) {
        this.pkIdTipoAlta = pkIdTipoAlta;
    }

    public InterTipoAlta(Integer pkIdTipoAlta, String descricao) {
        this.pkIdTipoAlta = pkIdTipoAlta;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdTipoAlta() {
        return pkIdTipoAlta;
    }

    public void setPkIdTipoAlta(Integer pkIdTipoAlta) {
        this.pkIdTipoAlta = pkIdTipoAlta;
    }

    @XmlTransient
    public List<InterRegistoSaida> getInterRegistoSaidaList() {
        return interRegistoSaidaList;
    }

    public void setInterRegistoSaidaList(List<InterRegistoSaida> interRegistoSaidaList) {
        this.interRegistoSaidaList = interRegistoSaidaList;
    }

    @XmlTransient
    public List<InterTituloAlta> getInterTituloAltaList() {
        return interTituloAltaList;
    }

    public void setInterTituloAltaList(List<InterTituloAlta> interTituloAltaList) {
        this.interTituloAltaList = interTituloAltaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoAlta != null ? pkIdTipoAlta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterTipoAlta)) {
            return false;
        }
        InterTipoAlta other = (InterTipoAlta) object;
        if ((this.pkIdTipoAlta == null && other.pkIdTipoAlta != null) || (this.pkIdTipoAlta != null && !this.pkIdTipoAlta.equals(other.pkIdTipoAlta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterTipoAlta[ pkIdTipoAlta=" + pkIdTipoAlta + " ]";
    }
    
}
