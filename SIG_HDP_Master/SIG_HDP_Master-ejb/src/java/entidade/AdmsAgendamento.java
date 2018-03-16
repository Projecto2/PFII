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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_agendamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsAgendamento.findAll", query = "SELECT a FROM AdmsAgendamento a"),
    @NamedQuery(name = "AdmsAgendamento.findByPkIdAgendamento", query = "SELECT a FROM AdmsAgendamento a WHERE a.pkIdAgendamento = :pkIdAgendamento"),
    @NamedQuery(name = "AdmsAgendamento.findByDataHoraInicio", query = "SELECT a FROM AdmsAgendamento a WHERE a.dataHoraInicio = :dataHoraInicio"),
    @NamedQuery(name = "AdmsAgendamento.findByDataHoraFim", query = "SELECT a FROM AdmsAgendamento a WHERE a.dataHoraFim = :dataHoraFim"),
    @NamedQuery(name = "AdmsAgendamento.findByDataHoraCheckIn", query = "SELECT a FROM AdmsAgendamento a WHERE a.dataHoraCheckIn = :dataHoraCheckIn")})
public class AdmsAgendamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_agendamento", nullable = false)
    private Long pkIdAgendamento;
    @Column(name = "data_hora_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraInicio;
    @Column(name = "data_hora_fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraFim;
    @Column(name = "data_hora_check_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCheckIn;
    @OneToMany(mappedBy = "fkIdAgendamento")
    private List<AmbNotificacoes> ambNotificacoesList;
    @OneToMany(mappedBy = "fkIdAgendamento")
    private List<AmbTriagem> ambTriagemList;
    @JoinColumn(name = "fk_id_estado_agendamento", referencedColumnName = "pk_id_estado_agendamento", nullable = false)
    @ManyToOne(optional = false)
    private AdmsEstadoAgendamento fkIdEstadoAgendamento;
    @JoinColumn(name = "fk_id_servico_solicitado", referencedColumnName = "pk_id_servico_solicitado", nullable = false)
    @ManyToOne(optional = false)
    private AdmsServicoSolicitado fkIdServicoSolicitado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdAgendamento")
    private List<AdmsAgendamentoMedico> admsAgendamentoMedicoList;

    public AdmsAgendamento() {
    }

    public AdmsAgendamento(Long pkIdAgendamento) {
        this.pkIdAgendamento = pkIdAgendamento;
    }

    public Long getPkIdAgendamento() {
        return pkIdAgendamento;
    }

    public void setPkIdAgendamento(Long pkIdAgendamento) {
        this.pkIdAgendamento = pkIdAgendamento;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Date dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public Date getDataHoraCheckIn() {
        return dataHoraCheckIn;
    }

    public void setDataHoraCheckIn(Date dataHoraCheckIn) {
        this.dataHoraCheckIn = dataHoraCheckIn;
    }

    @XmlTransient
    public List<AmbNotificacoes> getAmbNotificacoesList() {
        return ambNotificacoesList;
    }

    public void setAmbNotificacoesList(List<AmbNotificacoes> ambNotificacoesList) {
        this.ambNotificacoesList = ambNotificacoesList;
    }

    @XmlTransient
    public List<AmbTriagem> getAmbTriagemList() {
        return ambTriagemList;
    }

    public void setAmbTriagemList(List<AmbTriagem> ambTriagemList) {
        this.ambTriagemList = ambTriagemList;
    }

    public AdmsEstadoAgendamento getFkIdEstadoAgendamento() {
        return fkIdEstadoAgendamento;
    }

    public void setFkIdEstadoAgendamento(AdmsEstadoAgendamento fkIdEstadoAgendamento) {
        this.fkIdEstadoAgendamento = fkIdEstadoAgendamento;
    }

    public AdmsServicoSolicitado getFkIdServicoSolicitado() {
        return fkIdServicoSolicitado;
    }

    public void setFkIdServicoSolicitado(AdmsServicoSolicitado fkIdServicoSolicitado) {
        this.fkIdServicoSolicitado = fkIdServicoSolicitado;
    }

    @XmlTransient
    public List<AdmsAgendamentoMedico> getAdmsAgendamentoMedicoList() {
        return admsAgendamentoMedicoList;
    }

    public void setAdmsAgendamentoMedicoList(List<AdmsAgendamentoMedico> admsAgendamentoMedicoList) {
        this.admsAgendamentoMedicoList = admsAgendamentoMedicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAgendamento != null ? pkIdAgendamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsAgendamento)) {
            return false;
        }
        AdmsAgendamento other = (AdmsAgendamento) object;
        if ((this.pkIdAgendamento == null && other.pkIdAgendamento != null) || (this.pkIdAgendamento != null && !this.pkIdAgendamento.equals(other.pkIdAgendamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsAgendamento[ pkIdAgendamento=" + pkIdAgendamento + " ]";
    }
    
}
