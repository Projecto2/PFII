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
@Table(name = "rh_criterio_de_avaliacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhCriterioDeAvaliacao.findAll", query = "SELECT r FROM RhCriterioDeAvaliacao r"),
    @NamedQuery(name = "RhCriterioDeAvaliacao.findByPkIdCriterioDeAvaliacao", query = "SELECT r FROM RhCriterioDeAvaliacao r WHERE r.pkIdCriterioDeAvaliacao = :pkIdCriterioDeAvaliacao"),
    @NamedQuery(name = "RhCriterioDeAvaliacao.findByDescricao", query = "SELECT r FROM RhCriterioDeAvaliacao r WHERE r.descricao = :descricao")})
public class RhCriterioDeAvaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_criterio_de_avaliacao", nullable = false)
    private Integer pkIdCriterioDeAvaliacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCriterioAvaliacao")
    private List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCriterioDeAvaliacao")
    private List<RhClassificacaoDoCriterio> rhClassificacaoDoCriterioList;

    public RhCriterioDeAvaliacao() {
    }

    public RhCriterioDeAvaliacao(Integer pkIdCriterioDeAvaliacao) {
        this.pkIdCriterioDeAvaliacao = pkIdCriterioDeAvaliacao;
    }

    public RhCriterioDeAvaliacao(Integer pkIdCriterioDeAvaliacao, String descricao) {
        this.pkIdCriterioDeAvaliacao = pkIdCriterioDeAvaliacao;
        this.descricao = descricao;
    }

    public Integer getPkIdCriterioDeAvaliacao() {
        return pkIdCriterioDeAvaliacao;
    }

    public void setPkIdCriterioDeAvaliacao(Integer pkIdCriterioDeAvaliacao) {
        this.pkIdCriterioDeAvaliacao = pkIdCriterioDeAvaliacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList() {
        return rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList;
    }

    public void setRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList(List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList) {
        this.rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList = rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList;
    }

    @XmlTransient
    public List<RhClassificacaoDoCriterio> getRhClassificacaoDoCriterioList() {
        return rhClassificacaoDoCriterioList;
    }

    public void setRhClassificacaoDoCriterioList(List<RhClassificacaoDoCriterio> rhClassificacaoDoCriterioList) {
        this.rhClassificacaoDoCriterioList = rhClassificacaoDoCriterioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCriterioDeAvaliacao != null ? pkIdCriterioDeAvaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhCriterioDeAvaliacao)) {
            return false;
        }
        RhCriterioDeAvaliacao other = (RhCriterioDeAvaliacao) object;
        if ((this.pkIdCriterioDeAvaliacao == null && other.pkIdCriterioDeAvaliacao != null) || (this.pkIdCriterioDeAvaliacao != null && !this.pkIdCriterioDeAvaliacao.equals(other.pkIdCriterioDeAvaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhCriterioDeAvaliacao[ pkIdCriterioDeAvaliacao=" + pkIdCriterioDeAvaliacao + " ]";
    }
    
}
