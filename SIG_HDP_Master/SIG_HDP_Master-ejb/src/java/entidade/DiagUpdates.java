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
@Table(name = "diag_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagUpdates.findAll", query = "SELECT d FROM DiagUpdates d"),
    @NamedQuery(name = "DiagUpdates.findByPkIdUpdates", query = "SELECT d FROM DiagUpdates d WHERE d.pkIdUpdates = :pkIdUpdates"),
    @NamedQuery(name = "DiagUpdates.findBySubcategoriaExame", query = "SELECT d FROM DiagUpdates d WHERE d.subcategoriaExame = :subcategoriaExame"),
    @NamedQuery(name = "DiagUpdates.findByTipoAmostra", query = "SELECT d FROM DiagUpdates d WHERE d.tipoAmostra = :tipoAmostra"),
    @NamedQuery(name = "DiagUpdates.findByGrupoSanguineo", query = "SELECT d FROM DiagUpdates d WHERE d.grupoSanguineo = :grupoSanguineo"),
    @NamedQuery(name = "DiagUpdates.findByComponenteSanguineo", query = "SELECT d FROM DiagUpdates d WHERE d.componenteSanguineo = :componenteSanguineo"),
    @NamedQuery(name = "DiagUpdates.findByCategoriaExame", query = "SELECT d FROM DiagUpdates d WHERE d.categoriaExame = :categoriaExame"),
    @NamedQuery(name = "DiagUpdates.findByExame", query = "SELECT d FROM DiagUpdates d WHERE d.exame = :exame"),
    @NamedQuery(name = "DiagUpdates.findByCaracterTransfusao", query = "SELECT d FROM DiagUpdates d WHERE d.caracterTransfusao = :caracterTransfusao"),
    @NamedQuery(name = "DiagUpdates.findByEstadoClinico", query = "SELECT d FROM DiagUpdates d WHERE d.estadoClinico = :estadoClinico"),
    @NamedQuery(name = "DiagUpdates.findByNumeroDoacao", query = "SELECT d FROM DiagUpdates d WHERE d.numeroDoacao = :numeroDoacao"),
    @NamedQuery(name = "DiagUpdates.findByRespostasQuestoesRequisicaoComponentes", query = "SELECT d FROM DiagUpdates d WHERE d.respostasQuestoesRequisicaoComponentes = :respostasQuestoesRequisicaoComponentes"),
    @NamedQuery(name = "DiagUpdates.findByResultadoExameCandidato", query = "SELECT d FROM DiagUpdates d WHERE d.resultadoExameCandidato = :resultadoExameCandidato"),
    @NamedQuery(name = "DiagUpdates.findByResultadoTesteCompatibilidade", query = "SELECT d FROM DiagUpdates d WHERE d.resultadoTesteCompatibilidade = :resultadoTesteCompatibilidade"),
    @NamedQuery(name = "DiagUpdates.findByResultadoTriagem", query = "SELECT d FROM DiagUpdates d WHERE d.resultadoTriagem = :resultadoTriagem"),
    @NamedQuery(name = "DiagUpdates.findBySectorExame", query = "SELECT d FROM DiagUpdates d WHERE d.sectorExame = :sectorExame"),
    @NamedQuery(name = "DiagUpdates.findByTipoDoacao", query = "SELECT d FROM DiagUpdates d WHERE d.tipoDoacao = :tipoDoacao"),
    @NamedQuery(name = "DiagUpdates.findByTipoResultadoExame", query = "SELECT d FROM DiagUpdates d WHERE d.tipoResultadoExame = :tipoResultadoExame")})
public class DiagUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_updates", nullable = false)
    private Integer pkIdUpdates;
    @Column(name = "subcategoria_exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subcategoriaExame;
    @Column(name = "tipo_amostra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoAmostra;
    @Column(name = "grupo_sanguineo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grupoSanguineo;
    @Column(name = "componente_sanguineo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date componenteSanguineo;
    @Column(name = "categoria_exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date categoriaExame;
    @Column(name = "exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exame;
    @Column(name = "caracter_transfusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date caracterTransfusao;
    @Column(name = "estado_clinico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoClinico;
    @Column(name = "numero_doacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date numeroDoacao;
    @Column(name = "respostas_questoes_requisicao_componentes")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respostasQuestoesRequisicaoComponentes;
    @Column(name = "resultado_exame_candidato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoExameCandidato;
    @Column(name = "resultado_teste_compatibilidade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoTesteCompatibilidade;
    @Column(name = "resultado_triagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoTriagem;
    @Column(name = "sector_exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sectorExame;
    @Column(name = "tipo_doacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoDoacao;
    @Column(name = "tipo_resultado_exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoResultadoExame;

    public DiagUpdates() {
    }

    public DiagUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Integer getPkIdUpdates() {
        return pkIdUpdates;
    }

    public void setPkIdUpdates(Integer pkIdUpdates) {
        this.pkIdUpdates = pkIdUpdates;
    }

    public Date getSubcategoriaExame() {
        return subcategoriaExame;
    }

    public void setSubcategoriaExame(Date subcategoriaExame) {
        this.subcategoriaExame = subcategoriaExame;
    }

    public Date getTipoAmostra() {
        return tipoAmostra;
    }

    public void setTipoAmostra(Date tipoAmostra) {
        this.tipoAmostra = tipoAmostra;
    }

    public Date getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(Date grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public Date getComponenteSanguineo() {
        return componenteSanguineo;
    }

    public void setComponenteSanguineo(Date componenteSanguineo) {
        this.componenteSanguineo = componenteSanguineo;
    }

    public Date getCategoriaExame() {
        return categoriaExame;
    }

    public void setCategoriaExame(Date categoriaExame) {
        this.categoriaExame = categoriaExame;
    }

    public Date getExame() {
        return exame;
    }

    public void setExame(Date exame) {
        this.exame = exame;
    }

    public Date getCaracterTransfusao() {
        return caracterTransfusao;
    }

    public void setCaracterTransfusao(Date caracterTransfusao) {
        this.caracterTransfusao = caracterTransfusao;
    }

    public Date getEstadoClinico() {
        return estadoClinico;
    }

    public void setEstadoClinico(Date estadoClinico) {
        this.estadoClinico = estadoClinico;
    }

    public Date getNumeroDoacao() {
        return numeroDoacao;
    }

    public void setNumeroDoacao(Date numeroDoacao) {
        this.numeroDoacao = numeroDoacao;
    }

    public Date getRespostasQuestoesRequisicaoComponentes() {
        return respostasQuestoesRequisicaoComponentes;
    }

    public void setRespostasQuestoesRequisicaoComponentes(Date respostasQuestoesRequisicaoComponentes) {
        this.respostasQuestoesRequisicaoComponentes = respostasQuestoesRequisicaoComponentes;
    }

    public Date getResultadoExameCandidato() {
        return resultadoExameCandidato;
    }

    public void setResultadoExameCandidato(Date resultadoExameCandidato) {
        this.resultadoExameCandidato = resultadoExameCandidato;
    }

    public Date getResultadoTesteCompatibilidade() {
        return resultadoTesteCompatibilidade;
    }

    public void setResultadoTesteCompatibilidade(Date resultadoTesteCompatibilidade) {
        this.resultadoTesteCompatibilidade = resultadoTesteCompatibilidade;
    }

    public Date getResultadoTriagem() {
        return resultadoTriagem;
    }

    public void setResultadoTriagem(Date resultadoTriagem) {
        this.resultadoTriagem = resultadoTriagem;
    }

    public Date getSectorExame() {
        return sectorExame;
    }

    public void setSectorExame(Date sectorExame) {
        this.sectorExame = sectorExame;
    }

    public Date getTipoDoacao() {
        return tipoDoacao;
    }

    public void setTipoDoacao(Date tipoDoacao) {
        this.tipoDoacao = tipoDoacao;
    }

    public Date getTipoResultadoExame() {
        return tipoResultadoExame;
    }

    public void setTipoResultadoExame(Date tipoResultadoExame) {
        this.tipoResultadoExame = tipoResultadoExame;
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
        if (!(object instanceof DiagUpdates)) {
            return false;
        }
        DiagUpdates other = (DiagUpdates) object;
        if ((this.pkIdUpdates == null && other.pkIdUpdates != null) || (this.pkIdUpdates != null && !this.pkIdUpdates.equals(other.pkIdUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagUpdates[ pkIdUpdates=" + pkIdUpdates + " ]";
    }
    
}
