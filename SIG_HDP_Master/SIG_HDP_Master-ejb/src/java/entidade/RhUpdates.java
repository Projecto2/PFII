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
@Table(name = "rh_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhUpdates.findAll", query = "SELECT r FROM RhUpdates r"),
    @NamedQuery(name = "RhUpdates.findByPkIdUpdates", query = "SELECT r FROM RhUpdates r WHERE r.pkIdUpdates = :pkIdUpdates"),
    @NamedQuery(name = "RhUpdates.findByCategoriaProfissional", query = "SELECT r FROM RhUpdates r WHERE r.categoriaProfissional = :categoriaProfissional"),
    @NamedQuery(name = "RhUpdates.findByClassificacaoDoCriterio", query = "SELECT r FROM RhUpdates r WHERE r.classificacaoDoCriterio = :classificacaoDoCriterio"),
    @NamedQuery(name = "RhUpdates.findByCriterioDeAvaliacao", query = "SELECT r FROM RhUpdates r WHERE r.criterioDeAvaliacao = :criterioDeAvaliacao"),
    @NamedQuery(name = "RhUpdates.findByDepartamento", query = "SELECT r FROM RhUpdates r WHERE r.departamento = :departamento"),
    @NamedQuery(name = "RhUpdates.findByEspecialidade", query = "SELECT r FROM RhUpdates r WHERE r.especialidade = :especialidade"),
    @NamedQuery(name = "RhUpdates.findByEstadoCandidato", query = "SELECT r FROM RhUpdates r WHERE r.estadoCandidato = :estadoCandidato"),
    @NamedQuery(name = "RhUpdates.findByEstadoContrato", query = "SELECT r FROM RhUpdates r WHERE r.estadoContrato = :estadoContrato"),
    @NamedQuery(name = "RhUpdates.findByEstadoEstagiario", query = "SELECT r FROM RhUpdates r WHERE r.estadoEstagiario = :estadoEstagiario"),
    @NamedQuery(name = "RhUpdates.findByEstadoFerias", query = "SELECT r FROM RhUpdates r WHERE r.estadoFerias = :estadoFerias"),
    @NamedQuery(name = "RhUpdates.findByEstadoFuncionario", query = "SELECT r FROM RhUpdates r WHERE r.estadoFuncionario = :estadoFuncionario"),
    @NamedQuery(name = "RhUpdates.findByFormaPagamento", query = "SELECT r FROM RhUpdates r WHERE r.formaPagamento = :formaPagamento"),
    @NamedQuery(name = "RhUpdates.findByFuncao", query = "SELECT r FROM RhUpdates r WHERE r.funcao = :funcao"),
    @NamedQuery(name = "RhUpdates.findByNivelAcademico", query = "SELECT r FROM RhUpdates r WHERE r.nivelAcademico = :nivelAcademico"),
    @NamedQuery(name = "RhUpdates.findBySeccaoTrabalho", query = "SELECT r FROM RhUpdates r WHERE r.seccaoTrabalho = :seccaoTrabalho"),
    @NamedQuery(name = "RhUpdates.findBySubsidio", query = "SELECT r FROM RhUpdates r WHERE r.subsidio = :subsidio"),
    @NamedQuery(name = "RhUpdates.findByPeriodoAulas", query = "SELECT r FROM RhUpdates r WHERE r.periodoAulas = :periodoAulas"),
    @NamedQuery(name = "RhUpdates.findByProfissao", query = "SELECT r FROM RhUpdates r WHERE r.profissao = :profissao"),
    @NamedQuery(name = "RhUpdates.findByTipoDeHorarioTrabalho", query = "SELECT r FROM RhUpdates r WHERE r.tipoDeHorarioTrabalho = :tipoDeHorarioTrabalho"),
    @NamedQuery(name = "RhUpdates.findByTipoContrato", query = "SELECT r FROM RhUpdates r WHERE r.tipoContrato = :tipoContrato"),
    @NamedQuery(name = "RhUpdates.findByTipoEstagio", query = "SELECT r FROM RhUpdates r WHERE r.tipoEstagio = :tipoEstagio"),
    @NamedQuery(name = "RhUpdates.findByTipoFalta", query = "SELECT r FROM RhUpdates r WHERE r.tipoFalta = :tipoFalta"),
    @NamedQuery(name = "RhUpdates.findByTipoFuncionario", query = "SELECT r FROM RhUpdates r WHERE r.tipoFuncionario = :tipoFuncionario")})
public class RhUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_updates", nullable = false)
    private Integer pkIdUpdates;
    @Column(name = "categoria_profissional")
    @Temporal(TemporalType.TIMESTAMP)
    private Date categoriaProfissional;
    @Column(name = "classificacao_do_criterio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date classificacaoDoCriterio;
    @Column(name = "criterio_de_avaliacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date criterioDeAvaliacao;
    @Column(name = "departamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departamento;
    @Column(name = "especialidade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date especialidade;
    @Column(name = "estado_candidato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoCandidato;
    @Column(name = "estado_contrato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoContrato;
    @Column(name = "estado_estagiario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoEstagiario;
    @Column(name = "estado_ferias")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFerias;
    @Column(name = "estado_funcionario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFuncionario;
    @Column(name = "forma_pagamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date formaPagamento;
    @Column(name = "funcao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funcao;
    @Column(name = "nivel_academico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nivelAcademico;
    @Column(name = "seccao_trabalho")
    @Temporal(TemporalType.TIMESTAMP)
    private Date seccaoTrabalho;
    @Column(name = "subsidio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subsidio;
    @Column(name = "periodo_aulas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodoAulas;
    @Column(name = "profissao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date profissao;
    @Column(name = "tipo_de_horario_trabalho")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoDeHorarioTrabalho;
    @Column(name = "tipo_contrato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoContrato;
    @Column(name = "tipo_estagio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoEstagio;
    @Column(name = "tipo_falta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoFalta;
    @Column(name = "tipo_funcionario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoFuncionario;

    public RhUpdates() {
    }

    public RhUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Integer getPkIdUpdates() {
        return pkIdUpdates;
    }

    public void setPkIdUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Date getCategoriaProfissional() {
        return categoriaProfissional;
    }

    public void setCategoriaProfissional(Date categoriaProfissional) {
        this.categoriaProfissional = categoriaProfissional;
    }

    public Date getClassificacaoDoCriterio() {
        return classificacaoDoCriterio;
    }

    public void setClassificacaoDoCriterio(Date classificacaoDoCriterio) {
        this.classificacaoDoCriterio = classificacaoDoCriterio;
    }

    public Date getCriterioDeAvaliacao() {
        return criterioDeAvaliacao;
    }

    public void setCriterioDeAvaliacao(Date criterioDeAvaliacao) {
        this.criterioDeAvaliacao = criterioDeAvaliacao;
    }

    public Date getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Date departamento) {
        this.departamento = departamento;
    }

    public Date getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Date especialidade) {
        this.especialidade = especialidade;
    }

    public Date getEstadoCandidato() {
        return estadoCandidato;
    }

    public void setEstadoCandidato(Date estadoCandidato) {
        this.estadoCandidato = estadoCandidato;
    }

    public Date getEstadoContrato() {
        return estadoContrato;
    }

    public void setEstadoContrato(Date estadoContrato) {
        this.estadoContrato = estadoContrato;
    }

    public Date getEstadoEstagiario() {
        return estadoEstagiario;
    }

    public void setEstadoEstagiario(Date estadoEstagiario) {
        this.estadoEstagiario = estadoEstagiario;
    }

    public Date getEstadoFerias() {
        return estadoFerias;
    }

    public void setEstadoFerias(Date estadoFerias) {
        this.estadoFerias = estadoFerias;
    }

    public Date getEstadoFuncionario() {
        return estadoFuncionario;
    }

    public void setEstadoFuncionario(Date estadoFuncionario) {
        this.estadoFuncionario = estadoFuncionario;
    }

    public Date getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(Date formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Date getFuncao() {
        return funcao;
    }

    public void setFuncao(Date funcao) {
        this.funcao = funcao;
    }

    public Date getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(Date nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public Date getSeccaoTrabalho() {
        return seccaoTrabalho;
    }

    public void setSeccaoTrabalho(Date seccaoTrabalho) {
        this.seccaoTrabalho = seccaoTrabalho;
    }

    public Date getSubsidio() {
        return subsidio;
    }

    public void setSubsidio(Date subsidio) {
        this.subsidio = subsidio;
    }

    public Date getPeriodoAulas() {
        return periodoAulas;
    }

    public void setPeriodoAulas(Date periodoAulas) {
        this.periodoAulas = periodoAulas;
    }

    public Date getProfissao() {
        return profissao;
    }

    public void setProfissao(Date profissao) {
        this.profissao = profissao;
    }

    public Date getTipoDeHorarioTrabalho() {
        return tipoDeHorarioTrabalho;
    }

    public void setTipoDeHorarioTrabalho(Date tipoDeHorarioTrabalho) {
        this.tipoDeHorarioTrabalho = tipoDeHorarioTrabalho;
    }

    public Date getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(Date tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Date getTipoEstagio() {
        return tipoEstagio;
    }

    public void setTipoEstagio(Date tipoEstagio) {
        this.tipoEstagio = tipoEstagio;
    }

    public Date getTipoFalta() {
        return tipoFalta;
    }

    public void setTipoFalta(Date tipoFalta) {
        this.tipoFalta = tipoFalta;
    }

    public Date getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(Date tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
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
        if (!(object instanceof RhUpdates)) {
            return false;
        }
        RhUpdates other = (RhUpdates) object;
        if ((this.pkIdUpdates == null && other.pkIdUpdates != null) || (this.pkIdUpdates != null && !this.pkIdUpdates.equals(other.pkIdUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhUpdates[ pkIdUpdates=" + pkIdUpdates + " ]";
    }
    
}
