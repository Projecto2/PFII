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
@Table(name = "supi_total_mensal", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiTotalMensal.findAll", query = "SELECT s FROM SupiTotalMensal s"),
    @NamedQuery(name = "SupiTotalMensal.findByPkIdTotalMensal", query = "SELECT s FROM SupiTotalMensal s WHERE s.pkIdTotalMensal = :pkIdTotalMensal"),
    @NamedQuery(name = "SupiTotalMensal.findByValor", query = "SELECT s FROM SupiTotalMensal s WHERE s.valor = :valor"),
    @NamedQuery(name = "SupiTotalMensal.findByDataCadastro", query = "SELECT s FROM SupiTotalMensal s WHERE s.dataCadastro = :dataCadastro")})
public class SupiTotalMensal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_total_mensal", nullable = false)
    private Integer pkIdTotalMensal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false)
    private int valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    public SupiTotalMensal() {
    }

    public SupiTotalMensal(Integer pkIdTotalMensal) {
        this.pkIdTotalMensal = pkIdTotalMensal;
    }

    public SupiTotalMensal(Integer pkIdTotalMensal, int valor, Date dataCadastro) {
        this.pkIdTotalMensal = pkIdTotalMensal;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdTotalMensal() {
        return pkIdTotalMensal;
    }

    public void setPkIdTotalMensal(Integer pkIdTotalMensal) {
        this.pkIdTotalMensal = pkIdTotalMensal;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTotalMensal != null ? pkIdTotalMensal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiTotalMensal)) {
            return false;
        }
        SupiTotalMensal other = (SupiTotalMensal) object;
        if ((this.pkIdTotalMensal == null && other.pkIdTotalMensal != null) || (this.pkIdTotalMensal != null && !this.pkIdTotalMensal.equals(other.pkIdTotalMensal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiTotalMensal[ pkIdTotalMensal=" + pkIdTotalMensal + " ]";
    }
    
}
