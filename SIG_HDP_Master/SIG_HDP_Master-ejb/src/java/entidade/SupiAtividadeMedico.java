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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_atividade_medico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiAtividadeMedico.findAll", query = "SELECT s FROM SupiAtividadeMedico s"),
    @NamedQuery(name = "SupiAtividadeMedico.findByPkIdAtividadeMedico", query = "SELECT s FROM SupiAtividadeMedico s WHERE s.pkIdAtividadeMedico = :pkIdAtividadeMedico"),
    @NamedQuery(name = "SupiAtividadeMedico.findByDescricao", query = "SELECT s FROM SupiAtividadeMedico s WHERE s.descricao = :descricao")})
public class SupiAtividadeMedico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_atividade_medico", nullable = false)
    private Integer pkIdAtividadeMedico;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdAtividadeMedico")
    private List<SupiMedicoHasEscala> supiMedicoHasEscalaList;

    public SupiAtividadeMedico() {
    }

    public SupiAtividadeMedico(Integer pkIdAtividadeMedico) {
        this.pkIdAtividadeMedico = pkIdAtividadeMedico;
    }

    public Integer getPkIdAtividadeMedico() {
        return pkIdAtividadeMedico;
    }

    public void setPkIdAtividadeMedico(Integer pkIdAtividadeMedico) {
        this.pkIdAtividadeMedico = pkIdAtividadeMedico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiMedicoHasEscala> getSupiMedicoHasEscalaList() {
        return supiMedicoHasEscalaList;
    }

    public void setSupiMedicoHasEscalaList(List<SupiMedicoHasEscala> supiMedicoHasEscalaList) {
        this.supiMedicoHasEscalaList = supiMedicoHasEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAtividadeMedico != null ? pkIdAtividadeMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiAtividadeMedico)) {
            return false;
        }
        SupiAtividadeMedico other = (SupiAtividadeMedico) object;
        if ((this.pkIdAtividadeMedico == null && other.pkIdAtividadeMedico != null) || (this.pkIdAtividadeMedico != null && !this.pkIdAtividadeMedico.equals(other.pkIdAtividadeMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiAtividadeMedico[ pkIdAtividadeMedico=" + pkIdAtividadeMedico + " ]";
    }
    
}
