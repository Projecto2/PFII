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
@Table(name = "amb_consulta_has_impressoes_gerais", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultaHasImpressoesGerais.findAll", query = "SELECT a FROM AmbConsultaHasImpressoesGerais a"),
    @NamedQuery(name = "AmbConsultaHasImpressoesGerais.findByPkIdConsultaImpressoesGerais", query = "SELECT a FROM AmbConsultaHasImpressoesGerais a WHERE a.pkIdConsultaImpressoesGerais = :pkIdConsultaImpressoesGerais")})
public class AmbConsultaHasImpressoesGerais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consulta_impressoes_gerais", nullable = false)
    private Long pkIdConsultaImpressoesGerais;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_impressoes_gerais", referencedColumnName = "pk_id_impressoes_gerais")
    @ManyToOne
    private AmbImpressoesGerais fkIdImpressoesGerais;

    public AmbConsultaHasImpressoesGerais() {
    }

    public AmbConsultaHasImpressoesGerais(Long pkIdConsultaImpressoesGerais) {
        this.pkIdConsultaImpressoesGerais = pkIdConsultaImpressoesGerais;
    }

    public Long getPkIdConsultaImpressoesGerais() {
        return pkIdConsultaImpressoesGerais;
    }

    public void setPkIdConsultaImpressoesGerais(Long pkIdConsultaImpressoesGerais) {
        this.pkIdConsultaImpressoesGerais = pkIdConsultaImpressoesGerais;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public AmbImpressoesGerais getFkIdImpressoesGerais() {
        return fkIdImpressoesGerais;
    }

    public void setFkIdImpressoesGerais(AmbImpressoesGerais fkIdImpressoesGerais) {
        this.fkIdImpressoesGerais = fkIdImpressoesGerais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultaImpressoesGerais != null ? pkIdConsultaImpressoesGerais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultaHasImpressoesGerais)) {
            return false;
        }
        AmbConsultaHasImpressoesGerais other = (AmbConsultaHasImpressoesGerais) object;
        if ((this.pkIdConsultaImpressoesGerais == null && other.pkIdConsultaImpressoesGerais != null) || (this.pkIdConsultaImpressoesGerais != null && !this.pkIdConsultaImpressoesGerais.equals(other.pkIdConsultaImpressoesGerais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultaHasImpressoesGerais[ pkIdConsultaImpressoesGerais=" + pkIdConsultaImpressoesGerais + " ]";
    }
    
}
