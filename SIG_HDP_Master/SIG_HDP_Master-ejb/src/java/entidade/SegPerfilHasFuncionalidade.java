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
@Table(name = "seg_perfil_has_funcionalidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPerfilHasFuncionalidade.findAll", query = "SELECT s FROM SegPerfilHasFuncionalidade s"),
    @NamedQuery(name = "SegPerfilHasFuncionalidade.findByPkIdPerfilHasFuncionalidade", query = "SELECT s FROM SegPerfilHasFuncionalidade s WHERE s.pkIdPerfilHasFuncionalidade = :pkIdPerfilHasFuncionalidade"),
    @NamedQuery(name = "SegPerfilHasFuncionalidade.findByStatus", query = "SELECT s FROM SegPerfilHasFuncionalidade s WHERE s.status = :status")})
public class SegPerfilHasFuncionalidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_perfil_has_funcionalidade", nullable = false)
    private Integer pkIdPerfilHasFuncionalidade;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "fk_id_funcionalidade", referencedColumnName = "pk_id_funcionalidade")
    @ManyToOne
    private SegFuncionalidade fkIdFuncionalidade;
    @JoinColumn(name = "fk_id_perfil", referencedColumnName = "pk_id_perfil")
    @ManyToOne
    private SegPerfil fkIdPerfil;

    public SegPerfilHasFuncionalidade() {
    }

    public SegPerfilHasFuncionalidade(Integer pkIdPerfilHasFuncionalidade) {
        this.pkIdPerfilHasFuncionalidade = pkIdPerfilHasFuncionalidade;
    }

    public Integer getPkIdPerfilHasFuncionalidade() {
        return pkIdPerfilHasFuncionalidade;
    }

    public void setPkIdPerfilHasFuncionalidade(Integer pkIdPerfilHasFuncionalidade) {
        this.pkIdPerfilHasFuncionalidade = pkIdPerfilHasFuncionalidade;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SegFuncionalidade getFkIdFuncionalidade() {
        return fkIdFuncionalidade;
    }

    public void setFkIdFuncionalidade(SegFuncionalidade fkIdFuncionalidade) {
        this.fkIdFuncionalidade = fkIdFuncionalidade;
    }

    public SegPerfil getFkIdPerfil() {
        return fkIdPerfil;
    }

    public void setFkIdPerfil(SegPerfil fkIdPerfil) {
        this.fkIdPerfil = fkIdPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPerfilHasFuncionalidade != null ? pkIdPerfilHasFuncionalidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPerfilHasFuncionalidade)) {
            return false;
        }
        SegPerfilHasFuncionalidade other = (SegPerfilHasFuncionalidade) object;
        if ((this.pkIdPerfilHasFuncionalidade == null && other.pkIdPerfilHasFuncionalidade != null) || (this.pkIdPerfilHasFuncionalidade != null && !this.pkIdPerfilHasFuncionalidade.equals(other.pkIdPerfilHasFuncionalidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegPerfilHasFuncionalidade[ pkIdPerfilHasFuncionalidade=" + pkIdPerfilHasFuncionalidade + " ]";
    }
    
}
