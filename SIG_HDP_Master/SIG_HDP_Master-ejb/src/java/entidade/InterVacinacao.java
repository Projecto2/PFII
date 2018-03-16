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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_vacinacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterVacinacao.findAll", query = "SELECT i FROM InterVacinacao i"),
    @NamedQuery(name = "InterVacinacao.findByPkIdVacinacao", query = "SELECT i FROM InterVacinacao i WHERE i.pkIdVacinacao = :pkIdVacinacao"),
    @NamedQuery(name = "InterVacinacao.findByCartao", query = "SELECT i FROM InterVacinacao i WHERE i.cartao = :cartao"),
    @NamedQuery(name = "InterVacinacao.findByData", query = "SELECT i FROM InterVacinacao i WHERE i.data = :data")})
public class InterVacinacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_vacinacao", nullable = false)
    private Integer pkIdVacinacao;
    @Size(max = 45)
    @Column(name = "cartao", length = 45)
    private String cartao;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "fk_id_registo_internamento", referencedColumnName = "pk_id_registo_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterRegistoInternamento fkIdRegistoInternamento;
    @JoinColumn(name = "fk_id_vacina", referencedColumnName = "pk_id_vacina", nullable = false)
    @ManyToOne(optional = false)
    private InterVacina fkIdVacina;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public InterVacinacao() {
    }

    public InterVacinacao(Integer pkIdVacinacao) {
        this.pkIdVacinacao = pkIdVacinacao;
    }

    public Integer getPkIdVacinacao() {
        return pkIdVacinacao;
    }

    public void setPkIdVacinacao(Integer pkIdVacinacao) {
        this.pkIdVacinacao = pkIdVacinacao;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
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

    public InterVacina getFkIdVacina() {
        return fkIdVacina;
    }

    public void setFkIdVacina(InterVacina fkIdVacina) {
        this.fkIdVacina = fkIdVacina;
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
        hash += (pkIdVacinacao != null ? pkIdVacinacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterVacinacao)) {
            return false;
        }
        InterVacinacao other = (InterVacinacao) object;
        if ((this.pkIdVacinacao == null && other.pkIdVacinacao != null) || (this.pkIdVacinacao != null && !this.pkIdVacinacao.equals(other.pkIdVacinacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterVacinacao[ pkIdVacinacao=" + pkIdVacinacao + " ]";
    }
    
}
