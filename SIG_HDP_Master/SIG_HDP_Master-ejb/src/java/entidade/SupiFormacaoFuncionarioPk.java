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
@Table(name = "supi_formacao_funcionario_pk", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiFormacaoFuncionarioPk.findAll", query = "SELECT s FROM SupiFormacaoFuncionarioPk s"),
    @NamedQuery(name = "SupiFormacaoFuncionarioPk.findByPkIdFormacaoFuncionarioPk", query = "SELECT s FROM SupiFormacaoFuncionarioPk s WHERE s.pkIdFormacaoFuncionarioPk = :pkIdFormacaoFuncionarioPk")})
public class SupiFormacaoFuncionarioPk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_formacao_funcionario_pk", nullable = false)
    private Integer pkIdFormacaoFuncionarioPk;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_formacao", referencedColumnName = "pk_id_formacao", nullable = false)
    @ManyToOne(optional = false)
    private SupiFormacao fkIdFormacao;

    public SupiFormacaoFuncionarioPk() {
    }

    public SupiFormacaoFuncionarioPk(Integer pkIdFormacaoFuncionarioPk) {
        this.pkIdFormacaoFuncionarioPk = pkIdFormacaoFuncionarioPk;
    }

    public Integer getPkIdFormacaoFuncionarioPk() {
        return pkIdFormacaoFuncionarioPk;
    }

    public void setPkIdFormacaoFuncionarioPk(Integer pkIdFormacaoFuncionarioPk) {
        this.pkIdFormacaoFuncionarioPk = pkIdFormacaoFuncionarioPk;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public SupiFormacao getFkIdFormacao() {
        return fkIdFormacao;
    }

    public void setFkIdFormacao(SupiFormacao fkIdFormacao) {
        this.fkIdFormacao = fkIdFormacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFormacaoFuncionarioPk != null ? pkIdFormacaoFuncionarioPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiFormacaoFuncionarioPk)) {
            return false;
        }
        SupiFormacaoFuncionarioPk other = (SupiFormacaoFuncionarioPk) object;
        if ((this.pkIdFormacaoFuncionarioPk == null && other.pkIdFormacaoFuncionarioPk != null) || (this.pkIdFormacaoFuncionarioPk != null && !this.pkIdFormacaoFuncionarioPk.equals(other.pkIdFormacaoFuncionarioPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiFormacaoFuncionarioPk[ pkIdFormacaoFuncionarioPk=" + pkIdFormacaoFuncionarioPk + " ]";
    }
    
}
