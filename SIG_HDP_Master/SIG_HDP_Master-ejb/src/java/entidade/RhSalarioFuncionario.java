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
@Table(name = "rh_salario_funcionario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhSalarioFuncionario.findAll", query = "SELECT r FROM RhSalarioFuncionario r"),
    @NamedQuery(name = "RhSalarioFuncionario.findByPkIdSalarioFuncionario", query = "SELECT r FROM RhSalarioFuncionario r WHERE r.pkIdSalarioFuncionario = :pkIdSalarioFuncionario"),
    @NamedQuery(name = "RhSalarioFuncionario.findBySalario", query = "SELECT r FROM RhSalarioFuncionario r WHERE r.salario = :salario"),
    @NamedQuery(name = "RhSalarioFuncionario.findByDataCadastro", query = "SELECT r FROM RhSalarioFuncionario r WHERE r.dataCadastro = :dataCadastro")})
public class RhSalarioFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_salario_funcionario", nullable = false)
    private Integer pkIdSalarioFuncionario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salario", nullable = false)
    private double salario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public RhSalarioFuncionario() {
    }

    public RhSalarioFuncionario(Integer pkIdSalarioFuncionario) {
        this.pkIdSalarioFuncionario = pkIdSalarioFuncionario;
    }

    public RhSalarioFuncionario(Integer pkIdSalarioFuncionario, double salario, Date dataCadastro) {
        this.pkIdSalarioFuncionario = pkIdSalarioFuncionario;
        this.salario = salario;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdSalarioFuncionario() {
        return pkIdSalarioFuncionario;
    }

    public void setPkIdSalarioFuncionario(Integer pkIdSalarioFuncionario) {
        this.pkIdSalarioFuncionario = pkIdSalarioFuncionario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
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
        hash += (pkIdSalarioFuncionario != null ? pkIdSalarioFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhSalarioFuncionario)) {
            return false;
        }
        RhSalarioFuncionario other = (RhSalarioFuncionario) object;
        if ((this.pkIdSalarioFuncionario == null && other.pkIdSalarioFuncionario != null) || (this.pkIdSalarioFuncionario != null && !this.pkIdSalarioFuncionario.equals(other.pkIdSalarioFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhSalarioFuncionario[ pkIdSalarioFuncionario=" + pkIdSalarioFuncionario + " ]";
    }
    
}
