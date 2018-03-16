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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "seg_privilegio", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPrivilegio.findAll", query = "SELECT s FROM SegPrivilegio s"),
    @NamedQuery(name = "SegPrivilegio.findByPkIdPrivilegio", query = "SELECT s FROM SegPrivilegio s WHERE s.pkIdPrivilegio = :pkIdPrivilegio"),
    @NamedQuery(name = "SegPrivilegio.findByDescricao", query = "SELECT s FROM SegPrivilegio s WHERE s.descricao = :descricao")})
public class SegPrivilegio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_privilegio", nullable = false)
    private Integer pkIdPrivilegio;
    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;
    @OneToMany(mappedBy = "fkIdPrivilegio")
    private List<SegPrivilegioHasFuncionalidade> segPrivilegioHasFuncionalidadeList;
    @OneToMany(mappedBy = "fkIdPrivilegio")
    private List<SegPerfilHasPrivilegio> segPerfilHasPrivilegioList;

    public SegPrivilegio() {
    }

    public SegPrivilegio(Integer pkIdPrivilegio) {
        this.pkIdPrivilegio = pkIdPrivilegio;
    }

    public Integer getPkIdPrivilegio() {
        return pkIdPrivilegio;
    }

    public void setPkIdPrivilegio(Integer pkIdPrivilegio) {
        this.pkIdPrivilegio = pkIdPrivilegio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SegPrivilegioHasFuncionalidade> getSegPrivilegioHasFuncionalidadeList() {
        return segPrivilegioHasFuncionalidadeList;
    }

    public void setSegPrivilegioHasFuncionalidadeList(List<SegPrivilegioHasFuncionalidade> segPrivilegioHasFuncionalidadeList) {
        this.segPrivilegioHasFuncionalidadeList = segPrivilegioHasFuncionalidadeList;
    }

    @XmlTransient
    public List<SegPerfilHasPrivilegio> getSegPerfilHasPrivilegioList() {
        return segPerfilHasPrivilegioList;
    }

    public void setSegPerfilHasPrivilegioList(List<SegPerfilHasPrivilegio> segPerfilHasPrivilegioList) {
        this.segPerfilHasPrivilegioList = segPerfilHasPrivilegioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPrivilegio != null ? pkIdPrivilegio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPrivilegio)) {
            return false;
        }
        SegPrivilegio other = (SegPrivilegio) object;
        if ((this.pkIdPrivilegio == null && other.pkIdPrivilegio != null) || (this.pkIdPrivilegio != null && !this.pkIdPrivilegio.equals(other.pkIdPrivilegio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegPrivilegio[ pkIdPrivilegio=" + pkIdPrivilegio + " ]";
    }
    
}
