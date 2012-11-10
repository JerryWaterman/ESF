/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shem8application;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
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
 * @author jwaterma
 */
@Entity
@Table(name = "SHASPTEST", catalog = "", schema = "CSHEM")
@NamedQueries({
    @NamedQuery(name = "Shasptest.findAll", query = "SELECT s FROM Shasptest s"),
    @NamedQuery(name = "Shasptest.findBySysid", query = "SELECT s FROM Shasptest s WHERE s.sysid = :sysid"),
    @NamedQuery(name = "Shasptest.findByHaspnum", query = "SELECT s FROM Shasptest s WHERE s.haspnum = :haspnum"),
    @NamedQuery(name = "Shasptest.findByInactive", query = "SELECT s FROM Shasptest s WHERE s.inactive = :inactive"),
    @NamedQuery(name = "Shasptest.findByHaspPreparer", query = "SELECT s FROM Shasptest s WHERE s.haspPreparer = :haspPreparer"),
    @NamedQuery(name = "Shasptest.findByInvest1lastname", query = "SELECT s FROM Shasptest s WHERE s.invest1lastname = :invest1lastname"),
    @NamedQuery(name = "Shasptest.findByInvest1firstname", query = "SELECT s FROM Shasptest s WHERE s.invest1firstname = :invest1firstname"),
    @NamedQuery(name = "Shasptest.findByInvest2lastname", query = "SELECT s FROM Shasptest s WHERE s.invest2lastname = :invest2lastname"),
    @NamedQuery(name = "Shasptest.findByInvest2firstname", query = "SELECT s FROM Shasptest s WHERE s.invest2firstname = :invest2firstname"),
    @NamedQuery(name = "Shasptest.findByInvest3lastname", query = "SELECT s FROM Shasptest s WHERE s.invest3lastname = :invest3lastname"),
    @NamedQuery(name = "Shasptest.findByInvest3firstname", query = "SELECT s FROM Shasptest s WHERE s.invest3firstname = :invest3firstname"),
    @NamedQuery(name = "Shasptest.findByInvest4lastname", query = "SELECT s FROM Shasptest s WHERE s.invest4lastname = :invest4lastname"),
    @NamedQuery(name = "Shasptest.findByInvest4firstname", query = "SELECT s FROM Shasptest s WHERE s.invest4firstname = :invest4firstname"),
    @NamedQuery(name = "Shasptest.findByInvest5lastname", query = "SELECT s FROM Shasptest s WHERE s.invest5lastname = :invest5lastname"),
    @NamedQuery(name = "Shasptest.findByInvest5firstname", query = "SELECT s FROM Shasptest s WHERE s.invest5firstname = :invest5firstname"),
    @NamedQuery(name = "Shasptest.findByInvest6lastname", query = "SELECT s FROM Shasptest s WHERE s.invest6lastname = :invest6lastname"),
    @NamedQuery(name = "Shasptest.findByInvest6firstname", query = "SELECT s FROM Shasptest s WHERE s.invest6firstname = :invest6firstname"),
    @NamedQuery(name = "Shasptest.findByInvest7lastname", query = "SELECT s FROM Shasptest s WHERE s.invest7lastname = :invest7lastname"),
    @NamedQuery(name = "Shasptest.findByInvest7firstname", query = "SELECT s FROM Shasptest s WHERE s.invest7firstname = :invest7firstname"),
    @NamedQuery(name = "Shasptest.findByInvest8lastname", query = "SELECT s FROM Shasptest s WHERE s.invest8lastname = :invest8lastname"),
    @NamedQuery(name = "Shasptest.findByInvest8firstname", query = "SELECT s FROM Shasptest s WHERE s.invest8firstname = :invest8firstname"),
    @NamedQuery(name = "Shasptest.findByBranchchief", query = "SELECT s FROM Shasptest s WHERE s.branchchief = :branchchief"),
    @NamedQuery(name = "Shasptest.findByOffice", query = "SELECT s FROM Shasptest s WHERE s.office = :office"),
    @NamedQuery(name = "Shasptest.findByLab", query = "SELECT s FROM Shasptest s WHERE s.lab = :lab"),
    @NamedQuery(name = "Shasptest.findByDivisionId", query = "SELECT s FROM Shasptest s WHERE s.divisionId = :divisionId"),
    @NamedQuery(name = "Shasptest.findByBranchId", query = "SELECT s FROM Shasptest s WHERE s.branchId = :branchId"),
    @NamedQuery(name = "Shasptest.findByTitle", query = "SELECT s FROM Shasptest s WHERE s.title = :title"),
    @NamedQuery(name = "Shasptest.findByNotes", query = "SELECT s FROM Shasptest s WHERE s.notes = :notes"),
    @NamedQuery(name = "Shasptest.findByLocation1", query = "SELECT s FROM Shasptest s WHERE s.location1 = :location1"),
    @NamedQuery(name = "Shasptest.findByLocation2", query = "SELECT s FROM Shasptest s WHERE s.location2 = :location2"),
    @NamedQuery(name = "Shasptest.findByLocation3", query = "SELECT s FROM Shasptest s WHERE s.location3 = :location3"),
    @NamedQuery(name = "Shasptest.findByLocation4", query = "SELECT s FROM Shasptest s WHERE s.location4 = :location4"),
    @NamedQuery(name = "Shasptest.findByLocation5", query = "SELECT s FROM Shasptest s WHERE s.location5 = :location5"),
    @NamedQuery(name = "Shasptest.findByFieldactivities", query = "SELECT s FROM Shasptest s WHERE s.fieldactivities = :fieldactivities"),
    @NamedQuery(name = "Shasptest.findByLabactivities", query = "SELECT s FROM Shasptest s WHERE s.labactivities = :labactivities"),
    @NamedQuery(name = "Shasptest.findByTreatabilitystudy", query = "SELECT s FROM Shasptest s WHERE s.treatabilitystudy = :treatabilitystudy"),
    @NamedQuery(name = "Shasptest.findByHazardouswastegenerator", query = "SELECT s FROM Shasptest s WHERE s.hazardouswastegenerator = :hazardouswastegenerator"),
    @NamedQuery(name = "Shasptest.findByBuilding", query = "SELECT s FROM Shasptest s WHERE s.building = :building"),
    @NamedQuery(name = "Shasptest.findByCurrenthaspversion", query = "SELECT s FROM Shasptest s WHERE s.currenthaspversion = :currenthaspversion"),
    @NamedQuery(name = "Shasptest.findByApprovaldate", query = "SELECT s FROM Shasptest s WHERE s.approvaldate = :approvaldate"),
    @NamedQuery(name = "Shasptest.findByExpirationdate", query = "SELECT s FROM Shasptest s WHERE s.expirationdate = :expirationdate"),
    @NamedQuery(name = "Shasptest.findByNextreview", query = "SELECT s FROM Shasptest s WHERE s.nextreview = :nextreview"),
    @NamedQuery(name = "Shasptest.findByAnnualreview1", query = "SELECT s FROM Shasptest s WHERE s.annualreview1 = :annualreview1"),
    @NamedQuery(name = "Shasptest.findByAnnualreview2", query = "SELECT s FROM Shasptest s WHERE s.annualreview2 = :annualreview2"),
    @NamedQuery(name = "Shasptest.findByTestcolumn1", query = "SELECT s FROM Shasptest s WHERE s.testcolumn1 = :testcolumn1"),
    @NamedQuery(name = "Shasptest.findByTestcolumn2", query = "SELECT s FROM Shasptest s WHERE s.testcolumn2 = :testcolumn2")})
public class Shasptest implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SYSID")
    private Long sysid;
    @Column(name = "HASPNUM")
    private String haspnum;
    @Column(name = "INACTIVE")
    private String inactive;
    @Column(name = "HASP_PREPARER")
    private String haspPreparer;
    @Column(name = "INVEST1LASTNAME")
    private String invest1lastname;
    @Column(name = "INVEST1FIRSTNAME")
    private String invest1firstname;
    @Column(name = "INVEST2LASTNAME")
    private String invest2lastname;
    @Column(name = "INVEST2FIRSTNAME")
    private String invest2firstname;
    @Column(name = "INVEST3LASTNAME")
    private String invest3lastname;
    @Column(name = "INVEST3FIRSTNAME")
    private String invest3firstname;
    @Column(name = "INVEST4LASTNAME")
    private String invest4lastname;
    @Column(name = "INVEST4FIRSTNAME")
    private String invest4firstname;
    @Column(name = "INVEST5LASTNAME")
    private String invest5lastname;
    @Column(name = "INVEST5FIRSTNAME")
    private String invest5firstname;
    @Column(name = "INVEST6LASTNAME")
    private String invest6lastname;
    @Column(name = "INVEST6FIRSTNAME")
    private String invest6firstname;
    @Column(name = "INVEST7LASTNAME")
    private String invest7lastname;
    @Column(name = "INVEST7FIRSTNAME")
    private String invest7firstname;
    @Column(name = "INVEST8LASTNAME")
    private String invest8lastname;
    @Column(name = "INVEST8FIRSTNAME")
    private String invest8firstname;
    @Column(name = "BRANCHCHIEF")
    private String branchchief;
    @Column(name = "OFFICE")
    private String office;
    @Column(name = "LAB")
    private String lab;
    @Column(name = "DIVISION_ID")
    private String divisionId;
    @Column(name = "BRANCH_ID")
    private String branchId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "NOTES")
    private String notes;
    @Column(name = "LOCATION1")
    private String location1;
    @Column(name = "LOCATION2")
    private String location2;
    @Column(name = "LOCATION3")
    private String location3;
    @Column(name = "LOCATION4")
    private String location4;
    @Column(name = "LOCATION5")
    private String location5;
    @Column(name = "FIELDACTIVITIES")
    private String fieldactivities;
    @Column(name = "LABACTIVITIES")
    private String labactivities;
    @Column(name = "TREATABILITYSTUDY")
    private String treatabilitystudy;
    @Column(name = "HAZARDOUSWASTEGENERATOR")
    private String hazardouswastegenerator;
    @Column(name = "BUILDING")
    private String building;
    @Column(name = "CURRENTHASPVERSION")
    private String currenthaspversion;
    @Column(name = "APPROVALDATE")
    private String approvaldate;
    @Column(name = "EXPIRATIONDATE")
    private String expirationdate;
    @Column(name = "NEXTREVIEW")
    private String nextreview;
    @Column(name = "ANNUALREVIEW1")
    private String annualreview1;
    @Column(name = "ANNUALREVIEW2")
    private String annualreview2;
    @Column(name = "TESTCOLUMN1")
    private String testcolumn1;
    @Column(name = "TESTCOLUMN2")
    private String testcolumn2;

    public Shasptest() {
    }

    public Shasptest(Long sysid) {
        this.sysid = sysid;
    }

    public Long getSysid() {
        return sysid;
    }

    public void setSysid(Long sysid) {
        Long oldSysid = this.sysid;
        this.sysid = sysid;
        changeSupport.firePropertyChange("sysid", oldSysid, sysid);
    }

    public String getHaspnum() {
        return haspnum;
    }

    public void setHaspnum(String haspnum) {
        String oldHaspnum = this.haspnum;
        this.haspnum = haspnum;
        changeSupport.firePropertyChange("haspnum", oldHaspnum, haspnum);
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        String oldInactive = this.inactive;
        this.inactive = inactive;
        changeSupport.firePropertyChange("inactive", oldInactive, inactive);
    }

    public String getHaspPreparer() {
        return haspPreparer;
    }

    public void setHaspPreparer(String haspPreparer) {
        String oldHaspPreparer = this.haspPreparer;
        this.haspPreparer = haspPreparer;
        changeSupport.firePropertyChange("haspPreparer", oldHaspPreparer, haspPreparer);
    }

    public String getInvest1lastname() {
        return invest1lastname;
    }

    public void setInvest1lastname(String invest1lastname) {
        String oldInvest1lastname = this.invest1lastname;
        this.invest1lastname = invest1lastname;
        changeSupport.firePropertyChange("invest1lastname", oldInvest1lastname, invest1lastname);
    }

    public String getInvest1firstname() {
        return invest1firstname;
    }

    public void setInvest1firstname(String invest1firstname) {
        String oldInvest1firstname = this.invest1firstname;
        this.invest1firstname = invest1firstname;
        changeSupport.firePropertyChange("invest1firstname", oldInvest1firstname, invest1firstname);
    }

    public String getInvest2lastname() {
        return invest2lastname;
    }

    public void setInvest2lastname(String invest2lastname) {
        String oldInvest2lastname = this.invest2lastname;
        this.invest2lastname = invest2lastname;
        changeSupport.firePropertyChange("invest2lastname", oldInvest2lastname, invest2lastname);
    }

    public String getInvest2firstname() {
        return invest2firstname;
    }

    public void setInvest2firstname(String invest2firstname) {
        String oldInvest2firstname = this.invest2firstname;
        this.invest2firstname = invest2firstname;
        changeSupport.firePropertyChange("invest2firstname", oldInvest2firstname, invest2firstname);
    }

    public String getInvest3lastname() {
        return invest3lastname;
    }

    public void setInvest3lastname(String invest3lastname) {
        String oldInvest3lastname = this.invest3lastname;
        this.invest3lastname = invest3lastname;
        changeSupport.firePropertyChange("invest3lastname", oldInvest3lastname, invest3lastname);
    }

    public String getInvest3firstname() {
        return invest3firstname;
    }

    public void setInvest3firstname(String invest3firstname) {
        String oldInvest3firstname = this.invest3firstname;
        this.invest3firstname = invest3firstname;
        changeSupport.firePropertyChange("invest3firstname", oldInvest3firstname, invest3firstname);
    }

    public String getInvest4lastname() {
        return invest4lastname;
    }

    public void setInvest4lastname(String invest4lastname) {
        String oldInvest4lastname = this.invest4lastname;
        this.invest4lastname = invest4lastname;
        changeSupport.firePropertyChange("invest4lastname", oldInvest4lastname, invest4lastname);
    }

    public String getInvest4firstname() {
        return invest4firstname;
    }

    public void setInvest4firstname(String invest4firstname) {
        String oldInvest4firstname = this.invest4firstname;
        this.invest4firstname = invest4firstname;
        changeSupport.firePropertyChange("invest4firstname", oldInvest4firstname, invest4firstname);
    }

    public String getInvest5lastname() {
        return invest5lastname;
    }

    public void setInvest5lastname(String invest5lastname) {
        String oldInvest5lastname = this.invest5lastname;
        this.invest5lastname = invest5lastname;
        changeSupport.firePropertyChange("invest5lastname", oldInvest5lastname, invest5lastname);
    }

    public String getInvest5firstname() {
        return invest5firstname;
    }

    public void setInvest5firstname(String invest5firstname) {
        String oldInvest5firstname = this.invest5firstname;
        this.invest5firstname = invest5firstname;
        changeSupport.firePropertyChange("invest5firstname", oldInvest5firstname, invest5firstname);
    }

    public String getInvest6lastname() {
        return invest6lastname;
    }

    public void setInvest6lastname(String invest6lastname) {
        String oldInvest6lastname = this.invest6lastname;
        this.invest6lastname = invest6lastname;
        changeSupport.firePropertyChange("invest6lastname", oldInvest6lastname, invest6lastname);
    }

    public String getInvest6firstname() {
        return invest6firstname;
    }

    public void setInvest6firstname(String invest6firstname) {
        String oldInvest6firstname = this.invest6firstname;
        this.invest6firstname = invest6firstname;
        changeSupport.firePropertyChange("invest6firstname", oldInvest6firstname, invest6firstname);
    }

    public String getInvest7lastname() {
        return invest7lastname;
    }

    public void setInvest7lastname(String invest7lastname) {
        String oldInvest7lastname = this.invest7lastname;
        this.invest7lastname = invest7lastname;
        changeSupport.firePropertyChange("invest7lastname", oldInvest7lastname, invest7lastname);
    }

    public String getInvest7firstname() {
        return invest7firstname;
    }

    public void setInvest7firstname(String invest7firstname) {
        String oldInvest7firstname = this.invest7firstname;
        this.invest7firstname = invest7firstname;
        changeSupport.firePropertyChange("invest7firstname", oldInvest7firstname, invest7firstname);
    }

    public String getInvest8lastname() {
        return invest8lastname;
    }

    public void setInvest8lastname(String invest8lastname) {
        String oldInvest8lastname = this.invest8lastname;
        this.invest8lastname = invest8lastname;
        changeSupport.firePropertyChange("invest8lastname", oldInvest8lastname, invest8lastname);
    }

    public String getInvest8firstname() {
        return invest8firstname;
    }

    public void setInvest8firstname(String invest8firstname) {
        String oldInvest8firstname = this.invest8firstname;
        this.invest8firstname = invest8firstname;
        changeSupport.firePropertyChange("invest8firstname", oldInvest8firstname, invest8firstname);
    }

    public String getBranchchief() {
        return branchchief;
    }

    public void setBranchchief(String branchchief) {
        String oldBranchchief = this.branchchief;
        this.branchchief = branchchief;
        changeSupport.firePropertyChange("branchchief", oldBranchchief, branchchief);
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        String oldOffice = this.office;
        this.office = office;
        changeSupport.firePropertyChange("office", oldOffice, office);
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        String oldLab = this.lab;
        this.lab = lab;
        changeSupport.firePropertyChange("lab", oldLab, lab);
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        String oldDivisionId = this.divisionId;
        this.divisionId = divisionId;
        changeSupport.firePropertyChange("divisionId", oldDivisionId, divisionId);
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        String oldBranchId = this.branchId;
        this.branchId = branchId;
        changeSupport.firePropertyChange("branchId", oldBranchId, branchId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldTitle = this.title;
        this.title = title;
        changeSupport.firePropertyChange("title", oldTitle, title);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        String oldNotes = this.notes;
        this.notes = notes;
        changeSupport.firePropertyChange("notes", oldNotes, notes);
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        String oldLocation1 = this.location1;
        this.location1 = location1;
        changeSupport.firePropertyChange("location1", oldLocation1, location1);
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        String oldLocation2 = this.location2;
        this.location2 = location2;
        changeSupport.firePropertyChange("location2", oldLocation2, location2);
    }

    public String getLocation3() {
        return location3;
    }

    public void setLocation3(String location3) {
        String oldLocation3 = this.location3;
        this.location3 = location3;
        changeSupport.firePropertyChange("location3", oldLocation3, location3);
    }

    public String getLocation4() {
        return location4;
    }

    public void setLocation4(String location4) {
        String oldLocation4 = this.location4;
        this.location4 = location4;
        changeSupport.firePropertyChange("location4", oldLocation4, location4);
    }

    public String getLocation5() {
        return location5;
    }

    public void setLocation5(String location5) {
        String oldLocation5 = this.location5;
        this.location5 = location5;
        changeSupport.firePropertyChange("location5", oldLocation5, location5);
    }

    public String getFieldactivities() {
        return fieldactivities;
    }

    public void setFieldactivities(String fieldactivities) {
        String oldFieldactivities = this.fieldactivities;
        this.fieldactivities = fieldactivities;
        changeSupport.firePropertyChange("fieldactivities", oldFieldactivities, fieldactivities);
    }

    public String getLabactivities() {
        return labactivities;
    }

    public void setLabactivities(String labactivities) {
        String oldLabactivities = this.labactivities;
        this.labactivities = labactivities;
        changeSupport.firePropertyChange("labactivities", oldLabactivities, labactivities);
    }

    public String getTreatabilitystudy() {
        return treatabilitystudy;
    }

    public void setTreatabilitystudy(String treatabilitystudy) {
        String oldTreatabilitystudy = this.treatabilitystudy;
        this.treatabilitystudy = treatabilitystudy;
        changeSupport.firePropertyChange("treatabilitystudy", oldTreatabilitystudy, treatabilitystudy);
    }

    public String getHazardouswastegenerator() {
        return hazardouswastegenerator;
    }

    public void setHazardouswastegenerator(String hazardouswastegenerator) {
        String oldHazardouswastegenerator = this.hazardouswastegenerator;
        this.hazardouswastegenerator = hazardouswastegenerator;
        changeSupport.firePropertyChange("hazardouswastegenerator", oldHazardouswastegenerator, hazardouswastegenerator);
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        String oldBuilding = this.building;
        this.building = building;
        changeSupport.firePropertyChange("building", oldBuilding, building);
    }

    public String getCurrenthaspversion() {
        return currenthaspversion;
    }

    public void setCurrenthaspversion(String currenthaspversion) {
        String oldCurrenthaspversion = this.currenthaspversion;
        this.currenthaspversion = currenthaspversion;
        changeSupport.firePropertyChange("currenthaspversion", oldCurrenthaspversion, currenthaspversion);
    }

    public String getApprovaldate() {
        return approvaldate;
    }

    public void setApprovaldate(String approvaldate) {
        String oldApprovaldate = this.approvaldate;
        this.approvaldate = approvaldate;
        changeSupport.firePropertyChange("approvaldate", oldApprovaldate, approvaldate);
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        String oldExpirationdate = this.expirationdate;
        this.expirationdate = expirationdate;
        changeSupport.firePropertyChange("expirationdate", oldExpirationdate, expirationdate);
    }

    public String getNextreview() {
        return nextreview;
    }

    public void setNextreview(String nextreview) {
        String oldNextreview = this.nextreview;
        this.nextreview = nextreview;
        changeSupport.firePropertyChange("nextreview", oldNextreview, nextreview);
    }

    public String getAnnualreview1() {
        return annualreview1;
    }

    public void setAnnualreview1(String annualreview1) {
        String oldAnnualreview1 = this.annualreview1;
        this.annualreview1 = annualreview1;
        changeSupport.firePropertyChange("annualreview1", oldAnnualreview1, annualreview1);
    }

    public String getAnnualreview2() {
        return annualreview2;
    }

    public void setAnnualreview2(String annualreview2) {
        String oldAnnualreview2 = this.annualreview2;
        this.annualreview2 = annualreview2;
        changeSupport.firePropertyChange("annualreview2", oldAnnualreview2, annualreview2);
    }

    public String getTestcolumn1() {
        return testcolumn1;
    }

    public void setTestcolumn1(String testcolumn1) {
        String oldTestcolumn1 = this.testcolumn1;
        this.testcolumn1 = testcolumn1;
        changeSupport.firePropertyChange("testcolumn1", oldTestcolumn1, testcolumn1);
    }

    public String getTestcolumn2() {
        return testcolumn2;
    }

    public void setTestcolumn2(String testcolumn2) {
        String oldTestcolumn2 = this.testcolumn2;
        this.testcolumn2 = testcolumn2;
        changeSupport.firePropertyChange("testcolumn2", oldTestcolumn2, testcolumn2);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sysid != null ? sysid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shasptest)) {
            return false;
        }
        Shasptest other = (Shasptest) object;
        if ((this.sysid == null && other.sysid != null) || (this.sysid != null && !this.sysid.equals(other.sysid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shem8application.Shasptest[ sysid=" + sysid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
