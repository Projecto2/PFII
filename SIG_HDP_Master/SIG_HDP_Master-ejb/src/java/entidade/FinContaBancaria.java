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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "fin_conta_bancaria", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinContaBancaria.findAll", query = "SELECT f FROM FinContaBancaria f"),
    @NamedQuery(name = "FinContaBancaria.findByPkIdContaBancaria", query = "SELECT f FROM FinContaBancaria f WHERE f.pkIdContaBancaria = :pkIdContaBancaria"),
    @NamedQuery(name = "FinContaBancaria.findByNumeroConta", query = "SELECT f FROM FinContaBancaria f WHERE f.numeroConta = :numeroConta")})
public class FinContaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_conta_bancaria", nullable = false)
    private Integer pkIdContaBancaria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero_conta", nullable = false, length = 50)
    private String numeroConta;
    @JoinColumn(name = "fk_id_banco", referencedColumnName = "pk_id_banco", nullable = false)
    @ManyToOne(optional = false)
    private FinBanco fkIdBanco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdContaBancaria")
    private List<FinPagamentoViaBanco> finPagamentoViaBancoList;

    public FinContaBancaria() {
    }

    public FinContaBancaria(Integer pkIdContaBancaria) {
        this.pkIdContaBancaria = pkIdContaBancaria;
    }

    public FinContaBancaria(Integer pkIdContaBancaria, String numeroConta) {
        this.pkIdContaBancaria = pkIdContaBancaria;
        this.numeroConta = numeroConta;
    }

    public Integer getPkIdContaBancaria() {
        return pkIdContaBancaria;
    }

    public void setPkIdContaBancaria(Integer pkIdContaBancaria) {
        this.pkIdContaBancaria = pkIdContaBancaria;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public FinBanco getFkIdBanco() {
        return fkIdBanco;
    }

    public void setFkIdBanco(FinBanco fkIdBanco) {
        this.fkIdBanco = fkIdBanco;
    }

    @XmlTransient
    public List<FinPagamentoViaBanco> getFinPagamentoViaBancoList() {
        return finPagamentoViaBancoList;
    }

    public void setFinPagamentoViaBancoList(List<FinPagamentoViaBanco> finPagamentoViaBancoList) {
        this.finPagamentoViaBancoList = finPagamentoViaBancoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdContaBancaria != null ? pkIdContaBancaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinContaBancaria)) {
            return false;
        }
        FinContaBancaria other = (FinContaBancaria) object;
        if ((this.pkIdContaBancaria == null && other.pkIdContaBancaria != null) || (this.pkIdContaBancaria != null && !this.pkIdContaBancaria.equals(other.pkIdContaBancaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinContaBancaria[ pkIdContaBancaria=" + pkIdContaBancaria + " ]";
    }
    
}
