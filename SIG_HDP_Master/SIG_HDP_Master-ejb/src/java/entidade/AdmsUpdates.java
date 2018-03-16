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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsUpdates.findAll", query = "SELECT a FROM AdmsUpdates a"),
    @NamedQuery(name = "AdmsUpdates.findByPkIdUpdates", query = "SELECT a FROM AdmsUpdates a WHERE a.pkIdUpdates = :pkIdUpdates"),
    @NamedQuery(name = "AdmsUpdates.findByTipoDeServico", query = "SELECT a FROM AdmsUpdates a WHERE a.tipoDeServico = :tipoDeServico"),
    @NamedQuery(name = "AdmsUpdates.findByGrupoDeServico", query = "SELECT a FROM AdmsUpdates a WHERE a.grupoDeServico = :grupoDeServico"),
    @NamedQuery(name = "AdmsUpdates.findByServico", query = "SELECT a FROM AdmsUpdates a WHERE a.servico = :servico"),
    @NamedQuery(name = "AdmsUpdates.findByClassificacaoServicoSolicitado", query = "SELECT a FROM AdmsUpdates a WHERE a.classificacaoServicoSolicitado = :classificacaoServicoSolicitado"),
    @NamedQuery(name = "AdmsUpdates.findByEstadoAgendamento", query = "SELECT a FROM AdmsUpdates a WHERE a.estadoAgendamento = :estadoAgendamento"),
    @NamedQuery(name = "AdmsUpdates.findByEstadoPagamento", query = "SELECT a FROM AdmsUpdates a WHERE a.estadoPagamento = :estadoPagamento"),
    @NamedQuery(name = "AdmsUpdates.findByTipoSolicitacaoServico", query = "SELECT a FROM AdmsUpdates a WHERE a.tipoSolicitacaoServico = :tipoSolicitacaoServico"),
    @NamedQuery(name = "AdmsUpdates.findByCategoriaServico", query = "SELECT a FROM AdmsUpdates a WHERE a.categoriaServico = :categoriaServico")})
public class AdmsUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_updates", nullable = false)
    private Integer pkIdUpdates;
    @Column(name = "tipo_de_servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoDeServico;
    @Column(name = "grupo_de_servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grupoDeServico;
    @Column(name = "servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date servico;
    @Column(name = "classificacao_servico_solicitado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date classificacaoServicoSolicitado;
    @Column(name = "estado_agendamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoAgendamento;
    @Column(name = "estado_pagamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoPagamento;
    @Column(name = "tipo_solicitacao_servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoSolicitacaoServico;
    @Column(name = "categoria_servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date categoriaServico;

    public AdmsUpdates() {
    }

    public AdmsUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Integer getPkIdUpdates() {
        return pkIdUpdates;
    }

    public void setPkIdUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Date getTipoDeServico() {
        return tipoDeServico;
    }

    public void setTipoDeServico(Date tipoDeServico) {
        this.tipoDeServico = tipoDeServico;
    }

    public Date getGrupoDeServico() {
        return grupoDeServico;
    }

    public void setGrupoDeServico(Date grupoDeServico) {
        this.grupoDeServico = grupoDeServico;
    }

    public Date getServico() {
        return servico;
    }

    public void setServico(Date servico) {
        this.servico = servico;
    }

    public Date getClassificacaoServicoSolicitado() {
        return classificacaoServicoSolicitado;
    }

    public void setClassificacaoServicoSolicitado(Date classificacaoServicoSolicitado) {
        this.classificacaoServicoSolicitado = classificacaoServicoSolicitado;
    }

    public Date getEstadoAgendamento() {
        return estadoAgendamento;
    }

    public void setEstadoAgendamento(Date estadoAgendamento) {
        this.estadoAgendamento = estadoAgendamento;
    }

    public Date getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(Date estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public Date getTipoSolicitacaoServico() {
        return tipoSolicitacaoServico;
    }

    public void setTipoSolicitacaoServico(Date tipoSolicitacaoServico) {
        this.tipoSolicitacaoServico = tipoSolicitacaoServico;
    }

    public Date getCategoriaServico() {
        return categoriaServico;
    }

    public void setCategoriaServico(Date categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUpdates != null ? pkIdUpdates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsUpdates)) {
            return false;
        }
        AdmsUpdates other = (AdmsUpdates) object;
        if ((this.pkIdUpdates == null && other.pkIdUpdates != null) || (this.pkIdUpdates != null && !this.pkIdUpdates.equals(other.pkIdUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsUpdates[ pkIdUpdates=" + pkIdUpdates + " ]";
    }
    
}
