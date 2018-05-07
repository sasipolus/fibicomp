package com.polus.fibicomp.workflow.service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.polus.fibicomp.committee.dao.CommitteeDao;
import com.polus.fibicomp.constants.Constants;
import com.polus.fibicomp.workflow.comparator.WorkflowMapDetailComparator;
import com.polus.fibicomp.workflow.dao.WorkflowDao;
import com.polus.fibicomp.workflow.pojo.Workflow;
import com.polus.fibicomp.workflow.pojo.WorkflowAttachment;
import com.polus.fibicomp.workflow.pojo.WorkflowDetail;
import com.polus.fibicomp.workflow.pojo.WorkflowMapDetail;

@Transactional
@Service(value = "workflowService")
public class WorkflowServiceImpl implements WorkflowService {

	protected static Logger logger = Logger.getLogger(WorkflowServiceImpl.class.getName());

	@Autowired
	private WorkflowDao workflowDao;

	@Autowired
	private CommitteeDao committeeDao;

	@Override
	public Workflow createWorkflow(Integer moduleItemId, String userName) {
		// for re submission case
		Workflow activeWorkflow = null;
		activeWorkflow = workflowDao.fetchActiveWorkflowByModuleItemId(moduleItemId);
		if (activeWorkflow != null) {
			activeWorkflow.setIsWorkflowActive(false);
			workflowDao.saveWorkflow(activeWorkflow);
		}

		Workflow workflow = new Workflow();
		workflow.setIsWorkflowActive(true);
		workflow.setModuleCode(1);
		workflow.setModuleItemId(moduleItemId);
		workflow.setCreateTimeStamp(committeeDao.getCurrentTimestamp());
		workflow.setCreateUser(userName);
		workflow.setUpdateTimeStamp(committeeDao.getCurrentTimestamp());
		workflow.setUpdateUser(userName);

		List<WorkflowMapDetail> workflowMapDetails = workflowDao.fetchWorkflowMapDetail();
		Collections.sort(workflowMapDetails, new WorkflowMapDetailComparator());
		List<WorkflowDetail> workflowDetails = new ArrayList<WorkflowDetail>();
		for(WorkflowMapDetail workflowMapDetail : workflowMapDetails) {
			WorkflowDetail workflowDetail = new WorkflowDetail();
			if (workflowMapDetail.getApprovalStopNumber() == 1 && workflowMapDetail.getApproverNumber() == 1) {
				workflowDetail.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_WAITING);
				workflowDetail.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_WAITING));
			} else {
				workflowDetail.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_TO_BE_SUBMITTED);
				workflowDetail.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_TO_BE_SUBMITTED));
			}
			workflowDetail.setApprovalStopNumber(workflowMapDetail.getApprovalStopNumber());
			workflowDetail.setApproverNumber(workflowMapDetail.getApproverNumber());
			workflowDetail.setApproverPersonId(workflowMapDetail.getApproverPersonId());
			workflowDetail.setMapId(workflowMapDetail.getMapId());
			workflowDetail.setWorkflowMap(workflowMapDetail.getWorkflowMap());
			workflowDetail.setPrimaryApproverFlag(workflowMapDetail.getPrimaryApproverFlag());
			workflowDetail.setUpdateTimeStamp(committeeDao.getCurrentTimestamp());
			workflowDetail.setUpdateUser(userName);
			workflowDetail.setApproverPersonName(workflowMapDetail.getApproverPersonName());
			workflowDetail.setWorkflow(workflow);
			workflowDetails.add(workflowDetail);
		}
		workflow.setWorkflowDetails(workflowDetails);
		workflow = workflowDao.saveWorkflow(workflow);
		return workflow;		
	}

	@Override
	public void approveOrRejectWorkflowDetail(String actionType, Integer moduleItemId, String personId, String approverComment, MultipartFile[] files) {
		try {
			Workflow workflow = workflowDao.fetchActiveWorkflowByModuleItemId(moduleItemId);
			WorkflowDetail workflowDetail = workflowDao.findUniqueWorkflowDetailByCriteria(workflow.getWorkflowId(), personId);
			List<WorkflowAttachment> workflowAttachments = new ArrayList<WorkflowAttachment>();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					WorkflowAttachment workflowAttachment = new WorkflowAttachment();
					workflowAttachment.setDescription(approverComment);
					workflowAttachment.setUpdateTimeStamp(committeeDao.getCurrentTimestamp());
					workflowAttachment.setUpdateUser(personId);			
					workflowAttachment.setAttachment(files[i].getBytes());
					workflowAttachment.setFileName(files[i].getOriginalFilename());
					workflowAttachment.setMimeType(files[i].getContentType());
					workflowAttachment.setWorkflowDetail(workflowDetail);
					workflowAttachments.add(workflowAttachment);
				}
				workflowDetail.getWorkflowAttachments().addAll(workflowAttachments);
			}
			List<WorkflowDetail> workflowDetailLists = workflowDao.fetchWorkflowDetailListByApprovalStopNumber(workflowDetail.getApprovalStopNumber(), Constants.WORKFLOW_STATUS_CODE_WAITING);
			if (actionType.equals("A")) {
				/*workflowDetail.setApprovalComment(approverComment);
				workflowDetail.setApprovalDate(new Date(committeeDao.getCurrentDate().getTime()));*/
				workflowDetail.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_APPROVED);
				workflowDetail.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_APPROVED));
				//workflowDetail = workflowDao.saveWorkflowDetail(workflowDetail);
				for(WorkflowDetail workflowDetailList : workflowDetailLists) {
					workflowDetailList.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_APPROVED);
					workflowDetailList.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_APPROVED));
				}
			} else {
				/*workflowDetail.setApprovalComment(approverComment);
				workflowDetail.setApprovalDate(new Date(committeeDao.getCurrentDate().getTime()));*/
				workflowDetail.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_REJECTED);
				workflowDetail.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_REJECTED));
				//workflowDetail = workflowDao.saveWorkflowDetail(workflowDetail);
				for(WorkflowDetail workflowDetailList : workflowDetailLists) {
					workflowDetailList.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_REJECTED);
					workflowDetailList.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_REJECTED));
				}
			}
			workflowDetail.setApprovalComment(approverComment);
			workflowDetail.setApprovalDate(new Date(committeeDao.getCurrentDate().getTime()));
			Integer maxApprovalStopNumber = workflowDao.getMaxStopNumber(workflow.getWorkflowId());
			Integer approveStopNumber = workflowDetail.getApprovalStopNumber() + 1;
			if(approveStopNumber <= maxApprovalStopNumber) {
				List<WorkflowDetail> workflowDetailList = workflowDao.fetchWorkflowDetailListByApprovalStopNumber(approveStopNumber, Constants.WORKFLOW_STATUS_CODE_TO_BE_SUBMITTED);
				for(WorkflowDetail newWorkflowDetail : workflowDetailList) {
					newWorkflowDetail.setApprovalStatusCode(Constants.WORKFLOW_STATUS_CODE_WAITING);
					newWorkflowDetail.setWorkflowStatus(workflowDao.fetchWorkflowStatusByStatusCode(Constants.WORKFLOW_STATUS_CODE_WAITING));
				}
			}
			workflowDetail = workflowDao.saveWorkflowDetail(workflowDetail);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFinalApprover(Integer moduleItemId, String personId) {
		Workflow workflow = workflowDao.fetchActiveWorkflowByModuleItemId(moduleItemId);
		WorkflowDetail currentWorkflowDetail = workflowDao.findUniqueWorkflowDetailByCriteria(workflow.getWorkflowId(), personId);
		WorkflowDetail finalWorkflowDetail = workflowDao.fetchFinalApprover(workflow.getWorkflowId());
		if (currentWorkflowDetail.getApprovalStopNumber() == finalWorkflowDetail.getApprovalStopNumber()
				&& currentWorkflowDetail.getApproverNumber() == finalWorkflowDetail.getApproverNumber()) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntity<byte[]> downloadWorkflowAttachment(Integer attachmentId) {
		WorkflowAttachment attachment = workflowDao.fetchWorkflowAttachmentById(attachmentId);
		ResponseEntity<byte[]> attachmentData = null;
		try {
			byte[] data = attachment.getAttachment();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(attachment.getMimeType()));
			String filename = attachment.getFileName();
			headers.setContentDispositionFormData(filename, filename);
			headers.setContentLength(data.length);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			headers.setPragma("public");
			attachmentData = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attachmentData;
	}

	@Override
	public boolean isFirstApprover(Integer moduleItemId, String personId) {
		Workflow workflow = workflowDao.fetchActiveWorkflowByModuleItemId(moduleItemId);
		WorkflowDetail currentWorkflowDetail = workflowDao.findUniqueWorkflowDetailByCriteria(workflow.getWorkflowId(), personId);
		WorkflowDetail finalWorkflowDetail = workflowDao.fetchFirstApprover(workflow.getWorkflowId());
		if (currentWorkflowDetail.getApprovalStopNumber() == finalWorkflowDetail.getApprovalStopNumber()
				&& currentWorkflowDetail.getApproverNumber() == finalWorkflowDetail.getApproverNumber()) {
			return true;
		}
		return false;
	}

}