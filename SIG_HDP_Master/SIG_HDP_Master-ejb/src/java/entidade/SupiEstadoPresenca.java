/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "supi_estado_presenca", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEstadoPresenca.findAll", query = "SELECT s FROM SupiEstadoPresenca s"),
    @NamedQuery(name = "SupiEstadoPresenca.findByDescricao", query = "SELECT s FROM SupiEstadoPresenca s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SupiEstadoPresenca.findBySigla", query = "SELECT s FROM SupiEstadoPresenca s WHERE s.sigla = :sigla"),
    @NamedQuery(name = "SupiEstadoPresenca.findByValor", query = "SELECT s FROM SupiEstadoPresenca s WHERE s.valor = :valor"),
    @NamedQuery(name = "SupiEstadoPresenca.findByPkIdEstadoPresenca", query = "SELECT s FROM SupiEstadoPresenca s WHERE s.pkIdEstadoPresenca = :pkIdEstadoPresenca")})
public class SupiEstadoPresenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @Size(max = 100)
    @Column(name = "sigla", length = 100)
    private String sigla;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 17, scale = 17)
    private Double valor;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estado_presenca", nullable = false)
    private Integer pkIdEstadoPresenca;
    @OneToMany(mappedBy = "fkIdEstadoPresenca")
    private List<SupiControloPresenca> supiControloPresencaList;
    @OneToMany(mappedBy = "fkIdEstadoPresenca")
    private List<SupiPresencaEstagiario> supiPresencaEstagiarioList;

    public SupiEstadoPresenca() {
    }

    public SupiEstadoPresenca(Integer pkIdEstadoPresenca) {
        this.pkIdEstadoPresenca = pkIdEstadoPresenca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getPkIdEstadoPresenca() {
        return pkIdEstadoPresenca;
    }

    public void setPkIdEstadoPresenca(Integer pkIdEstadoPresenca) {
        this.pkIdEstadoPresenca = pkIdEstadoPresenca;
    }

    @XmlTransient
    public List<SupiControloPresenca> getSupiControloPresencaList() {
        return supiControloPresencaList;
    }

    public void setSupiControloPresencaList(List<SupiControloPresenca> supiControloPresencaList) {
        this.supiControloPresencaList = supiControloPresencaList;
    }

    @XmlTransient
    public List<SupiPresencaEstagiario> getSupiPresencaEstagiarioList() {
        return supiPresencaEstagiarioList;
    }

    public void setSupiPresencaEstagiarioList(List<SupiPresencaEstagiario> supiPresencaEstagiarioList) {
        this.supiPresencaEstagiarioList = supiPresencaEstagiarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoPresenca != null ? pkIdEstadoPresenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEstadoPresenca)) {
            return false;
        }
        SupiEstadoPresenca other = (SupiEstadoPresenca) object;
        if ((this.pkIdEstadoPresenca == null && other.pkIdEstadoPresenca != null) || (this.pkIdEstadoPresenca != null && !this.pkIdEstadoPresenca.equals(other.pkIdEstadoPresenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEstadoPresenca[ pkIdEstadoPresenca=" + pkIdEstadoPresenca + " ]";
    }
    
}
