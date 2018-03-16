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
@Table(name = "supi_formacao_formando", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiFormacaoFormando.findAll", query = "SELECT s FROM SupiFormacaoFormando s"),
    @NamedQuery(name = "SupiFormacaoFormando.findByPkIdFormacaoFormando", query = "SELECT s FROM SupiFormacaoFormando s WHERE s.pkIdFormacaoFormando = :pkIdFormacaoFormando")})
public class SupiFormacaoFormando implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_formacao_formando", nullable = false)
    private Integer pkIdFormacaoFormando;
    @JoinColumn(name = "fk_id_estagiario", referencedColumnName = "pk_id_estagiario")
    @ManyToOne
    private RhEstagiario fkIdEstagiario;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_formacao", referencedColumnName = "pk_id_formacao")
    @ManyToOne
    private SupiFormacao fkIdFormacao;

    public SupiFormacaoFormando() {
    }

    public SupiFormacaoFormando(Integer pkIdFormacaoFormando) {
        this.pkIdFormacaoFormando = pkIdFormacaoFormando;
    }

    public Integer getPkIdFormacaoFormando() {
        return pkIdFormacaoFormando;
    }

    public void setPkIdFormacaoFormando(Integer pkIdFormacaoFormando) {
        this.pkIdFormacaoFormando = pkIdFormacaoFormando;
    }

    public RhEstagiario getFkIdEstagiario() {
        return fkIdEstagiario;
    }

    public void setFkIdEstagiario(RhEstagiario fkIdEstagiario) {
        this.fkIdEstagiario = fkIdEstagiario;
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
        hash += (pkIdFormacaoFormando != null ? pkIdFormacaoFormando.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiFormacaoFormando)) {
            return false;
        }
        SupiFormacaoFormando other = (SupiFormacaoFormando) object;
        if ((this.pkIdFormacaoFormando == null && other.pkIdFormacaoFormando != null) || (this.pkIdFormacaoFormando != null && !this.pkIdFormacaoFormando.equals(other.pkIdFormacaoFormando))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiFormacaoFormando[ pkIdFormacaoFormando=" + pkIdFormacaoFormando + " ]";
    }
    
}
