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
@Table(name = "supi_pontuacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiPontuacao.findAll", query = "SELECT s FROM SupiPontuacao s"),
    @NamedQuery(name = "SupiPontuacao.findByPkIdPontuacao", query = "SELECT s FROM SupiPontuacao s WHERE s.pkIdPontuacao = :pkIdPontuacao"),
    @NamedQuery(name = "SupiPontuacao.findByDescricao", query = "SELECT s FROM SupiPontuacao s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SupiPontuacao.findByValor", query = "SELECT s FROM SupiPontuacao s WHERE s.valor = :valor")})
public class SupiPontuacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pontuacao", nullable = false)
    private Integer pkIdPontuacao;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @Column(name = "valor")
    private Integer valor;
    @OneToMany(mappedBy = "fkIdPontuacao")
    private List<SupiAvaliaCriterios> supiAvaliaCriteriosList;

    public SupiPontuacao() {
    }

    public SupiPontuacao(Integer pkIdPontuacao) {
        this.pkIdPontuacao = pkIdPontuacao;
    }

    public Integer getPkIdPontuacao() {
        return pkIdPontuacao;
    }

    public void setPkIdPontuacao(Integer pkIdPontuacao) {
        this.pkIdPontuacao = pkIdPontuacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<SupiAvaliaCriterios> getSupiAvaliaCriteriosList() {
        return supiAvaliaCriteriosList;
    }

    public void setSupiAvaliaCriteriosList(List<SupiAvaliaCriterios> supiAvaliaCriteriosList) {
        this.supiAvaliaCriteriosList = supiAvaliaCriteriosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPontuacao != null ? pkIdPontuacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiPontuacao)) {
            return false;
        }
        SupiPontuacao other = (SupiPontuacao) object;
        if ((this.pkIdPontuacao == null && other.pkIdPontuacao != null) || (this.pkIdPontuacao != null && !this.pkIdPontuacao.equals(other.pkIdPontuacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiPontuacao[ pkIdPontuacao=" + pkIdPontuacao + " ]";
    }
    
}
