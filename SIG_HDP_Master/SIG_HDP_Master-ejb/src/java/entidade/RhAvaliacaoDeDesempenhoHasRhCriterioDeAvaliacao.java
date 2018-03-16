/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_avaliacao_de_desempenho_has_rh_criterio_de_avaliacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao.findAll", query = "SELECT r FROM RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao r"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao.findByPkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao", query = "SELECT r FROM RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao r WHERE r.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao = :pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao.findByClassificacao", query = "SELECT r FROM RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao r WHERE r.classificacao = :classificacao"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao.findByDescricaoClassificacao", query = "SELECT r FROM RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao r WHERE r.descricaoClassificacao = :descricaoClassificacao")})
public class RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_avaliacao_de_desempenho_has_criterio_de_avaliacao", nullable = false)
    private Integer pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "classificacao", nullable = false)
    private int classificacao;
    @Size(max = 150)
    @Column(name = "descricao_classificacao", length = 150)
    private String descricaoClassificacao;
    @JoinColumn(name = "fk_id_avaliacao_desempenho", referencedColumnName = "pk_id_avaliacao_de_desempenho", nullable = false)
    @ManyToOne(optional = false)
    private RhAvaliacaoDeDesempenho fkIdAvaliacaoDesempenho;
    @JoinColumn(name = "fk_id_criterio_avaliacao", referencedColumnName = "pk_id_criterio_de_avaliacao", nullable = false)
    @ManyToOne(optional = false)
    private RhCriterioDeAvaliacao fkIdCriterioAvaliacao;

    public RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao() {
    }

    public RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao(Integer pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao) {
        this.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao = pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao;
    }

    public RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao(Integer pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao, int classificacao) {
        this.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao = pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao;
        this.classificacao = classificacao;
    }

    public Integer getPkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao() {
        return pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao;
    }

    public void setPkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao(Integer pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao) {
        this.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao = pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public String getDescricaoClassificacao() {
        return descricaoClassificacao;
    }

    public void setDescricaoClassificacao(String descricaoClassificacao) {
        this.descricaoClassificacao = descricaoClassificacao;
    }

    public RhAvaliacaoDeDesempenho getFkIdAvaliacaoDesempenho() {
        return fkIdAvaliacaoDesempenho;
    }

    public void setFkIdAvaliacaoDesempenho(RhAvaliacaoDeDesempenho fkIdAvaliacaoDesempenho) {
        this.fkIdAvaliacaoDesempenho = fkIdAvaliacaoDesempenho;
    }

    public RhCriterioDeAvaliacao getFkIdCriterioAvaliacao() {
        return fkIdCriterioAvaliacao;
    }

    public void setFkIdCriterioAvaliacao(RhCriterioDeAvaliacao fkIdCriterioAvaliacao) {
        this.fkIdCriterioAvaliacao = fkIdCriterioAvaliacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao != null ? pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao)) {
            return false;
        }
        RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao other = (RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao) object;
        if ((this.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao == null && other.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao != null) || (this.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao != null && !this.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao.equals(other.pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao[ pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao=" + pkIdAvaliacaoDeDesempenhoHasCriterioDeAvaliacao + " ]";
    }
    
}
