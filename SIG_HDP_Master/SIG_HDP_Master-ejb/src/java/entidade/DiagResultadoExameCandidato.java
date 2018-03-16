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
@Table(name = "diag_resultado_exame_candidato", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagResultadoExameCandidato.findAll", query = "SELECT d FROM DiagResultadoExameCandidato d"),
    @NamedQuery(name = "DiagResultadoExameCandidato.findByPkIdResultadoExameCandidato", query = "SELECT d FROM DiagResultadoExameCandidato d WHERE d.pkIdResultadoExameCandidato = :pkIdResultadoExameCandidato"),
    @NamedQuery(name = "DiagResultadoExameCandidato.findByDescricaoResultadoExameCandidato", query = "SELECT d FROM DiagResultadoExameCandidato d WHERE d.descricaoResultadoExameCandidato = :descricaoResultadoExameCandidato")})
public class DiagResultadoExameCandidato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_resultado_exame_candidato", nullable = false)
    private Integer pkIdResultadoExameCandidato;
    @Size(max = 45)
    @Column(name = "descricao_resultado_exame_candidato", length = 45)
    private String descricaoResultadoExameCandidato;
    @OneToMany(mappedBy = "resultadoPesquisaAnticorposIrregulares")
    private List<DiagRespostaRequisicaoComponenteSanguineo> diagRespostaRequisicaoComponenteSanguineoList;
    @OneToMany(mappedBy = "vdrl")
    private List<DiagExameCandidato> diagExameCandidatoList;
    @OneToMany(mappedBy = "aghbs")
    private List<DiagExameCandidato> diagExameCandidatoList1;
    @OneToMany(mappedBy = "hcv")
    private List<DiagExameCandidato> diagExameCandidatoList2;
    @OneToMany(mappedBy = "hiv")
    private List<DiagExameCandidato> diagExameCandidatoList3;
    @OneToMany(mappedBy = "mal")
    private List<DiagExameCandidato> diagExameCandidatoList4;
    @OneToMany(mappedBy = "provaSoroFisiologico")
    private List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList;
    @OneToMany(mappedBy = "testeCoombs")
    private List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList1;

    public DiagResultadoExameCandidato() {
    }

    public DiagResultadoExameCandidato(Integer pkIdResultadoExameCandidato) {
        this.pkIdResultadoExameCandidato = pkIdResultadoExameCandidato;
    }

    public Integer getPkIdResultadoExameCandidato() {
        return pkIdResultadoExameCandidato;
    }

    public void setPkIdResultadoExameCandidato(Integer pkIdResultadoExameCandidato) {
        this.pkIdResultadoExameCandidato = pkIdResultadoExameCandidato;
    }

    public String getDescricaoResultadoExameCandidato() {
        return descricaoResultadoExameCandidato;
    }

    public void setDescricaoResultadoExameCandidato(String descricaoResultadoExameCandidato) {
        this.descricaoResultadoExameCandidato = descricaoResultadoExameCandidato;
    }

    @XmlTransient
    public List<DiagRespostaRequisicaoComponenteSanguineo> getDiagRespostaRequisicaoComponenteSanguineoList() {
        return diagRespostaRequisicaoComponenteSanguineoList;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineoList(List<DiagRespostaRequisicaoComponenteSanguineo> diagRespostaRequisicaoComponenteSanguineoList) {
        this.diagRespostaRequisicaoComponenteSanguineoList = diagRespostaRequisicaoComponenteSanguineoList;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList() {
        return diagExameCandidatoList;
    }

    public void setDiagExameCandidatoList(List<DiagExameCandidato> diagExameCandidatoList) {
        this.diagExameCandidatoList = diagExameCandidatoList;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList1() {
        return diagExameCandidatoList1;
    }

    public void setDiagExameCandidatoList1(List<DiagExameCandidato> diagExameCandidatoList1) {
        this.diagExameCandidatoList1 = diagExameCandidatoList1;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList2() {
        return diagExameCandidatoList2;
    }

    public void setDiagExameCandidatoList2(List<DiagExameCandidato> diagExameCandidatoList2) {
        this.diagExameCandidatoList2 = diagExameCandidatoList2;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList3() {
        return diagExameCandidatoList3;
    }

    public void setDiagExameCandidatoList3(List<DiagExameCandidato> diagExameCandidatoList3) {
        this.diagExameCandidatoList3 = diagExameCandidatoList3;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList4() {
        return diagExameCandidatoList4;
    }

    public void setDiagExameCandidatoList4(List<DiagExameCandidato> diagExameCandidatoList4) {
        this.diagExameCandidatoList4 = diagExameCandidatoList4;
    }

    @XmlTransient
    public List<DiagTesteCompatibilidade> getDiagTesteCompatibilidadeList() {
        return diagTesteCompatibilidadeList;
    }

    public void setDiagTesteCompatibilidadeList(List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList) {
        this.diagTesteCompatibilidadeList = diagTesteCompatibilidadeList;
    }

    @XmlTransient
    public List<DiagTesteCompatibilidade> getDiagTesteCompatibilidadeList1() {
        return diagTesteCompatibilidadeList1;
    }

    public void setDiagTesteCompatibilidadeList1(List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList1) {
        this.diagTesteCompatibilidadeList1 = diagTesteCompatibilidadeList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdResultadoExameCandidato != null ? pkIdResultadoExameCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagResultadoExameCandidato)) {
            return false;
        }
        DiagResultadoExameCandidato other = (DiagResultadoExameCandidato) object;
        if ((this.pkIdResultadoExameCandidato == null && other.pkIdResultadoExameCandidato != null) || (this.pkIdResultadoExameCandidato != null && !this.pkIdResultadoExameCandidato.equals(other.pkIdResultadoExameCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagResultadoExameCandidato[ pkIdResultadoExameCandidato=" + pkIdResultadoExameCandidato + " ]";
    }
    
}
