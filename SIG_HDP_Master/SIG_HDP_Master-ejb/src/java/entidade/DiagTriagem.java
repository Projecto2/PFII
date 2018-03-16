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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_triagem", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTriagem.findAll", query = "SELECT d FROM DiagTriagem d"),
    @NamedQuery(name = "DiagTriagem.findByPkIdTriagem", query = "SELECT d FROM DiagTriagem d WHERE d.pkIdTriagem = :pkIdTriagem"),
    @NamedQuery(name = "DiagTriagem.findByGravida", query = "SELECT d FROM DiagTriagem d WHERE d.gravida = :gravida"),
    @NamedQuery(name = "DiagTriagem.findByToxicoDependente", query = "SELECT d FROM DiagTriagem d WHERE d.toxicoDependente = :toxicoDependente"),
    @NamedQuery(name = "DiagTriagem.findByPeso", query = "SELECT d FROM DiagTriagem d WHERE d.peso = :peso"),
    @NamedQuery(name = "DiagTriagem.findByUltimaDoacao", query = "SELECT d FROM DiagTriagem d WHERE d.ultimaDoacao = :ultimaDoacao"),
    @NamedQuery(name = "DiagTriagem.findByHepatite", query = "SELECT d FROM DiagTriagem d WHERE d.hepatite = :hepatite"),
    @NamedQuery(name = "DiagTriagem.findByEpilepsia", query = "SELECT d FROM DiagTriagem d WHERE d.epilepsia = :epilepsia"),
    @NamedQuery(name = "DiagTriagem.findBySifilis", query = "SELECT d FROM DiagTriagem d WHERE d.sifilis = :sifilis"),
    @NamedQuery(name = "DiagTriagem.findByTatuagem", query = "SELECT d FROM DiagTriagem d WHERE d.tatuagem = :tatuagem"),
    @NamedQuery(name = "DiagTriagem.findByTransfusao", query = "SELECT d FROM DiagTriagem d WHERE d.transfusao = :transfusao"),
    @NamedQuery(name = "DiagTriagem.findByAlimentacao", query = "SELECT d FROM DiagTriagem d WHERE d.alimentacao = :alimentacao"),
    @NamedQuery(name = "DiagTriagem.findBySono", query = "SELECT d FROM DiagTriagem d WHERE d.sono = :sono"),
    @NamedQuery(name = "DiagTriagem.findByParceiros", query = "SELECT d FROM DiagTriagem d WHERE d.parceiros = :parceiros"),
    @NamedQuery(name = "DiagTriagem.findByDataTriagem", query = "SELECT d FROM DiagTriagem d WHERE d.dataTriagem = :dataTriagem")})
public class DiagTriagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_triagem", nullable = false)
    private Integer pkIdTriagem;
    @Column(name = "gravida")
    private Boolean gravida;
    @Column(name = "toxico_dependente")
    private Boolean toxicoDependente;
    @Column(name = "peso")
    private Boolean peso;
    @Column(name = "ultima_doacao")
    private Boolean ultimaDoacao;
    @Column(name = "hepatite")
    private Boolean hepatite;
    @Column(name = "epilepsia")
    private Boolean epilepsia;
    @Column(name = "sifilis")
    private Boolean sifilis;
    @Column(name = "tatuagem")
    private Boolean tatuagem;
    @Column(name = "transfusao")
    private Boolean transfusao;
    @Column(name = "alimentacao")
    private Boolean alimentacao;
    @Column(name = "sono")
    private Boolean sono;
    @Column(name = "parceiros")
    private Boolean parceiros;
    @Column(name = "data_triagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTriagem;
    @JoinColumn(name = "fk_id_candidato_dador", referencedColumnName = "pk_id_candidato_dador")
    @ManyToOne
    private DiagCandidatoDador fkIdCandidatoDador;
    @JoinColumn(name = "fk_id_resultado_triagem", referencedColumnName = "pk_id_resultado_triagem", nullable = false)
    @ManyToOne(optional = false)
    private DiagResultadoTriagem fkIdResultadoTriagem;

    public DiagTriagem() {
    }

    public DiagTriagem(Integer pkIdTriagem) {
        this.pkIdTriagem = pkIdTriagem;
    }

    public Integer getPkIdTriagem() {
        return pkIdTriagem;
    }

    public void setPkIdTriagem(Integer pkIdTriagem) {
        this.pkIdTriagem = pkIdTriagem;
    }

    public Boolean getGravida() {
        return gravida;
    }

    public void setGravida(Boolean gravida) {
        this.gravida = gravida;
    }

    public Boolean getToxicoDependente() {
        return toxicoDependente;
    }

    public void setToxicoDependente(Boolean toxicoDependente) {
        this.toxicoDependente = toxicoDependente;
    }

    public Boolean getPeso() {
        return peso;
    }

    public void setPeso(Boolean peso) {
        this.peso = peso;
    }

    public Boolean getUltimaDoacao() {
        return ultimaDoacao;
    }

    public void setUltimaDoacao(Boolean ultimaDoacao) {
        this.ultimaDoacao = ultimaDoacao;
    }

    public Boolean getHepatite() {
        return hepatite;
    }

    public void setHepatite(Boolean hepatite) {
        this.hepatite = hepatite;
    }

    public Boolean getEpilepsia() {
        return epilepsia;
    }

    public void setEpilepsia(Boolean epilepsia) {
        this.epilepsia = epilepsia;
    }

    public Boolean getSifilis() {
        return sifilis;
    }

    public void setSifilis(Boolean sifilis) {
        this.sifilis = sifilis;
    }

    public Boolean getTatuagem() {
        return tatuagem;
    }

    public void setTatuagem(Boolean tatuagem) {
        this.tatuagem = tatuagem;
    }

    public Boolean getTransfusao() {
        return transfusao;
    }

    public void setTransfusao(Boolean transfusao) {
        this.transfusao = transfusao;
    }

    public Boolean getAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(Boolean alimentacao) {
        this.alimentacao = alimentacao;
    }

    public Boolean getSono() {
        return sono;
    }

    public void setSono(Boolean sono) {
        this.sono = sono;
    }

    public Boolean getParceiros() {
        return parceiros;
    }

    public void setParceiros(Boolean parceiros) {
        this.parceiros = parceiros;
    }

    public Date getDataTriagem() {
        return dataTriagem;
    }

    public void setDataTriagem(Date dataTriagem) {
        this.dataTriagem = dataTriagem;
    }

    public DiagCandidatoDador getFkIdCandidatoDador() {
        return fkIdCandidatoDador;
    }

    public void setFkIdCandidatoDador(DiagCandidatoDador fkIdCandidatoDador) {
        this.fkIdCandidatoDador = fkIdCandidatoDador;
    }

    public DiagResultadoTriagem getFkIdResultadoTriagem() {
        return fkIdResultadoTriagem;
    }

    public void setFkIdResultadoTriagem(DiagResultadoTriagem fkIdResultadoTriagem) {
        this.fkIdResultadoTriagem = fkIdResultadoTriagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTriagem != null ? pkIdTriagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTriagem)) {
            return false;
        }
        DiagTriagem other = (DiagTriagem) object;
        if ((this.pkIdTriagem == null && other.pkIdTriagem != null) || (this.pkIdTriagem != null && !this.pkIdTriagem.equals(other.pkIdTriagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTriagem[ pkIdTriagem=" + pkIdTriagem + " ]";
    }
    
}
