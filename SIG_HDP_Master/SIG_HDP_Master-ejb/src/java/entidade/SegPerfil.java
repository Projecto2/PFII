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
@Table(name = "seg_perfil", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPerfil.findAll", query = "SELECT s FROM SegPerfil s"),
    @NamedQuery(name = "SegPerfil.findByPkIdPerfil", query = "SELECT s FROM SegPerfil s WHERE s.pkIdPerfil = :pkIdPerfil"),
    @NamedQuery(name = "SegPerfil.findByDescricao", query = "SELECT s FROM SegPerfil s WHERE s.descricao = :descricao")})
public class SegPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_perfil", nullable = false)
    private Integer pkIdPerfil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segPerfil")
    private List<SegPerfilAssociado> segPerfilAssociadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segPerfil1")
    private List<SegPerfilAssociado> segPerfilAssociadoList1;
    @OneToMany(mappedBy = "fkIdPerfil")
    private List<SegPerfilHasPrivilegio> segPerfilHasPrivilegioList;
    @OneToMany(mappedBy = "fkIdPerfil")
    private List<SegPerfilHasFuncionalidade> segPerfilHasFuncionalidadeList;
    @OneToMany(mappedBy = "fkIdPerfil")
    private List<SegConta> segContaList;

    public SegPerfil() {
    }

    public SegPerfil(Integer pkIdPerfil) {
        this.pkIdPerfil = pkIdPerfil;
    }

    public SegPerfil(Integer pkIdPerfil, String descricao) {
        this.pkIdPerfil = pkIdPerfil;
        this.descricao = descricao;
    }

    public Integer getPkIdPerfil() {
        return pkIdPerfil;
    }

    public void setPkIdPerfil(Integer pkIdPerfil) {
        this.pkIdPerfil = pkIdPerfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SegPerfilAssociado> getSegPerfilAssociadoList() {
        return segPerfilAssociadoList;
    }

    public void setSegPerfilAssociadoList(List<SegPerfilAssociado> segPerfilAssociadoList) {
        this.segPerfilAssociadoList = segPerfilAssociadoList;
    }

    @XmlTransient
    public List<SegPerfilAssociado> getSegPerfilAssociadoList1() {
        return segPerfilAssociadoList1;
    }

    public void setSegPerfilAssociadoList1(List<SegPerfilAssociado> segPerfilAssociadoList1) {
        this.segPerfilAssociadoList1 = segPerfilAssociadoList1;
    }

    @XmlTransient
    public List<SegPerfilHasPrivilegio> getSegPerfilHasPrivilegioList() {
        return segPerfilHasPrivilegioList;
    }

    public void setSegPerfilHasPrivilegioList(List<SegPerfilHasPrivilegio> segPerfilHasPrivilegioList) {
        this.segPerfilHasPrivilegioList = segPerfilHasPrivilegioList;
    }

    @XmlTransient
    public List<SegPerfilHasFuncionalidade> getSegPerfilHasFuncionalidadeList() {
        return segPerfilHasFuncionalidadeList;
    }

    public void setSegPerfilHasFuncionalidadeList(List<SegPerfilHasFuncionalidade> segPerfilHasFuncionalidadeList) {
        this.segPerfilHasFuncionalidadeList = segPerfilHasFuncionalidadeList;
    }

    @XmlTransient
    public List<SegConta> getSegContaList() {
        return segContaList;
    }

    public void setSegContaList(List<SegConta> segContaList) {
        this.segContaList = segContaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPerfil != null ? pkIdPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPerfil)) {
            return false;
        }
        SegPerfil other = (SegPerfil) object;
        if ((this.pkIdPerfil == null && other.pkIdPerfil != null) || (this.pkIdPerfil != null && !this.pkIdPerfil.equals(other.pkIdPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegPerfil[ pkIdPerfil=" + pkIdPerfil + " ]";
    }
    
}
