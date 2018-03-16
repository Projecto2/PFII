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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_tratamento_sistematico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterTratamentoSistematico.findAll", query = "SELECT i FROM InterTratamentoSistematico i"),
    @NamedQuery(name = "InterTratamentoSistematico.findByPkIdTratamentoSistematico", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.pkIdTratamentoSistematico = :pkIdTratamentoSistematico"),
    @NamedQuery(name = "InterTratamentoSistematico.findByVita", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.vita = :vita"),
    @NamedQuery(name = "InterTratamentoSistematico.findByAntibiotico1", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.antibiotico1 = :antibiotico1"),
    @NamedQuery(name = "InterTratamentoSistematico.findByAmoxicilina", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.amoxicilina = :amoxicilina"),
    @NamedQuery(name = "InterTratamentoSistematico.findByCloroquina", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.cloroquina = :cloroquina"),
    @NamedQuery(name = "InterTratamentoSistematico.findByMebendazole", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.mebendazole = :mebendazole"),
    @NamedQuery(name = "InterTratamentoSistematico.findByAcidoFolico", query = "SELECT i FROM InterTratamentoSistematico i WHERE i.acidoFolico = :acidoFolico")})
public class InterTratamentoSistematico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tratamento_sistematico", nullable = false)
    private Long pkIdTratamentoSistematico;
    @Size(max = 45)
    @Column(name = "vita", length = 45)
    private String vita;
    @Size(max = 45)
    @Column(name = "antibiotico1", length = 45)
    private String antibiotico1;
    @Size(max = 45)
    @Column(name = "amoxicilina", length = 45)
    private String amoxicilina;
    @Size(max = 45)
    @Column(name = "cloroquina", length = 45)
    private String cloroquina;
    @Size(max = 45)
    @Column(name = "mebendazole", length = 45)
    private String mebendazole;
    @Column(name = "acido_folico")
    private Integer acidoFolico;
    @JoinColumn(name = "fk_id_tratamento_malnutricao", referencedColumnName = "pk_id_tratamento_malnutricao", nullable = false)
    @ManyToOne(optional = false)
    private InterTratamentoMalnutricao fkIdTratamentoMalnutricao;

    public InterTratamentoSistematico() {
    }

    public InterTratamentoSistematico(Long pkIdTratamentoSistematico) {
        this.pkIdTratamentoSistematico = pkIdTratamentoSistematico;
    }

    public Long getPkIdTratamentoSistematico() {
        return pkIdTratamentoSistematico;
    }

    public void setPkIdTratamentoSistematico(Long pkIdTratamentoSistematico) {
        this.pkIdTratamentoSistematico = pkIdTratamentoSistematico;
    }

    public String getVita() {
        return vita;
    }

    public void setVita(String vita) {
        this.vita = vita;
    }

    public String getAntibiotico1() {
        return antibiotico1;
    }

    public void setAntibiotico1(String antibiotico1) {
        this.antibiotico1 = antibiotico1;
    }

    public String getAmoxicilina() {
        return amoxicilina;
    }

    public void setAmoxicilina(String amoxicilina) {
        this.amoxicilina = amoxicilina;
    }

    public String getCloroquina() {
        return cloroquina;
    }

    public void setCloroquina(String cloroquina) {
        this.cloroquina = cloroquina;
    }

    public String getMebendazole() {
        return mebendazole;
    }

    public void setMebendazole(String mebendazole) {
        this.mebendazole = mebendazole;
    }

    public Integer getAcidoFolico() {
        return acidoFolico;
    }

    public void setAcidoFolico(Integer acidoFolico) {
        this.acidoFolico = acidoFolico;
    }

    public InterTratamentoMalnutricao getFkIdTratamentoMalnutricao() {
        return fkIdTratamentoMalnutricao;
    }

    public void setFkIdTratamentoMalnutricao(InterTratamentoMalnutricao fkIdTratamentoMalnutricao) {
        this.fkIdTratamentoMalnutricao = fkIdTratamentoMalnutricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTratamentoSistematico != null ? pkIdTratamentoSistematico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterTratamentoSistematico)) {
            return false;
        }
        InterTratamentoSistematico other = (InterTratamentoSistematico) object;
        if ((this.pkIdTratamentoSistematico == null && other.pkIdTratamentoSistematico != null) || (this.pkIdTratamentoSistematico != null && !this.pkIdTratamentoSistematico.equals(other.pkIdTratamentoSistematico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterTratamentoSistematico[ pkIdTratamentoSistematico=" + pkIdTratamentoSistematico + " ]";
    }
    
}
