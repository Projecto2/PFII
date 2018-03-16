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
@Table(name = "seg_log_acesso", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegLogAcesso.findAll", query = "SELECT s FROM SegLogAcesso s"),
    @NamedQuery(name = "SegLogAcesso.findByPkIdLogAcesso", query = "SELECT s FROM SegLogAcesso s WHERE s.pkIdLogAcesso = :pkIdLogAcesso"),
    @NamedQuery(name = "SegLogAcesso.findByEventoLogAcesso", query = "SELECT s FROM SegLogAcesso s WHERE s.eventoLogAcesso = :eventoLogAcesso"),
    @NamedQuery(name = "SegLogAcesso.findByRiscoLogAcesso", query = "SELECT s FROM SegLogAcesso s WHERE s.riscoLogAcesso = :riscoLogAcesso"),
    @NamedQuery(name = "SegLogAcesso.findByOperador", query = "SELECT s FROM SegLogAcesso s WHERE s.operador = :operador"),
    @NamedQuery(name = "SegLogAcesso.findByTipoUsuario", query = "SELECT s FROM SegLogAcesso s WHERE s.tipoUsuario = :tipoUsuario"),
    @NamedQuery(name = "SegLogAcesso.findByDataRegistoLogAcesso", query = "SELECT s FROM SegLogAcesso s WHERE s.dataRegistoLogAcesso = :dataRegistoLogAcesso"),
    @NamedQuery(name = "SegLogAcesso.findByIpLogAcess0", query = "SELECT s FROM SegLogAcesso s WHERE s.ipLogAcess0 = :ipLogAcess0"),
    @NamedQuery(name = "SegLogAcesso.findByResultadoLogAcesso", query = "SELECT s FROM SegLogAcesso s WHERE s.resultadoLogAcesso = :resultadoLogAcesso"),
    @NamedQuery(name = "SegLogAcesso.findByDetalhesLogAcesso", query = "SELECT s FROM SegLogAcesso s WHERE s.detalhesLogAcesso = :detalhesLogAcesso")})
public class SegLogAcesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_log_acesso", nullable = false)
    private Integer pkIdLogAcesso;
    @Size(max = 45)
    @Column(name = "evento_log_acesso", length = 45)
    private String eventoLogAcesso;
    @Size(max = 45)
    @Column(name = "risco_log_acesso", length = 45)
    private String riscoLogAcesso;
    @Size(max = 45)
    @Column(name = "operador", length = 45)
    private String operador;
    @Size(max = 45)
    @Column(name = "tipo_usuario", length = 45)
    private String tipoUsuario;
    @Column(name = "data_registo_log_acesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistoLogAcesso;
    @Size(max = 45)
    @Column(name = "ip_log_acess0", length = 45)
    private String ipLogAcess0;
    @Size(max = 45)
    @Column(name = "resultado_log_acesso", length = 45)
    private String resultadoLogAcesso;
    @Size(max = 300)
    @Column(name = "detalhes_log_acesso", length = 300)
    private String detalhesLogAcesso;
    @JoinColumn(name = "fk_id_conta", referencedColumnName = "pk_id_conta")
    @ManyToOne
    private SegConta fkIdConta;

    public SegLogAcesso() {
    }

    public SegLogAcesso(Integer pkIdLogAcesso) {
        this.pkIdLogAcesso = pkIdLogAcesso;
    }

    public Integer getPkIdLogAcesso() {
        return pkIdLogAcesso;
    }

    public void setPkIdLogAcesso(Integer pkIdLogAcesso) {
        this.pkIdLogAcesso = pkIdLogAcesso;
    }

    public String getEventoLogAcesso() {
        return eventoLogAcesso;
    }

    public void setEventoLogAcesso(String eventoLogAcesso) {
        this.eventoLogAcesso = eventoLogAcesso;
    }

    public String getRiscoLogAcesso() {
        return riscoLogAcesso;
    }

    public void setRiscoLogAcesso(String riscoLogAcesso) {
        this.riscoLogAcesso = riscoLogAcesso;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Date getDataRegistoLogAcesso() {
        return dataRegistoLogAcesso;
    }

    public void setDataRegistoLogAcesso(Date dataRegistoLogAcesso) {
        this.dataRegistoLogAcesso = dataRegistoLogAcesso;
    }

    public String getIpLogAcess0() {
        return ipLogAcess0;
    }

    public void setIpLogAcess0(String ipLogAcess0) {
        this.ipLogAcess0 = ipLogAcess0;
    }

    public String getResultadoLogAcesso() {
        return resultadoLogAcesso;
    }

    public void setResultadoLogAcesso(String resultadoLogAcesso) {
        this.resultadoLogAcesso = resultadoLogAcesso;
    }

    public String getDetalhesLogAcesso() {
        return detalhesLogAcesso;
    }

    public void setDetalhesLogAcesso(String detalhesLogAcesso) {
        this.detalhesLogAcesso = detalhesLogAcesso;
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
        hash += (pkIdLogAcesso != null ? pkIdLogAcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegLogAcesso)) {
            return false;
        }
        SegLogAcesso other = (SegLogAcesso) object;
        if ((this.pkIdLogAcesso == null && other.pkIdLogAcesso != null) || (this.pkIdLogAcesso != null && !this.pkIdLogAcesso.equals(other.pkIdLogAcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegLogAcesso[ pkIdLogAcesso=" + pkIdLogAcesso + " ]";
    }
    
}
