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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_sexo", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlSexo.findAll", query = "SELECT g FROM GrlSexo g"),
    @NamedQuery(name = "GrlSexo.findByPkIdSexo", query = "SELECT g FROM GrlSexo g WHERE g.pkIdSexo = :pkIdSexo"),
    @NamedQuery(name = "GrlSexo.findByCodigoSexo", query = "SELECT g FROM GrlSexo g WHERE g.codigoSexo = :codigoSexo"),
    @NamedQuery(name = "GrlSexo.findByDescricao", query = "SELECT g FROM GrlSexo g WHERE g.descricao = :descricao")})
public class GrlSexo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_sexo", nullable = false)
    private Integer pkIdSexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "codigo_sexo", nullable = false, length = 5)
    private String codigoSexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdSexo")
    private List<GrlPessoa> grlPessoaList;

    public GrlSexo() {
    }

    public GrlSexo(Integer pkIdSexo) {
        this.pkIdSexo = pkIdSexo;
    }

    public GrlSexo(Integer pkIdSexo, String codigoSexo, String descricao) {
        this.pkIdSexo = pkIdSexo;
        this.codigoSexo = codigoSexo;
        this.descricao = descricao;
    }

    public Integer getPkIdSexo() {
        return pkIdSexo;
    }

    public void setPkIdSexo(Integer pkIdSexo) {
        this.pkIdSexo = pkIdSexo;
    }

    public String getCodigoSexo() {
        return codigoSexo;
    }

    public void setCodigoSexo(String codigoSexo) {
        this.codigoSexo = codigoSexo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<GrlPessoa> getGrlPessoaList() {
        return grlPessoaList;
    }

    public void setGrlPessoaList(List<GrlPessoa> grlPessoaList) {
        this.grlPessoaList = grlPessoaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSexo != null ? pkIdSexo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlSexo)) {
            return false;
        }
        GrlSexo other = (GrlSexo) object;
        if ((this.pkIdSexo == null && other.pkIdSexo != null) || (this.pkIdSexo != null && !this.pkIdSexo.equals(other.pkIdSexo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlSexo[ pkIdSexo=" + pkIdSexo + " ]";
    }
    
}
