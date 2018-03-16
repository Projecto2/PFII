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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_teste_compatibilidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTesteCompatibilidade.findAll", query = "SELECT d FROM DiagTesteCompatibilidade d"),
    @NamedQuery(name = "DiagTesteCompatibilidade.findByPkIdTesteCompatibilidade", query = "SELECT d FROM DiagTesteCompatibilidade d WHERE d.pkIdTesteCompatibilidade = :pkIdTesteCompatibilidade"),
    @NamedQuery(name = "DiagTesteCompatibilidade.findByData", query = "SELECT d FROM DiagTesteCompatibilidade d WHERE d.data = :data")})
public class DiagTesteCompatibilidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_teste_compatibilidade", nullable = false)
    private Integer pkIdTesteCompatibilidade;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @OneToMany(mappedBy = "fkIdTesteCompatibilidade")
    private List<DiagRespostaRequisicaoHasComponenteHasTeste> diagRespostaRequisicaoHasComponenteHasTesteList;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente")
    @ManyToOne
    private AdmsPaciente fkIdPaciente;
    @JoinColumn(name = "fk_id_bolsa_sangue", referencedColumnName = "pk_id_bolsa_sangue")
    @ManyToOne
    private DiagBolsaSangue fkIdBolsaSangue;
    @JoinColumn(name = "fk_id_requisicao_componente", referencedColumnName = "pk_id_requisicao_componente_sanguineo")
    @ManyToOne
    private DiagRequisicaoComponenteSanguineo fkIdRequisicaoComponente;
    @JoinColumn(name = "prova_soro_fisiologico", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato provaSoroFisiologico;
    @JoinColumn(name = "teste_coombs", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato testeCoombs;
    @JoinColumn(name = "fk_id_resultado_teste_compatibilidade", referencedColumnName = "pk_id_resultado_teste_compatibilidade")
    @ManyToOne
    private DiagResultadoTesteCompatibilidade fkIdResultadoTesteCompatibilidade;

    public DiagTesteCompatibilidade() {
    }

    public DiagTesteCompatibilidade(Integer pkIdTesteCompatibilidade) {
        this.pkIdTesteCompatibilidade = pkIdTesteCompatibilidade;
    }

    public Integer getPkIdTesteCompatibilidade() {
        return pkIdTesteCompatibilidade;
    }

    public void setPkIdTesteCompatibilidade(Integer pkIdTesteCompatibilidade) {
        this.pkIdTesteCompatibilidade = pkIdTesteCompatibilidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoHasComponenteHasTeste> getDiagRespostaRequisicaoHasComponenteHasTesteList() {
        return diagRespostaRequisicaoHasComponenteHasTesteList;
    }

    public void setDiagRespostaRequisicaoHasComponenteHasTesteList(List<DiagRespostaRequisicaoHasComponenteHasTeste> diagRespostaRequisicaoHasComponenteHasTesteList) {
        this.diagRespostaRequisicaoHasComponenteHasTesteList = diagRespostaRequisicaoHasComponenteHasTesteList;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public DiagBolsaSangue getFkIdBolsaSangue() {
        return fkIdBolsaSangue;
    }

    public void setFkIdBolsaSangue(DiagBolsaSangue fkIdBolsaSangue) {
        this.fkIdBolsaSangue = fkIdBolsaSangue;
    }

    public DiagRequisicaoComponenteSanguineo getFkIdRequisicaoComponente() {
        return fkIdRequisicaoComponente;
    }

    public void setFkIdRequisicaoComponente(DiagRequisicaoComponenteSanguineo fkIdRequisicaoComponente) {
        this.fkIdRequisicaoComponente = fkIdRequisicaoComponente;
    }

    public DiagResultadoExameCandidato getProvaSoroFisiologico() {
        return provaSoroFisiologico;
    }

    public void setProvaSoroFisiologico(DiagResultadoExameCandidato provaSoroFisiologico) {
        this.provaSoroFisiologico = provaSoroFisiologico;
    }

    public DiagResultadoExameCandidato getTesteCoombs() {
        return testeCoombs;
    }

    public void setTesteCoombs(DiagResultadoExameCandidato testeCoombs) {
        this.testeCoombs = testeCoombs;
    }

    public DiagResultadoTesteCompatibilidade getFkIdResultadoTesteCompatibilidade() {
        return fkIdResultadoTesteCompatibilidade;
    }

    public void setFkIdResultadoTesteCompatibilidade(DiagResultadoTesteCompatibilidade fkIdResultadoTesteCompatibilidade) {
        this.fkIdResultadoTesteCompatibilidade = fkIdResultadoTesteCompatibilidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTesteCompatibilidade != null ? pkIdTesteCompatibilidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTesteCompatibilidade)) {
            return false;
        }
        DiagTesteCompatibilidade other = (DiagTesteCompatibilidade) object;
        if ((this.pkIdTesteCompatibilidade == null && other.pkIdTesteCompatibilidade != null) || (this.pkIdTesteCompatibilidade != null && !this.pkIdTesteCompatibilidade.equals(other.pkIdTesteCompatibilidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTesteCompatibilidade[ pkIdTesteCompatibilidade=" + pkIdTesteCompatibilidade + " ]";
    }
    
}
