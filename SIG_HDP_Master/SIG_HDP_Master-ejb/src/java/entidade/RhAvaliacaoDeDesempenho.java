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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rh_avaliacao_de_desempenho", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ano", "fk_id_funcionario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhAvaliacaoDeDesempenho.findAll", query = "SELECT r FROM RhAvaliacaoDeDesempenho r"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenho.findByPkIdAvaliacaoDeDesempenho", query = "SELECT r FROM RhAvaliacaoDeDesempenho r WHERE r.pkIdAvaliacaoDeDesempenho = :pkIdAvaliacaoDeDesempenho"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenho.findByAno", query = "SELECT r FROM RhAvaliacaoDeDesempenho r WHERE r.ano = :ano"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenho.findByClassificacao", query = "SELECT r FROM RhAvaliacaoDeDesempenho r WHERE r.classificacao = :classificacao"),
    @NamedQuery(name = "RhAvaliacaoDeDesempenho.findByDescricaoDaClassificacao", query = "SELECT r FROM RhAvaliacaoDeDesempenho r WHERE r.descricaoDaClassificacao = :descricaoDaClassificacao")})
public class RhAvaliacaoDeDesempenho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_avaliacao_de_desempenho", nullable = false)
    private Integer pkIdAvaliacaoDeDesempenho;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ano", nullable = false)
    private int ano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "classificacao", nullable = false)
    private double classificacao;
    @Size(max = 150)
    @Column(name = "descricao_da_classificacao", length = 150)
    private String descricaoDaClassificacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdAvaliacaoDesempenho")
    private List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public RhAvaliacaoDeDesempenho() {
    }

    public RhAvaliacaoDeDesempenho(Integer pkIdAvaliacaoDeDesempenho) {
        this.pkIdAvaliacaoDeDesempenho = pkIdAvaliacaoDeDesempenho;
    }

    public RhAvaliacaoDeDesempenho(Integer pkIdAvaliacaoDeDesempenho, int ano, double classificacao) {
        this.pkIdAvaliacaoDeDesempenho = pkIdAvaliacaoDeDesempenho;
        this.ano = ano;
        this.classificacao = classificacao;
    }

    public Integer getPkIdAvaliacaoDeDesempenho() {
        return pkIdAvaliacaoDeDesempenho;
    }

    public void setPkIdAvaliacaoDeDesempenho(Integer pkIdAvaliacaoDeDesempenho) {
        this.pkIdAvaliacaoDeDesempenho = pkIdAvaliacaoDeDesempenho;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public String getDescricaoDaClassificacao() {
        return descricaoDaClassificacao;
    }

    public void setDescricaoDaClassificacao(String descricaoDaClassificacao) {
        this.descricaoDaClassificacao = descricaoDaClassificacao;
    }

    @XmlTransient
    public List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList() {
        return rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList;
    }

    public void setRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList(List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList) {
        this.rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList = rhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAvaliacaoDeDesempenho != null ? pkIdAvaliacaoDeDesempenho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhAvaliacaoDeDesempenho)) {
            return false;
        }
        RhAvaliacaoDeDesempenho other = (RhAvaliacaoDeDesempenho) object;
        if ((this.pkIdAvaliacaoDeDesempenho == null && other.pkIdAvaliacaoDeDesempenho != null) || (this.pkIdAvaliacaoDeDesempenho != null && !this.pkIdAvaliacaoDeDesempenho.equals(other.pkIdAvaliacaoDeDesempenho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhAvaliacaoDeDesempenho[ pkIdAvaliacaoDeDesempenho=" + pkIdAvaliacaoDeDesempenho + " ]";
    }
    
}
