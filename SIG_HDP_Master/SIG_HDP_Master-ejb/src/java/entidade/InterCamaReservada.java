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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_cama_reservada", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterCamaReservada.findAll", query = "SELECT i FROM InterCamaReservada i"),
    @NamedQuery(name = "InterCamaReservada.findByPkIdCamaReservada", query = "SELECT i FROM InterCamaReservada i WHERE i.pkIdCamaReservada = :pkIdCamaReservada"),
    @NamedQuery(name = "InterCamaReservada.findByDataReserva", query = "SELECT i FROM InterCamaReservada i WHERE i.dataReserva = :dataReserva"),
    @NamedQuery(name = "InterCamaReservada.findByTempoMaximoReservado", query = "SELECT i FROM InterCamaReservada i WHERE i.tempoMaximoReservado = :tempoMaximoReservado")})
public class InterCamaReservada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_cama_reservada", nullable = false)
    private Integer pkIdCamaReservada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_reserva", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tempo_maximo_reservado", nullable = false)
    private int tempoMaximoReservado;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente", nullable = false)
    @ManyToOne(optional = false)
    private AdmsPaciente fkIdPaciente;
    @JoinColumn(name = "fk_id_cama_internamento", referencedColumnName = "pk_id_cama_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterCamaInternamento fkIdCamaInternamento;

    public InterCamaReservada() {
    }

    public InterCamaReservada(Integer pkIdCamaReservada) {
        this.pkIdCamaReservada = pkIdCamaReservada;
    }

    public InterCamaReservada(Integer pkIdCamaReservada, Date dataReserva, int tempoMaximoReservado) {
        this.pkIdCamaReservada = pkIdCamaReservada;
        this.dataReserva = dataReserva;
        this.tempoMaximoReservado = tempoMaximoReservado;
    }

    public Integer getPkIdCamaReservada() {
        return pkIdCamaReservada;
    }

    public void setPkIdCamaReservada(Integer pkIdCamaReservada) {
        this.pkIdCamaReservada = pkIdCamaReservada;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public int getTempoMaximoReservado() {
        return tempoMaximoReservado;
    }

    public void setTempoMaximoReservado(int tempoMaximoReservado) {
        this.tempoMaximoReservado = tempoMaximoReservado;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public InterCamaInternamento getFkIdCamaInternamento() {
        return fkIdCamaInternamento;
    }

    public void setFkIdCamaInternamento(InterCamaInternamento fkIdCamaInternamento) {
        this.fkIdCamaInternamento = fkIdCamaInternamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCamaReservada != null ? pkIdCamaReservada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterCamaReservada)) {
            return false;
        }
        InterCamaReservada other = (InterCamaReservada) object;
        if ((this.pkIdCamaReservada == null && other.pkIdCamaReservada != null) || (this.pkIdCamaReservada != null && !this.pkIdCamaReservada.equals(other.pkIdCamaReservada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterCamaReservada[ pkIdCamaReservada=" + pkIdCamaReservada + " ]";
    }
    
}
