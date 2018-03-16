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
@Table(name = "diag_resposta_requisicao_has_componente_has_teste", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagRespostaRequisicaoHasComponenteHasTeste.findAll", query = "SELECT d FROM DiagRespostaRequisicaoHasComponenteHasTeste d"),
    @NamedQuery(name = "DiagRespostaRequisicaoHasComponenteHasTeste.findByPkIdRespostaRequisicaoHasComponenteHasTeste", query = "SELECT d FROM DiagRespostaRequisicaoHasComponenteHasTeste d WHERE d.pkIdRespostaRequisicaoHasComponenteHasTeste = :pkIdRespostaRequisicaoHasComponenteHasTeste"),
    @NamedQuery(name = "DiagRespostaRequisicaoHasComponenteHasTeste.findByQuantidade", query = "SELECT d FROM DiagRespostaRequisicaoHasComponenteHasTeste d WHERE d.quantidade = :quantidade")})
public class DiagRespostaRequisicaoHasComponenteHasTeste implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_resposta_requisicao_has_componente_has_teste", nullable = false)
    private Integer pkIdRespostaRequisicaoHasComponenteHasTeste;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantidade", precision = 8, scale = 8)
    private Float quantidade;
    @JoinColumn(name = "fk_id_resposta_requisicao_has_componente", referencedColumnName = "pk_id_resposta_requisicao_componente_sanguineo_has_componente")
    @ManyToOne
    private DiagRespostaRequisicaoComponenteSanguineoHasComponente fkIdRespostaRequisicaoHasComponente;
    @JoinColumn(name = "fk_id_teste_compatibilidade", referencedColumnName = "pk_id_teste_compatibilidade")
    @ManyToOne
    private DiagTesteCompatibilidade fkIdTesteCompatibilidade;

    public DiagRespostaRequisicaoHasComponenteHasTeste() {
    }

    public DiagRespostaRequisicaoHasComponenteHasTeste(Integer pkIdRespostaRequisicaoHasComponenteHasTeste) {
        this.pkIdRespostaRequisicaoHasComponenteHasTeste = pkIdRespostaRequisicaoHasComponenteHasTeste;
    }

    public Integer getPkIdRespostaRequisicaoHasComponenteHasTeste() {
        return pkIdRespostaRequisicaoHasComponenteHasTeste;
    }

    public void setPkIdRespostaRequisicaoHasComponenteHasTeste(Integer pkIdRespostaRequisicaoHasComponenteHasTeste) {
        this.pkIdRespostaRequisicaoHasComponenteHasTeste = pkIdRespostaRequisicaoHasComponenteHasTeste;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public DiagRespostaRequisicaoComponenteSanguineoHasComponente getFkIdRespostaRequisicaoHasComponente() {
        return fkIdRespostaRequisicaoHasComponente;
    }

    public void setFkIdRespostaRequisicaoHasComponente(DiagRespostaRequisicaoComponenteSanguineoHasComponente fkIdRespostaRequisicaoHasComponente) {
        this.fkIdRespostaRequisicaoHasComponente = fkIdRespostaRequisicaoHasComponente;
    }

    public DiagTesteCompatibilidade getFkIdTesteCompatibilidade() {
        return fkIdTesteCompatibilidade;
    }

    public void setFkIdTesteCompatibilidade(DiagTesteCompatibilidade fkIdTesteCompatibilidade) {
        this.fkIdTesteCompatibilidade = fkIdTesteCompatibilidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdRespostaRequisicaoHasComponenteHasTeste != null ? pkIdRespostaRequisicaoHasComponenteHasTeste.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagRespostaRequisicaoHasComponenteHasTeste)) {
            return false;
        }
        DiagRespostaRequisicaoHasComponenteHasTeste other = (DiagRespostaRequisicaoHasComponenteHasTeste) object;
        if ((this.pkIdRespostaRequisicaoHasComponenteHasTeste == null && other.pkIdRespostaRequisicaoHasComponenteHasTeste != null) || (this.pkIdRespostaRequisicaoHasComponenteHasTeste != null && !this.pkIdRespostaRequisicaoHasComponenteHasTeste.equals(other.pkIdRespostaRequisicaoHasComponenteHasTeste))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagRespostaRequisicaoHasComponenteHasTeste[ pkIdRespostaRequisicaoHasComponenteHasTeste=" + pkIdRespostaRequisicaoHasComponenteHasTeste + " ]";
    }
    
}
