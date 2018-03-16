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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "seg_funcionalidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegFuncionalidade.findAll", query = "SELECT s FROM SegFuncionalidade s"),
    @NamedQuery(name = "SegFuncionalidade.findByPkIdFuncionalidade", query = "SELECT s FROM SegFuncionalidade s WHERE s.pkIdFuncionalidade = :pkIdFuncionalidade"),
    @NamedQuery(name = "SegFuncionalidade.findByNome", query = "SELECT s FROM SegFuncionalidade s WHERE s.nome = :nome"),
    @NamedQuery(name = "SegFuncionalidade.findByUrlPadrao", query = "SELECT s FROM SegFuncionalidade s WHERE s.urlPadrao = :urlPadrao")})
public class SegFuncionalidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_funcionalidade", nullable = false)
    private Integer pkIdFuncionalidade;
    @Size(max = 50)
    @Column(name = "nome", length = 50)
    private String nome;
    @Size(max = 250)
    @Column(name = "url_padrao", length = 250)
    private String urlPadrao;
    @OneToMany(mappedBy = "fkIdFuncionalidade")
    private List<SegPrivilegioHasFuncionalidade> segPrivilegioHasFuncionalidadeList;
    @OneToMany(mappedBy = "fkIdFuncionalidade")
    private List<SegPerfilHasFuncionalidade> segPerfilHasFuncionalidadeList;
    @OneToMany(mappedBy = "fkIdFuncionalidadePai")
    private List<SegFuncionalidade> segFuncionalidadeList;
    @JoinColumn(name = "fk_id_funcionalidade_pai", referencedColumnName = "pk_id_funcionalidade")
    @ManyToOne
    private SegFuncionalidade fkIdFuncionalidadePai;
    @JoinColumn(name = "fk_id_tipo_funcionalidade", referencedColumnName = "pk_id_tipo_funcionalidade")
    @ManyToOne
    private SegTipoFuncionalidade fkIdTipoFuncionalidade;

    public SegFuncionalidade() {
    }

    public SegFuncionalidade(Integer pkIdFuncionalidade) {
        this.pkIdFuncionalidade = pkIdFuncionalidade;
    }

    public Integer getPkIdFuncionalidade() {
        return pkIdFuncionalidade;
    }

    public void setPkIdFuncionalidade(Integer pkIdFuncionalidade) {
        this.pkIdFuncionalidade = pkIdFuncionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlPadrao() {
        return urlPadrao;
    }

    public void setUrlPadrao(String urlPadrao) {
        this.urlPadrao = urlPadrao;
    }

    @XmlTransient
    public List<SegPrivilegioHasFuncionalidade> getSegPrivilegioHasFuncionalidadeList() {
        return segPrivilegioHasFuncionalidadeList;
    }

    public void setSegPrivilegioHasFuncionalidadeList(List<SegPrivilegioHasFuncionalidade> segPrivilegioHasFuncionalidadeList) {
        this.segPrivilegioHasFuncionalidadeList = segPrivilegioHasFuncionalidadeList;
    }

    @XmlTransient
    public List<SegPerfilHasFuncionalidade> getSegPerfilHasFuncionalidadeList() {
        return segPerfilHasFuncionalidadeList;
    }

    public void setSegPerfilHasFuncionalidadeList(List<SegPerfilHasFuncionalidade> segPerfilHasFuncionalidadeList) {
        this.segPerfilHasFuncionalidadeList = segPerfilHasFuncionalidadeList;
    }

    @XmlTransient
    public List<SegFuncionalidade> getSegFuncionalidadeList() {
        return segFuncionalidadeList;
    }

    public void setSegFuncionalidadeList(List<SegFuncionalidade> segFuncionalidadeList) {
        this.segFuncionalidadeList = segFuncionalidadeList;
    }

    public SegFuncionalidade getFkIdFuncionalidadePai() {
        return fkIdFuncionalidadePai;
    }

    public void setFkIdFuncionalidadePai(SegFuncionalidade fkIdFuncionalidadePai) {
        this.fkIdFuncionalidadePai = fkIdFuncionalidadePai;
    }

    public SegTipoFuncionalidade getFkIdTipoFuncionalidade() {
        return fkIdTipoFuncionalidade;
    }

    public void setFkIdTipoFuncionalidade(SegTipoFuncionalidade fkIdTipoFuncionalidade) {
        this.fkIdTipoFuncionalidade = fkIdTipoFuncionalidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFuncionalidade != null ? pkIdFuncionalidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegFuncionalidade)) {
            return false;
        }
        SegFuncionalidade other = (SegFuncionalidade) object;
        if ((this.pkIdFuncionalidade == null && other.pkIdFuncionalidade != null) || (this.pkIdFuncionalidade != null && !this.pkIdFuncionalidade.equals(other.pkIdFuncionalidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegFuncionalidade[ pkIdFuncionalidade=" + pkIdFuncionalidade + " ]";
    }
    
}
