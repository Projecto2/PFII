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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_documento_identificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlDocumentoIdentificacao.findAll", query = "SELECT g FROM GrlDocumentoIdentificacao g"),
    @NamedQuery(name = "GrlDocumentoIdentificacao.findByPkIdDocumento", query = "SELECT g FROM GrlDocumentoIdentificacao g WHERE g.pkIdDocumento = :pkIdDocumento"),
    @NamedQuery(name = "GrlDocumentoIdentificacao.findByNumeroDocumento", query = "SELECT g FROM GrlDocumentoIdentificacao g WHERE g.numeroDocumento = :numeroDocumento")})
public class GrlDocumentoIdentificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_documento", nullable = false)
    private Integer pkIdDocumento;
    @Size(max = 100)
    @Column(name = "numero_documento", length = 100)
    private String numeroDocumento;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa")
    @ManyToOne
    private GrlPessoa fkIdPessoa;
    @JoinColumn(name = "fk_tipo_documento_identificacao", referencedColumnName = "pk_tipo_documento_identificacao", nullable = false)
    @ManyToOne(optional = false)
    private GrlTipoDocumentoIdentificacao fkTipoDocumentoIdentificacao;

    public GrlDocumentoIdentificacao() {
    }

    public GrlDocumentoIdentificacao(Integer pkIdDocumento) {
        this.pkIdDocumento = pkIdDocumento;
    }

    public Integer getPkIdDocumento() {
        return pkIdDocumento;
    }

    public void setPkIdDocumento(Integer pkIdDocumento) {
        this.pkIdDocumento = pkIdDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    public GrlTipoDocumentoIdentificacao getFkTipoDocumentoIdentificacao() {
        return fkTipoDocumentoIdentificacao;
    }

    public void setFkTipoDocumentoIdentificacao(GrlTipoDocumentoIdentificacao fkTipoDocumentoIdentificacao) {
        this.fkTipoDocumentoIdentificacao = fkTipoDocumentoIdentificacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDocumento != null ? pkIdDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlDocumentoIdentificacao)) {
            return false;
        }
        GrlDocumentoIdentificacao other = (GrlDocumentoIdentificacao) object;
        if ((this.pkIdDocumento == null && other.pkIdDocumento != null) || (this.pkIdDocumento != null && !this.pkIdDocumento.equals(other.pkIdDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlDocumentoIdentificacao[ pkIdDocumento=" + pkIdDocumento + " ]";
    }
    
}
