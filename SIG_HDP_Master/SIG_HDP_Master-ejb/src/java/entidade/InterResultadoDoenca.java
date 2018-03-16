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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_resultado_doenca", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterResultadoDoenca.findAll", query = "SELECT i FROM InterResultadoDoenca i"),
    @NamedQuery(name = "InterResultadoDoenca.findByDescricao", query = "SELECT i FROM InterResultadoDoenca i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterResultadoDoenca.findByPkIdResultadoDoenca", query = "SELECT i FROM InterResultadoDoenca i WHERE i.pkIdResultadoDoenca = :pkIdResultadoDoenca")})
public class InterResultadoDoenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_resultado_doenca", nullable = false)
    private Integer pkIdResultadoDoenca;
    @OneToMany(mappedBy = "fkIdResultadoDoenca")
    private List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList;

    public InterResultadoDoenca() {
    }

    public InterResultadoDoenca(Integer pkIdResultadoDoenca) {
        this.pkIdResultadoDoenca = pkIdResultadoDoenca;
    }

    public InterResultadoDoenca(Integer pkIdResultadoDoenca, String descricao) {
        this.pkIdResultadoDoenca = pkIdResultadoDoenca;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdResultadoDoenca() {
        return pkIdResultadoDoenca;
    }

    public void setPkIdResultadoDoenca(Integer pkIdResultadoDoenca) {
        this.pkIdResultadoDoenca = pkIdResultadoDoenca;
    }

    @XmlTransient
    public List<InterDoencaInternamentoPaciente> getInterDoencaInternamentoPacienteList() {
        return interDoencaInternamentoPacienteList;
    }

    public void setInterDoencaInternamentoPacienteList(List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList) {
        this.interDoencaInternamentoPacienteList = interDoencaInternamentoPacienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdResultadoDoenca != null ? pkIdResultadoDoenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterResultadoDoenca)) {
            return false;
        }
        InterResultadoDoenca other = (InterResultadoDoenca) object;
        if ((this.pkIdResultadoDoenca == null && other.pkIdResultadoDoenca != null) || (this.pkIdResultadoDoenca != null && !this.pkIdResultadoDoenca.equals(other.pkIdResultadoDoenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterResultadoDoenca[ pkIdResultadoDoenca=" + pkIdResultadoDoenca + " ]";
    }
    
}
