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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "seg_auditoria", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAuditoria.findAll", query = "SELECT s FROM SegAuditoria s"),
    @NamedQuery(name = "SegAuditoria.findByPkIdAuditoria", query = "SELECT s FROM SegAuditoria s WHERE s.pkIdAuditoria = :pkIdAuditoria"),
    @NamedQuery(name = "SegAuditoria.findByNome", query = "SELECT s FROM SegAuditoria s WHERE s.nome = :nome"),
    @NamedQuery(name = "SegAuditoria.findByNivelRisco", query = "SELECT s FROM SegAuditoria s WHERE s.nivelRisco = :nivelRisco"),
    @NamedQuery(name = "SegAuditoria.findByOperadorRegisto", query = "SELECT s FROM SegAuditoria s WHERE s.operadorRegisto = :operadorRegisto"),
    @NamedQuery(name = "SegAuditoria.findByTipoUtilizador", query = "SELECT s FROM SegAuditoria s WHERE s.tipoUtilizador = :tipoUtilizador"),
    @NamedQuery(name = "SegAuditoria.findByDataRegisto", query = "SELECT s FROM SegAuditoria s WHERE s.dataRegisto = :dataRegisto"),
    @NamedQuery(name = "SegAuditoria.findByIpAuditoria", query = "SELECT s FROM SegAuditoria s WHERE s.ipAuditoria = :ipAuditoria"),
    @NamedQuery(name = "SegAuditoria.findByCategoria", query = "SELECT s FROM SegAuditoria s WHERE s.categoria = :categoria"),
    @NamedQuery(name = "SegAuditoria.findByResultado", query = "SELECT s FROM SegAuditoria s WHERE s.resultado = :resultado"),
    @NamedQuery(name = "SegAuditoria.findByDetalhes", query = "SELECT s FROM SegAuditoria s WHERE s.detalhes = :detalhes")})
public class SegAuditoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_auditoria", nullable = false)
    private Integer pkIdAuditoria;
    @Size(max = 45)
    @Column(name = "nome", length = 45)
    private String nome;
    @Size(max = 45)
    @Column(name = "nivel_risco", length = 45)
    private String nivelRisco;
    @Size(max = 45)
    @Column(name = "operador_registo", length = 45)
    private String operadorRegisto;
    @Size(max = 45)
    @Column(name = "tipo_utilizador", length = 45)
    private String tipoUtilizador;
    @Column(name = "data_registo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegisto;
    @Size(max = 45)
    @Column(name = "ip_auditoria", length = 45)
    private String ipAuditoria;
    @Size(max = 45)
    @Column(name = "categoria", length = 45)
    private String categoria;
    @Size(max = 45)
    @Column(name = "resultado", length = 45)
    private String resultado;
    @Size(max = 250)
    @Column(name = "detalhes", length = 250)
    private String detalhes;
    @JoinColumn(name = "fk_id_conta", referencedColumnName = "pk_id_conta", nullable = false)
    @ManyToOne(optional = false)
    private SegConta fkIdConta;

    public SegAuditoria() {
    }

    public SegAuditoria(Integer pkIdAuditoria) {
        this.pkIdAuditoria = pkIdAuditoria;
    }

    public Integer getPkIdAuditoria() {
        return pkIdAuditoria;
    }

    public void setPkIdAuditoria(Integer pkIdAuditoria) {
        this.pkIdAuditoria = pkIdAuditoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(String nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public String getOperadorRegisto() {
        return operadorRegisto;
    }

    public void setOperadorRegisto(String operadorRegisto) {
        this.operadorRegisto = operadorRegisto;
    }

    public String getTipoUtilizador() {
        return tipoUtilizador;
    }

    public void setTipoUtilizador(String tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getIpAuditoria() {
        return ipAuditoria;
    }

    public void setIpAuditoria(String ipAuditoria) {
        this.ipAuditoria = ipAuditoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public SegConta getFkIdConta() {
        return fkIdConta;
    }

    public void setFkIdConta(SegConta fkIdConta) {
        this.fkIdConta = fkIdConta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAuditoria != null ? pkIdAuditoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAuditoria)) {
            return false;
        }
        SegAuditoria other = (SegAuditoria) object;
        if ((this.pkIdAuditoria == null && other.pkIdAuditoria != null) || (this.pkIdAuditoria != null && !this.pkIdAuditoria.equals(other.pkIdAuditoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegAuditoria[ pkIdAuditoria=" + pkIdAuditoria + " ]";
    }
    
}
