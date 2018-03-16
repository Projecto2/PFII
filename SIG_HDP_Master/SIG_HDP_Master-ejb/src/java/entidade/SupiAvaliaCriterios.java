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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_avalia_criterios", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiAvaliaCriterios.findAll", query = "SELECT s FROM SupiAvaliaCriterios s"),
    @NamedQuery(name = "SupiAvaliaCriterios.findByPkIdAvaliaCriterios", query = "SELECT s FROM SupiAvaliaCriterios s WHERE s.pkIdAvaliaCriterios = :pkIdAvaliaCriterios"),
    @NamedQuery(name = "SupiAvaliaCriterios.findByPontuacao", query = "SELECT s FROM SupiAvaliaCriterios s WHERE s.pontuacao = :pontuacao")})
public class SupiAvaliaCriterios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_avalia_criterios", nullable = false)
    private Integer pkIdAvaliaCriterios;
    @Column(name = "pontuacao")
    private Integer pontuacao;
    @JoinColumn(name = "fk_id_avaliacao_desempenho", referencedColumnName = "pk_id_avaliacao_desempenho", nullable = false)
    @ManyToOne(optional = false)
    private SupiAvaliacaoDesempenho fkIdAvaliacaoDesempenho;
    @JoinColumn(name = "fk_id_criterio_avaliacao", referencedColumnName = "pk_id_criterio_avaliacao", nullable = false)
    @ManyToOne(optional = false)
    private SupiCriterioAvaliacao fkIdCriterioAvaliacao;
    @JoinColumn(name = "fk_id_pontuacao", referencedColumnName = "pk_id_pontuacao")
    @ManyToOne
    private SupiPontuacao fkIdPontuacao;

    public SupiAvaliaCriterios() {
    }

    public SupiAvaliaCriterios(Integer pkIdAvaliaCriterios) {
        this.pkIdAvaliaCriterios = pkIdAvaliaCriterios;
    }

    public Integer getPkIdAvaliaCriterios() {
        return pkIdAvaliaCriterios;
    }

    public void setPkIdAvaliaCriterios(Integer pkIdAvaliaCriterios) {
        this.pkIdAvaliaCriterios = pkIdAvaliaCriterios;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public SupiAvaliacaoDesempenho getFkIdAvaliacaoDesempenho() {
        return fkIdAvaliacaoDesempenho;
    }

    public void setFkIdAvaliacaoDesempenho(SupiAvaliacaoDesempenho fkIdAvaliacaoDesempenho) {
        this.fkIdAvaliacaoDesempenho = fkIdAvaliacaoDesempenho;
    }

    public SupiCriterioAvaliacao getFkIdCriterioAvaliacao() {
        return fkIdCriterioAvaliacao;
    }

    public void setFkIdCriterioAvaliacao(SupiCriterioAvaliacao fkIdCriterioAvaliacao) {
        this.fkIdCriterioAvaliacao = fkIdCriterioAvaliacao;
    }

    public SupiPontuacao getFkIdPontuacao() {
        return fkIdPontuacao;
    }

    public void setFkIdPontuacao(SupiPontuacao fkIdPontuacao) {
        this.fkIdPontuacao = fkIdPontuacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAvaliaCriterios != null ? pkIdAvaliaCriterios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiAvaliaCriterios)) {
            return false;
        }
        SupiAvaliaCriterios other = (SupiAvaliaCriterios) object;
        if ((this.pkIdAvaliaCriterios == null && other.pkIdAvaliaCriterios != null) || (this.pkIdAvaliaCriterios != null && !this.pkIdAvaliaCriterios.equals(other.pkIdAvaliaCriterios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiAvaliaCriterios[ pkIdAvaliaCriterios=" + pkIdAvaliaCriterios + " ]";
    }
    
}
