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
@Table(name = "grl_area_interna", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo_area_interna"}),
    @UniqueConstraint(columnNames = {"descricao_area_interna"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlAreaInterna.findAll", query = "SELECT g FROM GrlAreaInterna g"),
    @NamedQuery(name = "GrlAreaInterna.findByPkIdAreaInterna", query = "SELECT g FROM GrlAreaInterna g WHERE g.pkIdAreaInterna = :pkIdAreaInterna"),
    @NamedQuery(name = "GrlAreaInterna.findByDescricaoAreaInterna", query = "SELECT g FROM GrlAreaInterna g WHERE g.descricaoAreaInterna = :descricaoAreaInterna"),
    @NamedQuery(name = "GrlAreaInterna.findByCodigoAreaInterna", query = "SELECT g FROM GrlAreaInterna g WHERE g.codigoAreaInterna = :codigoAreaInterna"),
    @NamedQuery(name = "GrlAreaInterna.findByObservacoes", query = "SELECT g FROM GrlAreaInterna g WHERE g.observacoes = :observacoes")})
public class GrlAreaInterna implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_area_interna", nullable = false)
    private Integer pkIdAreaInterna;
    @Size(max = 60)
    @Column(name = "descricao_area_interna", length = 60)
    private String descricaoAreaInterna;
    @Size(max = 5)
    @Column(name = "codigo_area_interna", length = 5)
    private String codigoAreaInterna;
    @Size(max = 250)
    @Column(name = "observacoes", length = 250)
    private String observacoes;
    @OneToMany(mappedBy = "fkIdArea")
    private List<AdmsServico> admsServicoList;

    public GrlAreaInterna() {
    }

    public GrlAreaInterna(Integer pkIdAreaInterna) {
        this.pkIdAreaInterna = pkIdAreaInterna;
    }

    public Integer getPkIdAreaInterna() {
        return pkIdAreaInterna;
    }

    public void setPkIdAreaInterna(Integer pkIdAreaInterna) {
        this.pkIdAreaInterna = pkIdAreaInterna;
    }

    public String getDescricaoAreaInterna() {
        return descricaoAreaInterna;
    }

    public void setDescricaoAreaInterna(String descricaoAreaInterna) {
        this.descricaoAreaInterna = descricaoAreaInterna;
    }

    public String getCodigoAreaInterna() {
        return codigoAreaInterna;
    }

    public void setCodigoAreaInterna(String codigoAreaInterna) {
        this.codigoAreaInterna = codigoAreaInterna;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @XmlTransient
    public List<AdmsServico> getAdmsServicoList() {
        return admsServicoList;
    }

    public void setAdmsServicoList(List<AdmsServico> admsServicoList) {
        this.admsServicoList = admsServicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAreaInterna != null ? pkIdAreaInterna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlAreaInterna)) {
            return false;
        }
        GrlAreaInterna other = (GrlAreaInterna) object;
        if ((this.pkIdAreaInterna == null && other.pkIdAreaInterna != null) || (this.pkIdAreaInterna != null && !this.pkIdAreaInterna.equals(other.pkIdAreaInterna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlAreaInterna[ pkIdAreaInterna=" + pkIdAreaInterna + " ]";
    }
    
}
