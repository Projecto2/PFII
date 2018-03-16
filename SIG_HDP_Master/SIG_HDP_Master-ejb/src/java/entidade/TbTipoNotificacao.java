/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "tb_tipo_notificacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTipoNotificacao.findAll", query = "SELECT t FROM TbTipoNotificacao t"),
    @NamedQuery(name = "TbTipoNotificacao.findByPkTipoNotificacao", query = "SELECT t FROM TbTipoNotificacao t WHERE t.pkTipoNotificacao = :pkTipoNotificacao"),
    @NamedQuery(name = "TbTipoNotificacao.findByDescricao", query = "SELECT t FROM TbTipoNotificacao t WHERE t.descricao = :descricao")})
public class TbTipoNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_tipo_notificacao", nullable = false)
    private Integer pkTipoNotificacao;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;

    public TbTipoNotificacao() {
    }

    public TbTipoNotificacao(Integer pkTipoNotificacao) {
        this.pkTipoNotificacao = pkTipoNotificacao;
    }

    public Integer getPkTipoNotificacao() {
        return pkTipoNotificacao;
    }

    public void setPkTipoNotificacao(Integer pkTipoNotificacao) {
        this.pkTipoNotificacao = pkTipoNotificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkTipoNotificacao != null ? pkTipoNotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTipoNotificacao)) {
            return false;
        }
        TbTipoNotificacao other = (TbTipoNotificacao) object;
        if ((this.pkTipoNotificacao == null && other.pkTipoNotificacao != null) || (this.pkTipoNotificacao != null && !this.pkTipoNotificacao.equals(other.pkTipoNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbTipoNotificacao[ pkTipoNotificacao=" + pkTipoNotificacao + " ]";
    }
    
}
