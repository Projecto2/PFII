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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "tb_medicamento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMedicamento.findAll", query = "SELECT t FROM TbMedicamento t"),
    @NamedQuery(name = "TbMedicamento.findByPkMedicamento", query = "SELECT t FROM TbMedicamento t WHERE t.pkMedicamento = :pkMedicamento"),
    @NamedQuery(name = "TbMedicamento.findByDescricao", query = "SELECT t FROM TbMedicamento t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TbMedicamento.findByAbreviatura", query = "SELECT t FROM TbMedicamento t WHERE t.abreviatura = :abreviatura")})
public class TbMedicamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_medicamento", nullable = false)
    private Integer pkMedicamento;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;
    @Size(max = 10)
    @Column(name = "abreviatura", length = 10)
    private String abreviatura;

    public TbMedicamento() {
    }

    public TbMedicamento(Integer pkMedicamento) {
        this.pkMedicamento = pkMedicamento;
    }

    public Integer getPkMedicamento() {
        return pkMedicamento;
    }

    public void setPkMedicamento(Integer pkMedicamento) {
        this.pkMedicamento = pkMedicamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkMedicamento != null ? pkMedicamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbMedicamento)) {
            return false;
        }
        TbMedicamento other = (TbMedicamento) object;
        if ((this.pkMedicamento == null && other.pkMedicamento != null) || (this.pkMedicamento != null && !this.pkMedicamento.equals(other.pkMedicamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbMedicamento[ pkMedicamento=" + pkMedicamento + " ]";
    }
    
}
