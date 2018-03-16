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
@Table(name = "diag_requisicao_componente_sanguineo_has_componente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineoHasComponente.findAll", query = "SELECT d FROM DiagRequisicaoComponenteSanguineoHasComponente d"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineoHasComponente.findByPkIdRequisicaoComponenteSanguineoHasComponentes", query = "SELECT d FROM DiagRequisicaoComponenteSanguineoHasComponente d WHERE d.pkIdRequisicaoComponenteSanguineoHasComponentes = :pkIdRequisicaoComponenteSanguineoHasComponentes"),
    @NamedQuery(name = "DiagRequisicaoComponenteSanguineoHasComponente.findByQuantidadeComponente", query = "SELECT d FROM DiagRequisicaoComponenteSanguineoHasComponente d WHERE d.quantidadeComponente = :quantidadeComponente")})
public class DiagRequisicaoComponenteSanguineoHasComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_requisicao_componente_sanguineo_has_componentes", nullable = false)
    private Integer pkIdRequisicaoComponenteSanguineoHasComponentes;
    @Column(name = "quantidade_componente")
    private Integer quantidadeComponente;
    @JoinColumn(name = "fk_id_componente", referencedColumnName = "pk_id_componente_sanguineo")
    @ManyToOne
    private DiagComponenteSanguineo fkIdComponente;
    @JoinColumn(name = "fk_id_requisicao_componente_sanguineo", referencedColumnName = "pk_id_requisicao_componente_sanguineo")
    @ManyToOne
    private DiagRequisicaoComponenteSanguineo fkIdRequisicaoComponenteSanguineo;

    public DiagRequisicaoComponenteSanguineoHasComponente() {
    }

    public DiagRequisicaoComponenteSanguineoHasComponente(Integer pkIdRequisicaoComponenteSanguineoHasComponentes) {
        this.pkIdRequisicaoComponenteSanguineoHasComponentes = pkIdRequisicaoComponenteSanguineoHasComponentes;
    }

    public Integer getPkIdRequisicaoComponenteSanguineoHasComponentes() {
        return pkIdRequisicaoComponenteSanguineoHasComponentes;
    }

    public void setPkIdRequisicaoComponenteSanguineoHasComponentes(Integer pkIdRequisicaoComponenteSanguineoHasComponentes) {
        this.pkIdRequisicaoComponenteSanguineoHasComponentes = pkIdRequisicaoComponenteSanguineoHasComponentes;
    }

    public Integer getQuantidadeComponente() {
        return quantidadeComponente;
    }

    public void setQuantidadeComponente(Integer quantidadeComponente) {
        this.quantidadeComponente = quantidadeComponente;
    }

    public DiagComponenteSanguineo getFkIdComponente() {
        return fkIdComponente;
    }

    public void setFkIdComponente(DiagComponenteSanguineo fkIdComponente) {
        this.fkIdComponente = fkIdComponente;
    }

    public DiagRequisicaoComponenteSanguineo getFkIdRequisicaoComponenteSanguineo() {
        return fkIdRequisicaoComponenteSanguineo;
    }

    public void setFkIdRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineo fkIdRequisicaoComponenteSanguineo) {
        this.fkIdRequisicaoComponenteSanguineo = fkIdRequisicaoComponenteSanguineo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRequisicaoComponenteSanguineoHasComponentes != null ? pkIdRequisicaoComponenteSanguineoHasComponentes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagRequisicaoComponenteSanguineoHasComponente)) {
            return false;
        }
        DiagRequisicaoComponenteSanguineoHasComponente other = (DiagRequisicaoComponenteSanguineoHasComponente) object;
        if ((this.pkIdRequisicaoComponenteSanguineoHasComponentes == null && other.pkIdRequisicaoComponenteSanguineoHasComponentes != null) || (this.pkIdRequisicaoComponenteSanguineoHasComponentes != null && !this.pkIdRequisicaoComponenteSanguineoHasComponentes.equals(other.pkIdRequisicaoComponenteSanguineoHasComponentes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagRequisicaoComponenteSanguineoHasComponente[ pkIdRequisicaoComponenteSanguineoHasComponentes=" + pkIdRequisicaoComponenteSanguineoHasComponentes + " ]";
    }
    
}
