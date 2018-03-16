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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_historia_clinica_exames", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterHistoriaClinicaExames.findAll", query = "SELECT i FROM InterHistoriaClinicaExames i"),
    @NamedQuery(name = "InterHistoriaClinicaExames.findByPkIdHistoriaClinicaExames", query = "SELECT i FROM InterHistoriaClinicaExames i WHERE i.pkIdHistoriaClinicaExames = :pkIdHistoriaClinicaExames"),
    @NamedQuery(name = "InterHistoriaClinicaExames.findByData", query = "SELECT i FROM InterHistoriaClinicaExames i WHERE i.data = :data"),
    @NamedQuery(name = "InterHistoriaClinicaExames.findByDescricao", query = "SELECT i FROM InterHistoriaClinicaExames i WHERE i.descricao = :descricao")})
public class InterHistoriaClinicaExames implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_historia_clinica_exames", nullable = false)
    private Integer pkIdHistoriaClinicaExames;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;
    @JoinColumn(name = "fk_id_servico_solicitado", referencedColumnName = "pk_id_servico_solicitado", nullable = false)
    @ManyToOne(optional = false)
    private AdmsServicoSolicitado fkIdServicoSolicitado;

    public InterHistoriaClinicaExames() {
    }

    public InterHistoriaClinicaExames(Integer pkIdHistoriaClinicaExames) {
        this.pkIdHistoriaClinicaExames = pkIdHistoriaClinicaExames;
    }

    public InterHistoriaClinicaExames(Integer pkIdHistoriaClinicaExames, Date data, String descricao) {
        this.pkIdHistoriaClinicaExames = pkIdHistoriaClinicaExames;
        this.data = data;
        this.descricao = descricao;
    }

    public Integer getPkIdHistoriaClinicaExames() {
        return pkIdHistoriaClinicaExames;
    }

    public void setPkIdHistoriaClinicaExames(Integer pkIdHistoriaClinicaExames) {
        this.pkIdHistoriaClinicaExames = pkIdHistoriaClinicaExames;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public AdmsServicoSolicitado getFkIdServicoSolicitado() {
        return fkIdServicoSolicitado;
    }

    public void setFkIdServicoSolicitado(AdmsServicoSolicitado fkIdServicoSolicitado) {
        this.fkIdServicoSolicitado = fkIdServicoSolicitado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdHistoriaClinicaExames != null ? pkIdHistoriaClinicaExames.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterHistoriaClinicaExames)) {
            return false;
        }
        InterHistoriaClinicaExames other = (InterHistoriaClinicaExames) object;
        if ((this.pkIdHistoriaClinicaExames == null && other.pkIdHistoriaClinicaExames != null) || (this.pkIdHistoriaClinicaExames != null && !this.pkIdHistoriaClinicaExames.equals(other.pkIdHistoriaClinicaExames))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterHistoriaClinicaExames[ pkIdHistoriaClinicaExames=" + pkIdHistoriaClinicaExames + " ]";
    }
    
}
