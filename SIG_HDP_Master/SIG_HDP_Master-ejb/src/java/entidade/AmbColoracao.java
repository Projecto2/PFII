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
@Table(name = "amb_coloracao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbColoracao.findAll", query = "SELECT a FROM AmbColoracao a"),
    @NamedQuery(name = "AmbColoracao.findByPkIdColoracao", query = "SELECT a FROM AmbColoracao a WHERE a.pkIdColoracao = :pkIdColoracao"),
    @NamedQuery(name = "AmbColoracao.findByDescricao", query = "SELECT a FROM AmbColoracao a WHERE a.descricao = :descricao")})
public class AmbColoracao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_coloracao", nullable = false)
    private Integer pkIdColoracao;
    @Size(max = 12)
    @Column(name = "descricao", length = 12)
    private String descricao;
    @OneToMany(mappedBy = "fkIdColoracao")
    private List<AmbConsultaHasColoracao> ambConsultaHasColoracaoList;

    public AmbColoracao() {
    }

    public AmbColoracao(Integer pkIdColoracao) {
        this.pkIdColoracao = pkIdColoracao;
    }

    public Integer getPkIdColoracao() {
        return pkIdColoracao;
    }

    public void setPkIdColoracao(Integer pkIdColoracao) {
        this.pkIdColoracao = pkIdColoracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsultaHasColoracao> getAmbConsultaHasColoracaoList() {
        return ambConsultaHasColoracaoList;
    }

    public void setAmbConsultaHasColoracaoList(List<AmbConsultaHasColoracao> ambConsultaHasColoracaoList) {
        this.ambConsultaHasColoracaoList = ambConsultaHasColoracaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdColoracao != null ? pkIdColoracao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbColoracao)) {
            return false;
        }
        AmbColoracao other = (AmbColoracao) object;
        if ((this.pkIdColoracao == null && other.pkIdColoracao != null) || (this.pkIdColoracao != null && !this.pkIdColoracao.equals(other.pkIdColoracao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbColoracao[ pkIdColoracao=" + pkIdColoracao + " ]";
    }
    
}
