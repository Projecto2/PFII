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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_notificacoes", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbNotificacoes.findAll", query = "SELECT a FROM AmbNotificacoes a"),
    @NamedQuery(name = "AmbNotificacoes.findByPkIdNotificacoes", query = "SELECT a FROM AmbNotificacoes a WHERE a.pkIdNotificacoes = :pkIdNotificacoes"),
    @NamedQuery(name = "AmbNotificacoes.findByDescricao", query = "SELECT a FROM AmbNotificacoes a WHERE a.descricao = :descricao"),
    @NamedQuery(name = "AmbNotificacoes.findByData", query = "SELECT a FROM AmbNotificacoes a WHERE a.data = :data")})
public class AmbNotificacoes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_notificacoes", nullable = false)
    private Long pkIdNotificacoes;
    @Size(max = 255)
    @Column(name = "descricao", length = 255)
    private String descricao;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "fk_id_agendamento", referencedColumnName = "pk_id_agendamento")
    @ManyToOne
    private AdmsAgendamento fkIdAgendamento;
    @JoinColumn(name = "fk_id_estado", referencedColumnName = "pk_id_estado_notificacao")
    @ManyToOne
    private AmbEstadoNotificacao fkIdEstado;

    public AmbNotificacoes() {
    }

    public AmbNotificacoes(Long pkIdNotificacoes) {
        this.pkIdNotificacoes = pkIdNotificacoes;
    }

    public Long getPkIdNotificacoes() {
        return pkIdNotificacoes;
    }

    public void setPkIdNotificacoes(Long pkIdNotificacoes) {
        this.pkIdNotificacoes = pkIdNotificacoes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public AdmsAgendamento getFkIdAgendamento() {
        return fkIdAgendamento;
    }

    public void setFkIdAgendamento(AdmsAgendamento fkIdAgendamento) {
        this.fkIdAgendamento = fkIdAgendamento;
    }

    public AmbEstadoNotificacao getFkIdEstado() {
        return fkIdEstado;
    }

    public void setFkIdEstado(AmbEstadoNotificacao fkIdEstado) {
        this.fkIdEstado = fkIdEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdNotificacoes != null ? pkIdNotificacoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbNotificacoes)) {
            return false;
        }
        AmbNotificacoes other = (AmbNotificacoes) object;
        if ((this.pkIdNotificacoes == null && other.pkIdNotificacoes != null) || (this.pkIdNotificacoes != null && !this.pkIdNotificacoes.equals(other.pkIdNotificacoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbNotificacoes[ pkIdNotificacoes=" + pkIdNotificacoes + " ]";
    }
    
}
