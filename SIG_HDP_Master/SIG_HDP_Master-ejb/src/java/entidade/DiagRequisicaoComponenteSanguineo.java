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
@Table(name = "diag_requisicao_componente_sanguineo", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineo.findAll", query = "SELECT d FROM DiagRequisicaoComponenteSanguineo d"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineo.findByPkIdRequisicaoComponenteSanguineo", query = "SELECT d FROM DiagRequisicaoComponenteSanguineo d WHERE d.pkIdRequisicaoComponenteSanguineo = :pkIdRequisicaoComponenteSanguineo"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineo.findByData", query = "SELECT d FROM DiagRequisicaoComponenteSanguineo d WHERE d.data = :data"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineo.findByDiagnosticoClinico", query = "SELECT d FROM DiagRequisicaoComponenteSanguineo d WHERE d.diagnosticoClinico = :diagnosticoClinico"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineo.findByPeso", query = "SELECT d FROM DiagRequisicaoComponenteSanguineo d WHERE d.peso = :peso"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineo.findByDataProgramadaTransfusao", query = "SELECT d FROM DiagRequisicaoComponenteSanguineo d WHERE d.dataProgramadaTransfusao = :dataProgramadaTransfusao")})
public class DiagRequisicaoComponenteSanguineo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_requisicao_componente_sanguineo", nullable = false)
    private Integer pkIdRequisicaoComponenteSanguineo;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Size(max = 100)
    @Column(name = "diagnostico_clinico", length = 100)
    private String diagnosticoClinico;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso", precision = 8, scale = 8)
    private Float peso;
    @Column(name = "data_programada_transfusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProgramadaTransfusao;
    @OneToMany(mappedBy = "fkIdRequisicaoComponenteSanguineo")
    private List<DiagRequisicaoComponenteSanguineoHasComponente> diagRequisicaoComponenteSanguineoHasComponenteList;
    @JoinColumn(name = "fk_id_caracter_transfusao", referencedColumnName = "pk_id_caracter_transfusao")
    @ManyToOne
    private DiagCaracterTransfusao fkIdCaracterTransfusao;
    @JoinColumn(name = "sistomatologia_hemorragica", referencedColumnName = "pk_id_respostas_questoes_requisicao_componentes")
    @ManyToOne
    private DiagRespostasQuestoesRequisicaoComponentes sistomatologiaHemorragica;
    @JoinColumn(name = "historia_reaccao_transfusional", referencedColumnName = "pk_id_respostas_questoes_requisicao_componentes")
    @ManyToOne
    private DiagRespostasQuestoesRequisicaoComponentes historiaReaccaoTransfusional;
    @JoinColumn(name = "gravidez_anterior", referencedColumnName = "pk_id_respostas_questoes_requisicao_componentes")
    @ManyToOne
    private DiagRespostasQuestoesRequisicaoComponentes gravidezAnterior;
    @JoinColumn(name = "transfusao_anterior", referencedColumnName = "pk_id_respostas_questoes_requisicao_componentes")
    @ManyToOne
    private DiagRespostasQuestoesRequisicaoComponentes transfusaoAnterior;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento")
    @ManyToOne
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_medico", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdMedico;
    @OneToMany(mappedBy = "fkIdRequisicaoComponenteSanguineo")
    private List<DiagRespostaRequisicaoComponenteSanguineo> diagRespostaRequisicaoComponenteSanguineoList;
    @OneToMany(mappedBy = "fkIdRequisicaoComponente")
    private List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList;

    public DiagRequisicaoComponenteSanguineo() {
    }

    public DiagRequisicaoComponenteSanguineo(Integer pkIdRequisicaoComponenteSanguineo) {
        this.pkIdRequisicaoComponenteSanguineo = pkIdRequisicaoComponenteSanguineo;
    }

    public Integer getPkIdRequisicaoComponenteSanguineo() {
        return pkIdRequisicaoComponenteSanguineo;
    }

    public void setPkIdRequisicaoComponenteSanguineo(Integer pkIdRequisicaoComponenteSanguineo) {
        this.pkIdRequisicaoComponenteSanguineo = pkIdRequisicaoComponenteSanguineo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDiagnosticoClinico() {
        return diagnosticoClinico;
    }

    public void setDiagnosticoClinico(String diagnosticoClinico) {
        this.diagnosticoClinico = diagnosticoClinico;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Date getDataProgramadaTransfusao() {
        return dataProgramadaTransfusao;
    }

    public void setDataProgramadaTransfusao(Date dataProgramadaTransfusao) {
        this.dataProgramadaTransfusao = dataProgramadaTransfusao;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineoHasComponente> getDiagRequisicaoComponenteSanguineoHasComponenteList() {
        return diagRequisicaoComponenteSanguineoHasComponenteList;
    }

    public void setDiagRequisicaoComponenteSanguineoHasComponenteList(List<DiagRequisicaoComponenteSanguineoHasComponente> diagRequisicaoComponenteSanguineoHasComponenteList) {
        this.diagRequisicaoComponenteSanguineoHasComponenteList = diagRequisicaoComponenteSanguineoHasComponenteList;
    }

    public DiagCaracterTransfusao getFkIdCaracterTransfusao() {
        return fkIdCaracterTransfusao;
    }

    public void setFkIdCaracterTransfusao(DiagCaracterTransfusao fkIdCaracterTransfusao) {
        this.fkIdCaracterTransfusao = fkIdCaracterTransfusao;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getSistomatologiaHemorragica() {
        return sistomatologiaHemorragica;
    }

    public void setSistomatologiaHemorragica(DiagRespostasQuestoesRequisicaoComponentes sistomatologiaHemorragica) {
        this.sistomatologiaHemorragica = sistomatologiaHemorragica;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getHistoriaReaccaoTransfusional() {
        return historiaReaccaoTransfusional;
    }

    public void setHistoriaReaccaoTransfusional(DiagRespostasQuestoesRequisicaoComponentes historiaReaccaoTransfusional) {
        this.historiaReaccaoTransfusional = historiaReaccaoTransfusional;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getGravidezAnterior() {
        return gravidezAnterior;
    }

    public void setGravidezAnterior(DiagRespostasQuestoesRequisicaoComponentes gravidezAnterior) {
        this.gravidezAnterior = gravidezAnterior;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getTransfusaoAnterior() {
        return transfusaoAnterior;
    }

    public void setTransfusaoAnterior(DiagRespostasQuestoesRequisicaoComponentes transfusaoAnterior) {
        this.transfusaoAnterior = transfusaoAnterior;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public RhFuncionario getFkIdMedico() {
        return fkIdMedico;
    }

    public void setFkIdMedico(RhFuncionario fkIdMedico) {
        this.fkIdMedico = fkIdMedico;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoComponenteSanguineo> getDiagRespostaRequisicaoComponenteSanguineoList() {
        return diagRespostaRequisicaoComponenteSanguineoList;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineoList(List<DiagRespostaRequisicaoComponenteSanguineo> diagRespostaRequisicaoComponenteSanguineoList) {
        this.diagRespostaRequisicaoComponenteSanguineoList = diagRespostaRequisicaoComponenteSanguineoList;
    }

    @XmlTransient
    public List<DiagTesteCompatibilidade> getDiagTesteCompatibilidadeList() {
        return diagTesteCompatibilidadeList;
    }

    public void setDiagTesteCompatibilidadeList(List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList) {
        this.diagTesteCompatibilidadeList = diagTesteCompatibilidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRequisicaoComponenteSanguineo != null ? pkIdRequisicaoComponenteSanguineo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagRequisicaoComponenteSanguineo)) {
            return false;
        }
        DiagRequisicaoComponenteSanguineo other = (DiagRequisicaoComponenteSanguineo) object;
        if ((this.pkIdRequisicaoComponenteSanguineo == null && other.pkIdRequisicaoComponenteSanguineo != null) || (this.pkIdRequisicaoComponenteSanguineo != null && !this.pkIdRequisicaoComponenteSanguineo.equals(other.pkIdRequisicaoComponenteSanguineo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagRequisicaoComponenteSanguineo[ pkIdRequisicaoComponenteSanguineo=" + pkIdRequisicaoComponenteSanguineo + " ]";
    }
    
}
