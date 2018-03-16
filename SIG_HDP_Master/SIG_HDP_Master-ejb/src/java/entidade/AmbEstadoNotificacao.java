/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "amb_estado_notificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbEstadoNotificacao.findAll", query = "SELECT a FROM AmbEstadoNotificacao a"),
    @NamedQuery(name = "AmbEstadoNotificacao.findByPkIdEstadoNotificacao", query = "SELECT a FROM AmbEstadoNotificacao a WHERE a.pkIdEstadoNotificacao = :pkIdEstadoNotificacao"),
    @NamedQuery(name = "AmbEstadoNotificacao.findByDescricao", query = "SELECT a FROM AmbEstadoNotificacao a WHERE a.descricao = :descricao")})
public class AmbEstadoNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_notificacao", nullable = false)
    private Integer pkIdEstadoNotificacao;
    @Size(max = 17)
    @Column(name = "descricao", length = 17)
    private String descricao;
    @OneToMany(mappedBy = "fkIdEstado")
    private List<AmbNotificacoes> ambNotificacoesList;

    public AmbEstadoNotificacao() {
    }

    public AmbEstadoNotificacao(Integer pkIdEstadoNotificacao) {
        this.pkIdEstadoNotificacao = pkIdEstadoNotificacao;
    }

    public Integer getPkIdEstadoNotificacao() {
        return pkIdEstadoNotificacao;
    }

    public void setPkIdEstadoNotificacao(Integer pkIdEstadoNotificacao) {
        this.pkIdEstadoNotificacao = pkIdEstadoNotificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbNotificacoes> getAmbNotificacoesList() {
        return ambNotificacoesList;
    }

    public void setAmbNotificacoesList(List<AmbNotificacoes> ambNotificacoesList) {
        this.ambNotificacoesList = ambNotificacoesList;
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
        if (!(object instanceof AmbEstadoNotificacao)) {
            return false;
        }
        AmbEstadoNotificacao other = (AmbEstadoNotificacao) object;
        if ((this.pkIdEstadoNotificacao == null && other.pkIdEstadoNotificacao != null) || (this.pkIdEstadoNotificacao != null && !this.pkIdEstadoNotificacao.equals(other.pkIdEstadoNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbEstadoNotificacao[ pkIdEstadoNotificacao=" + pkIdEstadoNotificacao + " ]";
    }
    
}
