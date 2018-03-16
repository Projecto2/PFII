/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author amed
 */
@Embeddable
public class EmgTratamentoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tratamento", nullable = false)
    private long pkIdTratamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_id_tipo_tratamento", nullable = false)
    private int fkIdTipoTratamento;

    public EmgTratamentoPK() {
    }

    public EmgTratamentoPK(long pkIdTratamento, int fkIdTipoTratamento) {
        this.pkIdTratamento = pkIdTratamento;
        this.fkIdTipoTratamento = fkIdTipoTratamento;
    }

    public long getPkIdTratamento() {
        return pkIdTratamento;
    }

    public void setPkIdTratamento(long pkIdTratamento) {
        this.pkIdTratamento = pkIdTratamento;
    }

    public int getFkIdTipoTratamento() {
        return fkIdTipoTratamento;
    }

    public void setFkIdTipoTratamento(int fkIdTipoTratamento) {
        this.fkIdTipoTratamento = fkIdTipoTratamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pkIdTratamento;
        hash += (int) fkIdTipoTratamento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmgTratamentoPK)) {
            return false;
        }
        EmgTratamentoPK other = (EmgTratamentoPK) object;
        if (this.pkIdTratamento != other.pkIdTratamento) {
            return false;
        }
        if (this.fkIdTipoTratamento != other.fkIdTipoTratamento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EmgTratamentoPK[ pkIdTratamento=" + pkIdTratamento + ", fkIdTipoTratamento=" + fkIdTipoTratamento + " ]";
    }
    
}
