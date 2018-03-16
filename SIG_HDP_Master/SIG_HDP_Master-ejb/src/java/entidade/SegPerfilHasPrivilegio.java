/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "seg_perfil_has_privilegio", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPerfilHasPrivilegio.findAll", query = "SELECT s FROM SegPerfilHasPrivilegio s"),
    @NamedQuery(name = "SegPerfilHasPrivilegio.findByPkIdPerfilHasPrivilegio", query = "SELECT s FROM SegPerfilHasPrivilegio s WHERE s.pkIdPerfilHasPrivilegio = :pkIdPerfilHasPrivilegio")})
public class SegPerfilHasPrivilegio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_perfil_has_privilegio", nullable = false)
    private Integer pkIdPerfilHasPrivilegio;
    @JoinColumn(name = "fk_id_perfil", referencedColumnName = "pk_id_perfil")
    @ManyToOne
    private SegPerfil fkIdPerfil;
    @JoinColumn(name = "fk_id_privilegio", referencedColumnName = "pk_id_privilegio")
    @ManyToOne
    private SegPrivilegio fkIdPrivilegio;

    public SegPerfilHasPrivilegio() {
    }

    public SegPerfilHasPrivilegio(Integer pkIdPerfilHasPrivilegio) {
        this.pkIdPerfilHasPrivilegio = pkIdPerfilHasPrivilegio;
    }

    public Integer getPkIdPerfilHasPrivilegio() {
        return pkIdPerfilHasPrivilegio;
    }

    public void setPkIdPerfilHasPrivilegio(Integer pkIdPerfilHasPrivilegio) {
        this.pkIdPerfilHasPrivilegio = pkIdPerfilHasPrivilegio;
    }

    public SegPerfil getFkIdPerfil() {
        return fkIdPerfil;
    }

    public void setFkIdPerfil(SegPerfil fkIdPerfil) {
        this.fkIdPerfil = fkIdPerfil;
    }

    public SegPrivilegio getFkIdPrivilegio() {
        return fkIdPrivilegio;
    }

    public void setFkIdPrivilegio(SegPrivilegio fkIdPrivilegio) {
        this.fkIdPrivilegio = fkIdPrivilegio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPerfilHasPrivilegio != null ? pkIdPerfilHasPrivilegio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPerfilHasPrivilegio)) {
            return false;
        }
        SegPerfilHasPrivilegio other = (SegPerfilHasPrivilegio) object;
        if ((this.pkIdPerfilHasPrivilegio == null && other.pkIdPerfilHasPrivilegio != null) || (this.pkIdPerfilHasPrivilegio != null && !this.pkIdPerfilHasPrivilegio.equals(other.pkIdPerfilHasPrivilegio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegPerfilHasPrivilegio[ pkIdPerfilHasPrivilegio=" + pkIdPerfilHasPrivilegio + " ]";
    }
    
}
