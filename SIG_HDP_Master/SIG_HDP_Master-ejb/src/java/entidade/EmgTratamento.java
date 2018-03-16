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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "emg_tratamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmgTratamento.findAll", query = "SELECT e FROM EmgTratamento e"),
    @NamedQuery(name = "EmgTratamento.findByPkIdTratamento", query = "SELECT e FROM EmgTratamento e WHERE e.emgTratamentoPK.pkIdTratamento = :pkIdTratamento"),
    @NamedQuery(name = "EmgTratamento.findByFkIdTipoTratamento", query = "SELECT e FROM EmgTratamento e WHERE e.emgTratamentoPK.fkIdTipoTratamento = :fkIdTipoTratamento"),
    @NamedQuery(name = "EmgTratamento.findByFkIdPaciente", query = "SELECT e FROM EmgTratamento e WHERE e.fkIdPaciente = :fkIdPaciente"),
    @NamedQuery(name = "EmgTratamento.findByObservacao", query = "SELECT e FROM EmgTratamento e WHERE e.observacao = :observacao"),
    @NamedQuery(name = "EmgTratamento.findByDataTratamento", query = "SELECT e FROM EmgTratamento e WHERE e.dataTratamento = :dataTratamento")})
public class EmgTratamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmgTratamentoPK emgTratamentoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_id_paciente", nullable = false)
    private int fkIdPaciente;
    @Size(max = 2147483647)
    @Column(name = "observacao", length = 2147483647)
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_tratamento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataTratamento;
    @JoinColumn(name = "fk_id_tipo_tratamento", referencedColumnName = "pk_id_tipo_tratamento", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmgTipoTratamento emgTipoTratamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public EmgTratamento() {
    }

    public EmgTratamento(EmgTratamentoPK emgTratamentoPK) {
        this.emgTratamentoPK = emgTratamentoPK;
    }

    public EmgTratamento(EmgTratamentoPK emgTratamentoPK, int fkIdPaciente, Date dataTratamento) {
        this.emgTratamentoPK = emgTratamentoPK;
        this.fkIdPaciente = fkIdPaciente;
        this.dataTratamento = dataTratamento;
    }

    public EmgTratamento(long pkIdTratamento, int fkIdTipoTratamento) {
        this.emgTratamentoPK = new EmgTratamentoPK(pkIdTratamento, fkIdTipoTratamento);
    }

    public EmgTratamentoPK getEmgTratamentoPK() {
        return emgTratamentoPK;
    }

    public void setEmgTratamentoPK(EmgTratamentoPK emgTratamentoPK) {
        this.emgTratamentoPK = emgTratamentoPK;
    }

    public int getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(int fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataTratamento() {
        return dataTratamento;
    }

    public void setDataTratamento(Date dataTratamento) {
        this.dataTratamento = dataTratamento;
    }

    public EmgTipoTratamento getEmgTipoTratamento() {
        return emgTipoTratamento;
    }

    public void setEmgTipoTratamento(EmgTipoTratamento emgTipoTratamento) {
        this.emgTipoTratamento = emgTipoTratamento;
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
        hash += (emgTratamentoPK != null ? emgTratamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmgTratamento)) {
            return false;
        }
        EmgTratamento other = (EmgTratamento) object;
        if ((this.emgTratamentoPK == null && other.emgTratamentoPK != null) || (this.emgTratamentoPK != null && !this.emgTratamentoPK.equals(other.emgTratamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EmgTratamento[ emgTratamentoPK=" + emgTratamentoPK + " ]";
    }
    
}
