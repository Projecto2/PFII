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
@Table(name = "inter_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterUpdates.findAll", query = "SELECT i FROM InterUpdates i"),
    @NamedQuery(name = "InterUpdates.findByPkIdUpdates", query = "SELECT i FROM InterUpdates i WHERE i.pkIdUpdates = :pkIdUpdates"),
    @NamedQuery(name = "InterUpdates.findByEnfermaria", query = "SELECT i FROM InterUpdates i WHERE i.enfermaria = :enfermaria"),
    @NamedQuery(name = "InterUpdates.findBySala", query = "SELECT i FROM InterUpdates i WHERE i.sala = :sala"),
    @NamedQuery(name = "InterUpdates.findByCama", query = "SELECT i FROM InterUpdates i WHERE i.cama = :cama"),
    @NamedQuery(name = "InterUpdates.findByEstadoCama", query = "SELECT i FROM InterUpdates i WHERE i.estadoCama = :estadoCama"),
    @NamedQuery(name = "InterUpdates.findByTipoDoencaInternamento", query = "SELECT i FROM InterUpdates i WHERE i.tipoDoencaInternamento = :tipoDoencaInternamento"),
    @NamedQuery(name = "InterUpdates.findByTipoNotificacao", query = "SELECT i FROM InterUpdates i WHERE i.tipoNotificacao = :tipoNotificacao"),
    @NamedQuery(name = "InterUpdates.findByTipoAlta", query = "SELECT i FROM InterUpdates i WHERE i.tipoAlta = :tipoAlta"),
    @NamedQuery(name = "InterUpdates.findByHoraMedicacao", query = "SELECT i FROM InterUpdates i WHERE i.horaMedicacao = :horaMedicacao"),
    @NamedQuery(name = "InterUpdates.findByOpcaoMedicacao", query = "SELECT i FROM InterUpdates i WHERE i.opcaoMedicacao = :opcaoMedicacao"),
    @NamedQuery(name = "InterUpdates.findByParametroVital", query = "SELECT i FROM InterUpdates i WHERE i.parametroVital = :parametroVital"),
    @NamedQuery(name = "InterUpdates.findByPulsoUnidade", query = "SELECT i FROM InterUpdates i WHERE i.pulsoUnidade = :pulsoUnidade"),
    @NamedQuery(name = "InterUpdates.findByResultadoDoenca", query = "SELECT i FROM InterUpdates i WHERE i.resultadoDoenca = :resultadoDoenca")})
public class InterUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_updates", nullable = false)
    private Integer pkIdUpdates;
    @Column(name = "enfermaria")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enfermaria;
    @Column(name = "sala")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sala;
    @Column(name = "cama")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cama;
    @Column(name = "estado_cama")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoCama;
    @Column(name = "tipo_doenca_internamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoDoencaInternamento;
    @Column(name = "tipo_notificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoNotificacao;
    @Column(name = "tipo_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoAlta;
    @Column(name = "hora_medicacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaMedicacao;
    @Column(name = "opcao_medicacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opcaoMedicacao;
    @Column(name = "parametro_vital")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parametroVital;
    @Column(name = "pulso_unidade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pulsoUnidade;
    @Column(name = "resultado_doenca")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoDoenca;

    public InterUpdates() {
    }

    public InterUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Integer getPkIdUpdates() {
        return pkIdUpdates;
    }

    public void setPkIdUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Date getEnfermaria() {
        return enfermaria;
    }

    public void setEnfermaria(Date enfermaria) {
        this.enfermaria = enfermaria;
    }

    public Date getSala() {
        return sala;
    }

    public void setSala(Date sala) {
        this.sala = sala;
    }

    public Date getCama() {
        return cama;
    }

    public void setCama(Date cama) {
        this.cama = cama;
    }

    public Date getEstadoCama() {
        return estadoCama;
    }

    public void setEstadoCama(Date estadoCama) {
        this.estadoCama = estadoCama;
    }

    public Date getTipoDoencaInternamento() {
        return tipoDoencaInternamento;
    }

    public void setTipoDoencaInternamento(Date tipoDoencaInternamento) {
        this.tipoDoencaInternamento = tipoDoencaInternamento;
    }

    public Date getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(Date tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public Date getTipoAlta() {
        return tipoAlta;
    }

    public void setTipoAlta(Date tipoAlta) {
        this.tipoAlta = tipoAlta;
    }

    public Date getHoraMedicacao() {
        return horaMedicacao;
    }

    public void setHoraMedicacao(Date horaMedicacao) {
        this.horaMedicacao = horaMedicacao;
    }

    public Date getOpcaoMedicacao() {
        return opcaoMedicacao;
    }

    public void setOpcaoMedicacao(Date opcaoMedicacao) {
        this.opcaoMedicacao = opcaoMedicacao;
    }

    public Date getParametroVital() {
        return parametroVital;
    }

    public void setParametroVital(Date parametroVital) {
        this.parametroVital = parametroVital;
    }

    public Date getPulsoUnidade() {
        return pulsoUnidade;
    }

    public void setPulsoUnidade(Date pulsoUnidade) {
        this.pulsoUnidade = pulsoUnidade;
    }

    public Date getResultadoDoenca() {
        return resultadoDoenca;
    }

    public void setResultadoDoenca(Date resultadoDoenca) {
        this.resultadoDoenca = resultadoDoenca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUpdates != null ? pkIdUpdates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterUpdates)) {
            return false;
        }
        InterUpdates other = (InterUpdates) object;
        if ((this.pkIdUpdates == null && other.pkIdUpdates != null) || (this.pkIdUpdates != null && !this.pkIdUpdates.equals(other.pkIdUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterUpdates[ pkIdUpdates=" + pkIdUpdates + " ]";
    }
    
}
