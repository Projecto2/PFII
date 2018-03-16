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
@Table(name = "farm_estado_notificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmEstadoNotificacao.findAll", query = "SELECT f FROM FarmEstadoNotificacao f"),
    @NamedQuery(name = "FarmEstadoNotificacao.findByPkIdEstadoNotificacao", query = "SELECT f FROM FarmEstadoNotificacao f WHERE f.pkIdEstadoNotificacao = :pkIdEstadoNotificacao"),
    @NamedQuery(name = "FarmEstadoNotificacao.findByDataAgendamento", query = "SELECT f FROM FarmEstadoNotificacao f WHERE f.dataAgendamento = :dataAgendamento"),
    @NamedQuery(name = "FarmEstadoNotificacao.findByDataResolucao", query = "SELECT f FROM FarmEstadoNotificacao f WHERE f.dataResolucao = :dataResolucao")})
public class FarmEstadoNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estado_notificacao", nullable = false)
    private Integer pkIdEstadoNotificacao;
    @Column(name = "data_agendamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgendamento;
    @Column(name = "data_resolucao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataResolucao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoNotificacao")
    private List<FarmNotificacao> farmNotificacaoList;
    @JoinColumn(name = "fk_id_estado", referencedColumnName = "pk_id_estado", nullable = false)
    @ManyToOne(optional = false)
    private FarmEstado fkIdEstado;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public FarmEstadoNotificacao() {
    }

    public FarmEstadoNotificacao(Integer pkIdEstadoNotificacao) {
        this.pkIdEstadoNotificacao = pkIdEstadoNotificacao;
    }

    public Integer getPkIdEstadoNotificacao() {
        return pkIdEstadoNotificacao;
    }

    public void setPkIdEstadoNotificacao(Integer pkIdEstadoNotificacao) {
        this.pkIdEstadoNotificacao = pkIdEstadoNotificacao;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Date getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(Date dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    @XmlTransient
    public List<FarmNotificacao> getFarmNotificacaoList() {
        return farmNotificacaoList;
    }

    public void setFarmNotificacaoList(List<FarmNotificacao> farmNotificacaoList) {
        this.farmNotificacaoList = farmNotificacaoList;
    }

    public FarmEstado getFkIdEstado() {
        return fkIdEstado;
    }

    public void setFkIdEstado(FarmEstado fkIdEstado) {
        this.fkIdEstado = fkIdEstado;
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
        hash += (pkIdEstadoNotificacao != null ? pkIdEstadoNotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmEstadoNotificacao)) {
            return false;
        }
        FarmEstadoNotificacao other = (FarmEstadoNotificacao) object;
        if ((this.pkIdEstadoNotificacao == null && other.pkIdEstadoNotificacao != null) || (this.pkIdEstadoNotificacao != null && !this.pkIdEstadoNotificacao.equals(other.pkIdEstadoNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmEstadoNotificacao[ pkIdEstadoNotificacao=" + pkIdEstadoNotificacao + " ]";
    }
    
}
