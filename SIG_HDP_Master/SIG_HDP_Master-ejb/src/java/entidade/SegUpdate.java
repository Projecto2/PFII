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
@Table(name = "seg_update", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegUpdate.findAll", query = "SELECT s FROM SegUpdate s"),
    @NamedQuery(name = "SegUpdate.findByPkIdUpdates", query = "SELECT s FROM SegUpdate s WHERE s.pkIdUpdates = :pkIdUpdates"),
    @NamedQuery(name = "SegUpdate.findByFuncionalidade", query = "SELECT s FROM SegUpdate s WHERE s.funcionalidade = :funcionalidade"),
    @NamedQuery(name = "SegUpdate.findByTipoFuncionalidade", query = "SELECT s FROM SegUpdate s WHERE s.tipoFuncionalidade = :tipoFuncionalidade")})
public class SegUpdate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_updates", nullable = false)
    private Integer pkIdUpdates;
    @Column(name = "funcionalidade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funcionalidade;
    @Column(name = "tipo_funcionalidade")
    @Temporal(TemporalType.TIME)
    private Date tipoFuncionalidade;

    public SegUpdate() {
    }

    public SegUpdate(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Integer getPkIdUpdates() {
        return pkIdUpdates;
    }

    public void setPkIdUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Date getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(Date funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public Date getTipoFuncionalidade() {
        return tipoFuncionalidade;
    }

    public void setTipoFuncionalidade(Date tipoFuncionalidade) {
        this.tipoFuncionalidade = tipoFuncionalidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUpdates != null ? pkIdUpdates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUpdate)) {
            return false;
        }
        SegUpdate other = (SegUpdate) object;
        if ((this.pkIdUpdates == null && other.pkIdUpdates != null) || (this.pkIdUpdates != null && !this.pkIdUpdates.equals(other.pkIdUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegUpdate[ pkIdUpdates=" + pkIdUpdates + " ]";
    }
    
}
