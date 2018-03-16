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
import javax.persistence.OneToOne;
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
@Table(name = "adms_servico_solicitado", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsServicoSolicitado.findAll", query = "SELECT a FROM AdmsServicoSolicitado a"),
    @NamedQuery(name = "AdmsServicoSolicitado.findByPkIdServicoSolicitado", query = "SELECT a FROM AdmsServicoSolicitado a WHERE a.pkIdServicoSolicitado = :pkIdServicoSolicitado"),
    @NamedQuery(name = "AdmsServicoSolicitado.findByObservacao", query = "SELECT a FROM AdmsServicoSolicitado a WHERE a.observacao = :observacao"),
    @NamedQuery(name = "AdmsServicoSolicitado.findByDataAtendimento", query = "SELECT a FROM AdmsServicoSolicitado a WHERE a.dataAtendimento = :dataAtendimento")})
public class AdmsServicoSolicitado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_servico_solicitado", nullable = false)
    private Long pkIdServicoSolicitado;
    @Size(max = 500)
    @Column(name = "observacao", length = 500)
    private String observacao;
    @Column(name = "data_atendimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtendimento;
    @OneToMany(mappedBy = "fkIdServicoSolicitado")
    private List<DiagExameRealizado> diagExameRealizadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdServicoSolicitado")
    private List<AdmsAgendamento> admsAgendamentoList;
    @JoinColumn(name = "fk_id_classificacao_servico_solicitado", referencedColumnName = "pk_id_classificacao_servico_solicitado", nullable = false)
    @ManyToOne(optional = false)
    private AdmsClassificacaoServicoSolicitado fkIdClassificacaoServicoSolicitado;
    @JoinColumn(name = "fk_id_estado_pagamento", referencedColumnName = "pk_id_estado_pagamento")
    @ManyToOne
    private AdmsEstadoPagamento fkIdEstadoPagamento;
    @JoinColumn(name = "fk_id_servico", referencedColumnName = "pk_id_servico", nullable = false)
    @ManyToOne(optional = false)
    private AdmsServico fkIdServico;
    @JoinColumn(name = "fk_id_solicitacao", referencedColumnName = "pk_id_solicitacao", nullable = false)
    @ManyToOne(optional = false)
    private AdmsSolicitacao fkIdSolicitacao;
    @JoinColumn(name = "fk_id_tipo_solicitacao", referencedColumnName = "pk_id_tipo_solicitacao")
    @ManyToOne
    private AdmsTipoSolicitacaoServico fkIdTipoSolicitacao;
    @JoinColumn(name = "fk_id_preco_categoria_servico", referencedColumnName = "pk_id_preco_categoria_servico")
    @ManyToOne
    private FinPrecoCategoriaServico fkIdPrecoCategoriaServico;
    @JoinColumn(name = "fk_id_recepcionista", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdRecepcionista;
    @OneToMany(mappedBy = "fkIdServicoSolicitado")
    private List<FinEncargoDevido> finEncargoDevidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pkIdServicoSolicitado")
    private List<AdmsAtividadeDesconhecido> admsAtividadeDesconhecidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdServicoSolicitado")
    private List<InterHistoriaClinicaExames> interHistoriaClinicaExamesList;
    @OneToOne(mappedBy = "fkIdServicoSolicitado")
    private AdmsServicoEfetuado admsServicoEfetuado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdServicoSolicitado")
    private List<InterRegistoInternamento> interRegistoInternamentoList;

    public AdmsServicoSolicitado() {
    }

    public AdmsServicoSolicitado(Long pkIdServicoSolicitado) {
        this.pkIdServicoSolicitado = pkIdServicoSolicitado;
    }

    public Long getPkIdServicoSolicitado() {
        return pkIdServicoSolicitado;
    }

    public void setPkIdServicoSolicitado(Long pkIdServicoSolicitado) {
        this.pkIdServicoSolicitado = pkIdServicoSolicitado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    @XmlTransient
    public List<DiagExameRealizado> getDiagExameRealizadoList() {
        return diagExameRealizadoList;
    }

    public void setDiagExameRealizadoList(List<DiagExameRealizado> diagExameRealizadoList) {
        this.diagExameRealizadoList = diagExameRealizadoList;
    }

    @XmlTransient
    public List<AdmsAgendamento> getAdmsAgendamentoList() {
        return admsAgendamentoList;
    }

    public void setAdmsAgendamentoList(List<AdmsAgendamento> admsAgendamentoList) {
        this.admsAgendamentoList = admsAgendamentoList;
    }

    public AdmsClassificacaoServicoSolicitado getFkIdClassificacaoServicoSolicitado() {
        return fkIdClassificacaoServicoSolicitado;
    }

    public void setFkIdClassificacaoServicoSolicitado(AdmsClassificacaoServicoSolicitado fkIdClassificacaoServicoSolicitado) {
        this.fkIdClassificacaoServicoSolicitado = fkIdClassificacaoServicoSolicitado;
    }

    public AdmsEstadoPagamento getFkIdEstadoPagamento() {
        return fkIdEstadoPagamento;
    }

    public void setFkIdEstadoPagamento(AdmsEstadoPagamento fkIdEstadoPagamento) {
        this.fkIdEstadoPagamento = fkIdEstadoPagamento;
    }

    public AdmsServico getFkIdServico() {
        return fkIdServico;
    }

    public void setFkIdServico(AdmsServico fkIdServico) {
        this.fkIdServico = fkIdServico;
    }

    public AdmsSolicitacao getFkIdSolicitacao() {
        return fkIdSolicitacao;
    }

    public void setFkIdSolicitacao(AdmsSolicitacao fkIdSolicitacao) {
        this.fkIdSolicitacao = fkIdSolicitacao;
    }

    public AdmsTipoSolicitacaoServico getFkIdTipoSolicitacao() {
        return fkIdTipoSolicitacao;
    }

    public void setFkIdTipoSolicitacao(AdmsTipoSolicitacaoServico fkIdTipoSolicitacao) {
        this.fkIdTipoSolicitacao = fkIdTipoSolicitacao;
    }

    public FinPrecoCategoriaServico getFkIdPrecoCategoriaServico() {
        return fkIdPrecoCategoriaServico;
    }

    public void setFkIdPrecoCategoriaServico(FinPrecoCategoriaServico fkIdPrecoCategoriaServico) {
        this.fkIdPrecoCategoriaServico = fkIdPrecoCategoriaServico;
    }

    public RhFuncionario getFkIdRecepcionista() {
        return fkIdRecepcionista;
    }

    public void setFkIdRecepcionista(RhFuncionario fkIdRecepcionista) {
        this.fkIdRecepcionista = fkIdRecepcionista;
    }

    @XmlTransient
    public List<FinEncargoDevido> getFinEncargoDevidoList() {
        return finEncargoDevidoList;
    }

    public void setFinEncargoDevidoList(List<FinEncargoDevido> finEncargoDevidoList) {
        this.finEncargoDevidoList = finEncargoDevidoList;
    }

    @XmlTransient
    public List<AdmsAtividadeDesconhecido> getAdmsAtividadeDesconhecidoList() {
        return admsAtividadeDesconhecidoList;
    }

    public void setAdmsAtividadeDesconhecidoList(List<AdmsAtividadeDesconhecido> admsAtividadeDesconhecidoList) {
        this.admsAtividadeDesconhecidoList = admsAtividadeDesconhecidoList;
    }

    @XmlTransient
    public List<InterHistoriaClinicaExames> getInterHistoriaClinicaExamesList() {
        return interHistoriaClinicaExamesList;
    }

    public void setInterHistoriaClinicaExamesList(List<InterHistoriaClinicaExames> interHistoriaClinicaExamesList) {
        this.interHistoriaClinicaExamesList = interHistoriaClinicaExamesList;
    }

    public AdmsServicoEfetuado getAdmsServicoEfetuado() {
        return admsServicoEfetuado;
    }

    public void setAdmsServicoEfetuado(AdmsServicoEfetuado admsServicoEfetuado) {
        this.admsServicoEfetuado = admsServicoEfetuado;
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
        hash += (pkIdServicoSolicitado != null ? pkIdServicoSolicitado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsServicoSolicitado)) {
            return false;
        }
        AdmsServicoSolicitado other = (AdmsServicoSolicitado) object;
        if ((this.pkIdServicoSolicitado == null && other.pkIdServicoSolicitado != null) || (this.pkIdServicoSolicitado != null && !this.pkIdServicoSolicitado.equals(other.pkIdServicoSolicitado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsServicoSolicitado[ pkIdServicoSolicitado=" + pkIdServicoSolicitado + " ]";
    }
    
}
