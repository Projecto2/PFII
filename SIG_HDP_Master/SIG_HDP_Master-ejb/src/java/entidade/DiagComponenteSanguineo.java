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
@Table(name = "diag_componente_sanguineo", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagComponenteSanguineo.findAll", query = "SELECT d FROM DiagComponenteSanguineo d"),
    @NamedQuery(name = "DiagComponenteSanguineo.findByPkIdComponenteSanguineo", query = "SELECT d FROM DiagComponenteSanguineo d WHERE d.pkIdComponenteSanguineo = :pkIdComponenteSanguineo"),
    @NamedQuery(name = "DiagComponenteSanguineo.findByDescricaoComponenteSanguineo", query = "SELECT d FROM DiagComponenteSanguineo d WHERE d.descricaoComponenteSanguineo = :descricaoComponenteSanguineo")})
public class DiagComponenteSanguineo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_componente_sanguineo", nullable = false)
    private Integer pkIdComponenteSanguineo;
    @Size(max = 100)
    @Column(name = "descricao_componente_sanguineo", length = 100)
    private String descricaoComponenteSanguineo;
    @OneToMany(mappedBy = "fkIdComponente")
    private List<DiagRequisicaoComponenteSanguineoHasComponente> diagRequisicaoComponenteSanguineoHasComponenteList;
    @OneToMany(mappedBy = "fkIdComponente")
    private List<DiagRespostaRequisicaoComponenteSanguineoHasComponente> diagRespostaRequisicaoComponenteSanguineoHasComponenteList;

    public DiagComponenteSanguineo() {
    }

    public DiagComponenteSanguineo(Integer pkIdComponenteSanguineo) {
        this.pkIdComponenteSanguineo = pkIdComponenteSanguineo;
    }

    public Integer getPkIdComponenteSanguineo() {
        return pkIdComponenteSanguineo;
    }

    public void setPkIdComponenteSanguineo(Integer pkIdComponenteSanguineo) {
        this.pkIdComponenteSanguineo = pkIdComponenteSanguineo;
    }

    public String getDescricaoComponenteSanguineo() {
        return descricaoComponenteSanguineo;
    }

    public void setDescricaoComponenteSanguineo(String descricaoComponenteSanguineo) {
        this.descricaoComponenteSanguineo = descricaoComponenteSanguineo;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineoHasComponente> getDiagRequisicaoComponenteSanguineoHasComponenteList() {
        return diagRequisicaoComponenteSanguineoHasComponenteList;
    }

    public void setDiagRequisicaoComponenteSanguineoHasComponenteList(List<DiagRequisicaoComponenteSanguineoHasComponente> diagRequisicaoComponenteSanguineoHasComponenteList) {
        this.diagRequisicaoComponenteSanguineoHasComponenteList = diagRequisicaoComponenteSanguineoHasComponenteList;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoComponenteSanguineoHasComponente> getDiagRespostaRequisicaoComponenteSanguineoHasComponenteList() {
        return diagRespostaRequisicaoComponenteSanguineoHasComponenteList;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineoHasComponenteList(List<DiagRespostaRequisicaoComponenteSanguineoHasComponente> diagRespostaRequisicaoComponenteSanguineoHasComponenteList) {
        this.diagRespostaRequisicaoComponenteSanguineoHasComponenteList = diagRespostaRequisicaoComponenteSanguineoHasComponenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdComponenteSanguineo != null ? pkIdComponenteSanguineo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagComponenteSanguineo)) {
            return false;
        }
        DiagComponenteSanguineo other = (DiagComponenteSanguineo) object;
        if ((this.pkIdComponenteSanguineo == null && other.pkIdComponenteSanguineo != null) || (this.pkIdComponenteSanguineo != null && !this.pkIdComponenteSanguineo.equals(other.pkIdComponenteSanguineo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagComponenteSanguineo[ pkIdComponenteSanguineo=" + pkIdComponenteSanguineo + " ]";
    }
    
}
