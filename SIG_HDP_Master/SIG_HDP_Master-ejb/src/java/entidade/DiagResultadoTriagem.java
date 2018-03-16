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
@Table(name = "diag_resultado_triagem", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_resultado_triagem"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagResultadoTriagem.findAll", query = "SELECT d FROM DiagResultadoTriagem d"),
    @NamedQuery(name = "DiagResultadoTriagem.findByPkIdResultadoTriagem", query = "SELECT d FROM DiagResultadoTriagem d WHERE d.pkIdResultadoTriagem = :pkIdResultadoTriagem"),
    @NamedQuery(name = "DiagResultadoTriagem.findByDescricaoResultadoTriagem", query = "SELECT d FROM DiagResultadoTriagem d WHERE d.descricaoResultadoTriagem = :descricaoResultadoTriagem")})
public class DiagResultadoTriagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_resultado_triagem", nullable = false)
    private Integer pkIdResultadoTriagem;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao_resultado_triagem", nullable = false, length = 45)
    private String descricaoResultadoTriagem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdResultadoTriagem")
    private List<DiagTriagem> diagTriagemList;
    @OneToMany(mappedBy = "conclusao")
    private List<DiagExameCandidato> diagExameCandidatoList;

    public DiagResultadoTriagem() {
    }

    public DiagResultadoTriagem(Integer pkIdResultadoTriagem) {
        this.pkIdResultadoTriagem = pkIdResultadoTriagem;
    }

    public DiagResultadoTriagem(Integer pkIdResultadoTriagem, String descricaoResultadoTriagem) {
        this.pkIdResultadoTriagem = pkIdResultadoTriagem;
        this.descricaoResultadoTriagem = descricaoResultadoTriagem;
    }

    public Integer getPkIdResultadoTriagem() {
        return pkIdResultadoTriagem;
    }

    public void setPkIdResultadoTriagem(Integer pkIdResultadoTriagem) {
        this.pkIdResultadoTriagem = pkIdResultadoTriagem;
    }

    public String getDescricaoResultadoTriagem() {
        return descricaoResultadoTriagem;
    }

    public void setDescricaoResultadoTriagem(String descricaoResultadoTriagem) {
        this.descricaoResultadoTriagem = descricaoResultadoTriagem;
    }

    @XmlTransient
    public List<DiagTriagem> getDiagTriagemList() {
        return diagTriagemList;
    }

    public void setDiagTriagemList(List<DiagTriagem> diagTriagemList) {
        this.diagTriagemList = diagTriagemList;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList() {
        return diagExameCandidatoList;
    }

    public void setDiagExameCandidatoList(List<DiagExameCandidato> diagExameCandidatoList) {
        this.diagExameCandidatoList = diagExameCandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdResultadoTriagem != null ? pkIdResultadoTriagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagResultadoTriagem)) {
            return false;
        }
        DiagResultadoTriagem other = (DiagResultadoTriagem) object;
        if ((this.pkIdResultadoTriagem == null && other.pkIdResultadoTriagem != null) || (this.pkIdResultadoTriagem != null && !this.pkIdResultadoTriagem.equals(other.pkIdResultadoTriagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagResultadoTriagem[ pkIdResultadoTriagem=" + pkIdResultadoTriagem + " ]";
    }
    
}
