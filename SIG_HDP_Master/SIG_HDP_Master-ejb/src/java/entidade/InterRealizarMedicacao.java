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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_realizar_medicacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterRealizarMedicacao.findAll", query = "SELECT i FROM InterRealizarMedicacao i"),
    @NamedQuery(name = "InterRealizarMedicacao.findByPkIdRealizarMedicacao", query = "SELECT i FROM InterRealizarMedicacao i WHERE i.pkIdRealizarMedicacao = :pkIdRealizarMedicacao"),
    @NamedQuery(name = "InterRealizarMedicacao.findByDataHora", query = "SELECT i FROM InterRealizarMedicacao i WHERE i.dataHora = :dataHora"),
    @NamedQuery(name = "InterRealizarMedicacao.findByHora", query = "SELECT i FROM InterRealizarMedicacao i WHERE i.hora = :hora"),
    @NamedQuery(name = "InterRealizarMedicacao.findByDataRegistadaNoPaciente", query = "SELECT i FROM InterRealizarMedicacao i WHERE i.dataRegistadaNoPaciente = :dataRegistadaNoPaciente")})
public class InterRealizarMedicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_realizar_medicacao", nullable = false)
    private Long pkIdRealizarMedicacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "hora")
    private Integer hora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_registada_no_paciente", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataRegistadaNoPaciente;
    @JoinColumn(name = "fk_id_medicacao_has_farm_produto", referencedColumnName = "pk_id_inter_medicacao_has_farm_produto", nullable = false)
    @ManyToOne(optional = false)
    private InterMedicacaoHasFarmProduto fkIdMedicacaoHasFarmProduto;
    @JoinColumn(name = "fk_id_opcao_medicacao", referencedColumnName = "pk_id_opcao_medicacao", nullable = false)
    @ManyToOne(optional = false)
    private InterOpcaoMedicacao fkIdOpcaoMedicacao;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterRealizarMedicacao() {
    }

    public InterRealizarMedicacao(Long pkIdRealizarMedicacao) {
        this.pkIdRealizarMedicacao = pkIdRealizarMedicacao;
    }

    public InterRealizarMedicacao(Long pkIdRealizarMedicacao, Date dataHora, Date dataRegistadaNoPaciente) {
        this.pkIdRealizarMedicacao = pkIdRealizarMedicacao;
        this.dataHora = dataHora;
        this.dataRegistadaNoPaciente = dataRegistadaNoPaciente;
    }

    public Long getPkIdRealizarMedicacao() {
        return pkIdRealizarMedicacao;
    }

    public void setPkIdRealizarMedicacao(Long pkIdRealizarMedicacao) {
        this.pkIdRealizarMedicacao = pkIdRealizarMedicacao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Date getDataRegistadaNoPaciente() {
        return dataRegistadaNoPaciente;
    }

    public void setDataRegistadaNoPaciente(Date dataRegistadaNoPaciente) {
        this.dataRegistadaNoPaciente = dataRegistadaNoPaciente;
    }

    public InterMedicacaoHasFarmProduto getFkIdMedicacaoHasFarmProduto() {
        return fkIdMedicacaoHasFarmProduto;
    }

    public void setFkIdMedicacaoHasFarmProduto(InterMedicacaoHasFarmProduto fkIdMedicacaoHasFarmProduto) {
        this.fkIdMedicacaoHasFarmProduto = fkIdMedicacaoHasFarmProduto;
    }

    public InterOpcaoMedicacao getFkIdOpcaoMedicacao() {
        return fkIdOpcaoMedicacao;
    }

    public void setFkIdOpcaoMedicacao(InterOpcaoMedicacao fkIdOpcaoMedicacao) {
        this.fkIdOpcaoMedicacao = fkIdOpcaoMedicacao;
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
        hash += (pkIdRealizarMedicacao != null ? pkIdRealizarMedicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterRealizarMedicacao)) {
            return false;
        }
        InterRealizarMedicacao other = (InterRealizarMedicacao) object;
        if ((this.pkIdRealizarMedicacao == null && other.pkIdRealizarMedicacao != null) || (this.pkIdRealizarMedicacao != null && !this.pkIdRealizarMedicacao.equals(other.pkIdRealizarMedicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterRealizarMedicacao[ pkIdRealizarMedicacao=" + pkIdRealizarMedicacao + " ]";
    }
    
}
