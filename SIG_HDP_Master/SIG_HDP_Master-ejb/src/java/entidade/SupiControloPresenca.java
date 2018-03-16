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
@Table(name = "supi_controlo_presenca", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiControloPresenca.findAll", query = "SELECT s FROM SupiControloPresenca s"),
    @NamedQuery(name = "SupiControloPresenca.findByPkIdControloPresenca", query = "SELECT s FROM SupiControloPresenca s WHERE s.pkIdControloPresenca = :pkIdControloPresenca")})
public class SupiControloPresenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_controlo_presenca", nullable = false)
    private Integer pkIdControloPresenca;
    @JoinColumn(name = "fk_id_estagiario", referencedColumnName = "pk_id_estagiario")
    @ManyToOne
    private RhEstagiario fkIdEstagiario;
    @JoinColumn(name = "fk_id_agenda_aula", referencedColumnName = "pk_id_agenda_aula")
    @ManyToOne
    private SupiAgendaAula fkIdAgendaAula;
    @JoinColumn(name = "fk_id_estado_presenca", referencedColumnName = "pk_id_estado_presenca")
    @ManyToOne
    private SupiEstadoPresenca fkIdEstadoPresenca;

    public SupiControloPresenca() {
    }

    public SupiControloPresenca(Integer pkIdControloPresenca) {
        this.pkIdControloPresenca = pkIdControloPresenca;
    }

    public Integer getPkIdControloPresenca() {
        return pkIdControloPresenca;
    }

    public void setPkIdControloPresenca(Integer pkIdControloPresenca) {
        this.pkIdControloPresenca = pkIdControloPresenca;
    }

    public RhEstagiario getFkIdEstagiario() {
        return fkIdEstagiario;
    }

    public void setFkIdEstagiario(RhEstagiario fkIdEstagiario) {
        this.fkIdEstagiario = fkIdEstagiario;
    }

    public SupiAgendaAula getFkIdAgendaAula() {
        return fkIdAgendaAula;
    }

    public void setFkIdAgendaAula(SupiAgendaAula fkIdAgendaAula) {
        this.fkIdAgendaAula = fkIdAgendaAula;
    }

    public SupiEstadoPresenca getFkIdEstadoPresenca() {
        return fkIdEstadoPresenca;
    }

    public void setFkIdEstadoPresenca(SupiEstadoPresenca fkIdEstadoPresenca) {
        this.fkIdEstadoPresenca = fkIdEstadoPresenca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdControloPresenca != null ? pkIdControloPresenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiControloPresenca)) {
            return false;
        }
        SupiControloPresenca other = (SupiControloPresenca) object;
        if ((this.pkIdControloPresenca == null && other.pkIdControloPresenca != null) || (this.pkIdControloPresenca != null && !this.pkIdControloPresenca.equals(other.pkIdControloPresenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiControloPresenca[ pkIdControloPresenca=" + pkIdControloPresenca + " ]";
    }
    
}
