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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_agendamento_medico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsAgendamentoMedico.findAll", query = "SELECT a FROM AdmsAgendamentoMedico a"),
    @NamedQuery(name = "AdmsAgendamentoMedico.findByPkIdAgendamentoMedico", query = "SELECT a FROM AdmsAgendamentoMedico a WHERE a.pkIdAgendamentoMedico = :pkIdAgendamentoMedico")})
public class AdmsAgendamentoMedico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_agendamento_medico", nullable = false)
    private Long pkIdAgendamentoMedico;
    @JoinColumn(name = "fk_id_agendamento", referencedColumnName = "pk_id_agendamento", nullable = false)
    @ManyToOne(optional = false)
    private AdmsAgendamento fkIdAgendamento;
    @JoinColumn(name = "fk_id_medico", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdMedico;

    public AdmsAgendamentoMedico() {
    }

    public AdmsAgendamentoMedico(Long pkIdAgendamentoMedico) {
        this.pkIdAgendamentoMedico = pkIdAgendamentoMedico;
    }

    public Long getPkIdAgendamentoMedico() {
        return pkIdAgendamentoMedico;
    }

    public void setPkIdAgendamentoMedico(Long pkIdAgendamentoMedico) {
        this.pkIdAgendamentoMedico = pkIdAgendamentoMedico;
    }

    public AdmsAgendamento getFkIdAgendamento() {
        return fkIdAgendamento;
    }

    public void setFkIdAgendamento(AdmsAgendamento fkIdAgendamento) {
        this.fkIdAgendamento = fkIdAgendamento;
    }

    public RhFuncionario getFkIdMedico() {
        return fkIdMedico;
    }

    public void setFkIdMedico(RhFuncionario fkIdMedico) {
        this.fkIdMedico = fkIdMedico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAgendamentoMedico != null ? pkIdAgendamentoMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsAgendamentoMedico)) {
            return false;
        }
        AdmsAgendamentoMedico other = (AdmsAgendamentoMedico) object;
        if ((this.pkIdAgendamentoMedico == null && other.pkIdAgendamentoMedico != null) || (this.pkIdAgendamentoMedico != null && !this.pkIdAgendamentoMedico.equals(other.pkIdAgendamentoMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsAgendamentoMedico[ pkIdAgendamentoMedico=" + pkIdAgendamentoMedico + " ]";
    }
    
}
