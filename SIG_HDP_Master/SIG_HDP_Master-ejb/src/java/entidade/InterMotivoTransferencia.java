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
@Table(name = "inter_motivo_transferencia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterMotivoTransferencia.findAll", query = "SELECT i FROM InterMotivoTransferencia i"),
    @NamedQuery(name = "InterMotivoTransferencia.findByPkIdMotivoTransferencia", query = "SELECT i FROM InterMotivoTransferencia i WHERE i.pkIdMotivoTransferencia = :pkIdMotivoTransferencia"),
    @NamedQuery(name = "InterMotivoTransferencia.findByDescricao", query = "SELECT i FROM InterMotivoTransferencia i WHERE i.descricao = :descricao")})
public class InterMotivoTransferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_motivo_transferencia", nullable = false)
    private Integer pkIdMotivoTransferencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMotivoTransferencia")
    private List<InterGuiaTransferenciaHasMotivos> interGuiaTransferenciaHasMotivosList;

    public InterMotivoTransferencia() {
    }

    public InterMotivoTransferencia(Integer pkIdMotivoTransferencia) {
        this.pkIdMotivoTransferencia = pkIdMotivoTransferencia;
    }

    public InterMotivoTransferencia(Integer pkIdMotivoTransferencia, String descricao) {
        this.pkIdMotivoTransferencia = pkIdMotivoTransferencia;
        this.descricao = descricao;
    }

    public Integer getPkIdMotivoTransferencia() {
        return pkIdMotivoTransferencia;
    }

    public void setPkIdMotivoTransferencia(Integer pkIdMotivoTransferencia) {
        this.pkIdMotivoTransferencia = pkIdMotivoTransferencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<InterGuiaTransferenciaHasMotivos> getInterGuiaTransferenciaHasMotivosList() {
        return interGuiaTransferenciaHasMotivosList;
    }

    public void setInterGuiaTransferenciaHasMotivosList(List<InterGuiaTransferenciaHasMotivos> interGuiaTransferenciaHasMotivosList) {
        this.interGuiaTransferenciaHasMotivosList = interGuiaTransferenciaHasMotivosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMotivoTransferencia != null ? pkIdMotivoTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterMotivoTransferencia)) {
            return false;
        }
        InterMotivoTransferencia other = (InterMotivoTransferencia) object;
        if ((this.pkIdMotivoTransferencia == null && other.pkIdMotivoTransferencia != null) || (this.pkIdMotivoTransferencia != null && !this.pkIdMotivoTransferencia.equals(other.pkIdMotivoTransferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterMotivoTransferencia[ pkIdMotivoTransferencia=" + pkIdMotivoTransferencia + " ]";
    }
    
}
