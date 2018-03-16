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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_notificacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_lote_produto", "fk_id_produto", "fk_id_estado_notificacao", "fk_id_tipo_notificacao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmNotificacao.findAll", query = "SELECT f FROM FarmNotificacao f"),
    @NamedQuery(name = "FarmNotificacao.findByPkIdNotificacao", query = "SELECT f FROM FarmNotificacao f WHERE f.pkIdNotificacao = :pkIdNotificacao"),
    @NamedQuery(name = "FarmNotificacao.findByTitulo", query = "SELECT f FROM FarmNotificacao f WHERE f.titulo = :titulo"),
    @NamedQuery(name = "FarmNotificacao.findByCorpo", query = "SELECT f FROM FarmNotificacao f WHERE f.corpo = :corpo"),
    @NamedQuery(name = "FarmNotificacao.findByDataCadastro", query = "SELECT f FROM FarmNotificacao f WHERE f.dataCadastro = :dataCadastro")})
public class FarmNotificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_notificacao", nullable = false)
    private Integer pkIdNotificacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
    @Size(max = 255)
    @Column(name = "corpo", length = 255)
    private String corpo;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_estado_notificacao", referencedColumnName = "pk_id_estado_notificacao", nullable = false)
    @ManyToOne(optional = false)
    private FarmEstadoNotificacao fkIdEstadoNotificacao;
    @JoinColumn(name = "fk_id_lote_produto", referencedColumnName = "pk_id_lote_produto")
    @ManyToOne
    private FarmLoteProduto fkIdLoteProduto;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto")
    @ManyToOne
    private FarmProduto fkIdProduto;
    @JoinColumn(name = "fk_id_tipo_notificacao", referencedColumnName = "pk_id_tipo_notificacao", nullable = false)
    @ManyToOne(optional = false)
    private FarmTipoNotificacao fkIdTipoNotificacao;

    public FarmNotificacao() {
    }

    public FarmNotificacao(Integer pkIdNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
    }

    public FarmNotificacao(Integer pkIdNotificacao, String titulo) {
        this.pkIdNotificacao = pkIdNotificacao;
        this.titulo = titulo;
    }

    public Integer getPkIdNotificacao() {
        return pkIdNotificacao;
    }

    public void setPkIdNotificacao(Integer pkIdNotificacao) {
        this.pkIdNotificacao = pkIdNotificacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public FarmEstadoNotificacao getFkIdEstadoNotificacao() {
        return fkIdEstadoNotificacao;
    }

    public void setFkIdEstadoNotificacao(FarmEstadoNotificacao fkIdEstadoNotificacao) {
        this.fkIdEstadoNotificacao = fkIdEstadoNotificacao;
    }

    public FarmLoteProduto getFkIdLoteProduto() {
        return fkIdLoteProduto;
    }

    public void setFkIdLoteProduto(FarmLoteProduto fkIdLoteProduto) {
        this.fkIdLoteProduto = fkIdLoteProduto;
    }

    public FarmProduto getFkIdProduto() {
        return fkIdProduto;
    }

    public void setFkIdProduto(FarmProduto fkIdProduto) {
        this.fkIdProduto = fkIdProduto;
    }

    public FarmTipoNotificacao getFkIdTipoNotificacao() {
        return fkIdTipoNotificacao;
    }

    public void setFkIdTipoNotificacao(FarmTipoNotificacao fkIdTipoNotificacao) {
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
        if (!(object instanceof FarmNotificacao)) {
            return false;
        }
        FarmNotificacao other = (FarmNotificacao) object;
        if ((this.pkIdNotificacao == null && other.pkIdNotificacao != null) || (this.pkIdNotificacao != null && !this.pkIdNotificacao.equals(other.pkIdNotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmNotificacao[ pkIdNotificacao=" + pkIdNotificacao + " ]";
    }
    
}
