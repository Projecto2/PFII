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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_registo_internamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterRegistoInternamento.findAll", query = "SELECT i FROM InterRegistoInternamento i"),
    @NamedQuery(name = "InterRegistoInternamento.findByPkIdRegistoInternamento", query = "SELECT i FROM InterRegistoInternamento i WHERE i.pkIdRegistoInternamento = :pkIdRegistoInternamento"),
    @NamedQuery(name = "InterRegistoInternamento.findByDataRegisto", query = "SELECT i FROM InterRegistoInternamento i WHERE i.dataRegisto = :dataRegisto"),
    @NamedQuery(name = "InterRegistoInternamento.findByInformacaoAdicional", query = "SELECT i FROM InterRegistoInternamento i WHERE i.informacaoAdicional = :informacaoAdicional"),
    @NamedQuery(name = "InterRegistoInternamento.findByAtivo", query = "SELECT i FROM InterRegistoInternamento i WHERE i.ativo = :ativo"),
    @NamedQuery(name = "InterRegistoInternamento.findByDataPrevistaAlta", query = "SELECT i FROM InterRegistoInternamento i WHERE i.dataPrevistaAlta = :dataPrevistaAlta"),
    @NamedQuery(name = "InterRegistoInternamento.findByDataInternamento", query = "SELECT i FROM InterRegistoInternamento i WHERE i.dataInternamento = :dataInternamento")})
public class InterRegistoInternamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_registo_internamento", nullable = false)
    private Long pkIdRegistoInternamento;
    @Column(name = "data_registo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegisto;
    @Size(max = 500)
    @Column(name = "informacao_adicional", length = 500)
    private String informacaoAdicional;
    @Column(name = "ativo")
    private Boolean ativo;
    @Column(name = "data_prevista_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrevistaAlta;
    @Column(name = "data_internamento")
    @Temporal(TemporalType.DATE)
    private Date dataInternamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterAnotacaoMedica> interAnotacaoMedicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterMedicacao> interMedicacaoList;
    @OneToMany(mappedBy = "fkIdRegistoInternamento")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList;
    @OneToMany(mappedBy = "fkIdRegistoInternamento")
    private List<DiagSolicitacaoTipagemDoente> diagSolicitacaoTipagemDoenteList;
    @OneToMany(mappedBy = "fkIdRegistoInternamento")
    private List<InterConclusao> interConclusaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterRegistoSaida> interRegistoSaidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterTituloAlta> interTituloAltaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterTratamentoMalnutricao> interTratamentoMalnutricaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterVacinacao> interVacinacaoList;
    @JoinColumn(name = "fk_id_servico_solicitado", referencedColumnName = "pk_id_servico_solicitado", nullable = false)
    @ManyToOne(optional = false)
    private AdmsServicoSolicitado fkIdServicoSolicitado;
    @JoinColumn(name = "fk_id_cama_internamento", referencedColumnName = "pk_id_cama_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterCamaInternamento fkIdCamaInternamento;
    @JoinColumn(name = "fk_id_inscricao_internamento", referencedColumnName = "pk_id_inscricao_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterInscricaoInternamento fkIdInscricaoInternamento;
    @JoinColumn(name = "fk_id_funcionario_enfermeiro", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionarioEnfermeiro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdRegistoInternamento")
    private List<InterAnotacaoEnfermagem> interAnotacaoEnfermagemList;

    public InterRegistoInternamento() {
    }

    public InterRegistoInternamento(Long pkIdRegistoInternamento) {
        this.pkIdRegistoInternamento = pkIdRegistoInternamento;
    }

    public Long getPkIdRegistoInternamento() {
        return pkIdRegistoInternamento;
    }

    public void setPkIdRegistoInternamento(Long pkIdRegistoInternamento) {
        this.pkIdRegistoInternamento = pkIdRegistoInternamento;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getInformacaoAdicional() {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(String informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataPrevistaAlta() {
        return dataPrevistaAlta;
    }

    public void setDataPrevistaAlta(Date dataPrevistaAlta) {
        this.dataPrevistaAlta = dataPrevistaAlta;
    }

    public Date getDataInternamento() {
        return dataInternamento;
    }

    public void setDataInternamento(Date dataInternamento) {
        this.dataInternamento = dataInternamento;
    }

    @XmlTransient
    public List<InterAnotacaoMedica> getInterAnotacaoMedicaList() {
        return interAnotacaoMedicaList;
    }

    public void setInterAnotacaoMedicaList(List<InterAnotacaoMedica> interAnotacaoMedicaList) {
        this.interAnotacaoMedicaList = interAnotacaoMedicaList;
    }

    @XmlTransient
    public List<InterRegistoInternamentoHasParametroVital> getInterRegistoInternamentoHasParametroVitalList() {
        return interRegistoInternamentoHasParametroVitalList;
    }

    public void setInterRegistoInternamentoHasParametroVitalList(List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList) {
        this.interRegistoInternamentoHasParametroVitalList = interRegistoInternamentoHasParametroVitalList;
    }

    @XmlTransient
    public List<InterDoencaInternamentoPaciente> getInterDoencaInternamentoPacienteList() {
        return interDoencaInternamentoPacienteList;
    }

    public void setInterDoencaInternamentoPacienteList(List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList) {
        this.interDoencaInternamentoPacienteList = interDoencaInternamentoPacienteList;
    }

    @XmlTransient
    public List<InterMedicacao> getInterMedicacaoList() {
        return interMedicacaoList;
    }

    public void setInterMedicacaoList(List<InterMedicacao> interMedicacaoList) {
        this.interMedicacaoList = interMedicacaoList;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList() {
        return diagRequisicaoComponenteSanguineoList;
    }

    public void setDiagRequisicaoComponenteSanguineoList(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList) {
        this.diagRequisicaoComponenteSanguineoList = diagRequisicaoComponenteSanguineoList;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaDoentes> getInterGuiaTransferenciaDoentesList() {
        return interGuiaTransferenciaDoentesList;
    }

    public void setInterGuiaTransferenciaDoentesList(List<InterGuiaTransferenciaDoentes> interGuiaTransferenciaDoentesList) {
        this.interGuiaTransferenciaDoentesList = interGuiaTransferenciaDoentesList;
    }

    @XmlTransient
    public List<DiagSolicitacaoTipagemDoente> getDiagSolicitacaoTipagemDoenteList() {
        return diagSolicitacaoTipagemDoenteList;
    }

    public void setDiagSolicitacaoTipagemDoenteList(List<DiagSolicitacaoTipagemDoente> diagSolicitacaoTipagemDoenteList) {
        this.diagSolicitacaoTipagemDoenteList = diagSolicitacaoTipagemDoenteList;
    }

    @XmlTransient
    public List<InterConclusao> getInterConclusaoList() {
        return interConclusaoList;
    }

    public void setInterConclusaoList(List<InterConclusao> interConclusaoList) {
        this.interConclusaoList = interConclusaoList;
    }

    @XmlTransient
    public List<InterRegistoSaida> getInterRegistoSaidaList() {
        return interRegistoSaidaList;
    }

    public void setInterRegistoSaidaList(List<InterRegistoSaida> interRegistoSaidaList) {
        this.interRegistoSaidaList = interRegistoSaidaList;
    }

    @XmlTransient
    public List<InterTituloAlta> getInterTituloAltaList() {
        return interTituloAltaList;
    }

    public void setInterTituloAltaList(List<InterTituloAlta> interTituloAltaList) {
        this.interTituloAltaList = interTituloAltaList;
    }

    @XmlTransient
    public List<InterTratamentoMalnutricao> getInterTratamentoMalnutricaoList() {
        return interTratamentoMalnutricaoList;
    }

    public void setInterTratamentoMalnutricaoList(List<InterTratamentoMalnutricao> interTratamentoMalnutricaoList) {
        this.interTratamentoMalnutricaoList = interTratamentoMalnutricaoList;
    }

    @XmlTransient
    public List<InterVacinacao> getInterVacinacaoList() {
        return interVacinacaoList;
    }

    public void setInterVacinacaoList(List<InterVacinacao> interVacinacaoList) {
        this.interVacinacaoList = interVacinacaoList;
    }

    public AdmsServicoSolicitado getFkIdServicoSolicitado() {
        return fkIdServicoSolicitado;
    }

    public void setFkIdServicoSolicitado(AdmsServicoSolicitado fkIdServicoSolicitado) {
        this.fkIdServicoSolicitado = fkIdServicoSolicitado;
    }

    public InterCamaInternamento getFkIdCamaInternamento() {
        return fkIdCamaInternamento;
    }

    public void setFkIdCamaInternamento(InterCamaInternamento fkIdCamaInternamento) {
        this.fkIdCamaInternamento = fkIdCamaInternamento;
    }

    public InterInscricaoInternamento getFkIdInscricaoInternamento() {
        return fkIdInscricaoInternamento;
    }

    public void setFkIdInscricaoInternamento(InterInscricaoInternamento fkIdInscricaoInternamento) {
        this.fkIdInscricaoInternamento = fkIdInscricaoInternamento;
    }

    public RhFuncionario getFkIdFuncionarioEnfermeiro() {
        return fkIdFuncionarioEnfermeiro;
    }

    public void setFkIdFuncionarioEnfermeiro(RhFuncionario fkIdFuncionarioEnfermeiro) {
        this.fkIdFuncionarioEnfermeiro = fkIdFuncionarioEnfermeiro;
    }

    @XmlTransient
    public List<InterAnotacaoEnfermagem> getInterAnotacaoEnfermagemList() {
        return interAnotacaoEnfermagemList;
    }

    public void setInterAnotacaoEnfermagemList(List<InterAnotacaoEnfermagem> interAnotacaoEnfermagemList) {
        this.interAnotacaoEnfermagemList = interAnotacaoEnfermagemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRegistoInternamento != null ? pkIdRegistoInternamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterRegistoInternamento)) {
            return false;
        }
        InterRegistoInternamento other = (InterRegistoInternamento) object;
        if ((this.pkIdRegistoInternamento == null && other.pkIdRegistoInternamento != null) || (this.pkIdRegistoInternamento != null && !this.pkIdRegistoInternamento.equals(other.pkIdRegistoInternamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterRegistoInternamento[ pkIdRegistoInternamento=" + pkIdRegistoInternamento + " ]";
    }
    
}
