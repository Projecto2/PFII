/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_tipo_notificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterTipoNotificacao.findAll", query = "SELECT i FROM InterTipoNotificacao i"),
    @NamedQuery(name = "InterTipoNotificacao.findByPkIdTipoNotificacao", query = "SELECT i FROM InterTipoNotificacao i WHERE i.pkIdTipoNotificacao = :pkIdTipoNotificacao"),
    @NamedQuery(name = "InterTipoNotificacao.findByDescricao", query = "SELECT i FROM InterTipoNotificacao i WHERE i.descricao = :descricao")})
public class InterTipoNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_notificacao", nullable = false)
    private Integer pkIdTipoNotificacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoNotificacao")
    private List<InterNotificacao> interNotificacaoList;

    public InterTipoNotificacao() {
    }

    public InterTipoNotificacao(Integer pkIdTipoNotificacao) {
        this.pkIdTipoNotificacao = pkIdTipoNotificacao;
    }

    public InterTipoNotificacao(Integer pkIdTipoNotificacao, String descricao) {
        this.pkIdTipoNotificacao = pkIdTipoNotificacao;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoNotificacao() {
        return pkIdTipoNotificacao;
    }

    public void setPkIdTipoNotificacao(Integer pkIdTipoNotificacao) {
        this.pkIdTipoNotificacao = pkIdTipoNotificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<InterNotificacao> getInterNotificacaoList() {
        return interNotificacaoList;
    }

    public void setInterNotificacaoList(List<InterNotificacao> interNotificacaoList) {
        this.interNotificacaoList = interNotificacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoNotificacao != null ? pkIdTipoNotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterTipoNotificacao)) {
            return false;
        }
        InterTipoNotificacao other = (InterTipoNotificacao) object;
        if ((this.pkIdTipoNotificacao == null && other.pkIdTipoNotificacao != null) || (this.pkIdTipoNotificacao != null && !this.pkIdTipoNotificacao.equals(other.pkIdTipoNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterTipoNotificacao[ pkIdTipoNotificacao=" + pkIdTipoNotificacao + " ]";
    }
    
}
