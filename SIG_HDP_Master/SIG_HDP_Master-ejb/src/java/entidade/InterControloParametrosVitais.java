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
@Table(name = "inter_controlo_parametros_vitais", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterControloParametrosVitais.findAll", query = "SELECT i FROM InterControloParametrosVitais i"),
    @NamedQuery(name = "InterControloParametrosVitais.findByPkIdControloParametrosVitais", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.pkIdControloParametrosVitais = :pkIdControloParametrosVitais"),
    @NamedQuery(name = "InterControloParametrosVitais.findByDataHoraGravadaNoSistema", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.dataHoraGravadaNoSistema = :dataHoraGravadaNoSistema"),
    @NamedQuery(name = "InterControloParametrosVitais.findByDataRegistadaNoPaciente", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.dataRegistadaNoPaciente = :dataRegistadaNoPaciente"),
    @NamedQuery(name = "InterControloParametrosVitais.findByValor", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.valor = :valor"),
    @NamedQuery(name = "InterControloParametrosVitais.findByTaValor1", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.taValor1 = :taValor1"),
    @NamedQuery(name = "InterControloParametrosVitais.findByTaValor2", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.taValor2 = :taValor2"),
    @NamedQuery(name = "InterControloParametrosVitais.findByHora", query = "SELECT i FROM InterControloParametrosVitais i WHERE i.hora = :hora")})
public class InterControloParametrosVitais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_controlo_parametros_vitais", nullable = false)
    private Long pkIdControloParametrosVitais;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora_gravada_no_sistema", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraGravadaNoSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_registada_no_paciente", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataRegistadaNoPaciente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false)
    private float valor;
    @Column(name = "ta_valor1")
    private Integer taValor1;
    @Column(name = "ta_valor2")
    private Integer taValor2;
    @Column(name = "hora")
    private Integer hora;
    @JoinColumn(name = "fk_id_pulso_unidade", referencedColumnName = "pk_id_pulso_unidade")
    @ManyToOne
    private InterPulsoUnidade fkIdPulsoUnidade;
    @JoinColumn(name = "fk_id_registo_internamento_has_parametro_vital", referencedColumnName = "pk_id_registo_internamento_has_parametro_vital", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamentoHasParametroVital fkIdRegistoInternamentoHasParametroVital;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterControloParametrosVitais() {
    }

    public InterControloParametrosVitais(Long pkIdControloParametrosVitais) {
        this.pkIdControloParametrosVitais = pkIdControloParametrosVitais;
    }

    public InterControloParametrosVitais(Long pkIdControloParametrosVitais, Date dataHoraGravadaNoSistema, Date dataRegistadaNoPaciente, float valor) {
        this.pkIdControloParametrosVitais = pkIdControloParametrosVitais;
        this.dataHoraGravadaNoSistema = dataHoraGravadaNoSistema;
        this.dataRegistadaNoPaciente = dataRegistadaNoPaciente;
        this.valor = valor;
    }

    public Long getPkIdControloParametrosVitais() {
        return pkIdControloParametrosVitais;
    }

    public void setPkIdControloParametrosVitais(Long pkIdControloParametrosVitais) {
        this.pkIdControloParametrosVitais = pkIdControloParametrosVitais;
    }

    public Date getDataHoraGravadaNoSistema() {
        return dataHoraGravadaNoSistema;
    }

    public void setDataHoraGravadaNoSistema(Date dataHoraGravadaNoSistema) {
        this.dataHoraGravadaNoSistema = dataHoraGravadaNoSistema;
    }

    public Date getDataRegistadaNoPaciente() {
        return dataRegistadaNoPaciente;
    }

    public void setDataRegistadaNoPaciente(Date dataRegistadaNoPaciente) {
        this.dataRegistadaNoPaciente = dataRegistadaNoPaciente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Integer getTaValor1() {
        return taValor1;
    }

    public void setTaValor1(Integer taValor1) {
        this.taValor1 = taValor1;
    }

    public Integer getTaValor2() {
        return taValor2;
    }

    public void setTaValor2(Integer taValor2) {
        this.taValor2 = taValor2;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public InterPulsoUnidade getFkIdPulsoUnidade() {
        return fkIdPulsoUnidade;
    }

    public void setFkIdPulsoUnidade(InterPulsoUnidade fkIdPulsoUnidade) {
        this.fkIdPulsoUnidade = fkIdPulsoUnidade;
    }

    public InterRegistoInternamentoHasParametroVital getFkIdRegistoInternamentoHasParametroVital() {
        return fkIdRegistoInternamentoHasParametroVital;
    }

    public void setFkIdRegistoInternamentoHasParametroVital(InterRegistoInternamentoHasParametroVital fkIdRegistoInternamentoHasParametroVital) {
        this.fkIdRegistoInternamentoHasParametroVital = fkIdRegistoInternamentoHasParametroVital;
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
        hash += (pkIdControloParametrosVitais != null ? pkIdControloParametrosVitais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterControloParametrosVitais)) {
            return false;
        }
        InterControloParametrosVitais other = (InterControloParametrosVitais) object;
        if ((this.pkIdControloParametrosVitais == null && other.pkIdControloParametrosVitais != null) || (this.pkIdControloParametrosVitais != null && !this.pkIdControloParametrosVitais.equals(other.pkIdControloParametrosVitais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterControloParametrosVitais[ pkIdControloParametrosVitais=" + pkIdControloParametrosVitais + " ]";
    }
    
}
