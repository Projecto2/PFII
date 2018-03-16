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
@Table(name = "diag_respostas_questoes_requisicao_componentes", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagRespostasQuestoesRequisicaoComponentes.findAll", query = "SELECT d FROM DiagRespostasQuestoesRequisicaoComponentes d"),
    @NamedQuery(name = "DiagRespostasQuestoesRequisicaoComponentes.findByPkIdRespostasQuestoesRequisicaoComponentes", query = "SELECT d FROM DiagRespostasQuestoesRequisicaoComponentes d WHERE d.pkIdRespostasQuestoesRequisicaoComponentes = :pkIdRespostasQuestoesRequisicaoComponentes"),
    @NamedQuery(name = "DiagRespostasQuestoesRequisicaoComponentes.findByDescricao", query = "SELECT d FROM DiagRespostasQuestoesRequisicaoComponentes d WHERE d.descricao = :descricao")})
public class DiagRespostasQuestoesRequisicaoComponentes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_respostas_questoes_requisicao_componentes", nullable = false)
    private Integer pkIdRespostasQuestoesRequisicaoComponentes;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;
    @OneToMany(mappedBy = "sistomatologiaHemorragica")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList;
    @OneToMany(mappedBy = "historiaReaccaoTransfusional")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList1;
    @OneToMany(mappedBy = "gravidezAnterior")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList2;
    @OneToMany(mappedBy = "transfusaoAnterior")
    private List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList3;

    public DiagRespostasQuestoesRequisicaoComponentes() {
    }

    public DiagRespostasQuestoesRequisicaoComponentes(Integer pkIdRespostasQuestoesRequisicaoComponentes) {
        this.pkIdRespostasQuestoesRequisicaoComponentes = pkIdRespostasQuestoesRequisicaoComponentes;
    }

    public Integer getPkIdRespostasQuestoesRequisicaoComponentes() {
        return pkIdRespostasQuestoesRequisicaoComponentes;
    }

    public void setPkIdRespostasQuestoesRequisicaoComponentes(Integer pkIdRespostasQuestoesRequisicaoComponentes) {
        this.pkIdRespostasQuestoesRequisicaoComponentes = pkIdRespostasQuestoesRequisicaoComponentes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList() {
        return diagRequisicaoComponenteSanguineoList;
    }

    public void setDiagRequisicaoComponenteSanguineoList(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList) {
        this.diagRequisicaoComponenteSanguineoList = diagRequisicaoComponenteSanguineoList;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList1() {
        return diagRequisicaoComponenteSanguineoList1;
    }

    public void setDiagRequisicaoComponenteSanguineoList1(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList1) {
        this.diagRequisicaoComponenteSanguineoList1 = diagRequisicaoComponenteSanguineoList1;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList2() {
        return diagRequisicaoComponenteSanguineoList2;
    }

    public void setDiagRequisicaoComponenteSanguineoList2(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList2) {
        this.diagRequisicaoComponenteSanguineoList2 = diagRequisicaoComponenteSanguineoList2;
    }

    @XmlTransient
    public List<DiagRequisicaoComponenteSanguineo> getDiagRequisicaoComponenteSanguineoList3() {
        return diagRequisicaoComponenteSanguineoList3;
    }

    public void setDiagRequisicaoComponenteSanguineoList3(List<DiagRequisicaoComponenteSanguineo> diagRequisicaoComponenteSanguineoList3) {
        this.diagRequisicaoComponenteSanguineoList3 = diagRequisicaoComponenteSanguineoList3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRespostasQuestoesRequisicaoComponentes != null ? pkIdRespostasQuestoesRequisicaoComponentes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagRespostasQuestoesRequisicaoComponentes)) {
            return false;
        }
        DiagRespostasQuestoesRequisicaoComponentes other = (DiagRespostasQuestoesRequisicaoComponentes) object;
        if ((this.pkIdRespostasQuestoesRequisicaoComponentes == null && other.pkIdRespostasQuestoesRequisicaoComponentes != null) || (this.pkIdRespostasQuestoesRequisicaoComponentes != null && !this.pkIdRespostasQuestoesRequisicaoComponentes.equals(other.pkIdRespostasQuestoesRequisicaoComponentes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagRespostasQuestoesRequisicaoComponentes[ pkIdRespostasQuestoesRequisicaoComponentes=" + pkIdRespostasQuestoesRequisicaoComponentes + " ]";
    }
    
}
