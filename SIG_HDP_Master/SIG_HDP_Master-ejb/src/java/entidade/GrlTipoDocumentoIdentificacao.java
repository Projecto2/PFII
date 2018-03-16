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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "grl_tipo_documento_identificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlTipoDocumentoIdentificacao.findAll", query = "SELECT g FROM GrlTipoDocumentoIdentificacao g"),
    @NamedQuery(name = "GrlTipoDocumentoIdentificacao.findByPkTipoDocumentoIdentificacao", query = "SELECT g FROM GrlTipoDocumentoIdentificacao g WHERE g.pkTipoDocumentoIdentificacao = :pkTipoDocumentoIdentificacao"),
    @NamedQuery(name = "GrlTipoDocumentoIdentificacao.findByDescricao", query = "SELECT g FROM GrlTipoDocumentoIdentificacao g WHERE g.descricao = :descricao")})
public class GrlTipoDocumentoIdentificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_tipo_documento_identificacao", nullable = false)
    private Integer pkTipoDocumentoIdentificacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkTipoDocumentoIdentificacao")
    private List<GrlDocumentoIdentificacao> grlDocumentoIdentificacaoList;

    public GrlTipoDocumentoIdentificacao() {
    }

    public GrlTipoDocumentoIdentificacao(Integer pkTipoDocumentoIdentificacao) {
        this.pkTipoDocumentoIdentificacao = pkTipoDocumentoIdentificacao;
    }

    public GrlTipoDocumentoIdentificacao(Integer pkTipoDocumentoIdentificacao, String descricao) {
        this.pkTipoDocumentoIdentificacao = pkTipoDocumentoIdentificacao;
        this.descricao = descricao;
    }

    public Integer getPkTipoDocumentoIdentificacao() {
        return pkTipoDocumentoIdentificacao;
    }

    public void setPkTipoDocumentoIdentificacao(Integer pkTipoDocumentoIdentificacao) {
        this.pkTipoDocumentoIdentificacao = pkTipoDocumentoIdentificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<GrlDocumentoIdentificacao> getGrlDocumentoIdentificacaoList() {
        return grlDocumentoIdentificacaoList;
    }

    public void setGrlDocumentoIdentificacaoList(List<GrlDocumentoIdentificacao> grlDocumentoIdentificacaoList) {
        this.grlDocumentoIdentificacaoList = grlDocumentoIdentificacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkTipoDocumentoIdentificacao != null ? pkTipoDocumentoIdentificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlTipoDocumentoIdentificacao)) {
            return false;
        }
        GrlTipoDocumentoIdentificacao other = (GrlTipoDocumentoIdentificacao) object;
        if ((this.pkTipoDocumentoIdentificacao == null && other.pkTipoDocumentoIdentificacao != null) || (this.pkTipoDocumentoIdentificacao != null && !this.pkTipoDocumentoIdentificacao.equals(other.pkTipoDocumentoIdentificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlTipoDocumentoIdentificacao[ pkTipoDocumentoIdentificacao=" + pkTipoDocumentoIdentificacao + " ]";
    }
    
}
