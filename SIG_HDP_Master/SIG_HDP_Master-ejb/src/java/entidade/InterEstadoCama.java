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
@Table(name = "inter_estado_cama", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterEstadoCama.findAll", query = "SELECT i FROM InterEstadoCama i"),
    @NamedQuery(name = "InterEstadoCama.findByDescricao", query = "SELECT i FROM InterEstadoCama i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterEstadoCama.findByPkIdEstadoCama", query = "SELECT i FROM InterEstadoCama i WHERE i.pkIdEstadoCama = :pkIdEstadoCama")})
public class InterEstadoCama implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_cama", nullable = false)
    private Integer pkIdEstadoCama;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoCama")
    private List<InterCamaInternamento> interCamaInternamentoList;

    public InterEstadoCama() {
    }

    public InterEstadoCama(Integer pkIdEstadoCama) {
        this.pkIdEstadoCama = pkIdEstadoCama;
    }

    public InterEstadoCama(Integer pkIdEstadoCama, String descricao) {
        this.pkIdEstadoCama = pkIdEstadoCama;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdEstadoCama() {
        return pkIdEstadoCama;
    }

    public void setPkIdEstadoCama(Integer pkIdEstadoCama) {
        this.pkIdEstadoCama = pkIdEstadoCama;
    }

    @XmlTransient
    public List<InterCamaInternamento> getInterCamaInternamentoList() {
        return interCamaInternamentoList;
    }

    public void setInterCamaInternamentoList(List<InterCamaInternamento> interCamaInternamentoList) {
        this.interCamaInternamentoList = interCamaInternamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoCama != null ? pkIdEstadoCama.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterEstadoCama)) {
            return false;
        }
        InterEstadoCama other = (InterEstadoCama) object;
        if ((this.pkIdEstadoCama == null && other.pkIdEstadoCama != null) || (this.pkIdEstadoCama != null && !this.pkIdEstadoCama.equals(other.pkIdEstadoCama))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterEstadoCama[ pkIdEstadoCama=" + pkIdEstadoCama + " ]";
    }
    
}
