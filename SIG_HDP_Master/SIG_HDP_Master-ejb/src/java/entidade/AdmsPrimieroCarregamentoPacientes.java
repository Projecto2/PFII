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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_primiero_carregamento_pacientes", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsPrimieroCarregamentoPacientes.findAll", query = "SELECT a FROM AdmsPrimieroCarregamentoPacientes a"),
    @NamedQuery(name = "AdmsPrimieroCarregamentoPacientes.findByPkIdPrimieroCarregamentoPacientes", query = "SELECT a FROM AdmsPrimieroCarregamentoPacientes a WHERE a.pkIdPrimieroCarregamentoPacientes = :pkIdPrimieroCarregamentoPacientes"),
    @NamedQuery(name = "AdmsPrimieroCarregamentoPacientes.findByData", query = "SELECT a FROM AdmsPrimieroCarregamentoPacientes a WHERE a.data = :data")})
public class AdmsPrimieroCarregamentoPacientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_primiero_carregamento_pacientes", nullable = false)
    private Integer pkIdPrimieroCarregamentoPacientes;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    public AdmsPrimieroCarregamentoPacientes() {
    }

    public AdmsPrimieroCarregamentoPacientes(Integer pkIdPrimieroCarregamentoPacientes) {
        this.pkIdPrimieroCarregamentoPacientes = pkIdPrimieroCarregamentoPacientes;
    }

    public Integer getPkIdPrimieroCarregamentoPacientes() {
        return pkIdPrimieroCarregamentoPacientes;
    }

    public void setPkIdPrimieroCarregamentoPacientes(Integer pkIdPrimieroCarregamentoPacientes) {
        this.pkIdPrimieroCarregamentoPacientes = pkIdPrimieroCarregamentoPacientes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPrimieroCarregamentoPacientes != null ? pkIdPrimieroCarregamentoPacientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsPrimieroCarregamentoPacientes)) {
            return false;
        }
        AdmsPrimieroCarregamentoPacientes other = (AdmsPrimieroCarregamentoPacientes) object;
        if ((this.pkIdPrimieroCarregamentoPacientes == null && other.pkIdPrimieroCarregamentoPacientes != null) || (this.pkIdPrimieroCarregamentoPacientes != null && !this.pkIdPrimieroCarregamentoPacientes.equals(other.pkIdPrimieroCarregamentoPacientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsPrimieroCarregamentoPacientes[ pkIdPrimieroCarregamentoPacientes=" + pkIdPrimieroCarregamentoPacientes + " ]";
    }
    
}
