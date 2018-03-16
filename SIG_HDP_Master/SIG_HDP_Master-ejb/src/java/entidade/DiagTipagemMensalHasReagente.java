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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_tipagem_mensal_has_reagente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipagemMensalHasReagente.findAll", query = "SELECT d FROM DiagTipagemMensalHasReagente d"),
    @NamedQuery(name = "DiagTipagemMensalHasReagente.findByPkIdTipagemMensalHasReagente", query = "SELECT d FROM DiagTipagemMensalHasReagente d WHERE d.pkIdTipagemMensalHasReagente = :pkIdTipagemMensalHasReagente"),
    @NamedQuery(name = "DiagTipagemMensalHasReagente.findByQuantidade", query = "SELECT d FROM DiagTipagemMensalHasReagente d WHERE d.quantidade = :quantidade")})
public class DiagTipagemMensalHasReagente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipagem_mensal_has_reagente", nullable = false)
    private Integer pkIdTipagemMensalHasReagente;
    @Column(name = "quantidade")
    private Integer quantidade;
    @JoinColumn(name = "fk_id_reagente", referencedColumnName = "pk_id_reagente")
    @ManyToOne
    private DiagReagente fkIdReagente;
    @JoinColumn(name = "fk_id_tipagem_mensal", referencedColumnName = "pk_id_tipagem_mensal")
    @ManyToOne
    private DiagTipagemMensal fkIdTipagemMensal;

    public DiagTipagemMensalHasReagente() {
    }

    public DiagTipagemMensalHasReagente(Integer pkIdTipagemMensalHasReagente) {
        this.pkIdTipagemMensalHasReagente = pkIdTipagemMensalHasReagente;
    }

    public Integer getPkIdTipagemMensalHasReagente() {
        return pkIdTipagemMensalHasReagente;
    }

    public void setPkIdTipagemMensalHasReagente(Integer pkIdTipagemMensalHasReagente) {
        this.pkIdTipagemMensalHasReagente = pkIdTipagemMensalHasReagente;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public DiagReagente getFkIdReagente() {
        return fkIdReagente;
    }

    public void setFkIdReagente(DiagReagente fkIdReagente) {
        this.fkIdReagente = fkIdReagente;
    }

    public DiagTipagemMensal getFkIdTipagemMensal() {
        return fkIdTipagemMensal;
    }

    public void setFkIdTipagemMensal(DiagTipagemMensal fkIdTipagemMensal) {
        this.fkIdTipagemMensal = fkIdTipagemMensal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipagemMensalHasReagente != null ? pkIdTipagemMensalHasReagente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipagemMensalHasReagente)) {
            return false;
        }
        DiagTipagemMensalHasReagente other = (DiagTipagemMensalHasReagente) object;
        if ((this.pkIdTipagemMensalHasReagente == null && other.pkIdTipagemMensalHasReagente != null) || (this.pkIdTipagemMensalHasReagente != null && !this.pkIdTipagemMensalHasReagente.equals(other.pkIdTipagemMensalHasReagente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipagemMensalHasReagente[ pkIdTipagemMensalHasReagente=" + pkIdTipagemMensalHasReagente + " ]";
    }
    
}
