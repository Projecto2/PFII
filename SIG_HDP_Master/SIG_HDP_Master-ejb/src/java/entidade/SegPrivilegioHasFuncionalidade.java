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
@Table(name = "seg_privilegio_has_funcionalidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPrivilegioHasFuncionalidade.findAll", query = "SELECT s FROM SegPrivilegioHasFuncionalidade s"),
    @NamedQuery(name = "SegPrivilegioHasFuncionalidade.findByPkIdPrivilegioHasFuncionalidade", query = "SELECT s FROM SegPrivilegioHasFuncionalidade s WHERE s.pkIdPrivilegioHasFuncionalidade = :pkIdPrivilegioHasFuncionalidade")})
public class SegPrivilegioHasFuncionalidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_privilegio_has_funcionalidade", nullable = false)
    private Integer pkIdPrivilegioHasFuncionalidade;
    @JoinColumn(name = "fk_id_funcionalidade", referencedColumnName = "pk_id_funcionalidade")
    @ManyToOne
    private SegFuncionalidade fkIdFuncionalidade;
    @JoinColumn(name = "fk_id_privilegio", referencedColumnName = "pk_id_privilegio")
    @ManyToOne
    private SegPrivilegio fkIdPrivilegio;

    public SegPrivilegioHasFuncionalidade() {
    }

    public SegPrivilegioHasFuncionalidade(Integer pkIdPrivilegioHasFuncionalidade) {
        this.pkIdPrivilegioHasFuncionalidade = pkIdPrivilegioHasFuncionalidade;
    }

    public Integer getPkIdPrivilegioHasFuncionalidade() {
        return pkIdPrivilegioHasFuncionalidade;
    }

    public void setPkIdPrivilegioHasFuncionalidade(Integer pkIdPrivilegioHasFuncionalidade) {
        this.pkIdPrivilegioHasFuncionalidade = pkIdPrivilegioHasFuncionalidade;
    }

    public SegFuncionalidade getFkIdFuncionalidade() {
        return fkIdFuncionalidade;
    }

    public void setFkIdFuncionalidade(SegFuncionalidade fkIdFuncionalidade) {
        this.fkIdFuncionalidade = fkIdFuncionalidade;
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
        hash += (pkIdPrivilegioHasFuncionalidade != null ? pkIdPrivilegioHasFuncionalidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPrivilegioHasFuncionalidade)) {
            return false;
        }
        SegPrivilegioHasFuncionalidade other = (SegPrivilegioHasFuncionalidade) object;
        if ((this.pkIdPrivilegioHasFuncionalidade == null && other.pkIdPrivilegioHasFuncionalidade != null) || (this.pkIdPrivilegioHasFuncionalidade != null && !this.pkIdPrivilegioHasFuncionalidade.equals(other.pkIdPrivilegioHasFuncionalidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegPrivilegioHasFuncionalidade[ pkIdPrivilegioHasFuncionalidade=" + pkIdPrivilegioHasFuncionalidade + " ]";
    }
    
}
