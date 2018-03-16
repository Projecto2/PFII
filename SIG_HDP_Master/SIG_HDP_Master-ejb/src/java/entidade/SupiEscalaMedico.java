/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_escala_medico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEscalaMedico.findAll", query = "SELECT s FROM SupiEscalaMedico s"),
    @NamedQuery(name = "SupiEscalaMedico.findByPkIdEscalaMedico", query = "SELECT s FROM SupiEscalaMedico s WHERE s.pkIdEscalaMedico = :pkIdEscalaMedico"),
    @NamedQuery(name = "SupiEscalaMedico.findByAno", query = "SELECT s FROM SupiEscalaMedico s WHERE s.ano = :ano"),
    @NamedQuery(name = "SupiEscalaMedico.findByMes", query = "SELECT s FROM SupiEscalaMedico s WHERE s.mes = :mes"),
    @NamedQuery(name = "SupiEscalaMedico.findByDataEscala", query = "SELECT s FROM SupiEscalaMedico s WHERE s.dataEscala = :dataEscala")})
public class SupiEscalaMedico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_escala_medico", nullable = false)
    private Integer pkIdEscalaMedico;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "data_escala")
    @Temporal(TemporalType.DATE)
    private Date dataEscala;
    @OneToMany(mappedBy = "fkIdEscalaMedico")
    private List<SupiMedicoHasEscala> supiMedicoHasEscalaList;
    @JoinColumn(name = "fk_id_seccao_trabalho", referencedColumnName = "pk_id_seccao_trabalho")
    @ManyToOne
    private RhSeccaoTrabalho fkIdSeccaoTrabalho;

    public SupiEscalaMedico() {
    }

    public SupiEscalaMedico(Integer pkIdEscalaMedico) {
        this.pkIdEscalaMedico = pkIdEscalaMedico;
    }

    public Integer getPkIdEscalaMedico() {
        return pkIdEscalaMedico;
    }

    public void setPkIdEscalaMedico(Integer pkIdEscalaMedico) {
        this.pkIdEscalaMedico = pkIdEscalaMedico;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Date getDataEscala() {
        return dataEscala;
    }

    public void setDataEscala(Date dataEscala) {
        this.dataEscala = dataEscala;
    }

    @XmlTransient
    public List<SupiMedicoHasEscala> getSupiMedicoHasEscalaList() {
        return supiMedicoHasEscalaList;
    }

    public void setSupiMedicoHasEscalaList(List<SupiMedicoHasEscala> supiMedicoHasEscalaList) {
        this.supiMedicoHasEscalaList = supiMedicoHasEscalaList;
    }

    public RhSeccaoTrabalho getFkIdSeccaoTrabalho() {
        return fkIdSeccaoTrabalho;
    }

    public void setFkIdSeccaoTrabalho(RhSeccaoTrabalho fkIdSeccaoTrabalho) {
        this.fkIdSeccaoTrabalho = fkIdSeccaoTrabalho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEscalaMedico != null ? pkIdEscalaMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEscalaMedico)) {
            return false;
        }
        SupiEscalaMedico other = (SupiEscalaMedico) object;
        if ((this.pkIdEscalaMedico == null && other.pkIdEscalaMedico != null) || (this.pkIdEscalaMedico != null && !this.pkIdEscalaMedico.equals(other.pkIdEscalaMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEscalaMedico[ pkIdEscalaMedico=" + pkIdEscalaMedico + " ]";
    }
    
}
