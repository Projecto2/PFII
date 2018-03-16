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
@Table(name = "amb_consulta_has_coloracao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasColoracao.findAll", query = "SELECT a FROM AmbConsultaHasColoracao a"),
    @NamedQuery(name = "AmbConsultaHasColoracao.findByPkIdConsultaColoracao", query = "SELECT a FROM AmbConsultaHasColoracao a WHERE a.pkIdConsultaColoracao = :pkIdConsultaColoracao")})
public class AmbConsultaHasColoracao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta_coloracao", nullable = false)
    private Long pkIdConsultaColoracao;
    @JoinColumn(name = "fk_id_coloracao", referencedColumnName = "pk_id_coloracao")
    @ManyToOne
    private AmbColoracao fkIdColoracao;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;

    public AmbConsultaHasColoracao() {
    }

    public AmbConsultaHasColoracao(Long pkIdConsultaColoracao) {
        this.pkIdConsultaColoracao = pkIdConsultaColoracao;
    }

    public Long getPkIdConsultaColoracao() {
        return pkIdConsultaColoracao;
    }

    public void setPkIdConsultaColoracao(Long pkIdConsultaColoracao) {
        this.pkIdConsultaColoracao = pkIdConsultaColoracao;
    }

    public AmbColoracao getFkIdColoracao() {
        return fkIdColoracao;
    }

    public void setFkIdColoracao(AmbColoracao fkIdColoracao) {
        this.fkIdColoracao = fkIdColoracao;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultaColoracao != null ? pkIdConsultaColoracao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasColoracao)) {
            return false;
        }
        AmbConsultaHasColoracao other = (AmbConsultaHasColoracao) object;
        if ((this.pkIdConsultaColoracao == null && other.pkIdConsultaColoracao != null) || (this.pkIdConsultaColoracao != null && !this.pkIdConsultaColoracao.equals(other.pkIdConsultaColoracao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasColoracao[ pkIdConsultaColoracao=" + pkIdConsultaColoracao + " ]";
    }
    
}
