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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_resposta_requisicao_componente_sanguineo_has_componente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagRespostaRequisicaoComponenteSanguineoHasComponente.findAll", query = "SELECT d FROM DiagRespostaRequisicaoComponenteSanguineoHasComponente d"),
    @NamedQuery(name = "DiagRespostaRequisicaoComponenteSanguineoHasComponente.findByPkIdRespostaRequisicaoComponenteSanguineoHasComponente", query = "SELECT d FROM DiagRespostaRequisicaoComponenteSanguineoHasComponente d WHERE d.pkIdRespostaRequisicaoComponenteSanguineoHasComponente = :pkIdRespostaRequisicaoComponenteSanguineoHasComponente")})
public class DiagRespostaRequisicaoComponenteSanguineoHasComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_resposta_requisicao_componente_sanguineo_has_componente", nullable = false)
    private Integer pkIdRespostaRequisicaoComponenteSanguineoHasComponente;
    @OneToMany(mappedBy = "fkIdRespostaRequisicaoHasComponente")
    private List<DiagRespostaRequisicaoHasComponenteHasTeste> diagRespostaRequisicaoHasComponenteHasTesteList;
    @JoinColumn(name = "fk_id_componente", referencedColumnName = "pk_id_componente_sanguineo")
    @ManyToOne
    private DiagComponenteSanguineo fkIdComponente;
    @JoinColumn(name = "fk_id_resposta_requisicao_componente", referencedColumnName = "pk_id_resposta_requisicao_componente_sanguineo")
    @ManyToOne
    private DiagRespostaRequisicaoComponenteSanguineo fkIdRespostaRequisicaoComponente;

    public DiagRespostaRequisicaoComponenteSanguineoHasComponente() {
    }

    public DiagRespostaRequisicaoComponenteSanguineoHasComponente(Integer pkIdRespostaRequisicaoComponenteSanguineoHasComponente) {
        this.pkIdRespostaRequisicaoComponenteSanguineoHasComponente = pkIdRespostaRequisicaoComponenteSanguineoHasComponente;
    }

    public Integer getPkIdRespostaRequisicaoComponenteSanguineoHasComponente() {
        return pkIdRespostaRequisicaoComponenteSanguineoHasComponente;
    }

    public void setPkIdRespostaRequisicaoComponenteSanguineoHasComponente(Integer pkIdRespostaRequisicaoComponenteSanguineoHasComponente) {
        this.pkIdRespostaRequisicaoComponenteSanguineoHasComponente = pkIdRespostaRequisicaoComponenteSanguineoHasComponente;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoHasComponenteHasTeste> getDiagRespostaRequisicaoHasComponenteHasTesteList() {
        return diagRespostaRequisicaoHasComponenteHasTesteList;
    }

    public void setDiagRespostaRequisicaoHasComponenteHasTesteList(List<DiagRespostaRequisicaoHasComponenteHasTeste> diagRespostaRequisicaoHasComponenteHasTesteList) {
        this.diagRespostaRequisicaoHasComponenteHasTesteList = diagRespostaRequisicaoHasComponenteHasTesteList;
    }

    public DiagComponenteSanguineo getFkIdComponente() {
        return fkIdComponente;
    }

    public void setFkIdComponente(DiagComponenteSanguineo fkIdComponente) {
        this.fkIdComponente = fkIdComponente;
    }

    public DiagRespostaRequisicaoComponenteSanguineo getFkIdRespostaRequisicaoComponente() {
        return fkIdRespostaRequisicaoComponente;
    }

    public void setFkIdRespostaRequisicaoComponente(DiagRespostaRequisicaoComponenteSanguineo fkIdRespostaRequisicaoComponente) {
        this.fkIdRespostaRequisicaoComponente = fkIdRespostaRequisicaoComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRespostaRequisicaoComponenteSanguineoHasComponente != null ? pkIdRespostaRequisicaoComponenteSanguineoHasComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagRespostaRequisicaoComponenteSanguineoHasComponente)) {
            return false;
        }
        DiagRespostaRequisicaoComponenteSanguineoHasComponente other = (DiagRespostaRequisicaoComponenteSanguineoHasComponente) object;
        if ((this.pkIdRespostaRequisicaoComponenteSanguineoHasComponente == null && other.pkIdRespostaRequisicaoComponenteSanguineoHasComponente != null) || (this.pkIdRespostaRequisicaoComponenteSanguineoHasComponente != null && !this.pkIdRespostaRequisicaoComponenteSanguineoHasComponente.equals(other.pkIdRespostaRequisicaoComponenteSanguineoHasComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagRespostaRequisicaoComponenteSanguineoHasComponente[ pkIdRespostaRequisicaoComponenteSanguineoHasComponente=" + pkIdRespostaRequisicaoComponenteSanguineoHasComponente + " ]";
    }
    
}
