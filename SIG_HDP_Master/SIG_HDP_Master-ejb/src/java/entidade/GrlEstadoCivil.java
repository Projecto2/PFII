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
@Table(name = "grl_estado_civil", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlEstadoCivil.findAll", query = "SELECT g FROM GrlEstadoCivil g"),
    @NamedQuery(name = "GrlEstadoCivil.findByPkIdEstadoCivil", query = "SELECT g FROM GrlEstadoCivil g WHERE g.pkIdEstadoCivil = :pkIdEstadoCivil"),
    @NamedQuery(name = "GrlEstadoCivil.findByCodigoEstadoCivil", query = "SELECT g FROM GrlEstadoCivil g WHERE g.codigoEstadoCivil = :codigoEstadoCivil"),
    @NamedQuery(name = "GrlEstadoCivil.findByDescricao", query = "SELECT g FROM GrlEstadoCivil g WHERE g.descricao = :descricao")})
public class GrlEstadoCivil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_civil", nullable = false)
    private Integer pkIdEstadoCivil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "codigo_estado_civil", nullable = false, length = 5)
    private String codigoEstadoCivil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoCivil")
    private List<GrlPessoa> grlPessoaList;

    public GrlEstadoCivil() {
    }

    public GrlEstadoCivil(Integer pkIdEstadoCivil) {
        this.pkIdEstadoCivil = pkIdEstadoCivil;
    }

    public GrlEstadoCivil(Integer pkIdEstadoCivil, String codigoEstadoCivil, String descricao) {
        this.pkIdEstadoCivil = pkIdEstadoCivil;
        this.codigoEstadoCivil = codigoEstadoCivil;
        this.descricao = descricao;
    }

    public Integer getPkIdEstadoCivil() {
        return pkIdEstadoCivil;
    }

    public void setPkIdEstadoCivil(Integer pkIdEstadoCivil) {
        this.pkIdEstadoCivil = pkIdEstadoCivil;
    }

    public String getCodigoEstadoCivil() {
        return codigoEstadoCivil;
    }

    public void setCodigoEstadoCivil(String codigoEstadoCivil) {
        this.codigoEstadoCivil = codigoEstadoCivil;
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
        hash += (pkIdEstadoCivil != null ? pkIdEstadoCivil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlEstadoCivil)) {
            return false;
        }
        GrlEstadoCivil other = (GrlEstadoCivil) object;
        if ((this.pkIdEstadoCivil == null && other.pkIdEstadoCivil != null) || (this.pkIdEstadoCivil != null && !this.pkIdEstadoCivil.equals(other.pkIdEstadoCivil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlEstadoCivil[ pkIdEstadoCivil=" + pkIdEstadoCivil + " ]";
    }
    
}
