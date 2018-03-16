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
@Table(name = "inter_medicacao_has_farm_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterMedicacaoHasFarmProduto.findAll", query = "SELECT i FROM InterMedicacaoHasFarmProduto i"),
    @NamedQuery(name = "InterMedicacaoHasFarmProduto.findByPkIdInterMedicacaoHasFarmProduto", query = "SELECT i FROM InterMedicacaoHasFarmProduto i WHERE i.pkIdInterMedicacaoHasFarmProduto = :pkIdInterMedicacaoHasFarmProduto"),
    @NamedQuery(name = "InterMedicacaoHasFarmProduto.findByDose", query = "SELECT i FROM InterMedicacaoHasFarmProduto i WHERE i.dose = :dose"),
    @NamedQuery(name = "InterMedicacaoHasFarmProduto.findBySOS", query = "SELECT i FROM InterMedicacaoHasFarmProduto i WHERE i.sOS = :sOS")})
public class InterMedicacaoHasFarmProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_inter_medicacao_has_farm_produto", nullable = false)
    private Integer pkIdInterMedicacaoHasFarmProduto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "dose", nullable = false, length = 20)
    private String dose;
    @Column(name = "s_o_s")
    private Boolean sOS;
    @JoinColumn(name = "fk_id_produto", referencedColumnName = "pk_id_produto", nullable = false)
    @ManyToOne(optional = false)
    private FarmProduto fkIdProduto;
    @JoinColumn(name = "fk_id_hora_medicacao", referencedColumnName = "pk_id_hora_medicacao", nullable = false)
    @ManyToOne(optional = false)
    private InterHoraMedicacao fkIdHoraMedicacao;
    @JoinColumn(name = "fk_id_medicacao", referencedColumnName = "pk_id_medicacao", nullable = false)
    @ManyToOne(optional = false)
    private InterMedicacao fkIdMedicacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdMedicacaoHasFarmProduto")
    private List<InterRealizarMedicacao> interRealizarMedicacaoList;

    public InterMedicacaoHasFarmProduto() {
    }

    public InterMedicacaoHasFarmProduto(Integer pkIdInterMedicacaoHasFarmProduto) {
        this.pkIdInterMedicacaoHasFarmProduto = pkIdInterMedicacaoHasFarmProduto;
    }

    public InterMedicacaoHasFarmProduto(Integer pkIdInterMedicacaoHasFarmProduto, String dose) {
        this.pkIdInterMedicacaoHasFarmProduto = pkIdInterMedicacaoHasFarmProduto;
        this.dose = dose;
    }

    public Integer getPkIdInterMedicacaoHasFarmProduto() {
        return pkIdInterMedicacaoHasFarmProduto;
    }

    public void setPkIdInterMedicacaoHasFarmProduto(Integer pkIdInterMedicacaoHasFarmProduto) {
        this.pkIdInterMedicacaoHasFarmProduto = pkIdInterMedicacaoHasFarmProduto;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Boolean getSOS() {
        return sOS;
    }

    public void setSOS(Boolean sOS) {
        this.sOS = sOS;
    }

    public FarmProduto getFkIdProduto() {
        return fkIdProduto;
    }

    public void setFkIdProduto(FarmProduto fkIdProduto) {
        this.fkIdProduto = fkIdProduto;
    }

    public InterHoraMedicacao getFkIdHoraMedicacao() {
        return fkIdHoraMedicacao;
    }

    public void setFkIdHoraMedicacao(InterHoraMedicacao fkIdHoraMedicacao) {
        this.fkIdHoraMedicacao = fkIdHoraMedicacao;
    }

    public InterMedicacao getFkIdMedicacao() {
        return fkIdMedicacao;
    }

    public void setFkIdMedicacao(InterMedicacao fkIdMedicacao) {
        this.fkIdMedicacao = fkIdMedicacao;
    }

    @XmlTransient
    public List<InterRealizarMedicacao> getInterRealizarMedicacaoList() {
        return interRealizarMedicacaoList;
    }

    public void setInterRealizarMedicacaoList(List<InterRealizarMedicacao> interRealizarMedicacaoList) {
        this.interRealizarMedicacaoList = interRealizarMedicacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdInterMedicacaoHasFarmProduto != null ? pkIdInterMedicacaoHasFarmProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterMedicacaoHasFarmProduto)) {
            return false;
        }
        InterMedicacaoHasFarmProduto other = (InterMedicacaoHasFarmProduto) object;
        if ((this.pkIdInterMedicacaoHasFarmProduto == null && other.pkIdInterMedicacaoHasFarmProduto != null) || (this.pkIdInterMedicacaoHasFarmProduto != null && !this.pkIdInterMedicacaoHasFarmProduto.equals(other.pkIdInterMedicacaoHasFarmProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterMedicacaoHasFarmProduto[ pkIdInterMedicacaoHasFarmProduto=" + pkIdInterMedicacaoHasFarmProduto + " ]";
    }
    
}
