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
@Table(name = "supi_area_avaliacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiAreaAvaliacao.findAll", query = "SELECT s FROM SupiAreaAvaliacao s"),
    @NamedQuery(name = "SupiAreaAvaliacao.findByPkIdAreaAvaliacao", query = "SELECT s FROM SupiAreaAvaliacao s WHERE s.pkIdAreaAvaliacao = :pkIdAreaAvaliacao"),
    @NamedQuery(name = "SupiAreaAvaliacao.findByDescricao", query = "SELECT s FROM SupiAreaAvaliacao s WHERE s.descricao = :descricao")})
public class SupiAreaAvaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_area_avaliacao", nullable = false)
    private Integer pkIdAreaAvaliacao;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdAreaAvaliacao")
    private List<SupiAvaliacaoDesempenho> supiAvaliacaoDesempenhoList;

    public SupiAreaAvaliacao() {
    }

    public SupiAreaAvaliacao(Integer pkIdAreaAvaliacao) {
        this.pkIdAreaAvaliacao = pkIdAreaAvaliacao;
    }

    public Integer getPkIdAreaAvaliacao() {
        return pkIdAreaAvaliacao;
    }

    public void setPkIdAreaAvaliacao(Integer pkIdAreaAvaliacao) {
        this.pkIdAreaAvaliacao = pkIdAreaAvaliacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiAvaliacaoDesempenho> getSupiAvaliacaoDesempenhoList() {
        return supiAvaliacaoDesempenhoList;
    }

    public void setSupiAvaliacaoDesempenhoList(List<SupiAvaliacaoDesempenho> supiAvaliacaoDesempenhoList) {
        this.supiAvaliacaoDesempenhoList = supiAvaliacaoDesempenhoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAreaAvaliacao != null ? pkIdAreaAvaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiAreaAvaliacao)) {
            return false;
        }
        SupiAreaAvaliacao other = (SupiAreaAvaliacao) object;
        if ((this.pkIdAreaAvaliacao == null && other.pkIdAreaAvaliacao != null) || (this.pkIdAreaAvaliacao != null && !this.pkIdAreaAvaliacao.equals(other.pkIdAreaAvaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiAreaAvaliacao[ pkIdAreaAvaliacao=" + pkIdAreaAvaliacao + " ]";
    }
    
}
