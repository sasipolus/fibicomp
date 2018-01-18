package com.polus.fibicomp.view;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.polus.fibicomp.util.JpaCharBooleanConversion;

@Entity
@Table(name = "PROPOSALS_MV")
public class ProposalView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROPOSAL_NUMBER")
	private String proposalNumber;

	@Column(name = "DOCUMENT_NUMBER")
	private String documentNumber;

	@Version
	@Column(name = "VER_NBR", length = 8)
	protected Integer versionNumber;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "LEAD_UNIT_NUMBER")
	private String leadUnitNumber;

	@Column(name = "LEAD_UNIT")
	private String leadUnit;

	@Column(name = "SPONSOR_CODE")
	private String sponsorCode;

	@Column(name = "SPONSOR")
	private String sponsor;

	@Column(name = "DEADLINE_DATE")
	private Date deadLinedate;

	@Column(name = "STATUS_CODE")
	private Integer statusCode;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "UPDATE_TIMESTAMP")
	private Timestamp updateTimeStamp;

	@Column(name = "UPDATE_USER")
	private String updateUser;

	@Column(name = "PERSON_ID")
	private String personId;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "TOTALCOST")
	private String totalCost;

	@Column(name = "OWNED_BY_UNIT")
	private String deptName;

	@Column(name = "CERTIFIED")
	@Convert(converter = JpaCharBooleanConversion.class)
	private boolean certified;

	@Column(name = "PROP_PERSON_ROLE_ID")
	private String proposalPersonRoleId;

	@Column(name = "CERTIFICATION_REQUIRED")
	@Convert(converter = JpaCharBooleanConversion.class)
	private boolean certificationRequired;

	@Column(name = "ROLE_NAME")
	private String roleName;

	public String getProposalNumber() {
		return proposalNumber;
	}

	public void setProposalNumber(String proposalNumber) {
		this.proposalNumber = proposalNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeadUnitNumber() {
		return leadUnitNumber;
	}

	public void setLeadUnitNumber(String leadUnitNumber) {
		this.leadUnitNumber = leadUnitNumber;
	}

	public String getLeadUnit() {
		return leadUnit;
	}

	public void setLeadUnit(String leadUnit) {
		this.leadUnit = leadUnit;
	}

	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public Date getDeadLinedate() {
		return deadLinedate;
	}

	public void setDeadLinedate(Date deadLinedate) {
		this.deadLinedate = deadLinedate;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public boolean isCertified() {
		return certified;
	}

	public void setCertified(boolean certified) {
		this.certified = certified;
	}

	public String getProposalPersonRoleId() {
		return proposalPersonRoleId;
	}

	public void setProposalPersonRoleId(String proposalPersonRoleId) {
		this.proposalPersonRoleId = proposalPersonRoleId;
	}

	public boolean isCertificationRequired() {
		return certificationRequired;
	}

	public void setCertificationRequired(boolean certificationRequired) {
		this.certificationRequired = certificationRequired;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
