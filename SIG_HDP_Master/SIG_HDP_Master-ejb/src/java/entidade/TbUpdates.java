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
@Table(name = "tb_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUpdates.findAll", query = "SELECT t FROM TbUpdates t"),
    @NamedQuery(name = "TbUpdates.findByPkUpdate", query = "SELECT t FROM TbUpdates t WHERE t.pkUpdate = :pkUpdate"),
    @NamedQuery(name = "TbUpdates.findByClassificacaoDoenca", query = "SELECT t FROM TbUpdates t WHERE t.classificacaoDoenca = :classificacaoDoenca"),
    @NamedQuery(name = "TbUpdates.findByFaseTratamento", query = "SELECT t FROM TbUpdates t WHERE t.faseTratamento = :faseTratamento"),
    @NamedQuery(name = "TbUpdates.findByGrupoRisco", query = "SELECT t FROM TbUpdates t WHERE t.grupoRisco = :grupoRisco"),
    @NamedQuery(name = "TbUpdates.findByMedicamento", query = "SELECT t FROM TbUpdates t WHERE t.medicamento = :medicamento"),
    @NamedQuery(name = "TbUpdates.findBySubclassificacaoDoenca", query = "SELECT t FROM TbUpdates t WHERE t.subclassificacaoDoenca = :subclassificacaoDoenca"),
    @NamedQuery(name = "TbUpdates.findByTipoDoente", query = "SELECT t FROM TbUpdates t WHERE t.tipoDoente = :tipoDoente"),
    @NamedQuery(name = "TbUpdates.findByTipoExame", query = "SELECT t FROM TbUpdates t WHERE t.tipoExame = :tipoExame"),
    @NamedQuery(name = "TbUpdates.findByTipoNotificacao", query = "SELECT t FROM TbUpdates t WHERE t.tipoNotificacao = :tipoNotificacao"),
    @NamedQuery(name = "TbUpdates.findByDoenteTratamento", query = "SELECT t FROM TbUpdates t WHERE t.doenteTratamento = :doenteTratamento"),
    @NamedQuery(name = "TbUpdates.findBySintoma", query = "SELECT t FROM TbUpdates t WHERE t.sintoma = :sintoma")})
public class TbUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_update", nullable = false)
    private Integer pkUpdate;
    @Column(name = "classificacao_doenca")
    @Temporal(TemporalType.TIMESTAMP)
    private Date classificacaoDoenca;
    @Column(name = "fase_tratamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date faseTratamento;
    @Column(name = "grupo_risco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grupoRisco;
    @Column(name = "medicamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date medicamento;
    @Column(name = "subclassificacao_doenca")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subclassificacaoDoenca;
    @Column(name = "tipo_doente")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoDoente;
    @Column(name = "tipo_exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoExame;
    @Column(name = "tipo_notificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoNotificacao;
    @Column(name = "doente_tratamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doenteTratamento;
    @Column(name = "sintoma")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sintoma;

    public TbUpdates() {
    }

    public TbUpdates(Integer pkUpdate) {
        this.pkUpdate = pkUpdate;
    }

    public Integer getPkUpdate() {
        return pkUpdate;
    }

    public void setPkUpdate(Integer pkUpdate) {
        this.pkUpdate = pkUpdate;
    }

    public Date getClassificacaoDoenca() {
        return classificacaoDoenca;
    }

    public void setClassificacaoDoenca(Date classificacaoDoenca) {
        this.classificacaoDoenca = classificacaoDoenca;
    }

    public Date getFaseTratamento() {
        return faseTratamento;
    }

    public void setFaseTratamento(Date faseTratamento) {
        this.faseTratamento = faseTratamento;
    }

    public Date getGrupoRisco() {
        return grupoRisco;
    }

    public void setGrupoRisco(Date grupoRisco) {
        this.grupoRisco = grupoRisco;
    }

    public Date getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Date medicamento) {
        this.medicamento = medicamento;
    }

    public Date getSubclassificacaoDoenca() {
        return subclassificacaoDoenca;
    }

    public void setSubclassificacaoDoenca(Date subclassificacaoDoenca) {
        this.subclassificacaoDoenca = subclassificacaoDoenca;
    }

    public Date getTipoDoente() {
        return tipoDoente;
    }

    public void setTipoDoente(Date tipoDoente) {
        this.tipoDoente = tipoDoente;
    }

    public Date getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(Date tipoExame) {
        this.tipoExame = tipoExame;
    }

    public Date getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(Date tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public Date getDoenteTratamento() {
        return doenteTratamento;
    }

    public void setDoenteTratamento(Date doenteTratamento) {
        this.doenteTratamento = doenteTratamento;
    }

    public Date getSintoma() {
        return sintoma;
    }

    public void setSintoma(Date sintoma) {
        this.sintoma = sintoma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUpdate != null ? pkUpdate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbUpdates)) {
            return false;
        }
        TbUpdates other = (TbUpdates) object;
        if ((this.pkUpdate == null && other.pkUpdate != null) || (this.pkUpdate != null && !this.pkUpdate.equals(other.pkUpdate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbUpdates[ pkUpdate=" + pkUpdate + " ]";
    }
    
}
