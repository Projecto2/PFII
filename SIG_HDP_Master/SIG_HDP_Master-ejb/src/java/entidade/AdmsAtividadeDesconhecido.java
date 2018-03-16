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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_atividade_desconhecido", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsAtividadeDesconhecido.findAll", query = "SELECT a FROM AdmsAtividadeDesconhecido a"),
    @NamedQuery(name = "AdmsAtividadeDesconhecido.findByPkIdAtividadeDesconhecido", query = "SELECT a FROM AdmsAtividadeDesconhecido a WHERE a.pkIdAtividadeDesconhecido = :pkIdAtividadeDesconhecido")})
public class AdmsAtividadeDesconhecido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_atividade_desconhecido", nullable = false)
    private Long pkIdAtividadeDesconhecido;
    @JoinColumn(name = "pk_id_desconhecido", referencedColumnName = "pk_id_desconhecido", nullable = false)
    @ManyToOne(optional = false)
    private AdmsDesconhecido pkIdDesconhecido;
    @JoinColumn(name = "pk_id_servico_solicitado", referencedColumnName = "pk_id_servico_solicitado", nullable = false)
    @ManyToOne(optional = false)
    private AdmsServicoSolicitado pkIdServicoSolicitado;

    public AdmsAtividadeDesconhecido() {
    }

    public AdmsAtividadeDesconhecido(Long pkIdAtividadeDesconhecido) {
        this.pkIdAtividadeDesconhecido = pkIdAtividadeDesconhecido;
    }

    public Long getPkIdAtividadeDesconhecido() {
        return pkIdAtividadeDesconhecido;
    }

    public void setPkIdAtividadeDesconhecido(Long pkIdAtividadeDesconhecido) {
        this.pkIdAtividadeDesconhecido = pkIdAtividadeDesconhecido;
    }

    public AdmsDesconhecido getPkIdDesconhecido() {
        return pkIdDesconhecido;
    }

    public void setPkIdDesconhecido(AdmsDesconhecido pkIdDesconhecido) {
        this.pkIdDesconhecido = pkIdDesconhecido;
    }

    public AdmsServicoSolicitado getPkIdServicoSolicitado() {
        return pkIdServicoSolicitado;
    }

    public void setPkIdServicoSolicitado(AdmsServicoSolicitado pkIdServicoSolicitado) {
        this.pkIdServicoSolicitado = pkIdServicoSolicitado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAtividadeDesconhecido != null ? pkIdAtividadeDesconhecido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsAtividadeDesconhecido)) {
            return false;
        }
        AdmsAtividadeDesconhecido other = (AdmsAtividadeDesconhecido) object;
        if ((this.pkIdAtividadeDesconhecido == null && other.pkIdAtividadeDesconhecido != null) || (this.pkIdAtividadeDesconhecido != null && !this.pkIdAtividadeDesconhecido.equals(other.pkIdAtividadeDesconhecido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsAtividadeDesconhecido[ pkIdAtividadeDesconhecido=" + pkIdAtividadeDesconhecido + " ]";
    }
    
}
