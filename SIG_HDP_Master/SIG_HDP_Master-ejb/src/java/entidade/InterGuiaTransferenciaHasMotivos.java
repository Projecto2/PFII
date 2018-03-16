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
@Table(name = "inter_guia_transferencia_has_motivos", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterGuiaTransferenciaHasMotivos.findAll", query = "SELECT i FROM InterGuiaTransferenciaHasMotivos i"),
    @NamedQuery(name = "InterGuiaTransferenciaHasMotivos.findByPkIdGuiaTransferenciaHasMotivos", query = "SELECT i FROM InterGuiaTransferenciaHasMotivos i WHERE i.pkIdGuiaTransferenciaHasMotivos = :pkIdGuiaTransferenciaHasMotivos")})
public class InterGuiaTransferenciaHasMotivos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_guia_transferencia_has_motivos", nullable = false)
    private Integer pkIdGuiaTransferenciaHasMotivos;
    @JoinColumn(name = "fk_id_guia_transferencia_doentes", referencedColumnName = "pk_id_guia_transferencia_doentes", nullable = false)
    @ManyToOne(optional = false)
    private InterGuiaTransferenciaDoentes fkIdGuiaTransferenciaDoentes;
    @JoinColumn(name = "fk_id_motivo_transferencia", referencedColumnName = "pk_id_motivo_transferencia", nullable = false)
    @ManyToOne(optional = false)
    private InterMotivoTransferencia fkIdMotivoTransferencia;

    public InterGuiaTransferenciaHasMotivos() {
    }

    public InterGuiaTransferenciaHasMotivos(Integer pkIdGuiaTransferenciaHasMotivos) {
        this.pkIdGuiaTransferenciaHasMotivos = pkIdGuiaTransferenciaHasMotivos;
    }

    public Integer getPkIdGuiaTransferenciaHasMotivos() {
        return pkIdGuiaTransferenciaHasMotivos;
    }

    public void setPkIdGuiaTransferenciaHasMotivos(Integer pkIdGuiaTransferenciaHasMotivos) {
        this.pkIdGuiaTransferenciaHasMotivos = pkIdGuiaTransferenciaHasMotivos;
    }

    public InterGuiaTransferenciaDoentes getFkIdGuiaTransferenciaDoentes() {
        return fkIdGuiaTransferenciaDoentes;
    }

    public void setFkIdGuiaTransferenciaDoentes(InterGuiaTransferenciaDoentes fkIdGuiaTransferenciaDoentes) {
        this.fkIdGuiaTransferenciaDoentes = fkIdGuiaTransferenciaDoentes;
    }

    public InterMotivoTransferencia getFkIdMotivoTransferencia() {
        return fkIdMotivoTransferencia;
    }

    public void setFkIdMotivoTransferencia(InterMotivoTransferencia fkIdMotivoTransferencia) {
        this.fkIdMotivoTransferencia = fkIdMotivoTransferencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdGuiaTransferenciaHasMotivos != null ? pkIdGuiaTransferenciaHasMotivos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterGuiaTransferenciaHasMotivos)) {
            return false;
        }
        InterGuiaTransferenciaHasMotivos other = (InterGuiaTransferenciaHasMotivos) object;
        if ((this.pkIdGuiaTransferenciaHasMotivos == null && other.pkIdGuiaTransferenciaHasMotivos != null) || (this.pkIdGuiaTransferenciaHasMotivos != null && !this.pkIdGuiaTransferenciaHasMotivos.equals(other.pkIdGuiaTransferenciaHasMotivos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterGuiaTransferenciaHasMotivos[ pkIdGuiaTransferenciaHasMotivos=" + pkIdGuiaTransferenciaHasMotivos + " ]";
    }
    
}
