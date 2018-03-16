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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_tipo_doacao", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_tipo_doacao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipoDoacao.findAll", query = "SELECT d FROM DiagTipoDoacao d"),
    @NamedQuery(name = "DiagTipoDoacao.findByPkIdTipoDoacao", query = "SELECT d FROM DiagTipoDoacao d WHERE d.pkIdTipoDoacao = :pkIdTipoDoacao"),
    @NamedQuery(name = "DiagTipoDoacao.findByDescricaoTipoDoacao", query = "SELECT d FROM DiagTipoDoacao d WHERE d.descricaoTipoDoacao = :descricaoTipoDoacao")})
public class DiagTipoDoacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_doacao", nullable = false)
    private Integer pkIdTipoDoacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao_tipo_doacao", nullable = false, length = 45)
    private String descricaoTipoDoacao;
    @OneToMany(mappedBy = "fkIdTipoDoacao")
    private List<DiagCandidatoDador> diagCandidatoDadorList;

    public DiagTipoDoacao() {
    }

    public DiagTipoDoacao(Integer pkIdTipoDoacao) {
        this.pkIdTipoDoacao = pkIdTipoDoacao;
    }

    public DiagTipoDoacao(Integer pkIdTipoDoacao, String descricaoTipoDoacao) {
        this.pkIdTipoDoacao = pkIdTipoDoacao;
        this.descricaoTipoDoacao = descricaoTipoDoacao;
    }

    public Integer getPkIdTipoDoacao() {
        return pkIdTipoDoacao;
    }

    public void setPkIdTipoDoacao(Integer pkIdTipoDoacao) {
        this.pkIdTipoDoacao = pkIdTipoDoacao;
    }

    public String getDescricaoTipoDoacao() {
        return descricaoTipoDoacao;
    }

    public void setDescricaoTipoDoacao(String descricaoTipoDoacao) {
        this.descricaoTipoDoacao = descricaoTipoDoacao;
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
        hash += (pkIdTipoDoacao != null ? pkIdTipoDoacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipoDoacao)) {
            return false;
        }
        DiagTipoDoacao other = (DiagTipoDoacao) object;
        if ((this.pkIdTipoDoacao == null && other.pkIdTipoDoacao != null) || (this.pkIdTipoDoacao != null && !this.pkIdTipoDoacao.equals(other.pkIdTipoDoacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipoDoacao[ pkIdTipoDoacao=" + pkIdTipoDoacao + " ]";
    }
    
}
