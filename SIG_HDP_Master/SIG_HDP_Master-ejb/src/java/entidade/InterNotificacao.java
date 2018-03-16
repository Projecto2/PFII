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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_notificacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterNotificacao.findAll", query = "SELECT i FROM InterNotificacao i"),
    @NamedQuery(name = "InterNotificacao.findByPkIdNotificacao", query = "SELECT i FROM InterNotificacao i WHERE i.pkIdNotificacao = :pkIdNotificacao"),
    @NamedQuery(name = "InterNotificacao.findByAssunto", query = "SELECT i FROM InterNotificacao i WHERE i.assunto = :assunto"),
    @NamedQuery(name = "InterNotificacao.findByDescricao", query = "SELECT i FROM InterNotificacao i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterNotificacao.findByDataDeNotificacao", query = "SELECT i FROM InterNotificacao i WHERE i.dataDeNotificacao = :dataDeNotificacao")})
public class InterNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_notificacao", nullable = false)
    private Integer pkIdNotificacao;
    @Size(max = 100)
    @Column(name = "assunto", length = 100)
    private String assunto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_de_notificacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeNotificacao;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente")
    @ManyToOne
    private AdmsPaciente fkIdPaciente;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto")
    @ManyToOne
    private FarmProduto fkIdProduto;
    @JoinColumn(name = "fk_id_enfermaria", referencedColumnName = "pk_id_enfermaria", nullable = false)
    @ManyToOne(optional = false)
    private InterEnfermaria fkIdEnfermaria;
    @JoinColumn(name = "fk_id_parametro_vital", referencedColumnName = "pk_id_parametro_vital")
    @ManyToOne
    private InterParametroVital fkIdParametroVital;
    @JoinColumn(name = "fk_id_tipo_notificacao", referencedColumnName = "pk_id_tipo_notificacao", nullable = false)
    @ManyToOne(optional = false)
    private InterTipoNotificacao fkIdTipoNotificacao;

    public InterNotificacao() {
    }

    public InterNotificacao(Integer pkIdNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
    }

    public InterNotificacao(Integer pkIdNotificacao, String descricao, Date dataDeNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
        this.descricao = descricao;
        this.dataDeNotificacao = dataDeNotificacao;
    }

    public Integer getPkIdNotificacao() {
        return pkIdNotificacao;
    }

    public void setPkIdNotificacao(Integer pkIdNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataDeNotificacao() {
        return dataDeNotificacao;
    }

    public void setDataDeNotificacao(Date dataDeNotificacao) {
        this.dataDeNotificacao = dataDeNotificacao;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public FarmProduto getFkIdProduto() {
        return fkIdProduto;
    }

    public void setFkIdProduto(FarmProduto fkIdProduto) {
        this.fkIdProduto = fkIdProduto;
    }

    public InterEnfermaria getFkIdEnfermaria() {
        return fkIdEnfermaria;
    }

    public void setFkIdEnfermaria(InterEnfermaria fkIdEnfermaria) {
        this.fkIdEnfermaria = fkIdEnfermaria;
    }

    public InterParametroVital getFkIdParametroVital() {
        return fkIdParametroVital;
    }

    public void setFkIdParametroVital(InterParametroVital fkIdParametroVital) {
        this.fkIdParametroVital = fkIdParametroVital;
    }

    public InterTipoNotificacao getFkIdTipoNotificacao() {
        return fkIdTipoNotificacao;
    }

    public void setFkIdTipoNotificacao(InterTipoNotificacao fkIdTipoNotificacao) {
        this.fkIdTipoNotificacao = fkIdTipoNotificacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdNotificacao != null ? pkIdNotificacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterNotificacao)) {
            return false;
        }
        InterNotificacao other = (InterNotificacao) object;
        if ((this.pkIdNotificacao == null && other.pkIdNotificacao != null) || (this.pkIdNotificacao != null && !this.pkIdNotificacao.equals(other.pkIdNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterNotificacao[ pkIdNotificacao=" + pkIdNotificacao + " ]";
    }
    
}
