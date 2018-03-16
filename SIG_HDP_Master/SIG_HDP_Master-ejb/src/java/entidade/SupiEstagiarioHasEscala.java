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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_estagiario_has_escala", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_estagiario", "fk_id_escala", "data"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEstagiarioHasEscala.findAll", query = "SELECT s FROM SupiEstagiarioHasEscala s"),
    @NamedQuery(name = "SupiEstagiarioHasEscala.findByPkIdEstagiarioEscala", query = "SELECT s FROM SupiEstagiarioHasEscala s WHERE s.pkIdEstagiarioEscala = :pkIdEstagiarioEscala"),
    @NamedQuery(name = "SupiEstagiarioHasEscala.findByData", query = "SELECT s FROM SupiEstagiarioHasEscala s WHERE s.data = :data")})
public class SupiEstagiarioHasEscala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estagiario_escala", nullable = false)
    private Integer pkIdEstagiarioEscala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_estagiario", referencedColumnName = "pk_id_estagiario", nullable = false)
    @ManyToOne(optional = false)
    private RhEstagiario fkIdEstagiario;
    @JoinColumn(name = "fk_id_escala", referencedColumnName = "pk_id_escala", nullable = false)
    @ManyToOne(optional = false)
    private SupiEscala fkIdEscala;
    @JoinColumn(name = "fk_id_turno", referencedColumnName = "pk_id_turno", nullable = false)
    @ManyToOne(optional = false)
    private SupiTurno fkIdTurno;

    public SupiEstagiarioHasEscala() {
    }

    public SupiEstagiarioHasEscala(Integer pkIdEstagiarioEscala) {
        this.pkIdEstagiarioEscala = pkIdEstagiarioEscala;
    }

    public SupiEstagiarioHasEscala(Integer pkIdEstagiarioEscala, Date data) {
        this.pkIdEstagiarioEscala = pkIdEstagiarioEscala;
        this.data = data;
    }

    public Integer getPkIdEstagiarioEscala() {
        return pkIdEstagiarioEscala;
    }

    public void setPkIdEstagiarioEscala(Integer pkIdEstagiarioEscala) {
        this.pkIdEstagiarioEscala = pkIdEstagiarioEscala;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public RhEstagiario getFkIdEstagiario() {
        return fkIdEstagiario;
    }

    public void setFkIdEstagiario(RhEstagiario fkIdEstagiario) {
        this.fkIdEstagiario = fkIdEstagiario;
    }

    public SupiEscala getFkIdEscala() {
        return fkIdEscala;
    }

    public void setFkIdEscala(SupiEscala fkIdEscala) {
        this.fkIdEscala = fkIdEscala;
    }

    public SupiTurno getFkIdTurno() {
        return fkIdTurno;
    }

    public void setFkIdTurno(SupiTurno fkIdTurno) {
        this.fkIdTurno = fkIdTurno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstagiarioEscala != null ? pkIdEstagiarioEscala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEstagiarioHasEscala)) {
            return false;
        }
        SupiEstagiarioHasEscala other = (SupiEstagiarioHasEscala) object;
        if ((this.pkIdEstagiarioEscala == null && other.pkIdEstagiarioEscala != null) || (this.pkIdEstagiarioEscala != null && !this.pkIdEstagiarioEscala.equals(other.pkIdEstagiarioEscala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEstagiarioHasEscala[ pkIdEstagiarioEscala=" + pkIdEstagiarioEscala + " ]";
    }
    
}
