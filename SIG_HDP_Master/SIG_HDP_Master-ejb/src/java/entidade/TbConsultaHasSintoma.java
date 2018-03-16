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
@Table(name = "tb_consulta_has_sintoma", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbConsultaHasSintoma.findAll", query = "SELECT t FROM TbConsultaHasSintoma t"),
    @NamedQuery(name = "TbConsultaHasSintoma.findByPkConsultaHasSintoma", query = "SELECT t FROM TbConsultaHasSintoma t WHERE t.pkConsultaHasSintoma = :pkConsultaHasSintoma")})
public class TbConsultaHasSintoma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_consulta_has_sintoma", nullable = false)
    private Long pkConsultaHasSintoma;
    @JoinColumn(name = "fk_consulta", referencedColumnName = "pk_consulta")
    @ManyToOne
    private TbConsulta fkConsulta;
    @JoinColumn(name = "fk_sintoma", referencedColumnName = "pk_sintoma")
    @ManyToOne
    private TbSintoma fkSintoma;

    public TbConsultaHasSintoma() {
    }

    public TbConsultaHasSintoma(Long pkConsultaHasSintoma) {
        this.pkConsultaHasSintoma = pkConsultaHasSintoma;
    }

    public Long getPkConsultaHasSintoma() {
        return pkConsultaHasSintoma;
    }

    public void setPkConsultaHasSintoma(Long pkConsultaHasSintoma) {
        this.pkConsultaHasSintoma = pkConsultaHasSintoma;
    }

    public TbConsulta getFkConsulta() {
        return fkConsulta;
    }

    public void setFkConsulta(TbConsulta fkConsulta) {
        this.fkConsulta = fkConsulta;
    }

    public TbSintoma getFkSintoma() {
        return fkSintoma;
    }

    public void setFkSintoma(TbSintoma fkSintoma) {
        this.fkSintoma = fkSintoma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkConsultaHasSintoma != null ? pkConsultaHasSintoma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbConsultaHasSintoma)) {
            return false;
        }
        TbConsultaHasSintoma other = (TbConsultaHasSintoma) object;
        if ((this.pkConsultaHasSintoma == null && other.pkConsultaHasSintoma != null) || (this.pkConsultaHasSintoma != null && !this.pkConsultaHasSintoma.equals(other.pkConsultaHasSintoma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbConsultaHasSintoma[ pkConsultaHasSintoma=" + pkConsultaHasSintoma + " ]";
    }
    
}
