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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_anotacao_enfermagem", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterAnotacaoEnfermagem.findAll", query = "SELECT i FROM InterAnotacaoEnfermagem i"),
    @NamedQuery(name = "InterAnotacaoEnfermagem.findByPkIdAnotacaoEnfermagem", query = "SELECT i FROM InterAnotacaoEnfermagem i WHERE i.pkIdAnotacaoEnfermagem = :pkIdAnotacaoEnfermagem"),
    @NamedQuery(name = "InterAnotacaoEnfermagem.findByDataHora", query = "SELECT i FROM InterAnotacaoEnfermagem i WHERE i.dataHora = :dataHora"),
    @NamedQuery(name = "InterAnotacaoEnfermagem.findByDescricao", query = "SELECT i FROM InterAnotacaoEnfermagem i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterAnotacaoEnfermagem.findByData", query = "SELECT i FROM InterAnotacaoEnfermagem i WHERE i.data = :data")})
public class InterAnotacaoEnfermagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_anotacao_enfermagem", nullable = false)
    private Integer pkIdAnotacaoEnfermagem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public InterAnotacaoEnfermagem() {
    }

    public InterAnotacaoEnfermagem(Integer pkIdAnotacaoEnfermagem) {
        this.pkIdAnotacaoEnfermagem = pkIdAnotacaoEnfermagem;
    }

    public InterAnotacaoEnfermagem(Integer pkIdAnotacaoEnfermagem, Date dataHora, String descricao, Date data) {
        this.pkIdAnotacaoEnfermagem = pkIdAnotacaoEnfermagem;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.data = data;
    }

    public Integer getPkIdAnotacaoEnfermagem() {
        return pkIdAnotacaoEnfermagem;
    }

    public void setPkIdAnotacaoEnfermagem(Integer pkIdAnotacaoEnfermagem) {
        this.pkIdAnotacaoEnfermagem = pkIdAnotacaoEnfermagem;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
        hash += (pkIdAnotacaoEnfermagem != null ? pkIdAnotacaoEnfermagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterAnotacaoEnfermagem)) {
            return false;
        }
        InterAnotacaoEnfermagem other = (InterAnotacaoEnfermagem) object;
        if ((this.pkIdAnotacaoEnfermagem == null && other.pkIdAnotacaoEnfermagem != null) || (this.pkIdAnotacaoEnfermagem != null && !this.pkIdAnotacaoEnfermagem.equals(other.pkIdAnotacaoEnfermagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterAnotacaoEnfermagem[ pkIdAnotacaoEnfermagem=" + pkIdAnotacaoEnfermagem + " ]";
    }
    
}
