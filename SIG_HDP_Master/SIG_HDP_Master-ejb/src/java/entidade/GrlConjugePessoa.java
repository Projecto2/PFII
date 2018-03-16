/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_conjuge_pessoa", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlConjugePessoa.findAll", query = "SELECT g FROM GrlConjugePessoa g"),
    @NamedQuery(name = "GrlConjugePessoa.findByPkIdConjugePessoa", query = "SELECT g FROM GrlConjugePessoa g WHERE g.pkIdConjugePessoa = :pkIdConjugePessoa"),
    @NamedQuery(name = "GrlConjugePessoa.findByNomeConjuge", query = "SELECT g FROM GrlConjugePessoa g WHERE g.nomeConjuge = :nomeConjuge"),
    @NamedQuery(name = "GrlConjugePessoa.findByDataNascimento", query = "SELECT g FROM GrlConjugePessoa g WHERE g.dataNascimento = :dataNascimento")})
public class GrlConjugePessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_conjuge_pessoa", nullable = false)
    private Integer pkIdConjugePessoa;
    @Size(max = 45)
    @Column(name = "nome_conjuge", length = 45)
    private String nomeConjuge;
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @OneToMany(mappedBy = "fkIdConjugePessoa")
    private List<GrlPessoa> grlPessoaList;

    public GrlConjugePessoa() {
    }

    public GrlConjugePessoa(Integer pkIdConjugePessoa) {
        this.pkIdConjugePessoa = pkIdConjugePessoa;
    }

    public Integer getPkIdConjugePessoa() {
        return pkIdConjugePessoa;
    }

    public void setPkIdConjugePessoa(Integer pkIdConjugePessoa) {
        this.pkIdConjugePessoa = pkIdConjugePessoa;
    }

    public String getNomeConjuge() {
        return nomeConjuge;
    }

    public void setNomeConjuge(String nomeConjuge) {
        this.nomeConjuge = nomeConjuge;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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
        hash += (pkIdConjugePessoa != null ? pkIdConjugePessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlConjugePessoa)) {
            return false;
        }
        GrlConjugePessoa other = (GrlConjugePessoa) object;
        if ((this.pkIdConjugePessoa == null && other.pkIdConjugePessoa != null) || (this.pkIdConjugePessoa != null && !this.pkIdConjugePessoa.equals(other.pkIdConjugePessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlConjugePessoa[ pkIdConjugePessoa=" + pkIdConjugePessoa + " ]";
    }
    
}
