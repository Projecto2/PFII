/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_tipo_de_horario_trabalho", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhTipoDeHorarioTrabalho.findAll", query = "SELECT r FROM RhTipoDeHorarioTrabalho r"),
    @NamedQuery(name = "RhTipoDeHorarioTrabalho.findByPkIdTipoDeHorarioTrabalho", query = "SELECT r FROM RhTipoDeHorarioTrabalho r WHERE r.pkIdTipoDeHorarioTrabalho = :pkIdTipoDeHorarioTrabalho"),
    @NamedQuery(name = "RhTipoDeHorarioTrabalho.findByDescricao", query = "SELECT r FROM RhTipoDeHorarioTrabalho r WHERE r.descricao = :descricao")})
public class RhTipoDeHorarioTrabalho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id__tipo_de_horario_trabalho", nullable = false)
    private Integer pkIdTipoDeHorarioTrabalho;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descricao", nullable = false, length = 120)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTipoDeHorarioTrabalho")
    private List<RhFuncionario> rhFuncionarioList;

    public RhTipoDeHorarioTrabalho() {
    }

    public RhTipoDeHorarioTrabalho(Integer pkIdTipoDeHorarioTrabalho) {
        this.pkIdTipoDeHorarioTrabalho = pkIdTipoDeHorarioTrabalho;
    }

    public RhTipoDeHorarioTrabalho(Integer pkIdTipoDeHorarioTrabalho, String descricao) {
        this.pkIdTipoDeHorarioTrabalho = pkIdTipoDeHorarioTrabalho;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoDeHorarioTrabalho() {
        return pkIdTipoDeHorarioTrabalho;
    }

    public void setPkIdTipoDeHorarioTrabalho(Integer pkIdTipoDeHorarioTrabalho) {
        this.pkIdTipoDeHorarioTrabalho = pkIdTipoDeHorarioTrabalho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoDeHorarioTrabalho != null ? pkIdTipoDeHorarioTrabalho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhTipoDeHorarioTrabalho)) {
            return false;
        }
        RhTipoDeHorarioTrabalho other = (RhTipoDeHorarioTrabalho) object;
        if ((this.pkIdTipoDeHorarioTrabalho == null && other.pkIdTipoDeHorarioTrabalho != null) || (this.pkIdTipoDeHorarioTrabalho != null && !this.pkIdTipoDeHorarioTrabalho.equals(other.pkIdTipoDeHorarioTrabalho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhTipoDeHorarioTrabalho[ pkIdTipoDeHorarioTrabalho=" + pkIdTipoDeHorarioTrabalho + " ]";
    }
    
}
