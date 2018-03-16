/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_funcionario_has_rh_subsidio", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_funcionario", "fk_id_subsidio"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhFuncionarioHasRhSubsidio.findAll", query = "SELECT r FROM RhFuncionarioHasRhSubsidio r"),
    @NamedQuery(name = "RhFuncionarioHasRhSubsidio.findByPkIdFuncionarioHasSubsidio", query = "SELECT r FROM RhFuncionarioHasRhSubsidio r WHERE r.pkIdFuncionarioHasSubsidio = :pkIdFuncionarioHasSubsidio")})
public class RhFuncionarioHasRhSubsidio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_funcionario_has_subsidio", nullable = false)
    private Integer pkIdFuncionarioHasSubsidio;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_subsidio", referencedColumnName = "pk_id_subsidio", nullable = false)
    @ManyToOne(optional = false)
    private RhSubsidio fkIdSubsidio;

    public RhFuncionarioHasRhSubsidio() {
    }

    public RhFuncionarioHasRhSubsidio(Integer pkIdFuncionarioHasSubsidio) {
        this.pkIdFuncionarioHasSubsidio = pkIdFuncionarioHasSubsidio;
    }

    public Integer getPkIdFuncionarioHasSubsidio() {
        return pkIdFuncionarioHasSubsidio;
    }

    public void setPkIdFuncionarioHasSubsidio(Integer pkIdFuncionarioHasSubsidio) {
        this.pkIdFuncionarioHasSubsidio = pkIdFuncionarioHasSubsidio;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public RhSubsidio getFkIdSubsidio() {
        return fkIdSubsidio;
    }

    public void setFkIdSubsidio(RhSubsidio fkIdSubsidio) {
        this.fkIdSubsidio = fkIdSubsidio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFuncionarioHasSubsidio != null ? pkIdFuncionarioHasSubsidio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhFuncionarioHasRhSubsidio)) {
            return false;
        }
        RhFuncionarioHasRhSubsidio other = (RhFuncionarioHasRhSubsidio) object;
        if ((this.pkIdFuncionarioHasSubsidio == null && other.pkIdFuncionarioHasSubsidio != null) || (this.pkIdFuncionarioHasSubsidio != null && !this.pkIdFuncionarioHasSubsidio.equals(other.pkIdFuncionarioHasSubsidio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhFuncionarioHasRhSubsidio[ pkIdFuncionarioHasSubsidio=" + pkIdFuncionarioHasSubsidio + " ]";
    }
    
}
