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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_tipo_notificacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTipoNotificacao.findAll", query = "SELECT f FROM FarmTipoNotificacao f"),
    @NamedQuery(name = "FarmTipoNotificacao.findByPkIdTipoNotificacao", query = "SELECT f FROM FarmTipoNotificacao f WHERE f.pkIdTipoNotificacao = :pkIdTipoNotificacao"),
    @NamedQuery(name = "FarmTipoNotificacao.findByDescricao", query = "SELECT f FROM FarmTipoNotificacao f WHERE f.descricao = :descricao")})
public class FarmTipoNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_notificacao", nullable = false)
    private Integer pkIdTipoNotificacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoNotificacao")
    private List<FarmNotificacao> farmNotificacaoList;

    public FarmTipoNotificacao() {
    }

    public FarmTipoNotificacao(Integer pkIdTipoNotificacao) {
        this.pkIdTipoNotificacao = pkIdTipoNotificacao;
    }

    public FarmTipoNotificacao(Integer pkIdTipoNotificacao, String descricao) {
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
    public List<FarmNotificacao> getFarmNotificacaoList() {
        return farmNotificacaoList;
    }

    public void setFarmNotificacaoList(List<FarmNotificacao> farmNotificacaoList) {
        this.farmNotificacaoList = farmNotificacaoList;
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
        if (!(object instanceof FarmTipoNotificacao)) {
            return false;
        }
        FarmTipoNotificacao other = (FarmTipoNotificacao) object;
        if ((this.pkIdTipoNotificacao == null && other.pkIdTipoNotificacao != null) || (this.pkIdTipoNotificacao != null && !this.pkIdTipoNotificacao.equals(other.pkIdTipoNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTipoNotificacao[ pkIdTipoNotificacao=" + pkIdTipoNotificacao + " ]";
    }
    
}
