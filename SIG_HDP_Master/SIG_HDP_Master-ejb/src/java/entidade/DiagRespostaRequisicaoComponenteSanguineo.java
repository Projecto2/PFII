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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_resposta_requisicao_componente_sanguineo", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagRespostaRequisicaoComponenteSanguineo.findAll", query = "SELECT d FROM DiagRespostaRequisicaoComponenteSanguineo d"),
    @NamedQuery(name = "DiagRespostaRequisicaoComponenteSanguineo.findByPkIdRespostaRequisicaoComponenteSanguineo", query = "SELECT d FROM DiagRespostaRequisicaoComponenteSanguineo d WHERE d.pkIdRespostaRequisicaoComponenteSanguineo = :pkIdRespostaRequisicaoComponenteSanguineo"),
    @NamedQuery(name = "DiagRespostaRequisicaoComponenteSanguineo.findByDataResposta", query = "SELECT d FROM DiagRespostaRequisicaoComponenteSanguineo d WHERE d.dataResposta = :dataResposta"),
    @NamedQuery(name = "DiagRespostaRequisicaoComponenteSanguineo.findByObservacoes", query = "SELECT d FROM DiagRespostaRequisicaoComponenteSanguineo d WHERE d.observacoes = :observacoes")})
public class DiagRespostaRequisicaoComponenteSanguineo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_resposta_requisicao_componente_sanguineo", nullable = false)
    private Integer pkIdRespostaRequisicaoComponenteSanguineo;
    @Column(name = "data_resposta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataResposta;
    @Size(max = 255)
    @Column(name = "observacoes", length = 255)
    private String observacoes;
    @JoinColumn(name = "fk_id_requisicao_componente_sanguineo", referencedColumnName = "pk_id_requisicao_componente_sanguineo")
    @ManyToOne
    private DiagRequisicaoComponenteSanguineo fkIdRequisicaoComponenteSanguineo;
    @JoinColumn(name = "resultado_pesquisa_anticorpos_irregulares", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato resultadoPesquisaAnticorposIrregulares;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @OneToMany(mappedBy = "fkIdRespostaRequisicaoComponente")
    private List<DiagRespostaRequisicaoComponenteSanguineoHasComponente> diagRespostaRequisicaoComponenteSanguineoHasComponenteList;
    @OneToMany(mappedBy = "fkIdRespostaRequisicaoComponenteSanguineo")
    private List<DiagTransfusao> diagTransfusaoList;

    public DiagRespostaRequisicaoComponenteSanguineo() {
    }

    public DiagRespostaRequisicaoComponenteSanguineo(Integer pkIdRespostaRequisicaoComponenteSanguineo) {
        this.pkIdRespostaRequisicaoComponenteSanguineo = pkIdRespostaRequisicaoComponenteSanguineo;
    }

    public Integer getPkIdRespostaRequisicaoComponenteSanguineo() {
        return pkIdRespostaRequisicaoComponenteSanguineo;
    }

    public void setPkIdRespostaRequisicaoComponenteSanguineo(Integer pkIdRespostaRequisicaoComponenteSanguineo) {
        this.pkIdRespostaRequisicaoComponenteSanguineo = pkIdRespostaRequisicaoComponenteSanguineo;
    }

    public Date getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(Date dataResposta) {
        this.dataResposta = dataResposta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public DiagRequisicaoComponenteSanguineo getFkIdRequisicaoComponenteSanguineo() {
        return fkIdRequisicaoComponenteSanguineo;
    }

    public void setFkIdRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineo fkIdRequisicaoComponenteSanguineo) {
        this.fkIdRequisicaoComponenteSanguineo = fkIdRequisicaoComponenteSanguineo;
    }

    public DiagResultadoExameCandidato getResultadoPesquisaAnticorposIrregulares() {
        return resultadoPesquisaAnticorposIrregulares;
    }

    public void setResultadoPesquisaAnticorposIrregulares(DiagResultadoExameCandidato resultadoPesquisaAnticorposIrregulares) {
        this.resultadoPesquisaAnticorposIrregulares = resultadoPesquisaAnticorposIrregulares;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoComponenteSanguineoHasComponente> getDiagRespostaRequisicaoComponenteSanguineoHasComponenteList() {
        return diagRespostaRequisicaoComponenteSanguineoHasComponenteList;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineoHasComponenteList(List<DiagRespostaRequisicaoComponenteSanguineoHasComponente> diagRespostaRequisicaoComponenteSanguineoHasComponenteList) {
        this.diagRespostaRequisicaoComponenteSanguineoHasComponenteList = diagRespostaRequisicaoComponenteSanguineoHasComponenteList;
    }

    @XmlTransient
    public List<DiagTransfusao> getDiagTransfusaoList() {
        return diagTransfusaoList;
    }

    public void setDiagTransfusaoList(List<DiagTransfusao> diagTransfusaoList) {
        this.diagTransfusaoList = diagTransfusaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRespostaRequisicaoComponenteSanguineo != null ? pkIdRespostaRequisicaoComponenteSanguineo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagRespostaRequisicaoComponenteSanguineo)) {
            return false;
        }
        DiagRespostaRequisicaoComponenteSanguineo other = (DiagRespostaRequisicaoComponenteSanguineo) object;
        if ((this.pkIdRespostaRequisicaoComponenteSanguineo == null && other.pkIdRespostaRequisicaoComponenteSanguineo != null) || (this.pkIdRespostaRequisicaoComponenteSanguineo != null && !this.pkIdRespostaRequisicaoComponenteSanguineo.equals(other.pkIdRespostaRequisicaoComponenteSanguineo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagRespostaRequisicaoComponenteSanguineo[ pkIdRespostaRequisicaoComponenteSanguineo=" + pkIdRespostaRequisicaoComponenteSanguineo + " ]";
    }
    
}
