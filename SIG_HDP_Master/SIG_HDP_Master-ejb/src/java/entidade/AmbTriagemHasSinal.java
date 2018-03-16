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
@Table(name = "amb_triagem_has_sinal", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbTriagemHasSinal.findAll", query = "SELECT a FROM AmbTriagemHasSinal a"),
    @NamedQuery(name = "AmbTriagemHasSinal.findByPkIdTriagemSinal", query = "SELECT a FROM AmbTriagemHasSinal a WHERE a.pkIdTriagemSinal = :pkIdTriagemSinal")})
public class AmbTriagemHasSinal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_triagem_sinal", nullable = false)
    private Integer pkIdTriagemSinal;
    @JoinColumn(name = "fk_id_sinal", referencedColumnName = "pk_id_sinal")
    @ManyToOne
    private AmbSinal fkIdSinal;
    @JoinColumn(name = "fk_id_triagem", referencedColumnName = "pk_id_triagem")
    @ManyToOne
    private AmbTriagem fkIdTriagem;

    public AmbTriagemHasSinal() {
    }

    public AmbTriagemHasSinal(Integer pkIdTriagemSinal) {
        this.pkIdTriagemSinal = pkIdTriagemSinal;
    }

    public Integer getPkIdTriagemSinal() {
        return pkIdTriagemSinal;
    }

    public void setPkIdTriagemSinal(Integer pkIdTriagemSinal) {
        this.pkIdTriagemSinal = pkIdTriagemSinal;
    }

    public AmbSinal getFkIdSinal() {
        return fkIdSinal;
    }

    public void setFkIdSinal(AmbSinal fkIdSinal) {
        this.fkIdSinal = fkIdSinal;
    }

    public AmbTriagem getFkIdTriagem() {
        return fkIdTriagem;
    }

    public void setFkIdTriagem(AmbTriagem fkIdTriagem) {
        this.fkIdTriagem = fkIdTriagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTriagemSinal != null ? pkIdTriagemSinal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbTriagemHasSinal)) {
            return false;
        }
        AmbTriagemHasSinal other = (AmbTriagemHasSinal) object;
        if ((this.pkIdTriagemSinal == null && other.pkIdTriagemSinal != null) || (this.pkIdTriagemSinal != null && !this.pkIdTriagemSinal.equals(other.pkIdTriagemSinal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbTriagemHasSinal[ pkIdTriagemSinal=" + pkIdTriagemSinal + " ]";
    }
    
}
