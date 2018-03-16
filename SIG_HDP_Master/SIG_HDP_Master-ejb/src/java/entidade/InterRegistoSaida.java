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
@Table(name = "inter_registo_saida", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterRegistoSaida.findAll", query = "SELECT i FROM InterRegistoSaida i"),
    @NamedQuery(name = "InterRegistoSaida.findByPkIdRegistoSaida", query = "SELECT i FROM InterRegistoSaida i WHERE i.pkIdRegistoSaida = :pkIdRegistoSaida"),
    @NamedQuery(name = "InterRegistoSaida.findByDiagnosticoDefinitivo", query = "SELECT i FROM InterRegistoSaida i WHERE i.diagnosticoDefinitivo = :diagnosticoDefinitivo"),
    @NamedQuery(name = "InterRegistoSaida.findByDataRegistoSaida", query = "SELECT i FROM InterRegistoSaida i WHERE i.dataRegistoSaida = :dataRegistoSaida"),
    @NamedQuery(name = "InterRegistoSaida.findByData", query = "SELECT i FROM InterRegistoSaida i WHERE i.data = :data")})
public class InterRegistoSaida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_registo_saida", nullable = false)
    private Integer pkIdRegistoSaida;
    @Size(max = 200)
    @Column(name = "diagnostico_definitivo", length = 200)
    private String diagnosticoDefinitivo;
    @Column(name = "data_registo_saida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistoSaida;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_tipo_alta", referencedColumnName = "pk_id_tipo_alta", nullable = false)
    @ManyToOne(optional = false)
    private InterTipoAlta fkIdTipoAlta;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterRegistoSaida() {
    }

    public InterRegistoSaida(Integer pkIdRegistoSaida) {
        this.pkIdRegistoSaida = pkIdRegistoSaida;
    }

    public Integer getPkIdRegistoSaida() {
        return pkIdRegistoSaida;
    }

    public void setPkIdRegistoSaida(Integer pkIdRegistoSaida) {
        this.pkIdRegistoSaida = pkIdRegistoSaida;
    }

    public String getDiagnosticoDefinitivo() {
        return diagnosticoDefinitivo;
    }

    public void setDiagnosticoDefinitivo(String diagnosticoDefinitivo) {
        this.diagnosticoDefinitivo = diagnosticoDefinitivo;
    }

    public Date getDataRegistoSaida() {
        return dataRegistoSaida;
    }

    public void setDataRegistoSaida(Date dataRegistoSaida) {
        this.dataRegistoSaida = dataRegistoSaida;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public InterTipoAlta getFkIdTipoAlta() {
        return fkIdTipoAlta;
    }

    public void setFkIdTipoAlta(InterTipoAlta fkIdTipoAlta) {
        this.fkIdTipoAlta = fkIdTipoAlta;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRegistoSaida != null ? pkIdRegistoSaida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterRegistoSaida)) {
            return false;
        }
        InterRegistoSaida other = (InterRegistoSaida) object;
        if ((this.pkIdRegistoSaida == null && other.pkIdRegistoSaida != null) || (this.pkIdRegistoSaida != null && !this.pkIdRegistoSaida.equals(other.pkIdRegistoSaida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterRegistoSaida[ pkIdRegistoSaida=" + pkIdRegistoSaida + " ]";
    }
    
}
