package com.polus.fibicomp.workflow.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.polus.fibicomp.util.JpaCharBooleanConversion;

@Entity
@Table(name = "FIBI_WORKFLOW_DETAIL")
public class WorkflowDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "workflowDetailIdGenerator", strategy = "increment", parameters = {
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "workflowDetailIdGenerator")
	@Column(name = "WORKFLOW_DETAIL_ID")
	private Integer workflowDetailId;

	@JsonBackReference
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK1_FIBI_WORKFLOW_DETAIL"), name = "WORKFLOW_ID", referencedColumnName = "WORKFLOW_ID")
	private Workflow workflow;

	@Column(name = "MAP_ID")
	private Integer mapId;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK2_FIBI_WORKFLOW_DETAIL"), name = "MAP_ID", referencedColumnName = "MAP_ID", insertable = false, updatable = false)
	private WorkflowMap workflowMap;

	@Column(name = "MAP_NUMBER")
	private Integer mapNumber;

	@Column(name = "APPROVAL_STOP_NUMBER")
	private Integer approvalStopNumber;

	@Column(name = "APPROVER_NUMBER")
	private Integer approverNumber;

	@Column(name = "PRIMARY_APPROVER_FLAG")
	@Convert(converter = JpaCharBooleanConversion.class)
	private Boolean primaryApproverFlag;

	@Column(name = "APPROVER_PERSON_ID")
	private String approverPersonId;

	@Column(name = "APPROVER_PERSON_NAME")
	private String approverPersonName;

	@Column(name = "APPROVAL_STATUS_CODE")
	private String approvalStatusCode;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK3_FIBI_WORKFLOW_DETAIL"), name = "APPROVAL_STATUS_CODE", referencedColumnName = "APPROVAL_STATUS_CODE", insertable = false, updatable = false)
	private WorkflowStatus workflowStatus;

	@Column(name = "APPROVAL_COMMENT")
	private String approvalComment;

	@Column(name = "APPROVAL_DATE")
	private Date approvalDate;

	@Column(name = "UPDATE_USER")
	private String updateUser;

	@Column(name = "UPDATE_TIMESTAMP")
	private Timestamp updateTimeStamp;

	@JsonManagedReference
	@OneToMany(mappedBy = "workflowDetail", orphanRemoval = true, cascade = { CascadeType.ALL })
	private List<WorkflowAttachment> workflowAttachments;

	public Integer getWorkflowDetailId() {
		return workflowDetailId;
	}

	public void setWorkflowDetailId(Integer workflowDetailId) {
		this.workflowDetailId = workflowDetailId;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public WorkflowMap getWorkflowMap() {
		return workflowMap;
	}

	public void setWorkflowMap(WorkflowMap workflowMap) {
		this.workflowMap = workflowMap;
	}

	public Integer getMapNumber() {
		return mapNumber;
	}

	public void setMapNumber(Integer mapNumber) {
		this.mapNumber = mapNumber;
	}

	public Integer getApprovalStopNumber() {
		return approvalStopNumber;
	}

	public void setApprovalStopNumber(Integer approvalStopNumber) {
		this.approvalStopNumber = approvalStopNumber;
	}

	public Integer getApproverNumber() {
		return approverNumber;
	}

	public void setApproverNumber(Integer approverNumber) {
		this.approverNumber = approverNumber;
	}

	public Boolean getPrimaryApproverFlag() {
		return primaryApproverFlag;
	}

	public void setPrimaryApproverFlag(Boolean primaryApproverFlag) {
		this.primaryApproverFlag = primaryApproverFlag;
	}

	public String getApproverPersonId() {
		return approverPersonId;
	}

	public void setApproverPersonId(String approverPersonId) {
		this.approverPersonId = approverPersonId;
	}

	public WorkflowStatus getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(WorkflowStatus workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getApprovalComment() {
		return approvalComment;
	}

	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public String getApprovalStatusCode() {
		return approvalStatusCode;
	}

	public void setApprovalStatusCode(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<WorkflowAttachment> getWorkflowAttachments() {
		return workflowAttachments;
	}

	public void setWorkflowAttachments(List<WorkflowAttachment> workflowAttachments) {
		this.workflowAttachments = workflowAttachments;
	}

	public String getApproverPersonName() {
		return approverPersonName;
	}

	public void setApproverPersonName(String approverPersonName) {
		this.approverPersonName = approverPersonName;
	}

}