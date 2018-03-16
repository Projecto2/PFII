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
@Table(name = "grl_religiao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlReligiao.findAll", query = "SELECT g FROM GrlReligiao g"),
    @NamedQuery(name = "GrlReligiao.findByPkIdReligiao", query = "SELECT g FROM GrlReligiao g WHERE g.pkIdReligiao = :pkIdReligiao"),
    @NamedQuery(name = "GrlReligiao.findByDescricao", query = "SELECT g FROM GrlReligiao g WHERE g.descricao = :descricao")})
public class GrlReligiao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_religiao", nullable = false)
    private Integer pkIdReligiao;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdReligiao")
    private List<GrlPessoa> grlPessoaList;

    public GrlReligiao() {
    }

    public GrlReligiao(Integer pkIdReligiao) {
        this.pkIdReligiao = pkIdReligiao;
    }

    public Integer getPkIdReligiao() {
        return pkIdReligiao;
    }

    public void setPkIdReligiao(Integer pkIdReligiao) {
        this.pkIdReligiao = pkIdReligiao;
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
        hash += (pkIdReligiao != null ? pkIdReligiao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlReligiao)) {
            return false;
        }
        GrlReligiao other = (GrlReligiao) object;
        if ((this.pkIdReligiao == null && other.pkIdReligiao != null) || (this.pkIdReligiao != null && !this.pkIdReligiao.equals(other.pkIdReligiao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlReligiao[ pkIdReligiao=" + pkIdReligiao + " ]";
    }
    
}
