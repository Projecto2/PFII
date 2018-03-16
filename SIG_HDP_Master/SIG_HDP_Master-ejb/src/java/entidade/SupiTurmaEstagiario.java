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
@Table(name = "supi_turma_estagiario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiTurmaEstagiario.findAll", query = "SELECT s FROM SupiTurmaEstagiario s"),
    @NamedQuery(name = "SupiTurmaEstagiario.findByPkIdTurmaEstagiario", query = "SELECT s FROM SupiTurmaEstagiario s WHERE s.pkIdTurmaEstagiario = :pkIdTurmaEstagiario")})
public class SupiTurmaEstagiario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_turma_estagiario", nullable = false)
    private Integer pkIdTurmaEstagiario;
    @JoinColumn(name = "fk_id_estagiario", referencedColumnName = "pk_id_estagiario", nullable = false)
    @ManyToOne(optional = false)
    private RhEstagiario fkIdEstagiario;
    @JoinColumn(name = "fk_id_criacao_turma", referencedColumnName = "pk_id_criacao_turma", nullable = false)
    @ManyToOne(optional = false)
    private SupiCriacaoTurma fkIdCriacaoTurma;

    public SupiTurmaEstagiario() {
    }

    public SupiTurmaEstagiario(Integer pkIdTurmaEstagiario) {
        this.pkIdTurmaEstagiario = pkIdTurmaEstagiario;
    }

    public Integer getPkIdTurmaEstagiario() {
        return pkIdTurmaEstagiario;
    }

    public void setPkIdTurmaEstagiario(Integer pkIdTurmaEstagiario) {
        this.pkIdTurmaEstagiario = pkIdTurmaEstagiario;
    }

    public RhEstagiario getFkIdEstagiario() {
        return fkIdEstagiario;
    }

    public void setFkIdEstagiario(RhEstagiario fkIdEstagiario) {
        this.fkIdEstagiario = fkIdEstagiario;
    }

    public SupiCriacaoTurma getFkIdCriacaoTurma() {
        return fkIdCriacaoTurma;
    }

    public void setFkIdCriacaoTurma(SupiCriacaoTurma fkIdCriacaoTurma) {
        this.fkIdCriacaoTurma = fkIdCriacaoTurma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurmaEstagiario != null ? pkIdTurmaEstagiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiTurmaEstagiario)) {
            return false;
        }
        SupiTurmaEstagiario other = (SupiTurmaEstagiario) object;
        if ((this.pkIdTurmaEstagiario == null && other.pkIdTurmaEstagiario != null) || (this.pkIdTurmaEstagiario != null && !this.pkIdTurmaEstagiario.equals(other.pkIdTurmaEstagiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiTurmaEstagiario[ pkIdTurmaEstagiario=" + pkIdTurmaEstagiario + " ]";
    }
    
}
