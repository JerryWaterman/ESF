/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shem8application;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Jeremy
 */
@Entity
@Table(name = "HASP_PI", catalog = "", schema = "CSHEM")
@NamedQueries({
    @NamedQuery(name = "HaspPi.findAll", query = "SELECT h FROM HaspPi h"),
    @NamedQuery(name = "HaspPi.findByPiSysid", query = "SELECT h FROM HaspPi h WHERE h.piSysid = :piSysid"),
    @NamedQuery(name = "HaspPi.findByPiName", query = "SELECT h FROM HaspPi h WHERE h.piName = :piName")})
public class HaspPi implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PI_SYSID")
    private BigDecimal piSysid;
    @Column(name = "PI_NAME")
    private String piName;

    public HaspPi() {
    }

    public HaspPi(BigDecimal piSysid) {
        this.piSysid = piSysid;
    }

    public BigDecimal getPiSysid() {
        return piSysid;
    }

    public void setPiSysid(BigDecimal piSysid) {
        BigDecimal oldPiSysid = this.piSysid;
        this.piSysid = piSysid;
        changeSupport.firePropertyChange("piSysid", oldPiSysid, piSysid);
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        String oldPiName = this.piName;
        this.piName = piName;
        changeSupport.firePropertyChange("piName", oldPiName, piName);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (piSysid != null ? piSysid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HaspPi)) {
            return false;
        }
        HaspPi other = (HaspPi) object;
        if ((this.piSysid == null && other.piSysid != null) || (this.piSysid != null && !this.piSysid.equals(other.piSysid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return piName;
        //return "shem8application.HaspPi[ piSysid=" + piSysid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
