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
@Table(name = "amb_confirmacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConfirmacao.findAll", query = "SELECT a FROM AmbConfirmacao a"),
    @NamedQuery(name = "AmbConfirmacao.findByPkIdConfirmacao", query = "SELECT a FROM AmbConfirmacao a WHERE a.pkIdConfirmacao = :pkIdConfirmacao"),
    @NamedQuery(name = "AmbConfirmacao.findByDescricao", query = "SELECT a FROM AmbConfirmacao a WHERE a.descricao = :descricao")})
public class AmbConfirmacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_confirmacao", nullable = false)
    private Integer pkIdConfirmacao;
    @Size(max = 3)
    @Column(name = "descricao", length = 3)
    private String descricao;
    @OneToMany(mappedBy = "fkIdConfirmacao")
    private List<AmbConsulta> ambConsultaList;

    public AmbConfirmacao() {
    }

    public AmbConfirmacao(Integer pkIdConfirmacao) {
        this.pkIdConfirmacao = pkIdConfirmacao;
    }

    public Integer getPkIdConfirmacao() {
        return pkIdConfirmacao;
    }

    public void setPkIdConfirmacao(Integer pkIdConfirmacao) {
        this.pkIdConfirmacao = pkIdConfirmacao;
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
        hash += (pkIdConfirmacao != null ? pkIdConfirmacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConfirmacao)) {
            return false;
        }
        AmbConfirmacao other = (AmbConfirmacao) object;
        if ((this.pkIdConfirmacao == null && other.pkIdConfirmacao != null) || (this.pkIdConfirmacao != null && !this.pkIdConfirmacao.equals(other.pkIdConfirmacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConfirmacao[ pkIdConfirmacao=" + pkIdConfirmacao + " ]";
    }
    
}
