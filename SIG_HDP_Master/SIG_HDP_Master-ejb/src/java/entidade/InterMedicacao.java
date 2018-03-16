/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_medicacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterMedicacao.findAll", query = "SELECT i FROM InterMedicacao i"),
    @NamedQuery(name = "InterMedicacao.findByPkIdMedicacao", query = "SELECT i FROM InterMedicacao i WHERE i.pkIdMedicacao = :pkIdMedicacao"),
    @NamedQuery(name = "InterMedicacao.findByDataHora", query = "SELECT i FROM InterMedicacao i WHERE i.dataHora = :dataHora")})
public class InterMedicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_medicacao", nullable = false)
    private Integer pkIdMedicacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMedicacao")
    private List<InterMedicacaoHasFarmProduto> interMedicacaoHasFarmProdutoList;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterMedicacao() {
    }

    public InterMedicacao(Integer pkIdMedicacao) {
        this.pkIdMedicacao = pkIdMedicacao;
    }

    public InterMedicacao(Integer pkIdMedicacao, Date dataHora) {
        this.pkIdMedicacao = pkIdMedicacao;
        this.dataHora = dataHora;
    }

    public Integer getPkIdMedicacao() {
        return pkIdMedicacao;
    }

    public void setPkIdMedicacao(Integer pkIdMedicacao) {
        this.pkIdMedicacao = pkIdMedicacao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    @XmlTransient
    public List<InterMedicacaoHasFarmProduto> getInterMedicacaoHasFarmProdutoList() {
        return interMedicacaoHasFarmProdutoList;
    }

    public void setInterMedicacaoHasFarmProdutoList(List<InterMedicacaoHasFarmProduto> interMedicacaoHasFarmProdutoList) {
        this.interMedicacaoHasFarmProdutoList = interMedicacaoHasFarmProdutoList;
    }

    public InterRegistoInternamento getFkIdRegistoInternamento() {
        return fkIdRegistoInternamento;
    }

    public void setFkIdRegistoInternamento(InterRegistoInternamento fkIdRegistoInternamento) {
        this.fkIdRegistoInternamento = fkIdRegistoInternamento;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMedicacao != null ? pkIdMedicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterMedicacao)) {
            return false;
        }
        InterMedicacao other = (InterMedicacao) object;
        if ((this.pkIdMedicacao == null && other.pkIdMedicacao != null) || (this.pkIdMedicacao != null && !this.pkIdMedicacao.equals(other.pkIdMedicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterMedicacao[ pkIdMedicacao=" + pkIdMedicacao + " ]";
    }
    
}
