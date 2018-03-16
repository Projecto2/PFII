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
@Table(name = "supi_avaliacao_desempenho", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findAll", query = "SELECT s FROM SupiAvaliacaoDesempenho s"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByPkIdAvaliacaoDesempenho", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.pkIdAvaliacaoDesempenho = :pkIdAvaliacaoDesempenho"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByDataAvaliacao", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.dataAvaliacao = :dataAvaliacao"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByTotalMensal", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.totalMensal = :totalMensal"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByObservacao", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.observacao = :observacao"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByObservacaoInstituicao", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.observacaoInstituicao = :observacaoInstituicao"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByPresenca", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.presenca = :presenca"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByRelatorio", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.relatorio = :relatorio"),
    @NamedQuery(name = "SupiAvaliacaoDesempenho.findByProcessoEnfermagem", query = "SELECT s FROM SupiAvaliacaoDesempenho s WHERE s.processoEnfermagem = :processoEnfermagem")})
public class SupiAvaliacaoDesempenho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_avaliacao_desempenho", nullable = false)
    private Integer pkIdAvaliacaoDesempenho;
    @Column(name = "data_avaliacao")
    @Temporal(TemporalType.DATE)
    private Date dataAvaliacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_mensal", nullable = false)
    private double totalMensal;
    @Size(max = 100)
    @Column(name = "observacao", length = 100)
    private String observacao;
    @Size(max = 100)
    @Column(name = "observacao_instituicao", length = 100)
    private String observacaoInstituicao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "presenca", precision = 17, scale = 17)
    private Double presenca;
    @Column(name = "relatorio", precision = 17, scale = 17)
    private Double relatorio;
    @Column(name = "processo_enfermagem", precision = 17, scale = 17)
    private Double processoEnfermagem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdAvaliacaoDesempenho")
    private List<SupiAvaliaCriterios> supiAvaliaCriteriosList;
    @OneToMany(mappedBy = "fkIdAvaliacao")
    private List<SupiItensAvaliacao> supiItensAvaliacaoList;
    @JoinColumn(name = "fk_id_estagiario", referencedColumnName = "pk_id_estagiario", nullable = false)
    @ManyToOne(optional = false)
    private RhEstagiario fkIdEstagiario;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_area_avaliacao", referencedColumnName = "pk_id_area_avaliacao")
    @ManyToOne
    private SupiAreaAvaliacao fkIdAreaAvaliacao;

    public SupiAvaliacaoDesempenho() {
    }

    public SupiAvaliacaoDesempenho(Integer pkIdAvaliacaoDesempenho) {
        this.pkIdAvaliacaoDesempenho = pkIdAvaliacaoDesempenho;
    }

    public SupiAvaliacaoDesempenho(Integer pkIdAvaliacaoDesempenho, double totalMensal) {
        this.pkIdAvaliacaoDesempenho = pkIdAvaliacaoDesempenho;
        this.totalMensal = totalMensal;
    }

    public Integer getPkIdAvaliacaoDesempenho() {
        return pkIdAvaliacaoDesempenho;
    }

    public void setPkIdAvaliacaoDesempenho(Integer pkIdAvaliacaoDesempenho) {
        this.pkIdAvaliacaoDesempenho = pkIdAvaliacaoDesempenho;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public double getTotalMensal() {
        return totalMensal;
    }

    public void setTotalMensal(double totalMensal) {
        this.totalMensal = totalMensal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getObservacaoInstituicao() {
        return observacaoInstituicao;
    }

    public void setObservacaoInstituicao(String observacaoInstituicao) {
        this.observacaoInstituicao = observacaoInstituicao;
    }

    public Double getPresenca() {
        return presenca;
    }

    public void setPresenca(Double presenca) {
        this.presenca = presenca;
    }

    public Double getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Double relatorio) {
        this.relatorio = relatorio;
    }

    public Double getProcessoEnfermagem() {
        return processoEnfermagem;
    }

    public void setProcessoEnfermagem(Double processoEnfermagem) {
        this.processoEnfermagem = processoEnfermagem;
    }

    @XmlTransient
    public List<SupiAvaliaCriterios> getSupiAvaliaCriteriosList() {
        return supiAvaliaCriteriosList;
    }

    public void setSupiAvaliaCriteriosList(List<SupiAvaliaCriterios> supiAvaliaCriteriosList) {
        this.supiAvaliaCriteriosList = supiAvaliaCriteriosList;
    }

    @XmlTransient
    public List<SupiItensAvaliacao> getSupiItensAvaliacaoList() {
        return supiItensAvaliacaoList;
    }

    public void setSupiItensAvaliacaoList(List<SupiItensAvaliacao> supiItensAvaliacaoList) {
        this.supiItensAvaliacaoList = supiItensAvaliacaoList;
    }

    public RhEstagiario getFkIdEstagiario() {
        return fkIdEstagiario;
    }

    public void setFkIdEstagiario(RhEstagiario fkIdEstagiario) {
        this.fkIdEstagiario = fkIdEstagiario;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public SupiAreaAvaliacao getFkIdAreaAvaliacao() {
        return fkIdAreaAvaliacao;
    }

    public void setFkIdAreaAvaliacao(SupiAreaAvaliacao fkIdAreaAvaliacao) {
        this.fkIdAreaAvaliacao = fkIdAreaAvaliacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAvaliacaoDesempenho != null ? pkIdAvaliacaoDesempenho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiAvaliacaoDesempenho)) {
            return false;
        }
        SupiAvaliacaoDesempenho other = (SupiAvaliacaoDesempenho) object;
        if ((this.pkIdAvaliacaoDesempenho == null && other.pkIdAvaliacaoDesempenho != null) || (this.pkIdAvaliacaoDesempenho != null && !this.pkIdAvaliacaoDesempenho.equals(other.pkIdAvaliacaoDesempenho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiAvaliacaoDesempenho[ pkIdAvaliacaoDesempenho=" + pkIdAvaliacaoDesempenho + " ]";
    }
    
}
