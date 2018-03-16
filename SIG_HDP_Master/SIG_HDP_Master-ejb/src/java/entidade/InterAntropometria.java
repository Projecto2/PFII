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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_antropometria", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterAntropometria.findAll", query = "SELECT i FROM InterAntropometria i"),
    @NamedQuery(name = "InterAntropometria.findByPkIdAntropometria", query = "SELECT i FROM InterAntropometria i WHERE i.pkIdAntropometria = :pkIdAntropometria"),
    @NamedQuery(name = "InterAntropometria.findByAlturaCm", query = "SELECT i FROM InterAntropometria i WHERE i.alturaCm = :alturaCm"),
    @NamedQuery(name = "InterAntropometria.findByPesoKg", query = "SELECT i FROM InterAntropometria i WHERE i.pesoKg = :pesoKg"),
    @NamedQuery(name = "InterAntropometria.findByPA", query = "SELECT i FROM InterAntropometria i WHERE i.pA = :pA"),
    @NamedQuery(name = "InterAntropometria.findByPbMm", query = "SELECT i FROM InterAntropometria i WHERE i.pbMm = :pbMm"),
    @NamedQuery(name = "InterAntropometria.findByEdemas", query = "SELECT i FROM InterAntropometria i WHERE i.edemas = :edemas"),
    @NamedQuery(name = "InterAntropometria.findByPesoAlvo", query = "SELECT i FROM InterAntropometria i WHERE i.pesoAlvo = :pesoAlvo")})
public class InterAntropometria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_antropometria", nullable = false)
    private Long pkIdAntropometria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "altura_cm", nullable = false)
    private float alturaCm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_kg", nullable = false)
    private float pesoKg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "p_a", nullable = false)
    private float pA;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pb_mm", nullable = false)
    private float pbMm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "edemas", nullable = false, length = 45)
    private String edemas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_alvo", nullable = false)
    private float pesoAlvo;
    @JoinColumn(name = "fk_id_tratamento_malnutricao", referencedColumnName = "pk_id_tratamento_malnutricao", nullable = false)
    @ManyToOne(optional = false)
    private InterTratamentoMalnutricao fkIdTratamentoMalnutricao;

    public InterAntropometria() {
    }

    public InterAntropometria(Long pkIdAntropometria) {
        this.pkIdAntropometria = pkIdAntropometria;
    }

    public InterAntropometria(Long pkIdAntropometria, float alturaCm, float pesoKg, float pA, float pbMm, String edemas, float pesoAlvo) {
        this.pkIdAntropometria = pkIdAntropometria;
        this.alturaCm = alturaCm;
        this.pesoKg = pesoKg;
        this.pA = pA;
        this.pbMm = pbMm;
        this.edemas = edemas;
        this.pesoAlvo = pesoAlvo;
    }

    public Long getPkIdAntropometria() {
        return pkIdAntropometria;
    }

    public void setPkIdAntropometria(Long pkIdAntropometria) {
        this.pkIdAntropometria = pkIdAntropometria;
    }

    public float getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(float alturaCm) {
        this.alturaCm = alturaCm;
    }

    public float getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }

    public float getPA() {
        return pA;
    }

    public void setPA(float pA) {
        this.pA = pA;
    }

    public float getPbMm() {
        return pbMm;
    }

    public void setPbMm(float pbMm) {
        this.pbMm = pbMm;
    }

    public String getEdemas() {
        return edemas;
    }

    public void setEdemas(String edemas) {
        this.edemas = edemas;
    }

    public float getPesoAlvo() {
        return pesoAlvo;
    }

    public void setPesoAlvo(float pesoAlvo) {
        this.pesoAlvo = pesoAlvo;
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
        hash += (pkIdAntropometria != null ? pkIdAntropometria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterAntropometria)) {
            return false;
        }
        InterAntropometria other = (InterAntropometria) object;
        if ((this.pkIdAntropometria == null && other.pkIdAntropometria != null) || (this.pkIdAntropometria != null && !this.pkIdAntropometria.equals(other.pkIdAntropometria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterAntropometria[ pkIdAntropometria=" + pkIdAntropometria + " ]";
    }
    
}
