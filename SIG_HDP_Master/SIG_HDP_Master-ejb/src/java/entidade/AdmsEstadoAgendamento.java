/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "adms_estado_agendamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsEstadoAgendamento.findAll", query = "SELECT a FROM AdmsEstadoAgendamento a"),
    @NamedQuery(name = "AdmsEstadoAgendamento.findByPkIdEstadoAgendamento", query = "SELECT a FROM AdmsEstadoAgendamento a WHERE a.pkIdEstadoAgendamento = :pkIdEstadoAgendamento"),
    @NamedQuery(name = "AdmsEstadoAgendamento.findByDescricaoEstadoAgendamento", query = "SELECT a FROM AdmsEstadoAgendamento a WHERE a.descricaoEstadoAgendamento = :descricaoEstadoAgendamento")})
public class AdmsEstadoAgendamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_agendamento", nullable = false)
    private Integer pkIdEstadoAgendamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao_estado_agendamento", nullable = false, length = 100)
    private String descricaoEstadoAgendamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoAgendamento")
    private List<AdmsAgendamento> admsAgendamentoList;

    public AdmsEstadoAgendamento() {
    }

    public AdmsEstadoAgendamento(Integer pkIdEstadoAgendamento) {
        this.pkIdEstadoAgendamento = pkIdEstadoAgendamento;
    }

    public AdmsEstadoAgendamento(Integer pkIdEstadoAgendamento, String descricaoEstadoAgendamento) {
        this.pkIdEstadoAgendamento = pkIdEstadoAgendamento;
        this.descricaoEstadoAgendamento = descricaoEstadoAgendamento;
    }

    public Integer getPkIdEstadoAgendamento() {
        return pkIdEstadoAgendamento;
    }

    public void setPkIdEstadoAgendamento(Integer pkIdEstadoAgendamento) {
        this.pkIdEstadoAgendamento = pkIdEstadoAgendamento;
    }

    public String getDescricaoEstadoAgendamento() {
        return descricaoEstadoAgendamento;
    }

    public void setDescricaoEstadoAgendamento(String descricaoEstadoAgendamento) {
        this.descricaoEstadoAgendamento = descricaoEstadoAgendamento;
    }

    @XmlTransient
    public List<AdmsAgendamento> getAdmsAgendamentoList() {
        return admsAgendamentoList;
    }

    public void setAdmsAgendamentoList(List<AdmsAgendamento> admsAgendamentoList) {
        this.admsAgendamentoList = admsAgendamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoAgendamento != null ? pkIdEstadoAgendamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsEstadoAgendamento)) {
            return false;
        }
        AdmsEstadoAgendamento other = (AdmsEstadoAgendamento) object;
        if ((this.pkIdEstadoAgendamento == null && other.pkIdEstadoAgendamento != null) || (this.pkIdEstadoAgendamento != null && !this.pkIdEstadoAgendamento.equals(other.pkIdEstadoAgendamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsEstadoAgendamento[ pkIdEstadoAgendamento=" + pkIdEstadoAgendamento + " ]";
    }
    
}
