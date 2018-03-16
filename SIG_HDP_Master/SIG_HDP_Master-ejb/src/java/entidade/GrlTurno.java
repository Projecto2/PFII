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
@Table(name = "grl_turno", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlTurno.findAll", query = "SELECT g FROM GrlTurno g"),
    @NamedQuery(name = "GrlTurno.findByPkIdTurno", query = "SELECT g FROM GrlTurno g WHERE g.pkIdTurno = :pkIdTurno"),
    @NamedQuery(name = "GrlTurno.findByCodigoTurno", query = "SELECT g FROM GrlTurno g WHERE g.codigoTurno = :codigoTurno"),
    @NamedQuery(name = "GrlTurno.findByDescricao", query = "SELECT g FROM GrlTurno g WHERE g.descricao = :descricao")})
public class GrlTurno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_turno", nullable = false)
    private Integer pkIdTurno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "codigo_turno", nullable = false, length = 5)
    private String codigoTurno;
    @Size(max = 20)
    @Column(name = "descricao", length = 20)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTurno")
    private List<GrlHorarioIntervalo> grlHorarioIntervaloList;

    public GrlTurno() {
    }

    public GrlTurno(Integer pkIdTurno) {
        this.pkIdTurno = pkIdTurno;
    }

    public GrlTurno(Integer pkIdTurno, String codigoTurno) {
        this.pkIdTurno = pkIdTurno;
        this.codigoTurno = codigoTurno;
    }

    public Integer getPkIdTurno() {
        return pkIdTurno;
    }

    public void setPkIdTurno(Integer pkIdTurno) {
        this.pkIdTurno = pkIdTurno;
    }

    public String getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(String codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<GrlHorarioIntervalo> getGrlHorarioIntervaloList() {
        return grlHorarioIntervaloList;
    }

    public void setGrlHorarioIntervaloList(List<GrlHorarioIntervalo> grlHorarioIntervaloList) {
        this.grlHorarioIntervaloList = grlHorarioIntervaloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurno != null ? pkIdTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlTurno)) {
            return false;
        }
        GrlTurno other = (GrlTurno) object;
        if ((this.pkIdTurno == null && other.pkIdTurno != null) || (this.pkIdTurno != null && !this.pkIdTurno.equals(other.pkIdTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlTurno[ pkIdTurno=" + pkIdTurno + " ]";
    }
    
}
