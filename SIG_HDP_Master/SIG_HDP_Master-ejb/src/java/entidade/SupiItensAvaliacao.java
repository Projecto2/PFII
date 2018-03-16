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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_itens_avaliacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiItensAvaliacao.findAll", query = "SELECT s FROM SupiItensAvaliacao s"),
    @NamedQuery(name = "SupiItensAvaliacao.findByPkIdItensAvaliacao", query = "SELECT s FROM SupiItensAvaliacao s WHERE s.pkIdItensAvaliacao = :pkIdItensAvaliacao"),
    @NamedQuery(name = "SupiItensAvaliacao.findByPontuacao", query = "SELECT s FROM SupiItensAvaliacao s WHERE s.pontuacao = :pontuacao")})
public class SupiItensAvaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_itens_avaliacao", nullable = false)
    private Integer pkIdItensAvaliacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pontuacao", nullable = false)
    private double pontuacao;
    @JoinColumn(name = "fk_id_avaliacao", referencedColumnName = "pk_id_avaliacao_desempenho")
    @ManyToOne
    private SupiAvaliacaoDesempenho fkIdAvaliacao;
    @JoinColumn(name = "fk_id_criterio_avaliacao", referencedColumnName = "pk_id_criterio_avaliacao")
    @ManyToOne
    private SupiCriterioAvaliacao fkIdCriterioAvaliacao;

    public SupiItensAvaliacao() {
    }

    public SupiItensAvaliacao(Integer pkIdItensAvaliacao) {
        this.pkIdItensAvaliacao = pkIdItensAvaliacao;
    }

    public SupiItensAvaliacao(Integer pkIdItensAvaliacao, double pontuacao) {
        this.pkIdItensAvaliacao = pkIdItensAvaliacao;
        this.pontuacao = pontuacao;
    }

    public Integer getPkIdItensAvaliacao() {
        return pkIdItensAvaliacao;
    }

    public void setPkIdItensAvaliacao(Integer pkIdItensAvaliacao) {
        this.pkIdItensAvaliacao = pkIdItensAvaliacao;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public SupiAvaliacaoDesempenho getFkIdAvaliacao() {
        return fkIdAvaliacao;
    }

    public void setFkIdAvaliacao(SupiAvaliacaoDesempenho fkIdAvaliacao) {
        this.fkIdAvaliacao = fkIdAvaliacao;
    }

    public SupiCriterioAvaliacao getFkIdCriterioAvaliacao() {
        return fkIdCriterioAvaliacao;
    }

    public void setFkIdCriterioAvaliacao(SupiCriterioAvaliacao fkIdCriterioAvaliacao) {
        this.fkIdCriterioAvaliacao = fkIdCriterioAvaliacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdItensAvaliacao != null ? pkIdItensAvaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiItensAvaliacao)) {
            return false;
        }
        SupiItensAvaliacao other = (SupiItensAvaliacao) object;
        if ((this.pkIdItensAvaliacao == null && other.pkIdItensAvaliacao != null) || (this.pkIdItensAvaliacao != null && !this.pkIdItensAvaliacao.equals(other.pkIdItensAvaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiItensAvaliacao[ pkIdItensAvaliacao=" + pkIdItensAvaliacao + " ]";
    }
    
}
