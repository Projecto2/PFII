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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_estado_mosquiteiro", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEstadoMosquiteiro.findAll", query = "SELECT s FROM SupiEstadoMosquiteiro s"),
    @NamedQuery(name = "SupiEstadoMosquiteiro.findByPkIdEstadoMosquiteiro", query = "SELECT s FROM SupiEstadoMosquiteiro s WHERE s.pkIdEstadoMosquiteiro = :pkIdEstadoMosquiteiro"),
    @NamedQuery(name = "SupiEstadoMosquiteiro.findByDescricao", query = "SELECT s FROM SupiEstadoMosquiteiro s WHERE s.descricao = :descricao")})
public class SupiEstadoMosquiteiro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estado_mosquiteiro", nullable = false)
    private Integer pkIdEstadoMosquiteiro;
    @Size(max = 50)
    @Column(name = "descricao", length = 50)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoMosquiteiro")
    private List<SupiMosquiteiro> supiMosquiteiroList;

    public SupiEstadoMosquiteiro() {
    }

    public SupiEstadoMosquiteiro(Integer pkIdEstadoMosquiteiro) {
        this.pkIdEstadoMosquiteiro = pkIdEstadoMosquiteiro;
    }

    public Integer getPkIdEstadoMosquiteiro() {
        return pkIdEstadoMosquiteiro;
    }

    public void setPkIdEstadoMosquiteiro(Integer pkIdEstadoMosquiteiro) {
        this.pkIdEstadoMosquiteiro = pkIdEstadoMosquiteiro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiMosquiteiro> getSupiMosquiteiroList() {
        return supiMosquiteiroList;
    }

    public void setSupiMosquiteiroList(List<SupiMosquiteiro> supiMosquiteiroList) {
        this.supiMosquiteiroList = supiMosquiteiroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoMosquiteiro != null ? pkIdEstadoMosquiteiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEstadoMosquiteiro)) {
            return false;
        }
        SupiEstadoMosquiteiro other = (SupiEstadoMosquiteiro) object;
        if ((this.pkIdEstadoMosquiteiro == null && other.pkIdEstadoMosquiteiro != null) || (this.pkIdEstadoMosquiteiro != null && !this.pkIdEstadoMosquiteiro.equals(other.pkIdEstadoMosquiteiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEstadoMosquiteiro[ pkIdEstadoMosquiteiro=" + pkIdEstadoMosquiteiro + " ]";
    }
    
}
