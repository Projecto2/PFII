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
@Table(name = "diag_numero_doacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagNumeroDoacao.findAll", query = "SELECT d FROM DiagNumeroDoacao d"),
    @NamedQuery(name = "DiagNumeroDoacao.findByPkIdNumeroDoacao", query = "SELECT d FROM DiagNumeroDoacao d WHERE d.pkIdNumeroDoacao = :pkIdNumeroDoacao"),
    @NamedQuery(name = "DiagNumeroDoacao.findByDescricao", query = "SELECT d FROM DiagNumeroDoacao d WHERE d.descricao = :descricao")})
public class DiagNumeroDoacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_numero_doacao", nullable = false)
    private Integer pkIdNumeroDoacao;
    @Size(max = 25)
    @Column(name = "descricao", length = 25)
    private String descricao;
    @OneToMany(mappedBy = "fkIdNumeroDoacao")
    private List<DiagCandidatoDador> diagCandidatoDadorList;

    public DiagNumeroDoacao() {
    }

    public DiagNumeroDoacao(Integer pkIdNumeroDoacao) {
        this.pkIdNumeroDoacao = pkIdNumeroDoacao;
    }

    public Integer getPkIdNumeroDoacao() {
        return pkIdNumeroDoacao;
    }

    public void setPkIdNumeroDoacao(Integer pkIdNumeroDoacao) {
        this.pkIdNumeroDoacao = pkIdNumeroDoacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<DiagCandidatoDador> getDiagCandidatoDadorList() {
        return diagCandidatoDadorList;
    }

    public void setDiagCandidatoDadorList(List<DiagCandidatoDador> diagCandidatoDadorList) {
        this.diagCandidatoDadorList = diagCandidatoDadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdNumeroDoacao != null ? pkIdNumeroDoacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagNumeroDoacao)) {
            return false;
        }
        DiagNumeroDoacao other = (DiagNumeroDoacao) object;
        if ((this.pkIdNumeroDoacao == null && other.pkIdNumeroDoacao != null) || (this.pkIdNumeroDoacao != null && !this.pkIdNumeroDoacao.equals(other.pkIdNumeroDoacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagNumeroDoacao[ pkIdNumeroDoacao=" + pkIdNumeroDoacao + " ]";
    }
    
}
