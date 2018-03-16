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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_presenca_estagiario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiPresencaEstagiario.findAll", query = "SELECT s FROM SupiPresencaEstagiario s"),
    @NamedQuery(name = "SupiPresencaEstagiario.findByTotalPresenca", query = "SELECT s FROM SupiPresencaEstagiario s WHERE s.totalPresenca = :totalPresenca"),
    @NamedQuery(name = "SupiPresencaEstagiario.findByData", query = "SELECT s FROM SupiPresencaEstagiario s WHERE s.data = :data"),
    @NamedQuery(name = "SupiPresencaEstagiario.findByPkIdPresencaEstagiario", query = "SELECT s FROM SupiPresencaEstagiario s WHERE s.pkIdPresencaEstagiario = :pkIdPresencaEstagiario")})
public class SupiPresencaEstagiario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "total_presenca")
    private Integer totalPresenca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_presenca_estagiario", nullable = false)
    private Integer pkIdPresencaEstagiario;
    @JoinColumn(name = "fk_id_estado_presenca", referencedColumnName = "pk_id_estado_presenca")
    @ManyToOne
    private SupiEstadoPresenca fkIdEstadoPresenca;

    public SupiPresencaEstagiario() {
    }

    public SupiPresencaEstagiario(Integer pkIdPresencaEstagiario) {
        this.pkIdPresencaEstagiario = pkIdPresencaEstagiario;
    }

    public SupiPresencaEstagiario(Integer pkIdPresencaEstagiario, Date data) {
        this.pkIdPresencaEstagiario = pkIdPresencaEstagiario;
        this.data = data;
    }

    public Integer getTotalPresenca() {
        return totalPresenca;
    }

    public void setTotalPresenca(Integer totalPresenca) {
        this.totalPresenca = totalPresenca;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getPkIdPresencaEstagiario() {
        return pkIdPresencaEstagiario;
    }

    public void setPkIdPresencaEstagiario(Integer pkIdPresencaEstagiario) {
        this.pkIdPresencaEstagiario = pkIdPresencaEstagiario;
    }

    public SupiEstadoPresenca getFkIdEstadoPresenca() {
        return fkIdEstadoPresenca;
    }

    public void setFkIdEstadoPresenca(SupiEstadoPresenca fkIdEstadoPresenca) {
        this.fkIdEstadoPresenca = fkIdEstadoPresenca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPresencaEstagiario != null ? pkIdPresencaEstagiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiPresencaEstagiario)) {
            return false;
        }
        SupiPresencaEstagiario other = (SupiPresencaEstagiario) object;
        if ((this.pkIdPresencaEstagiario == null && other.pkIdPresencaEstagiario != null) || (this.pkIdPresencaEstagiario != null && !this.pkIdPresencaEstagiario.equals(other.pkIdPresencaEstagiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiPresencaEstagiario[ pkIdPresencaEstagiario=" + pkIdPresencaEstagiario + " ]";
    }
    
}
