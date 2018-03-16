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
@Table(name = "emg_urgencia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmgUrgencia.findAll", query = "SELECT e FROM EmgUrgencia e"),
    @NamedQuery(name = "EmgUrgencia.findByPkIdUrgencia", query = "SELECT e FROM EmgUrgencia e WHERE e.pkIdUrgencia = :pkIdUrgencia"),
    @NamedQuery(name = "EmgUrgencia.findByDataCadastro", query = "SELECT e FROM EmgUrgencia e WHERE e.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "EmgUrgencia.findByFkIdPaciente", query = "SELECT e FROM EmgUrgencia e WHERE e.fkIdPaciente = :fkIdPaciente")})
public class EmgUrgencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_urgencia", nullable = false)
    private Integer pkIdUrgencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_id_paciente", nullable = false)
    private int fkIdPaciente;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public EmgUrgencia() {
    }

    public EmgUrgencia(Integer pkIdUrgencia) {
        this.pkIdUrgencia = pkIdUrgencia;
    }

    public EmgUrgencia(Integer pkIdUrgencia, Date dataCadastro, int fkIdPaciente) {
        this.pkIdUrgencia = pkIdUrgencia;
        this.dataCadastro = dataCadastro;
        this.fkIdPaciente = fkIdPaciente;
    }

    public Integer getPkIdUrgencia() {
        return pkIdUrgencia;
    }

    public void setPkIdUrgencia(Integer pkIdUrgencia) {
        this.pkIdUrgencia = pkIdUrgencia;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(int fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUrgencia != null ? pkIdUrgencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmgUrgencia)) {
            return false;
        }
        EmgUrgencia other = (EmgUrgencia) object;
        if ((this.pkIdUrgencia == null && other.pkIdUrgencia != null) || (this.pkIdUrgencia != null && !this.pkIdUrgencia.equals(other.pkIdUrgencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EmgUrgencia[ pkIdUrgencia=" + pkIdUrgencia + " ]";
    }
    
}
