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
@Table(name = "supi_formacao_funcionario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiFormacaoFuncionario.findAll", query = "SELECT s FROM SupiFormacaoFuncionario s"),
    @NamedQuery(name = "SupiFormacaoFuncionario.findByPkIdFormacaoFuncionarios", query = "SELECT s FROM SupiFormacaoFuncionario s WHERE s.pkIdFormacaoFuncionarios = :pkIdFormacaoFuncionarios"),
    @NamedQuery(name = "SupiFormacaoFuncionario.findByDataCadastro", query = "SELECT s FROM SupiFormacaoFuncionario s WHERE s.dataCadastro = :dataCadastro")})
public class SupiFormacaoFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_formacao_funcionarios", nullable = false)
    private Integer pkIdFormacaoFuncionarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_estado", referencedColumnName = "pk_id_estado", nullable = false)
    @ManyToOne(optional = false)
    private SupiEstado fkIdEstado;
    @JoinColumn(name = "fk_id_formacao", referencedColumnName = "pk_id_formacao", nullable = false)
    @ManyToOne(optional = false)
    private SupiFormacao fkIdFormacao;

    public SupiFormacaoFuncionario() {
    }

    public SupiFormacaoFuncionario(Integer pkIdFormacaoFuncionarios) {
        this.pkIdFormacaoFuncionarios = pkIdFormacaoFuncionarios;
    }

    public SupiFormacaoFuncionario(Integer pkIdFormacaoFuncionarios, Date dataCadastro) {
        this.pkIdFormacaoFuncionarios = pkIdFormacaoFuncionarios;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdFormacaoFuncionarios() {
        return pkIdFormacaoFuncionarios;
    }

    public void setPkIdFormacaoFuncionarios(Integer pkIdFormacaoFuncionarios) {
        this.pkIdFormacaoFuncionarios = pkIdFormacaoFuncionarios;
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

    public SupiEstado getFkIdEstado() {
        return fkIdEstado;
    }

    public void setFkIdEstado(SupiEstado fkIdEstado) {
        this.fkIdEstado = fkIdEstado;
    }

    public SupiFormacao getFkIdFormacao() {
        return fkIdFormacao;
    }

    public void setFkIdFormacao(SupiFormacao fkIdFormacao) {
        this.fkIdFormacao = fkIdFormacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFormacaoFuncionarios != null ? pkIdFormacaoFuncionarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiFormacaoFuncionario)) {
            return false;
        }
        SupiFormacaoFuncionario other = (SupiFormacaoFuncionario) object;
        if ((this.pkIdFormacaoFuncionarios == null && other.pkIdFormacaoFuncionarios != null) || (this.pkIdFormacaoFuncionarios != null && !this.pkIdFormacaoFuncionarios.equals(other.pkIdFormacaoFuncionarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiFormacaoFuncionario[ pkIdFormacaoFuncionarios=" + pkIdFormacaoFuncionarios + " ]";
    }
    
}
