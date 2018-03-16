/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_inscricao_internamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterInscricaoInternamento.findAll", query = "SELECT i FROM InterInscricaoInternamento i"),
    @NamedQuery(name = "InterInscricaoInternamento.findByPkIdInscricaoInternamento", query = "SELECT i FROM InterInscricaoInternamento i WHERE i.pkIdInscricaoInternamento = :pkIdInscricaoInternamento"),
    @NamedQuery(name = "InterInscricaoInternamento.findByDataInscricao", query = "SELECT i FROM InterInscricaoInternamento i WHERE i.dataInscricao = :dataInscricao"),
    @NamedQuery(name = "InterInscricaoInternamento.findByNumeroInscricao", query = "SELECT i FROM InterInscricaoInternamento i WHERE i.numeroInscricao = :numeroInscricao")})
public class InterInscricaoInternamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_inscricao_internamento", nullable = false)
    private Integer pkIdInscricaoInternamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inscricao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInscricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "numero_inscricao", nullable = false, length = 100)
    private String numeroInscricao;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdInscricaoInternamento")
    private List<InterRegistoInternamento> interRegistoInternamentoList;

    public InterInscricaoInternamento() {
    }

    public InterInscricaoInternamento(Integer pkIdInscricaoInternamento) {
        this.pkIdInscricaoInternamento = pkIdInscricaoInternamento;
    }

    public InterInscricaoInternamento(Integer pkIdInscricaoInternamento, Date dataInscricao, String numeroInscricao) {
        this.pkIdInscricaoInternamento = pkIdInscricaoInternamento;
        this.dataInscricao = dataInscricao;
        this.numeroInscricao = numeroInscricao;
    }

    public Integer getPkIdInscricaoInternamento() {
        return pkIdInscricaoInternamento;
    }

    public void setPkIdInscricaoInternamento(Integer pkIdInscricaoInternamento) {
        this.pkIdInscricaoInternamento = pkIdInscricaoInternamento;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(String numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @XmlTransient
    public List<InterRegistoInternamento> getInterRegistoInternamentoList() {
        return interRegistoInternamentoList;
    }

    public void setInterRegistoInternamentoList(List<InterRegistoInternamento> interRegistoInternamentoList) {
        this.interRegistoInternamentoList = interRegistoInternamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdInscricaoInternamento != null ? pkIdInscricaoInternamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterInscricaoInternamento)) {
            return false;
        }
        InterInscricaoInternamento other = (InterInscricaoInternamento) object;
        if ((this.pkIdInscricaoInternamento == null && other.pkIdInscricaoInternamento != null) || (this.pkIdInscricaoInternamento != null && !this.pkIdInscricaoInternamento.equals(other.pkIdInscricaoInternamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterInscricaoInternamento[ pkIdInscricaoInternamento=" + pkIdInscricaoInternamento + " ]";
    }
    
}
