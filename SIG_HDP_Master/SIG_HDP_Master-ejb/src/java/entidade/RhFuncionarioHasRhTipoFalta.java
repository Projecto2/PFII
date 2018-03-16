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
@Table(name = "rh_funcionario_has_rh_tipo_falta", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhFuncionarioHasRhTipoFalta.findAll", query = "SELECT r FROM RhFuncionarioHasRhTipoFalta r"),
    @NamedQuery(name = "RhFuncionarioHasRhTipoFalta.findByPkIdFuncionarioHasTipoFalta", query = "SELECT r FROM RhFuncionarioHasRhTipoFalta r WHERE r.pkIdFuncionarioHasTipoFalta = :pkIdFuncionarioHasTipoFalta"),
    @NamedQuery(name = "RhFuncionarioHasRhTipoFalta.findByData", query = "SELECT r FROM RhFuncionarioHasRhTipoFalta r WHERE r.data = :data"),
    @NamedQuery(name = "RhFuncionarioHasRhTipoFalta.findByObservacao", query = "SELECT r FROM RhFuncionarioHasRhTipoFalta r WHERE r.observacao = :observacao")})
public class RhFuncionarioHasRhTipoFalta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_funcionario_has_tipo_falta", nullable = false)
    private Long pkIdFuncionarioHasTipoFalta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 2147483647)
    @Column(name = "observacao", length = 2147483647)
    private String observacao;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_tipo_falta", referencedColumnName = "pk_id_tipo_falta")
    @ManyToOne
    private RhTipoFalta fkIdTipoFalta;

    public RhFuncionarioHasRhTipoFalta() {
    }

    public RhFuncionarioHasRhTipoFalta(Long pkIdFuncionarioHasTipoFalta) {
        this.pkIdFuncionarioHasTipoFalta = pkIdFuncionarioHasTipoFalta;
    }

    public RhFuncionarioHasRhTipoFalta(Long pkIdFuncionarioHasTipoFalta, Date data) {
        this.pkIdFuncionarioHasTipoFalta = pkIdFuncionarioHasTipoFalta;
        this.data = data;
    }

    public Long getPkIdFuncionarioHasTipoFalta() {
        return pkIdFuncionarioHasTipoFalta;
    }

    public void setPkIdFuncionarioHasTipoFalta(Long pkIdFuncionarioHasTipoFalta) {
        this.pkIdFuncionarioHasTipoFalta = pkIdFuncionarioHasTipoFalta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public RhTipoFalta getFkIdTipoFalta() {
        return fkIdTipoFalta;
    }

    public void setFkIdTipoFalta(RhTipoFalta fkIdTipoFalta) {
        this.fkIdTipoFalta = fkIdTipoFalta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFuncionarioHasTipoFalta != null ? pkIdFuncionarioHasTipoFalta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhFuncionarioHasRhTipoFalta)) {
            return false;
        }
        RhFuncionarioHasRhTipoFalta other = (RhFuncionarioHasRhTipoFalta) object;
        if ((this.pkIdFuncionarioHasTipoFalta == null && other.pkIdFuncionarioHasTipoFalta != null) || (this.pkIdFuncionarioHasTipoFalta != null && !this.pkIdFuncionarioHasTipoFalta.equals(other.pkIdFuncionarioHasTipoFalta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhFuncionarioHasRhTipoFalta[ pkIdFuncionarioHasTipoFalta=" + pkIdFuncionarioHasTipoFalta + " ]";
    }
    
}
