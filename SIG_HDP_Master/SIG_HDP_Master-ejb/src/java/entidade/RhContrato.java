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
@Table(name = "rh_contrato", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhContrato.findAll", query = "SELECT r FROM RhContrato r"),
    @NamedQuery(name = "RhContrato.findByPkIdContrato", query = "SELECT r FROM RhContrato r WHERE r.pkIdContrato = :pkIdContrato"),
    @NamedQuery(name = "RhContrato.findByDuracaoMeses", query = "SELECT r FROM RhContrato r WHERE r.duracaoMeses = :duracaoMeses"),
    @NamedQuery(name = "RhContrato.findByDataInicio", query = "SELECT r FROM RhContrato r WHERE r.dataInicio = :dataInicio"),
    @NamedQuery(name = "RhContrato.findByDataTermino", query = "SELECT r FROM RhContrato r WHERE r.dataTermino = :dataTermino"),
    @NamedQuery(name = "RhContrato.findBySalarioBase", query = "SELECT r FROM RhContrato r WHERE r.salarioBase = :salarioBase"),
    @NamedQuery(name = "RhContrato.findByDataCadastro", query = "SELECT r FROM RhContrato r WHERE r.dataCadastro = :dataCadastro")})
public class RhContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_contrato", nullable = false)
    private Integer pkIdContrato;
    @Column(name = "duracao_meses")
    private Integer duracaoMeses;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "data_termino")
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salario_base", nullable = false)
    private double salarioBase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_anexo_contrato", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdAnexoContrato;
    @JoinColumn(name = "fk_id_candidato", referencedColumnName = "pk_id_candidato")
    @ManyToOne
    private RhCandidato fkIdCandidato;
    @JoinColumn(name = "fk_id_estado_contrato", referencedColumnName = "pk_id_estado_contrato", nullable = false)
    @ManyToOne(optional = false)
    private RhEstadoContrato fkIdEstadoContrato;
    @JoinColumn(name = "fk_id_forma_pagamento", referencedColumnName = "pk_id_forma_pagamento", nullable = false)
    @ManyToOne(optional = false)
    private RhFormaPagamento fkIdFormaPagamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_tipo_contrato", referencedColumnName = "pk_id_tipo_contrato")
    @ManyToOne
    private RhTipoContrato fkIdTipoContrato;

    public RhContrato() {
    }

    public RhContrato(Integer pkIdContrato) {
        this.pkIdContrato = pkIdContrato;
    }

    public RhContrato(Integer pkIdContrato, Date dataInicio, double salarioBase, Date dataCadastro) {
        this.pkIdContrato = pkIdContrato;
        this.dataInicio = dataInicio;
        this.salarioBase = salarioBase;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdContrato() {
        return pkIdContrato;
    }

    public void setPkIdContrato(Integer pkIdContrato) {
        this.pkIdContrato = pkIdContrato;
    }

    public Integer getDuracaoMeses() {
        return duracaoMeses;
    }

    public void setDuracaoMeses(Integer duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public GrlFicheiroAnexado getFkIdAnexoContrato() {
        return fkIdAnexoContrato;
    }

    public void setFkIdAnexoContrato(GrlFicheiroAnexado fkIdAnexoContrato) {
        this.fkIdAnexoContrato = fkIdAnexoContrato;
    }

    public RhCandidato getFkIdCandidato() {
        return fkIdCandidato;
    }

    public void setFkIdCandidato(RhCandidato fkIdCandidato) {
        this.fkIdCandidato = fkIdCandidato;
    }

    public RhEstadoContrato getFkIdEstadoContrato() {
        return fkIdEstadoContrato;
    }

    public void setFkIdEstadoContrato(RhEstadoContrato fkIdEstadoContrato) {
        this.fkIdEstadoContrato = fkIdEstadoContrato;
    }

    public RhFormaPagamento getFkIdFormaPagamento() {
        return fkIdFormaPagamento;
    }

    public void setFkIdFormaPagamento(RhFormaPagamento fkIdFormaPagamento) {
        this.fkIdFormaPagamento = fkIdFormaPagamento;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public RhTipoContrato getFkIdTipoContrato() {
        return fkIdTipoContrato;
    }

    public void setFkIdTipoContrato(RhTipoContrato fkIdTipoContrato) {
        this.fkIdTipoContrato = fkIdTipoContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdContrato != null ? pkIdContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhContrato)) {
            return false;
        }
        RhContrato other = (RhContrato) object;
        if ((this.pkIdContrato == null && other.pkIdContrato != null) || (this.pkIdContrato != null && !this.pkIdContrato.equals(other.pkIdContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhContrato[ pkIdContrato=" + pkIdContrato + " ]";
    }
    
}
