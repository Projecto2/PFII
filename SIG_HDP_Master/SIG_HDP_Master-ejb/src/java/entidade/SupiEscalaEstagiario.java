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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_escala_estagiario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEscalaEstagiario.findAll", query = "SELECT s FROM SupiEscalaEstagiario s"),
    @NamedQuery(name = "SupiEscalaEstagiario.findByPkIdEscalaEstagiario", query = "SELECT s FROM SupiEscalaEstagiario s WHERE s.pkIdEscalaEstagiario = :pkIdEscalaEstagiario"),
    @NamedQuery(name = "SupiEscalaEstagiario.findByDataEscala", query = "SELECT s FROM SupiEscalaEstagiario s WHERE s.dataEscala = :dataEscala"),
    @NamedQuery(name = "SupiEscalaEstagiario.findByDataCadastro", query = "SELECT s FROM SupiEscalaEstagiario s WHERE s.dataCadastro = :dataCadastro")})
public class SupiEscalaEstagiario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_escala_estagiario", nullable = false)
    private Integer pkIdEscalaEstagiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_escala", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEscala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_estagiario", referencedColumnName = "pk_id_estagiario", nullable = false)
    @ManyToOne(optional = false)
    private RhEstagiario fkIdEstagiario;

    public SupiEscalaEstagiario() {
    }

    public SupiEscalaEstagiario(Integer pkIdEscalaEstagiario) {
        this.pkIdEscalaEstagiario = pkIdEscalaEstagiario;
    }

    public SupiEscalaEstagiario(Integer pkIdEscalaEstagiario, Date dataEscala, Date dataCadastro) {
        this.pkIdEscalaEstagiario = pkIdEscalaEstagiario;
        this.dataEscala = dataEscala;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdEscalaEstagiario() {
        return pkIdEscalaEstagiario;
    }

    public void setPkIdEscalaEstagiario(Integer pkIdEscalaEstagiario) {
        this.pkIdEscalaEstagiario = pkIdEscalaEstagiario;
    }

    public Date getDataEscala() {
        return dataEscala;
    }

    public void setDataEscala(Date dataEscala) {
        this.dataEscala = dataEscala;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public RhEstagiario getFkIdEstagiario() {
        return fkIdEstagiario;
    }

    public void setFkIdEstagiario(RhEstagiario fkIdEstagiario) {
        this.fkIdEstagiario = fkIdEstagiario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEscalaEstagiario != null ? pkIdEscalaEstagiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEscalaEstagiario)) {
            return false;
        }
        SupiEscalaEstagiario other = (SupiEscalaEstagiario) object;
        if ((this.pkIdEscalaEstagiario == null && other.pkIdEscalaEstagiario != null) || (this.pkIdEscalaEstagiario != null && !this.pkIdEscalaEstagiario.equals(other.pkIdEscalaEstagiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEscalaEstagiario[ pkIdEscalaEstagiario=" + pkIdEscalaEstagiario + " ]";
    }
    
}
