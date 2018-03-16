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
@Table(name = "diag_exame", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagExame.findAll", query = "SELECT d FROM DiagExame d"),
    @NamedQuery(name = "DiagExame.findByPkIdExame", query = "SELECT d FROM DiagExame d WHERE d.pkIdExame = :pkIdExame"),
    @NamedQuery(name = "DiagExame.findByDescricaoExame", query = "SELECT d FROM DiagExame d WHERE d.descricaoExame = :descricaoExame"),
    @NamedQuery(name = "DiagExame.findByValorReferenciaMinimo", query = "SELECT d FROM DiagExame d WHERE d.valorReferenciaMinimo = :valorReferenciaMinimo"),
    @NamedQuery(name = "DiagExame.findByValorReferenciaMaximo", query = "SELECT d FROM DiagExame d WHERE d.valorReferenciaMaximo = :valorReferenciaMaximo")})
public class DiagExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_exame", nullable = false)
    private Integer pkIdExame;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao_exame", nullable = false, length = 45)
    private String descricaoExame;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_referencia_minimo", precision = 8, scale = 8)
    private Float valorReferenciaMinimo;
    @Column(name = "valor_referencia_maximo", precision = 8, scale = 8)
    private Float valorReferenciaMaximo;
    @OneToMany(mappedBy = "fkIdExame")
    private List<DiagExameRealizado> diagExameRealizadoList;
    @JoinColumn(name = "fk_id_categoria_exame", referencedColumnName = "pk_id_categoria", nullable = false)
    @ManyToOne(optional = false)
    private DiagCategoriaExame fkIdCategoriaExame;
    @JoinColumn(name = "fk_id_subcategoria_exame", referencedColumnName = "pk_id_subcategoria_exame")
    @ManyToOne
    private DiagSubcategoriaExame fkIdSubcategoriaExame;
    @JoinColumn(name = "fk_id_tipo_resultado", referencedColumnName = "pk_id_tipo_resultado_exame")
    @ManyToOne
    private DiagTipoResultadoExame fkIdTipoResultado;
    @JoinColumn(name = "fk_id_unidade_medida", referencedColumnName = "pk_id_unidade_medida")
    @ManyToOne
    private FarmUnidadeMedida fkIdUnidadeMedida;

    public DiagExame() {
    }

    public DiagExame(Integer pkIdExame) {
        this.pkIdExame = pkIdExame;
    }

    public DiagExame(Integer pkIdExame, String descricaoExame) {
        this.pkIdExame = pkIdExame;
        this.descricaoExame = descricaoExame;
    }

    public Integer getPkIdExame() {
        return pkIdExame;
    }

    public void setPkIdExame(Integer pkIdExame) {
        this.pkIdExame = pkIdExame;
    }

    public String getDescricaoExame() {
        return descricaoExame;
    }

    public void setDescricaoExame(String descricaoExame) {
        this.descricaoExame = descricaoExame;
    }

    public Float getValorReferenciaMinimo() {
        return valorReferenciaMinimo;
    }

    public void setValorReferenciaMinimo(Float valorReferenciaMinimo) {
        this.valorReferenciaMinimo = valorReferenciaMinimo;
    }

    public Float getValorReferenciaMaximo() {
        return valorReferenciaMaximo;
    }

    public void setValorReferenciaMaximo(Float valorReferenciaMaximo) {
        this.valorReferenciaMaximo = valorReferenciaMaximo;
    }

    @XmlTransient
    public List<DiagExameRealizado> getDiagExameRealizadoList() {
        return diagExameRealizadoList;
    }

    public void setDiagExameRealizadoList(List<DiagExameRealizado> diagExameRealizadoList) {
        this.diagExameRealizadoList = diagExameRealizadoList;
    }

    public DiagCategoriaExame getFkIdCategoriaExame() {
        return fkIdCategoriaExame;
    }

    public void setFkIdCategoriaExame(DiagCategoriaExame fkIdCategoriaExame) {
        this.fkIdCategoriaExame = fkIdCategoriaExame;
    }

    public DiagSubcategoriaExame getFkIdSubcategoriaExame() {
        return fkIdSubcategoriaExame;
    }

    public void setFkIdSubcategoriaExame(DiagSubcategoriaExame fkIdSubcategoriaExame) {
        this.fkIdSubcategoriaExame = fkIdSubcategoriaExame;
    }

    public DiagTipoResultadoExame getFkIdTipoResultado() {
        return fkIdTipoResultado;
    }

    public void setFkIdTipoResultado(DiagTipoResultadoExame fkIdTipoResultado) {
        this.fkIdTipoResultado = fkIdTipoResultado;
    }

    public FarmUnidadeMedida getFkIdUnidadeMedida() {
        return fkIdUnidadeMedida;
    }

    public void setFkIdUnidadeMedida(FarmUnidadeMedida fkIdUnidadeMedida) {
        this.fkIdUnidadeMedida = fkIdUnidadeMedida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdExame != null ? pkIdExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagExame)) {
            return false;
        }
        DiagExame other = (DiagExame) object;
        if ((this.pkIdExame == null && other.pkIdExame != null) || (this.pkIdExame != null && !this.pkIdExame.equals(other.pkIdExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagExame[ pkIdExame=" + pkIdExame + " ]";
    }
    
}
