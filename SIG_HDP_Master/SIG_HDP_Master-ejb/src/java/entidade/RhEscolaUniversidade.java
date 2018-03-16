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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_escola_universidade", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEscolaUniversidade.findAll", query = "SELECT r FROM RhEscolaUniversidade r"),
    @NamedQuery(name = "RhEscolaUniversidade.findByPkIdEscolaUniversidade", query = "SELECT r FROM RhEscolaUniversidade r WHERE r.pkIdEscolaUniversidade = :pkIdEscolaUniversidade"),
    @NamedQuery(name = "RhEscolaUniversidade.findByDescricao", query = "SELECT r FROM RhEscolaUniversidade r WHERE r.descricao = :descricao"),
    @NamedQuery(name = "RhEscolaUniversidade.findBySigla", query = "SELECT r FROM RhEscolaUniversidade r WHERE r.sigla = :sigla")})
public class RhEscolaUniversidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_escola_universidade", nullable = false)
    private Integer pkIdEscolaUniversidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descricao", nullable = false, length = 120)
    private String descricao;
    @Size(max = 20)
    @Column(name = "sigla", length = 20)
    private String sigla;
    @OneToMany(mappedBy = "fkIdEscolaUniversidade")
    private List<RhEstagiario> rhEstagiarioList;

    public RhEscolaUniversidade() {
    }

    public RhEscolaUniversidade(Integer pkIdEscolaUniversidade) {
        this.pkIdEscolaUniversidade = pkIdEscolaUniversidade;
    }

    public RhEscolaUniversidade(Integer pkIdEscolaUniversidade, String descricao) {
        this.pkIdEscolaUniversidade = pkIdEscolaUniversidade;
        this.descricao = descricao;
    }

    public Integer getPkIdEscolaUniversidade() {
        return pkIdEscolaUniversidade;
    }

    public void setPkIdEscolaUniversidade(Integer pkIdEscolaUniversidade) {
        this.pkIdEscolaUniversidade = pkIdEscolaUniversidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @XmlTransient
    public List<RhEstagiario> getRhEstagiarioList() {
        return rhEstagiarioList;
    }

    public void setRhEstagiarioList(List<RhEstagiario> rhEstagiarioList) {
        this.rhEstagiarioList = rhEstagiarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEscolaUniversidade != null ? pkIdEscolaUniversidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEscolaUniversidade)) {
            return false;
        }
        RhEscolaUniversidade other = (RhEscolaUniversidade) object;
        if ((this.pkIdEscolaUniversidade == null && other.pkIdEscolaUniversidade != null) || (this.pkIdEscolaUniversidade != null && !this.pkIdEscolaUniversidade.equals(other.pkIdEscolaUniversidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEscolaUniversidade[ pkIdEscolaUniversidade=" + pkIdEscolaUniversidade + " ]";
    }
    
}
