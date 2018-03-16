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
@Table(name = "amb_cid_perfis_doencas", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidPerfisDoencas.findAll", query = "SELECT a FROM AmbCidPerfisDoencas a"),
    @NamedQuery(name = "AmbCidPerfisDoencas.findByPkIdPerfisDoencas", query = "SELECT a FROM AmbCidPerfisDoencas a WHERE a.pkIdPerfisDoencas = :pkIdPerfisDoencas")})
public class AmbCidPerfisDoencas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_perfis_doencas", nullable = false)
    private Integer pkIdPerfisDoencas;
    @JoinColumn(name = "fk_id_prioridades", referencedColumnName = "pk_id_doencas_prioridades")
    @ManyToOne
    private AmbCidDoencasPrioridades fkIdPrioridades;
    @JoinColumn(name = "fk_id_perfil", referencedColumnName = "pk_id_nome")
    @ManyToOne
    private AmbCidPerfis fkIdPerfil;
    @JoinColumn(name = "fk_id_subcategorias", referencedColumnName = "pk_id_subcategorias")
    @ManyToOne
    private AmbCidSubcategorias fkIdSubcategorias;

    public AmbCidPerfisDoencas() {
    }

    public AmbCidPerfisDoencas(Integer pkIdPerfisDoencas) {
        this.pkIdPerfisDoencas = pkIdPerfisDoencas;
    }

    public Integer getPkIdPerfisDoencas() {
        return pkIdPerfisDoencas;
    }

    public void setPkIdPerfisDoencas(Integer pkIdPerfisDoencas) {
        this.pkIdPerfisDoencas = pkIdPerfisDoencas;
    }

    public AmbCidDoencasPrioridades getFkIdPrioridades() {
        return fkIdPrioridades;
    }

    public void setFkIdPrioridades(AmbCidDoencasPrioridades fkIdPrioridades) {
        this.fkIdPrioridades = fkIdPrioridades;
    }

    public AmbCidPerfis getFkIdPerfil() {
        return fkIdPerfil;
    }

    public void setFkIdPerfil(AmbCidPerfis fkIdPerfil) {
        this.fkIdPerfil = fkIdPerfil;
    }

    public AmbCidSubcategorias getFkIdSubcategorias() {
        return fkIdSubcategorias;
    }

    public void setFkIdSubcategorias(AmbCidSubcategorias fkIdSubcategorias) {
        this.fkIdSubcategorias = fkIdSubcategorias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPerfisDoencas != null ? pkIdPerfisDoencas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidPerfisDoencas)) {
            return false;
        }
        AmbCidPerfisDoencas other = (AmbCidPerfisDoencas) object;
        if ((this.pkIdPerfisDoencas == null && other.pkIdPerfisDoencas != null) || (this.pkIdPerfisDoencas != null && !this.pkIdPerfisDoencas.equals(other.pkIdPerfisDoencas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidPerfisDoencas[ pkIdPerfisDoencas=" + pkIdPerfisDoencas + " ]";
    }
    
}
