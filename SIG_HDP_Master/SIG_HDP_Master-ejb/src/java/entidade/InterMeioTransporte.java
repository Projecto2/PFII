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
@Table(name = "inter_meio_transporte", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterMeioTransporte.findAll", query = "SELECT i FROM InterMeioTransporte i"),
    @NamedQuery(name = "InterMeioTransporte.findByPkIdMeioTransporte", query = "SELECT i FROM InterMeioTransporte i WHERE i.pkIdMeioTransporte = :pkIdMeioTransporte"),
    @NamedQuery(name = "InterMeioTransporte.findByDescricao", query = "SELECT i FROM InterMeioTransporte i WHERE i.descricao = :descricao")})
public class InterMeioTransporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_meio_transporte", nullable = false)
    private Integer pkIdMeioTransporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdMeioTransporte")
    private List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList;

    public InterMeioTransporte() {
    }

    public InterMeioTransporte(Integer pkIdMeioTransporte) {
        this.pkIdMeioTransporte = pkIdMeioTransporte;
    }

    public InterMeioTransporte(Integer pkIdMeioTransporte, String descricao) {
        this.pkIdMeioTransporte = pkIdMeioTransporte;
        this.descricao = descricao;
    }

    public Integer getPkIdMeioTransporte() {
        return pkIdMeioTransporte;
    }

    public void setPkIdMeioTransporte(Integer pkIdMeioTransporte) {
        this.pkIdMeioTransporte = pkIdMeioTransporte;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaDoentes> getInterGuiaTransferenciaDoentesList() {
        return interGuiaTransferenciaDoentesList;
    }

    public void setInterGuiaTransferenciaDoentesList(List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList) {
        this.interGuiaTransferenciaDoentesList = interGuiaTransferenciaDoentesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMeioTransporte != null ? pkIdMeioTransporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterMeioTransporte)) {
            return false;
        }
        InterMeioTransporte other = (InterMeioTransporte) object;
        if ((this.pkIdMeioTransporte == null && other.pkIdMeioTransporte != null) || (this.pkIdMeioTransporte != null && !this.pkIdMeioTransporte.equals(other.pkIdMeioTransporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterMeioTransporte[ pkIdMeioTransporte=" + pkIdMeioTransporte + " ]";
    }
    
}
