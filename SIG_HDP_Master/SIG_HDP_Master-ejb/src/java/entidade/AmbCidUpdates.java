/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_cid_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidUpdates.findAll", query = "SELECT a FROM AmbCidUpdates a"),
    @NamedQuery(name = "AmbCidUpdates.findByCapitulos", query = "SELECT a FROM AmbCidUpdates a WHERE a.capitulos = :capitulos"),
    @NamedQuery(name = "AmbCidUpdates.findByAgrupamentos", query = "SELECT a FROM AmbCidUpdates a WHERE a.agrupamentos = :agrupamentos"),
    @NamedQuery(name = "AmbCidUpdates.findByCategorias", query = "SELECT a FROM AmbCidUpdates a WHERE a.categorias = :categorias"),
    @NamedQuery(name = "AmbCidUpdates.findBySubcategorias", query = "SELECT a FROM AmbCidUpdates a WHERE a.subcategorias = :subcategorias"),
    @NamedQuery(name = "AmbCidUpdates.findByPkIdCidUpdates", query = "SELECT a FROM AmbCidUpdates a WHERE a.pkIdCidUpdates = :pkIdCidUpdates"),
    @NamedQuery(name = "AmbCidUpdates.findByDoencasPrioridades", query = "SELECT a FROM AmbCidUpdates a WHERE a.doencasPrioridades = :doencasPrioridades"),
    @NamedQuery(name = "AmbCidUpdates.findByPerfilTipos", query = "SELECT a FROM AmbCidUpdates a WHERE a.perfilTipos = :perfilTipos")})
public class AmbCidUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "capitulos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date capitulos;
    @Column(name = "agrupamentos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agrupamentos;
    @Column(name = "categorias")
    @Temporal(TemporalType.TIMESTAMP)
    private Date categorias;
    @Column(name = "subcategorias")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subcategorias;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_cid_updates", nullable = false)
    private Integer pkIdCidUpdates;
    @Column(name = "doencas_prioridades")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doencasPrioridades;
    @Column(name = "perfil_tipos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perfilTipos;

    public AmbCidUpdates() {
    }

    public AmbCidUpdates(Integer pkIdCidUpdates) {
        this.pkIdCidUpdates = pkIdCidUpdates;
    }

    public Date getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Date capitulos) {
        this.capitulos = capitulos;
    }

    public Date getAgrupamentos() {
        return agrupamentos;
    }

    public void setAgrupamentos(Date agrupamentos) {
        this.agrupamentos = agrupamentos;
    }

    public Date getCategorias() {
        return categorias;
    }

    public void setCategorias(Date categorias) {
        this.categorias = categorias;
    }

    public Date getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(Date subcategorias) {
        this.subcategorias = subcategorias;
    }

    public Integer getPkIdCidUpdates() {
        return pkIdCidUpdates;
    }

    public void setPkIdCidUpdates(Integer pkIdCidUpdates) {
        this.pkIdCidUpdates = pkIdCidUpdates;
    }

    public Date getDoencasPrioridades() {
        return doencasPrioridades;
    }

    public void setDoencasPrioridades(Date doencasPrioridades) {
        this.doencasPrioridades = doencasPrioridades;
    }

    public Date getPerfilTipos() {
        return perfilTipos;
    }

    public void setPerfilTipos(Date perfilTipos) {
        this.perfilTipos = perfilTipos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCidUpdates != null ? pkIdCidUpdates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidUpdates)) {
            return false;
        }
        AmbCidUpdates other = (AmbCidUpdates) object;
        if ((this.pkIdCidUpdates == null && other.pkIdCidUpdates != null) || (this.pkIdCidUpdates != null && !this.pkIdCidUpdates.equals(other.pkIdCidUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidUpdates[ pkIdCidUpdates=" + pkIdCidUpdates + " ]";
    }
    
}
