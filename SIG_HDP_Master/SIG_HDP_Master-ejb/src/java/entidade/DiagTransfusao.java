/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_transfusao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTransfusao.findAll", query = "SELECT d FROM DiagTransfusao d"),
    @NamedQuery(name = "DiagTransfusao.findByPkIdTransfusao", query = "SELECT d FROM DiagTransfusao d WHERE d.pkIdTransfusao = :pkIdTransfusao"),
    @NamedQuery(name = "DiagTransfusao.findByDataTransfusao", query = "SELECT d FROM DiagTransfusao d WHERE d.dataTransfusao = :dataTransfusao")})
public class DiagTransfusao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_transfusao", nullable = false)
    private Integer pkIdTransfusao;
    @Column(name = "data_transfusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTransfusao;
    @JoinColumn(name = "fk_id_resposta_requisicao_componente_sanguineo", referencedColumnName = "pk_id_resposta_requisicao_componente_sanguineo")
    @ManyToOne
    private DiagRespostaRequisicaoComponenteSanguineo fkIdRespostaRequisicaoComponenteSanguineo;
    @JoinColumn(name = "fk_realizado_por", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkRealizadoPor;

    public DiagTransfusao() {
    }

    public DiagTransfusao(Integer pkIdTransfusao) {
        this.pkIdTransfusao = pkIdTransfusao;
    }

    public Integer getPkIdTransfusao() {
        return pkIdTransfusao;
    }

    public void setPkIdTransfusao(Integer pkIdTransfusao) {
        this.pkIdTransfusao = pkIdTransfusao;
    }

    public Date getDataTransfusao() {
        return dataTransfusao;
    }

    public void setDataTransfusao(Date dataTransfusao) {
        this.dataTransfusao = dataTransfusao;
    }

    public DiagRespostaRequisicaoComponenteSanguineo getFkIdRespostaRequisicaoComponenteSanguineo() {
        return fkIdRespostaRequisicaoComponenteSanguineo;
    }

    public void setFkIdRespostaRequisicaoComponenteSanguineo(DiagRespostaRequisicaoComponenteSanguineo fkIdRespostaRequisicaoComponenteSanguineo) {
        this.fkIdRespostaRequisicaoComponenteSanguineo = fkIdRespostaRequisicaoComponenteSanguineo;
    }

    public RhFuncionario getFkRealizadoPor() {
        return fkRealizadoPor;
    }

    public void setFkRealizadoPor(RhFuncionario fkRealizadoPor) {
        this.fkRealizadoPor = fkRealizadoPor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTransfusao != null ? pkIdTransfusao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTransfusao)) {
            return false;
        }
        DiagTransfusao other = (DiagTransfusao) object;
        if ((this.pkIdTransfusao == null && other.pkIdTransfusao != null) || (this.pkIdTransfusao != null && !this.pkIdTransfusao.equals(other.pkIdTransfusao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTransfusao[ pkIdTransfusao=" + pkIdTransfusao + " ]";
    }
    
}
