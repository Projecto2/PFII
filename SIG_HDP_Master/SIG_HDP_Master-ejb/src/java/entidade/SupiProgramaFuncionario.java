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
@Table(name = "supi_programa_funcionario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiProgramaFuncionario.findAll", query = "SELECT s FROM SupiProgramaFuncionario s"),
    @NamedQuery(name = "SupiProgramaFuncionario.findByPkIdProgramaFuncionario", query = "SELECT s FROM SupiProgramaFuncionario s WHERE s.pkIdProgramaFuncionario = :pkIdProgramaFuncionario"),
    @NamedQuery(name = "SupiProgramaFuncionario.findByDataCadastro", query = "SELECT s FROM SupiProgramaFuncionario s WHERE s.dataCadastro = :dataCadastro")})
public class SupiProgramaFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_programa_funcionario", nullable = false)
    private Long pkIdProgramaFuncionario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_programa_posto", referencedColumnName = "pk_id_programa_posto", nullable = false)
    @ManyToOne(optional = false)
    private SupiProgramaPosto fkIdProgramaPosto;

    public SupiProgramaFuncionario() {
    }

    public SupiProgramaFuncionario(Long pkIdProgramaFuncionario) {
        this.pkIdProgramaFuncionario = pkIdProgramaFuncionario;
    }

    public SupiProgramaFuncionario(Long pkIdProgramaFuncionario, Date dataCadastro) {
        this.pkIdProgramaFuncionario = pkIdProgramaFuncionario;
        this.dataCadastro = dataCadastro;
    }

    public Long getPkIdProgramaFuncionario() {
        return pkIdProgramaFuncionario;
    }

    public void setPkIdProgramaFuncionario(Long pkIdProgramaFuncionario) {
        this.pkIdProgramaFuncionario = pkIdProgramaFuncionario;
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

    public SupiProgramaPosto getFkIdProgramaPosto() {
        return fkIdProgramaPosto;
    }

    public void setFkIdProgramaPosto(SupiProgramaPosto fkIdProgramaPosto) {
        this.fkIdProgramaPosto = fkIdProgramaPosto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProgramaFuncionario != null ? pkIdProgramaFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiProgramaFuncionario)) {
            return false;
        }
        SupiProgramaFuncionario other = (SupiProgramaFuncionario) object;
        if ((this.pkIdProgramaFuncionario == null && other.pkIdProgramaFuncionario != null) || (this.pkIdProgramaFuncionario != null && !this.pkIdProgramaFuncionario.equals(other.pkIdProgramaFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiProgramaFuncionario[ pkIdProgramaFuncionario=" + pkIdProgramaFuncionario + " ]";
    }
    
}
