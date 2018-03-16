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
@Table(name = "amb_estado_hidratacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbEstadoHidratacao.findAll", query = "SELECT a FROM AmbEstadoHidratacao a"),
    @NamedQuery(name = "AmbEstadoHidratacao.findByPkIdEstadoHidratacao", query = "SELECT a FROM AmbEstadoHidratacao a WHERE a.pkIdEstadoHidratacao = :pkIdEstadoHidratacao"),
    @NamedQuery(name = "AmbEstadoHidratacao.findByDescricao", query = "SELECT a FROM AmbEstadoHidratacao a WHERE a.descricao = :descricao")})
public class AmbEstadoHidratacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_hidratacao", nullable = false)
    private Integer pkIdEstadoHidratacao;
    @Size(max = 11)
    @Column(name = "descricao", length = 11)
    private String descricao;
    @OneToMany(mappedBy = "fkIdEstadoHidratacao")
    private List<AmbConsulta> ambConsultaList;

    public AmbEstadoHidratacao() {
    }

    public AmbEstadoHidratacao(Integer pkIdEstadoHidratacao) {
        this.pkIdEstadoHidratacao = pkIdEstadoHidratacao;
    }

    public Integer getPkIdEstadoHidratacao() {
        return pkIdEstadoHidratacao;
    }

    public void setPkIdEstadoHidratacao(Integer pkIdEstadoHidratacao) {
        this.pkIdEstadoHidratacao = pkIdEstadoHidratacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsulta> getAmbConsultaList() {
        return ambConsultaList;
    }

    public void setAmbConsultaList(List<AmbConsulta> ambConsultaList) {
        this.ambConsultaList = ambConsultaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoHidratacao != null ? pkIdEstadoHidratacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbEstadoHidratacao)) {
            return false;
        }
        AmbEstadoHidratacao other = (AmbEstadoHidratacao) object;
        if ((this.pkIdEstadoHidratacao == null && other.pkIdEstadoHidratacao != null) || (this.pkIdEstadoHidratacao != null && !this.pkIdEstadoHidratacao.equals(other.pkIdEstadoHidratacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbEstadoHidratacao[ pkIdEstadoHidratacao=" + pkIdEstadoHidratacao + " ]";
    }
    
}
