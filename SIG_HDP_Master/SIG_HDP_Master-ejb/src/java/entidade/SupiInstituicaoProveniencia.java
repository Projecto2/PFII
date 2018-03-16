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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_instituicao_proveniencia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiInstituicaoProveniencia.findAll", query = "SELECT s FROM SupiInstituicaoProveniencia s"),
    @NamedQuery(name = "SupiInstituicaoProveniencia.findByDescricao", query = "SELECT s FROM SupiInstituicaoProveniencia s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SupiInstituicaoProveniencia.findByPkIdInstituicaoProveniencia", query = "SELECT s FROM SupiInstituicaoProveniencia s WHERE s.pkIdInstituicaoProveniencia = :pkIdInstituicaoProveniencia")})
public class SupiInstituicaoProveniencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_instituicao_proveniencia", nullable = false)
    private Integer pkIdInstituicaoProveniencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdInstituicaoProveniencia")
    private List<SupiFormadorAux> supiFormadorAuxList;
    @OneToMany(mappedBy = "fkIdEntidade")
    private List<SupiFormadorAux> supiFormadorAuxList1;
    @JoinColumn(name = "fk_id_endereco", referencedColumnName = "pk_id_endereco", nullable = false)
    @ManyToOne(optional = false)
    private GrlEndereco fkIdEndereco;

    public SupiInstituicaoProveniencia() {
    }

    public SupiInstituicaoProveniencia(Integer pkIdInstituicaoProveniencia) {
        this.pkIdInstituicaoProveniencia = pkIdInstituicaoProveniencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdInstituicaoProveniencia() {
        return pkIdInstituicaoProveniencia;
    }

    public void setPkIdInstituicaoProveniencia(Integer pkIdInstituicaoProveniencia) {
        this.pkIdInstituicaoProveniencia = pkIdInstituicaoProveniencia;
    }

    @XmlTransient
    public List<SupiFormadorAux> getSupiFormadorAuxList() {
        return supiFormadorAuxList;
    }

    public void setSupiFormadorAuxList(List<SupiFormadorAux> supiFormadorAuxList) {
        this.supiFormadorAuxList = supiFormadorAuxList;
    }

    @XmlTransient
    public List<SupiFormadorAux> getSupiFormadorAuxList1() {
        return supiFormadorAuxList1;
    }

    public void setSupiFormadorAuxList1(List<SupiFormadorAux> supiFormadorAuxList1) {
        this.supiFormadorAuxList1 = supiFormadorAuxList1;
    }

    public GrlEndereco getFkIdEndereco() {
        return fkIdEndereco;
    }

    public void setFkIdEndereco(GrlEndereco fkIdEndereco) {
        this.fkIdEndereco = fkIdEndereco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdInstituicaoProveniencia != null ? pkIdInstituicaoProveniencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiInstituicaoProveniencia)) {
            return false;
        }
        SupiInstituicaoProveniencia other = (SupiInstituicaoProveniencia) object;
        if ((this.pkIdInstituicaoProveniencia == null && other.pkIdInstituicaoProveniencia != null) || (this.pkIdInstituicaoProveniencia != null && !this.pkIdInstituicaoProveniencia.equals(other.pkIdInstituicaoProveniencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiInstituicaoProveniencia[ pkIdInstituicaoProveniencia=" + pkIdInstituicaoProveniencia + " ]";
    }
    
}
