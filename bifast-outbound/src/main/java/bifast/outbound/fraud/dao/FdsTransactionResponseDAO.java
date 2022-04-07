package bifast.outbound.fraud.dao;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Transaction")
public class FdsTransactionResponseDAO {

	@JsonProperty("TransactionDateTime")
	private String transactionDateTime;
	@JsonProperty("ScoringScore")
	private String scoringScore;
	@JsonProperty("Grade")
	private String grade;
	@JsonProperty("TriggeredRules")
	private String triggeredRules;
	@JsonProperty("AssignedUser")
	private String assignedUser;
	@JsonProperty("AssignedTeam")
	private String assignedTeam;
	@JsonProperty("Alert")
	private String alert;
	@JsonProperty("RiskLevel")
	private String riskLevel;
	@JsonProperty("RuleDecision")
	private String ruleDecision;
	@JsonProperty("RandomNumber")
	private String randomNumber;
	@JsonProperty("ActionsQueued")
	private String actionsQueued;
	@JsonProperty("MachineLearningScore")
	private String machineLearningScore;
	
	public String getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public String getScoringScore() {
		return scoringScore;
	}
	public void setScoringScore(String scoringScore) {
		this.scoringScore = scoringScore;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTriggeredRules() {
		return triggeredRules;
	}
	public void setTriggeredRules(String triggeredRules) {
		this.triggeredRules = triggeredRules;
	}
	public String getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	public String getAssignedTeam() {
		return assignedTeam;
	}
	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getRuleDecision() {
		return Optional.ofNullable(ruleDecision).orElse("");
	}
	public void setRuleDecision(String ruleDecision) {
		this.ruleDecision = ruleDecision;
	}
	public String getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}
	public String getActionsQueued() {
		return actionsQueued;
	}
	public void setActionsQueued(String actionsQueued) {
		this.actionsQueued = actionsQueued;
	}
	public String getMachineLearningScore() {
		return machineLearningScore;
	}
	public void setMachineLearningScore(String machineLearningScore) {
		this.machineLearningScore = machineLearningScore;
	}
	
	
	
}
