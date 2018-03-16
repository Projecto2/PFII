/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_conclusao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterConclusao.findAll", query = "SELECT i FROM InterConclusao i"),
    @NamedQuery(name = "InterConclusao.findByPkIdConclusao", query = "SELECT i FROM InterConclusao i WHERE i.pkIdConclusao = :pkIdConclusao"),
    @NamedQuery(name = "InterConclusao.findByDataAlta", query = "SELECT i FROM InterConclusao i WHERE i.dataAlta = :dataAlta"),
    @NamedQuery(name = "InterConclusao.findByAleitamento", query = "SELECT i FROM InterConclusao i WHERE i.aleitamento = :aleitamento"),
    @NamedQuery(name = "InterConclusao.findByAlimentacaocomplentar", query = "SELECT i FROM InterConclusao i WHERE i.alimentacaocomplentar = :alimentacaocomplentar"),
    @NamedQuery(name = "InterConclusao.findByProblemasmaiores", query = "SELECT i FROM InterConclusao i WHERE i.problemasmaiores = :problemasmaiores")})
public class InterConclusao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_conclusao", nullable = false)
    private Integer pkIdConclusao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_alta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlta;
    @Size(max = 45)
    @Column(name = "aleitamento", length = 45)
    private String aleitamento;
    @Size(max = 45)
    @Column(name = "alimentacaocomplentar", length = 45)
    private String alimentacaocomplentar;
    @Size(max = 200)
    @Column(name = "problemasmaiores", length = 200)
    private String problemasmaiores;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento")
    @ManyToOne
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_titulo_alta", referencedColumnName = "pk_id_titulo_alta", nullable = false)
    @ManyToOne(optional = false)
    private InterTituloAlta fkIdTituloAlta;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public InterConclusao() {
    }

    public InterConclusao(Integer pkIdConclusao) {
        this.pkIdConclusao = pkIdConclusao;
    }

    public InterConclusao(Integer pkIdConclusao, Date dataAlta) {
        this.pkIdConclusao = pkIdConclusao;
        this.dataAlta = dataAlta;
    }

    public Integer getPkIdConclusao() {
        return pkIdConclusao;
    }

    public void setPkIdConclusao(Integer pkIdConclusao) {
        this.pkIdConclusao = pkIdConclusao;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getAleitamento() {
        return aleitamento;
    }

    public void setAleitamento(String aleitamento) {
        this.aleitamento = aleitamento;
    }

    public String getAlimentacaocomplentar() {
        return alimentacaocomplentar;
    }

    public void setAlimentacaocomplentar(String alimentacaocomplentar) {
        this.alimentacaocomplentar = alimentacaocomplentar;
    }

    public String getProblemasmaiores() {
        return problemasmaiores;
    }

    public void setProblemasmaiores(String problemasmaiores) {
        this.problemasmaiores = problemasmaiores;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public InterTituloAlta getFkIdTituloAlta() {
        return fkIdTituloAlta;
    }

    public void setFkIdTituloAlta(InterTituloAlta fkIdTituloAlta) {
        this.fkIdTituloAlta = fkIdTituloAlta;
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
        hash += (pkIdConclusao != null ? pkIdConclusao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterConclusao)) {
            return false;
        }
        InterConclusao other = (InterConclusao) object;
        if ((this.pkIdConclusao == null && other.pkIdConclusao != null) || (this.pkIdConclusao != null && !this.pkIdConclusao.equals(other.pkIdConclusao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterConclusao[ pkIdConclusao=" + pkIdConclusao + " ]";
    }
    
}
