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
@Table(name = "amb_ce_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCeUpdates.findAll", query = "SELECT a FROM AmbCeUpdates a"),
    @NamedQuery(name = "AmbCeUpdates.findByPkIdCeUpdates", query = "SELECT a FROM AmbCeUpdates a WHERE a.pkIdCeUpdates = :pkIdCeUpdates"),
    @NamedQuery(name = "AmbCeUpdates.findByClassificacaoDor", query = "SELECT a FROM AmbCeUpdates a WHERE a.classificacaoDor = :classificacaoDor"),
    @NamedQuery(name = "AmbCeUpdates.findByConsultorio", query = "SELECT a FROM AmbCeUpdates a WHERE a.consultorio = :consultorio"),
    @NamedQuery(name = "AmbCeUpdates.findByEstado", query = "SELECT a FROM AmbCeUpdates a WHERE a.estado = :estado"),
    @NamedQuery(name = "AmbCeUpdates.findByEstadoPagamento", query = "SELECT a FROM AmbCeUpdates a WHERE a.estadoPagamento = :estadoPagamento"),
    @NamedQuery(name = "AmbCeUpdates.findBySinal", query = "SELECT a FROM AmbCeUpdates a WHERE a.sinal = :sinal"),
    @NamedQuery(name = "AmbCeUpdates.findByObservacoesMedicas", query = "SELECT a FROM AmbCeUpdates a WHERE a.observacoesMedicas = :observacoesMedicas"),
    @NamedQuery(name = "AmbCeUpdates.findByImpressoesGerais", query = "SELECT a FROM AmbCeUpdates a WHERE a.impressoesGerais = :impressoesGerais"),
    @NamedQuery(name = "AmbCeUpdates.findByColoracao", query = "SELECT a FROM AmbCeUpdates a WHERE a.coloracao = :coloracao"),
    @NamedQuery(name = "AmbCeUpdates.findByEstadoHidratacao", query = "SELECT a FROM AmbCeUpdates a WHERE a.estadoHidratacao = :estadoHidratacao"),
    @NamedQuery(name = "AmbCeUpdates.findByCor", query = "SELECT a FROM AmbCeUpdates a WHERE a.cor = :cor"),
    @NamedQuery(name = "AmbCeUpdates.findByTextura", query = "SELECT a FROM AmbCeUpdates a WHERE a.textura = :textura"),
    @NamedQuery(name = "AmbCeUpdates.findByTurgor", query = "SELECT a FROM AmbCeUpdates a WHERE a.turgor = :turgor"),
    @NamedQuery(name = "AmbCeUpdates.findByEspessura", query = "SELECT a FROM AmbCeUpdates a WHERE a.espessura = :espessura"),
    @NamedQuery(name = "AmbCeUpdates.findByTamanho", query = "SELECT a FROM AmbCeUpdates a WHERE a.tamanho = :tamanho"),
    @NamedQuery(name = "AmbCeUpdates.findByAderencia", query = "SELECT a FROM AmbCeUpdates a WHERE a.aderencia = :aderencia"),
    @NamedQuery(name = "AmbCeUpdates.findByEstadoNotificacao", query = "SELECT a FROM AmbCeUpdates a WHERE a.estadoNotificacao = :estadoNotificacao"),
    @NamedQuery(name = "AmbCeUpdates.findByConfirmacao", query = "SELECT a FROM AmbCeUpdates a WHERE a.confirmacao = :confirmacao")})
public class AmbCeUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_ce_updates", nullable = false)
    private Integer pkIdCeUpdates;
    @Column(name = "classificacao_dor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date classificacaoDor;
    @Column(name = "consultorio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date consultorio;
    @Column(name = "estado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estado;
    @Column(name = "estado_pagamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoPagamento;
    @Column(name = "sinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sinal;
    @Column(name = "observacoes_medicas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date observacoesMedicas;
    @Column(name = "impressoes_gerais")
    @Temporal(TemporalType.TIMESTAMP)
    private Date impressoesGerais;
    @Column(name = "coloracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date coloracao;
    @Column(name = "estado_hidratacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoHidratacao;
    @Column(name = "cor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cor;
    @Column(name = "textura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date textura;
    @Column(name = "turgor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date turgor;
    @Column(name = "espessura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date espessura;
    @Column(name = "tamanho")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tamanho;
    @Column(name = "aderencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aderencia;
    @Column(name = "estado_notificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoNotificacao;
    @Column(name = "confirmacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmacao;

    public AmbCeUpdates() {
    }

    public AmbCeUpdates(Integer pkIdCeUpdates) {
        this.pkIdCeUpdates = pkIdCeUpdates;
    }

    public Integer getPkIdCeUpdates() {
        return pkIdCeUpdates;
    }

    public void setPkIdCeUpdates(Integer pkIdCeUpdates) {
        this.pkIdCeUpdates = pkIdCeUpdates;
    }

    public Date getClassificacaoDor() {
        return classificacaoDor;
    }

    public void setClassificacaoDor(Date classificacaoDor) {
        this.classificacaoDor = classificacaoDor;
    }

    public Date getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Date consultorio) {
        this.consultorio = consultorio;
    }

    public Date getEstado() {
        return estado;
    }

    public void setEstado(Date estado) {
        this.estado = estado;
    }

    public Date getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(Date estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public Date getSinal() {
        return sinal;
    }

    public void setSinal(Date sinal) {
        this.sinal = sinal;
    }

    public Date getObservacoesMedicas() {
        return observacoesMedicas;
    }

    public void setObservacoesMedicas(Date observacoesMedicas) {
        this.observacoesMedicas = observacoesMedicas;
    }

    public Date getImpressoesGerais() {
        return impressoesGerais;
    }

    public void setImpressoesGerais(Date impressoesGerais) {
        this.impressoesGerais = impressoesGerais;
    }

    public Date getColoracao() {
        return coloracao;
    }

    public void setColoracao(Date coloracao) {
        this.coloracao = coloracao;
    }

    public Date getEstadoHidratacao() {
        return estadoHidratacao;
    }

    public void setEstadoHidratacao(Date estadoHidratacao) {
        this.estadoHidratacao = estadoHidratacao;
    }

    public Date getCor() {
        return cor;
    }

    public void setCor(Date cor) {
        this.cor = cor;
    }

    public Date getTextura() {
        return textura;
    }

    public void setTextura(Date textura) {
        this.textura = textura;
    }

    public Date getTurgor() {
        return turgor;
    }

    public void setTurgor(Date turgor) {
        this.turgor = turgor;
    }

    public Date getEspessura() {
        return espessura;
    }

    public void setEspessura(Date espessura) {
        this.espessura = espessura;
    }

    public Date getTamanho() {
        return tamanho;
    }

    public void setTamanho(Date tamanho) {
        this.tamanho = tamanho;
    }

    public Date getAderencia() {
        return aderencia;
    }

    public void setAderencia(Date aderencia) {
        this.aderencia = aderencia;
    }

    public Date getEstadoNotificacao() {
        return estadoNotificacao;
    }

    public void setEstadoNotificacao(Date estadoNotificacao) {
        this.estadoNotificacao = estadoNotificacao;
    }

    public Date getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(Date confirmacao) {
        this.confirmacao = confirmacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCeUpdates != null ? pkIdCeUpdates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCeUpdates)) {
            return false;
        }
        AmbCeUpdates other = (AmbCeUpdates) object;
        if ((this.pkIdCeUpdates == null && other.pkIdCeUpdates != null) || (this.pkIdCeUpdates != null && !this.pkIdCeUpdates.equals(other.pkIdCeUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCeUpdates[ pkIdCeUpdates=" + pkIdCeUpdates + " ]";
    }
    
}
