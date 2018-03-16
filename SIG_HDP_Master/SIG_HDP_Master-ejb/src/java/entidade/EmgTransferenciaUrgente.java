/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "emg_transferencia_urgente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmgTransferenciaUrgente.findAll", query = "SELECT e FROM EmgTransferenciaUrgente e"),
    @NamedQuery(name = "EmgTransferenciaUrgente.findByPkIdTransferencia", query = "SELECT e FROM EmgTransferenciaUrgente e WHERE e.pkIdTransferencia = :pkIdTransferencia"),
    @NamedQuery(name = "EmgTransferenciaUrgente.findByFkIdPaciente", query = "SELECT e FROM EmgTransferenciaUrgente e WHERE e.fkIdPaciente = :fkIdPaciente"),
    @NamedQuery(name = "EmgTransferenciaUrgente.findByDataTransferencia", query = "SELECT e FROM EmgTransferenciaUrgente e WHERE e.dataTransferencia = :dataTransferencia"),
    @NamedQuery(name = "EmgTransferenciaUrgente.findByEstadoTranferencia", query = "SELECT e FROM EmgTransferenciaUrgente e WHERE e.estadoTranferencia = :estadoTranferencia"),
    @NamedQuery(name = "EmgTransferenciaUrgente.findByResponsavel", query = "SELECT e FROM EmgTransferenciaUrgente e WHERE e.responsavel = :responsavel")})
public class EmgTransferenciaUrgente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_transferencia", nullable = false)
    private Integer pkIdTransferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_id_paciente", nullable = false)
    private int fkIdPaciente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_transferencia", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataTransferencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "estado_tranferencia", nullable = false, length = 2147483647)
    private String estadoTranferencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "responsavel", nullable = false, length = 2147483647)
    private String responsavel;

    public EmgTransferenciaUrgente() {
    }

    public EmgTransferenciaUrgente(Integer pkIdTransferencia) {
        this.pkIdTransferencia = pkIdTransferencia;
    }

    public EmgTransferenciaUrgente(Integer pkIdTransferencia, int fkIdPaciente, Date dataTransferencia, String estadoTranferencia, String responsavel) {
        this.pkIdTransferencia = pkIdTransferencia;
        this.fkIdPaciente = fkIdPaciente;
        this.dataTransferencia = dataTransferencia;
        this.estadoTranferencia = estadoTranferencia;
        this.responsavel = responsavel;
    }

    public Integer getPkIdTransferencia() {
        return pkIdTransferencia;
    }

    public void setPkIdTransferencia(Integer pkIdTransferencia) {
        this.pkIdTransferencia = pkIdTransferencia;
    }

    public int getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(int fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public Date getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(Date dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public String getEstadoTranferencia() {
        return estadoTranferencia;
    }

    public void setEstadoTranferencia(String estadoTranferencia) {
        this.estadoTranferencia = estadoTranferencia;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTransferencia != null ? pkIdTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmgTransferenciaUrgente)) {
            return false;
        }
        EmgTransferenciaUrgente other = (EmgTransferenciaUrgente) object;
        if ((this.pkIdTransferencia == null && other.pkIdTransferencia != null) || (this.pkIdTransferencia != null && !this.pkIdTransferencia.equals(other.pkIdTransferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EmgTransferenciaUrgente[ pkIdTransferencia=" + pkIdTransferencia + " ]";
    }
    
}
