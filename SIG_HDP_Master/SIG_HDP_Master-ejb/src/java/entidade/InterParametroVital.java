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
@Table(name = "inter_parametro_vital", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterParametroVital.findAll", query = "SELECT i FROM InterParametroVital i"),
    @NamedQuery(name = "InterParametroVital.findByDescricao", query = "SELECT i FROM InterParametroVital i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterParametroVital.findByPkIdParametroVital", query = "SELECT i FROM InterParametroVital i WHERE i.pkIdParametroVital = :pkIdParametroVital")})
public class InterParametroVital implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_parametro_vital", nullable = false)
    private Integer pkIdParametroVital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdParametroVital")
    private List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList;
    @OneToMany(mappedBy = "fkIdParametroVital")
    private List<InterNotificacao> interNotificacaoList;

    public InterParametroVital() {
    }

    public InterParametroVital(Integer pkIdParametroVital) {
        this.pkIdParametroVital = pkIdParametroVital;
    }

    public InterParametroVital(Integer pkIdParametroVital, String descricao) {
        this.pkIdParametroVital = pkIdParametroVital;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdParametroVital() {
        return pkIdParametroVital;
    }

    public void setPkIdParametroVital(Integer pkIdParametroVital) {
        this.pkIdParametroVital = pkIdParametroVital;
    }

    @XmlTransient
    public List<InterRegistoInternamentoHasParametroVital> getInterRegistoInternamentoHasParametroVitalList() {
        return interRegistoInternamentoHasParametroVitalList;
    }

    public void setInterRegistoInternamentoHasParametroVitalList(List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList) {
        this.interRegistoInternamentoHasParametroVitalList = interRegistoInternamentoHasParametroVitalList;
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
        hash += (pkIdParametroVital != null ? pkIdParametroVital.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterParametroVital)) {
            return false;
        }
        InterParametroVital other = (InterParametroVital) object;
        if ((this.pkIdParametroVital == null && other.pkIdParametroVital != null) || (this.pkIdParametroVital != null && !this.pkIdParametroVital.equals(other.pkIdParametroVital))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterParametroVital[ pkIdParametroVital=" + pkIdParametroVital + " ]";
    }
    
}
