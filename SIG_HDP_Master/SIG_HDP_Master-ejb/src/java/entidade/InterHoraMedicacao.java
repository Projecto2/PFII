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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_hora_medicacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterHoraMedicacao.findAll", query = "SELECT i FROM InterHoraMedicacao i"),
    @NamedQuery(name = "InterHoraMedicacao.findByDescricao", query = "SELECT i FROM InterHoraMedicacao i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterHoraMedicacao.findByPkIdHoraMedicacao", query = "SELECT i FROM InterHoraMedicacao i WHERE i.pkIdHoraMedicacao = :pkIdHoraMedicacao")})
public class InterHoraMedicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "descricao", nullable = false, length = 10)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_hora_medicacao", nullable = false)
    private Integer pkIdHoraMedicacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdHoraMedicacao")
    private List<InterMedicacaoHasFarmProduto> interMedicacaoHasFarmProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdHoraMedicacao")
    private List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdHoraMedicacao")
    private List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList;

    public InterHoraMedicacao() {
    }

    public InterHoraMedicacao(Integer pkIdHoraMedicacao) {
        this.pkIdHoraMedicacao = pkIdHoraMedicacao;
    }

    public InterHoraMedicacao(Integer pkIdHoraMedicacao, String descricao) {
        this.pkIdHoraMedicacao = pkIdHoraMedicacao;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdHoraMedicacao() {
        return pkIdHoraMedicacao;
    }

    public void setPkIdHoraMedicacao(Integer pkIdHoraMedicacao) {
        this.pkIdHoraMedicacao = pkIdHoraMedicacao;
    }

    @XmlTransient
    public List<InterMedicacaoHasFarmProduto> getInterMedicacaoHasFarmProdutoList() {
        return interMedicacaoHasFarmProdutoList;
    }

    public void setInterMedicacaoHasFarmProdutoList(List<InterMedicacaoHasFarmProduto> interMedicacaoHasFarmProdutoList) {
        this.interMedicacaoHasFarmProdutoList = interMedicacaoHasFarmProdutoList;
    }

    @XmlTransient
    public List<InterRegistoInternamentoHasParametroVital> getInterRegistoInternamentoHasParametroVitalList() {
        return interRegistoInternamentoHasParametroVitalList;
    }

    public void setInterRegistoInternamentoHasParametroVitalList(List<InterRegistoInternamentoHasParametroVital> interRegistoInternamentoHasParametroVitalList) {
        this.interRegistoInternamentoHasParametroVitalList = interRegistoInternamentoHasParametroVitalList;
    }

    @XmlTransient
    public List<AmbPrescricaoMedicaHasProduto> getAmbPrescricaoMedicaHasProdutoList() {
        return ambPrescricaoMedicaHasProdutoList;
    }

    public void setAmbPrescricaoMedicaHasProdutoList(List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList) {
        this.ambPrescricaoMedicaHasProdutoList = ambPrescricaoMedicaHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdHoraMedicacao != null ? pkIdHoraMedicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterHoraMedicacao)) {
            return false;
        }
        InterHoraMedicacao other = (InterHoraMedicacao) object;
        if ((this.pkIdHoraMedicacao == null && other.pkIdHoraMedicacao != null) || (this.pkIdHoraMedicacao != null && !this.pkIdHoraMedicacao.equals(other.pkIdHoraMedicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterHoraMedicacao[ pkIdHoraMedicacao=" + pkIdHoraMedicacao + " ]";
    }
    
}
