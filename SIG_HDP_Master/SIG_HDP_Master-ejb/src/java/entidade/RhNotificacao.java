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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_notificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhNotificacao.findAll", query = "SELECT r FROM RhNotificacao r"),
    @NamedQuery(name = "RhNotificacao.findByPkIdNotificacao", query = "SELECT r FROM RhNotificacao r WHERE r.pkIdNotificacao = :pkIdNotificacao"),
    @NamedQuery(name = "RhNotificacao.findByAssunto", query = "SELECT r FROM RhNotificacao r WHERE r.assunto = :assunto"),
    @NamedQuery(name = "RhNotificacao.findByDescricao", query = "SELECT r FROM RhNotificacao r WHERE r.descricao = :descricao"),
    @NamedQuery(name = "RhNotificacao.findByDataDeNotificacao", query = "SELECT r FROM RhNotificacao r WHERE r.dataDeNotificacao = :dataDeNotificacao"),
    @NamedQuery(name = "RhNotificacao.findByLida", query = "SELECT r FROM RhNotificacao r WHERE r.lida = :lida"),
    @NamedQuery(name = "RhNotificacao.findByDataDeEvento", query = "SELECT r FROM RhNotificacao r WHERE r.dataDeEvento = :dataDeEvento"),
    @NamedQuery(name = "RhNotificacao.findByIdDaEntidadeDoEvento", query = "SELECT r FROM RhNotificacao r WHERE r.idDaEntidadeDoEvento = :idDaEntidadeDoEvento")})
public class RhNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_notificacao", nullable = false)
    private Integer pkIdNotificacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "assunto", nullable = false, length = 200)
    private String assunto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_notificacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeNotificacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lida", nullable = false)
    private boolean lida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_evento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataDeEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_da_entidade_do_evento", nullable = false)
    private int idDaEntidadeDoEvento;

    public RhNotificacao() {
    }

    public RhNotificacao(Integer pkIdNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
    }

    public RhNotificacao(Integer pkIdNotificacao, String assunto, String descricao, Date dataDeNotificacao, boolean lida, Date dataDeEvento, int idDaEntidadeDoEvento) {
        this.pkIdNotificacao = pkIdNotificacao;
        this.assunto = assunto;
        this.descricao = descricao;
        this.dataDeNotificacao = dataDeNotificacao;
        this.lida = lida;
        this.dataDeEvento = dataDeEvento;
        this.idDaEntidadeDoEvento = idDaEntidadeDoEvento;
    }

    public Integer getPkIdNotificacao() {
        return pkIdNotificacao;
    }

    public void setPkIdNotificacao(Integer pkIdNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataDeNotificacao() {
        return dataDeNotificacao;
    }

    public void setDataDeNotificacao(Date dataDeNotificacao) {
        this.dataDeNotificacao = dataDeNotificacao;
    }

    public boolean getLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public Date getDataDeEvento() {
        return dataDeEvento;
    }

    public void setDataDeEvento(Date dataDeEvento) {
        this.dataDeEvento = dataDeEvento;
    }

    public int getIdDaEntidadeDoEvento() {
        return idDaEntidadeDoEvento;
    }

    public void setIdDaEntidadeDoEvento(int idDaEntidadeDoEvento) {
        this.idDaEntidadeDoEvento = idDaEntidadeDoEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdNotificacao != null ? pkIdNotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhNotificacao)) {
            return false;
        }
        RhNotificacao other = (RhNotificacao) object;
        if ((this.pkIdNotificacao == null && other.pkIdNotificacao != null) || (this.pkIdNotificacao != null && !this.pkIdNotificacao.equals(other.pkIdNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhNotificacao[ pkIdNotificacao=" + pkIdNotificacao + " ]";
    }
    
}
