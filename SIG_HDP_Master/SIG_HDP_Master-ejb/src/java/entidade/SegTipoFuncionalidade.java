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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "seg_tipo_funcionalidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegTipoFuncionalidade.findAll", query = "SELECT s FROM SegTipoFuncionalidade s"),
    @NamedQuery(name = "SegTipoFuncionalidade.findByPkIdTipoFuncionalidade", query = "SELECT s FROM SegTipoFuncionalidade s WHERE s.pkIdTipoFuncionalidade = :pkIdTipoFuncionalidade"),
    @NamedQuery(name = "SegTipoFuncionalidade.findByNome", query = "SELECT s FROM SegTipoFuncionalidade s WHERE s.nome = :nome")})
public class SegTipoFuncionalidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_funcionalidade", nullable = false)
    private Integer pkIdTipoFuncionalidade;
    @Size(max = 50)
    @Column(name = "nome", length = 50)
    private String nome;
    @OneToMany(mappedBy = "fkIdTipoFuncionalidade")
    private List<SegFuncionalidade> segFuncionalidadeList;

    public SegTipoFuncionalidade() {
    }

    public SegTipoFuncionalidade(Integer pkIdTipoFuncionalidade) {
        this.pkIdTipoFuncionalidade = pkIdTipoFuncionalidade;
    }

    public Integer getPkIdTipoFuncionalidade() {
        return pkIdTipoFuncionalidade;
    }

    public void setPkIdTipoFuncionalidade(Integer pkIdTipoFuncionalidade) {
        this.pkIdTipoFuncionalidade = pkIdTipoFuncionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<SegFuncionalidade> getSegFuncionalidadeList() {
        return segFuncionalidadeList;
    }

    public void setSegFuncionalidadeList(List<SegFuncionalidade> segFuncionalidadeList) {
        this.segFuncionalidadeList = segFuncionalidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoFuncionalidade != null ? pkIdTipoFuncionalidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegTipoFuncionalidade)) {
            return false;
        }
        SegTipoFuncionalidade other = (SegTipoFuncionalidade) object;
        if ((this.pkIdTipoFuncionalidade == null && other.pkIdTipoFuncionalidade != null) || (this.pkIdTipoFuncionalidade != null && !this.pkIdTipoFuncionalidade.equals(other.pkIdTipoFuncionalidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegTipoFuncionalidade[ pkIdTipoFuncionalidade=" + pkIdTipoFuncionalidade + " ]";
    }
    
}
