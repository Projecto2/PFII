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
@Table(name = "emg_exame", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmgExame.findAll", query = "SELECT e FROM EmgExame e"),
    @NamedQuery(name = "EmgExame.findByPkIdExame", query = "SELECT e FROM EmgExame e WHERE e.pkIdExame = :pkIdExame"),
    @NamedQuery(name = "EmgExame.findByDataPedidoExame", query = "SELECT e FROM EmgExame e WHERE e.dataPedidoExame = :dataPedidoExame"),
    @NamedQuery(name = "EmgExame.findByObservacao", query = "SELECT e FROM EmgExame e WHERE e.observacao = :observacao"),
    @NamedQuery(name = "EmgExame.findByResponsavel", query = "SELECT e FROM EmgExame e WHERE e.responsavel = :responsavel")})
public class EmgExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_exame", nullable = false)
    private Integer pkIdExame;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_pedido_exame", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataPedidoExame;
    @Size(max = 2147483647)
    @Column(name = "observacao", length = 2147483647)
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "responsavel", nullable = false, length = 2147483647)
    private String responsavel;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente", nullable = false)
    @ManyToOne(optional = false)
    private AdmsPaciente fkIdPaciente;

    public EmgExame() {
    }

    public EmgExame(Integer pkIdExame) {
        this.pkIdExame = pkIdExame;
    }

    public EmgExame(Integer pkIdExame, Date dataPedidoExame, String responsavel) {
        this.pkIdExame = pkIdExame;
        this.dataPedidoExame = dataPedidoExame;
        this.responsavel = responsavel;
    }

    public Integer getPkIdExame() {
        return pkIdExame;
    }

    public void setPkIdExame(Integer pkIdExame) {
        this.pkIdExame = pkIdExame;
    }

    public Date getDataPedidoExame() {
        return dataPedidoExame;
    }

    public void setDataPedidoExame(Date dataPedidoExame) {
        this.dataPedidoExame = dataPedidoExame;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdExame != null ? pkIdExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmgExame)) {
            return false;
        }
        EmgExame other = (EmgExame) object;
        if ((this.pkIdExame == null && other.pkIdExame != null) || (this.pkIdExame != null && !this.pkIdExame.equals(other.pkIdExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EmgExame[ pkIdExame=" + pkIdExame + " ]";
    }
    
}
