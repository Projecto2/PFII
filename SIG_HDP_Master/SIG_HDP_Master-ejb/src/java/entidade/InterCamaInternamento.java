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
@Table(name = "inter_cama_internamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterCamaInternamento.findAll", query = "SELECT i FROM InterCamaInternamento i"),
    @NamedQuery(name = "InterCamaInternamento.findByDescricaoCamaInternamento", query = "SELECT i FROM InterCamaInternamento i WHERE i.descricaoCamaInternamento = :descricaoCamaInternamento"),
    @NamedQuery(name = "InterCamaInternamento.findByCodigoCamaInternamento", query = "SELECT i FROM InterCamaInternamento i WHERE i.codigoCamaInternamento = :codigoCamaInternamento"),
    @NamedQuery(name = "InterCamaInternamento.findByPkIdCamaInternamento", query = "SELECT i FROM InterCamaInternamento i WHERE i.pkIdCamaInternamento = :pkIdCamaInternamento")})
public class InterCamaInternamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao_cama_internamento", nullable = false, length = 100)
    private String descricaoCamaInternamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo_cama_internamento", nullable = false, length = 45)
    private String codigoCamaInternamento;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_cama_internamento", nullable = false)
    private Integer pkIdCamaInternamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCamaInternamento")
    private List<InterCamaReservada> interCamaReservadaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCamaInternamento")
    private List<InterRegistoInternamento> interRegistoInternamentoList;
    @JoinColumn(name = "fk_id_estado_cama", referencedColumnName = "pk_id_estado_cama", nullable = false)
    @ManyToOne(optional = false)
    private InterEstadoCama fkIdEstadoCama;
    @JoinColumn(name = "fk_id_sala_internamento", referencedColumnName = "pk_id_sala_internamento", nullable = false)
    @ManyToOne(optional = false)
    private InterSalaInternamento fkIdSalaInternamento;

    public InterCamaInternamento() {
    }

    public InterCamaInternamento(Integer pkIdCamaInternamento) {
        this.pkIdCamaInternamento = pkIdCamaInternamento;
    }

    public InterCamaInternamento(Integer pkIdCamaInternamento, String descricaoCamaInternamento, String codigoCamaInternamento) {
        this.pkIdCamaInternamento = pkIdCamaInternamento;
        this.descricaoCamaInternamento = descricaoCamaInternamento;
        this.codigoCamaInternamento = codigoCamaInternamento;
    }

    public String getDescricaoCamaInternamento() {
        return descricaoCamaInternamento;
    }

    public void setDescricaoCamaInternamento(String descricaoCamaInternamento) {
        this.descricaoCamaInternamento = descricaoCamaInternamento;
    }

    public String getCodigoCamaInternamento() {
        return codigoCamaInternamento;
    }

    public void setCodigoCamaInternamento(String codigoCamaInternamento) {
        this.codigoCamaInternamento = codigoCamaInternamento;
    }

    public Integer getPkIdCamaInternamento() {
        return pkIdCamaInternamento;
    }

    public void setPkIdCamaInternamento(Integer pkIdCamaInternamento) {
        this.pkIdCamaInternamento = pkIdCamaInternamento;
    }

    @XmlTransient
    public List<InterCamaReservada> getInterCamaReservadaList() {
        return interCamaReservadaList;
    }

    public void setInterCamaReservadaList(List<InterCamaReservada> interCamaReservadaList) {
        this.interCamaReservadaList = interCamaReservadaList;
    }

    @XmlTransient
    public List<InterRegistoInternamento> getInterRegistoInternamentoList() {
        return interRegistoInternamentoList;
    }

    public void setInterRegistoInternamentoList(List<InterRegistoInternamento> interRegistoInternamentoList) {
        this.interRegistoInternamentoList = interRegistoInternamentoList;
    }

    public InterEstadoCama getFkIdEstadoCama() {
        return fkIdEstadoCama;
    }

    public void setFkIdEstadoCama(InterEstadoCama fkIdEstadoCama) {
        this.fkIdEstadoCama = fkIdEstadoCama;
    }

    public InterSalaInternamento getFkIdSalaInternamento() {
        return fkIdSalaInternamento;
    }

    public void setFkIdSalaInternamento(InterSalaInternamento fkIdSalaInternamento) {
        this.fkIdSalaInternamento = fkIdSalaInternamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCamaInternamento != null ? pkIdCamaInternamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterCamaInternamento)) {
            return false;
        }
        InterCamaInternamento other = (InterCamaInternamento) object;
        if ((this.pkIdCamaInternamento == null && other.pkIdCamaInternamento != null) || (this.pkIdCamaInternamento != null && !this.pkIdCamaInternamento.equals(other.pkIdCamaInternamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterCamaInternamento[ pkIdCamaInternamento=" + pkIdCamaInternamento + " ]";
    }
    
}
