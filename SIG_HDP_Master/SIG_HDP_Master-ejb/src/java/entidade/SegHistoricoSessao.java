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
@Table(name = "seg_historico_sessao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegHistoricoSessao.findAll", query = "SELECT s FROM SegHistoricoSessao s"),
    @NamedQuery(name = "SegHistoricoSessao.findByPkIdHistoricoSessao", query = "SELECT s FROM SegHistoricoSessao s WHERE s.pkIdHistoricoSessao = :pkIdHistoricoSessao"),
    @NamedQuery(name = "SegHistoricoSessao.findByDataHoraLogout", query = "SELECT s FROM SegHistoricoSessao s WHERE s.dataHoraLogout = :dataHoraLogout"),
    @NamedQuery(name = "SegHistoricoSessao.findByIpMaquina", query = "SELECT s FROM SegHistoricoSessao s WHERE s.ipMaquina = :ipMaquina"),
    @NamedQuery(name = "SegHistoricoSessao.findByCodigoSessao", query = "SELECT s FROM SegHistoricoSessao s WHERE s.codigoSessao = :codigoSessao"),
    @NamedQuery(name = "SegHistoricoSessao.findByDataHoraLogin", query = "SELECT s FROM SegHistoricoSessao s WHERE s.dataHoraLogin = :dataHoraLogin")})
public class SegHistoricoSessao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_historico_sessao", nullable = false)
    private Integer pkIdHistoricoSessao;
    @Column(name = "data_hora_logout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraLogout;
    @Size(max = 255)
    @Column(name = "ip_maquina", length = 255)
    private String ipMaquina;
    @Size(max = 250)
    @Column(name = "codigo_sessao", length = 250)
    private String codigoSessao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora_login", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraLogin;
    @JoinColumn(name = "fk_id_conta", referencedColumnName = "pk_id_conta")
    @ManyToOne
    private SegConta fkIdConta;

    public SegHistoricoSessao() {
    }

    public SegHistoricoSessao(Integer pkIdHistoricoSessao) {
        this.pkIdHistoricoSessao = pkIdHistoricoSessao;
    }

    public SegHistoricoSessao(Integer pkIdHistoricoSessao, Date dataHoraLogin) {
        this.pkIdHistoricoSessao = pkIdHistoricoSessao;
        this.dataHoraLogin = dataHoraLogin;
    }

    public Integer getPkIdHistoricoSessao() {
        return pkIdHistoricoSessao;
    }

    public void setPkIdHistoricoSessao(Integer pkIdHistoricoSessao) {
        this.pkIdHistoricoSessao = pkIdHistoricoSessao;
    }

    public Date getDataHoraLogout() {
        return dataHoraLogout;
    }

    public void setDataHoraLogout(Date dataHoraLogout) {
        this.dataHoraLogout = dataHoraLogout;
    }

    public String getIpMaquina() {
        return ipMaquina;
    }

    public void setIpMaquina(String ipMaquina) {
        this.ipMaquina = ipMaquina;
    }

    public String getCodigoSessao() {
        return codigoSessao;
    }

    public void setCodigoSessao(String codigoSessao) {
        this.codigoSessao = codigoSessao;
    }

    public Date getDataHoraLogin() {
        return dataHoraLogin;
    }

    public void setDataHoraLogin(Date dataHoraLogin) {
        this.dataHoraLogin = dataHoraLogin;
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
        hash += (pkIdHistoricoSessao != null ? pkIdHistoricoSessao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegHistoricoSessao)) {
            return false;
        }
        SegHistoricoSessao other = (SegHistoricoSessao) object;
        if ((this.pkIdHistoricoSessao == null && other.pkIdHistoricoSessao != null) || (this.pkIdHistoricoSessao != null && !this.pkIdHistoricoSessao.equals(other.pkIdHistoricoSessao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegHistoricoSessao[ pkIdHistoricoSessao=" + pkIdHistoricoSessao + " ]";
    }
    
}
