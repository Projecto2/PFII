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
@Table(name = "rh_classificacao_do_criterio", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhClassificacaoDoCriterio.findAll", query = "SELECT r FROM RhClassificacaoDoCriterio r"),
    @NamedQuery(name = "RhClassificacaoDoCriterio.findByPkIdClassificacaoDoCriterio", query = "SELECT r FROM RhClassificacaoDoCriterio r WHERE r.pkIdClassificacaoDoCriterio = :pkIdClassificacaoDoCriterio"),
    @NamedQuery(name = "RhClassificacaoDoCriterio.findByDescricao", query = "SELECT r FROM RhClassificacaoDoCriterio r WHERE r.descricao = :descricao"),
    @NamedQuery(name = "RhClassificacaoDoCriterio.findByClassificacao", query = "SELECT r FROM RhClassificacaoDoCriterio r WHERE r.classificacao = :classificacao")})
public class RhClassificacaoDoCriterio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_classificacao_do_criterio", nullable = false)
    private Integer pkIdClassificacaoDoCriterio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "classificacao", nullable = false)
    private int classificacao;
    @JoinColumn(name = "fk_id_criterio_de_avaliacao", referencedColumnName = "pk_id_criterio_de_avaliacao", nullable = false)
    @ManyToOne(optional = false)
    private RhCriterioDeAvaliacao fkIdCriterioDeAvaliacao;

    public RhClassificacaoDoCriterio() {
    }

    public RhClassificacaoDoCriterio(Integer pkIdClassificacaoDoCriterio) {
        this.pkIdClassificacaoDoCriterio = pkIdClassificacaoDoCriterio;
    }

    public RhClassificacaoDoCriterio(Integer pkIdClassificacaoDoCriterio, String descricao, int classificacao) {
        this.pkIdClassificacaoDoCriterio = pkIdClassificacaoDoCriterio;
        this.descricao = descricao;
        this.classificacao = classificacao;
    }

    public Integer getPkIdClassificacaoDoCriterio() {
        return pkIdClassificacaoDoCriterio;
    }

    public void setPkIdClassificacaoDoCriterio(Integer pkIdClassificacaoDoCriterio) {
        this.pkIdClassificacaoDoCriterio = pkIdClassificacaoDoCriterio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public RhCriterioDeAvaliacao getFkIdCriterioDeAvaliacao() {
        return fkIdCriterioDeAvaliacao;
    }

    public void setFkIdCriterioDeAvaliacao(RhCriterioDeAvaliacao fkIdCriterioDeAvaliacao) {
        this.fkIdCriterioDeAvaliacao = fkIdCriterioDeAvaliacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdClassificacaoDoCriterio != null ? pkIdClassificacaoDoCriterio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhClassificacaoDoCriterio)) {
            return false;
        }
        RhClassificacaoDoCriterio other = (RhClassificacaoDoCriterio) object;
        if ((this.pkIdClassificacaoDoCriterio == null && other.pkIdClassificacaoDoCriterio != null) || (this.pkIdClassificacaoDoCriterio != null && !this.pkIdClassificacaoDoCriterio.equals(other.pkIdClassificacaoDoCriterio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhClassificacaoDoCriterio[ pkIdClassificacaoDoCriterio=" + pkIdClassificacaoDoCriterio + " ]";
    }
    
}
